package at.ac.tuwien.big.autoedit.scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.RandomAccess;
import java.util.Map.Entry;

import at.tuwien.big.virtmod.datatype.IteratorUtils;

/***Der Double gibt an, wie oft - relativ gesehen - eine Lösung vorkommen sollte*/
public class SimpleMapValueScope<T> implements ValueScope<T, Double> {
	
	private PriorityQueue<ComparablePair<T, Double>> heap = 
			new PriorityQueue<ComparablePair<T,Double>>();
	private Map<T,Double> solutionQuality = new HashMap<>();
	//1/solutionQuality, normalisiert
	private double[] invsolutionQuality;
	private List<T> accessList = new ArrayList<>();
	
	public void readPriorityQueue() {
		invsolutionQuality = new double[heap.size()];
		for (ComparablePair<T,Double> pair: heap) {
			solutionQuality.put(pair.getSolution(), pair.getQuality());
			invsolutionQuality[accessList.size()]  = Math.max(1E-8,1.0/pair.getQuality());
			accessList.add(pair.getSolution());
		}
	}

	@Override
	public boolean contains(T sol) {
		return solutionQuality.containsKey(sol);
	}

	@Override
	public Double getQuality(T sol) {
		return solutionQuality.get(sol);
	}

	@Override
	public Iterator<T> iterator() {
		return IteratorUtils.convert(qualityIterate(),(x)->x.getSolution());
	}
	
	/**Should give solutions in decreating quality if possible*/
	public Iterator<ComparablePair<T,Double>> qualityIterate() {
		return heap.iterator();
	}

	@Override
	public boolean isFinite() {
		return true;
	}

	@Override
	public Iterator<T> sample() {
		return new SamplingIterator<T>(this);
	}

	public Iterator<T> deterministicSampler() {
		return new DeterministicSampler<T>(accessList,invsolutionQuality);
	}
	
	public Iterator<T> completelyRandomSampler() {
		return new RandomSampler<T>(accessList);
	}
	
	private Iterator<T> mySampler = sample();

	@Override
	public synchronized T sampled() {
		return mySampler.next();
	}


}
