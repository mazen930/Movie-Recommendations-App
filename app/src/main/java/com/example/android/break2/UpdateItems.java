package com.example.android.break2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UpdateItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_items);
    }
    public void UpdateActorActive(View v)
    {
        Intent intent = new Intent(this,UpdateActor.class);
        startActivity(intent);
    }
    public void UpdateAuthorActive(View v)
    {
        Intent intent = new Intent(this,UpdateAuthor.class);
        startActivity(intent);
    }
    public void UpdateDirectorActive(View v)
    {
        Intent intent = new Intent(this,UpdateDirector.class);
        startActivity(intent);
    }
    public void UpdateCinemaActive(View v)
    {
        Intent intent = new Intent(this,UpdateCinema.class);
        startActivity(intent);
    }
    public void UpdateAdvActive(View v)
    {
        Intent intent = new Intent(this,UpdateAdv.class);
        startActivity(intent);
    }
    public void UpdateMovieActive(View v)
    {
        Intent intent = new Intent(this,UpdateMovie.class);
        startActivity(intent);
    }
    public void UpdateAward(View v)
    {
        Intent intent = new Intent(this,UpdateAward.class);
        startActivity(intent);
    }
}
