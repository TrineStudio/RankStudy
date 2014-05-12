package network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KeyWordSimNetwork extends BaseNetwork {
	
	final String requestUrl = "http://life.chacuo.net/convertsimilar";
	final String markBegin = "\uff1a";
	final String markEnd = "%!";
	
	public double getSimilarity(String str1, String str2){
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		double d = 0;
		
		System.out.println(str1);
		System.out.println(str2);
		
		parameters.add(new BasicNameValuePair("type", "similar"));
		parameters.add(new BasicNameValuePair("arg", ""));
		parameters.add(new BasicNameValuePair("beforeSend", "undefined"));
		parameters.add(new BasicNameValuePair("data", str1+"^^^"+str2));
		String response =  sendPost(requestUrl, parameters);
		
		String numberStr = getJsonData(response);
		System.out.println(response);
		System.out.println(numberStr);
		
		if(numberStr != null){
			d = Double.valueOf(numberStr);
		}
		return d;
	}
	
	public String getJsonData(String original){
		try {
			JSONObject jsonObject = new JSONObject(original);
			JSONArray array = jsonObject.getJSONArray("data");
			String firstString = array.getString(0);
			return firstString.substring(firstString.indexOf(markBegin)+markBegin.length(), firstString.indexOf(markEnd));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
