package factors;

public class PopularityParser {

	public static final int MAXPOP = 1177707 * 4  + 1932197 * 2 + 870111;
	public PopularityParser(){
		
	}
	
	public double calcPopularity(int repost, int comment, int like, int rrepost, int rcomment, int rlike){
		int score = getPopScore(repost, comment, like) + getPopScore(rrepost, rcomment, rlike);
		System.out.println(Integer.toString(score));
//		double sigmoidNum = sigmoid(score);
//		System.out.println(sigmoidNum + "");
//		double ans = postSigmoid(sigmoidNum);

		double ans = divideMax(score, true);
		System.out.println(ans + "");
		return ans;
	}

	public double calcPopularity(int repost, int comment, int like){
		int score = getPopScore(repost, comment, like);
		System.out.println(Integer.toString(score));
//		double sigmoidNum = sigmoid(score);
//		System.out.println(sigmoidNum + "");
//		double ans = postSigmoid(sigmoidNum);

		double ans = divideMax(score, false);
		System.out.println(ans + "");
		return ans;
	}
	
	public int getPopScore(int repost, int comment, int like){
		return repost * 4 + comment * 2 + like;
	}
	
	public double divideMax(int num, boolean isReposted){
			
		if (!isReposted)
			return (double)Math.log10(num + 1) / Math.log10(MAXPOP);
		else
			return (double)Math.log10(num + 1) / Math.log10(2 * MAXPOP);
	}
	
	public double sigmoid(int num){
		double d1 = (double)Math.exp( (double)-num);
		double d2 = d1 + 1.0f;
		return (double)((double)1 / d2);
		//there is a bug
	}
	
	public double postSigmoid(double sigResult){
		return (sigResult - 0.5f) * 2.0f;
	}
}
