package model;

import Comparator.WeiboComparatorViaSHTFP;
import static Comparator.WeiboComparatorViaSHTFP.*;

public class Weibo {
	private int id;
	private int uid;
	private int repostsCount;
	private int commentsCount;
	private int attitudesCount;
	
	private String content;
	private String createdAt;
	private String authorName;
	
	private boolean isForwarded = false;
	private boolean isCommented = false;
	private boolean isBiFollowing = false;
	
	private double familiarity;
	private double homogeneity;
	private double popularity;
	private double similarity;
	private double timeDecay;
	
	private double edgeRankValue = 0;
	
	private double currentPositionValue = 0;
	
	private User publisher;
	
	public Weibo(int id, int uid, int repostsCount, int commentsCount,
			int attitudesCount, String content, String createdAt,
			String authorName, boolean isCommented, boolean isForwarded) {
		super();
		this.id = id;
		this.uid = uid;
		this.repostsCount = repostsCount;
		this.commentsCount = commentsCount;
		this.attitudesCount = attitudesCount;
		this.content = content;
		this.createdAt = createdAt;
		this.authorName = authorName;
		this.isForwarded = isForwarded;
		this.isCommented = isCommented;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getRepostsCount() {
		return repostsCount;
	}
	public void setRepostsCount(int repostsCount) {
		this.repostsCount = repostsCount;
	}
	public int getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}
	public int getAttitudesCount() {
		return attitudesCount;
	}
	public void setAttitudesCount(int attitudesCount) {
		this.attitudesCount = attitudesCount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public boolean isForwarded() {
		return isForwarded;
	}
	public void setForwarded(boolean isForwarded) {
		this.isForwarded = isForwarded;
	}
	public boolean isCommented() {
		return isCommented;
	}
	public void setCommented(boolean isCommented) {
		this.isCommented = isCommented;
	}
	public User getPublisher() {
		return publisher;
	}
	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

	public boolean isBiFollowing() {
		return isBiFollowing;
	}

	public void setBiFollowing(boolean isBiFollowing) {
		this.isBiFollowing = isBiFollowing;
	}

	public double getFamiliarity() {
		return familiarity;
	}

	public void setFamiliarity(double familiarity) {
		this.familiarity = familiarity;
	}

	public double getHomogeneity() {
		return homogeneity;
	}

	public void setHomogeneity(double homogeneity) {
		this.homogeneity = homogeneity;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public double getTimeDecay() {
		return timeDecay;
	}

	public void setTimeDecay(double timeDecay) {
		this.timeDecay = timeDecay;
	}

	public double caluclateFactors(int omit, boolean isNormal) {
		double[] indexes = getWeiboIndexes();
		double factorResult = 0;
		
		int type = getWeiboType();
		
		for (int i = 0; i != indexes.length; i++) {
			if (i != omit && omit != NONE)
				continue;
			else {
				double tmp;
				if (S[i] == 0)
					tmp = indexes[i];
				else
					tmp = (double)(indexes[i] - AVG[i]) / S[i];

				if (!isNormal)
					factorResult += K[i] * tmp * K_FACTORS[type][i];
				else
					factorResult += K[i] * tmp;
			}
		}
		
		return factorResult;
	}
	
	public double[] getWeiboIndexes() {
		double[] indexes = new double[5];
		
		indexes[0] = getSimilarity() * WeiboComparatorViaSHTFP.SWEIGHT;
		indexes[1] = getTimeDecay() * WeiboComparatorViaSHTFP.TWEIGHT;
		indexes[2] = getPopularity() * WeiboComparatorViaSHTFP.PWEIGHT;
		indexes[3] = getHomogeneity() * WeiboComparatorViaSHTFP.HWEIGHT;
		indexes[4] = getFamiliarity() * WeiboComparatorViaSHTFP.FWEIGHT;
		
		return indexes;
	}

	public double getCurrentPositionValue() {
		return currentPositionValue;
	}

	public void setCurrentPositionValue(double currentPositionValue) {
		this.currentPositionValue = currentPositionValue;
	}

	public double getEdgeRankValue() {
		return edgeRankValue;
	}

	public void setEdgeRankValue(double edgeRankValue) {
		this.edgeRankValue = edgeRankValue;
	}

	public int getWeiboType() {
		User user = getPublisher();
		
		double ratio = (double)user.getFollowersCount() / (double)user.getFriendsCount();
		
		if (ratio > 11000) {
			return FAMOUS;
		}
		else if (ratio > 17 && ratio <= 11000) {
			return KNOWN;
		}
		
		return ORDINARY;
	}
}
