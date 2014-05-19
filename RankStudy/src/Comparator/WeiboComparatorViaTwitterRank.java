package Comparator;

import java.util.Comparator;

import model.Weibo;

public class WeiboComparatorViaTwitterRank implements Comparator<Weibo>{
	@Override
	public int compare(Weibo o1, Weibo o2) {
		double o1TwitterRankValue = o1.getPublisher().getTwitterRank();
		double o2TwitterRankValue = o2.getPublisher().getTwitterRank();
		
		int result = 0;
		
		if (o1TwitterRankValue < o2TwitterRankValue)
			result = 1;
		else if (o1TwitterRankValue == o2TwitterRankValue)
			result = 0;
		else
			result = -1;
		
		return result;
	}
}
