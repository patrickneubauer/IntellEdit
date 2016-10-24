package at.ac.tuwien.big.forms.form.validation.ocl;

import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import java.util.ArrayList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Enumeration;

import at.ac.tuwien.big.oclgen.OCL2QuickfixSupport;
import at.ac.tuwien.big.oclgen.OCLBasedValidationQuickfix;

public class EnumerationValidator extends org.eclipse.xtext.validation.AbstractDeclarativeValidator {
	
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
	
	public static final String ENUMERATIONNAMEISUNIQUE_ISSUE_KEY = "EnumerationNameIsUniqueError";
	private static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Enumeration> evaluatorEnumerationNameIsUnique = at.ac.tuwien.big.forms.form.validation.ocl.EnumerationNameIsUniqueBooleanExpressionEvaluator.INSTANCE;
	
	@Check
	def void checkEnumerationNameIsUnique(Enumeration o) {
		if (!evaluatorEnumerationNameIsUnique.isValid(o)) {
			val EStructuralFeature feature = evaluatorEnumerationNameIsUnique.findErrorFeature(o);
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
							if (evaluatorEnumerationNameIsUnique.isValid(o)) {
								userData.add(OCL2QuickfixSupport.toString(quickfix));
							}
						}
					} finally {
						o.eSet(feature, original);
						o.eSetDeliver(deliver);
					}
				}
			}
			
			error("The OCL-Expression named 'EnumerationNameIsUnique' evaluated to false!", feature, ENUMERATIONNAMEISUNIQUE_ISSUE_KEY, userData.toArray(newArrayOfSize(userData.size())));
		}
	}
	
	public static final String LITERALINENUMERATIONISUNIQUE_ISSUE_KEY = "LiteralInEnumerationIsUniqueError";
	private static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Enumeration> evaluatorLiteralInEnumerationIsUnique = at.ac.tuwien.big.forms.form.validation.ocl.LiteralInEnumerationIsUniqueBooleanExpressionEvaluator.INSTANCE;
	
	@Check
	def void checkLiteralInEnumerationIsUnique(Enumeration o) {
		if (!evaluatorLiteralInEnumerationIsUnique.isValid(o)) {
			val EStructuralFeature feature = evaluatorLiteralInEnumerationIsUnique.findErrorFeature(o);
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
							if (evaluatorLiteralInEnumerationIsUnique.isValid(o)) {
								userData.add(OCL2QuickfixSupport.toString(quickfix));
							}
						}
					} finally {
						o.eSet(feature, original);
						o.eSetDeliver(deliver);
					}
				}
			}
			
			error("The OCL-Expression named 'LiteralInEnumerationIsUnique' evaluated to false!", feature, LITERALINENUMERATIONISUNIQUE_ISSUE_KEY, userData.toArray(newArrayOfSize(userData.size())));
		}
	}
}