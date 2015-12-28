package com.turningcloud.drawer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.askmygift.giftdairy.constant.QueryMetadata;
import com.askmygift.giftdairy.dao.entity.Address;
import com.askmygift.giftdairy.dao.entity.Preferences;
import com.askmygift.giftdairy.dao.entity.User;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.turningcloud.askmygift.R;


public class UpdateAccount extends Fragment implements OnClickListener{ 

	 String id=null, personPhotoUrl=null,gender=null,encodedImage;
	    private static final int PROFILE_PIC_SIZE = 100;
	EditText mEdit,fullName,dob,addressLine1,addressLine2,district,pinCode;
	Button updateButton;
	public static String url="http://4-dot-askmygiftweb.appspot.com/rest/UserWebservice/UserServices/";
	JSONObject jsonObject,jsonObject2,jsonObject3;
	 ImageView imageView;
	 String userid,addressId,preferenceId;
		   int REQUEST_CAMERA = 1, SELECT_FILE = 2;
		   
		  
	  RoundImage roundImage;
	  SharedPreferences shared;
	  SharedPreferences.Editor editor;
	  private RadioGroup radioSexGroup;
	    private RadioButton radioButton,radioMale,radioFemale;
	   public UpdateAccount() {  
	   }  
	   
	   @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	       View rootView = inflater.inflate(R.layout.update_account, container, false);  
	       shared = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
	       
	       fullName=(EditText) rootView.findViewById(R.id.fullname);
	       dob=(EditText) rootView.findViewById(R.id.dateOfBirth);
	       addressLine1=(EditText) rootView.findViewById(R.id.AddLine1);
	       addressLine2=(EditText) rootView.findViewById(R.id.AddLine2);
	       district=(EditText) rootView.findViewById(R.id.dstrict);
	       pinCode=(EditText) rootView.findViewById(R.id.pincode);
	       updateButton=(Button) rootView.findViewById(R.id.UpdateButton);
	     imageView=(ImageView) rootView.findViewById(R.id.viewImage);
	       
	       userid=shared.getString("Userid", "");
	       addressId=shared.getString("Address", "");
	       preferenceId=shared.getString("Preference", "");
	       
	       fullName.setText(shared.getString("FullName", ""));
			dob.setText(shared.getString("DOB", ""));
			addressLine1.setText(shared.getString("ADDRESS1", ""));
			addressLine2.setText(shared.getString("ADDRESS2", ""));					
			district.setText(shared.getString("DISTRICT", ""));	
			pinCode.setText(shared.getString("PINCODE", ""));
			 
			 
	        gender=shared.getString("GENDER", "");
	        Log.d("gender", gender);
	        id=shared.getString("FbID", "");
	        personPhotoUrl=shared.getString("GProfilePic", "");
	        if(id.length()!=0 || personPhotoUrl.length()!=0)
	        displayProfileImage();
	       
	      
	    	     imageView.setOnClickListener(this);
	    	     
