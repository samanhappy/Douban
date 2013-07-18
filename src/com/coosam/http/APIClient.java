package com.coosam.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class APIClient {

	private static final String TAG = "apiClient";

	private HttpClient client;

	public APIClient() {
		client = new DefaultHttpClient();
	}

	public String getHttpContent(String url) {
		StringBuffer content = new StringBuffer();
		HttpGet get = new HttpGet(
				"https://api.douban.com/v2/book/user/samanhappy/collections");

		HttpResponse response = null;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));
				for (String str = reader.readLine(); str != null; str = reader
						.readLine()) {
					content.append(str);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.d(TAG, "content got from url:" + url + " is " + content.toString());
		return content.toString();
	}
}
