package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.Vector;

public class ViewMovie extends AppCompatActivity {
    Controller obj=new Controller();
    ListOfItems obj1=new ListOfItems();
    ListOfItems2 obj2=new ListOfItems2();
    ListOfItems3 obj3=new ListOfItems3();
    Wishlist w=new Wishlist();
    History hi=new History();
    Home h=new Home();

    class  info
    {
        public String Name;
        public String Synopsis;
        public String Url;
        public String Author;
        public String Director;
        public String Actor;
        public String Actress;
        public String RelaseYear;
        public int Age;
        public String Language;
        public String Duration;
        public float Rating;
        public float userAvgRating;
    }
    EditText et;
    int moveId,authorId,directorId;
    info i=new info();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
        ResultSet info;
        et=(EditText)findViewById(R.id.editboxRate);
        if(obj1.Listitem_active==1)
        {
            info=obj.SelectMovieByName(obj1.nameOfItem);
            obj1.Listitem_active=0;
        }
        else if(obj2.Listitem2_active==1)
        {
            info=obj.SelectMovieByName(obj2.nameOfItem);
            obj2.Listitem2_active=0;
        }
        else if(obj3.Listitem3_active==1)
        {
            info=obj.SelectMovieByName(obj3.nameOfItem);
            obj3.Listitem3_active=0;
        }
        else if(w.wishList_active==1)
        {
            info=obj.SelectMovieByName(w.nameOfItem);
            w.wishList_active=0;
        }
        else
        {
            info=obj.SelectMovieByName(hi.nameOfItem);
            hi.History_ActiveList=0;
        }

        try {
            setRow(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            viewData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        obj.InsertInHistoryList(h.userID,moveId);
    }
    public void setRow(ResultSet s) throws SQLException {

        while(s!=null&&s.next())
        {
           i.Name=s.getString("movie_name");
           i.Synopsis=s.getString("movie_description");
           i.Url=s.getString("url");
           i.RelaseYear=s.getString("release_date");
           i.Age=s.getInt("age_resriction");
           i.Language=s.getString("movie_language");
           i.Duration=s.getString("duration");
           i.Rating=s.getFloat("reviewers_rating");
           authorId=s.getInt("authors_id");
           directorId=s.getInt("director_id");
           moveId=s.getInt("movie_id");
           break;
        }

        ResultSet authorName=obj.GetAuthorName(authorId);
        authorName.next();
        i.Author=authorName.getString("member_name");
        ResultSet directname=obj.GetDirectorName(directorId);
        directname.next();
        i.Director=directname.getString("member_name");
        ResultSet actorList=obj.GetActorName(moveId);
        actorList.next();
        i.Actor=actorList.getString("member_name");

    }
    public void viewData() throws SQLException {
        TextView movieName= (TextView)findViewById(R.id.textView4);
        movieName.setText(i.Name);
        TextView actor= (TextView)findViewById(R.id.textView6);
        actor.setText(i.Actor);
        TextView author= (TextView)findViewById(R.id.textView9);
        author.setText(i.Author);
        TextView director= (TextView)findViewById(R.id.textView11);
        director.setText(i.Director);
        TextView synopsis= (TextView)findViewById(R.id.textView3);
        synopsis.setText(i.Synopsis);
        TextView relaseyear= (TextView)findViewById(R.id.textView16);
        relaseyear.setText(i.RelaseYear);
        TextView age= (TextView)findViewById(R.id.textView18);
        age.setText(Integer.toString(i.Age));
        TextView length= (TextView)findViewById(R.id.textView20);
        length.setText(i.Duration);
        TextView rate= (TextView)findViewById(R.id.textView22);
        rate.setText(Float.toString(i.Rating));
        TextView avgrate= (TextView)findViewById(R.id.avguserrate);
        ResultSet s=obj.getAvgUserRating(moveId);
        if(s!=null)
            s.next();
        String averageRate=s.getString("ar");
        String temp="Users Rating : ";
        avgrate.setText(temp+averageRate);
    }
    public void addToWishList(View view)
    {
        obj.InsertInWishList(h.userID,moveId);
    }
    public  void followerRate(View view) throws SQLException {
        obj.recordUserRate(h.userID,moveId,et.getText().toString());
        TextView avgrate= (TextView)findViewById(R.id.avguserrate);
        ResultSet s=obj.getAvgUserRating(moveId);
        if(s!=null)
            s.next();
        String averageRate=s.getString("ar");
        String temp="Users Rating : ";
        avgrate.setText(temp+averageRate);
    }
}
