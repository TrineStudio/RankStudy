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
	
	public static final int SWEIGHT = 1;
	public static final int TWEIGHT = 6;
	public static final int PWEIGHT = 6;
	public static final int HWEIGHT = 6;
	public static final int FWEIGHT = 2;
	
	public static final double AVG[] = new double[5];
	public static final double S[] = new double[5];
	
	private int omit;
	private boolean isNormal;
	

	public WeiboComparatorViaSHTFP(int omit, boolean isNormal) {
		super();
		this.omit = omit;
		this.isNormal = isNormal;
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
			result = -1;
		else if (o1PageRank == o2PageRank)
			result = 0;
		else
			result = 1;
		
		return result;
	}

	public double caluclateFactors(double[] indexes) {
		double factorResult = 0;
		
		double[] values = new double[]
		{
		    1.0f * SWEIGHT, 1.0f * TWEIGHT, 1.0f * PWEIGHT, 1.0f * HWEIGHT, 1.0f * FWEIGHT
		};
		
		if (isNormal) {
			values = new double[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
		}
		
		for (int i = 0; i != indexes.length; i++) {
			if (i == omit)
				continue;
			else {
				double tmp;

				if (S[i] != 0)
					tmp = ((double)(indexes[i] - AVG[i])) / S[i];
				else
					tmp = indexes[i];
				
				factorResult += Math.pow(tmp - values[i], 2);
			}
		}
		
		return Math.sqrt(factorResult);
	}
	
	public double[] getWeiboIndexes(Weibo weibo) {
		double[] indexes = new double[5];
		
		if (!isNormal) {
			indexes[0] = weibo.getSimilarity() * SWEIGHT;
        	indexes[1] = weibo.getTimeDecay() * TWEIGHT;
        	indexes[2] = weibo.getPopularity() * PWEIGHT;
        	indexes[3] = weibo.getHomogeneity() * HWEIGHT;
        	indexes[4] = weibo.getFamiliarity() * FWEIGHT;
		}
		else {
			indexes[0] = weibo.getSimilarity();
        	indexes[1] = weibo.getTimeDecay();
        	indexes[2] = weibo.getPopularity(); 
        	indexes[3] = weibo.getHomogeneity();
        	indexes[4] = weibo.getFamiliarity();
		}
		
		return indexes;
	}


}

