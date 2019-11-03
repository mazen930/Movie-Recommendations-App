package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddCinema extends AppCompatActivity {
    Controller c;
    EditText cinemaName_ET , cinemaLoc_ET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cinema);
    }
    public void AddNewCinemaClick(View v)throws SQLException
    {
        c = new Controller();
        cinemaName_ET=findViewById(R.id.CinemaNameID);
        cinemaLoc_ET=findViewById(R.id.CinemaLocationID);
        String cname=cinemaName_ET.getText().toString();
        String cloc=cinemaLoc_ET.getText().toString();
        if (cname.isEmpty() || cloc.isEmpty())
        {
            Toast.makeText(AddCinema.this,"Please fill all information ",Toast.LENGTH_SHORT).show();

        }
        else {
            int res = c.InsertNewCinema(cname, cloc);
            if (res==1){
                Toast.makeText(AddCinema.this,"Successfully "+cname +" is added as a cinema ",Toast.LENGTH_SHORT).show();
                //this.finish();
            }
            else
                Toast.makeText(AddCinema.this,"Failed something gone wrong ",Toast.LENGTH_SHORT).show();

        }
    }
}
