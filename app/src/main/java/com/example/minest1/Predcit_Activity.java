package com.example.minest1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minest1.util.DressPoJo;
import com.google.android.material.floatingactionbutton.*;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.minest1.HomeAdapter.PopUpPredcitAdapter;
import com.example.minest1.HomeAdapter.PopUpPredcitItems;
import com.example.minest1.HomeAdapter.PreviewItem;
import com.example.minest1.util.UrlClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Predcit_Activity extends AppCompatActivity {
    private ImageView UPloaded_predcit_image;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    private ArrayList<PopUpPredcitItems> mpopUpPredcitItems;
    private Button Predict_image;
    private PopUpPredcitAdapter hpopUpPredcitAdapter;
    RecyclerView Popup_recycelreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predcit_);


        UPloaded_predcit_image = findViewById(R.id.UPload_predcit_image);
        UPloaded_predcit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycler(v);
            }
        });

        Predict_image = findViewById(R.id.Predict_image);
        Predict_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Predcit_Activity.this, "Predcited", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recycler(View view) {

        alertDialogBuilder = new AlertDialog.Builder(Predcit_Activity.this);
        inflater = LayoutInflater.from(Predcit_Activity.this);
        view = inflater.inflate(R.layout.popup_recyeclerview, null);
        Popup_recycelreView = (RecyclerView) view.findViewById(R.id.Popup_recycelreView);
        Popup_recycelreView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        Popup_recycelreView.setLayoutManager(layoutManager);
        mpopUpPredcitItems = new ArrayList<>();

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();


        dialog.show();
        DisplayDress();
        Popup_recycelreView.setLayoutManager(layoutManager);

    }


    private void DisplayDress() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String Url = "http://192.168.1.4:4000/Dress";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //mpopUpPredcitItems.clear();
                for (int i = 0; i < response.length(); i++) {


                    try {
                        JSONObject Dress = response.getJSONObject(i);
                        String imageUrl = UrlClass.IMAGE_BASE_URL + Dress.getString("image");
                        String Type_name = Dress.getString("type_name");
                        String Color = Dress.getString("color");
                        String USer_id=Dress.getString("user_id");
                        int id=Dress.getInt("id");
                        PopUpPredcitItems predcitItems = new PopUpPredcitItems(imageUrl, Type_name, Color,USer_id,id);
                        mpopUpPredcitItems.add(predcitItems);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                hpopUpPredcitAdapter = new PopUpPredcitAdapter(Predcit_Activity.this, mpopUpPredcitItems);
                Popup_recycelreView.setAdapter(hpopUpPredcitAdapter);
                hpopUpPredcitAdapter.setOnItemClickListener(new PopUpPredcitAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        String imageUrl = mpopUpPredcitItems.get(position).getImageUrl();
                        String label = mpopUpPredcitItems.get(position).getType_name();
                        String Color = mpopUpPredcitItems.get(position).getColor();
                        String user_id=mpopUpPredcitItems.get(position).getUser_id();
                        int id= mpopUpPredcitItems.get(position).getId();
                        Picasso.get().load(imageUrl).fit().into(UPloaded_predcit_image);
                        dialog.dismiss();

                        if (label.toLowerCase().equals("t_shirt") || label.toLowerCase().equals("top") || label.toLowerCase().equals("coat") || label.toLowerCase().equals("shirt") || label.toLowerCase().equals("pullover")) {


                            Predcit_bots(imageUrl, label, Color,user_id,id);


                        } else if (label.toLowerCase().equals("trouser")) {
                            Predcit_tops(imageUrl, label, Color,user_id,id);
                            Toast.makeText(Predcit_Activity.this, "" + label, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    private void Predcit_tops(String imageUrl, String label, String color,String Users_id,int id) {
        List<DressPoJo> tops = new ArrayList<DressPoJo>();
        List<DressPoJo> bots = new ArrayList<DressPoJo>();
        
        tops.add(new DressPoJo(imageUrl,label,color,Users_id,id));


    }

    private void Predcit_bots(String imageUrl, String label, String color,String Users_id,int id) {



    }


}