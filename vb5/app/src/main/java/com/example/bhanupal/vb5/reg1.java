package com.example.bhanupal.vb5;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class reg1 extends AppCompatActivity {

  private static final String TAG = "SignupActivity";
  private static final String LOGIN_URL = "http://bhautikng143.comlu.com/registerbhanu.php";
  String N,E,P,C;
  @Bind(R.id.input_name) EditText _nameText;
  @Bind(R.id.input_email) EditText _emailText;
  @Bind(R.id.input_password) EditText _passwordText;
  EditText city1;
  RequestQueue mQueue11;
  @Bind(R.id.btn_signup) Button _signupButton;
  @Bind(R.id.link_login) TextView _loginLink;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);
    city1=(EditText)findViewById(R.id.city);
    mQueue11 = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
            .getRequestQueue();

    ButterKnife.bind(this);


    Backendless.initApp(this,  "093DF326-19D7-9239-FFFB-505F826BD800", "A47821CC-94F7-7737-FFEA-D42AE81EAC00","v1");

    _signupButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        signup();
      }
    });

    _loginLink.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Finish the registration screen and return to the Login activity
        finish();
      }
    });
  }

  public void signup() {
    Log.d(TAG, "Signup");
    volleyconnect();
    Backendless.initApp(this,  "093DF326-19D7-9239-FFFB-505F826BD800", "A47821CC-94F7-7737-FFEA-D42AE81EAC00", "v1");

    if (!validate()) {
      onSignupFailed();
      return;
    }

    _signupButton.setEnabled(false);

    final ProgressDialog progressDialog = new ProgressDialog(reg1.this,
            R.style.AppTheme_Dark_Dialog);
    progressDialog.setIndeterminate(true);
    progressDialog.setMessage("Creating Account...");
    progressDialog.show();

    String name = _nameText.getText().toString();
    String email = _emailText.getText().toString();
    String password = _passwordText.getText().toString();


    // TODO: Implement your own signup logic here.

    Backendless.initApp(this,  "093DF326-19D7-9239-FFFB-505F826BD800", "A47821CC-94F7-7737-FFEA-D42AE81EAC00", "v1"); // where to get the argument values for this call

    BackendlessUser user = new BackendlessUser();
    user.setProperty( "email" , email );
    user.setPassword( password );
    user.setProperty("name",name);

    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
      public void handleResponse(BackendlessUser registeredUser) {

        Toast.makeText(reg1.this, "Sign up Success", Toast.LENGTH_SHORT).show();
        // user has been registered and now can login
      }

      public void handleFault(BackendlessFault fault) {
        String s = fault.getMessage();
        Toast.makeText(reg1.this, "Message : " + s, Toast.LENGTH_SHORT).show();
        // an error has occurred, the error code can be retrieved with fault.getCode()
      }
    });

    new android.os.Handler().postDelayed(
            new Runnable() {
              public void run() {
                // On complete call either onSignupSuccess or onSignupFailed
                // depending on success
                onSignupSuccess();
                // onSignupFailed();
                progressDialog.dismiss();
              }
            }, 3000);
  }


  public void onSignupSuccess() {
    _signupButton.setEnabled(true);
    setResult(RESULT_OK, null);
    finish();
  }

  public void onSignupFailed() {
    Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

    _signupButton.setEnabled(true);
  }

  public boolean validate() {
    boolean valid = true;

    String name = _nameText.getText().toString();
    String email = _emailText.getText().toString();
    String password = _passwordText.getText().toString();

    if (name.isEmpty() || name.length() < 3) {
      _nameText.setError("at least 3 characters");
      valid = false;
    } else {
      _nameText.setError(null);
    }

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
  private void volleyconnect(){
    N=_nameText.getText().toString();
    E=_emailText.getText().toString();
    P=_passwordText.getText().toString();
    //Mobile= mobile.getText().toString();
    C=city1.getText().toString();
    //Gender=radioSexButton.getText().toString();



    JSONObject jo=new JSONObject();

    try {
      jo.put("Name",N);
      jo.put("Email",E);
      jo.put("Password",P);
      //jo.put("Mobile",Mobile);
      jo.put("city",C);
      //jo.put("Gender",Gender);

    } catch (JSONException e) {
      e.printStackTrace();

    }
    Log.d("data", jo.toString());

    JsonObjectRequest req = new JsonObjectRequest(LOGIN_URL,jo,
            new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                try {

                  //pDialog.dismiss();
                  int i = response.getInt("success");
                  String msg = response.getString("message");
                  if(i==1){
                   // Intent ii = new Intent(reg1.this, home.class);
                    //ii.putExtra("name",username);
                    //finish();
                    // this finish() method is used to tell android os that we are done with current //activity now! Moving to other activity
                    //startActivity(ii);
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
       // pDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Network Error", Toast.LENGTH_SHORT).show();
      }
    });
    mQueue11.add(req);
  }
}


