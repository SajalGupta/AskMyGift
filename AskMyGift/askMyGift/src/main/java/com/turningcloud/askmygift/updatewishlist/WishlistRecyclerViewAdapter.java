package com.turningcloud.askmygift.updatewishlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.turningcloud.askmygift.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sajal on 11-08-2015.
 */
public class WishlistRecyclerViewAdapter extends RecyclerView.Adapter<WishlistRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "WlistAdapter";
    private ArrayList<WishData> mDataset;

    public WishlistRecyclerViewAdapter(ArrayList<WishData> mDataset) {
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
        dataObjectHolder.textViewWish.setText(mDataset.get(i).getWishName());

        dataObjectHolder.wishImage.setImageBitmap(mDataset.get(i).getWishImage());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }





    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        TextView textViewWish;
        CircleImageView wishImage;
        public DataObjectHolder(View itemView) {
            super(itemView);
             textViewWish = (TextView) itemView.findViewById(R.id.textViewWish);
             wishImage = (CircleImageView) itemView.findViewById(R.id.card_image_wish);

        }
    }


}
