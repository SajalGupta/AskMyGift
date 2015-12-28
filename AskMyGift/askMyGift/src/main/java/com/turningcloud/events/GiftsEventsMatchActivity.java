package com.turningcloud.events;



import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.turningcloud.askmygift.R;
import com.turningcloud.drawer.MainActivity;


public class GiftsEventsMatchActivity extends Activity implements OnClickListener{
	Button drawer_Btn;
	String[] genricResultArr;
	String[] personalResultArr;
	String[] giftStrArr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gifts_events_match);
		
		
		
		
		drawer_Btn=(Button) findViewById(R.id.drawerButton);
		drawer_Btn.setOnClickListener(this);
		
		
		Bundle b = getIntent().getExtras();
		 genricResultArr = b.getStringArray("GenericItems");
		 personalResultArr = b.getStringArray("PersonalItems");
		 
		 ArrayList<String> both = new ArrayList<String>(Arrays.asList(genricResultArr));
		 both.addAll(Arrays.asList(personalResultArr));

		 both.toArray();
		 
		ListView lv = (ListView) findViewById(R.id.listView1);
			ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, both);
		lv.setAdapter(adapter1);
		 
		giftStrArr=b.getStringArray("GiftItems");
		
		String[] sCheeseStrings=giftStrArr;
		ArrayList<String>mCheeseList = new ArrayList<String>();
        for (int i = 0; i < sCheeseStrings.length; ++i) {
            mCheeseList.add(sCheeseStrings[i]);
            
            StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.text_view, mCheeseList);
            DynamicListView listView = (DynamicListView) findViewById(R.id.listView2);

            listView.setCheeseList(mCheeseList);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            
        }
				
				
				
		
	
	
	
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gifts_events_match, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		Intent opendrawer=new Intent(GiftsEventsMatchActivity.this,MainActivity.class);
		startActivity(opendrawer);
				
		
	}
}
