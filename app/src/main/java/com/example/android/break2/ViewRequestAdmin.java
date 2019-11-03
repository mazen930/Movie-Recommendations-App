package com.example.android.break2;

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

public class ViewRequestAdmin extends AppCompatActivity {
    int userID, privi;
    ListView List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request_admin);
        Bundle info = getIntent().getExtras();
        userID=info.getInt("ID");
        privi=info.getInt("Privileges");
        List=(ListView)findViewById(R.id.Listview1);
        try
        {
            ViewAllRequests();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    protected void ViewAllRequests() throws SQLException {

        final Controller c = new Controller();
        ResultSet result = c.ViewAllRequests();
        final ArrayList<String> arrayList = new ArrayList<>();
        while (result.next()) {
            arrayList.add(result.getString("req_movie_name"));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        List.setAdapter(arrayAdapter);

        List.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView <?> parent, View view, int position, long id) {

                int requestID = -1;
                int deleteres = -1;
                try {
                    requestID = c.SelectRequestIDByName(arrayList.get(position));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (requestID != -1) {
                    try {
                        deleteres = c.DeleteFromRequest( requestID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (deleteres != -1)
                {
                    Toast.makeText(ViewRequestAdmin.this, " Selected item has been deleted.", Toast.LENGTH_SHORT).show();
                    try {
                        ViewAllRequests();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });


    }
}
