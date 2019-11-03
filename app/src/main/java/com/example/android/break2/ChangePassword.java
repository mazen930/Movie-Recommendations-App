package com.example.android.break2;


import android.icu.text.StringSearch;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;

public class ChangePassword extends AppCompatActivity {

    int userID, privi;
    EditText oldpass,newpass, confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Bundle info = getIntent().getExtras();
        userID=info.getInt("ID");
        privi=info.getInt("Privileges");


    }
    public void ChangePasswordClick(View v) throws SQLException {
        oldpass=findViewById(R.id.Oldpass_ET);
        newpass=findViewById(R.id.Newpass_ET);
        confirm=findViewById(R.id.Confirm_ET);

        String oldp = oldpass.getText().toString();
        String newp = newpass.getText().toString();
        String confirmp = confirm.getText().toString();


        if (oldp.isEmpty()||newp.isEmpty()||confirmp.isEmpty()) {
            Toast.makeText(ChangePassword.this, "Adding failed. Please re-check your input information ", Toast.LENGTH_SHORT).show();
        }
        else if (newp.equals(confirmp)){
            Controller c = new Controller();
            String oldpl = oldp.toLowerCase();
            String newpl = newp.toLowerCase();
            String confirmpl = confirmp.toLowerCase();

            int res = c.ChangePass(oldpl,newpl,userID,privi);
            if (res == 1) {
                Toast.makeText(ChangePassword.this, " Password is rested successfully .", Toast.LENGTH_SHORT).show();

            }
            else if(res==77) {
                Toast.makeText(ChangePassword.this, " Password is not correct .", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(ChangePassword.this, "Password reset failed something gone wrong please check your server.", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(ChangePassword.this, " Passwords don't match.", Toast.LENGTH_SHORT).show();

        }
    }

}
