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

public class Wishlist extends AppCompatActivity {
    int userID, privi;
    ListView List;
    public static String nameOfItem;
    public  static int wishList_active=0;
    boolean cliked =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Bundle info = getIntent().getExtras();
        userID=info.getInt("ID");
        privi=info.getInt("Privileges");
        List=(ListView)findViewById(R.id.Listview1);
        try
        {
            ViewWish();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    protected void ViewWish() throws SQLException {

        final Controller c = new Controller();
        ResultSet result = c.ViewWishlist(userID);
        final ArrayList<String> arrayList = new ArrayList<>();
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
                        deleteres = c.DeleteFromWsihlist(userID, movieID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (deleteres != -1)
                {
                    Toast.makeText(Wishlist.this, " Selected item has been deleted.", Toast.LENGTH_SHORT).show();
                    try {
                        ViewWish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                cliked=true;
                return true;
            }
        });
        if(!cliked) {
            List.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = String.valueOf(parent.getItemAtPosition(position));
                            //positions starts from zero
                            if (position <= 100) {
                                //code specific to any list item
                                nameOfItem = arrayList.get(position);
                                Intent myIntent = new Intent(view.getContext(), ViewMovie.class);
                                wishList_active++;
                                startActivityForResult(myIntent, 0);
                            }//to do set the rest of postions
                        }
                    });
        }
    }
}
