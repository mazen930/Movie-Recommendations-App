package com.example.android.break2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Statistics extends AppCompatActivity {
    int Id;
    int priv;
    Controller c = new Controller();
    ArrayList<String> statType = new ArrayList<String>();
    Spinner stat_spinner;
    BarChart bchart;
    ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
    BarDataSet dataSet;
    BarData data;
    ArrayList<String>xLabels =new ArrayList<String>();
    ArrayList<String>yVal = new ArrayList<String>();
    TableLayout t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        stat_spinner =(Spinner)findViewById(R.id.Stat_Spinner);
        bchart = (BarChart)findViewById(R.id.graph);
        t=(TableLayout)findViewById(R.id.table);
        Bundle info = getIntent().getExtras();
        Id = info.getInt("ID");
        priv = info.getInt("Privileges");
        bchart.setVisibility(View.INVISIBLE);
        if(priv==1) //admin
        {
            statType.add("Your Activity");
            statType.add("Database Info");
            ArrayAdapter<String> Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,statType);
            Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stat_spinner.setAdapter(Adapter);
        }
        else if(priv==2)//follower
        {
            statType.add("On Favourite Movie Categories");
            statType.add("On Most Visited Movie Categories");
            statType.add("On Most Rated Movies");
            statType.add("On Most Visited Movies");
            statType.add("On Most Liked Movies");
            statType.add("On Your Activity");
            ArrayAdapter<String> Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,statType);
            Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stat_spinner.setAdapter(Adapter);
        }
    }

    void DrawBarChart(ArrayList<String> xAxisLabel,BarDataSet Set)
    {
        bchart.clear();
        data=new BarData(xAxisLabel,Set);
        XAxis xAxis=bchart.getXAxis();
       // xAxis.setLabelRotationAngle(-90);
        xAxis.setSpaceBetweenLabels(1);
        bchart.setData(data);
        bchart.setTouchEnabled(true);
        bchart.setDragEnabled(true);
        bchart.setScaleEnabled(true);
        bchart.setVisibility(View.VISIBLE);
        bchart.invalidate();

    }

    public void getStat(View view)throws java.sql.SQLException {
        if (priv == 2) {
            switch (stat_spinner.getSelectedItemPosition()) {
                case (0):
                    xLabels.clear();
                    barEntries.clear();
                    yVal.clear();
                    t.setVisibility(View.INVISIBLE);
                    ResultSet r = c.getStatFavCat(Id);
                    xLabels = c.SetNamesToArray(r, yVal);
                    for (int i = 0; i < yVal.size(); i++) {
                        barEntries.add(new BarEntry(Float.parseFloat(yVal.get(i)), i));
                    }
                    dataSet = new BarDataSet(barEntries, "No Of Movies");
                    DrawBarChart(xLabels, dataSet);

                    break;
                case (1):

                    xLabels.clear();
                    barEntries.clear();
                    yVal.clear();
                    t.setVisibility(View.INVISIBLE);
                    ResultSet r1 = c.getStatHistCat(Id);
                    xLabels = c.SetNamesToArray(r1, yVal);
                    for (int i = 0; i < yVal.size(); i++) {
                        barEntries.add(new BarEntry(Float.parseFloat(yVal.get(i)), i));
                    }
                    dataSet = new BarDataSet(barEntries, "No Of Movies");
                    DrawBarChart(xLabels, dataSet);

                    break;
                case (2):

                    xLabels.clear();
                    barEntries.clear();
                    yVal.clear();
                    t.setVisibility(View.INVISIBLE);
                    ResultSet r3 = c.getStatMostRated();
                    xLabels = c.SetNamesToArray(r3, yVal);
                    for (int i = 0; i < yVal.size(); i++) {
                        barEntries.add(new BarEntry(Float.parseFloat(yVal.get(i)), i));
                    }
                    dataSet = new BarDataSet(barEntries, "No Of Movies");
                    DrawBarChart(xLabels, dataSet);

                    break;
                case (3):

                    xLabels.clear();
                    barEntries.clear();
                    yVal.clear();
                    t.setVisibility(View.INVISIBLE);
                    ResultSet r4 = c.getStatMostViewed();
                    xLabels = c.SetNamesToArray(r4, yVal);
                    for (int i = 0; i < yVal.size(); i++) {
                        barEntries.add(new BarEntry(Float.parseFloat(yVal.get(i)), i));
                    }
                    dataSet = new BarDataSet(barEntries, "No Of Movies");
                    DrawBarChart(xLabels, dataSet);

                    break;
                case (4):

                    xLabels.clear();
                    barEntries.clear();
                    yVal.clear();
                    t.setVisibility(View.INVISIBLE);
                    ResultSet r5 = c.getStatMostLiked();
                    xLabels = c.SetNamesToArray(r5, yVal);
                    for (int i = 0; i < yVal.size(); i++) {
                        barEntries.add(new BarEntry(Float.parseFloat(yVal.get(i)), i));
                    }
                    dataSet = new BarDataSet(barEntries, "No Of Movies");
                    DrawBarChart(xLabels, dataSet);

                    break;
                case (5):

                    xLabels.clear();
                    barEntries.clear();
                    yVal.clear();
                    bchart.setVisibility(View.INVISIBLE);
                    t.setVisibility(View.VISIBLE);
                    TextView TVisited, TLiked, TRated, TReq;
                    TVisited = (TextView) findViewById(R.id.VisitedM);
                    TLiked = (TextView) findViewById(R.id.LikedM);
                    TRated = (TextView) findViewById(R.id.RatedM);
                    TReq = (TextView) findViewById(R.id.ReqM);
                    TVisited.setText(c.getVisitedM(Id));
                    TLiked.setText(c.getLikeddM(Id));
                    TRated.setText(c.getRateddM(Id));
                    TReq.setText(c.getReqdM(Id));
                    break;
            }
        }else if (priv==1)
        {
            TableLayout t1 = (TableLayout)findViewById(R.id.table1);
            TableLayout t2 = (TableLayout)findViewById(R.id.table2);
            switch (stat_spinner.getSelectedItemPosition()) {
                case 0:
                    t2.setVisibility(View.INVISIBLE);
                    t1.setVisibility(View.VISIBLE);
                    TextView Mov,Ad,Req;
                    Mov = (TextView)findViewById(R.id.AddedM);
                    Ad = (TextView)findViewById(R.id.AddedAd);
                    Req = (TextView)findViewById(R.id.AnsReq);
                    Mov.setText(c.getAdminActivityInfo(0,Id));
                    Ad.setText(c.getAdminActivityInfo(1,Id));
                    Req.setText(c.getAdminActivityInfo(2,Id));

                    break;
                case 1:
                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.VISIBLE);
                    TextView M,Act,Aut,Dir,Aw,Add,Cin,Fol,Adm;
                    M=(TextView)findViewById(R.id.NoMov);
                    Act=(TextView)findViewById(R.id.NoAct);
                    Aut=(TextView)findViewById(R.id.NoAut);
                    Dir=(TextView)findViewById(R.id.NoDir);
                    Aw=(TextView)findViewById(R.id.NoAw);
                    Add=(TextView)findViewById(R.id.NoAds);
                    Cin=(TextView)findViewById(R.id.NoCin);
                    Fol=(TextView)findViewById(R.id.NoFoll);
                    Adm=(TextView)findViewById(R.id.NoAdmins);
                    M.setText(c.getDatabaseInfo(0));
                    Act.setText(c.getDatabaseInfo(1));
                    Aut.setText(c.getDatabaseInfo(2));
                    Dir.setText(c.getDatabaseInfo(3));
                    Cin.setText(c.getDatabaseInfo(4));
                    Aw.setText(c.getDatabaseInfo(5));
                    Add.setText(c.getDatabaseInfo(6));
                    Fol.setText(c.getDatabaseInfo(7));
                    Adm.setText(c.getDatabaseInfo(8));
                    break;
            }
        }
    }
}
