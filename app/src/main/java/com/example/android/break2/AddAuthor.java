package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddAuthor extends AppCompatActivity {

    EditText Fname_ET, Lname_ET, BirthDate_ET, Brief_ET, Stlye_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
    }

    public void AddAuthorClick(View view) throws SQLException {

        Fname_ET = findViewById(R.id.authorFN);
        Lname_ET = findViewById(R.id.authorLN);
        BirthDate_ET = findViewById(R.id.authorBD);
        Brief_ET = findViewById(R.id.authorBrief);
        Stlye_ET = findViewById(R.id.authorStyle);

        String name = Fname_ET.getText().toString() + " " + Lname_ET.getText().toString();
        String birthDate = BirthDate_ET.getText().toString();
        String brief = Brief_ET.getText().toString();
        String style = Stlye_ET.getText().toString();

        if (name.isEmpty() || birthDate.isEmpty() || brief.isEmpty() || style.isEmpty()) {
            Toast.makeText(AddAuthor.this, "Adding failed. Please re-check your input information ", Toast.LENGTH_SHORT).show();
        } else {
            Controller c = new Controller();
            int res = c.InsertAuthor(name, birthDate, brief, style);
            if (res == 1) {
                Toast.makeText(AddAuthor.this, "Author. " + name + " has been added successfully ", Toast.LENGTH_SHORT).show();

            } else {
                if (res == 1) {
                    Toast.makeText(AddAuthor.this, name + " added successfully as an author.", Toast.LENGTH_SHORT).show();
                } else if (res == 404) {
                    Toast.makeText(AddAuthor.this, " Adding failed this author is already added before.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddAuthor.this, "Adding failed something gone wrong please check your data.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
