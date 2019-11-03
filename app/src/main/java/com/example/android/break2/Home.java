package com.example.android.break2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public int arr[]=new int[5];
    int active=0;
    public static int userID,privi;
    GridLayout mainGrid;
    Controller obj=new Controller();
    Login l=new Login();
    SignUp su=new SignUp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Bundle info = getIntent().getExtras();
        userID = info.getInt("userID");
        privi =info.getInt("privileges");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headView=navigationView.getHeaderView(0);
        TextView t1=headView.findViewById(R.id.username123);
        TextView t2=headView.findViewById(R.id.userEmail);
        if(l.activeForm==1) {
            t1.setText(l.userName);
            t2.setText(l.Email);
            l.activeForm=0;
        }
        else
        {
            t1.setText(su.usernametxt.getText());
            t2.setText(su.emailtxt.getText());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem register =menu.findItem(R.id.addfromHome);;
        if(privi==2)
        {
            register.setVisible(false);

        }
        MenuItem register1 =menu.findItem(R.id.updatefromHome);;
        if(privi==2)
        {
            register1.setVisible(false);
        }
        MenuItem register2 =menu.findItem(R.id.deletefromHome);;
        if(privi==2)
        {
            register2.setVisible(false);
        }
        MenuItem register3 =menu.findItem(R.id.Reply);;
        if(privi==2)
        {
            register3.setVisible(false);
        }
        MenuItem register4 =menu.findItem(R.id.RQ);
        if(privi==1)
        {
            register4.setVisible(false);
        }
        MenuItem register5 =menu.findItem(R.id.Wish);
        if(privi==1)
        {
            register5.setVisible(false);
        }
        MenuItem register6 =menu.findItem(R.id.Hist);
        if(privi==1)
        {
            register6.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //noinspection SimplifiableIfStatement
        if (id == R.id.addfromHome) {
            Intent intent = new Intent(this,Adding.class);
            intent.putExtra("ID",userID);
            intent.putExtra("Privileges",privi);

            startActivity(intent);
        }

        if (id == R.id.updatefromHome) {
            Intent intent = new Intent(this,UpdateItems.class);
            intent.putExtra("ID",userID);
            intent.putExtra("Privileges",privi);
            startActivity(intent);
        }
        if (id == R.id.deletefromHome) {
            Intent intent = new Intent(this,DeleteItems.class);
            intent.putExtra("ID",userID);
            intent.putExtra("Privileges",privi);
            startActivity(intent);
        }

        else if (id == R.id.RQ) {
            Intent intent = new Intent(this,AddNewRequest.class);
            intent.putExtra("ID",userID);
            intent.putExtra("Privileges",privi);
            startActivity(intent);

        }

        else if (id == R.id.Reply) {
            Intent intent = new Intent(this, ViewRequestAdmin.class);
            intent.putExtra("ID", userID);
            intent.putExtra("Privileges", privi);
            startActivity(intent);
        }
        else if (id == R.id.changePassword) {
            Intent intent = new Intent(this, ChangePassword.class);
            intent.putExtra("ID", userID);
            intent.putExtra("Privileges", privi);
            startActivity(intent);
        }
        else if (id == R.id.Wish) {
            Intent intent = new Intent(this, Wishlist.class);
            intent.putExtra("ID", userID);
            intent.putExtra("Privileges", privi);
            startActivity(intent);
        }
        else if (id == R.id.Hist) {
            Intent intent = new Intent(this, History.class);
            intent.putExtra("ID", userID);
            intent.putExtra("Privileges", privi);
            startActivity(intent);
        }else if (id == R.id.Stat) {
            Intent intent = new Intent(this, Statistics.class);
            intent.putExtra("ID", userID);
            intent.putExtra("Privileges", privi);
            startActivity(intent);
        }
        if (id == R.id.logout) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this,ListOfItems.class);
            active=1;
            intent.putExtra("temp",1);
            intent.putExtra("followerid",userID);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this,ListOfItems.class);
            intent.putExtra("temp",2);
            active=1;
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this,ListOfItems.class);
            intent.putExtra("temp",3);
            active=1;
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this,ListOfItems.class);
            intent.putExtra("temp",4);
            active=1;
            startActivity(intent);
        } else if (id == R.id.nav_Cinema) {
            Intent intent = new Intent(this,ListOfItems4.class);
            intent.putExtra("temp",5);
            active=1;
            startActivity(intent);
        }
        else if(id==R.id.nav_Search){
            Intent intent = new Intent(this,Search.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void getMostWished(View view) {
       Intent intent=new Intent(this,ListOfItems3.class);
       intent.putExtra("type",1);
       startActivity(intent);

    }
    public void getMostViewed(View view) {
        Intent intent=new Intent(this,ListOfItems3.class);
        intent.putExtra("type",2);
        startActivity(intent);
    }
    public void getMostRated(View view) {
        Intent intent=new Intent(this,ListOfItems3.class);
        intent.putExtra("type",3);
        startActivity(intent);

    }
    public void getNewcRelased(View view) {
        Intent intent=new Intent(this,ListOfItems3.class);
        intent.putExtra("type",4);
        startActivity(intent);

    }


}
