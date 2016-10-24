package at.ac.tuwien.big.forms.form.validation.ocl;

import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import java.util.ArrayList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Entity;

import at.ac.tuwien.big.oclgen.OCL2QuickfixSupport;
import at.ac.tuwien.big.oclgen.OCLBasedValidationQuickfix;

public class EntityValidator extends org.eclipse.xtext.validation.AbstractDeclarativeValidator {
	
	override void register(EValidatorRegistrar registrar) {
		// Do nothing to prevent registration and duplicate validation messages
	}
	
	override java.util.List<EPackage> getEPackages() {
	    val result = new ArrayList<EPackage>();
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.big.tuwien.ac.at/forms"));
		return result;
	}
	
	def static EPackage getPackage() {
		return EPackage.Registry.INSTANCE.getEPackage("http://www.big.tuwien.ac.at/forms");
	}
	
	public static final String ENTITYNAMEISUNIQUE_ISSUE_KEY = "EntityNameIsUniqueError";
	private static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Entity> evaluatorEntityNameIsUnique = at.ac.tuwien.big.forms.form.validation.ocl.EntityNameIsUniqueBooleanExpressionEvaluator.INSTANCE;
	
	@Check
	def void checkEntityNameIsUnique(Entity o) {
		if (!evaluatorEntityNameIsUnique.isValid(o)) {
			val EStructuralFeature feature = evaluatorEntityNameIsUnique.findErrorFeature(o);
			val userData = new ArrayList<String>();
			
			// We do this at runtime because later we might want to find out the error feature more dynamically
			if (feature != null && feature instanceof EAttribute) {
				val EAttribute attribute = feature as EAttribute;
				val Class<?> clazz = attribute.getEAttributeType().getInstanceClass();
				val list = new ArrayList<OCLBasedValidationQuickfix>();
				
				// Right now we only support boolean quickfixes
				if (clazz == boolean || clazz == Boolean) {
					val Boolean oldValue = o.eGet(feature, false) as Boolean;
					
					if (oldValue == null) {
						list.add(OCLBasedValidationQuickfix.fromValue(true, attribute.name));
						list.add(OCLBasedValidationQuickfix.fromValue(false, attribute.name));
					} else {
						if (Boolean.FALSE.equals(oldValue)) {
							list.add(OCLBasedValidationQuickfix.fromValue(true, attribute.name));
						} else if (Boolean.TRUE.equals(oldValue)) {
							list.add(OCLBasedValidationQuickfix.fromValue(false, attribute.name));
						}
						
						if (!feature.isRequired()) {
							list.add(OCLBasedValidationQuickfix.fromValue(null, attribute.name));
						}
					}
				}
				
				// Maybe we should remove validation since it could lead to filtering out quickfixes that could make sense locally
				if (!list.empty) {
					val original = o.eGet(feature, false);
					val deliver = o.eDeliver();
					o.eSetDeliver(false);
					
					try {
						for (quickfix : list) {
							quickfix.apply(o, attribute);
							if (evaluatorEntityNameIsUnique.isValid(o)) {
								userData.add(OCL2QuickfixSupport.toString(quickfix));
							}
						}
					} finally {
						o.eSet(feature, original);
						o.eSetDeliver(deliver);
					}
				}
			}
			
			error("The OCL-Expression named 'EntityNameIsUnique' evaluated to false!", feature, ENTITYNAMEISUNIQUE_ISSUE_KEY, userData.toArray(newArrayOfSize(userData.size())));
		}
	}
	
	public static final String FEATUREINENTITYISUNIQUE_ISSUE_KEY = "FeatureInEntityIsUniqueError";
	private static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Entity> evaluatorFeatureInEntityIsUnique = at.ac.tuwien.big.forms.form.validation.ocl.FeatureInEntityIsUniqueBooleanExpressionEvaluator.INSTANCE;
	
