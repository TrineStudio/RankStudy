package network;

import model.JSONParser;
import model.Weibo;

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
				e.printStackTrace();
				return null;
			}
		}
	}
}
