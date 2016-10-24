package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import at.ac.tuwien.big.autoedit.fixer.Decrease;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.Increase;
import at.ac.tuwien.big.autoedit.fixer.MakeDifferent;
import at.ac.tuwien.big.autoedit.fixer.MakeEqual;
import at.ac.tuwien.big.autoedit.fixer.MakeFalse;
import at.ac.tuwien.big.autoedit.fixer.MakeTrue;
import at.ac.tuwien.big.autoedit.fixer.SetAddAny;
import at.ac.tuwien.big.autoedit.fixer.SetRemoveAny;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.QualityCalculator;
import at.ac.tuwien.big.autoedit.oclvisit.RejectingFilterManager;

public class BasicQualityCalculator implements QualityCalculator<Object, Object> {
	
	public static final BasicQualityCalculator INSTANCE = new BasicQualityCalculator();

	@Override
	public Class<Object> getFilterClass() {
		return null;
	}

	@Override
	public Class<Object> getResultClass() {
		return null;
	}

	@Override
	public boolean acceptNullResult() {
		return true;
	}

	@Override
	public String getRequiredName() {
		return null;
	}

	@Override
	public Double getQualityCalculation(FixAttempt singleAttemptForThis, EvalResult baseRes, ExpressionResult res,
			Object expr, Object result, RejectingFilterManager man, GetQualityFunc qualityFunc) {
		if (singleAttemptForThis instanceof MakeDifferent) {
			if (Objects.equals(((MakeDifferent)singleAttemptForThis).border(), result)) {
				return 0.0;
			}
			return 1.0;
		} else if (singleAttemptForThis instanceof MakeEqual || singleAttemptForThis instanceof MakeTrue || singleAttemptForThis instanceof MakeFalse) {
			Object target = null;
			if (singleAttemptForThis instanceof MakeEqual) {
				target = ((MakeEqual) singleAttemptForThis).border();
			} else if (singleAttemptForThis instanceof MakeTrue) {
				target = true; 
			} else if (singleAttemptForThis instanceof MakeFalse) {
				target = false;
			}
			if (Objects.equals(target, result)) {
				return 1.0;
			}
			return 0.0;
		} else if (singleAttemptForThis instanceof Increase || singleAttemptForThis instanceof Decrease) {
			Object border = (singleAttemptForThis instanceof Increase)?(((Increase)singleAttemptForThis).border()):
				(((Decrease)singleAttemptForThis).border());
			if (border instanceof Number && result instanceof Number) {
				double borderV = ((Number)border).doubleValue();
				double resultV = ((Number)result).doubleValue();
				if (Double.isNaN(borderV) || Double.isNaN(resultV)) {
					return 0.0;
				}
				return 1.0/Math.abs(borderV-resultV);
			}			
		} else if (singleAttemptForThis instanceof SetAddAny || singleAttemptForThis instanceof SetRemoveAny) {
			int targetSize = (singleAttemptForThis instanceof SetRemoveAny)?((SetRemoveAny) singleAttemptForThis).targetSize():((SetAddAny) singleAttemptForThis).targetSize();
			if (result instanceof Collection) {
				Collection curCol = (Collection)result;
				if (singleAttemptForThis instanceof SetAddAny && curCol.size() >= targetSize) {
					return 1.0;
				}
				if (singleAttemptForThis instanceof SetRemoveAny && curCol.size() <= targetSize) {
					return 1.0;
				}
				return 1.0/(1+Math.abs(curCol.size()-targetSize));
			}
		}
		return null;
	}

}
