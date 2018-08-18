package com.example.bhanupal.vb5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class home extends AppCompatActivity
{
  public static String EXTRA_MESSAGE;
  public static String consName;
  public static String stateName;
  public static String vote_Status = null;
String city="";

  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ImageButton imageView1=(ImageButton)findViewById(R.id.btnVote);
    ImageButton imageView2=(ImageButton)findViewById(R.id.btnResult);
    ImageButton imageView3=(ImageButton)findViewById(R.id.btnPoll);
    ImageButton imageView4=(ImageButton)findViewById(R.id.btnElection);
    ImageButton imageView5=(ImageButton)findViewById(R.id.btnPromo);
    ImageButton imageView6=(ImageButton)findViewById(R.id.btnAbout);
    city=getIntent().getExtras().getString("city");
    Log.e("home city is ", city);
    Toast.makeText(getApplicationContext(),"city is "+city,Toast.LENGTH_LONG).show();
    imageView1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i1=new Intent(home.this,VoteCandidate.class);
        i1.putExtra("city", city);

        startActivity(i1);

      }
    });
    imageView2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i1=new Intent(home.this,result.class);
        startActivity(i1);

      }
    });
    imageView3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i1=new Intent(home.this,poll.class);
        startActivity(i1);

      }
    });
    imageView4.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i1=new Intent(home.this,election2014.class);
        startActivity(i1);

      }
    });
    imageView5.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i1=new Intent(home.this,promo.class);
        startActivity(i1);

      }
    });
    imageView6.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i1=new Intent(home.this,About.class);
        startActivity(i1);

      }
    });





  }
}
