package network;

public class InteractionNetwork extends BaseNetwork {
	public boolean isUserForward(int uid, int weiboId) {
		String url = IS_USER_FORWARD + "?uid=" + uid + "&weibo_id=" + weiboId;
		
		String result = sendGet(url);
		
		if (result.equals("yes\n"))
			return true;
		else
			return false;
	}

	public boolean isUserComment(int uid, int weiboId) {
		String url = IS_USER_COMMENT + "?uid=" + uid + "&weibo_id=" + weiboId;
		
		String result = sendGet(url);
		
		if (result.equals("yes\n"))
			return true;
		else
			return false;
	}
}
