package network;

public interface NetworkUrls {
	public static final String HOST = "http://codingmiao.com/maggie/";
	public static final String GET_ALL_USER = HOST + "getAllUser.php";
	public static final String GET_USER_FOLLOWERS = HOST + "getUserFollowers.php";
	public static final String GET_WEIBO = HOST + "getWeibo.php";
	public static final String GET_USER_FORWARD_WEIBO = HOST + "getUserForwardWeibo.php";
	public static final String GET_USER_AVAILABLE_WEIBO = HOST + "getUserAvailableWeibo.php";
	public static final String IS_USER_FORWARD = HOST + "is-user-forward.php";
	public static final String IS_USER_COMMENT = HOST + "is-user-comment.php";
	public static final String GET_USER_INFO = HOST + "getUserInfo.php";
	public static final String GET_USER_RELATION = HOST + "getUserRelation.php";
	public static final String GET_WEIBO_COMMENTER = HOST + "getWeiboCommenters.php";
	public static final String GET_ROOT_USER = HOST + "getRootUser.php";
	public static final String GET_WEIBO_FORWARD = HOST + "getWeiboForward.php";
	public static final String GET_USER_KEYWORD = HOST + "getUserRecentWeiboKeywords.php";
	public static final String GET_MULTI_FOLLOER_COUNT = HOST + "getMultiFollowersCount.php";
}
