package com.example.bhanupal.vb5;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VoteCandidate extends AppCompatActivity {

    GridView gv;
    List<String> votername;
    RequestQueue mQueue11;
    private ProgressDialog pDialog;
    CustomGridViewAdapter adapter;
    CustomGridViewAdapter adapter1;
    private static final String Url="http://bhautikng143.comlu.com/candidate.php";
    final static String Url1="http://bhautikng143.comlu.com/candidate2.php";
    //final static String Url2="http://bhautikng143.comlu.com/candidate2.php";

    String city="";
    String cbjp,cinc,cip,cjp,caap,cbsp,count;
    int cbjp1;
    int cinc1;
    int cip1;
    int cjp1;
    int caap1;
    int cbsp1;
    int count1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_candidate);
        gv = (GridView) findViewById(R.id.gridView);

        votername = new ArrayList<>();
        mQueue11 = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        city = getIntent().getExtras().getString("city");
        Log.e("here city is ",city);
        startprogress();
        volleyconnect1(Url);
        //volleyconnect2(Url1);



    }


    public void startprogress(){
        pDialog = new ProgressDialog(VoteCandidate.this);

        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    private void stopProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }


    public void volleyconnect1(String url){

        JSONObject jo= new JSONObject();
        // String bjp,aap,inc,bsp,ip,jp="";
        try {
            jo.put("city",city);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        com.android.volley.toolbox.JsonArrayRequest req = new com.android.volley.toolbox.JsonArrayRequest(Request.Method.POST, url,jo, new Response.Listener<JSONArray>()

        {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    JSONObject jresponse = null;
                    try {
                        jresponse = response.getJSONObject(i);
                        votername.add("BJP:"+jresponse.getString("BJP"));
                        votername.add("INC:"+jresponse.getString("INC"));
                        votername.add("AAP:"+jresponse.getString("AAP"));
                        votername.add("BSP:"+jresponse.getString("BSP"));
                        votername.add("IP:"+jresponse.getString("IP"));
                        votername.add("JP:"+jresponse.getString("JP"));
                        cbjp = (jresponse.getString("cbjp"));

                        cinc = (jresponse.getString("cinc"));
                        caap = (jresponse.getString("caap"));
                        cbsp = (jresponse.getString("cbsp"));
                        cip = (jresponse.getString("cip"));
                        cjp = (jresponse.getString("cjp"));
                        count=(jresponse.getString("count"));
                        //Toast.makeText(getApplicationContext(),"cbj is"+cbjp,Toast.LENGTH_LONG).show();
                        //System.out.print(cbjp);
                        cbjp1=Integer.parseInt(cbjp);
                        cinc1=Integer.parseInt(cinc);
                        caap1=Integer.parseInt(caap);
                        cbsp1=Integer.parseInt(cbsp);
                        cip1=Integer.parseInt(cip);
                        cjp1=Integer.parseInt(cjp);
                        count1=Integer.parseInt(count);
                        Toast.makeText(getApplicationContext(),"count1 is"+count1,Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                adapter = new CustomGridViewAdapter(getApplicationContext(),city,votername,cbjp1, cinc1, caap1, cbsp1, cip1, cjp1, count1);
                stopProgressDialog();
                gv.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        stopProgressDialog();
                        Toast.makeText(getApplicationContext(), "could not fetch data", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue11.add(req);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vote_candidate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void volleyconnect2(String Url1){

        JSONObject jo= new JSONObject();
        // String bjp,aap,inc,bsp,ip,jp="";
        try {
            jo.put("city",city);
            jo.put("cbjp",cbjp1);
            jo.put("cinc",cinc1);
            jo.put("caap",caap1);
            jo.put("cbsp",cbsp1);
            jo.put("cip",cip1);
            jo.put("cjp",cjp1);
            jo.put("count",count1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        com.android.volley.toolbox.JsonArrayRequest req = new com.android.volley.toolbox.JsonArrayRequest(Request.Method.POST, Url1,jo, new Response.Listener<JSONArray>()

        {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    JSONObject jresponse = null;
                    try {
                        jresponse = response.getJSONObject(i);



                        //Toast.makeText(context,"sucess",Toast.LENGTH_LONG).show();
                        //System.out.print(cbjp);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "could not fetch data", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue11.add(req);
    }







}
