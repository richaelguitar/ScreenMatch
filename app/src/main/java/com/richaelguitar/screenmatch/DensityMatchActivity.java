package com.richaelguitar.screenmatch;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.richaelguitar.screenmatch.utils.DensityMatch;

public class DensityMatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DensityMatch.getInstance(getApplication()).applyDensityMatch(this,"");
        setContentView(R.layout.activity_density);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            DensityMatch.getInstance(getApplication()).applyDensityMatch(this,"height");
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            DensityMatch.getInstance(getApplication()).applyDensityMatch(this,"");
        }
    }
}
