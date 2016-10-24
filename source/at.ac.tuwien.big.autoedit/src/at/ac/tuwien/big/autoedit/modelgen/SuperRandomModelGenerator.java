package at.ac.tuwien.big.autoedit.modelgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import at.ac.tuwien.big.autoedit.change.ParameterType;
import at.ac.tuwien.big.autoedit.ecore.util.MyEcoreUtil;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.scope.ResortScope;
import at.ac.tuwien.big.autoedit.scope.ValueScope;

public class SuperRandomModelGenerator extends EcoreModelGenerator {
	
	private int baseObjects;
	private int unscopedbaseFeatures;
	private int scopedBaseFeatures;
	private List<EClass> validClasses = new ArrayList<EClass>();
	private Set<EClass> containerClasses;
	private EClass container = null;
	private Map<EClass,List<EReference>> containmentReferences = new HashMap<EClass, List<EReference>>();
	
	public static Random random = new Random();
	

	public SuperRandomModelGenerator(Resource ecoreResource, int baseObjects, int unscopedbaseFeatures, int scopedBaseFeatures) {
		super(ecoreResource);
		this.unscopedbaseFeatures = unscopedbaseFeatures;
		this.scopedBaseFeatures = scopedBaseFeatures;
		this.baseObjects = baseObjects;
		//Randomly choose a valid container-class, i.e. a class not contained in any other class
		Set<EClass> nonrootcontainers = new HashSet<EClass>();
		containerClasses = new HashSet<EClass>();
		Map<EClass,Set<EClass>> subTypes = new HashMap<>();
		for (EObject eobj: (Iterable<EObject>)()->ecoreResource.getAllContents()) {
			if (eobj instanceof EClass) {
				EClass cl = (EClass)eobj;
				List<EReference> contRef = new ArrayList<EReference>();
				containmentReferences.put(cl, contRef);
				for (EReference ref: cl.getEAllReferences()) {
					if (ref.isContainment()) {
						containerClasses.add(cl);
						nonrootcontainers.add(ref.getEReferenceType());
						contRef.add(ref);
					}
				}
				
				for (EClass sup: cl.getEAllSuperTypes()) {
					Set<EClass> curSub = subTypes.get(sup);
					if (curSub == null) {
						subTypes.put(sup, curSub = new HashSet<EClass>());
					}
					curSub.add(cl);
				}
			}
		}
		Set<EClass> subnonrootcontainers = new HashSet<EClass>();
		for (EClass cl: nonrootcontainers) {
			subnonrootcontainers.addAll(subTypes.getOrDefault(cl, Collections.emptySet()));
		}
		nonrootcontainers.addAll(subnonrootcontainers);
		Set<EClass> bestCandidates = new HashSet<EClass>(containerClasses);
		bestCandidates.removeAll(nonrootcontainers);
		List<EClass> bestList = new ArrayList<EClass>(bestCandidates.isEmpty()?containerClasses:bestCandidates);
		container = bestList.get(random.nextInt(bestList.size()));
		Set<EClass> processed = new HashSet<>();
		Stack<EClass> toprocess = new Stack<EClass>();
		toprocess.add(container);
		while (!toprocess.isEmpty()) {
			EClass cl = toprocess.pop();
			Set<EReference> allRefs = new HashSet<EReference>(cl.getEReferences());
			for (EClass subType: subTypes.getOrDefault(cl,Collections.emptySet())) {
				allRefs.addAll(subType.getEReferences());
			}
			for (EReference ref: allRefs) {
				if (ref.isContainment()) {
					if (processed.add(ref.getEReferenceType())) {
						toprocess.add(ref.getEReferenceType());
					}
				}
			}
		}
		validClasses = new ArrayList<EClass>(processed);
	}
	
	public static void checkRequire(EObject eobj, List<EObject> requireContainers) {
		EClass cl = eobj.eClass();
		if (!requireContainers.contains(eobj)) {
			for (EReference ref: cl.getEReferences()) {
				if (ref.isContainment() && ref.getLowerBound() > 0 && MyEcoreUtil.getAsCollectionSize(eobj, ref) < ref.getLowerBound()) {
					requireContainers.add(eobj);
					return;
				}
			}
		} else {
			for (EReference ref: cl.getEReferences()) {
				if (ref.isContainment() && ref.getLowerBound() > 0 && MyEcoreUtil.getAsCollectionSize(eobj, ref) < ref.getLowerBound()) {
					return;
				}
			}
			requireContainers.remove(eobj);
		}
	}
	
	public static void checkFeatureRequire(EObject eobj, List<EObject> requireContainers, List<EStructuralFeature> considerFeatures) {
		EClass cl = eobj.eClass();
		if (!requireContainers.contains(eobj)) {
			for (EStructuralFeature ref: considerFeatures) {
				if (ref.getLowerBound() > 0 && MyEcoreUtil.getAsCollectionSize(eobj, ref) < ref.getLowerBound()) {
					requireContainers.add(eobj);
					return;
				}
			}
		} else {
			for (EStructuralFeature ref: considerFeatures) {
				if (ref.getLowerBound() > 0 && MyEcoreUtil.getAsCollectionSize(eobj, ref) < ref.getLowerBound()) {
					return;
				}
			}
			requireContainers.remove(eobj);
		}
	}

