package com.example.android.break2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class Welcome extends AppCompatActivity {
    AdView adds;
    Controller obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        VideoView vid;
        // vid = (VideoView)findViewById(R.id.videoView);
        // MediaController m = new MediaController(this);
        //vid.setMediaController(m);
        String vidAddress = "android.resource://com.example.android.database/raw/video";
        Uri u = Uri.parse(vidAddress);
        // vid.setVideoURI(u);
        // vid.start();
        // vid.seekTo(1);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        adds=(AdView)findViewById(R.id.adView);
        AdRequest adrequest=new AdRequest.Builder().build();
        adds.loadAd(adrequest);
    }
    public void MoveToLogin(View v)
    {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
    public void MoveToRegiester(View v)
    {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
}