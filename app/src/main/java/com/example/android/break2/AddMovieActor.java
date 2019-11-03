package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddMovieActor extends AppCompatActivity {

    EditText character_ET ;
    Spinner ActorSpinner,MovieSpinner;
    Controller c1 = new Controller();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_actor);

        character_ET=findViewById(R.id.Character_ET);
        ActorSpinner=findViewById(R.id.Actorspinner);
        MovieSpinner=findViewById(R.id.Moviespinner);
        try {
            FillActorSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            FillMoviesSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void AddActorToMovie(View v) throws SQLException
    {
        String Actorname = ActorSpinner.getSelectedItem().toString();
        String Moviename = MovieSpinner.getSelectedItem().toString();
        String chname =character_ET.getText().toString();
        if(chname.isEmpty()||Actorname.isEmpty()||Moviename.isEmpty())
        {
            Toast.makeText(AddMovieActor.this,"Adding failed. Please re-check your input information ",Toast.LENGTH_SHORT).show();
        }
        else {
            Controller c = new Controller();

            int res =c.InsertActorToMovie(Actorname,Moviename,chname);
            if(res==1){Toast.makeText(AddMovieActor.this,Actorname+" is added successfully as an actor.",Toast.LENGTH_SHORT).show();
            }
            else if(res==400){Toast.makeText(AddMovieActor.this," Adding failed there is no actor with this name.",Toast.LENGTH_SHORT).show();
            }
            else if(res==404){Toast.makeText(AddMovieActor.this," Adding failed there is no movie with this name.",Toast.LENGTH_SHORT).show();
            }
            else if(res==22){Toast.makeText(AddMovieActor.this," Actor is added to this movie before.",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddMovieActor.this,"Adding failed something gone wrong please check your server.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void FillActorSpinner()throws SQLException
    {
        ArrayList<String> arrayList = new ArrayList <String>();

        ResultSet res = c1.GetActorNames();
        while (!res.equals(null) && res.next())
        {
            arrayList.add(res.getString("member_name"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        ActorSpinner.setAdapter(adapter);

    }

    protected void FillMoviesSpinner()throws SQLException
    {
        ArrayList<String> arrayList = new ArrayList <String>();

        ResultSet res = c1.GetMovieNames();
        while (!res.equals(null) && res.next())
        {
            arrayList.add(res.getString("movie_name"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        MovieSpinner.setAdapter(adapter);
    }

}