	@Override
	public EcoreModelgeneratorState generateState(MyResource forRes) {
		return new EcoreModelgeneratorState(forRes) {
			@Override
			public void augment() {
				//First randomly generate objects
				Resource res = forRes.getResource();
				EObject containerObj = forRes.createInstance(container);
				res.getContents().add(containerObj);
				
				List<EObject> allGen = new ArrayList<EObject>();
				allGen.add(containerObj);
				{
					
					List<EObject> containers = new ArrayList<EObject>();
					List<EObject> requireContainers = new ArrayList<EObject>();
					Set<EObject> forbiddenContainers = new HashSet<EObject>();
					
					containers.add(containerObj);
					//Add objects to containment references
					mainLoop: for (int i = 0, totTry = 0; !containers.isEmpty() && i < baseObjects &&  totTry < baseObjects*5; ++i, ++totTry) {
						//Add to a random object having a containment reference
						if (containers.isEmpty()) {
							//Cannot add any more ...
							break;
						}
						boolean requireCont = !requireContainers.isEmpty();
						EObject randomCont = requireCont?
								requireContainers.get(random.nextInt(requireContainers.size())):
									containers.get(random.nextInt(containers.size()));
						//Get a random feature
						List<EReference> contRefs = containmentReferences.get(randomCont.eClass());
						List<EReference> copy = new ArrayList<EReference>(contRefs);
						Collections.shuffle(copy);
						for (EReference tryRef: copy) {
							if ((requireCont && tryRef.getLowerBound() == 0) || (tryRef.getUpperBound() >= 0 && MyEcoreUtil.getAsCollectionSize(randomCont, tryRef) >= tryRef.getUpperBound())) {
								continue;
							}
							List<EClass> randomInstanceClass = forRes.getInstancibleTypes(tryRef.getEReferenceType());
							EObject toAdd = forRes.createInstance(randomInstanceClass.get(random.nextInt(randomInstanceClass.size())));
							allGen.add(toAdd);
							MyEcoreUtil.addValue(randomCont, tryRef, toAdd);
							
							checkRequire(toAdd, requireContainers);
							if (!forbiddenContainers.contains(randomCont)) {
								checkRequire(randomCont, requireContainers);
							}
							if (containerClasses.contains(toAdd.eClass())) {
								containers.add(toAdd);
							}
							continue mainLoop;
						}
						if (!requireCont) {
							containers.remove(randomCont);
						} else {
							requireContainers.remove(randomCont);
							forbiddenContainers.add(randomCont);
						}
						--i;					
					}
				}
				{
				
					//Add references and so on
					List<EObject> requireFeature = new ArrayList<EObject>();
					List<EObject> featureHavers = new ArrayList<EObject>(allGen);
					Set<EObject> forbiddenFeature = new HashSet<EObject>();
					for (EObject eobj: featureHavers) {
						for (EReference ref: eobj.eClass().getEAllReferences()) {
							if (ref.isContainment() || ref.getLowerBound() == 0) {
								continue;
							}
							if (ref.getLowerBound() > MyEcoreUtil.getAsCollectionSize(eobj, ref)) {
								requireFeature.add(eobj);
							}
						}
					}
					int baseFeatures = unscopedbaseFeatures+scopedBaseFeatures;
					int haveUnscoped = 0;
					int haveScoped = 0;
					mainLoop: for (int i = 0, totTry = 0; !featureHavers.isEmpty() && i < baseFeatures && totTry < baseFeatures*5; ++i, ++totTry) {
						boolean useScoping = unscopedbaseFeatures*haveScoped<=scopedBaseFeatures*haveUnscoped;
						boolean requireCont = !requireFeature.isEmpty();
						EObject randomCont = requireCont?
								requireFeature.get(random.nextInt(requireFeature.size())):
									featureHavers.get(random.nextInt(featureHavers.size()));
						//Get a random feature
						List<EStructuralFeature> contRefs = randomCont.eClass().getEAllStructuralFeatures();
						
						List<EStructuralFeature> copy = new ArrayList<>(contRefs);
						copy.removeIf((x)->((x instanceof EReference) && ((EReference)x).isContainment()));
						Collections.shuffle(copy);
						for (EStructuralFeature tryRef: copy) {
							if ((requireCont && tryRef.getLowerBound() == 0) || (tryRef.getUpperBound() >= 0 && MyEcoreUtil.getAsCollectionSize(randomCont, tryRef) >= tryRef.getUpperBound())) {
								continue;
							}
							ParameterType type = forRes.defaultGenerator(tryRef);
							ValueScope<?, ?> scope = type.getDefaultScope();
							if (useScoping) {
								scope =  ResortScope.defaultResortScope(scope, randomCont, tryRef, -2);
							}
							Iterator iter = useScoping?scope.iterator():scope.sample();
							if (iter.hasNext()) {
								MyEcoreUtil.addValue(randomCont, tryRef, iter.next());
								if (!forbiddenFeature.contains(randomCont)) {
									checkFeatureRequire(randomCont, requireFeature, contRefs);
								}
								if (useScoping) {
									++haveScoped;
								} else {
									++haveUnscoped;
								}
								continue mainLoop;
							}
							
						}
						if (!requireCont) {
							featureHavers.remove(randomCont);
						} else {
							requireFeature.remove(randomCont);
							forbiddenFeature.add(randomCont);
						}
						--i;				
					}
				}
			}
		};
	}

}
