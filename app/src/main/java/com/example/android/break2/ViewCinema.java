package com.example.android.break2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ViewCinema extends AppCompatActivity {

    Controller obj=new Controller();
    public static String  nameOfItem;
    String[] nameArray =new String[100];
    ListView listView;
    ListOfItems4 obj4=new ListOfItems4();
    int active=0;
    public static int Listitem3_active=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_items);
        try {
            nameArray=setnames(obj.movie_cinema_names(obj4.nameOfItem));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Customlistadapter Names = new Customlistadapter(this, nameArray);
        listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(Names);

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
