package com.example.android.break2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Search extends AppCompatActivity {
    Controller obj=new Controller();
    TextView DummyParameter;
    public static ResultSet search;
    String[] Names=new String[100];
    int active=0;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }
    public void bindSearch(View view) throws SQLException {
        int id= addListenerOnButton();
        EditText T=(EditText)findViewById(R.id.editText);
        String s= T.getText().toString();
        s.toLowerCase();
        ResultSet check=obj.GetMovieInfo(s);
        ResultSet check1=obj.GetMemberInfo(s);
        if(id==0&&check.next())
        {
            search=obj.GetMovieInfo(s);
            Intent intent = new Intent(this,ListOfItems2.class);
            intent.putExtra("Current",1);
            startActivity(intent);
        }
        else if(id==1&check1.next())
        {
            search=obj.GetMemberInfo(s);
            Intent intent = new Intent(this,ListOfItems2.class);
            intent.putExtra("Current",2);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), "No Result Found", Toast.LENGTH_SHORT).show();
    }
    public int addListenerOnButton() {
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        int radioButtonID= radioSexGroup.getCheckedRadioButtonId();
        View radioButton = radioSexGroup.findViewById(radioButtonID);
        int idx = radioSexGroup.indexOfChild(radioButton);
        return idx;
    }

    }

