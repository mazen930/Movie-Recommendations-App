package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddDirector extends AppCompatActivity {
    EditText Fname_ET, Lname_ET, BirthDate_ET, Brief_ET, Stlye_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_director);
    }
    public void AddDirectorClick(View v)throws SQLException
    {
        Fname_ET = findViewById(R.id.directorFName);
        Lname_ET = findViewById(R.id.directorLName);
        BirthDate_ET = findViewById(R.id.directorBirthDate);
        Brief_ET = findViewById(R.id.directorBrief);
        Stlye_ET = findViewById(R.id.directorStyle);

        String name = Fname_ET.getText().toString() + " " + Lname_ET.getText().toString();
        String birthDate = BirthDate_ET.getText().toString();
        String brief = Brief_ET.getText().toString();
        String style = Stlye_ET.getText().toString();

        if (name.isEmpty() || birthDate.isEmpty() || brief.isEmpty() || style.isEmpty()) {
            Toast.makeText(AddDirector.this, "Adding failed. Please re-check your input information ", Toast.LENGTH_SHORT).show();
        } else {
            Controller c = new Controller();
            int res = c.InsertDirector(name, birthDate, brief, style);
            if (res == 1) {
                Toast.makeText(AddDirector.this, "Director. " + name + " has been added successfully ", Toast.LENGTH_SHORT).show();
                //this.finish();
            }
            else {
                if (res == 1) {
                    Toast.makeText(AddDirector.this, name + " added successfully as a director.", Toast.LENGTH_SHORT).show();
                } else if (res == 404) {
                    Toast.makeText(AddDirector.this, " Adding failed this director is already added before.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddDirector.this, "Adding failed something gone wrong please check your data.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
