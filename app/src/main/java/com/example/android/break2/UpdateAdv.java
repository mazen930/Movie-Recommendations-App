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

public class UpdateAdv extends AppCompatActivity {
    Spinner AllAdsSpinner ; // items to be selected for being deleted
    Controller c=new Controller();
    EditText Title ;
    EditText CompanyName ;
    EditText StartDate,EndDate;
    EditText Content,Price,Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_adv);
        try {
            FillAdvSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void FillAdvSpinner()throws SQLException
    {
        AllAdsSpinner=findViewById(R.id.UpdataAdvSpinner);
        ArrayList<String> arrayList = new ArrayList <String>();
        ResultSet res = c.SelectAllAdvs();
        while (res.next())
        {
            arrayList.add(res.getString("ad_title"));
        }
        String[] array = arrayList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item,array);
        AllAdsSpinner.setAdapter(adapter);
    }

    public void GetAdvDataClick(View view)throws SQLException
    {
        Title =findViewById(R.id.AdvTitleUpdate);
        CompanyName  =findViewById(R.id.CompanyNameUpdate);
        StartDate=findViewById(R.id.StartDateAdvUpdate);
        EndDate=findViewById(R.id.EndDateAdvUpdate);
        Content=findViewById(R.id.AdvContentUpdate);
        Price=findViewById(R.id.AdvPriceUpdate);
        Url=findViewById(R.id.AdvUrlUpdate);
        String AdvName= (String) AllAdsSpinner.getSelectedItem();
        ResultSet DirectorData= c.SelectAdvByName(AdvName);
        if(DirectorData.next()) {
            Title.setText(DirectorData.getString("ad_title"),TextView.BufferType.EDITABLE);
            CompanyName.setText(DirectorData.getString("company_name"),TextView.BufferType.EDITABLE);
            StartDate.setText(DirectorData.getString("ad_start_date"),TextView.BufferType.EDITABLE);
            EndDate.setText(DirectorData.getString("ad_end_date"),TextView.BufferType.EDITABLE);
            Content.setText(DirectorData.getString("ad_content"),TextView.BufferType.EDITABLE);
            Price.setText(DirectorData.getString("price"),TextView.BufferType.EDITABLE);
            Url.setText(DirectorData.getString("url"),TextView.BufferType.EDITABLE);
        }

    }
    public void UpdateAdvClick(View view)throws SQLException
    {
        Title =findViewById(R.id.AdvTitleUpdate);
        CompanyName  =findViewById(R.id.CompanyNameUpdate);
        StartDate=findViewById(R.id.StartDateAdvUpdate);
        EndDate=findViewById(R.id.EndDateAdvUpdate);
        Content=findViewById(R.id.AdvContentUpdate);
        Price=findViewById(R.id.AdvPriceUpdate);
        Url=findViewById(R.id.AdvUrlUpdate);
        String AdvIdentify= (String) AllAdsSpinner.getSelectedItem();
        String title= Title.getText().toString();
        String companyname= CompanyName.getText().toString();
        String startdate= StartDate.getText().toString();
        String enddate= EndDate.getText().toString();
        String content= Content.getText().toString();
        String price= Price.getText().toString();
        String url= Url.getText().toString();

        if(title.isEmpty() || companyname.isEmpty() || startdate.isEmpty() || enddate.isEmpty() ||content.isEmpty() || price.isEmpty() || url.isEmpty() )
            Toast.makeText(UpdateAdv.this,"Please fill all data ",Toast.LENGTH_SHORT).show();
        else
        {
            int res = c.UpdateAdv(title,companyname,startdate,enddate,content,price,url,AdvIdentify);
            if (res==1) {
                Toast.makeText(UpdateAdv.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(UpdateAdv.this,"Something gone wrong ",Toast.LENGTH_SHORT).show();
        }
        FillAdvSpinner();
    }
}

