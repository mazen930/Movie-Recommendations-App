package com.example.android.break2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteItems extends AppCompatActivity {
    Controller c;
    Spinner s_itemtype;
    Spinner Selecteditems ; // items to be selected for being deleted
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_items);
        String[] arraySpinner = new String[]{"Crew Members","Movies","Cinemas","Advertisements"};
        s_itemtype = findViewById(R.id.itemTypespinner);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,arraySpinner);
        s_itemtype.setAdapter(adapter);
        s_itemtype.getSelectedItem();
    }
    public void GetItemsClick (View v)throws SQLException
    {   Selecteditems=findViewById(R.id.itemtobeDeletedspinner);
        c=new Controller();
        String selected_item = (String) s_itemtype.getSelectedItem();
        ArrayList<String> arrayList = new ArrayList <String>();
        if (selected_item=="Crew Members")
        {
            ResultSet res = c.SelectAllCrewMembers();
            while (res.next())
            {
                arrayList.add(res.getString("member_name"));
            }
            String[] array = arrayList.toArray(new String[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
            Selecteditems.setAdapter(adapter);
            Selecteditems.getSelectedItem();
        }
        if (selected_item=="Movies")
        {
            ResultSet res = c.SelectAllMovies();
            while (res.next())
            {
                arrayList.add(res.getString("movie_name"));
            }
            String[] array = arrayList.toArray(new String[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
            Selecteditems.setAdapter(adapter);
            Selecteditems.getSelectedItem();
        }

        else if (selected_item=="Cinemas")
        {
            ResultSet res = c.SelectAllCinemas();
            while (res.next())
            {
                arrayList.add(res.getString("cinema_name"));
            }
            String[] array = arrayList.toArray(new String[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
            Selecteditems.setAdapter(adapter);
            Selecteditems.getSelectedItem();
        }


        else if (selected_item=="Advertisements")
        {
            ResultSet res = c.SelectAllAdvs();
            while (res.next())
            {
                arrayList.add(res.getString("ad_title"));
            }
            String[] array = arrayList.toArray(new String[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
            Selecteditems.setAdapter(adapter);
            Selecteditems.getSelectedItem();
        }

    }
    public void DeleteItemClick(View v)throws SQLException
    {
        Selecteditems=findViewById(R.id.itemtobeDeletedspinner);
        c=new Controller();
        String selected_item_type = (String) s_itemtype.getSelectedItem();
        if (selected_item_type=="Crew Members")
        {
            String deleteme= (String) Selecteditems.getSelectedItem();
            int res =c.DeleteCrewMember(deleteme);
            if (res>=1)
            {
                Toast.makeText(DeleteItems.this,"Successfully item deleted ",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(DeleteItems.this,"Cannot delete something gone wrong ",Toast.LENGTH_SHORT).show();

            }

        }
        else
        if (selected_item_type=="Movies")
        {
            String deleteme= (String) Selecteditems.getSelectedItem();
            int res =c.DeleteMovie(deleteme);
            if (res>=1)
            {
                Toast.makeText(DeleteItems.this,"Successfully item deleted ",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(DeleteItems.this,"Cannot delete something gone wrong ",Toast.LENGTH_SHORT).show();

            }
        }

        else
        if (selected_item_type=="Cinemas")
        {
            String deleteme= (String) Selecteditems.getSelectedItem();
            int res =c.DeleteCinema(deleteme);
            if (res>=1)
            {
                Toast.makeText(DeleteItems.this,"Successfully item deleted ",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(DeleteItems.this,"Cannot delete something gone wrong ",Toast.LENGTH_SHORT).show();

            }
        }

        else
        if (selected_item_type=="Advertisements")
        {
            String deleteme= (String) Selecteditems.getSelectedItem();
            int res = c.DeleteAdv(deleteme); // res has the no of rows effected
            if (res>=1)
            {
                Toast.makeText(DeleteItems.this,"Successfully item deleted ",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(DeleteItems.this,"Cannot delete something gone wrong ",Toast.LENGTH_SHORT).show();

            }
        }



    }
}