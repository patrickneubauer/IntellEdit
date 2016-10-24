package at.ac.tuwien.big.forms.form.scoping;

import java.util.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;

public class FormScopeProviderBase extends org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider {
	
	private static final Map<String, Map<String, Map<String, Set<at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<?>>>>> map = new HashMap<>();
	
	static {
		Map<String, Map<String, Set<at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<?>>>> referencesMap;
		Map<String, Set<at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<?>>> eClassesMap;
		Set<at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<?>> evaluators;
		
		referencesMap = map.computeIfAbsent("Attribute", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("enumeration", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Attribute", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("SelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("AttributePageElement", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("attribute", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("DateSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.DateIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("SelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("TimeSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TimeIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("AttributePageElement", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("AttributeValueCondition", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("attribute", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Column", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("attribute", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("DateSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.DateIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("SelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("TimeSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TimeIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Column", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("CompositeCondition", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("composedConditions", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("CompositeCondition", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.ComposedConditionTypesAreEqualBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("DateSelectionField", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("attribute", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("DateSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.DateIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("SelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("TimeSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TimeIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("DateSelectionField", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Entity", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("features", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Entity", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.FeatureInEntityIsUniqueBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Relationship", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.OppositeRelationshipTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Entity", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("superType", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Entity", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.NoSelfInheritanceBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Enumeration", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("literals", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Enumeration", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.LiteralInEnumerationIsUniqueBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Form", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("entity", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("RelationshipPageElement", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Form", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("pages", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("List", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("List", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("editingForm", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("RelationshipPageElement", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormIsNotWelcomeFormBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("List", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("relationship", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("RelationshipPageElement", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Page", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Page", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("pageElements", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("PageElement", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Relationship", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("opposite", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Relationship", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.OppositeRelationshipTypeIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.OppositeRelationshipsReferenceEachOtherBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Relationship", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("target", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Relationship", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.OppositeRelationshipTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("RelationshipPageElement", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("RelationshipPageElement", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("RelationshipPageElement", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("editingForm", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("RelationshipPageElement", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormIsNotWelcomeFormBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("RelationshipPageElement", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("relationship", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("RelationshipPageElement", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("SelectionField", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("attribute", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("DateSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.DateIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("SelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("TimeSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TimeIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("SelectionField", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Table", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("columns", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Table", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Table", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("editingForm", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("RelationshipPageElement", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormIsNotWelcomeFormBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("Table", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("relationship", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("RelationshipPageElement", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("TextArea", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("attribute", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("DateSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.DateIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("SelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("TimeSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TimeIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("TextArea", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("TextField", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("attribute", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("DateSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.DateIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("SelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("TimeSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TimeIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("TextField", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("TimeSelectionField", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("attribute", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("DateSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.DateIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("SelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("Table", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator.INSTANCE);
			evaluators = eClassesMap.computeIfAbsent("TimeSelectionField", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.TimeIsValidBooleanExpressionEvaluator.INSTANCE);
		
		referencesMap = map.computeIfAbsent("TimeSelectionField", key -> new HashMap<>());
		eClassesMap = referencesMap.computeIfAbsent("condition", key -> new HashMap<>());
			evaluators = eClassesMap.computeIfAbsent("Form", key -> new HashSet<>());
				evaluators.add(at.ac.tuwien.big.forms.form.validation.ocl.AttributeOfConditionIsValidBooleanExpressionEvaluator.INSTANCE);
		
	}
	
	@Override
	public IScope getScope(EObject context, EReference reference) {
		IScope scope = super.getScope(context, reference);
		
		// Workaround by Bill
		StackTraceElement[] els = new Exception().getStackTrace();
		for (int i = 0; i < els.length; ++i) {
			if ("resolveLazyCrossReferences".equals(els[i].getMethodName())) {
				return scope;
			}
		}
		
		Map<String, Map<String, Set<at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<?>>>> referencesMap;
		Map<String, Set<at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<?>>> eClassesMap;
		Set<at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<?>> evaluators;
		
		referencesMap = map.get(context.eClass().getName());
		
		if (referencesMap != null) {
			eClassesMap = referencesMap.get(reference.getName());
			
			if (eClassesMap != null) {
				java.util.List<java.util.Map.Entry<EObject, at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator[]>> parents = new java.util.ArrayList<>();
				EObject currentObject = context;
				while (currentObject != null) {
					evaluators = eClassesMap.get(currentObject.eClass().getName());
					
					if (evaluators != null && !evaluators.isEmpty()) {
						parents.add(new java.util.AbstractMap.SimpleEntry<>(currentObject, evaluators.toArray(new at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator[evaluators.size()])));
					}
					
					currentObject = currentObject.eContainer();
				}
				
				if (!parents.isEmpty()) {
					java.util.Iterator<IEObjectDescription> iter = scope.getAllElements().iterator();
					java.util.List<EObject> results = new ArrayList<EObject>();
					
					final Object original = context.eGet(reference, false);
					final boolean deliver = context.eDeliver();
					context.eSetDeliver(false);
					
					try {
						// Mutate and check validations
						while (iter.hasNext()) {
							final IEObjectDescription oDesc = iter.next();
							
							context.eSet(reference, original);
							
							final EObject o = org.eclipse.emf.ecore.util.EcoreUtil.resolve(oDesc.getEObjectOrProxy(), context);
							context.eSet(reference, o);
							
							PARENTS: for (java.util.Map.Entry<EObject, at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator[]> entry : parents) {
								final EObject parent = entry.getKey();
								for (at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator evaluator : entry.getValue()) {
									if (!evaluator.isValid(parent)) {
										break PARENTS;
									}	
								}
								results.add(o);
							}
						}
					} finally {
						context.eSet(reference, original);
						context.eSetDeliver(deliver);
					}
					
					// Avoid filtering out all elements and instead deliver the original scope
					if (results.isEmpty()) {
						return scope;
					}
					
					return Scopes.scopeFor(results);
				}
			}
		}

		return scope;
	}
}
