package at.ac.tuwien.big.forms.form.validation.ocl;

import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import java.util.ArrayList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Form;

import at.ac.tuwien.big.oclgen.OCL2QuickfixSupport;
import at.ac.tuwien.big.oclgen.OCLBasedValidationQuickfix;

public class FormValidator extends org.eclipse.xtext.validation.AbstractDeclarativeValidator {
	
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
	
	public static final String ATTRIBUTEOFCONDITIONISVALID_ISSUE_KEY = "AttributeOfConditionIsValidError";
	private static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> evaluatorAttributeOfConditionIsValid = at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE;
	
	@Check
	def void checkAttributeOfConditionIsValid(Form o) {
		if (!evaluatorAttributeOfConditionIsValid.isValid(o)) {
			val EStructuralFeature feature = evaluatorAttributeOfConditionIsValid.findErrorFeature(o);
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
							if (evaluatorAttributeOfConditionIsValid.isValid(o)) {
								userData.add(OCL2QuickfixSupport.toString(quickfix));
							}
						}
					} finally {
						o.eSet(feature, original);
						o.eSetDeliver(deliver);
					}
				}
			}
			
			error("The OCL-Expression named 'AttributeOfConditionIsValid' evaluated to false!", feature, ATTRIBUTEOFCONDITIONISVALID_ISSUE_KEY, userData.toArray(newArrayOfSize(userData.size())));
		}
	}
	
	public static final String EXACTLYONEWELCOMEPAGEEXISTS_ISSUE_KEY = "ExactlyOneWelcomePageExistsError";
	private static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> evaluatorExactlyOneWelcomePageExists = at.ac.tuwien.big.forms.form.validation.ocl.ExactlyOneWelcomePageExistsBooleanExpressionEvaluator.INSTANCE;
	
	@Check
	def void checkExactlyOneWelcomePageExists(Form o) {
		if (!evaluatorExactlyOneWelcomePageExists.isValid(o)) {
			val EStructuralFeature feature = evaluatorExactlyOneWelcomePageExists.findErrorFeature(o);
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
							if (evaluatorExactlyOneWelcomePageExists.isValid(o)) {
								userData.add(OCL2QuickfixSupport.toString(quickfix));
							}
						}
					} finally {
						o.eSet(feature, original);
						o.eSetDeliver(deliver);
					}
				}
			}
			
			error("The OCL-Expression named 'ExactlyOneWelcomePageExists' evaluated to false!", feature, EXACTLYONEWELCOMEPAGEEXISTS_ISSUE_KEY, userData.toArray(newArrayOfSize(userData.size())));
		}
	}
	
	public static final String FIRSTLETTERINFORMTITLEISUPPERCASE_ISSUE_KEY = "FirstLetterInFormTitleIsUpperCaseError";
	private static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> evaluatorFirstLetterInFormTitleIsUpperCase = at.ac.tuwien.big.forms.form.validation.ocl.FirstLetterInFormTitleIsUpperCaseBooleanExpressionEvaluator.INSTANCE;
	
	@Check
	def void checkFirstLetterInFormTitleIsUpperCase(Form o) {
		if (!evaluatorFirstLetterInFormTitleIsUpperCase.isValid(o)) {
			val EStructuralFeature feature = evaluatorFirstLetterInFormTitleIsUpperCase.findErrorFeature(o);
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
							if (evaluatorFirstLetterInFormTitleIsUpperCase.isValid(o)) {
								userData.add(OCL2QuickfixSupport.toString(quickfix));
							}
						}
					} finally {
						o.eSet(feature, original);
						o.eSetDeliver(deliver);
					}
				}
			}
			
			error("The OCL-Expression named 'FirstLetterInFormTitleIsUpperCase' evaluated to false!", feature, FIRSTLETTERINFORMTITLEISUPPERCASE_ISSUE_KEY, userData.toArray(newArrayOfSize(userData.size())));
		}
	}
	
	public static final String PAGEELEMENTSREFERTOFORMENTITYFEATURES_ISSUE_KEY = "PageElementsReferToFormEntityFeaturesError";
	private static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> evaluatorPageElementsReferToFormEntityFeatures = at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE;
	
	@Check
	def void checkPageElementsReferToFormEntityFeatures(Form o) {
		if (!evaluatorPageElementsReferToFormEntityFeatures.isValid(o)) {
			val EStructuralFeature feature = evaluatorPageElementsReferToFormEntityFeatures.findErrorFeature(o);
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
							if (evaluatorPageElementsReferToFormEntityFeatures.isValid(o)) {
								userData.add(OCL2QuickfixSupport.toString(quickfix));
							}
						}
					} finally {
						o.eSet(feature, original);
						o.eSetDeliver(deliver);
					}
				}
			}
			
			error("The OCL-Expression named 'PageElementsReferToFormEntityFeatures' evaluated to false!", feature, PAGEELEMENTSREFERTOFORMENTITYFEATURES_ISSUE_KEY, userData.toArray(newArrayOfSize(userData.size())));
		}
	}
}