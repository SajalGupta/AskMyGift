package com.turningcloud.Dashboard;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.turningcloud.askmygift.R;
import com.turningcloud.askmygift.updatewishlist.UpdateWishlistActivity;

import java.util.ArrayList;

/**
 * Created by sumeet on 27/7/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;
        TextView wishList;
        ListView mListView;
        CardView mCardView;
        Button viewAllButton;
        Button rightButton;
        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView_CV);
            dateTime = (TextView) itemView.findViewById(R.id.textView2_CV);
            wishList = (TextView) itemView.findViewById(R.id.wishlist_CV);
            mListView = (ListView) itemView.findViewById(R.id.listViewCard);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            viewAllButton = (Button)itemView.findViewById(R.id.viewAllButton);
            rightButton = (Button)itemView.findViewById(R.id.updateWishlist);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("card is clicked","card clicked");
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {

        holder.label.setText(mDataset.get(position).getmText1());
        holder.dateTime.setText(mDataset.get(position).getmText2());

        Log.i("com.turningcloud", "Bind" + position);
        MyListAdapter mAdapter = new MyListAdapter(Globals.getGlobalContext(),Globals.getGlobalWishList());
        holder.mListView.setAdapter(mAdapter);
        final ViewGroup.LayoutParams layoutParams = holder.mCardView.getLayoutParams();
        if (position == 1) {

            Log.i("com.turningcloud", "Setting Visibility");
            holder.mListView.setVisibility(View.VISIBLE);
            holder.wishList.setVisibility(View.VISIBLE);
            holder.viewAllButton.setVisibility(View.VISIBLE);
            holder.viewAllButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            holder.viewAllButton.setOnClickListener(new View.OnClickListener() {
                boolean pressed = true;

                @Override
                public void onClick(View v) {
                    if (pressed) {
                        pressed = false;
                        Animation ani = new AnimateHeight(holder.mCardView,300,320,true);
                        ani.setDuration(500);
                        holder.mCardView.startAnimation(ani);
                        //layoutParams.height = 630;
                        holder.mCardView.setLayoutParams(layoutParams);
                        holder.viewAllButton.setText("Minimize");
                    } else {
                        Animation ani = new AnimateHeight(holder.mCardView,300,300,false);
                        ani.setDuration(500);
                        holder.mCardView.startAnimation(ani);
                        pressed = true;
                        //layoutParams.height = 300;
                        holder.mCardView.setLayoutParams(layoutParams);
                        holder.viewAllButton.setText("View All");
                    }
                }
            });

        }
        if(position == 3 ||position==4){
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p.weight = 1;

            holder.mCardView.setLayoutParams(layoutParams);
            holder.viewAllButton.setVisibility(View.VISIBLE);
            holder.viewAllButton.setLayoutParams(p);
            holder.viewAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Globals.getGlobalContext().startActivity(new Intent(Globals.getGlobalContext(), UpdateWishlistActivity.class));
                }
            });
            holder.rightButton.setVisibility(View.VISIBLE);
            holder.rightButton.setLayoutParams(p);
            if(position == 3){
                layoutParams.height=220;
                holder.viewAllButton.setText("Update Wishlist");
                holder.rightButton.setText("View Inventory");
            }
            if(position == 4){
                holder.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("turning cloud","click on "+ position);
                    }
                });
                layoutParams.height=320;
                holder.mListView.setVisibility(View.VISIBLE);
                holder.viewAllButton.setText("Update Wishlist");
                holder.rightButton.setText("Camera Stuff");
                holder.rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                        Globals.getGlobalContext().startActivity(intent);
                    }
                });
            }
        }
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}