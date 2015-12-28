package com.turningcloud.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.turningcloud.askmygift.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sajal on 22-07-2015.
 */
public class  FilterAdapter extends BaseAdapter{

    Context mContext;
    LayoutInflater inflater;
    ArrayList<String> mItemList;
    ArrayList<String> randomFreeList;
    public FilterAdapter(Context context,ArrayList<String> mList) {
        mContext = context;
        mItemList = mList;
        inflater = LayoutInflater.from(mContext);
        this.randomFreeList = new ArrayList<String>();
        this.randomFreeList.addAll(mItemList);

    }
    public class ViewHolder {
        TextView itemName;
        // AnyView

    }
    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.filter_search_list,null);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemNameList);
            //Reference
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemName.setText(mItemList.get(position));
        //Add Data
        return convertView;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        mItemList.clear();
        //ArrayList<ContactStore> mpl = new ArrayList<ContactStore>();
        if(charText.length()==0){
            mItemList.addAll(randomFreeList);
        }
        else{
            for(String mpl : randomFreeList){
                if(mpl.toLowerCase(Locale.getDefault()).contains(charText)){
                    mItemList.add(mpl);
                }
            }

        }
        notifyDataSetChanged();
    }

}
