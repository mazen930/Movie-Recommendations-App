package com.example.android.break2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class Login extends Activity
{

    public int  priv = -1;
    EditText email_username_txt;
    EditText password_txt;
    Controller c ;
    public static String userName;
    public static String Email;
    public static int activeForm=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        email_username_txt =  findViewById(R.id.email_username);
        password_txt = findViewById(R.id.password);
        activeForm=1;
    }

    public void loginFucntion(View view) throws SQLException {
        c = new Controller();
        String username_email_str = email_username_txt.getText().toString();
        userName=username_email_str;
        String password_str = password_txt.getText().toString();
        int[] login_Result = (c.LoginCheck(username_email_str, password_str));
        if (login_Result != null )
        {
            priv=login_Result[0]; // to be edited
            int userID = login_Result[1];
            if (priv==1 || priv==2) {
                Toast.makeText(Login.this, "Successfully login", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Home.class);
                i.putExtra("userID",userID);
                i.putExtra("privileges",priv);
                startActivity(i);
                this.finish();
                ResultSet email;
                if(priv==1)
                    email=c.getEmailAddressAdmins(username_email_str);
                else
                    email=c.getEmailAddressFollowers(username_email_str);
                if(email!=null&&email.next())
                {
                    Email=email.getString("email");
                }
            }
        }
        else {

            Toast.makeText(Login.this, "Failed to login please re-check your info", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}