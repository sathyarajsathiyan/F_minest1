package com.example.minest1;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.minest1.HomeAdapter.HistoryAdapter;
import com.example.minest1.HomeAdapter.HistoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VirtualCloset extends DashbordMain {
    RecyclerView Histroy_recyclerView;
    private HistoryAdapter pHistoryAdapter;
    private ArrayList<HistoryItem> mHistoryItems;

    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_closet);
        Histroy_recyclerView = findViewById(R.id.Histroy_recyclerView);
        Histroy_recyclerView.setHasFixedSize(true);
        Histroy_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mHistoryItems = new ArrayList<>();
        DisplayHistory();

    }

    private void DisplayHistory() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String Url = "http://192.168.1.4:4000/Wornhis";
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mHistoryItems.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject Dress = response.getJSONObject(i);
                        String topImageUrl = Dress.getString("image1");
                        String botImageUrl = Dress.getString("image2");
                        String Date = Dress.getString("date");
                        HistoryItem history = new HistoryItem(topImageUrl,botImageUrl,Date);
                        mHistoryItems.add(history);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                pHistoryAdapter=new HistoryAdapter(getApplicationContext(),mHistoryItems) ;
                Histroy_recyclerView.setAdapter(pHistoryAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    public void onBackPressed() {
        count++;
        if (count >= 1) {
            Intent intent = new Intent(VirtualCloset.this, DashbordMain.class);
            startActivity(intent);
            finish();
            super.onBackPressed();
        }


    }
}
