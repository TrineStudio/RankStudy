package network;

import java.util.ArrayList;
import java.util.List;

import model.JSONParser;
import model.Keyword;
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
	
	public List<User> getUserFollowers(int id) {
		String url = GET_WEIBO_COMMENTER + "?id=" + id;
		
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
		
		try {
			return JSONParser.jsonToUser(new JSONObject(result));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
