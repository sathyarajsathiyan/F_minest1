package com.example.minest1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.minest1.HomeAdapter.OutfitsAdapter;
import com.example.minest1.HomeAdapter.OutfitsItem;
import com.example.minest1.util.UrlClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tshirt extends AppCompatActivity {
    private String type_name;
    SharedPreferences pref, sharedpreferences;
    private RecyclerView TShirt_recyclerView;
    private OutfitsAdapter mOutfitsAdapter;
    private ArrayList<OutfitsItem> mOutfitsItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt);
        pref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        final String session_id = pref.getString("session", null);

        Intent intent = getIntent();
        if (intent.hasExtra("type_name")) {
            type_name = intent.getStringExtra("type_name");

            TShirt_recyclerView = findViewById(R.id.tShirt_recyclerView);
            TShirt_recyclerView.setHasFixedSize(true);
            TShirt_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mOutfitsItems = new ArrayList<>();
            Listoutfits(type_name, session_id);
            Toast.makeText(Tshirt.this, ""+type_name, Toast.LENGTH_SHORT).show();

        }


    }

    private void Listoutfits(String type, String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String bUrl = "http://192.168.1.4:4000/Dress" + "?type_name=" + type + "&user_id=" + id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, bUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {


                        JSONObject Dress = response.getJSONObject(i);
                        String imageUrl = UrlClass.IMAGE_BASE_URL + Dress.getString("image");
                        String label=Dress.getString("type_name");
                        //Log.d("imageUrl", "imageUrl ");

                        mOutfitsItems.add(new OutfitsItem(imageUrl));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                mOutfitsAdapter = new OutfitsAdapter(getApplicationContext(), mOutfitsItems);
                TShirt_recyclerView.setAdapter(mOutfitsAdapter);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

}
