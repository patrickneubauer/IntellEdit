package at.ac.tuwien.big.forms.form.ui.quickfix.ocl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import at.ac.tuwien.big.oclgen.OCL2QuickfixSupport;
import at.ac.tuwien.big.oclgen.OCLBasedValidationQuickfix;

public class FormQuickfixProvider extends org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider {
	
	@Fix(at.ac.tuwien.big.forms.form.validation.ocl.FormValidator.EXACTLYONEWELCOMEPAGEEXISTS_ISSUE_KEY)
	public void fixIssueExactlyOneWelcomePageExists(Issue issue, IssueResolutionAcceptor acceptor) {
		for (String userData : issue.getData()) {
			final OCLBasedValidationQuickfix quickFix = OCL2QuickfixSupport.fromString(userData);
			acceptor.accept(issue, quickFix.getName(), quickFix.getDescription(), quickFix.getImage(), (EObject element, IModificationContext context) -> {
				((at.ac.tuwien.big.forms.Form) element).setWelcomeForm((boolean) quickFix.getValue());
			});
		}
	}
}
