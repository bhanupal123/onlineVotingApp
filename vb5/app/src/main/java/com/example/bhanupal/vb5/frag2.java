package com.example.bhanupal.vb5;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jenil777007 on 03/04/16.
 */
public class frag2 extends Fragment {
    TextView tv1;
    ImageView im1;
    int image[]={R.drawable.about};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View v= inflater.inflate(R.layout.frag2,container,false);

        tv1= (TextView) v.findViewById(R.id.tvpaty);
        im1= (ImageView) v.findViewById(R.id.partImage);

        return v;

    }

    public void setData(String st, int res){
        tv1.setText(st);
        //im1.setImageResource(R.drawable.btn_vote_fx);

    }
}
