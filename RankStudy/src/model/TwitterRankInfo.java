package model;

public class TwitterRankInfo {
	private int weiboCounts;
	private int weiboCount;
	private Keyword keyword;
	
	public TwitterRankInfo(int weiboCount, Keyword keyword, int weiboCounts) {
		super();
		this.weiboCount = weiboCount;
		this.keyword = keyword;
		this.weiboCounts = weiboCounts;
	}

	public int getWeiboCount() {
		return weiboCount;
	}
	public void setWeiboCount(int weiboCount) {
		this.weiboCount = weiboCount;
	}
	public Keyword getKeyword() {
		return keyword;
	}
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	public int getWeiboCounts() {
		return weiboCounts;
	}

	public void setWeiboCounts(int weiboCounts) {
		this.weiboCounts = weiboCounts;
	}
	
}
