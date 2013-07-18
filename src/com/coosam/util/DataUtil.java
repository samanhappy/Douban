package com.coosam.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.coosam.bean.APIResponse;
import com.coosam.bean.BookCollection;

public class DataUtil
{

	private static final String SETTER_METHOD_PREFIX = "set";

	private static final Locale locale = Locale.CHINA;

	/**
	 * 
	 * @param fieldNames
	 * @param values
	 * @param clazz
	 * @return
	 */
	public static Object jsonObject2Object(JSONObject json, Class<?> clazz)
	{
		Object object;
		try
		{
			object = clazz.newInstance();
			for (Field field : clazz.getDeclaredFields())
			{
				String fieldName = field.getName();
				Class<?> fieldClass = field.getType();
				Method setterMethod = getSetterMethod(clazz, fieldName, field);
				if (setterMethod != null)
				{
					Object value = json.get(fieldName);
					if (fieldClass.isPrimitive() || fieldClass.equals(String.class))
					{

					}
					else if (fieldClass.equals(Date.class))
					{
						value = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale).parse((String) value);
					}
					else if (fieldClass.equals(List.class))
					{
						value = jsonArray2List((JSONArray) value, getClassByName(fieldName));
					}
					else
					{
						value = jsonObject2Object((JSONObject) value, fieldClass);
					}

					setterMethod.invoke(object, value);
				}
			}
			return object;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param fieldNames
	 * @param values
	 * @param clazz
	 * @return
	 */
	public static List<Object> jsonArray2List(JSONArray array, Class<?> clazz)
	{
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++)
		{
			try
			{
				list.add(jsonObject2Object(array.getJSONObject(i), clazz));
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		return list;
	}

	public static Class<?> getClassByName(String name)
	{
		if (name.equals("collections"))
		{
			return BookCollection.class;
		}
		return null;
	}

	/**
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param field
	 * @return
	 */
	private static Method getSetterMethod(Class<?> clazz, String fieldName, Field field)
	{
		try
		{
			return clazz.getDeclaredMethod(getSetterMethodName(fieldName), field.getType());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param fieldName
	 * @return
	 */
	private static String getSetterMethodName(String fieldName)
	{
		String methodName = SETTER_METHOD_PREFIX + fieldName.substring(0, 1).toUpperCase(locale) + fieldName.substring(1);
		return methodName;
	}

	public static void main(String[] args) throws JSONException
	{
		JSONObject json = new JSONObject("{\"count\":20,\"start\":0,\"total\":117,\"collections\":[{\"status\":\"wish\",\"updated\":\"2013-07-12 16:25:47\",\"user_id\":\"43963667\",\"book\":{\"rating\":{\"max\":10,\"numRaters\":646,\"average\":\"8.7\",\"min\":0},\"image\":\"http://img3.douban.com/mpic/s6089770.jpg\",\"title\":\"冬吴相对论\",\"url\":\"http://api.douban.com/v2/book/4124834\"},\"book_id\":\"4124834\",\"id\":703342186}]}");
		APIResponse response = (APIResponse) DataUtil.jsonObject2Object(json, APIResponse.class);

		System.out.println(response.getCount());
		System.out.println(response.getCollections().get(0).getBook().getImage());
	}
}
