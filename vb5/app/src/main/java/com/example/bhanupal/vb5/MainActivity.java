package com.example.bhanupal.vb5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String LOGIN_URL = "http://bhautikng143.comlu.com/loginnew.php";    //url of your php file
    private EditText user, pass;
    private Button aLogin;

    RequestQueue mQueue11;
    private ProgressDialog pDialog;
    private String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("VoteBot");
        String appVersion = "v1";
    	//Backendless.initApp( this,093DF326-19D7-9239-FFFB-505F826BD800,A47821CC-94F7-7737-FFEA-D42AE81EAC00, appVersion );
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        aLogin = (Button) findViewById(R.id.button);
        mQueue11 = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        BackendlessUser user = new BackendlessUser();
	user.setEmail( "bhanupalsingh@gmail.com" );
	user.setPassword( "sunlight1" );
 
Backendless.UserService.register( user, new BackendlessCallback<BackendlessUser>()
{
    @Override
    public void handleResponse( BackendlessUser backendlessUser )
    {
      Log.i("Registration", backendlessUser.getEmail() + " successfully registered");
    }
} );

        aLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startprogress();
                volleyconnect();
            }
        });


    }
    public void startprogress(){
        pDialog = new ProgressDialog(MainActivity.this);

        pDialog.setMessage("Attempting for login...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }


    public String getstr(EditText et){
        String st1 = et.getText().toString();
        return st1;
    }

    private void volleyconnect(){
        username=getstr(user);
        password=getstr(pass);


        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password",password);

        JsonObjectRequest req = new JsonObjectRequest(LOGIN_URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            pDialog.dismiss();
                            int i = response.getInt("success");
                            String msg = response.getString("message");
                            if(i==1){
                           Intent j = new Intent(MainActivity.this, home.class);
       				 startActivity(j);}
                            /*if(i==1){
                                Intent ii = new Intent(Admin.this, Admin_Panel.class);
                                //ii.putExtra("name",username);
                                finish();
                                // this finish() method is used to tell android os that we are done with current //activity now! Moving to other activity
                                startActivity(ii);
                            }*/

                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue11.add(req);
    }

    public void registration(View view) {
        Intent i = new Intent(this, Register.class);
        startActivity(i);

    }
}
