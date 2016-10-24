package at.ac.tuwien.big.autoedit.xtext;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.quickfix.XtextQuickAssistProcessor;

public class DynamicQuickAssistProcessor extends XtextQuickAssistProcessor {
	
	
	protected void sortQuickfixes(List<ICompletionProposal> quickFixes) {
		Collections.sort(quickFixes, new Comparator<ICompletionProposal>() {
			public int compare(ICompletionProposal o1, ICompletionProposal o2) {
				if (o1 instanceof ConfigurableCompletionProposal && o2 instanceof ConfigurableCompletionProposal) {
					ConfigurableCompletionProposal c1 = (ConfigurableCompletionProposal)o1;
					ConfigurableCompletionProposal c2 = (ConfigurableCompletionProposal)o2;
					Object co1 = c1.getAdditionalData("index");
					Object co2 = c2.getAdditionalData("index");
					if (co1 instanceof Integer && co2 instanceof Integer) {
						return ((Integer)co1).compareTo((Integer)co2);
					}
				}
				return 0;
			}
		});
	}

}
