package com.example.bhanupal.vb5;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends Activity {
    RequestQueue mQueue11;
    EditText fullName, email, password, mobile, address;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    Button submit;
    private ProgressDialog pDialog;
    String Name;
    static String Email;
    static String Password;
    static String Mobile;
    String Address;
    String Gender;
    int selectedId;
    final String url = "http://bhautikng143.comlu.com/registerbhanu.php"; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullName = (EditText) findViewById(R.id.full_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        mobile = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.address);
        Name=fullName.getText().toString();
        Email=email.getText().toString();
        Password=password.getText().toString();
        Mobile= mobile.getText().toString();
        Address=address.getText().toString();
       // Gender=radioSexButton.getText().toString();


        submit = (Button)findViewById(R.id.submit);
        //Log.d("Gender",radioSexButton.getText().toString());
        final RequestQueue rq= Volley.newRequestQueue(this);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              //  radioSexGroup=(RadioGroup)findViewById(R.id.gender);
              //  selectedId=radioSexGroup.getCheckedRadioButtonId();
              //  radioSexButton = (RadioButton)findViewById(selectedId);


                startprogress();
                
                Backendless.initApp( this, "093DF326-19D7-9239-FFFB-505F826BD800", "A47821CC-94F7-7737-FFEA-D42AE81EAC00", "v1" );

    			registerUser();
    			registerUserAsync();
    			volleyconnect();
            }});
        mQueue11 = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
    }


 private static void registerUser()
  {
    BackendlessUser user = new BackendlessUser();
    user.setEmail(Email);
    user.setPassword("don");
    user.setProperty("mobileno","9157988852" );

    BackendlessUser registeredUser = Backendless.UserService.register( user );
    System.out.println( "User has been registered - " + registeredUser.getObjectId() );
  }

  private static void registerUserAsync()
  {
    AsyncCallback<BackendlessUser> callback = new AsyncCallback<BackendlessUser>()
    {
      @Override
      public void handleResponse( BackendlessUser registeredUser )
      {
        System.out.println( "User has been registered - " + registeredUser.getObjectId() );
      }

      @Override
      public void handleFault( BackendlessFault backendlessFault )
      {
        System.out.println( "Server reported an error - " + backendlessFault.getMessage() );
      }
    };

    BackendlessUser user = new BackendlessUser();
    user.setEmail(Email);
    user.setPassword(Password);
    user.setProperty("mobileno",Mobile);

    Backendless.UserService.register( user, callback );
  }
    public void startprogress(){
        pDialog = new ProgressDialog(Register.this);

        pDialog.setMessage("Attempting for registering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    private void volleyconnect(){




        JSONObject jo=new JSONObject();

        try {
            jo.put("vid",Name);
            jo.put("em",Email);
            jo.put("password",Password);
            jo.put("pn",Mobile);
            //jo.put("Address",Address);
            //jo.put("Gender",Gender);

        } catch (JSONException e) {
            e.printStackTrace();

        }
        Log.d("data", jo.toString());

        JsonObjectRequest req = new JsonObjectRequest(url,jo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            pDialog.dismiss();
                            int i = response.getInt("success");
                            String msg = response.getString("message");
                            if(i==1){
                                Intent ii = new Intent(Register.this, MainActivity1.class);
                                //ii.putExtra("name",username);
                                finish();
                                // this finish() method is used to tell android os that we are done with current //activity now! Moving to other activity
                                startActivity(ii);
                            }

                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.e("Error: ", error.getMessage());
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue11.add(req);
    }
    public void toast(View v){


        //Toast.makeText(getApplicationContext(),radioSexButton.getText(), Toast.LENGTH_SHORT).show();
    }

}

