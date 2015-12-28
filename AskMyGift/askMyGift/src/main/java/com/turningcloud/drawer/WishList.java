package com.turningcloud.drawer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.askmygift.giftdairy.constant.QueryMetadata;
import com.askmygift.giftdairy.dao.entity.Event;
import com.askmygift.giftdairy.dao.entity.Product;
import com.askmygift.giftdairy.dao.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.turningcloud.askmygift.R;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;


public class WishList extends Fragment {
    
	ListView listView;
	JSONObject jsonObject,jsonObject2,object;
	public  static String url="http://4-dot-askmygiftweb.appspot.com/rest/DairyWebservice/DairyServices/";
	SharedPreferences shared;
	 String userId;
	 List<Product> listProducts;
	 Product product=null;
	String productId;
		ArrayAdapter< String> adapter;
		 ArrayList<String> list=null;
		 
		 EditText edittext;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wishlist,container,false);
        
        listView = ( ListView ) v.findViewById(R.id.listview);
       
        shared = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
       userId=shared.getString("Userid", "");
        
       
      // new WishListAsyntask().execute(url);
        
        return v;
    }
    
private class WishListAsyntask extends AsyncTask<String, Void, String>{
    	
    	
    	private ProgressDialog dialog = new ProgressDialog(getActivity());
    	@Override
    	protected void onPreExecute() {

    		this.dialog.setMessage("Please wait.....");
            this.dialog.show();
    	}

    	
		@Override
    	protected String doInBackground(String... urls) {
			 try 
			 {
				 
    		User user=new User();
    		user.setUserId(userId);
    		user.setMethodName(String.valueOf(QueryMetadata.dairyWebServiceType.USER_PRODUCT_LIST));
       			Gson gson = new Gson();
       			
       	 String userData = gson.toJson(user);
		
			jsonObject = new JSONObject(userData);

		
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
	
		 JSONArray arr = new JSONArray();

         arr.put(jsonObject);

        
         
         return POST(urls[0],arr);
    		
    	}

		private String POST(String string, JSONArray arr)  {
			InputStream inputStream = null;
	        String result = "";
	        try {
	        	
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httpPost = new HttpPost(url);
	 
	            String json = "";
	 
	            json = arr.toString();
	            StringEntity se = new StringEntity(json);
	            httpPost.setEntity(se);
	            httpPost.setHeader("Accept", "application/json");
	            httpPost.setHeader("Content-type", "application/json");
	         	 HttpResponse httpResponse = httpclient.execute(httpPost);
	            inputStream = httpResponse.getEntity().getContent();
	 
	            if(inputStream != null){
	                result = convertInputStreamToString(inputStream);
	                
	           Log.d("Wish list  response", result);
	                
	           object = new JSONObject(result);
	           
	           Gson gs = new Gson();
               
               product= gs.fromJson((String) object.get("data"), new TypeToken<Product>() {

                }.getType());
                
               listProducts=product.getDataList();
                 
                for (Product product2 : listProducts) {
                	
                	 Log.d("Product name "+product2.getProductId(),product2.getProductName());
                	 
                }	
	           
	           
					
	            }
	            
	            else
	                result = "Did not work!";
	            
	          
	        } catch (Exception e) {
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
	 
	      
	        
	        
	        // 11. return result
	        return result;
	    }

		private  String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	 
	        inputStream.close();
	        return result;
	 
	    }   


		@Override
    	protected void onPostExecute(String result) {
			
			 list = new ArrayList<String>();
			 final List<String> list1 = new ArrayList<String>();
			 for (Product product2 : listProducts) {
            	list.add(product2.getProductName());
            	list1.add(product2.getProductId());
            }	
			
			  adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
		      
		       
			       listView.setAdapter(adapter);
			
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view,  final int position, long id) {
					 AlertDialog.Builder showPlace = new AlertDialog.Builder(getActivity());
			            showPlace.setMessage("Delete Events from Generic Event?");
			            showPlace.setPositiveButton("DELETE", new OnClickListener() {
			                public void onClick(DialogInterface dialog, int which) {    
			                	
			                	productId = list1.get(position);
			                		list.remove(position);
			                		
			                		new DeleteAsytask().execute(url);
			                	 
			                	adapter.notifyDataSetChanged();
			                    adapter.notifyDataSetInvalidated();

			                }

			            });
			            showPlace.setNegativeButton("UPDATE", new OnClickListener() {
			                public void onClick(DialogInterface dialog, int which) {

			                	AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

			                	edittext= new EditText(getActivity());
			                    alert.setMessage("Add New Gift item");
			                    alert.setTitle("Update gift items");

			                    alert.setView(edittext);

			                    alert.setPositiveButton("Add ", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int whichButton) {
			                         //What ever you want to do with the value
			                    
			                      String YouEditTextValue = edittext.getText().toString();
			                      
			                      productId = list1.get(position);
			                      
			                      adapter.remove(adapter.getItem(position));     
			                      adapter.insert(YouEditTextValue, position);
			                      
			                      new UpdateAsytask().execute(url);
			                    
			                      adapter.notifyDataSetChanged();

			                      }
			                    });

			                    alert.setNegativeButton("No ", new DialogInterface.OnClickListener() {
			                      public void onClick(DialogInterface dialog, int whichButton) {
			                        // what ever you want to do with No option.
			                      }
			                    });

			                    alert.show();
			                	
			                }
			            });
			            showPlace.show();
			        

			
			      
