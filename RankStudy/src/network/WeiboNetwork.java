package network;

import java.util.ArrayList;
import java.util.List;

import model.JSONParser;
import model.User;
import model.Weibo;
import model.WeiboIndex;

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
	
	public void setWeiboIndex(Weibo weibo) {
		String url = SET_WEIBO_INDEX + "?id=" + weibo.getId() + "&similarity=" + weibo.getSimilarity() + "&homogeneity=" +
					 weibo.getHomogeneity() + "&familarity=" + weibo.getFamiliarity() + "&time_decay=" + weibo.getTimeDecay()
					 + "&popularity=" + weibo.getPopularity() + "&edge_rank=" + weibo.getEdgeRankValue();
		
		sendGet(url);
	}
	
	public WeiboIndex getWeiboIndex(int id) {
		String url = GET_WEIBO_INDEX + "?id=" + id;
		
		String result = sendGet(url);
		
		if (result.indexOf("{") == -1)
			return null;
		else 
			try {
				return JSONParser.jsonToWeiboIndex(new JSONObject(result));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
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
