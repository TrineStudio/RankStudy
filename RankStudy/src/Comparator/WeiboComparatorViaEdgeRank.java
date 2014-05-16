package Comparator;

import java.util.Comparator;

import model.Weibo;

public class WeiboComparatorViaEdgeRank implements Comparator<Weibo>{
	@Override
	public int compare(Weibo o1, Weibo o2) {
		double o1EdgeRank = o1.getEdgeRankValue();
		double o2EdgeRank = o2.getEdgeRankValue();
		
		int result = 0;
		
		if (o1EdgeRank < o2EdgeRank)
			result = 1;
		else if (o1EdgeRank == o2EdgeRank)
			result = 0;
		else
			result = -1;
		
		return result;
	}
}
