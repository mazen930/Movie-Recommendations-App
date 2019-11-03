package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateMovie extends AppCompatActivity {
    Spinner AllmoviesSpinner ;
    Controller c=new Controller();
    EditText movieName_ET,Lang_ET,RDate_ET,Des_ET,ageR_ET,url_ET,reviewersRating_ET,duration_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_movie);
        try {
            FillMovieSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void GetMovieClick(View v) throws java.sql.SQLException {
        movieName_ET = findViewById(R.id.movieName);
        Lang_ET = findViewById(R.id.movieLang);
        RDate_ET = findViewById(R.id.releaseDate);
        Des_ET = findViewById(R.id.movieDescription);
        ageR_ET = findViewById(R.id.ageRest);
        url_ET = findViewById(R.id.movieUrl);
        reviewersRating_ET = findViewById(R.id.reviewRating);
        duration_ET = findViewById(R.id.movieDuration);
        String MovieName= (String) AllmoviesSpinner.getSelectedItem();
        ResultSet MovieData=c.SelectMovieByName(MovieName);
        if(MovieData.next()) {
            movieName_ET.setText(MovieData.getString("movie_name"), TextView.BufferType.EDITABLE);
            Lang_ET.setText(MovieData.getString("movie_language"),TextView.BufferType.EDITABLE);
            RDate_ET .setText(MovieData.getString("release_date"),TextView.BufferType.EDITABLE);
            Des_ET.setText(MovieData.getString("movie_description"),TextView.BufferType.EDITABLE);
            ageR_ET .setText(MovieData.getString("age_resriction"),TextView.BufferType.EDITABLE);
            url_ET.setText(MovieData.getString("url"),TextView.BufferType.EDITABLE);
            reviewersRating_ET.setText(MovieData.getString("reviewers_rating"),TextView.BufferType.EDITABLE);
            duration_ET.setText(MovieData.getString("duration"),TextView.BufferType.EDITABLE);

        }

    }
    public void UpdateMovieClick(View view)throws SQLException
    {
        movieName_ET = findViewById(R.id.movieName);
        Lang_ET = findViewById(R.id.movieLang);
        RDate_ET = findViewById(R.id.releaseDate);
        Des_ET = findViewById(R.id.movieDescription);
        ageR_ET = findViewById(R.id.ageRest);
        url_ET = findViewById(R.id.movieUrl);
        reviewersRating_ET = findViewById(R.id.reviewRating);
        duration_ET = findViewById(R.id.movieDuration);
        String MovieName= (String) AllmoviesSpinner.getSelectedItem();

        String name =  movieName_ET .getText().toString();
        String lang=Lang_ET .getText().toString();
        String des=Des_ET.getText().toString();
        String ageR=ageR_ET .getText().toString();
        String url=url_ET.getText().toString();
        String reviewerRating=reviewersRating_ET.getText().toString();
        String duration=duration_ET .getText().toString();



        if(name.isEmpty() || lang.isEmpty() || des.isEmpty()|| ageR.isEmpty()|| url.isEmpty()|| reviewerRating.isEmpty()|| duration.isEmpty()) {
            Toast.makeText(UpdateMovie.this,"Please fill all data ",Toast.LENGTH_SHORT).show();
        }
        else {
            int res = c.UpdateMovie(name,lang,des,ageR,url,reviewerRating,duration,MovieName);
            if (res==1) {
                Toast.makeText(UpdateMovie.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
                FillMovieSpinner();
            }
            else
                Toast.makeText(UpdateMovie.this,"Something gone wrong ",Toast.LENGTH_SHORT).show();
        }
    }
    protected void FillMovieSpinner()throws SQLException
    {
        AllmoviesSpinner=findViewById(R.id.UpdateMovieSpinner);
        ArrayList<String> arrayList = new ArrayList <String>();

        ResultSet res = c.SelectAllMovies();
        while (res.next())
        {
            arrayList.add(res.getString("movie_name"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        AllmoviesSpinner.setAdapter(adapter);
    }
}
