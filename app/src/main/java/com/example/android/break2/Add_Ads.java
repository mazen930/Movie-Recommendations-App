package com.example.android.break2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class Add_Ads extends Activity {
    int adminID=1;
    EditText title_ET ,compName_ET,stDate_ET,endDate_ET,content_ET,price_ET,url_ET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__ads);

        Bundle info = getIntent().getExtras();
        adminID=info.getInt("adminID");
    }
    public  void AddNewAdv(View view)throws SQLException
    {
        title_ET = findViewById(R.id.advTitle);
        compName_ET = findViewById(R.id.compName);
        stDate_ET = findViewById(R.id.startDate_adv);
        endDate_ET = findViewById(R.id.endDate_adv);
        content_ET = findViewById(R.id.advContent);
        price_ET = findViewById(R.id.advPrice);
        url_ET = findViewById(R.id.advUrl);

        if(title_ET.getText().toString().isEmpty() ||
                compName_ET.getText().toString().isEmpty()||
                stDate_ET.getText().toString().isEmpty() ||
                endDate_ET.getText().toString().isEmpty() ||
                content_ET.getText().toString().isEmpty() ||
                price_ET.getText().toString().isEmpty() ||
                url_ET.getText().toString().isEmpty()
                )
        {
            Toast.makeText(Add_Ads.this, "Please enter full info .", Toast.LENGTH_SHORT).show();
        }
        else{
            Controller c=new Controller();
            int result=c.InsertAdv(title_ET.getText().toString(),
                    compName_ET.getText().toString(),
                    stDate_ET.getText().toString(),
                    endDate_ET.getText().toString(),
                    content_ET.getText().toString(),
                    price_ET.getText().toString(),
                    url_ET.getText().toString(),adminID);

            if(result==1)
            {
                Toast.makeText(Add_Ads.this, "Successfully added new advertisement", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(Add_Ads.this, "The adv. has not been added something gone wrong .It might be added before", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //c.InsertAdv()
}
