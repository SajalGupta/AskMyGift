package com.turningcloud.askmygift.updatewishlist;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.turningcloud.Dashboard.Globals;
import com.turningcloud.askmygift.R;
import com.turningcloud.askmygift.addgift.ProductData;

import java.util.ArrayList;


public class UpdateWishlistActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_wishlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BitmapDrawable background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
                R.drawable.section_background));

        background.setTileModeX(android.graphics.Shader.TileMode.REPEAT);

        getSupportActionBar().setBackgroundDrawable(background);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_wishlist);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        WishlistRecyclerViewAdapter mAdapter = new WishlistRecyclerViewAdapter(getData());
        mRecyclerView.setAdapter(mAdapter);

    }
    public ArrayList<WishData> getData(){
        ArrayList<WishData> wishList = new ArrayList<>();
        ArrayList<ProductData> gathered = Globals.getWishlistData();
        for(int i = 0;i<gathered.size();i++){
            WishData temp = new WishData();
            temp.setWishImage(gathered.get(i).getProductImageBit());
            temp.setWishName(gathered.get(i).getProductName());
            wishList.add(temp);
        }
        return  wishList;
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
}
