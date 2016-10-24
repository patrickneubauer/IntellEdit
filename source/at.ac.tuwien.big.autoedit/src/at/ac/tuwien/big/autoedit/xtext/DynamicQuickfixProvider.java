package at.ac.tuwien.big.autoedit.xtext;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
//import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.model.edit.IssueModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.SemanticModificationWrapper;
import org.eclipse.xtext.ui.editor.model.edit.IssueModificationContext.Factory.Default;
import org.eclipse.xtext.ui.editor.quickfix.AbstractDeclarativeQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.ui.editor.quickfix.ReplaceModification;
import org.eclipse.xtext.validation.Issue;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.transfer.URIBasedEcoreTransferFunction;
import at.ac.tuwien.big.oclgen.OCL2QuickfixSupport;
import at.ac.tuwien.big.oclgen.OCLBasedValidationQuickfix;
import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import difflib.myers.MyersDiff;

public class DynamicQuickfixProvider extends org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider {
	
	
	
//	private Set<QuickfixReference,Long> providedFixes = 
	
	public boolean displayChange(Change<?> ch) {
		return true;
	}
	
	

	
	  public static List<Character> asList(final String string) {
	        return new AbstractList<Character>() {
	            public int size() { return string.length(); }
	            public Character get(int index) { return string.charAt(index); }
	        };
	    }
	  
	  public static void modify(IXtextDocument doc, Change<?> ch) {
		List<String> oldLines = Arrays.asList(doc.get().split("\n"));
		ch.execute();		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ch.forResource().save(bos, new HashMap<>());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String os = new String(bos.toByteArray());
		List<String> newLines = Arrays.asList(os.split("\n"));
		Patch patch = DiffUtils.diff(oldLines, newLines, new MyersDiff());
		int curLinePos = 0;
		int curCharPos = 0;
		for (Delta delta: patch.getDeltas()) {
			//int originalDiffLine = delta.getOriginal().getPosition()+1;
			
			Chunk original = delta.getOriginal();
			Chunk revised = delta.getRevised();
			while (curLinePos < revised.getPosition()) {
				curCharPos+= newLines.get(curLinePos).length()+1;
				++curLinePos;
			}
			curLinePos+= revised.getLines().size();
			if (original.getLines().size() == 0) {
				StringBuilder totalString = new StringBuilder();
				for (String str: (List<String>)revised.getLines()) {
					totalString.append(str);
					totalString.append("\n");
				}
				if (revised.getLines().size()+revised.getPosition() == newLines.size()) {
					totalString.replace(totalString.length()-1,totalString.length(),"");
				}
				String str = totalString.toString();
				try {
					doc.replace(curCharPos, 0, str);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				curCharPos+= str.length();
				//Insert
			} else if (revised.getLines().size() == 0) {
				//Delete
				StringBuilder totalString = new StringBuilder();
				int deleteCount = 0;
				for (String str: (List<String>)original.getLines()) {
					deleteCount+= str.length()+1;
				}
				if (original.getLines().size()+original.getPosition() == oldLines.size()) {
					--deleteCount;
				}
				try {
					doc.replace(curCharPos, deleteCount,"");
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				
			} else {
				//Update
				if (original.getLines().size() != revised.getLines().size()) {
					throw new RuntimeException("Expectation violated!");
				}
				for (int i = 0; i < original.getLines().size(); ++i) {
					List<Character> start = asList((String)original.getLines().get(i));
					List<Character> end = asList((String)revised.getLines().get(i));
	            Patch deltaPatch = DiffUtils.diff(start, end);
	            List<Delta> strDeltas = deltaPatch.getDeltas();
	            int oldCharPos = curCharPos;
	            int curSubPos = 0;
	            for (Delta strDelta : strDeltas) {
	            	int advance = strDelta.getOriginal().getPosition()-curSubPos;
	            	curCharPos+= advance;
	            	curSubPos = strDelta.getOriginal().getPosition()+strDelta.getOriginal().size();
	            	List<Character> newChars = (List<Character>)strDelta.getRevised().getLines();
	            	StringBuilder builder = new StringBuilder();
	            	for (Character c: newChars) {
	            		builder.append(c);
	            	}
	            	String buildStr = builder.toString();
	            	try {
							doc.replace(curCharPos, strDelta.getOriginal().size(), buildStr);
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
	                curCharPos+= buildStr.length();
	            }
	            curCharPos+= start.size()+1-curSubPos;
	            if (oldCharPos + revised.size()+1 != curCharPos) {
	            	System.err.println();
	            }

				}
				
//				if (revised.getLines().size()+revised.getPosition() != newLines.size()) {
//					++curCharPos;
//				}
			}
		}
	  }
	@Override
	public List<IssueResolution> getResolutions(final Issue issue) {
		if (issue.getData().length != 3) {
			return Collections.emptyList();
		}
		DynamicValidator validator = DynamicValidator.getValidator(issue.getData()[0]);
		if (validator == null) {
			return Collections.emptyList();
		}
		boolean knowIssue = validator.knowIssue(issue);
		ExpressionQuickfixInfo<?> qi = validator.getQuickfixes(issue.getData()[1]);;
		List<QuickfixReference> curQuickfixes = qi.getQuickfix(issue.getData()[2],issue.getUriToProblem().toString());
		List<IssueResolution> resList = new ArrayList<IssueResolution>(curQuickfixes.size());
		boolean displayed = false;
		for (QuickfixReference ref: curQuickfixes) {
			displayed = true;
			if (validator.displayedReferences().put(ref, true) != null) {
				continue;
			}
			if (!displayChange(ref.getChange())) {
				continue;
			}
			IModificationContext context = getModificationContextFactory().createModificationContext(issue);
			ISemanticModification smod = new ISemanticModification() {
				
				@Override
				public void apply(EObject element, IModificationContext context) throws Exception {
					
					Change<?> ch = ref.getChange();
					if (element.eResource() != ch.forResource()) {
						ch = ch.clone();
						ch.transfer(new URIBasedEcoreTransferFunction(ch.forResource(),element.eResource()));
					}
					ch.execute();
					
					
				}
			};
			SemanticModificationWrapper modificationWrapper = new SemanticModificationWrapper(issue.getUriToProblem(), smod);
			IModification modification = new IModification() {
				
				@Override
				public void apply(IModificationContext context) throws Exception {
					Change<?> ch = ref.getChange();
					/*if (element.eResource() != ch.forResource()) {
						ch = ch.clone();
						ch.transfer(new URIBasedEcoreTransferFunction(ch.forResource(),element.eResource()));
					}*/
					
					modify(context.getXtextDocument(), ch);
				}
			}; 
			//Ich hoffe, wenn ich das übreschreibe passt es
			IssueResolution res = new IssueResolution(ref.getName(), 
					ref.getDescription(), ref.getImage(), context,  modification);	
			resList.add(res);
		}
		if (!displayed) {
			//resList.add(new EmptyQuickfixReference());
		}
		return resList;
	}
	
	public Iterable<Method> collectMethods(Class<? extends AbstractDeclarativeQuickfixProvider> cl, String issueCode) {
		
		return super.collectMethods(cl, issueCode);
	}


	@Override
	public boolean hasResolutionFor(String issueCode) {
		//TODO: Stimmt natürlich nicht immer, hängt vom Issue ab ...
		return (issueCode != null && issueCode.startsWith("DYNISSUE_")) || super.hasResolutionFor(issueCode);
	}
	
	@Fix(value = "")
	public List<IssueResolution> fixSomething(Issue issue) {
		return getResolutions(issue);
	}

	

}
