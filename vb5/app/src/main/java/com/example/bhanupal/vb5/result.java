package com.example.bhanupal.vb5;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;

public class result extends AppCompatActivity {


    String[] ct;
    String[] st;
    RequestQueue mQueue11;
    private ProgressDialog pDialog;
    ArrayAdapter<String> cityAdapter;
    Spinner City;
    String cityname="";
    final static String Url="http://bhautikng143.comlu.com/getschool.php";

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_selection);

         City = (Spinner) findViewById(R.id.sp_const);
        Spinner state = (Spinner) findViewById(R.id.sp_state);
        st = getResources().getStringArray(R.array.states_list);
       // state.setOnItemSelectedListener(this);
        mQueue11 = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        /* ArrayList<String> state_options = new ArrayList<String>();
        state_options.add("Gujarat");
        state_options.add("state_2");
        state_options.add("state_3");

        //ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(selection.this, android.R.layout.simple_spinner_item, state_options);
        ArrayAdapter stateAdapter = ArrayAdapter.createFromResource(this,R.array.states_list,android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);
        //state.setOnItemSelectedListener(this);*/
        state.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long id) {

                String stateName = st[position];
                //resetCity(stateName);
                Log.i("State", stateName);
                //startprogress();
                volleyconnect1(Url, stateName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        City.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityname = ct[i];
                Log.i("city:",cityname);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }





    public void volleyconnect1(String url,String state){

        JSONObject jo= new JSONObject();
        //username=getstr(et2);
        try {
            jo.put("state",state);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        com.android.volley.toolbox.JsonArrayRequest req = new com.android.volley.toolbox.JsonArrayRequest(Request.Method.POST, url,jo, new Response.Listener<JSONArray>()

        {
            @Override
            public void onResponse(JSONArray response) {

                ct = new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jresponse = null;
                    try {
                        jresponse = response.getJSONObject(i);
                        ct[i] = jresponse.getString("city");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                cityAdapter = new ArrayAdapter<String>(result.this,android.R.layout.simple_spinner_item, ct);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                City.setAdapter(cityAdapter);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", error.toString());
                        //mTextView.setText("Error: " + error.getMessage());

                        Toast.makeText(getApplicationContext(), "could not fetch data", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue11.add(req);




    }

    public void actionGo(View v){
           Intent i = new Intent(result.this,resultpoll.class);
           i.putExtra("city",cityname);
            startActivity(i);
            finish();
    }

    public void timepass(){

    }


}
