package com.turningcloud.askmygift;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Sajal on 10-08-2015.
 */
public class CustomPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {

        int pageWidth = page.getWidth();


        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(1);

        } else if (position <= 1) { // [-1,1]
            ImageView dummyImageView = (ImageView) page.findViewById(R.id.dummyImageView);
            dummyImageView.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(1);
        }



    }
}
