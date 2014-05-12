package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser implements JSONConstants {
	
	public static Keyword jsonToKeyword(JSONObject jsonObject) {
		try {
			int length = jsonObject.getInt(LENGTH);
			String words = jsonObject.getString(WORDS);
			
			return new Keyword(length, words);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Weibo jsonToWeibo(JSONObject jsonObject) {
		
		try {
			int id = jsonObject.getInt(ID);
			int repostsCount = jsonObject.getInt(REPOSTS_COUNT);
			int commentsCount = jsonObject.getInt(COMMENTS_COUNT);
			int attitudesCount = jsonObject.getInt(ATTITUDES_COUNT);
			int uid = jsonObject.getInt(UID);
			
			boolean isFollowed = jsonObject.getBoolean(IS_FOLLOWED);
			boolean isCommented = jsonObject.getBoolean(IS_COMMENTED);
			
			String createdAt = jsonObject.getString(CREATED_AT);
			String content = jsonObject.getString(CONTENT);
			String authorName = jsonObject.getString(AUTHOR);
			
			return new Weibo(id, uid, repostsCount, commentsCount, attitudesCount, content, createdAt, authorName, isCommented, isFollowed);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static List<Weibo> arrayToWeiboList(JSONArray array) {
		List<Weibo> weiboList = new ArrayList<Weibo>();
		
		try {
			for (int i = 0; i != array.length(); i++) {
				Weibo weibo = jsonToWeibo(array.getJSONObject(i));
				
				if (weibo != null)
					weiboList.add(weibo);
			}
		}
		catch (Exception e) {
			
		}
		
		return weiboList;
	}

	public static User jsonToUser(JSONObject jsonObject) {

		try {
			int id = jsonObject.getInt(ID);
			int friendsCount = jsonObject.getInt(FRIENDS_COUNT);
			int followersCount = jsonObject.getInt(FOLLOWERS_COUNT);
			int biFollowersCount = jsonObject.getInt(BI_FOLLOWERS_COUNT);
			
			String name = jsonObject.getString(NAME);
			String uid = jsonObject.getString(UID);
			
			return new User(id, friendsCount, followersCount, biFollowersCount, uid, name);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<User> arrayToUsers(JSONArray array) {
		List<User> users = new ArrayList<User>();
		
        try {
		    for (int i = 0; i != array.length(); i++) {
		    	User user = jsonToUser(array.getJSONObject(i));
		    	
		    	if (user != null)
		    		users.add(user);
		    }
        } catch (JSONException e) {
        	e.printStackTrace();
        }
		
		return users;
	}
}
