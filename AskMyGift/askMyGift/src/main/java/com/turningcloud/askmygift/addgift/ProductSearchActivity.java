package com.turningcloud.askmygift.addgift;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.PBnostra13.PBuniversalimageloader.core.ImageLoader;
import com.PBnostra13.PBuniversalimageloader.core.ImageLoaderConfiguration;
import com.PBnostra13.PBuniversalimageloader.core.listener.SimpleImageLoadingListener;
import com.turningcloud.Dashboard.Globals;
import com.turningcloud.askmygift.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ProductSearchActivity extends ActionBarActivity {
    RecyclerView mRecyclerView;
    ArrayList<ProductData> productList = new ArrayList<>();
    ProductRecyclerViewAdapter mAdapter = null;


    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        BitmapDrawable background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
                R.drawable.section_background));

        background.setTileModeX(android.graphics.Shader.TileMode.REPEAT);

        getSupportActionBar().setBackgroundDrawable(background);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_product);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final EditText searchEditText = (EditText) findViewById(R.id.SearchEditText);
        Button searchButton = (Button) findViewById(R.id.SearchButton);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mAdapter.notifyDataSetChanged();
            }
        });





        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchEditText.getText().toString();
                Log.i("query",searchQuery);
                searchQuery = searchQuery.replaceAll("\\s","+");
                Log.i("query",searchQuery);
                if(productList!=null){ for(int i=0;i<productList.size();i++){
                    productList.remove(i);
                }}

                if(mAdapter!=null)
                    mAdapter.removeAll();
                 new JSONParse().execute("https://affiliate-api.flipkart.net/affiliate/search/json?query="+searchQuery+"&resultCount=10");

            }
        });
      /*  Button notify = (Button) findViewById(R.id.notify);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.notifyDataSetChanged();
            }
        }); */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            for(int i=0;i<productList.size();i++){
                productList.remove(i);
            }
            if(mAdapter!=null)
                 mAdapter.removeAll();
            pDialog = new ProgressDialog(ProductSearchActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json = null;
            try {
                URL url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setRequestProperty("Fk-Affiliate-Id", "ashishturn");
                conn.setRequestProperty("Fk-Affiliate-Token", "bc42e21b0ad2401fab2d3f973cc94b34");
                // Starts the query
                conn.connect();
                InputStream stream = conn.getInputStream();

                String data = convertStreamToString(stream);
                json = new JSONObject(data);

                stream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array


                JSONArray sys  = json.getJSONArray("productInfoList");
                for(int i=0;i<sys.length();i++)

                {

               /* sys2 = sys.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");

                latlnStore[i] =  new LatLng(Double.parseDouble(sys2.getString("lat")),Double.parseDouble(sys2.getString("lng")));
                names[i] = sys.getJSONObject(i).getString("name"); */
                    Log.i("JSON_Async", sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productIdentifier").getString("productId"));
                    Log.i("JSON_Async", sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productAttributes").getString("title"));
                    Log.i("JSON_Async", sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productAttributes").getJSONObject("imageUrls").getString("75x75"));
                    Log.i("JSON_Async", sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productAttributes").getString("discountPercentage"));
                    Log.i("JSON_Async", sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productAttributes").getString("productUrl"));
                    final ProductData temp = new ProductData();
                    temp.setProductName(sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productAttributes").getString("title"));
                    temp.setProductID(sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productIdentifier").getString("productId"));
                    temp.setImgUrl(new URL(sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productAttributes").getJSONObject("imageUrls").getString("75x75")));
                    temp.setProductURL(new URL(sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productAttributes").getString("productUrl")));
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.loadImage(temp.getImgUrl().toString(), new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            temp.setProductImageBit(loadedImage);
                        }
                    });
                    temp.setProductDiscount(sys.getJSONObject(i).getJSONObject("productBaseInfo").getJSONObject("productAttributes").getString("discountPercentage"));
                    productList.add(temp);



                }
                ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //Remove swiped item from list and notify the RecyclerView
                        Log.i("Swipe", "Performed");

                        mAdapter.remove(viewHolder.getAdapterPosition());

                        ProductData temp = new ProductData();
                        ImageView img = (ImageView)viewHolder.itemView.findViewById(R.id.product_image);
                        TextView name = (TextView)viewHolder.itemView.findViewById(R.id.product_title);
                        TextView discount = (TextView)viewHolder.itemView.findViewById(R.id.product_discount);
                        temp.setProductImageBit(((BitmapDrawable) img.getDrawable()).getBitmap());
                        temp.setProductName(name.getText().toString());
                        temp.setProductDiscount(discount.getText().toString());
                        Globals.getWishlistData().add(temp);
                        for(int i=0;i<Globals.getWishlistData().size();i++){
                            Log.i("Wishlist",Globals.getWishlistData().get(i).getProductName()+Globals.getWishlistData().get(i).getProductDiscount());
                        }

                }
                };
                mAdapter = new ProductRecyclerViewAdapter(productList);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                itemTouchHelper.attachToRecyclerView(mRecyclerView);






            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



}
