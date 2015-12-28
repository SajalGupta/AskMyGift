package com.turningcloud.askmygift;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



import com.askmygift.giftdairy.dao.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser<T> {
	
	private String CLASS_NAME = JsonParser.class.getSimpleName();

	private Logger log = Logger.getLogger(getClass().getName());

	public JsonParser() {
		
	}
	
	public static void main(String args[]) {
		
		User u = new User();
    	u.setUserId("1223445678");
    	
    	JSONArray js = new JSONArray();
    	JsonParser<User> jp = new JsonParser<User>();
    	JSONObject json = jp.convertObjectToJson(u);
    	js.put(json);

        // Step2: Now pass JSON File Data to REST Service
        try {
            URL url = new URL("http://161.202.19.141:8080/AskMyGiftWS/giftdiary/DairyWebservice/getUserDetails/");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(js.toString());
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            /*while (in.readLine() != null) {	
            	System.out.println(in);
            }*/
            
            String line = "";
	        String result = "";
	        while((line = in.readLine()) != null)
	            result += line;
	 
	        in.close();    	        
            System.out.println("\nREST Service Invoked Successfully.."+result);
            
            
    		
        } catch (Exception e) {
            System.out.println("\nError while calling REST Service");
            System.out.println(e);
        }
	}
	
	public T convertJsonToObject(T t, String json) {
		
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e1) {
			log.severe(CLASS_NAME+" - JSONException - "+e1.getMessage());
		}
		
		Field[] tField = t.getClass().getDeclaredFields();

		for (Field field : tField) {
			if(jsonObject.has(field.getName())) {
				try {
					field.set(t, jsonObject.get(field.getName()));
				} catch (IllegalArgumentException e) {
					log.severe(CLASS_NAME+" - IllegalArgumentException - "+e.getMessage());
				} catch (IllegalAccessException e) {
					log.severe(CLASS_NAME+" - IllegalAccessException - "+e.getMessage());
				} catch (JSONException e) {
					log.severe(CLASS_NAME+" - JSONException - "+e.getMessage());
				}
			}
		}
		
		return t;
	}
	
	public JSONObject convertObjectToJson(T t) {
		
		JSONObject jsonObject = new JSONObject();
		
		Field[] tField = t.getClass().getDeclaredFields();

		for (Field field : tField) {
			String val = null;
			try {
				val = (String) field.get(t);
			} catch(IllegalAccessException e) {
				e.printStackTrace();
			}
			if (val != null && !val.isEmpty()) {
				try {
					jsonObject.put(field.getName(), val);
				} catch (JSONException e) {
					log.severe(CLASS_NAME+" - JSONException - "+e.getMessage());
				}
			}
		}

		return jsonObject;
	}
	
	public JSONArray convertListObjectToJson(List<T> objList) {
		
		JSONArray jsonArray = new JSONArray();
		
		for(T t : objList) {
			
			JSONObject jsonObject = new JSONObject();
			Field[] tField = t.getClass().getDeclaredFields();

			for (Field field : tField) {
				String val = null;
				try {
					val = (String) field.get(t);
				} catch(IllegalAccessException e) {
					e.printStackTrace();
				}
				if (val != null && !val.isEmpty()) {
					try {
						jsonObject.put(field.getName(), val);
					} catch (JSONException e) {
						log.severe(CLASS_NAME+" - JSONException - "+e.getMessage());
					}
				}
			}
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
	}
	
	public List<T> convertJsonToListObject(T t, String json) {
		
		List<T> list = new ArrayList<T>();
		
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(json);
		} catch (JSONException e1) {
			log.severe(CLASS_NAME+" - JSONException - "+e1.getMessage());
		}
		
		for(int i=0; i<jsonArray.length(); i++) {
			JSONObject jsonObject = null;
			try {
				jsonObject = jsonArray.getJSONObject(i);
			} catch (JSONException e1) {
				log.severe(CLASS_NAME+" - JSONException - "+e1.getMessage());
			}
			
			Field[] tField = t.getClass().getDeclaredFields();

			for (Field field : tField) {
				if(jsonObject.has(field.getName())) {
					try {
						field.set(t, jsonObject.get(field.getName()));
					} catch (IllegalArgumentException e) {
						log.severe(CLASS_NAME+" - IllegalArgumentException - "+e.getMessage());
					} catch (IllegalAccessException e) {
						log.severe(CLASS_NAME+" - IllegalAccessException - "+e.getMessage());
					} catch (JSONException e) {
						log.severe(CLASS_NAME+" - JSONException - "+e.getMessage());
					}
				}
			}
			list.add(t);
		}
		
		return list;
	}
}
