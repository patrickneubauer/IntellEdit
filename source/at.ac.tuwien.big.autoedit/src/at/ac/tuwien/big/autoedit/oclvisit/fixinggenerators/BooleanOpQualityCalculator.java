package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ocl.expressions.OperationCallExp;

import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.MakeDifferent;
import at.ac.tuwien.big.autoedit.fixer.MakeEqual;
import at.ac.tuwien.big.autoedit.fixer.MakeFalse;
import at.ac.tuwien.big.autoedit.fixer.MakeTrue;
import at.ac.tuwien.big.autoedit.fixer.impl.MakeFalseImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.MakeTrueImpl;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;
import at.ac.tuwien.big.autoedit.oclvisit.QualityCalculator;
import at.ac.tuwien.big.autoedit.oclvisit.RejectingFilterManager;
import at.ac.tuwien.big.autoedit.scope.helper.GetFunc;

public class BooleanOpQualityCalculator extends AbstractSelectiveEvaluator<OperationCallExp, Boolean> implements QualityCalculator<OperationCallExp, Boolean> {

	public static final BooleanOpQualityCalculator INSTANCE = new BooleanOpQualityCalculator();


	public BooleanOpQualityCalculator() {
		super(OperationCallExp.class, Boolean.class, true, null);
	}
	
	
	public static interface BooleanOpCalculator {
		public Double getQuality(GetFunc<Double> firstTrue, GetFunc<Double> firstFalse,
				GetFunc<Double> secondTrue, GetFunc<Double> secondFalse);
	}
	

	public static Map<String,BooleanOpCalculator> wantTrueMap = new HashMap<>();
	public static Map<String,BooleanOpCalculator> wantFalseMap = new HashMap<>();
	
	static {
		addCalculator("and", (at,af,bt,bf)->Math.min(at.get(), bt.get())*0.9999+0.0001*Math.max(at.get(), bt.get()),
				(at,af,bt,bf)->Math.max(af.get(), bf.get())*0.9999+0.0001*Math.min(at.get(), bt.get()));
		addCalculator("or", (at,af,bt,bf)->Math.max(at.get(), bt.get())*0.9999+0.0001*Math.min(at.get(), bt.get()),
				(at,af,bt,bf)->Math.min(af.get(), bf.get())*0.9999+0.0001*Math.max(at.get(), bt.get()));
		addCalculator("xor", (at,af,bt,bf)->Math.min(
				Math.abs(at.get()-bt.get()),
				Math.abs(af.get()-bf.get())),
				(at,af,bt,bf)->Math.min(
						Math.abs(at.get()-bf.get()),
						Math.abs(af.get()-bt.get())));
		addCalculator("<>", (at,af,bt,bf)->Math.min(
				Math.abs(at.get()-bt.get()),
				Math.abs(af.get()-bf.get())),
				(at,af,bt,bf)->Math.min(
						Math.abs(at.get()-bf.get()),
						Math.abs(af.get()-bt.get())));
		addCalculator("=",
				(at,af,bt,bf)->Math.min(
						Math.abs(at.get()-bf.get()),
						Math.abs(af.get()-bt.get())),
				(at,af,bt,bf)->Math.min(
				Math.abs(at.get()-bt.get()),
				Math.abs(af.get()-bf.get())));
		addCalculator("implies", (at,af,bt,bf)->Math.max(
				af.get(),bt.get()),
				(at,af,bt,bf)->Math.min(
						at.get(),bf.get()));
	}
	
	
	public static void addCalculator(String op, BooleanOpCalculator wantTrue, BooleanOpCalculator wantFalse) {
		wantTrueMap.put(op, wantTrue);
		wantFalseMap.put(op, wantFalse);
	}

			
	
	@Override
	public Double getQualityCalculation(FixAttempt singleAttemptForThis, EvalResult baseRes, ExpressionResult res,
			OperationCallExp expr, Boolean result, RejectingFilterManager man, GetQualityFunc qualityFunc) {
		if (res == null) {
			return null;
		}
		Boolean target = null;
		if (singleAttemptForThis instanceof MakeTrue) {
			target = true;
		} else if (singleAttemptForThis instanceof MakeFalse) {
			target = false;
		} else if (singleAttemptForThis instanceof MakeEqual) {
			Object border = ((MakeDifferent) singleAttemptForThis).border();
			if (border instanceof Boolean) {
				target = ((Boolean)border);
			}
		} else if (singleAttemptForThis instanceof MakeDifferent) {
			Object border = ((MakeDifferent) singleAttemptForThis).border();
			if (border instanceof Boolean) {
				target = !((Boolean)border);
			}
		}
		if (target == null) {
			return null;
		}
		String name = res.getExpressionName();
		BooleanOpCalculator calc = target?wantTrueMap.get(name):wantFalseMap.get(name);
		if (calc == null) {
			return null;
		}
		return calc.getQuality(
				()->qualityFunc.getQuality(0, MakeTrueImpl.INSTANCE),
				()->qualityFunc.getQuality(0, MakeFalseImpl.INSTANCE),
				()->qualityFunc.getQuality(1, MakeTrueImpl.INSTANCE),
				()->qualityFunc.getQuality(1, MakeFalseImpl.INSTANCE));
	}

}
