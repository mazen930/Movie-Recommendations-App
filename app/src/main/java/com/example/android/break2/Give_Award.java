package com.example.android.break2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Give_Award extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give__award);

    }

    public void AddAwardToMovie(View v) throws java.sql.SQLException {
        Intent i = new Intent(this, Add_Award_To_Movie.class);
        startActivity(i);
    }

    public void AddAwardToActor(View v) throws java.sql.SQLException {
        //Intent i = new Intent(this, Statistics.class);
        //startActivity(i);
        Intent i = new Intent(this, AddAwardToActor.class);
        startActivity(i);
    }

    public void AddAwardToAuthor(View v) throws java.sql.SQLException {
        Intent i = new Intent(this, AddAwardToAuther.class);
        startActivity(i);
    }

    public void AddAwardToDirector(View v) throws java.sql.SQLException {
        Intent i = new Intent(this, AddAwardToDirector.class);
        startActivity(i);
    }


}