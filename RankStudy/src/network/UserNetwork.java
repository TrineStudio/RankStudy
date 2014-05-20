package network;

import java.util.ArrayList;
import java.util.List;

import model.JSONParser;
import model.Keyword;
import model.TwitterRankInfo;
import model.User;
import model.Weibo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserNetwork extends BaseNetwork{
	
	public User getRootUser() {
		String result = sendGet(GET_ROOT_USER);
		
		try {
			return JSONParser.jsonToUser(new JSONObject(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Keyword getUserKeyword(int id) {
		String url = GET_USER_KEYWORD + "?id=" + id;
		
		String result = sendGet(url);
		
		if (result.length() == 0)
			return null;
		
		try {
			return JSONParser.jsonToKeyword(new JSONObject(result));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public List<User> getUserFocus(int id) {
		String url = GET_USER_FOLLOWERS + "?uid=" + id;
		
		String result = sendGet(url);
		
		try {
			return JSONParser.arrayToUsers(new JSONArray(result));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<User>();
	}
	
	public double getUserHomogeneity(int readerId, int authorId) {
		String url = GET_USER_HOMOGENEITY + "?reader_id=" + readerId + "&author_id=" + authorId;
		
		String result = sendGet(url);
		
		return Double.parseDouble(result);
	}
	
	public List<TwitterRankInfo> getUserTwitterRankInfoList(int id) {
		String url = GET_USER_TWITTER_RANK_INFO + "?id=" + id;
		
		String result = sendGet(url);
		
		try {
			return JSONParser.arrayToTwitterRankInfo(new JSONArray(result));
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TwitterRankInfo>();
		}
	}
	
	public void setUserTwitterRank(int id, double value) {
		String url = SET_USER_TWITTER_RANK + "?id=" + id + "&value=" + value;
		
		sendGet(url);
	}
	
	public double getUserTwitterRank(int id) {
		String url = GET_USER_TWITTER_RANK + "?id=" + id;
		
		String result = sendGet(url);
		
		return Double.parseDouble(result);
	}

	public List<User> getUserFollowers(int id) {
		String url = GET_USER_FOLLOWER + "?id=" + id;
		
		String result = sendGet(url);
		
		try {
			return JSONParser.arrayToUsers(new JSONArray(result));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<User>();
	}
	
	public List<User> getAllUser() {
		String result = sendGet(GET_ALL_USER);
		
		List<User> users = new ArrayList<User>();
		
		try {
			JSONArray jsonArray = new JSONArray(result);
			return JSONParser.arrayToUsers(jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public boolean isBiFollowed(String id1, String id2) {
		String url = GET_USER_RELATION + "?id1=" + id1 +"&id2=" + id2;
		
		String result = sendGet(url);
		
		if (result.contains("no")) {
			return false;
		}
		else
			return true;
	}
	
	public User getUserInfo(int uid) {
		String url = GET_USER_INFO + "?id=" + uid;
		
		String result = sendGet(url);
		
		if (result.length() == 0)
			return null;
		
		try {
			return JSONParser.jsonToUser(new JSONObject(result));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getUserInteractionCount(int id1, int id2) {
		String url = GET_USER_INTERACTION_COUNT + "?id1=" + id1 + "&id2=" + id2;
		
		String result = sendGet(url);

		try {
			return Integer.parseInt(result.substring(0, result.length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int getMultiFollowerCount(int id1, int id2) {
		String url = GET_MULTI_FOLLOER_COUNT + "?id1=" + id1 + "&id2=" + id2;
		
		String result = sendGet(url);
		
		System.out.println("======================");
		System.out.println(result);
		
		try {
			return Integer.parseInt(result.substring(0, result.length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<Weibo> getUserAvailableWeibo(int id) {
		String url = GET_USER_AVAILABLE_WEIBO + "?id=" + id;
		
		String result = sendGet(url);
		
		List<Weibo> weiboList = new ArrayList<Weibo>();
		
		try {
			JSONArray jsonArray = new JSONArray(result);
			return JSONParser.arrayToWeiboList(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return weiboList;
	}
}
