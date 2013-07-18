package com.coosam.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coosam.bean.APIResponse;
import com.coosam.bean.BookCollection;
import com.coosam.util.DataUtil;

public class JsonDataGetApi extends WebDataGetApi {
    private static final String BASE_URL = "http://10.0.2.2:82/AccountService/";
    private static final String EXTENSION = "Json/";;

    public JSONObject getObject(String sbj) throws JSONException, Exception {
        return new JSONObject(getRequest(BASE_URL + EXTENSION + sbj));
    }

    public JSONArray getArray(String sbj) throws JSONException, Exception {
        return new JSONArray(getRequest(BASE_URL + EXTENSION + sbj));
    }
    
    public static void main(String[] args)
   	{
       	JSONObject json = null;
   		try
   		{
   			json = new JSONObject(new JsonDataGetApi().getRequest("https://api.douban.com/v2/book/user/samanhappy/collections"));
   		}
   		catch (JSONException e)
   		{
   			e.printStackTrace();
   		}
   		catch (Exception e)
   		{
   			e.printStackTrace();
   		}
   		APIResponse response = (APIResponse) DataUtil.jsonObject2Object(json, APIResponse.class);

   		for (BookCollection bc : response.getCollections())
   		{
   			System.out.println(bc.getBook().getTitle());
   			System.out.println(bc.getBook().getRating().getAverage());
   		}
   		
   	}
}
