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
import java.util.ArrayList;

public class AddAwardToActor extends AppCompatActivity {
    Controller c =new Controller();
    Spinner Award_spinner;
    Spinner Act_spinner;
    ArrayList<String> Act_names= new ArrayList<String>();
    ArrayList<String> Act_id= new ArrayList<String>();
    ArrayList<String> Award_names= new ArrayList<String>();
    ArrayList<String> Award_id= new ArrayList<String>();
    Spinner Movie_spinner;
    ArrayList<String> Movie_names= new ArrayList<String>();
    ArrayList<String> Movie_id= new ArrayList<String>();
    EditText date ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_award_to_actor);
        Act_spinner= (Spinner) findViewById(R.id.Act_Spinner);
        Award_spinner = (Spinner) findViewById(R.id.Award_Spinner1);
        Movie_spinner= (Spinner) findViewById(R.id.Movie_Spinner1);
//actor spinner
        try {
            ResultSet r1 = c.actors_names();
            Act_names= c.SetNamesToArray(r1,Act_id);
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Act_names);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Act_spinner.setAdapter(dataAdapter1);

            //awards

            ResultSet r2 = c.getAward(1);
            Award_names= c.SetNamesToArray(r2,Award_id);
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Award_names);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Award_spinner.setAdapter(dataAdapter2);

        }catch (SQLException e){e.printStackTrace();}

    }

    public void AddAward(View v) throws java.sql.SQLException{
        date = findViewById(R.id.WinDate1);
        String Wdate = date.getText().toString();
        if(Wdate.isEmpty()||Act_names.isEmpty()||Movie_names.isEmpty()||Award_names.isEmpty())
            Toast.makeText(this, "Adding failed. Please re-check your input information ", Toast.LENGTH_LONG).show();
        else {
            int res = c.add_award_to_actor(Movie_id.get(Movie_spinner.getSelectedItemPosition()),Act_id.get(Act_spinner.getSelectedItemPosition()),Award_id.get(Award_spinner.getSelectedItemPosition()),Wdate);
            if (res == 1) {
                Toast.makeText(this,  "award added successfully to an actor.", Toast.LENGTH_SHORT).show();
            } else if (res == 404) {
                Toast.makeText(this, " Adding failed award has been already added before.", Toast.LENGTH_SHORT).show();
            }else if (res == -1) {
                Toast.makeText(this, " Adding failed this actor didn't participate in this movie,please refresh get movie button.", Toast.LENGTH_SHORT).show();
            }else  {
                Toast.makeText(this, " Adding failed .", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void GetActMovies(View view) throws SQLException{
        //movies spinner
        Movie_names.clear();
        ResultSet r3 = c.getMemberMovies(0, Act_id.get(Act_spinner.getSelectedItemPosition()));
        Movie_names = c.SetNamesToArray(r3, Movie_id);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Movie_names);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Movie_spinner.setAdapter(dataAdapter3);

    }
}