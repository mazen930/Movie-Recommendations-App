package com.example.android.break2;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.util.ArrayList;

public class AddMovie extends AppCompatActivity {
    ArrayList<StateVO> listVOs;
    ArrayList<String> authors_id ;
    ArrayList<String> authors_names ;
    ArrayList<String> directors_id ;
    ArrayList<String> directors_names ;
    Spinner author_spinner;
    Spinner director_spinner;
    int adminID;
    Controller c;

    EditText movieName_ET,Lang_ET,RDate_ET,Des_ET,ageR_ET,url_ET,reviewersRating_ET,duration_ET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        c= new Controller();
        listVOs = new ArrayList<>();
        authors_id = new ArrayList<String>();
        authors_names = new ArrayList<String>();
        directors_id = new ArrayList<String>();
        directors_names = new ArrayList<String>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        Bundle info = getIntent().getExtras();
        adminID=info.getInt("ID");



        //giving values to genre spinner
        final String[] select_qualification = {
                "Select Movie Genre", "Action", "Comedy", "Documentry", "Drama", "Horror", "Mystery", "Romance", "Science Fiction",
                "Thriller"};
        Spinner Genre_spinner = (Spinner) findViewById(R.id.Genre);

        for (int i = 0; i < select_qualification.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        MyAdapter myAdapter = new MyAdapter(this, 0,
                listVOs);
        Genre_spinner.setAdapter(myAdapter);


        try{
        //author spinner
        author_spinner = (Spinner) findViewById(R.id.Author_Spinner);
        director_spinner = (Spinner)findViewById(R.id.director_spinner);
        ResultSet r = c.authors_names();
        authors_names= c.SetNamesToArray(r,authors_id);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, authors_names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        author_spinner.setAdapter(dataAdapter);

        //director spinner

        ResultSet r1 = c.directors_names();
        directors_names= c.SetNamesToArray(r1,directors_id);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, directors_names);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        director_spinner.setAdapter(dataAdapter1);
        }catch (java.sql.SQLException e){e.printStackTrace();}
    }
    public void AddMovieClick(View v) throws java.sql.SQLException {
        movieName_ET = findViewById(R.id.movieName);
        Lang_ET = findViewById(R.id.movieLang);
        RDate_ET = findViewById(R.id.releaseDate);
        Des_ET = findViewById(R.id.movieDescription);
        ageR_ET = findViewById(R.id.ageRest);
        url_ET = findViewById(R.id.movieUrl);
        reviewersRating_ET = findViewById(R.id.reviewRating);
        duration_ET = findViewById(R.id.movieDuration);


        String name = movieName_ET.getText().toString();
        String language = Lang_ET.getText().toString();
        String rdate = RDate_ET.getText().toString();
        String description = Des_ET.getText().toString();
        String age_rest = ageR_ET.getText().toString();
        String url = url_ET.getText().toString();
        String reviewers = reviewersRating_ET.getText().toString();
        String duration = duration_ET.getText().toString();

        if (name.isEmpty() || language.isEmpty() ||
                rdate.isEmpty() || description.isEmpty() ||
                age_rest.isEmpty() || url.isEmpty() ||
                reviewers.isEmpty() || duration.isEmpty() ) {
            Toast.makeText(AddMovie.this, "Adding failed. Please re-check your input information ", Toast.LENGTH_LONG).show();
        } else {

            String A_id = authors_id.get(author_spinner.getSelectedItemPosition());


            String D_id = directors_id.get(director_spinner.getSelectedItemPosition());
            int res = c.InsertNewMovie(name,description,language,age_rest,rdate,reviewers
                    ,url,duration,adminID,A_id,D_id);

            if (res == 1) {
                Toast.makeText(AddMovie.this, name + " added successfully as a movie.", Toast.LENGTH_SHORT).show();
            } else if (res == 404) {
                Toast.makeText(AddMovie.this, " Adding failed this movie is already added before.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddMovie.this, "Adding failed something gone wrong please check your server.", Toast.LENGTH_SHORT).show();
            }

            int m_id=c.getMovieId(name,rdate);
            if(m_id!=-1){
                for (int i = 0; i < listVOs.size(); i++) {
                    if (listVOs.get(i).isSelected()) {
                        int x = c.Add_Genre(Integer.toString(m_id), listVOs.get(i).getTitle());
                    }

                }
            }

        }


    }




}