	@Check
	def void checkFeatureInEntityIsUnique(Entity o) {
		if (!evaluatorFeatureInEntityIsUnique.isValid(o)) {
			val EStructuralFeature feature = evaluatorFeatureInEntityIsUnique.findErrorFeature(o);
			val userData = new ArrayList<String>();
			
			// We do this at runtime because later we might want to find out the error feature more dynamically
			if (feature != null && feature instanceof EAttribute) {
				val EAttribute attribute = feature as EAttribute;
				val Class<?> clazz = attribute.getEAttributeType().getInstanceClass();
				val list = new ArrayList<OCLBasedValidationQuickfix>();
				
				// Right now we only support boolean quickfixes
				if (clazz == boolean || clazz == Boolean) {
					val Boolean oldValue = o.eGet(feature, false) as Boolean;
					
					if (oldValue == null) {
						list.add(OCLBasedValidationQuickfix.fromValue(true, attribute.name));
						list.add(OCLBasedValidationQuickfix.fromValue(false, attribute.name));
					} else {
						if (Boolean.FALSE.equals(oldValue)) {
							list.add(OCLBasedValidationQuickfix.fromValue(true, attribute.name));
						} else if (Boolean.TRUE.equals(oldValue)) {
							list.add(OCLBasedValidationQuickfix.fromValue(false, attribute.name));
						}
						
						if (!feature.isRequired()) {
							list.add(OCLBasedValidationQuickfix.fromValue(null, attribute.name));
						}
					}
				}
				
				// Maybe we should remove validation since it could lead to filtering out quickfixes that could make sense locally
				if (!list.empty) {
					val original = o.eGet(feature, false);
					val deliver = o.eDeliver();
					o.eSetDeliver(false);
					
					try {
						for (quickfix : list) {
							quickfix.apply(o, attribute);
							if (evaluatorFeatureInEntityIsUnique.isValid(o)) {
								userData.add(OCL2QuickfixSupport.toString(quickfix));
							}
						}
					} finally {
						o.eSet(feature, original);
						o.eSetDeliver(deliver);
					}
				}
			}
			
			error("The OCL-Expression named 'FeatureInEntityIsUnique' evaluated to false!", feature, FEATUREINENTITYISUNIQUE_ISSUE_KEY, userData.toArray(newArrayOfSize(userData.size())));
		}
	}
	
	public static final String NOSELFINHERITANCE_ISSUE_KEY = "NoSelfInheritanceError";
	private static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Entity> evaluatorNoSelfInheritance = at.ac.tuwien.big.forms.form.validation.ocl.NoSelfInheritanceBooleanExpressionEvaluator.INSTANCE;
	
	@Check
	def void checkNoSelfInheritance(Entity o) {
		if (!evaluatorNoSelfInheritance.isValid(o)) {
			val EStructuralFeature feature = evaluatorNoSelfInheritance.findErrorFeature(o);
			val userData = new ArrayList<String>();
			
			// We do this at runtime because later we might want to find out the error feature more dynamically
			if (feature != null && feature instanceof EAttribute) {
				val EAttribute attribute = feature as EAttribute;
				val Class<?> clazz = attribute.getEAttributeType().getInstanceClass();
				val list = new ArrayList<OCLBasedValidationQuickfix>();
				
				// Right now we only support boolean quickfixes
				if (clazz == boolean || clazz == Boolean) {
					val Boolean oldValue = o.eGet(feature, false) as Boolean;
					
					if (oldValue == null) {
						list.add(OCLBasedValidationQuickfix.fromValue(true, attribute.name));
						list.add(OCLBasedValidationQuickfix.fromValue(false, attribute.name));
					} else {
						if (Boolean.FALSE.equals(oldValue)) {
							list.add(OCLBasedValidationQuickfix.fromValue(true, attribute.name));
						} else if (Boolean.TRUE.equals(oldValue)) {
							list.add(OCLBasedValidationQuickfix.fromValue(false, attribute.name));
						}
						
						if (!feature.isRequired()) {
							list.add(OCLBasedValidationQuickfix.fromValue(null, attribute.name));
						}
					}
				}
				
				// Maybe we should remove validation since it could lead to filtering out quickfixes that could make sense locally
				if (!list.empty) {
					val original = o.eGet(feature, false);
					val deliver = o.eDeliver();
					o.eSetDeliver(false);
					
					try {
						for (quickfix : list) {
							quickfix.apply(o, attribute);
							if (evaluatorNoSelfInheritance.isValid(o)) {
								userData.add(OCL2QuickfixSupport.toString(quickfix));
							}
						}
					} finally {
						o.eSet(feature, original);
						o.eSetDeliver(deliver);
					}
				}
			}
			
			error("The OCL-Expression named 'NoSelfInheritance' evaluated to false!", feature, NOSELFINHERITANCE_ISSUE_KEY, userData.toArray(newArrayOfSize(userData.size())));
		}
	}
}