	     mEdit = (EditText)rootView. findViewById(R.id.dateOfBirth);
	      mEdit.setInputType(InputType.TYPE_NULL);
	      mEdit.requestFocus();
	      mEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 DialogFragment newFragment = new SelectDateFragment();
		            newFragment.show(getActivity().getFragmentManager(), "DatePicker");
			}
		});
	      
	      radioSexGroup = (RadioGroup)rootView.findViewById(R.id.radioSex);
	      radioMale= (RadioButton)rootView.findViewById(R.id.radioMale);
	       radioFemale= (RadioButton)rootView.findViewById(R.id.radioFemale);
	   if(gender!=null)
	      {
	       if(gender.equalsIgnoreCase("male")){
	    	   radioMale.setChecked(true);
	    	   radioFemale.setChecked(false);
	       }
	       else 
	       {
	    	   radioFemale.setChecked(true);
	    	   radioMale.setChecked(false);
	    	   
	       }
	      }
	       int selectedId = radioSexGroup.getCheckedRadioButtonId();
	       radioButton=(RadioButton)rootView.findViewById(selectedId);
	       
	       updateButton.setOnClickListener(this);
	       return rootView;  
	   }  
	       
	  
	private void displayProfileImage() {


			if(id.length()>0)
			{
			ImageLoader imageLoader = ImageLoader.getInstance();
			if (!imageLoader.isInited()) {
				imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
			}

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.blank)
					.showImageForEmptyUri(R.drawable.blank)
					.showImageOnFail(R.drawable.blank).cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
			
			{
			imageLoader.displayImage("https://graph.facebook.com/" + id+ "/picture?type=large", imageView, options,new SimpleImageLoadingListener() 
			{
			
				@Override
						public void onLoadingStarted(String imageUri, View view) {
						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
						}

						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
						}
					}, new ImageLoadingProgressListener() {
						@Override
						public void onProgressUpdate(String imageUri, View view,
								int current, int total) {
						}
					});
			}
		}
			else
			{
				personPhotoUrl = personPhotoUrl.substring(0,personPhotoUrl.length() - 2)+ PROFILE_PIC_SIZE;
					new LoadProfileImage(imageView).execute(personPhotoUrl);
				
			}
			
		
		
	}

	   
	public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	        @Override
	        public Dialog onCreateDialog(Bundle savedInstanceState) {
	        final Calendar calendar = Calendar.getInstance();
	        int yy = calendar.get(Calendar.YEAR);
	        int mm = calendar.get(Calendar.MONTH);
	        int dd = calendar.get(Calendar.DAY_OF_MONTH);
	        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
	        }

	        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
	            populateSetDate(yy, mm+1, dd);
	        }
	        public void populateSetDate(int year, int month, int day) {
	        	mEdit.setText(month+"/"+day+"/"+year);
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

	
	    @Override
	        public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	
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
			
			imageView.setImageBitmap(thumbnail);
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
			imageView.setImageBitmap(bm);
			if(bm!=null)
			{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] imageBytes = baos.toByteArray();
			encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.viewImage:
				selectImage();
				break;

				case R.id.UpdateButton:
					
					
					
					if(isConnected()){
						
						
						
					new UpdateAsyntask().execute(url);
					
					}
					 else
				        {
				            Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
				        }
				break;
				
			default:
				break;
			}
			
			
		}
	
		private   boolean isConnected() {
			 
			
    		ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;  
	}

		private class UpdateAsyntask extends AsyncTask<String, Void, String>{

	    	
	    	Context context;
	    	private ProgressDialog dialog = new ProgressDialog(getActivity());
	    	@Override
	    	protected void onPreExecute() {

	    		this.dialog.setMessage("Please wait....");
	            this.dialog.show();
	            
	            editor = shared.edit();
		  		editor.putString("FullName", fullName.getText().toString());	
		  		editor.putString("DOB",dob.getText().toString());
		  		editor.putString("ADDRESS1",addressLine1.getText().toString());
		  		editor.putString("ADDRESS2",addressLine2.getText().toString());
		  		editor.putString("DISTRICT",district.getText().toString());
		  		editor.putString("PINCODE",pinCode.getText().toString());
		  		editor.putString("GENDER", radioButton.getText().toString());
		  //		editor.putString("IMAGE", encodedImage);
			editor.commit();
	    	}

	    	
			@Override
	    	protected String doInBackground(String... urls) {
	    		
	    		User user=new User();
	    		user.setFullName(fullName.getText().toString());
	    		user.setDateOfBirth(dob.getText().toString());
	       		user.setGender(radioButton.getText().toString());
	    		user.setImageUrl(encodedImage);
	    		user.setUserId(userid);
	       		user.setMethodName(String.valueOf(QueryMetadata.userWebServiceType.UPDATE_USER_ACCOUNT));
	       		
	       			Gson gson = new Gson();
	       			
	       	 String userData = gson.toJson(user);
			try 
			{
				jsonObject = new JSONObject(userData);
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}

			Address address=new Address();
			address.setAddressLine1(addressLine1.getText().toString());
			address.setAddressLine2(addressLine2.getText().toString());
			address.setDistrict(district.getText().toString());
			address.setPostalCode(pinCode.getText().toString());
			address.setAddressId(addressId);
			 String addressData = gson.toJson(address);
		
			
			 
				 try {
					 
					jsonObject2 = new JSONObject(addressData);
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				 
				 Preferences preferences=new Preferences();
				
					
					 String preferenceData = gson.toJson(preferences);
				
					
					 
						 try {
							 
							jsonObject3 = new JSONObject(preferenceData);
							
						} catch (JSONException e) {
							
							e.printStackTrace();
						}
				
		
			 JSONArray arr = new JSONArray();

	         arr.put(jsonObject);
	         arr.put(jsonObject2);
	         arr.put(jsonObject3);
	         return POST(urls[0],arr);
	    		
	    	}
	    	
	    	

			private String POST(String string, JSONArray arr)  {
				InputStream inputStream = null;
		        String result = "";
		        try {
		 
		            // 1. create HttpClient
		            HttpClient httpclient = new DefaultHttpClient();
		 
		            // 2. make POST request to the given URL
		            HttpPost httpPost = new HttpPost(url);
		 
		            String json = "";
		 
		          
		            json = arr.toString();
		 
		          
		            StringEntity se = new StringEntity(json);
		 
		           
		            httpPost.setEntity(se);
		 
		          
		            httpPost.setHeader("Accept", "application/json");
		            httpPost.setHeader("Content-type", "application/json");
		 
		           
		            HttpResponse httpResponse = httpclient.execute(httpPost);
		 
		         
		            inputStream = httpResponse.getEntity().getContent();
		 
		            // 10. convert inputstream to string
		            if(inputStream != null){
		                result = convertInputStreamToString(inputStream);
		                
		                Log.d("Resposnes cskkk", result);
		               
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
				
			
				Toast.makeText(getActivity(), "Data has been successfully updated", Toast.LENGTH_SHORT).show();	
				
				if (dialog.isShowing()) {
					
		            dialog.dismiss();
		        }
	    	
	    	}

	    
		}
		
		
		private  class  LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
		    ImageView bmImage;
		 
		    public LoadProfileImage(ImageView bmImage) {
		        this.bmImage = bmImage;
		    }
		 
		    protected Bitmap doInBackground(String... urls) {
		        String urldisplay = urls[0];
		        Bitmap mIcon11 = null;
		        try {
		            InputStream in = new java.net.URL(urldisplay).openStream();
		            mIcon11 = BitmapFactory.decodeStream(in);
		        } catch (Exception e) {
		            Log.e("Error", e.getMessage());
		            e.printStackTrace();
		        }
		        return mIcon11;
		    }
		 
		    protected void onPostExecute(Bitmap result) {
		        bmImage.setImageBitmap(result);
		    }
		}
	 
		
	        }

	  