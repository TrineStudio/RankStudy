package Comparator;

import java.util.Comparator;
import java.util.Date;

import model.Weibo;

public class WeiboComparatorViaTime implements Comparator<Weibo>{

	@SuppressWarnings("deprecation")
	@Override
	public int compare(Weibo o1, Weibo o2) {
		double o1TimeValue = new Date(o1.getCreatedAt()).getTime();
		double o2TimeValue = new Date(o2.getCreatedAt()).getTime();
		
		int result = 0;
		
		if (o1TimeValue < o2TimeValue)
			result = 1;
		else if (o1TimeValue == o2TimeValue)
			result = 0;
		else
			result = -1;
		
		return result;
	}
}
