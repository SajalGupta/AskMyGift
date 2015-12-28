package com.turningcloud.askmygift.friendlist;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.turningcloud.Dashboard.Globals;
import com.turningcloud.askmygift.R;
import com.turningcloud.askmygift.addgift.ProductData;
import com.turningcloud.askmygift.updatewishlist.WishData;
import com.turningcloud.askmygift.updatewishlist.WishlistRecyclerViewAdapter;

import java.util.ArrayList;


public class FriendListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BitmapDrawable background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
                R.drawable.section_background));

        background.setTileModeX(android.graphics.Shader.TileMode.REPEAT);

        getSupportActionBar().setBackgroundDrawable(background);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_friend);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final FriendListRecyclerViewAdapter mAdapter = new FriendListRecyclerViewAdapter(getData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TOuch", "resp");
                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }
    public ArrayList<FriendData> getData(){
       return Globals.getFriendList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
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
}
