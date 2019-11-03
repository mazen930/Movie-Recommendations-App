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

public class AddAwardToDirector extends AppCompatActivity {
    Controller c =new Controller();
    Spinner Award_spinner;
    Spinner Dir_spinner;
    ArrayList<String> Dir_names= new ArrayList<String>();
    ArrayList<String> Dir_id= new ArrayList<String>();
    ArrayList<String> Award_names= new ArrayList<String>();
    ArrayList<String> Award_id= new ArrayList<String>();
    Spinner Movie_spinner;
    ArrayList<String> Movie_names= new ArrayList<String>();
    ArrayList<String> Movie_id= new ArrayList<String>();
    EditText date ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_award_to_director);
        Dir_spinner= (Spinner) findViewById(R.id.Dir_Spinner);
        Award_spinner = (Spinner) findViewById(R.id.Award_Spinner2);
        Movie_spinner= (Spinner) findViewById(R.id.Movie_Spinner2);
        try {
//movies spinner
            ResultSet r1 = c.directors_names();
            Dir_names = c.SetNamesToArray(r1, Dir_id);
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Dir_names);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Dir_spinner.setAdapter(dataAdapter1);

            //awards

            ResultSet r2 = c.getAward(3);
            Award_names = c.SetNamesToArray(r2, Award_id);
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Award_names);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Award_spinner.setAdapter(dataAdapter2);

        }catch (SQLException e){e.printStackTrace();}
    }

    public void AddAward(View v) throws java.sql.SQLException{
        date = findViewById(R.id.WinDate2);
        String Wdate = date.getText().toString();
        if(Wdate.isEmpty()||Dir_names.isEmpty()||Movie_names.isEmpty()||Award_names.isEmpty())
            Toast.makeText(this, "Adding failed. Please re-check your input information ", Toast.LENGTH_LONG).show();
        else {
            int res = c.add_award_to_director(Movie_id.get(Movie_spinner.getSelectedItemPosition()),Dir_id.get(Dir_spinner.getSelectedItemPosition()),Award_id.get(Award_spinner.getSelectedItemPosition()),Wdate);
            if (res == 1) {
                Toast.makeText(this,  "award added successfully to a director.", Toast.LENGTH_SHORT).show();
            } else if (res == 404) {
                Toast.makeText(this, " Adding failed it has been already added before.", Toast.LENGTH_SHORT).show();
            }else if (res == -1) {
                Toast.makeText(this, " Adding failed this director didn't participate in this movie,please refresh get movie button.", Toast.LENGTH_SHORT).show();
            }else  {
                Toast.makeText(this, " Adding failed .", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void GetAutMovies(View view)throws SQLException{
        //movies spinner
        ResultSet r3 = c.getMemberMovies(2,Dir_id.get(Dir_spinner.getSelectedItemPosition()));
        Movie_names = c.SetNamesToArray(r3, Movie_id);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Movie_names);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Movie_spinner.setAdapter(dataAdapter3);
    }
}
