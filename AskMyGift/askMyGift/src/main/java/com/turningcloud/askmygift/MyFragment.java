package com.turningcloud.askmygift;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.turningcloud.drawer.MainActivity;
import com.turningcloud.login.NewFacebookLogin;
import com.turningcloud.login.RegisterActivity;

/**
 * Created by Sajal on 09-07-2015.
 */
public class MyFragment extends Fragment {
    int mCurrentPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Getting the arguments to the Bundle object */
        Bundle data = getArguments();

        /** Getting integer data of the key current_page from the bundle */
        mCurrentPage = data.getInt("current_page", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tutorial_fragment_layout, container,false);
        TextView tv = (TextView) v.findViewById(R.id.tv);
        tv.setVisibility(View.INVISIBLE);
        if(mCurrentPage==4){
            Log.i("Tour","Inside Page 4");
            Button startButton = (Button) v.findViewById(R.id.startButton);
            startButton.setVisibility(View.VISIBLE);

            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), RegisterActivity.class);
                    getActivity().finish();
                    startActivity(i);
                }
            });

        }
        else if(mCurrentPage==1){
            Log.i("Tour","Inside Page 1");
            RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.fragmentRelativeLayout);
          //  relativeLayout.setBackgroundResource(R.drawable.tour_01_v2);
            ImageView dummyImageView = (ImageView) v.findViewById(R.id.dummyImageView);
            tv.setVisibility(View.VISIBLE);
            tv.setText("Share with friends");
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            TextView tvNext = (TextView) v.findViewById(R.id.tvNext);
            tvNext.setText("Select, Share, Socialize");
            tvNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

            dummyImageView.setImageDrawable(getResources().getDrawable(R.drawable.tut_screen_one));
        }
        else if(mCurrentPage==2){
            Log.i("Tour","Inside Page 2");
            tv.setVisibility(View.VISIBLE);
            RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.fragmentRelativeLayout);
          //  relativeLayout.setBackgroundResource(R.drawable.tour_02);
            ImageView dummyImageView = (ImageView) v.findViewById(R.id.dummyImageView);
            dummyImageView.setImageDrawable(getResources().getDrawable(R.drawable.tut_screen_two));
            tv.setText("Make your Wishlist");
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            TextView tvNext = (TextView) v.findViewById(R.id.tvNext);
            tvNext.setText("Click, Scan, Search");
            tvNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        }
        else if(mCurrentPage==3){
            Log.i("Tour","Inside Page 3");
            tv.setVisibility(View.VISIBLE);
            RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.fragmentRelativeLayout);
            //  relativeLayout.setBackgroundResource(R.drawable.tour_02);
            ImageView dummyImageView = (ImageView) v.findViewById(R.id.dummyImageView);
            dummyImageView.setImageDrawable(getResources().getDrawable(R.drawable.tut_screen_two));
            tv.setText("Make your Wishlist");
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            TextView tvNext = (TextView) v.findViewById(R.id.tvNext);
            tvNext.setText("Click, Scan, Search");
            tvNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        }

        return v;
    }
}
