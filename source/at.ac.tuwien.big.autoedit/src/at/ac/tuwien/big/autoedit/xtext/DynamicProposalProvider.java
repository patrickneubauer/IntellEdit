package at.ac.tuwien.big.autoedit.xtext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.common.ui.contentassist.TerminalsProposalProvider;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.Wrapper;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.ParameterType;
import at.ac.tuwien.big.autoedit.change.basic.BasicAddConstantChange;
import at.ac.tuwien.big.autoedit.change.basic.BasicSetConstantChange;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.search.local.impl.Evaluation;
import at.ac.tuwien.big.autoedit.search.local.impl.ResourceEvaluator;
import at.ac.tuwien.big.autoedit.search.local.impl.ViolatedConstraintsEvaluator;
import at.ac.tuwien.big.autoedit.transfer.EcoreMapTransferFunction;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public class DynamicProposalProvider extends TerminalsProposalProvider {
	
	public static final int MAX_ASSIGNMENTS = 20;
	
	public static final int MAX_VALUE_FULLTRIES = 100;
	public static final int MAX_VALUE_TRIES = 1000;
	
	
	@Override
	public void completeAssignment(Assignment assignment, ContentAssistContext contentAssistContext,
			ICompletionProposalAcceptor acceptor) {
		Resource res = contentAssistContext.getResource();
		MyResource myRes = MyResource.get(res);
		EObject curObj = contentAssistContext.getCurrentModel();
		if (curObj == null || curObj.eClass() == null) {
			super.completeAssignment(assignment, contentAssistContext, acceptor);
			return;
		}
		String featureName = assignment.getFeature();
		EStructuralFeature feat = curObj.eClass().getEStructuralFeature(featureName);
		if (feat == null || !(feat instanceof EAttribute)) {
			super.completeAssignment(assignment, contentAssistContext, acceptor);
			return;
		}
		
		EcoreUtil.Copier copier = new EcoreUtil.Copier();
		MyResource originalRes = myRes;
		MyResource clonedRes = originalRes.clone(copier);
		EObject context = copier.get(curObj);
		EcoreTransferFunction transferFunc = new EcoreMapTransferFunction(originalRes.getResource(),
				clonedRes.getResource(), copier);
		ViolatedConstraintsEvaluator eval = new ViolatedConstraintsEvaluator();
		
		EAttribute attr = (EAttribute)feat;
		ParameterType par = (feat.isMany())?myRes.defaultGenerator(feat):myRes.defaultModifier(feat, curObj);
		int allValueTries = 0;
		int fullValueTries = 0;
		Evaluation wr = new Evaluation();
		Map<Object, double[]> map = new HashMap<>();
		for (Object o: par.getDefaultScope()) {
			if (++allValueTries > MAX_VALUE_TRIES) {
				break;
			}
			String str = String.valueOf(o);
			if (!str.startsWith(contentAssistContext.getPrefix())) {
				continue;
			}
			if (++fullValueTries > MAX_VALUE_FULLTRIES) {
				break;
			}
			double quality[] = eval.evaluate(new BasicSetConstantChange(clonedRes.getResource(),context,attr,o),wr);
			map.put(o,quality);
		}
		List<Object> possible = new ArrayList<>();
		possible.addAll(map.keySet());
		Collections.sort(possible, new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				double[] o1d = map.get(o1);
				double[] o2d = map.get(o2);
				int ret = -Double.compare(o1d[2],o2d[2]);
				if (ret == 0) {
					ret = -Double.compare(o1d[0],o2d[0]);
				}
				if (ret == 0) {
					ret = Double.compare(o1d[1],o2d[1]);
				}
				return ret;
			}
		});
		int curAssignments = 0;
		for (Object o: possible) {
			String proposalText = String.valueOf(o);
			double[] dv = map.get(o);
			String displayText = Change.costToInvisible(curAssignments, true)+proposalText + " - " + featureName + " - quality " +dv[0]+", cost "+dv[1];
			ICompletionProposal proposal = createCompletionProposal(proposalText, displayText, null, contentAssistContext);
			if (proposal instanceof ConfigurableCompletionProposal) {
				ConfigurableCompletionProposal configurable = (ConfigurableCompletionProposal) proposal;
				configurable.setSelectionStart(configurable.getReplacementOffset());
				configurable.setSelectionLength(proposalText.length());
				configurable.setAutoInsertable(false);
				configurable.setSimpleLinkedMode(contentAssistContext.getViewer(), '\t', ' ');
			}
			acceptor.accept(proposal);
			if (++curAssignments > MAX_ASSIGNMENTS) {
				break;
			}
		}
		if (possible.isEmpty()) {
			super.completeAssignment(assignment, contentAssistContext, acceptor);
		}
	}
	
	
	//lookupCrossReference(((CrossReference)assignment.getTerminal()), context, acceptor);
	
	
	

}
