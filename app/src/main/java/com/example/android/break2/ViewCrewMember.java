package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ViewCrewMember extends AppCompatActivity {
    Controller obj=new Controller();
    ListOfItems obj1=new ListOfItems();
    ListOfItems2 obj2=new ListOfItems2();
    public static int memberId=-1;
    String[] nameArray =new String[100];
    String[] nameArray2 =new String[100];
    ListView listView;
    ListView listView1;
    int memberType=-1;
    class memberInfo
    {
        public String Name;
        public String BirthDate;
        public String Berif;
        public int NoofAwards;
        public int noOfMovies;
    }
    memberInfo i=new memberInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_crew_member);
        ResultSet s;
        if(obj1.Listitem_active==1)
        {
            s=obj.SelectCrewMemberByName(obj1.nameOfItem);
            obj1.Listitem_active=0;
        }
        else
        {
            s=obj.SelectCrewMemberByName(obj2.nameOfItem);
            obj2.Listitem2_active=0;
        }
        try {
            setnamesofmemebrs(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            setnames();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            nameArray=setnames(obj.getAwardName(memberId));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Customlistadapter Names = new Customlistadapter(this, nameArray);
        listView = (ListView) findViewById(R.id.lv2);
        listView.setAdapter(Names);
        String memberIds=Integer.toString(memberId);
        try {
        nameArray2=setnames(obj.getMemberMovies(memberType,memberIds));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Customlistadapter Names1 = new Customlistadapter(this, nameArray2);
        listView1 = (ListView) findViewById(R.id.lv3);
        listView1.setAdapter(Names1);
    }
    public void setnamesofmemebrs(ResultSet name) throws SQLException {
        while(name.next())
        {
            i.Name=name.getString("member_name");
            i.BirthDate=name.getString("birth_date");
            memberId=name.getInt("member_id");
            memberType=name.getInt("member_type");
        }
    }
    public void setnames() throws SQLException {
        TextView memberName =(TextView)findViewById(R.id.textView11);
        memberName.setText(i.Name);
        TextView birthDate =(TextView)findViewById(R.id.textView23);
        birthDate.setText(i.BirthDate);
        ResultSet temp=obj.getNoOfAwardsForMember(memberId);
        ResultSet temp2;
        if(memberType==0)
        {
            temp2=obj.getNoOfMovieForActor(memberId);
        }
        else if(memberType==1)
        {
            temp2=obj.getNoOfMovieForAuthor(memberId);
        }
        else {
            temp2=obj.getNoOfMovieForDirector(memberId);

        }
        if(temp!=null) {
            temp.next();
            i.NoofAwards = temp.getInt(1);
        }
        if(temp2!=null){
        temp2.next();
        i.noOfMovies =temp2.getInt(1);}
        TextView noOfAwards =(TextView)findViewById(R.id.textView25);
        noOfAwards.setText(Integer.toString(i.NoofAwards));
        TextView noOfMovies =(TextView)findViewById(R.id.textView24);
        noOfMovies.setText(Integer.toString(i.noOfMovies));
    }
    public String[] setnames(ResultSet name) throws SQLException {
        Vector<String> vec=new Vector<>();
        while(name!=null&&name.next())
        {
            vec.add( name.getString(1));
        }
        String[]Names=new String[vec.size()];
        for(int j=0;j<vec.size();j++)
            Names[j]= vec.get(j);
        return Names;
    }

}
