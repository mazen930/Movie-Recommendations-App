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

public class UpdateActor extends AppCompatActivity {
    Spinner AllActorsSpinner ; // items to be selected for being deleted
    Controller c=new Controller();
    EditText Name ;
    EditText Brief ;
    EditText BirthDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_actor);

        try {
            FillActorSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    protected void FillActorSpinner()throws SQLException
    {
        AllActorsSpinner=findViewById(R.id.UpdateActorSpinner);
        ArrayList<String> arrayList = new ArrayList <String>();

        ResultSet res = c.SelectAllActors();
        while (res.next())
        {
            arrayList.add(res.getString("member_name"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        AllActorsSpinner.setAdapter(adapter);
    }
    public void GetActorDataClick(View view)throws SQLException
    {
        EditText Name = findViewById(R.id.actorfullnameUpdate);
        EditText Brief = findViewById(R.id.actorBriefUpdate);
        EditText BirthDate=findViewById(R.id.ActorBirthDateUpdate);

        String ActorName= (String) AllActorsSpinner.getSelectedItem();
        ResultSet actorData= c.SelectCrewMemberByName(ActorName);
        if(actorData.next()) {
            Name.setText(actorData.getString("member_name"),TextView.BufferType.EDITABLE);
            BirthDate.setText(actorData.getString("birth_date"),TextView.BufferType.EDITABLE);
            Brief.setText(actorData.getString("brief"),TextView.BufferType.EDITABLE);
        }
    }
    public void UpdateActorClick(View view)throws SQLException
    {
        EditText Name = findViewById(R.id.actorfullnameUpdate);
        EditText Brief = findViewById(R.id.actorBriefUpdate);
        EditText BirthDate=findViewById(R.id.ActorBirthDateUpdate);

        String ActorIdentify= (String) AllActorsSpinner.getSelectedItem();
        String name = Name.getText().toString();
        String bdate=BirthDate.getText().toString();
        String brief=Brief.getText().toString();
        if(name.isEmpty() || bdate.isEmpty() || brief.isEmpty()) {
            Toast.makeText(UpdateActor.this,"Please fill all data ",Toast.LENGTH_SHORT).show();
        }
        else {
            int res = c.UpdateActor(name,bdate,brief,ActorIdentify);
            if (res==1) {
                Toast.makeText(UpdateActor.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
                FillActorSpinner();
            }
            else
                Toast.makeText(UpdateActor.this,"Something gone wrong ",Toast.LENGTH_SHORT).show();
        }
    }


}
