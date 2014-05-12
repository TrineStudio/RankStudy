package Comparator;

import java.util.Comparator;

import model.Weibo;

public class WeiboComparatorViaPageRank implements Comparator<Weibo>{

	@Override
	public int compare(Weibo o1, Weibo o2) {
		double o1PageRank = o1.getPublisher().getPageRankValue();
		double o2PageRank = o2.getPublisher().getPageRankValue();
		
		int result = 0;
		
		if (o1PageRank < o2PageRank)
			result = 1;
		else if (o1PageRank == o2PageRank)
			result = 0;
		else
			result = -1;
		
		return result;
	}

}
