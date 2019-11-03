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

public class UpdateAward extends AppCompatActivity {
    Spinner AllAwardsSpinner ; // items to be selected for being deleted
    Controller c=new Controller();
    Spinner Fest_date_spinner ;
    Spinner Award_type_spinner;
    EditText Award_name,Fest_name,Fest_loc;
    ArrayList<String> months =new ArrayList<>();
    ArrayList<String> award_type = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_award);

        try {
            FillAwardSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //initialize fest_date_spinner
        months.add("January");
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
    protected void FillAwardSpinner()throws SQLException
    {
        AllAwardsSpinner=findViewById(R.id.UpdateAwardSpinner);
        ArrayList<String> arrayList = new ArrayList <String>();

        ResultSet res = c.SelectAllAwards();
        while (res.next())
        {
            arrayList.add(res.getString("award_name"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        AllAwardsSpinner.setAdapter(adapter);
    }
    public void GetAwardDataClick(View view)throws SQLException
    {
        Award_name = findViewById(R.id.Award_Name);
        Fest_name = findViewById(R.id.Fest_Name);
        Fest_loc = findViewById(R.id.Fest_Loc);

        String AwardName= (String) AllAwardsSpinner.getSelectedItem();
        ResultSet actorData= c.SelectAwardByName(AwardName);
        if(actorData.next()) {
            Award_name.setText(actorData.getString("award_name"), TextView.BufferType.EDITABLE);
            Fest_name.setText(actorData.getString("fest_name"),TextView.BufferType.EDITABLE);
            Fest_loc.setText(actorData.getString("fest_location"),TextView.BufferType.EDITABLE);
        }
    }
    public void UpdateAwardClick(View view)throws SQLException
    {
        Award_name = findViewById(R.id.Award_Name);
        Fest_name = findViewById(R.id.Fest_Name);
        Fest_loc = findViewById(R.id.Fest_Loc);

        String Awardname= (String) AllAwardsSpinner.getSelectedItem();
        String name =Award_name.getText().toString();
        String festname=Fest_name .getText().toString();
        String festloc=Fest_loc.getText().toString();
        int awardtype=Award_type_spinner.getSelectedItemPosition();
        String festdate=(String)Fest_date_spinner.getSelectedItem();

        if(name.isEmpty() || festloc.isEmpty() || festname.isEmpty()) {
            Toast.makeText(UpdateAward.this,"Please fill all data ",Toast.LENGTH_SHORT).show();
        }
        else {
            int res = c.UpdateAward(name,festloc,awardtype, festname,festdate,Awardname);
            if (res==1) {
                Toast.makeText(UpdateAward.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
                FillAwardSpinner();
            }
            else
                Toast.makeText(UpdateAward.this,"Something gone wrong ",Toast.LENGTH_SHORT).show();
        }
    }


}
