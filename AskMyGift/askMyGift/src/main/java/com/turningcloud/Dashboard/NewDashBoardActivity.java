package com.turningcloud.Dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.PBnostra13.PBuniversalimageloader.core.ImageLoader;
import com.PBnostra13.PBuniversalimageloader.core.ImageLoaderConfiguration;
import com.PBnostra13.PBuniversalimageloader.core.listener.SimpleImageLoadingListener;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.turningcloud.askmygift.friendlist.FriendData;
import com.turningcloud.askmygift.friendlist.FriendListActivity;
import com.turningcloud.askmygift.addgift.ProductSearchActivity;
import com.turningcloud.askmygift.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewDashBoardActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> friendIdList = new ArrayList<>();

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash);
        Globals.setGlobalContext(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        list.add("IPhone 6");
        list.add("Play Station");
        list.add("Jaguar");
        list.add("MacBook pro");
        Globals.setGlobalWishList(list);
        mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BitmapDrawable icon = new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
                R.drawable.drawer));


        getSupportActionBar().setHomeAsUpIndicator(icon);

        BitmapDrawable background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
                R.drawable.section_background));

        background.setTileModeX(android.graphics.Shader.TileMode.REPEAT);
        getSupportActionBar().setBackgroundDrawable(background);




        //ActionBar myCustomActions= getSupportActionBar();
        //myCustomActions.setCustomView(R.layout.action_bar);
        //to disable title in actionbar
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportedActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(248, 248, 248)));

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        //   new JSONParse().execute("https://affiliate-api.flipkart.net/affiliate/search/json?query=iphone&resultCount=5");

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        // fragmentManager.beginTransaction()
        // .replace(R.id.navigation_drawer, PlaceholderFragment.newInstance(position + 1))
        // .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        for(int i=0;i<Globals.getFriendList().size();i++)
            Globals.getFriendList().remove(i);
        final ImageLoader imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        final AccessToken mAccessToken = AccessToken.getCurrentAccessToken();
        Log.i("Token", mAccessToken.getUserId());
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + mAccessToken.getUserId() + "/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {

                    public void onCompleted(GraphResponse response) {
                        Log.i("Friends", response.toString());
                        Log.i("Ex", response.getJSONObject().toString());
                        JSONObject data = response.getJSONObject();
                        try {
                            JSONArray sys = data.getJSONArray("data");
                            for (int i = 0; i < sys.length(); i++) {
                                FriendData temp = new FriendData();
                                Log.i("Name :", sys.getJSONObject(i).getString("name"));
                                Log.i("ID :", sys.getJSONObject(i).getString("id"));
                                temp.setFacebookID(sys.getJSONObject(i).getString("id"));
                                temp.setFriendName(sys.getJSONObject(i).getString("name"));
                                Globals.getFriendList().add(temp);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int num = 0; num < Globals.getFriendList().size(); num++) {
                            final int finalNum = num;
                            GraphRequest picGraphRequest = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/" + Globals.getFriendList().get(num).getFacebookID() + "/picture", new GraphRequest.Callback() {
                                @Override
                                public void onCompleted(GraphResponse graphResponse) {
                                    Log.d("Pic", graphResponse.getJSONObject().toString());

                                    try {
                                        imageLoader.loadImage(graphResponse.getJSONObject().getJSONObject("data").getString("url"), new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Log.i("Image", "Fetched" + finalNum + Globals.getFriendList().get(finalNum).getFriendName());
                                                Globals.getFriendList().get(finalNum).setFriendImage(loadedImage);
                                                Globals.setDataAvailable(true);

                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            Bundle picParameters = new Bundle();
                            picParameters.putString("width", "9999");
                            picParameters.putString("redirect", "false");
                            picGraphRequest.setParameters(picParameters);
                            picGraphRequest.executeAsync();
                        }


                    }
                }

        ).executeAsync();
        for (int i = 0; i < Globals.getFriendList().size(); i++)
            Globals.getFriendList().remove(i);
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (position == 0) {
                    startActivity(new Intent(NewDashBoardActivity.this, ProductSearchActivity.class));

                }
                if (position == 2) {

                    startActivity(new Intent(NewDashBoardActivity.this, FriendListActivity.class));
                }
                Toast.makeText(getApplicationContext(), "Clicked on card" + position, Toast.LENGTH_LONG).show();
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < 5; index++) {
            DataObject obj = new DataObject("START ADDING GIFTS TO YOUR WISHLIST " + index,
                    "Get started, let your friend see what you wish for " + index);
            results.add(index, obj);
        }
        return results;
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.action_bar, menu);
            //restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
        if (id == R.id.action_help) {
            Toast.makeText(this, "HELP", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NewDashBoardActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
