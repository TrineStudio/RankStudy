package network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


@SuppressWarnings("deprecation")
public class BaseNetwork implements NetworkUrls{

	private HttpClient httpClient;

	public String sendPost(String url, List<NameValuePair> parameters) {

        if (httpClient == null)
            httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String strResult = EntityUtils.toString(httpResponse.getEntity());

            return strResult;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

	public String sendGet(String url) {

        if (httpClient == null)
            httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String strResult = EntityUtils.toString(httpResponse.getEntity());

            return strResult;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
