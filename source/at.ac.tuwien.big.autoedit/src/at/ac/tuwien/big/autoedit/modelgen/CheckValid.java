package at.ac.tuwien.big.autoedit.modelgen;

import java.io.File;
import java.util.Arrays;

import org.eclipse.emf.ecore.resource.Resource;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.search.local.impl.Evaluation;
import at.ac.tuwien.big.autoedit.search.local.impl.ViolatedConstraintsEvaluator;
import at.ac.tuwien.big.autoedit.test.OclExtractor;

public class CheckValid {
	
	public static void main(String[] args) {
		File ecoreFile = new File("model/serviceexample.ecore");
		Resource ecoreRes = OclExtractor.getEcore(ecoreFile);
		Resource res = OclExtractor.getXMI(new File("model/generated/generated_0.75_19.xmi"), ecoreRes);
		ViolatedConstraintsEvaluator eval = new ViolatedConstraintsEvaluator();
		double[] rv = eval.evaluate(Change.empty(res), new Evaluation());
		System.out.println(Arrays.toString(rv));
	}

}
