package com.example.android.break2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Vector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListOfItems extends AppCompatActivity {
    Controller obj=new Controller();
    public static String  nameOfItem;
    int active=0;
    int any=0;
    public static int id=0;
    String[] nameArray =new String[100];
    Home auxulary=new Home();
    ListView listView;
    public static int Listitem_active=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_items);
        Bundle info = getIntent().getExtras();
        any= info.getInt("temp");
        id=info.getInt("followerid");
        ResultSet Name=GetListName();
        try {
            nameArray=SetNamesToArray(Name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(nameArray==null){
            Toast.makeText(getApplicationContext(), "No items Found", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
        }

        Customlistadapter Names = new Customlistadapter(this, nameArray);
        listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(Names);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(ListOfItems.this, item, Toast.LENGTH_LONG).show();
                            //positions starts from zero
                        if (position <= 100) {
                            //code specific to any list item
                            nameOfItem=nameArray[position];
                            if(active==1)
                            {Intent myIntent = new Intent(view.getContext(), ViewMovie.class);
                            Listitem_active++;
                            startActivityForResult(myIntent, 0);}
                            else
                            {
                                Intent myIntent = new Intent(view.getContext(), ViewCrewMember.class);
                                Listitem_active++;
                                startActivityForResult(myIntent, 0);
                            }
                        }
                        //to do set the rest of postions
                    }


                });
    }
    public ResultSet GetListName()
    {
        ResultSet s;
        if(any==1)
        {
             s = obj.GetMoviesNames();
             active++;

        }
        else if(any==5)
        {
            s = obj.GetCinemaNames();
            active++;
        }
        else
        {
            int type=0;
            if(any==2)type=0;
            else if(any==3)type=1;
            else type=2;
             s = obj.GetMemberNames(type);
                active+=2;
        }
        return s;
    }
    public String[] SetNamesToArray(ResultSet names) throws SQLException {
        int i=0;
        Vector<String> vec=new Vector<>();
        while(names!=null&&names.next())
        {
             vec.add( names.getString(1));
             i++;

        }
        String[]Names=new String[vec.size()];
        for(int j=0;j<vec.size();j++)
            Names[j]= vec.get(j);
        return Names;
    }

    }

