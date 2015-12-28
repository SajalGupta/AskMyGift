package com.turningcloud.askmygift;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;




public class GetStartedPageAdapter extends PagerAdapter {
	//generic events materials
	
	
	
	View view;
    public int getCount() {
        return 3;
    }

    public Object instantiateItem(View collection, int position) {

        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           
        int resId = 0;
        switch (position) {
        case 0:
            resId = R.layout.ask_intro1;
            view = inflater.inflate(resId, null);

            ((ViewPager) collection).addView(view, 0);
            
            final TextView tv=(TextView)view.findViewById(R.id.textView1);
            tv.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
				
					tv.setText("hello");
				}
			});
            
            break;
        case 1:
            resId = R.layout.ask_about_app;
            view = inflater.inflate(resId, null);

            ((ViewPager) collection).addView(view, 0);
            break;
        case 3:
            resId = R.layout.getstarted_generic_events;
            view = inflater.inflate(resId, null);
            ((ViewPager) collection).addView(view, 0);
            break;
        case 2:
        	 resId = R.layout.ask_getstarted;
             
             view = inflater.inflate(resId, null);

             ((ViewPager) collection).addView(view, 0);
             break;
       
          
        case 4:
            resId = R.layout.getstarted_gifts;
            view = inflater.inflate(resId, null);

            ((ViewPager) collection).addView(view, 0);
            break;
        case 5:
        	  resId = R.layout.getstarted_personal_events;
              view = inflater.inflate(resId, null);

              ((ViewPager) collection).addView(view, 0);
              break;
           
        }

     
        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);

    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}