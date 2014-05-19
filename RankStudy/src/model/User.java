package model;

public class User {
	private int id;
	private int friendsCount;
	private int followersCount;
	private int biFollowersCount;
	
	private int interactionType;
	private int weiboCount;
	
	private double twitterRank = 0;
	private double pageRankValue = 0;
	
	private String uid;
	private String name;
	private String interactionTime;
	
	public User(int id, int friendsCount, int followersCount,
			int biFollowersCount, String uid, String name) {
		super();
		this.id = id;
		this.friendsCount = friendsCount;
		this.followersCount = followersCount;
		this.biFollowersCount = biFollowersCount;
		this.uid = uid;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFriendsCount() {
		if (friendsCount == 0)
			return 1;
					
		return friendsCount;
	}
	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}
	public int getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}
	public int getBiFollowersCount() {
		return biFollowersCount;
	}
	public void setBiFollowersCount(int biFollowersCount) {
		this.biFollowersCount = biFollowersCount;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getPageRankValue() {
		return pageRankValue;
	}

	public void setPageRankValue(double pageRankValue) {
		this.pageRankValue = pageRankValue;
	}

	public int getInteractionType() {
		return interactionType;
	}

	public void setInteractionType(int interactionType) {
		this.interactionType = interactionType;
	}

	public int getWeiboCount() {
		return weiboCount;
	}

	public void setWeiboCount(int weiboCount) {
		this.weiboCount = weiboCount;
	}

	public String getInteractionTime() {
		return interactionTime;
	}

	public void setInteractionTime(String interactionTime) {
		this.interactionTime = interactionTime;
	}

	public double getTwitterRank() {
		return twitterRank;
	}

	public void setTwitterRank(double twitterRank) {
		this.twitterRank = twitterRank;
	}
}
