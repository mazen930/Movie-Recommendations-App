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

public class UpdateCinema extends AppCompatActivity {
    Spinner AllCinemasSpinner ;
    Controller c=new Controller();
    EditText Name ;
    EditText Location ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cinema);
        try {
            FillCinemaSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void FillCinemaSpinner()throws SQLException
    {
        AllCinemasSpinner=findViewById(R.id.UpdateCinemaSpinner);
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
    public void GetCinemaDataClick(View view)throws SQLException
    {
        Name = findViewById(R.id.CinemaNameUpdate);
        Location = findViewById(R.id.CinemaLocationUpdate);
        String CinemaName= (String) AllCinemasSpinner.getSelectedItem();
        ResultSet CinemaData= c.SelectCinemaByName(CinemaName);
        if(CinemaData.next()) {
            Name.setText(CinemaData.getString("cinema_name"),TextView.BufferType.EDITABLE);
            Location.setText(CinemaData.getString("cinema_location"),TextView.BufferType.EDITABLE);
        }
    }

    public void UpdateCinemaClick(View view)throws SQLException
    {
        Name = findViewById(R.id.CinemaNameUpdate);
        Location = findViewById(R.id.CinemaLocationUpdate);

        String CinemaIdentify= (String) AllCinemasSpinner.getSelectedItem();
        String name = Name.getText().toString();
        String location=Location.getText().toString();

        if(name.isEmpty() || location.isEmpty() ) {
            Toast.makeText(UpdateCinema.this,"Please fill all data ",Toast.LENGTH_SHORT).show();
        }
        else {
            int res = c.UpdateCinema(name,location,CinemaIdentify);
            if (res==1) {
                Toast.makeText(UpdateCinema.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
                FillCinemaSpinner();
            }
            else
                Toast.makeText(UpdateCinema.this,"Something gone wrong ",Toast.LENGTH_SHORT).show();
        }
    }


}
