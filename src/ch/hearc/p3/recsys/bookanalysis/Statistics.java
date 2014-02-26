package ch.hearc.p3.recsys.bookanalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.utils.Pair;

public class Statistics
{
	private List<Integer>	features;
	private double			average;
	private double			variance;
	private double			standardDeviation;
	private double			min;
	private double			max;
	private double			median;

	public Statistics(List<List<Pair<Integer, List<Pair<String, Double>>>>> books)
	{
		Map<Integer, Integer> featuresDict = new HashMap<Integer, Integer>();
		for (List<Pair<Integer, List<Pair<String, Double>>>> list : books)
			for (Pair<Integer, List<Pair<String, Double>>> pair : list)
			{
				if (!featuresDict.containsKey(pair.getKey()))
					featuresDict.put(pair.getKey(), 0);
				featuresDict.put(pair.getKey(), featuresDict.get(pair.getKey()) + pair.getValue().size());
			}
		features = new ArrayList<Integer>(featuresDict.values());

		computeAverage();
		computeVariance();
		computeStandardDeviation();
		computeMinMaxMedian();
	}

	@Override
	public String toString()
	{
		return "Average : " + average + "\nVariance : " + variance + "\nStandard Deviation : " + standardDeviation + "\nMin : " + min + "\nMax : " + max + "\nMedian : " + median;
	}

	private void computeMinMaxMedian()
	{
		Collections.sort(features);
		min = features.get(0);
		max = features.get(features.size() - 1);
		median = (features.size() % 2 == 0) ? (features.get(features.size() / 2) + features.get(features.size() / 2 + 1)) / 2.0 : features.get(features.size() / 2);
	}

	private void computeStandardDeviation()
	{
		standardDeviation = Math.sqrt(variance);
	}

	private void computeVariance()
	{
		variance = 0.0;
		for (Integer f : features)
			variance += (f - average) * (f - average);
		variance /= features.size();
	}

	private void computeAverage()
	{
		average = 0.0;
		for (Integer f : features)
			average += f;
		average /= features.size();
	}

	public List<Integer> getFeatures()
	{
		return features;
	}

	public double getAverage()
	{
		return average;
	}

	public double getVariance()
	{
		return variance;
	}

	public double getStandardDeviation()
	{
		return standardDeviation;
	}

	public double getMin()
	{
		return min;
	}

	public double getMax()
	{
		return max;
	}

	public double getMedian()
	{
		return median;
	}
}
