package at.ac.tuwien.big.autoedit.modelgen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.Undoer;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.search.local.impl.Evaluation;
import at.ac.tuwien.big.autoedit.search.local.impl.ViolatedConstraintsEvaluator;
import at.ac.tuwien.big.autoedit.test.OclExtractor;
import at.ac.tuwien.big.autoedit.transfer.EcoreMapTransferFunction;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public class BasicChangeTest {
	
	public static void main(String[] args) {
		File ecoreFile = new File("model/serviceexample.ecore");
		Resource ecoreRes = OclExtractor.getEcore(ecoreFile);
		
		Random random = new Random();
		
		File noChange = new File("model/change_no.xmi");
		File midChange = new File("model/change_applied.xmi");
		File afterChange = new File("model/change_undone.xmi");
		
		for (int i = 0; i < 30; ++i) {
			SuperRandomModelGenerator gen = new SuperRandomModelGenerator(ecoreRes, 20, 100, 0);
			Resource generated = new XMLResourceImpl();
			gen.populate(generated);
			MyResource res = MyResource.get(generated);
			EcoreUtil.Copier copier = new EcoreUtil.Copier();
			MyResource cloned = res.clone(copier);
			EcoreTransferFunction etf = new EcoreMapTransferFunction(res.getResource(), cloned.getResource(), copier);
			if (!res.equals(cloned,etf)) {
				System.err.println("Clone error!!");
			}
			
			for (int j = 0; j < 1000; ++j) {
				Change<?> ch = res.randomChange(random).compileWithMissing();
				
				if (ch != null) {
					System.out.print("Trying " + ch+" ==> ");
					ch.transfered(etf).execute().undo();
					if (!res.equals(cloned,etf)) {
						System.out.println("ERROR");
						Map<Object,Object> options = new HashMap<Object, Object>();
						options.put(XMIResource.SCHEMA_LOCATION, true);
						try {
							res.getResource().save(new FileOutputStream(noChange),options);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Undoer undoer = ch.execute();
						try {
							res.getResource().save(new FileOutputStream(midChange),options);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						undoer.undo();
						try {
							res.getResource().save(new FileOutputStream(afterChange),options);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						System.out.println("OK");
					}
				}
				
			}
		}
	}

}
