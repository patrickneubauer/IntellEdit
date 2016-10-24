package at.ac.tuwien.big.autoedit.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.EvaluationVisitor;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.EvaluationVisitorImpl;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCLExpression;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.Variation;
import org.moeaframework.core.spi.OperatorFactory;
import org.moeaframework.core.spi.OperatorProvider;
import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.util.distributed.DistributedProblem;
import org.moeaframework.util.distributed.FutureSolution;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.ChangeType;
import at.ac.tuwien.big.autoedit.change.CostProvider;
import at.ac.tuwien.big.autoedit.change.Undoer;
import at.ac.tuwien.big.autoedit.change.composite.CompositeChangeImpl;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.evaluate.impl.OCLExpressionEvaluable;
import at.ac.tuwien.big.autoedit.fixer.MakeTrue;
import at.ac.tuwien.big.autoedit.fixer.impl.MakeTrueImpl;
import at.ac.tuwien.big.autoedit.global.GlobalSearch;
import at.ac.tuwien.big.autoedit.global.MOEAChangeVariable;
import at.ac.tuwien.big.autoedit.global.MyResourceContainer;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
import at.ac.tuwien.big.autoedit.oclvisit.RejectingFilterManager;
import at.ac.tuwien.big.autoedit.oclvisit.TracingEvaluationVisitor;
import at.ac.tuwien.big.autoedit.proposal.Proposal;
import at.ac.tuwien.big.autoedit.proposal.impl.ProposalImpl;
import at.ac.tuwien.big.autoedit.search.local.NeighborhoodProvider;
import at.ac.tuwien.big.autoedit.search.local.SimpleStream;
import at.ac.tuwien.big.autoedit.search.local.impl.LocalSearchInterfaceImpl;
import at.ac.tuwien.big.autoedit.search.local.impl.LocalSearchPartialSolution;
import at.ac.tuwien.big.autoedit.search.local.impl.ViolatedConstraintsEvaluator;
import at.ac.tuwien.big.autoedit.transfer.EcoreMapTransferFunction;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;
import at.ac.tuwien.big.autoedit.xtext.DynamicValidator;
import jmetal.util.comparators.ViolatedConstraintComparator;

/**Read in an Ecore File and Generate Fixes for it*/
public class FixerGenerator {
	
	public static void printFixes(File xmiFile, File ecoreFile) throws IOException {
		Resource ecoreRes = OclExtractor.getEcore(ecoreFile);
		Map<EClass, Map<String, String>> allOCLExpressionsStr = OclExtractor.getAllClassOCLExpressions(ecoreRes);
		Map<EClass, Map<String, OCLExpression>> allOCLExpressions = OclExtractor.getAllOCLExpressions(allOCLExpressionsStr);
		
		Resource xmiRes = OclExtractor.getXMI(xmiFile, ecoreRes);
		MyResource myRes = MyResource.get(xmiRes);
		
		RejectingFilterManager man = new RejectingFilterManager();
		man.initDefault();
		EcoreEnvironmentFactory fact = EcoreEnvironmentFactory.INSTANCE;
		OCL ocl = OCL.newInstance();
		
		DynamicValidator valid = new DynamicValidator();
		
		for (EObject eobj: (Iterable<EObject>)(()->xmiRes.getAllContents())) {
			if (eobj == null) {
				continue;
			}
			EClass cl = eobj.eClass();
			if (cl == null) {
				continue;
			}
			Map<String, OCLExpression> oclExpr = allOCLExpressions.getOrDefault(cl, Collections.emptyMap());
			
			
			
			for (Entry<String,OCLExpression> entr: oclExpr.entrySet()) {
				/*String name = entr.getKey();
				EvaluationEnvironment localEvalEnv = fact.createEvaluationEnvironment();

				localEvalEnv.add(Environment.SELF_VARIABLE_NAME, eobj);

				Map extents = localEvalEnv.createExtentMap(eobj);

				EvaluationVisitor ev = fact
					.createEvaluationVisitor(fact.createEnvironment(), localEvalEnv, extents);

				
				TracingEvaluationVisitor evalVisitor = new TracingEvaluationVisitor(ev);
				Object ret = entr.getValue().accept(evalVisitor);
				System.out.println("Evaluation of " + name+" was " + ret + " ("+oclExpr+")");
				if (!(ret instanceof Boolean) || !((Boolean)ret)) {
					
					EvalResult res = evalVisitor.getTopResult();
					
					System.out.println("") ;
					man.calculateEverything(myRes, res);
					System.out.println("Need fix - Quality " + res.getQuality(MakeTrueImpl.INSTANCE, man));
					System.out.println("Calculated Everything");
					List<ChangeType<?, ?>> allPossibleChanges = res.getAllFixingActions();
					Collections.shuffle(allPossibleChanges);
					for (ChangeType<?, ?> change: allPossibleChanges) {
						Undoer undoer = change.apply();
						Object newRet = entr.getValue().accept(ev);
						if (newRet instanceof Boolean && ((Boolean)newRet)) {
							System.out.println("I could really fix the problem with " + change);
							break;
						} else {
							System.out.println("I could not fix the problem with " + change);
							undoer.undo();
						}
					}
					System.out.println("Fix Attempts:");
					res.printAllFixAttempts();
					System.out.println("Direct Fixing Actions:");
					res.printAllDirectFixingActions();
					System.out.println("Fixing Actions:");
					res.printAllFixingActions();
				}*/
				/*LocalSearchInterfaceImpl impl = LocalSearchInterfaceImpl.create(MyResource.get(xmiRes),
						entr.getKey(),
						new OCLExpressionEvaluable(entr.getValue()), eobj, SimpleStream.getStream((os,ns,oq,curCosts)
								->{
									System.out.println("Found solution:\n "+os);
									return new ProposalImpl<>(ns);
								}), NeighborhoodProvider.DEFAULT_LOCALSEARCH, -1.0);
				impl.initSearch();
				for (int i = 0; i < 100; ++i) {
					impl.doSomeSearch();
				}*/
			}

		}
		GlobalSearch gs = new GlobalSearch(valid, new SimpleStream<Change<?>>() {

			@Override
			public Proposal<?, ?> add(Change<?> original, Change<?> processed, double localErrorRemaining,
					double curCosts) {
				System.out.println("Received solution!");
			}
		});
		valid.checkFile(xmiRes);
				NondominatedPopulation pop = exec.run();
		valid.abort();
		for (int k = 0; k < 3; ++k) {
			for (int i = 0; i < pop.size(); ++i) {
				Solution s = pop.get(i);
				System.out.println("Solution " +i+", Violated Constraints: "+s.getObjective(0)+", Costs: "+s.getObjective(1));
				for (int j = 0; j < s.getNumberOfVariables(); ++j) {
					Variable var = (MOEAChangeVariable)s.getVariable(j);
					System.out.println("Variable "+j+": "+((MOEAChangeVariable)var).getCurChange());
				}

				problem.evaluate(s);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		xmiRes.save(new HashMap<>());
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		File file = new File("model/router.xmi");
		File routeroutput = new File("model/routeroutput.xmi");
		Files.copy(file.toPath(), new FileOutputStream(routeroutput));
		File ecore = new File("model/router.ecore");
		printFixes(routeroutput,ecore);
	}

}
