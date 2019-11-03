package com.example.android.break2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ListOfItems3 extends AppCompatActivity {
    Controller obj=new Controller();
    public static String  nameOfItem;
    String[] nameArray =new String[100];
    ListView listView;
    int active=0;
    public static int Listitem3_active=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_items);
        Bundle info = getIntent().getExtras();
        active=info.getInt("type");
        if(active==1) {
            try {
                nameArray = setnames(obj.getMostWished());//send resultset in parameter
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(active==2)
        {
            try {
                nameArray = setnames(obj.getMostViewed());//send resultset in parameter
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(active==3)
        {
            try {
                nameArray = setnames(obj.getMostRated());//send resultset in parameter
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                nameArray = setnames(obj.getNewReales());//send resultset in parameter
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Customlistadapter Names = new Customlistadapter(this, nameArray);
        listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(Names);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(ListOfItems3.this, item, Toast.LENGTH_LONG).show();
                        //positions starts from zero
                        if (position <= 100) {
                            //code specific to any list item
                            nameOfItem=nameArray[position];
                            Intent myIntent = new Intent(view.getContext(), ViewMovie.class);
                            Listitem3_active++;
                            startActivityForResult(myIntent, 0);
                        }
                        //to do set the rest of postions
                    }


                });
    }
    public String[] setnames(ResultSet name) throws SQLException {
        int i=0;
        Vector<String> vec=new Vector<>();
        while(name!=null&&name.next())
        {
            vec.add( name.getString(1));
            i++;

        }
        String[]Names=new String[vec.size()];
        for(int j=0;j<vec.size();j++)
            Names[j]= vec.get(j);
        return Names;
    }

}
