package com.example.bhanupal.vb5;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity1 extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
//private String p="093DF326-19D7-9239-FFFB-505F826BD800,A47821CC-94F7-7737-FFEA-D42AE81EAC00";
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    RequestQueue mQueue11;
    private static final String Url="http://bhautikng143.comlu.com/loginnew.php";
    private String city="";
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        mQueue11 = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        ButterKnife.bind(this);

        Backendless.initApp(this, "093DF326-19D7-9239-FFFB-505F826BD800", "A47821CC-94F7-7737-FFEA-D42AE81EAC00", "v1");
        //App42API.initialize(this, "", "");
        Backendless.Messaging.registerDevice("", "default", new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void aVoid) {
                Toast.makeText(MainActivity1.this, "Device Registered", Toast.LENGTH_SHORT).show();
                // ResponderHelper responder = new ResponderHelper();
                PublishOptions publishOptions = new PublishOptions();
                publishOptions.putHeader("android-ticker-text", "You just got a push notification!");
                publishOptions.putHeader("android-content-title", "This is a notification title");
                publishOptions.putHeader("android-content-text", "Push Notifications are cool");
                //  MessageStatus status = Backendless.Messaging.publish( "Hi Devices!", (Object) publishOptions);


                Object h = "Simply posting";

                Backendless.Messaging.publish(h,
                        publishOptions,
                        new AsyncCallback<MessageStatus>() {
                            @Override
                            public void handleResponse(MessageStatus messageStatus) {
                                Log.d("message", messageStatus.toString());
                            }

                            @Override
                            public void handleFault(BackendlessFault backendlessFault) {
                                Log.d("messagefail", backendlessFault.toString());
                            }
                        });


                //BackendlessBroadcastReceiver br  = new BackendlessBroadcastReceiver();
                // br.onReceive(context, );


                // Log.d("this is sta",status.toString());
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.d("FaltRegistration", backendlessFault.toString());
            }
        });





        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                volleyconnect1(Url);
                Toast.makeText(getApplicationContext(),"city is "+city,Toast.LENGTH_LONG).show();
               login();
                //Intent intent = new Intent(getApplicationContext(), home.class);
                //startActivity(intent);
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(),reg1.class);
                startActivity(intent);
            }
        });
    }
    public void volleyconnect1(String url){

        JSONObject jo= new JSONObject();
        // String bjp,aap,inc,bsp,ip,jp="";
        try {
            jo.put("Email",email);
            jo.put("Password",password);
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
                        city=jresponse.getString("city");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "could not fetch data", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue11.add(req);
    }

    public void login(){
        Log.d(TAG, "lOGIN");

       

        _loginButton.setEnabled(false);

        if (!validate()) {
            onLoginFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity1.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();




        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {

        _loginButton.setEnabled(true);

        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();




        Log.d("This is the place", "where it is starteed");

        Backendless.initApp(this, "093DF326-19D7-9239-FFFB-505F826BD800", "A47821CC-94F7-7737-FFEA-D42AE81EAC00","v1"); // where to get the argument values for this call

        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
            public void handleResponse(BackendlessUser user) {
                volleyconnect1(Url);
                Toast.makeText(getApplicationContext(),city, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), home.class);
                intent.putExtra("city",city);
                  startActivity(intent);
            }

            public void handleFault(BackendlessFault fault) {

                Log.e("loginerror", fault.getMessage());
            }
        });

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
