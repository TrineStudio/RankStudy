package network;

import java.util.ArrayList;
import java.util.List;

import model.JSONParser;
import model.User;
import model.Weibo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeiboNetwork extends BaseNetwork{
	public Weibo getWeiboForwardInfo(int uid, int weiboId) {
		String url = GET_WEIBO_FORWARD + "?id=" + uid + "&weibo_id=" + weiboId;
		
		String result = sendGet(url);
		
		if (result.length() == 0)
			return null;
		else 
		{
			try {
				return JSONParser.jsonToWeibo(new JSONObject(result));
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	public List<User> getWeiboInteractionUsers(int weiboId) {
		String url = GET_WEIBO_COMMENTER + "?id=" + weiboId;
		String result = sendGet(url);
		
		try {
			return JSONParser.arrayToUsers(new JSONArray(result));
		} catch (JSONException e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}
	}
}
