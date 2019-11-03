package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddNewAward extends AppCompatActivity {
    Spinner Fest_date_spinner ;
    Spinner Award_type_spinner;
    EditText Award_name,Fest_name,Fest_loc;
    ArrayList<String> months =new ArrayList<>();
    ArrayList<String> award_type = new ArrayList<>();
    Controller c = new Controller();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_award);

        //initialize fest_date_spinner
        months.add("Jaunuary");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        Fest_date_spinner =(Spinner)findViewById(R.id.Fest_Date_Spinner);
        ArrayAdapter<String> FestAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,months);
        FestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Fest_date_spinner.setAdapter(FestAdapter);

        //initialize Award_type_spinner
        award_type.add("Movie");
        award_type.add("Actor");
        award_type.add("Author");
        award_type.add("Director");
        Award_type_spinner = (Spinner)findViewById(R.id.Award_Type_Spinner);
        ArrayAdapter <String> TypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,award_type);
        TypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Award_type_spinner.setAdapter(TypeAdapter);
    }


    public void AddNewAward(View v) throws java.sql.SQLException
    {
        Award_name = findViewById(R.id.Award_Name);
        Fest_name = findViewById(R.id.Fest_Name);
        Fest_loc = findViewById(R.id.Fest_Loc);

        String Aname = Award_name.getText().toString();
        String Fname = Fest_name.getText().toString();
        String Floc = Fest_loc.getText().toString();
        String Fdate = Fest_date_spinner.getSelectedItem().toString();
        int Atype = Award_type_spinner.getSelectedItemPosition();
        if(Aname.isEmpty()||Fname.isEmpty()||Floc.isEmpty())
            Toast.makeText(AddNewAward.this, "Adding failed. Please re-check your input information ", Toast.LENGTH_LONG).show();
        else {
            int res = c.InsertAward(Aname, Atype, Fname, Fdate, Floc);
            if (res == 1) {
                Toast.makeText(AddNewAward.this, Aname + " added successfully as an award.", Toast.LENGTH_SHORT).show();
            } else if (res == 404) {
                Toast.makeText(AddNewAward.this, " Adding failed this award is already added before.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddNewAward.this, "Adding failed something gone wrong please check your server.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