					return false;
				}
			});
		

				
		           
			
			if (dialog.isShowing()) {
			
	            dialog.dismiss();
	        }
    	
    	}

    }
    

private class DeleteAsytask extends AsyncTask<String, Void, String>{
	
	Context context;
	private ProgressDialog dialog = new ProgressDialog(getActivity());
	@Override
	protected void onPreExecute() {

		this.dialog.setMessage("Please wait");
        this.dialog.show();
	}

	
	@Override
	protected String doInBackground(String... urls) {
		 try 
		 {
			 
		User user=new User();
		user.setUserId(userId);
		user.setMethodName(String.valueOf(QueryMetadata.dairyWebServiceType.DELETE_PRODUCT));
   			Gson gson = new Gson();
   			
   	 String userData = gson.toJson(user);
	
		jsonObject = new JSONObject(userData);

	product = new Product();
	product.setProductId(productId);
	 
	 String eventsData = gson.toJson(product);

			jsonObject2 = new JSONObject(eventsData);
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}

	 JSONArray arr = new JSONArray();

     arr.put(jsonObject);

     arr.put(jsonObject2);
     
     return POST(urls[0],arr);
		
	}

	private String POST(String string, JSONArray arr)  {
		InputStream inputStream = null;
        String result = "";
        try {
        	
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
 
            String json = "";
 
            json = arr.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
         	 HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
 
            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
                
            
                
            }
            
            else
                result = "Did not work!";
            
          
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

	private  String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }   


	@Override
	protected void onPostExecute(String result) {
		
	Toast.makeText(getActivity(), "DataDeleted successfully", Toast.LENGTH_SHORT).show();
	
		if (dialog.isShowing()) {
		
            dialog.dismiss();
        }
	
	}

}


private class UpdateAsytask extends AsyncTask<String, Void, String>{
	
	Context context;
	private ProgressDialog dialog = new ProgressDialog(getActivity());
	@Override
	protected void onPreExecute() {

		this.dialog.setMessage("Please wait");
        this.dialog.show();
	}

	
	@Override
	protected String doInBackground(String... urls) {
		 try 
		 {
			 
		User user=new User();
		user.setUserId(userId);
		user.setMethodName(String.valueOf(QueryMetadata.dairyWebServiceType.UPDATE_PRODUCT));
   			Gson gson = new Gson();
   			
   	 String userData = gson.toJson(user);
	
		jsonObject = new JSONObject(userData);

	product = new Product();
	product.setProductId(productId);
	 
	 String eventsData = gson.toJson(product);

			jsonObject2 = new JSONObject(eventsData);
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}

	 JSONArray arr = new JSONArray();

     arr.put(jsonObject);

     arr.put(jsonObject2);
     
     return POST(urls[0],arr);
		
	}

	private String POST(String string, JSONArray arr)  {
		InputStream inputStream = null;
        String result = "";
        try {
        	
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
 
            String json = "";
 
            json = arr.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
         	 HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
 
            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
                
            
                
            }
            
            else
                result = "Did not work!";
            
          
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

	private  String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }   


	@Override
	protected void onPostExecute(String result) {
		
	Toast.makeText(getActivity(), "DATA updated successfully", Toast.LENGTH_SHORT).show();
	
		if (dialog.isShowing()) {
		
            dialog.dismiss();
        }
	
	}

}


}