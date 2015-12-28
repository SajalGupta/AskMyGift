package com.turningcloud.drawer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.askmygift.giftdairy.constant.QueryMetadata;
import com.askmygift.giftdairy.dao.entity.Event;
import com.askmygift.giftdairy.dao.entity.Merchant;
import com.askmygift.giftdairy.dao.entity.Product;
import com.askmygift.giftdairy.dao.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.turningcloud.askmygift.R;



public class AddWishListGift extends Fragment implements OnClickListener{  

	Merchant merchant;
	Event event;
	String eventId=null, EventSpinnerItem,spinnerId,merchantId,MerchantSpinnerItem;
	  List<Event> events;
	  List<Merchant> listMerchants;
	
	JSONObject object;
	
  
	
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	String contents,format;
	JSONObject jsonObject,jsonObject2;
	String userId,scanCodeDetails;
	 Spinner spinnerEvent,spinnerMerchant;
	SharedPreferences shared;
	   Button scanCode,selectPic, addGifts;
	   EditText  editTitle,editDescription,editAmount,editMerchant,editUrl,editEvent;
	   ImageView giftImage;
	   int REQUEST_CAMERA = 1, SELECT_FILE = 2;
	  RoundImage roundImage;
	  public  static String url="http://4-dot-askmygiftweb.appspot.com/rest/DairyWebservice/DairyServices/";
	  public  static String merchanturl="http://4-dot-askmygiftweb.appspot.com/rest/MerchantWebservice/MerchantServices/";
	  ArrayList<String> list=null;
	  ArrayAdapter<String> adapter;
	  String encodedImage;
	  Bitmap bm;
	   public AddWishListGift() {  
	   }  

	   @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	       View rootView = inflater.inflate(R.layout.add_wish_list_someone, container, false);  
	      
	       
	       shared = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
	        userId=shared.getString("Userid", "");
	        
	        
	       giftImage=(ImageView) rootView.findViewById(R.id.viewImage);
	       editTitle=(EditText) rootView.findViewById(R.id.title);
	       editDescription=(EditText) rootView.findViewById(R.id.description);
	       editAmount=(EditText) rootView.findViewById(R.id.amount);
	       
	       spinnerEvent=(Spinner) rootView.findViewById(R.id.spinnerEvents);
	       
	       new SpinnerEventAsyntask().execute(url);
	       
	       spinnerMerchant=(Spinner) rootView.findViewById(R.id.spinnerMerchants);
	         
	       new SpinnerMerchantAsyntask().execute(merchanturl);
	      
