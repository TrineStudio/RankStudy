package Comparator;

import java.util.Comparator;

import model.Weibo;

public class WeiboComparatorViaSHTFP implements Comparator<Weibo>{
	
	public static final int NONE = -1;
	public static final int SIMILARITY = 0;
	public static final int TIME_DECAY = 1;
	public static final int POPULARITY = 2;
	public static final int HOMOGENEITY = 3;
	public static final int FAMILIARITY = 4;
	
	private int omit;
	

	public WeiboComparatorViaSHTFP(int omit) {
		super();
		this.omit = omit;
	}



	public int getOmit() {
		return omit;
	}



	public void setOmit(int omit) {
		this.omit = omit;
	}



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
		double factorResult = 1;
		
		for (int i = 0; i != indexes.length; i++) {
			if (i == omit)
				continue;
			else
				factorResult *= indexes[i];
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

