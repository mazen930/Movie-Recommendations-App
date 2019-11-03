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

public class UpdateDirector extends AppCompatActivity {
    Spinner AllDirectorsSpinner ; // items to be selected for being deleted
    Controller c=new Controller();
    EditText Name ;
    EditText Brief ;
    EditText BirthDate;
    EditText Style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_director);
        try {
            FillActorSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void FillActorSpinner()throws SQLException
    {
        AllDirectorsSpinner=findViewById(R.id.UpdateDirectorSpinner);
        ArrayList<String> arrayList = new ArrayList <String>();

        ResultSet res = c.SelectAllDirectors();
        while (res.next())
        {
            arrayList.add(res.getString("member_name"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        AllDirectorsSpinner.setAdapter(adapter);
    }
    public void GetDirectorDataClick(View view)throws SQLException
    {
        Name = findViewById(R.id.DirectorFullNameUpdate);
        Brief = findViewById(R.id.DirectorBriefUpdate);
        BirthDate=findViewById(R.id.DirectorBirthDateUpdate);
        Style=findViewById(R.id.DirectorStyleUpdate);
        String DirectorName= (String) AllDirectorsSpinner.getSelectedItem();
        ResultSet DirectorData= c.SelectDirectorByName(DirectorName);
        if(DirectorData.next()) {
            Name.setText(DirectorData.getString("member_name"),TextView.BufferType.EDITABLE);
            BirthDate.setText(DirectorData.getString("birth_date"),TextView.BufferType.EDITABLE);
            Brief.setText(DirectorData.getString("brief"),TextView.BufferType.EDITABLE);
            Style.setText(DirectorData.getString("making_style"),TextView.BufferType.EDITABLE);
        }
    }
    public void UpdateDirectorClick(View view)throws SQLException
    {
        Name = findViewById(R.id.DirectorFullNameUpdate);
        Brief = findViewById(R.id.DirectorBriefUpdate);
        BirthDate=findViewById(R.id.DirectorBirthDateUpdate);
        Style = findViewById(R.id.DirectorStyleUpdate);

        String DirectorIdentify= (String) AllDirectorsSpinner.getSelectedItem();
        String name = Name.getText().toString();
        String bdate=BirthDate.getText().toString();
        String brief=Brief.getText().toString();
        String style = Style.getText().toString();

        if(name.isEmpty() || bdate.isEmpty() || brief.isEmpty() || style.isEmpty()) {
            Toast.makeText(UpdateDirector.this,"Please fill all data ",Toast.LENGTH_SHORT).show();
        }
        else {
            int res = c.UpdateDirector(name,bdate,brief,style,DirectorIdentify);
            if (res==1) {
                Toast.makeText(UpdateDirector.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
                FillActorSpinner();
            }
            else
                Toast.makeText(UpdateDirector.this,"Something gone wrong ",Toast.LENGTH_SHORT).show();
        }
    }


}
