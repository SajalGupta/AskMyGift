package com.turningcloud.drawer;

import com.turningcloud.askmygift.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] itemname;
	private final Integer[] imgid;
	
	public ContactListAdapter(Activity context, String[] itemname, Integer[] imgid) {
		super(context, R.layout.contact_friendlist, itemname);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.itemname=itemname;
		this.imgid=imgid;
	}

	
	@SuppressLint("ViewHolder") 
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.contact_friendlist, null,true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.Itemname);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		
		
		txtTitle.setText(itemname[position]);
		imageView.setImageResource(imgid[position]);

		return rowView;
		
	};
}