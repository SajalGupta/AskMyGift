package com.turningcloud.askmygift.addgift;

import android.support.v7.widget.CardView;
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
 * Created by Sajal on 12-08-2015.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "ProductAdapter";
    private ArrayList<ProductData> mDataset;

    public ProductRecyclerViewAdapter(ArrayList<ProductData> mDataset) {
        this.mDataset = mDataset;
    }
    public void removeAll(){
        for(int i=0;i<mDataset.size();i++){
            mDataset.remove(i);
        }
        notifyDataSetChanged();
    }
    public void remove(int i){
        mDataset.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_card_row, viewGroup, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder dataObjectHolder, int i) {

        dataObjectHolder.productName.setText(mDataset.get(i).getProductName());
        dataObjectHolder.productDiscount.setText(mDataset.get(i).getProductDiscount()+"% DISCOUNT");
        dataObjectHolder.productImage.setImageBitmap(mDataset.get(i).getProductImageBit());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        CircleImageView productImage;
        TextView productName;
        TextView productDiscount;
        public DataObjectHolder(View itemView) {

            super(itemView);
             cardView = (CardView) itemView.findViewById(R.id.card_view);
             productImage = (CircleImageView) itemView.findViewById(R.id.product_image);
             productName = (TextView) itemView.findViewById(R.id.product_title);
             productDiscount = (TextView) itemView.findViewById(R.id.product_discount);

        }
    }
}
