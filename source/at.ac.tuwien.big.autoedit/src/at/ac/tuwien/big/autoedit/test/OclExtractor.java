package at.ac.tuwien.big.autoedit.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.OCL.Helper;

import at.ac.tuwien.big.oclgen.OCLBasedQuickfixDefinition;

public class OclExtractor {
	
	public static Map<String,OCLExpression> convertToExpression(Helper oclHelper, EClass context, Map<String,String> map) {
		Map<String, OCLExpression> expr = new HashMap<String, OCLExpression>();
		for (Entry<String,String> subEntr: map.entrySet()) {
			oclHelper.setContext(context);
			try {
				OCLExpression oclexpr = oclHelper.createQuery(subEntr.getValue());					
				expr.put(subEntr.getKey(), oclexpr);
			} catch (ParserException ex) {
				throw new RuntimeException(ex);
			}
			
		}
		return expr;
	}

	public static Map<EClass, Map<String, OCLExpression>> getAllOCLExpressions(Map<EClass, Map<String,String>> map) {
		Map<EClass, Map<String,OCLExpression>> ret = new HashMap<EClass, Map<String,OCLExpression>>();
		OCL ocl = OCL.newInstance();
		Helper oclHelper = ocl.createOCLHelper();
		
		for (Entry<EClass, Map<String,String>> entr: map.entrySet()) {
			Map<String,OCLExpression> expr = convertToExpression(oclHelper, entr.getKey(), entr.getValue());
			ret.put(entr.getKey(), expr);
		}
		return ret;
	}
	
	public static Map<EClass, Map<String, String>> getAllClassOCLExpressions(Resource ecoreFile) {
		try {
			// TODO: we need to somehow pass that as parameter in or extract from registry?
			EPackage ePackage = (EPackage) Class.forName("at.ac.tuwien.big.forms.FormsPackage").getDeclaredField("eINSTANCE").get(null);
			TreeIterator<EObject> iter = ecoreFile.getAllContents();
			return getAllClassOCLExpressions(iter, ePackage);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static Resource getEcore(File ecoreFile) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource.Factory.Registry reg = resourceSet.getResourceFactoryRegistry();		
		reg.getExtensionToFactoryMap().put(
				"xmi", 
				new XMIResourceFactoryImpl());
		reg.getExtensionToFactoryMap().put(
				"ecore", 
				new EcoreResourceFactoryImpl());
		
		//Register ecore file
		final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(resourceSet.getPackageRegistry());
		resourceSet.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);

		Resource ecoreResource = resourceSet.getResource(URI.createFileURI(ecoreFile.getAbsolutePath()), true);
		return ecoreResource;
	}
	
	public static Resource getXMI(File xmiFile, Resource ecoreRes) {
		Resource xmiResource = ecoreRes.getResourceSet().getResource(URI.createFileURI(xmiFile.getAbsolutePath()), true);
		return xmiResource;
	}
	
	public static Map<String, String> getConstraintMap(EClass eclass) {
		Map<String, String> constraintMap = new TreeMap<String, String>();
		int constraintNr = 1;
		for (EAnnotation eann : eclass.getEAnnotations()) {
			if (eann.getSource() == null) {
				continue;
			}
			if (!eann.getSource().startsWith("http://www.eclipse.org/emf/2002/Ecore/OCL")) {
				// Not an OCL expression
				continue;
			}
			String constraintName = null;
			String oclExpr = null;
			for (Entry<String, String> entry : eann.getDetails().entrySet()) {
				constraintName = entry.getKey();
				oclExpr = entry.getValue();
				// System.out.println("Constraint "+ constraintName+" for "
				// + eclass.getName()+": "+oclExpr);
				if (constraintName == null || "".equals(constraintName)) {
					constraintName = "Constraint" + constraintNr;
				}
				constraintMap.put(constraintName, oclExpr);
				++constraintNr;
			}
		}
		return constraintMap;
	}

	private static Map<EClass, Map<String, String>> getAllClassOCLExpressions(TreeIterator<EObject> iter, EPackage ePackage) {
		Map<EClass, Map<String, String>> ret = new HashMap<>();
		while (iter.hasNext()) {
			EObject cur = iter.next();

			// Fix the XMI generated EClasses
			if (cur instanceof EClassifier) {
				EClassifier cls = (EClassifier) cur;
				EClassifier base = ePackage.getEClassifier(cls.getName());
				if (base != null && base.getInstanceClass() != null) {
					cls.setInstanceClass(base.getInstanceClass());
				}
			}
			if (!(cur instanceof EClass)) {
				continue;
			}

			EClass eclass = (EClass) cur;
			Map<String, String> constraintMap = getConstraintMap(eclass);
			ret.put(eclass, constraintMap);
			
		}
		return ret;
	}

}
