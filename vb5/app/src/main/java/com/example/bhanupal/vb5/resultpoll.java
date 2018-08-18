package com.example.bhanupal.vb5;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class resultpoll extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private PieChart mChart;
    String cbjp,cinc,caap,cbsp,cip,cjp;

    private static final String Url4="http://bhautikng143.comlu.com/candidate3.php";
    String city;
    String city1="";
    int count2;
    LinkedList<String> votecount;
    // we're going to display pie chart for smartphones martket shares
    public int[] yData;//={0,10,20,30,40,50} ;
    private String[] xData = {"BJP", "INC", "AAP", "BSP", "IP", "JP"};
    private RequestQueue mQueue11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultpoll);
        city = getIntent().getExtras().getString("city");
        votecount = new LinkedList<>();

        mainLayout = (RelativeLayout) findViewById(R.id.relative);
        mChart = new PieChart(this);
        mChart = (PieChart) findViewById(R.id.c1);
        mQueue11 = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        volleyconnect1(Url4);
        // add pie chart to main layout
        //mainLayout.addView(mChart);
        mainLayout.setBackgroundColor(Color.parseColor("#55656C"));
        //mChart.setMinimumHeight(1000);
        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Voting Account");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(3);
        mChart.setTransparentCircleRadius(50);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(resultpoll.this,
                        xData[e.getXIndex()] + " = " + yData[e.getXIndex()] + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data


        // customize legends
        Legend l = mChart.getLegend();

        l.setPosition(LegendPosition.RIGHT_OF_CHART);

        l.setXEntrySpace(17);
        l.setYEntrySpace(15);
    }


    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Voting Analysis");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());

        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }


    public void volleyconnect1(String url) {

        JSONObject jo = new JSONObject();
        //username=getstr(et2);
        try {
            jo.put("city", city);
            Log.e("bhanu",city);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("bhanu", "error");
        }

        com.android.volley.toolbox.JsonArrayRequest req = new com.android.volley.toolbox.JsonArrayRequest(Request.Method.POST, url,jo, new Response.Listener<JSONArray>()

        {

            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    JSONObject jresponse = null;
                    try {
                        jresponse = response.getJSONObject(i);
                        //city1=jresponse.getString("city");

                        cbjp = (jresponse.getString("cbjp"));
                        cinc = (jresponse.getString("cinc"));
                        caap = (jresponse.getString("caap"));
                        cbsp = (jresponse.getString("cbsp"));
                        cip = (jresponse.getString("cip"));
                        cjp = (jresponse.getString("cjp"));
                        //count=(jresponse.getString("count"));
                        //Toast.makeText(getApplicationContext(),"cbj is"+cbjp,Toast.LENGTH_LONG).show();
                        //System.out.print(cbjp);
                        yData= new int[]{Integer.parseInt(cbjp), Integer.parseInt(cinc), Integer.parseInt(cinc), Integer.parseInt(cinc), Integer.parseInt(cinc), Integer.parseInt(cinc)};
                        // yData[0]=Integer.parseInt(cbjp);
                        // yData[1]=Integer.parseInt(cinc);
                        // yData[2]=Integer.parseInt(caap);
                        //yData[3]=Integer.parseInt(cbsp);
                        //yData[4]=Integer.parseInt(cip);
                        //yData[5]=Integer.parseInt(cjp);
                        //yData[0]=Integer.parseInt(count);

                        addData();
                        //Toast.makeText(getApplicationContext(),yData[0]+yData[1]+yData[2]+yData[3]+yData[4]+yData[5],Toast.LENGTH_LONG).show();
                        // votecount.pollFirst();
                        //System.out.println(yData);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //stopProgressDialog();
                        Toast.makeText(getApplicationContext(), "could not fetch data", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue11.add(req);


    }
}
