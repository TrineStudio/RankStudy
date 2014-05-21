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
			result = -1;
		else if (o1PageRank == o2PageRank)
			result = 0;
		else
			result = 1;
		
		return result;
	}

	public double caluclateFactors(double[] indexes) {
		double factorResult = 0;
		
		for (int i = 0; i != indexes.length; i++) {
			if (i == SIMILARITY || i == FAMILIARITY)
				continue;
			else {
				factorResult += indexes[i] * K[i];
			}
		}

		return factorResult;
	}
	
	public double[] getWeiboIndexes(Weibo weibo) {
		double[] indexes = new double[5];
		
        indexes[0] = weibo.getSimilarity();
        indexes[1] = weibo.getTimeDecay(); 
        indexes[2] = weibo.getPopularity();
        indexes[3] = weibo.getHomogeneity();
        indexes[4] = weibo.getFamiliarity();
		
		return indexes;
	}


}

