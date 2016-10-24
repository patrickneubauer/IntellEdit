package at.ac.tuwien.big.autoedit.search.local.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.EvaluationVisitor;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCLExpression;
import org.omg.CORBA.portable.RemarshalException;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.ChangeType;
import at.ac.tuwien.big.autoedit.change.CostProvider;
import at.ac.tuwien.big.autoedit.change.Undoer;
import at.ac.tuwien.big.autoedit.change.basic.BasicClearConstantChange;
import at.ac.tuwien.big.autoedit.change.composite.CompositeChangeImpl;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.evaluate.Evaluable;
import at.ac.tuwien.big.autoedit.evaluate.EvaluableManager;
import at.ac.tuwien.big.autoedit.evaluate.EvaluationState;
import at.ac.tuwien.big.autoedit.evaluate.impl.EvaluableManagerImpl;
import at.ac.tuwien.big.autoedit.fixer.MakeTrue;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptReferenceInfo;
import at.ac.tuwien.big.autoedit.oclvisit.RejectingFilterManager;
import at.ac.tuwien.big.autoedit.oclvisit.TracingEvaluationVisitor;
import at.ac.tuwien.big.autoedit.proposal.Proposal;
import at.ac.tuwien.big.autoedit.proposal.ProposalList;
import at.ac.tuwien.big.autoedit.proposal.impl.ProposalListImpl;
import at.ac.tuwien.big.autoedit.search.local.LocalSearchInterface;
import at.ac.tuwien.big.autoedit.search.local.LocalSearchManager;
import at.ac.tuwien.big.autoedit.search.local.NeighborhoodProvider;
import at.ac.tuwien.big.autoedit.search.local.SimpleStream;
import at.ac.tuwien.big.autoedit.transfer.EcoreMapTransferFunction;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public class LocalSearchInterfaceImpl implements LocalSearchInterface {
	
	private Evaluable orieval;
	private String name;
	private EObject context;
	private SimpleStream<Change<?>> stream;
	private MyResource clonedRes;
	private WeakReference<MyResource> originalRes;
	private EcoreUtil.Copier copier;
	private EcoreTransferFunction transferFunc;
	private int processedChanges = 0;
	private CostProvider costProvider = CostProvider.DEFAULT_PROVIDER;
	
	private ProposalList<Double,? extends Proposal<Double,?>> myProposals =
			new ProposalListImpl<>();
	
	private boolean isFinished;
	
	private NeighborhoodProvider provider = NeighborhoodProvider.DEFAULT_DIRECTFIX;
	private int maxNeighbors = Integer.MAX_VALUE;
	private int maxSolutions = Integer.MAX_VALUE;
	private double objectReduction = 0.0002;
	private double originalQuality = 0.0;
	
	@Override
	public NeighborhoodProvider getNeighborhood() {
		return provider;
	}
	
	@Override
	public void setNeighborhood(NeighborhoodProvider provider) {
		this.provider = provider;
	}
	
	private LocalSearchInterfaceImpl(MyResource res, String name, Evaluable expr, EObject context, SimpleStream<Change<?>> stream,
			NeighborhoodProvider provider, double originalQuality, int maxNeighbors, int maxSolutions) {
		this.name = name;
		this.orieval = expr;
		
		this.stream = stream;
		
		this.provider = provider;
		if (maxNeighbors == -1) {
			maxNeighbors = provider==NeighborhoodProvider.DEFAULT_DIRECTFIX?Integer.MAX_VALUE:100;
		}
		if (maxSolutions == -1) {
			maxSolutions = provider==NeighborhoodProvider.DEFAULT_DIRECTFIX?Integer.MAX_VALUE:1000;
		}
		this.originalQuality = originalQuality;
		this.maxNeighbors = maxNeighbors;
		this.maxSolutions = maxSolutions;
		processedChanges = provider == NeighborhoodProvider.DEFAULT_LOCALSEARCH?100:0;
		this.maxSolutions+= processedChanges;

		//Theoretisch könnte man hier statisch slicen
		initResourceCopy(res,context);
		
	}
	
	public void initResourceCopy(MyResource res, EObject context) {
		copier = new EcoreUtil.Copier();
		this.clonedRes = res.clone(copier);
		this.context = copier.get(context);
		this.originalRes = new WeakReference<MyResource>(res);
		transferFunc = new EcoreMapTransferFunction(originalRes.get().getResource(),
				clonedRes.getResource(), copier);
	}
		
	public static LocalSearchInterfaceImpl create(MyResource res, String name, Evaluable expr, EObject context, SimpleStream<Change<?>> stream,
			NeighborhoodProvider provider, double originalQuality, int maxNeighbors, int maxSolutions) {
		return new LocalSearchInterfaceImpl(res, name, expr, context, stream, provider, originalQuality, maxNeighbors, maxSolutions);
	}
	
	public static LocalSearchInterfaceImpl create(MyResource res, String name, Evaluable expr, EObject context, SimpleStream<Change<?>> stream,
			NeighborhoodProvider provider, double originalQuality) {
		return new LocalSearchInterfaceImpl(res, name, expr, context, stream, provider, originalQuality, -1, -1);
	}
	
	public EcoreTransferFunction toThisResource() {
		return transferFunc;
	}
	
	public EcoreTransferFunction toOriginalResource() {
		return transferFunc.inverse();
	}

	@Override
	public LocalSearchInterface clone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void transfer(EcoreTransferFunction func) {
		context = func.forward(context);
	}

	@Override
	public Evaluable<?,?> getOriginalEvaluable() {
		return orieval;
	}
	

	@Override
	public EObject getContext() {
		return context;
	}

	@Override
	public SimpleStream<Change<?>> getStream() {
		return stream;
	}
	
	private Change<?>[] buildOriginalResourceChange(List<Change<?>> oldchanges, Change<?>... toAdd) {
		List<Change<?>> newchanges = new ArrayList<Change<?>>();
		EcoreTransferFunction toOriginal = toOriginalResource();
		List<Change<?>> totalOldChanges = new ArrayList<Change<?>>();
		for (Change<?> ch: oldchanges) {
			totalOldChanges.add(ch);
		}
		for (Change<?> ch: toAdd) {
			totalOldChanges.add(ch);
		}
		Change<?> oldr = new CompositeChangeImpl(clonedRes.getResource(), totalOldChanges);
		oldr.removeUnnecessarySubchanges();
		if (oldr.unbox().size() == 1) {
			oldr = oldr.unbox().get(0);
		}
		Change<?> newr = oldr.transfered(toOriginal);		
		return new Change<?>[]{oldr,newr};
	}
	
	private static final long maxTime = 5*1000L;
	
	
	
	private long stepTime = maxTime/10;
	private long totalTime = Long.MAX_VALUE;
	private PriorityQueue<SolutionState> curSolutions = new PriorityQueue<SolutionState>();
	private Set<List<Change<?>>> allSolutions = new HashSet<>();
	
	public void setTime(long stepTimeMs, long totalTimeMs) {
		this.stepTime = stepTimeMs;
		this.totalTime = totalTimeMs;
	}
	
	private EvaluableManager manager = new EvaluableManagerImpl();
	
	@Override
	public synchronized void initSearch() {
		

		EvaluationState state = manager.basicEvaluate(clonedRes,getOriginalEvaluable(), getContext());
		Object ret = state.getBasicResult();
		//System.out.println("Evaluation of " + name+" was " + ret + " ("+getOriginalEvaluable()+")");
		if (!(ret instanceof Boolean) || !((Boolean)ret)) {
			double curQuality = state.getQuality();
			EvalResult res = state.getResult();
			FixAttemptReferenceInfo completeInfo = res.getCompleteReferenceInfo();
			LocalSearchPartialSolution initSol = new LocalSearchPartialSolution(new Stack<Change<?>>(), curQuality, provider.getBaseFixes(res));
			allSolutions.add(new ArrayList<>());
			curSolutions.add(new SolutionState(initSol, provider.getNeighbours(initSol).iterator()));
		}
			
	}
	
	private boolean abort= false;

	@Override
	public void abortSearch() {
		
		abort = true;
		/*stepTime = totalTime = 0;
		clonedRes = null;
		copier = null;
		transferFunc = null;
		curSolutions = null;*/
	}
	
	private class SolutionState implements Comparable<SolutionState> {
		
		public SolutionState(LocalSearchPartialSolution solution, Iterator<? extends Change<?>> iter) {
			this.solution = solution;
			this.iter = iter;
		}
		
		private LocalSearchPartialSolution solution;
		private Iterator<? extends Change<?>> iter;
		private int usedSolutions;
		
		public Iterator<? extends Change<?>> iter() {
			if (iter == null) {
				iter = provider.getNeighbours(solution).iterator();
			}
			return iter;
		}
		
		public void addUsedSolution(int num) {
			usedSolutions+= num;
		}
		
		public double getEffectiveQuality() {
			return solution.getQuality()-usedSolutions*objectReduction;
		}

		@Override
		public int compareTo(SolutionState arg0) {
			int ret = -Double.compare(getEffectiveQuality(), arg0.getEffectiveQuality());
			return ret;
		}

		public LocalSearchPartialSolution getSolution() {
			return solution;
		}

		public double getOriginalQuality() {
			return solution.getImproveQuality();
		}
	}
	
	public void run() {
		doSomeSearch();
	}
	
	private int calculatedSolutions = 0;

	@Override
	public synchronized boolean doSomeSearch() {
		try {
		if (abort) {
			return false;
		}
		stepTime = Math.min(totalTime, stepTime);
		totalTime-= stepTime;
		double endTime = new Date().getTime()+stepTime;
		if (curCheckSolutionIndex < checkSolutions.size()) {
			
			for (; curCheckSolutionIndex < checkSolutions.size(); ++curCheckSolutionIndex) {
				if (abort || new Date().getTime() >= endTime) {
					//System.out.println("Time elapsed!");
					return false;
				}
				Change<?> oriChange = checkSolutions.get(curCheckSolutionIndex);
				Change<?> curChange = oriChange.transfered(transferFunc);
				Undoer undoer = curChange.execute();
				try {
					++processedChanges;
					EvaluationState state = manager.basicEvaluate(clonedRes,getOriginalEvaluable(), getContext());
					Object ret = state.getBasicResult();
					//System.out.println("Evaluation of " + name+" was " + ret + " ("+getOriginalEvaluable()+")");
					if (!(ret instanceof Boolean) || !((Boolean)ret)) {
							//Need search
						EvalResult res = state.getResult();
						double curQuality = state.getQuality();
						res.printAllDirectFixingActions();
						FixAttemptReferenceInfo completeInfo = res.getCompleteReferenceInfo();
						completeInfo.printComplete();
						Stack<Change<?>> alStack = new Stack<Change<?>>();
						alStack.addAll(curChange.unbox());
						if (allSolutions.add(oriChange.unbox())) {
							 if (curQuality > originalQuality) {
								 undoer.undo();
								 LocalSearchPartialSolution initSol = new LocalSearchPartialSolution(alStack, curQuality,  res.getAllDirectFixingActions());
								 curSolutions.add(new SolutionState(initSol, provider.getNeighbours(initSol).iterator()));
								 returnedSolutions.add(oriChange);
								 getStream().add(curChange, oriChange, curQuality, oriChange.getCosts());
								 undoer = curChange.execute();
							 }
						}
					} else {
						 if (allSolutions.add(oriChange.unbox())) {
							 returnedSolutions.add(oriChange);
							 undoer.undo();
							 getStream().add(curChange,oriChange, 1.0, oriChange.getCosts());
							 undoer = curChange.execute();
						 }
					}
				} finally {
					undoer.undo();
				}
			}
			if (curCheckSolutionIndex >= checkSolutions.size() && !checkSolutions.isEmpty()) {
				checkSolutions.clear();
			}
		}
		try {
		while (!curSolutions.isEmpty()) {
			if (abort || new Date().getTime() >= endTime) {
				//System.out.println("Time elapsed!");
				return false;
			}
			if (processedChanges > maxSolutions) {
				abort = true;
				return false;
			}
			++processedChanges;
			SolutionState curSol = curSolutions.poll();
			List<Undoer> undoers = new ArrayList<Undoer>();
			try {
			for (Change<?> change: curSol.getSolution().getChanges()) {
				undoers.add(change.execute());
			}
			//Jetzt ist man einmal auf dem alten Stand
			//Probiere alles an Fixes durch
			//Man muss ein paar ausprobieren und dann weiter machen, aber das existierende speichern
			int curNeighbors = 0;
			Iterator<? extends Change<?>> chIter = curSol.iter();
			
			for (Change<?> ch: (Iterable<Change<?>>)(Iterable)(()->chIter)) {
				
				if (ch.isIdentity()) {
					continue;
				}
				if (curNeighbors++ >= maxNeighbors) {
					//Store Iterator
					curSol.addUsedSolution(curNeighbors);
					curSolutions.add(curSol);
					break;
				}
				boolean previouslyName = false;
				Set<String> acceptableNames = new HashSet<String>(Arrays.asList("R1","G1","G2","C1","C2","Bla"));
				
				 for (EObject obj:clonedRes.iterateAllContents()) {
						if (obj != null && (obj.eClass().getName().equals("Consumer") || obj.eClass().getName().equals("Computer")) ) {
							if (!acceptableNames.contains(obj.eGet(obj.eClass().getEStructuralFeature("name")))) {
								previouslyName = true;
							} 
						}
					 }
				
				 Undoer undoer = ch.execute();
				 boolean haveUndone = false;
				 try {
				 /*System.out.println("Trying " +ch);
				 fact = EcoreEnvironmentFactory.INSTANCE;*/
				 EvaluationState state = manager.basicEvaluate(clonedRes,getOriginalEvaluable(), getContext());
				 Object ret = state.getBasicResult();

				 
				 if (ret instanceof Boolean && ((Boolean)ret)) {
					 //Passt, jetzt muss man nicht mehr weitermachen!

					/* for (EObject obj:clonedRes.iterateAllContents()) {
							if (obj != null && (obj.eClass().getName().equals("Consumer") || obj.eClass().getName().equals("Computer")) ) {
								if (!acceptableNames.contains(obj.eGet(obj.eClass().getEStructuralFeature("name"))) && !(ch instanceof BasicClearConstantChange)) {
									System.err.println("Namename");
								} 
							}
						 }*/
					 undoer.undo();
					 haveUndone = true;
					 for (int i = undoers.size()-1; i >= 0; --i) {
							undoers.get(i).undo();
					 }
					 Change<?>[] oriNewChange = buildOriginalResourceChange(curSol.getSolution().getChanges(),ch);
					 if (allSolutions.add(oriNewChange[0].unbox())) {
						 
						 returnedSolutions.add(oriNewChange[1]);
						 getStream().add(oriNewChange[0],oriNewChange[1], 1.0, oriNewChange[1].getCosts());
					 }
					 undoers = new ArrayList<Undoer>();
					 for (Change<?> change: curSol.getSolution().getChanges()) {
						undoers.add(change.execute());
					 }
				 } else {
					 EvalResult res = state.getResult();
					 double curQuality = state.getQuality();
					 LocalSearchPartialSolution toAdd = curSol.getSolution().added(ch, curQuality, provider.getBaseFixes(res));
					 if (allSolutions.add(toAdd.getChanges())) {
						 curSolutions.add(new SolutionState(toAdd,null));
						 if (curQuality > curSol.getOriginalQuality()) {
							
							 
							 undoer.undo();
							 haveUndone = true;
							 for (int i = undoers.size()-1; i >= 0; --i) {
									undoers.get(i).undo();
							 }
							 Change<?>[] oriNewChange = buildOriginalResourceChange(curSol.getSolution().getChanges(),ch);
							 returnedSolutions.add(oriNewChange[1]);
							 getStream().add(oriNewChange[0],oriNewChange[1], curQuality, oriNewChange[1].getCosts());
							 undoers = new ArrayList<Undoer>();
							 for (Change<?> change: curSol.getSolution().getChanges()) {
								undoers.add(change.execute());
							 }
						 }
					 }
					 
				 }
				/* if (returnedSolutions.size() > 200) {
					 System.err.println("High number of solutions?!");
				 }*/
				 }finally {
					 if (!haveUndone) {
						 undoer.undo();
					 }
				 }
					if (!previouslyName) {
					
					 for (EObject obj:clonedRes.iterateAllContents()) {
							if (obj != null && (obj.eClass().getName().equals("Consumer") || obj.eClass().getName().equals("Computer")) ) {
								if (!acceptableNames.contains(obj.eGet(obj.eClass().getEStructuralFeature("name")))) {
									previouslyName = true;
								} 
							}
						 }
					}
				
			}
			
		
			} catch (Exception e)  {
				if (abort) {
					return false;
				}
				e.printStackTrace();
				StringWriter writer = new StringWriter();
				PrintWriter pw = new PrintWriter(writer);
				e.printStackTrace(pw);
				String op = writer.toString();
				System.out.println(op);
			} finally {			
				for (int i = undoers.size()-1; i >= 0; --i) {
					undoers.get(i).undo();
				}
			}
		}
			
		} catch (Exception e) {
			if (abort) {
				return false;
			}
			StringWriter writer = new StringWriter();
			PrintWriter pw = new PrintWriter(writer);
			e.printStackTrace(pw);
			String op = writer.toString();
			System.out.println(op);
		}
		
		if (curSolutions.isEmpty()) {
			isFinished = true;
		}
		} catch (Exception e) {
			if (abort) {return false;}
			throw e;
		}
		return isFinished;
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public boolean isObsolete(Resource curResource) {
		return abort || originalRes.get() == null || !originalRes.get().getResource().equals(curResource);
	}
	
	@Override
	public double getQuality() {
		return -processedChanges;
	}
	
	private List<Change<?>> checkSolutions = new ArrayList<>();
	private List<Change<?>> returnedSolutions = new ArrayList<>();
	private int curCheckSolutionIndex = 0;
	
	public List<Change<?>> getReturnedOrCheckSolutions() {
		if (checkSolutions.isEmpty()) {
			return returnedSolutions;
		}
		return checkSolutions;
	}
	
	public List<Change<?>> getReturnedSolutions() {
		return this.returnedSolutions;
	}

	@Override
	public void copyFrom(LocalSearchInterface localSearchInterface, boolean reuseResource) {
		if (!(localSearchInterface instanceof LocalSearchInterfaceImpl)) {
			throw new UnsupportedOperationException("Can't copy localSearchInterfaceImpl with different class");
		}
		LocalSearchInterfaceImpl other = (LocalSearchInterfaceImpl)localSearchInterface;
		this.checkSolutions = other.getReturnedOrCheckSolutions();
		
	}


}
