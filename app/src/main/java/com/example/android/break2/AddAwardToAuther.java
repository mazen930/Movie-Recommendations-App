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

public class AddAwardToAuther extends AppCompatActivity {
    Controller c =new Controller();
    Spinner Award_spinner;
    Spinner Aut_spinner;
    ArrayList<String> Aut_names= new ArrayList<String>();
    ArrayList<String> Aut_id= new ArrayList<String>();
    ArrayList<String> Award_names= new ArrayList<String>();
    ArrayList<String> Award_id= new ArrayList<String>();
    Spinner Movie_spinner;
    ArrayList<String> Movie_names= new ArrayList<String>();
    ArrayList<String> Movie_id= new ArrayList<String>();
    EditText date ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_award_to_auther);

        Aut_spinner= (Spinner) findViewById(R.id.Aut_Spinner);
        Award_spinner = (Spinner) findViewById(R.id.Award_Spinner3);
        Movie_spinner= (Spinner) findViewById(R.id.Movie_Spinner3);
//authors spinner
        try {
            ResultSet r1 = c.authors_names();
            Aut_names = c.SetNamesToArray(r1, Aut_id);
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Aut_names);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Aut_spinner.setAdapter(dataAdapter1);

            //awards

            ResultSet r2 = c.getAward(2);
            Award_names = c.SetNamesToArray(r2, Award_id);
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Award_names);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Award_spinner.setAdapter(dataAdapter2);


        }catch (SQLException e){e.printStackTrace();}
    } public void AddAward(View v) throws java.sql.SQLException{
        date = findViewById(R.id.WinDate3);
        String Wdate = date.getText().toString();
        if(Wdate.isEmpty()||Aut_names.isEmpty()||Movie_names.isEmpty()||Award_names.isEmpty())
            Toast.makeText(this, "Adding failed. Please re-check your input information ", Toast.LENGTH_LONG).show();
        else {
            int res = c.add_award_to_author(Movie_id.get(Movie_spinner.getSelectedItemPosition()),Aut_id.get(Aut_spinner.getSelectedItemPosition()),Award_id.get(Award_spinner.getSelectedItemPosition()),Wdate);
            if (res == 1) {
                Toast.makeText(this,  "award added successfully to an author.", Toast.LENGTH_SHORT).show();
            } else if (res == 404) {
                Toast.makeText(this, " Adding failed award has been already added before.", Toast.LENGTH_SHORT).show();
            }else if (res == -1) {
                Toast.makeText(this, " Adding failed this author didn't participate in this movie,please refresh get movie button.", Toast.LENGTH_SHORT).show();
            }else  {
                Toast.makeText(this, " Adding failed .", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void GetAutMovies(View view)throws SQLException {
        //movies spinner
        ResultSet r3 = c.getMemberMovies(1,Aut_id.get(Aut_spinner.getSelectedItemPosition()));
        Movie_names = c.SetNamesToArray(r3, Movie_id);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Movie_names);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Movie_spinner.setAdapter(dataAdapter3);
    }
}