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
import java.util.List;

public class Add_Award_To_Movie extends AppCompatActivity {
    Controller c =new Controller();
    Spinner Award_spinner;
    Spinner Movie_spinner;
    ArrayList<String> Movie_names= new ArrayList<String>();
    ArrayList<String> Movie_id= new ArrayList<String>();
    ArrayList<String> Award_names= new ArrayList<String>();
    ArrayList<String> Award_id= new ArrayList<String>();
    EditText date ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__award__to__movie);
        Movie_spinner= (Spinner) findViewById(R.id.Movie_Spinner);
        Award_spinner = (Spinner) findViewById(R.id.Award_Spinner);
        try {
//movies spinner
            ResultSet r1 = c.get_movies();
            Movie_names = c.SetNamesToArray(r1, Movie_id);
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Movie_names);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Movie_spinner.setAdapter(dataAdapter1);

            //awards

            ResultSet r2 = c.getAward(0);
            Award_names = c.SetNamesToArray(r2, Award_id);
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Award_names);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Award_spinner.setAdapter(dataAdapter2);
        }catch (SQLException e){e.printStackTrace();}
    }

    public void AddAward(View v) throws java.sql.SQLException{
        date = findViewById(R.id.WinDate);
        String Wdate = date.getText().toString();
        if(Wdate.isEmpty())
            Toast.makeText(Add_Award_To_Movie.this, "Adding failed. Please re-check your input information ", Toast.LENGTH_LONG).show();
        else {
            int res = c.add_award_to_movie(Movie_id.get(Movie_spinner.getSelectedItemPosition()),Award_id.get(Award_spinner.getSelectedItemPosition()),Wdate);
            if (res == 1) {
                Toast.makeText(Add_Award_To_Movie.this,  "award added successfully to a movie.", Toast.LENGTH_SHORT).show();
            } else if (res == 404) {
                Toast.makeText(Add_Award_To_Movie.this, " Adding failed it has been already added before.", Toast.LENGTH_SHORT).show();
            }else  {
                Toast.makeText(Add_Award_To_Movie.this, " Adding failed .", Toast.LENGTH_SHORT).show();
            }
        }
    }
}