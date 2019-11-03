package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddActor extends AppCompatActivity {

    EditText Fname_ET , Lname_ET,Brief_ET,Bdate_ET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_actor);
    }
    public void AddNewActor(View v) throws SQLException
    {
        Fname_ET=findViewById(R.id.actorFName);
        Lname_ET=findViewById(R.id.actorLName);
        Brief_ET=findViewById(R.id.actorBrief);
        Bdate_ET=findViewById(R.id.ActorBirthDate);
        String fname =Fname_ET.getText().toString();
        String lname = Lname_ET.getText().toString();
        String brief = Brief_ET.getText().toString();
        String date = Bdate_ET.getText().toString();

        if(fname.isEmpty() || lname.isEmpty() || brief.isEmpty() || date.isEmpty())
        {
            Toast.makeText(AddActor.this,"Adding failed. Please re-check your input information ",Toast.LENGTH_SHORT).show();
        }
        else {
            Controller c = new Controller();
            String name = fname.toLowerCase()+" "+lname.toLowerCase() ;
            int res =c.InsertActor(name,date,brief);
            if(res==1)
            {
                Toast.makeText(AddActor.this,fname+" added successfully as an actor.",Toast.LENGTH_SHORT).show();

            }
            else if(res==404){Toast.makeText(AddActor.this," Adding failed this actor is already added before.",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddActor.this,"Adding failed something gone wrong please check your data.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
