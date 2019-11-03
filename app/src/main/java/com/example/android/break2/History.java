package com.example.android.break2;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class History extends AppCompatActivity {
    int userID, privi;
    ListView List;
    public static String nameOfItem;
    public static int History_ActiveList=0;
    boolean clicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle info = getIntent().getExtras();
        userID=info.getInt("ID");
        privi=info.getInt("Privileges");
        List=(ListView)findViewById(R.id.Listview1);
        try
        {
            ViewHist();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }



    }
    protected void ViewHist() throws SQLException {

        final Controller c = new Controller();
        int size = 0;
        ResultSet result = c.ViewHistory(userID);
        final ArrayList <String> arrayList = new ArrayList <>();
        while (result.next()) {
            arrayList.add(result.getString("movie_name"));
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        List.setAdapter(arrayAdapter);
        List.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView <?> parent, View view, int position, long id) {

                int movieID = -1;
                int deleteres = -1;
                try {
                    movieID = c.SelectMovieIDByName(arrayList.get(position));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (movieID != -1) {
                    try {
                        deleteres = c.DeleteFromHistory(userID, movieID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (deleteres != -1)
                {
                    Toast.makeText(History.this, " Selected item has been deleted.", Toast.LENGTH_SHORT).show();
                    try {
                        ViewHist();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                clicked=true;
                return true;
            }
        });
        if(!clicked)
        {
        List.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(position));
                        //positions starts from zero
                        if (position <= 100) {
                            //code specific to any list item
                            nameOfItem= arrayList.get(position);
                            Intent myIntent = new Intent(view.getContext(), ViewMovie.class);
                            History_ActiveList++;
                            startActivityForResult(myIntent, 0);
                        }//to do set the rest of postions
                    }
                });
        }
    }
}
