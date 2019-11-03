package com.example.android.break2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class AddMovieInCinema extends AppCompatActivity {
    Spinner AllCinemasSpinner,AllMoviesSpinner ;
    Controller c = new Controller();
    EditText Time_ET;
    EditText Price_ET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_in_cinema);
        try {
            FillCinemaSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            FillMoviesSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void FillCinemaSpinner()throws SQLException
    {
        AllCinemasSpinner=findViewById(R.id.ADDMCCinemasSpinner);
        ArrayList<String> arrayList = new ArrayList <String>();

        ResultSet res = c.SelectAllCinemas();
        while (res.next())
        {
            arrayList.add(res.getString("cinema_name"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        AllCinemasSpinner.setAdapter(adapter);
    }

    protected void FillMoviesSpinner()throws SQLException
    {
        AllMoviesSpinner=findViewById(R.id.ADDMCMoviesSpinner);
        ArrayList<String> arrayList = new ArrayList <String>();

        ResultSet res = c.SelectAllMovies();
        while (res.next())
        {
            arrayList.add(res.getString("movie_name"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        AllMoviesSpinner.setAdapter(adapter);
    }
    protected void AddMovieInCinemaClick(View view)throws SQLException
    {
        Time_ET=findViewById(R.id.MovinInCinemaTime);
        Price_ET=findViewById(R.id.MovineIncCinemaPrice);
        String movie= (String) AllMoviesSpinner.getSelectedItem();
        String cinema=(String) AllCinemasSpinner.getSelectedItem();
        String time=Time_ET.getText().toString();
        String price_str=Price_ET.getText().toString();
        int price=Integer.parseInt(price_str);
        if(movie.isEmpty() ||cinema.isEmpty() ||time.isEmpty() )
        {
            Toast.makeText(AddMovieInCinema.this,"Please fill all information .",Toast.LENGTH_SHORT).show();
        }
        else {
            Controller c = new Controller();
            int res= c.AddMovieToCinema(cinema,movie,time,price);
            if(res==1)
            {
                Toast.makeText(AddMovieInCinema.this,"Successfully "+movie+" movie added in "+cinema,Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(AddMovieInCinema.this,"Something gone wrong please check your info. ",Toast.LENGTH_SHORT).show();
            }
        }


    }

}
