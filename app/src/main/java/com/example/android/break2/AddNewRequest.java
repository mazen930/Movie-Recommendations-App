package com.example.android.break2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddNewRequest extends AppCompatActivity {
    int userID, privi;
    EditText Moviename_ET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_request);

        Bundle info = getIntent().getExtras();
        userID=info.getInt("ID");
        privi=info.getInt("Privileges");
    }

    public void AddRequestClick(View v) throws SQLException {
        Moviename_ET = findViewById(R.id.Moviename);

        String Rname = Moviename_ET.getText().toString();

        if (Rname.isEmpty()) {
            Toast.makeText(AddNewRequest.this, "Adding failed. Please re-check your input information ", Toast.LENGTH_SHORT).show();
        } else {
            Controller c = new Controller();
            String name = Rname.toLowerCase();
            int res = c.InsertRequest(name,userID);
            if (res == 1) {
                Toast.makeText(AddNewRequest.this, Rname + " is added successfully .", Toast.LENGTH_SHORT).show();

            } else if (res == 404) {
                Toast.makeText(AddNewRequest.this, " Adding failed this movie is already requested before.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddNewRequest.this, "Adding failed something gone wrong please check your server.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
