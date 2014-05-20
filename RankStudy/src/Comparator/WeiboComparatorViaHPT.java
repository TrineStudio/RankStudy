package Comparator;

import java.util.Comparator;
import static Comparator.WeiboComparatorViaSHTFP.*;

import model.Weibo;

public class WeiboComparatorViaHPT implements Comparator<Weibo>{

	@Override
	public int compare(Weibo o1, Weibo o2) {
		
		double[] o1Indexes = getWeiboIndexes(o1);
		double[] o2Indexes = getWeiboIndexes(o2);
		
		double o1PageRank = caluclateFactors(o1Indexes);
		double o2PageRank = caluclateFactors(o2Indexes);
		
		int result = 0;
		
		if (o1PageRank < o2PageRank)
			result = 1;
		else if (o1PageRank == o2PageRank)
			result = 0;
		else
			result = -1;
		
		return result;
	}

	public double caluclateFactors(double[] indexes) {
		double factorResult = 0;
		
		double[] values = new double[]
		{
		    1.0f * SWEIGHT, 1.0f * TWEIGHT, 1.0f * PWEIGHT, 1.0f * HWEIGHT, 1.0f * FWEIGHT
		};
		
		double indexSqrt = 0;

		double valueSqrt = 0;
		
		for (int i = 0; i != indexes.length; i++) {
			if (i == SIMILARITY || i == FAMILIARITY)
				continue;
			else {
				factorResult += indexes[i] * values[i];
				indexSqrt += indexes[i] * indexes[i];
				valueSqrt += values[i] * values[i];
			}
		}
		
		
		return Math.acos(factorResult / (Math.sqrt(indexSqrt) * Math.sqrt(valueSqrt)));
	}
	
	public double[] getWeiboIndexes(Weibo weibo) {
		double[] indexes = new double[5];
		
        indexes[0] = weibo.getSimilarity() * SWEIGHT;
        indexes[1] = weibo.getTimeDecay() * TWEIGHT;
        indexes[2] = weibo.getPopularity() * PWEIGHT;
        indexes[3] = weibo.getHomogeneity() * HWEIGHT;
        indexes[4] = weibo.getFamiliarity() * FWEIGHT;
		
		return indexes;
	}


}

