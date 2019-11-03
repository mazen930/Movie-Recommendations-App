package com.example.android.break2;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddAdmin extends Activity {

    EditText adminUsername_ET,adminEmail_ET,adminPass_ET;
    Controller c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

    }

    public void AddAdminClick(View view) throws SQLException {
        adminUsername_ET=findViewById(R.id.adminUsername);
        adminEmail_ET=findViewById(R.id.adminEmail);
        adminPass_ET=findViewById(R.id.adminPass);
        c=new Controller();
        String usernameStr=adminUsername_ET.getText().toString();
        String emailStr=adminEmail_ET.getText().toString();
        String passStr=adminPass_ET.getText().toString();
        if(usernameStr.isEmpty() || emailStr.isEmpty() || passStr.isEmpty()) {
            Toast.makeText(AddAdmin.this,"Failed . Please re-check your input information ",Toast.LENGTH_SHORT).show();
        }
        else {
            int res = c.InsertNewAdmin(usernameStr, emailStr, passStr);
            if (res == 1) {
                Toast.makeText(AddAdmin.this,"Successfully '"+ usernameStr +"' admin added ",Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(AddAdmin.this,"Failed . Something gone wrong . try to change username it might be used before ",Toast.LENGTH_SHORT).show();

            }

        }

    }
}
