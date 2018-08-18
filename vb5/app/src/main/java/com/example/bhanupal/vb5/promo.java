package com.example.bhanupal.vb5;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by bhanupal on 5/3/16.
 */
public class promo extends AppCompatActivity {
    ListView listParty;
    String party[]={"BJP","Congress","AAP","CPM","BSP","SP"};
    String partyInfo[]={"The Bharatiya Janata Party is one of the two major political parties in India, along with the Indian National Congress.\n\nPresident: Amit Shah\n\nFounded: April 6,1980",
    "The Indian National Congress is one of two major political parties in India; the other being the Bharatiya Janata Party.\n\nPresident: Sonia Gandhi\n\nFounded: December 28, 1885",
    "Aam Aadmi Party is an Indian political party, formally launched on 26 November 2012, and is currently the ruling party of Delhi.\n\nLeader: Arvind Kejriwal\n\nFounded: November 26, 2012",
    "The Communist Party of India is a communist party in India. In the Indian Communist movement, there are different views on exactly when the Communist Party of India was founded.\n\nFounded: December 25, 1925",
    "The Bahujan Samaj Party is a national political party in India. It was formed mainly to represent Bahujans, referring to people from the Scheduled Castes, Scheduled Tribes and Other Backward Castes as well as minorities.\n\nFounded: 1984",
    "Samajwadi Party is a recognised state political party in India based in the Indian state of Uttar Pradesh. It describes itself as a democratic socialist party.\n\nFounded: October 4, 1992"};

    //int image[]={R.drawable.about};


    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_promo);
        setTitle("Campaign");

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,party);
        listParty=(ListView)findViewById(R.id.listParty);
        listParty.setAdapter(adapter);

        listParty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                frag2 f2= (frag2) getFragmentManager().findFragmentById(R.id.f2);
                f2.setData(partyInfo[position],position);
            }
        });
    }
}
