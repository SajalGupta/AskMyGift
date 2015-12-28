package com.turningcloud.drawer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.turningcloud.askmygift.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FilterSearchActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);
        ArrayList<String> mItemList = new ArrayList<String>();
        mItemList.add("Ipod");
        mItemList.add("Ipad");
        mItemList.add("Imac");
        mItemList.add("Itouch");
        mItemList.add("Bat");
        mItemList.add("Mat");
        mItemList.add("Cat");
        mItemList.add("Watch");
        mItemList.add("Car");

        ListView filterListView = (ListView) findViewById(R.id.filterListView);
        final FilterAdapter mFilterAdapter = new FilterAdapter(this, mItemList);

        filterListView.setAdapter(mFilterAdapter);
        final EditText searchText = (EditText) findViewById(R.id.editTextSearch);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchText.getText().toString().toLowerCase(Locale.getDefault());
                mFilterAdapter.filter(text);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter_search, menu);
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
