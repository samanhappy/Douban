package com.coosam.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestHttpClient {

	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		HttpClient client = new DefaultHttpClient();
		
		HttpGet get = new HttpGet("https://api.douban.com/v2/book/user/samanhappy/collections");
		
		HttpResponse response = client.execute(get);
		
		System.out.println(response.getStatusLine().getStatusCode());
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		for (String str = reader.readLine(); str != null; str = reader.readLine())
		{
			System.out.println(str);
		}
		
		
	}
}