	       editUrl=(EditText) rootView.findViewById(R.id.url);
	       scanCode=(Button) rootView.findViewById(R.id.scanBarCode);
	       selectPic=(Button) rootView.findViewById(R.id.selectPhoto);
	       addGifts=(Button) rootView.findViewById(R.id.Addgifts);
	       scanCode.setOnClickListener(this);
	       selectPic.setOnClickListener(this);
	       addGifts.setOnClickListener(this);
	       return rootView;  
	   }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scanBarCode:
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); 
			builder.setMessage("Select Scanner").setCancelable(true)
			.setPositiveButton("BarCode", new DialogInterface.OnClickListener() {  
                public void onClick(DialogInterface dialog, int id) {  
                
                	try {
			            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
			
			            Intent intent = new Intent(ACTION_SCAN);
			            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			            startActivityForResult(intent, 0);
			        } catch (ActivityNotFoundException anfe) {
			            //on catch, show the download dialog
			            showDialog(getActivity(), "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
			        }
                }  
            })  
			.setNegativeButton("QRCode", new DialogInterface.OnClickListener() {  
                public void onClick(DialogInterface dialog, int id) {  
                	try {
                		
                		            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                	                		            Intent intent = new Intent(ACTION_SCAN);
                	                		            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                		                		            startActivityForResult(intent, 0);
                	                		        } catch (ActivityNotFoundException anfe) {
                		
                		            //on catch, show the download dialog
                	                		            showDialog(getActivity(), "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                		
                		        }

             }  
            });  
			AlertDialog alert = builder.create();  
	        //Setting the title manually  
	        alert.setTitle("Select Scanner- Qr Scanner And Bar Scanner");  
	        alert.show();  
			
			break;
			
			
			case R.id.selectPhoto:
				
				selectImage();
				
				break;
				
			case R.id.Addgifts:
				
				new AddProductAsyntask().execute(url);
				break;
				
				
			
				
				
		default:
			break;
		}
		
	}

	

	

	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Gallery",
		"Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, REQUEST_CAMERA);
				} else if (items[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	//alert dialog for downloadDialog
	    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
	        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
	        downloadDialog.setTitle(title);
	        downloadDialog.setMessage(message);
	        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialogInterface, int i) {
	                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
	                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	                try {
	                    act.startActivity(intent);
	                } catch (ActivityNotFoundException anfe) {
	                }
	            }
	        });
	        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialogInterface, int i) {
	            }
	        });
	        return downloadDialog.show();
	}  
	    
		@Override
	        public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	
	    	if ( requestCode== 0) {
	           if(resultCode==Activity.RESULT_OK){
	        	   
	                    contents = data.getStringExtra("SCAN_RESULT");
	                   format = data.getStringExtra("SCAN_RESULT_FORMAT");
	                    
	                    Toast toast =    Toast.makeText(getActivity(), "Contents"+contents+" "+"Formats"+format, Toast.LENGTH_LONG);
	                    toast.setGravity(Gravity.TOP, 25, 400);
		                toast.show();
	                   
	                     
	                  
	           }
	           else if (resultCode == Activity.RESULT_CANCELED) {
	                Toast toast = Toast.makeText(getActivity(), "Scan was Cancelled!", Toast.LENGTH_LONG);
	                toast.setGravity(Gravity.TOP, 25, 400);
	                toast.show();
	                
	            }
	           
	                }
	           if (resultCode == Activity.RESULT_OK) {
	   			if (requestCode == SELECT_FILE)
	   				onSelectFromGalleryResult(data);
	   			else if (requestCode == REQUEST_CAMERA)
	   				onCaptureImageResult(data);
	   		}
	    	
	    
	   
	            }

	    private void onCaptureImageResult(Intent data) {
			Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

			File destination = new File(Environment.getExternalStorageDirectory(),
					System.currentTimeMillis() + ".jpg");

			FileOutputStream fo;
			try {
				destination.createNewFile();
				fo = new FileOutputStream(destination);
				fo.write(bytes.toByteArray());
				fo.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			giftImage.setImageBitmap(thumbnail);
			if(thumbnail!=null){
			byte[] imageBytes = bytes.toByteArray();
			encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
			}
		}

	 
		private void onSelectFromGalleryResult(Intent data) {
			Uri selectedImageUri = data.getData();
			String[] projection = { MediaColumns.DATA };
			@SuppressWarnings("deprecation")
			Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,null);
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			cursor.moveToFirst();

			String selectedImagePath = cursor.getString(column_index);

			Bitmap bm;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(selectedImagePath, options);
			final int REQUIRED_SIZE = 200;
			int scale = 1;
			while (options.outWidth / scale / 2 >= REQUIRED_SIZE
					&& options.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;
			options.inSampleSize = scale;
			options.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(selectedImagePath, options);
			
			giftImage.setImageBitmap(bm);
			
			if(bm!=null)
			{
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] imageBytes = baos.toByteArray();
				encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
			}
		}


	    private class AddProductAsyntask extends AsyncTask<String, Void, String>{
	    	
	    	
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
	    		user.setMethodName(String.valueOf(QueryMetadata.dairyWebServiceType.ADD_PRODUCT));
	       			Gson gson = new Gson();
	       			
	       	 String userData = gson.toJson(user);
			
				jsonObject = new JSONObject(userData);

			
				Product product=new Product();
				product.setProductName(editTitle.getText().toString());
				product.setProductDesc(editDescription.getText().toString());
				product.setProductCost(editAmount.getText().toString());
				product.setProductUrl(editUrl.getText().toString());
				product.setProductCode(format+"/"+contents);
				product.setEventId(spinnerId);
				product.setMerchantId(merchantId);
				product.setProductImageUrl(encodedImage);
				
				
				 String productData = gson.toJson(product);
				
				jsonObject2=new JSONObject(productData);
				
					
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
		                
		           Log.d("Add wish list list  response", result);
		                
		              /*  object = new JSONObject(result);
		                
		                genericData=object.get("datag").toString();
		                
		                Gson gs = new Gson();
		                
		               event = gs.fromJson((String) object.get("datag"), new TypeToken<Event>() {

		                }.getType());
		                
		                 events=event.getDataList();
		                 
		                for (Event event2 : events) {
		                	 Log.d("Event name "+event2.getEventId(),event2.getEventName());
		                	 
		                }	
						*/
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
				
			Toast.makeText(getActivity(), "Gift SuccessFully added", Toast.LENGTH_LONG).show();
				
				if (dialog.isShowing()) {
				
		            dialog.dismiss();
		        }
	    	
	    	}

	    }
	    
	    private class SpinnerEventAsyntask extends AsyncTask<String, Void, String>{
	    	
	    	
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
	    		user.setMethodName(String.valueOf(QueryMetadata.dairyWebServiceType.GET_EVENT_LIST));
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
		                
		           Log.d("Generic Event list  response", result);
		                
		                object = new JSONObject(result);
		                
		              
		                
		                Gson gs = new Gson();
		                
		               event = gs.fromJson((String) object.get("data"), new TypeToken<Event>() {

		                }.getType());
		                
		                 events=event.getDataList();
		                 
		              
						
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
				 for (Event event2 : events) {
	            	list.add(event2.getEventName());
	            	list1.add(event2.getEventId());
	            }	
				
				  adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
			      
				 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				 
				       spinnerEvent.setAdapter(adapter);
				       
				       spinnerEvent.setOnItemSelectedListener(new  OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							spinnerId=list1.get(position);
							EventSpinnerItem=parent.getItemAtPosition(position).toString();
						//	Toast.makeText(getActivity(), EventSpinnerItem, Toast.LENGTH_LONG).show();
							
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
					});
				
				   //   EventSpinnerItem= String.valueOf(spinnerEvent.getSelectedItem());
				    
				
				if (dialog.isShowing()) {
				
		            dialog.dismiss();
		        }
	    	
	    	}

	    }

 private class SpinnerMerchantAsyntask extends AsyncTask<String, Void, String>{
	    	
	    	
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
	    		user.setMethodName(String.valueOf(QueryMetadata.merchantWebServiceType.GET_MERCHANT_LIST));
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
		            HttpPost httpPost = new HttpPost(merchanturl);
		 
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
		                
		           Log.d("Merchant list response", result);
		                
		                object = new JSONObject(result);
		                
		              
		                
		                Gson gs = new Gson();
		                
		                merchant = gs.fromJson((String) object.get("data"), new TypeToken<Merchant>() {

		                }.getType());
		                
		                listMerchants=merchant.getDataList();
		                 
		              
						
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
				 for (Merchant merchant2 : listMerchants) {
	            	list.add(merchant2.getMerchantName());
	            	list1.add(merchant2.getMerchantId());
	            }	
				
				 adapter = new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_spinner_item, list);
			      
				 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				 
				       spinnerMerchant.setAdapter(adapter);
				       
				       spinnerMerchant.setOnItemSelectedListener(new  OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							
							merchantId=list1.get(position);
							MerchantSpinnerItem=parent.getItemAtPosition(position).toString();
						//	Toast.makeText(getActivity(),MerchantSpinnerItem, Toast.LENGTH_LONG).show();
							
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
					});
				
				   //   EventSpinnerItem= String.valueOf(spinnerEvent.getSelectedItem());
				    
				
				if (dialog.isShowing()) {
				
		            dialog.dismiss();
		        }
	    	
	    	}

	    }

	  
	    
		
		
	        }

	  