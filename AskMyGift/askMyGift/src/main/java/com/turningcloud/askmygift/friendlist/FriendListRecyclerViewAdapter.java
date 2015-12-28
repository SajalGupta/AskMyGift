package com.turningcloud.askmygift.friendlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.turningcloud.askmygift.R;
import com.turningcloud.askmygift.updatewishlist.WishData;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sajal on 13-08-2015.
 */
public class FriendListRecyclerViewAdapter extends RecyclerView.Adapter<FriendListRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "FriendAdapter";
    private ArrayList<FriendData> mDataset;

    public FriendListRecyclerViewAdapter(ArrayList<FriendData> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.wishlist_row, viewGroup, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder dataObjectHolder, int i) {
        dataObjectHolder.textViewName.setText(mDataset.get(i).getFriendName());

        dataObjectHolder.userProfilePic.setImageBitmap(mDataset.get(i).getFriendImage());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }





    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        CircleImageView userProfilePic;
        public DataObjectHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewWish);
            userProfilePic = (CircleImageView) itemView.findViewById(R.id.card_image_wish);

        }
    }


}