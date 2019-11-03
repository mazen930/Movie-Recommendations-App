package com.example.android.break2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateAuthor extends AppCompatActivity {
    Spinner AllAuthorsSpinner ; // items to be selected for being deleted
    Controller c=new Controller();
    EditText Name ;
    EditText Brief ;
    EditText BirthDate;
    EditText Style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_author);
        try {
            FillActorSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void FillActorSpinner()throws SQLException
    {
        AllAuthorsSpinner=findViewById(R.id.UpdateAuthorSpinner);
        ArrayList<String> arrayList = new ArrayList <String>();

        ResultSet res = c.SelectAllAuthors();
        while (res.next())
        {
            arrayList.add(res.getString("member_name"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        AllAuthorsSpinner.setAdapter(adapter);
    }
    public void GetAuthorDataClick(View view)throws SQLException
    {
        Name = findViewById(R.id.AuthorFullNameUpdate);
        Brief = findViewById(R.id.AuthorBriefUpdate);
        BirthDate=findViewById(R.id.AuthorBirthDateUpdate);
        Style=findViewById(R.id.AuthorStyleUpdate);
        String AuthorName= (String) AllAuthorsSpinner.getSelectedItem();
        ResultSet authorData= c.SelectAuthorByName(AuthorName);
        if(authorData.next()) {
            Name.setText(authorData.getString("member_name"),TextView.BufferType.EDITABLE);
            BirthDate.setText(authorData.getString("birth_date"),TextView.BufferType.EDITABLE);
            Brief.setText(authorData.getString("brief"),TextView.BufferType.EDITABLE);
            Style.setText(authorData.getString("writing_style"),TextView.BufferType.EDITABLE);
        }
    }
    public void UpdateAuthorClick(View view)throws SQLException
    {
        Name = findViewById(R.id.AuthorFullNameUpdate);
        Brief = findViewById(R.id.AuthorBriefUpdate);
        BirthDate=findViewById(R.id.AuthorBirthDateUpdate);
        Style = findViewById(R.id.AuthorStyleUpdate);

        String AuthorIdentify= (String) AllAuthorsSpinner.getSelectedItem();
        String name = Name.getText().toString();
        String bdate=BirthDate.getText().toString();
        String brief=Brief.getText().toString();
        String style = Style.getText().toString();

        if(name.isEmpty() || bdate.isEmpty() || brief.isEmpty() || style.isEmpty()) {
            Toast.makeText(UpdateAuthor.this,"Please fill all data ",Toast.LENGTH_SHORT).show();
        }
        else {
            int res = c.UpdateAuthor(name,bdate,brief,style,AuthorIdentify);
            if (res==1) {
                Toast.makeText(UpdateAuthor.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
                FillActorSpinner();
            }
            else
                Toast.makeText(UpdateAuthor.this,"Something gone wrong ",Toast.LENGTH_SHORT).show();
        }
    }


}

