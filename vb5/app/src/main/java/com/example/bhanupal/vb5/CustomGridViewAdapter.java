package com.example.bhanupal.vb5;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class CustomGridViewAdapter extends ArrayAdapter<String> {
    int cbjp1;
    int cinc1,caap1,count1,cbsp1,cip1,cjp1;
    Context context;
    Context context1;
    RequestQueue mQueue11;
    VoteCandidate vc;
    int i=0;

    final static String Url1="http://bhautikng143.comlu.com/candidate2.php";

    //List<Integer> layoutResourceId = new ArrayList<Integer>();
    List<String> data = new ArrayList<String>();
    String city;
    private String username;


    public CustomGridViewAdapter(Context context,String city,
                                 List<String> data,int cbjp1,int cinc1,int caap1,int cbsp1,int cip1,int cjp1,int count1) {
        super(context, R.layout.custgrid, data);
        this.city = city;
        this.context = context;
        this.data = data;
        this.cbjp1 = cbjp1;
        this.cinc1 = cinc1;
        this.caap1 = caap1;
        this.cbsp1 = cbsp1;
        this.cip1 = cip1;
        this.cjp1 = cjp1;
        this.count1 = count1;

        mQueue11 = CustomVolleyRequestQueue.getInstance(context)
                .getRequestQueue();
    }
   /* public CustomGridViewAdapter(Context context,int cbjp1,int cinc1,int caap1,int cbsp1,int cip1,int cjp1,int count1) {

        super(context,cbjp1,cinc1,caap1,cbsp1,cip1,cjp1,count1);
        //this.city = city;
        this.context = context;
        this.cbjp1 = cbjp1;
        this.cinc1 = cinc1;
        this.caap1 = caap1;
        this.cbsp1 = cbsp1;
        this.cip1 = cip1;
        this.cjp1 = cjp1;
        this.count1 = count1;
    }*/





    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custgrid, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.votername);
            holder.imageString = (ImageView) row.findViewById(R.id.voterimg);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        String name = data.get(position);
        int index = name.indexOf(":");


        if(index!=-1){
            String party = name.substring(0,index);
            String cname= name.substring(index+1);

            holder.txtTitle.setText(cname+"\n"+party);
        }
        else {
            holder.txtTitle.setText("Error");
        }
        final String party = name.substring(0,index);
        final String cname= name.substring(index+1);
        final String url = geturl(city, cname);

        final VoteCandidate vt=new VoteCandidate();
        //holder.imageString.setImageResource(url);
        Glide.with(context).load(url).placeholder(R.drawable.placeholder).into(holder.imageString);
        holder.imageString.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(i==0)
                {
                    i=1;
                count1=count1+1;
//                Toast.makeText(context,count1,Toast.LENGTH_SHORT).show();
                if (party.equalsIgnoreCase("BJP")) {
                    cbjp1 = cbjp1 + 1;
                    //count++;
                    volleyconnect2(Url1);
                    Toast.makeText(context, cname + ":" + cbjp1 + "/" + count1, Toast.LENGTH_SHORT).show();

                } else if (party.equalsIgnoreCase("INC")) {
                    cinc1 = cinc1 + 1;
                    //count++;
                    volleyconnect2(Url1);
                    Toast.makeText(context, cname + ":" + cinc1 + "/" + count1, Toast.LENGTH_SHORT).show();
                } else if (party.equalsIgnoreCase("AAP")) {
                    caap1 = caap1 + 1;
                    //count++;
                    volleyconnect2(Url1);
                    Toast.makeText(context, cname + ":" + caap1 + "/" + count1, Toast.LENGTH_SHORT).show();
                } else if (party.equalsIgnoreCase("BSP")) {
                    cbsp1 = cbsp1 + 1;
                    volleyconnect2(Url1);
                    //count++;
                    Toast.makeText(context, cname + ":" + cbsp1 + "/" + count1, Toast.LENGTH_SHORT).show();
                } else if (party.equalsIgnoreCase("IP")) {
                    cip1 = cip1 + 1;
                    volleyconnect2(Url1);
                    // count++;
                    Toast.makeText(context, cname + ":" + cip1 + "/" + count1, Toast.LENGTH_SHORT).show();
                } else if (party.equalsIgnoreCase("JP")) {
                    cjp1 = cjp1 + 1;
                    volleyconnect2(Url1);
                    Toast.makeText(context, cname + ":" + cjp1 + "/" + count1, Toast.LENGTH_SHORT).show();


                }


               // vc.volleyconnect2(Url1);
                // Intent k=new Intent(view.getContext(),home.class);
                // view.getContext().startActivity(k);

            }
            else
                {
                    Toast.makeText(context, "sorry you are already given your vote", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return row;

    }

    public String geturl(String city,String name){
        String url = "http://bhautikng143.comlu.com/images/"+city+"/"+name+".jpg";
        Log.i("Url", url);
        return url;
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

                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(context, "could not fetch data", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        mQueue11.add(req);
    }




    static class RecordHolder {
        TextView txtTitle;
        ImageView imageString;

    }



}
