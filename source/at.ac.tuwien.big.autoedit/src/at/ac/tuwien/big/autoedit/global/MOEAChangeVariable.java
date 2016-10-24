package at.ac.tuwien.big.autoedit.global;

import java.util.Iterator;
import java.util.Random;

import org.eclipse.emf.ecore.resource.Resource;
import org.moeaframework.core.Variable;

import com.google.common.base.Objects;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.ChangeType;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;
import at.ac.tuwien.big.autoedit.xtext.DynamicValidator;
import at.ac.tuwien.big.autoedit.xtext.DynamicValidatorIFace;

public class MOEAChangeVariable implements Variable {
	
	private ChangeType<?, ?> curChangeType;
	private Iterator<?> sampler;
	private Change<?> curChange;
	private DynamicValidatorIFace changeProvider;
	private Random random = new Random();
	
	public boolean equals(Object o) {
		if (o instanceof MOEAChangeVariable) {
			return Objects.equal(curChange, ((MOEAChangeVariable) o).curChange);
		}
		return false;
	}
	
	public MOEAChangeVariable(DynamicValidatorIFace validator) {
		this.changeProvider = validator;
		randomize();
	}
	
	public MOEAChangeVariable(DynamicValidatorIFace changeProvider, ChangeType<?,?> curChangeType, Change<?> curChange) {
		this.changeProvider = changeProvider;
		this.curChangeType = curChangeType;
		this.curChange = curChange;
		this.sampler = (curChangeType!=null)?curChangeType.sampleWithMissing():null;
	}
	

	@Override
	public Variable copy() {
		return new MOEAChangeVariable(changeProvider, curChangeType, (curChange==null)?null:curChange.clone());
	}

	@Override
	public void randomize() {
		randomChange(null);
	}
	
	public void randomChange(EcoreTransferFunction tf) {
		if (random.nextInt(3) == 0) {
			curChange = null;
			return;
		}
		if (sampler != null && random.nextBoolean()) { //randomly just change the change
			curChange = (Change<?>)sampler.next();
		} else { //Define a new change
			curChangeType = null;
			curChange = null;
			sampler = null;
			if (random.nextBoolean()) {
				//It is an already existing quickfix
				curChange = changeProvider.randomQuickfix(random);
			} 
			//Just a random change
			if (curChange == null) {
				if (tf == null) {
					curChangeType = changeProvider.randomChange(random);
				} else {
					curChangeType = MyResource.get(tf.forwardResource()).randomChange(random);
					curChangeType.transfer(tf.inverse());
				}
				curChange = curChangeType.compileWithMissing();
				sampler = curChangeType.sampleWithMissing();
			}
			if (curChange == null) {
				curChange = Change.empty(changeProvider.getResource());
			}
		}
	}
	
	public Change<?> getCurChange() {
		return curChange;
	}

}
