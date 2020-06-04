package com.example.minest1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.example.minest1.HomeAdapter.DressCombinationAdapter;
import com.example.minest1.HomeAdapter.PredcitCombinatonnAdapter;
import com.example.minest1.util.CombinationPoJo;
import com.example.minest1.util.DressCombinationClass;
import com.example.minest1.util.DressPoJo;
import com.google.android.material.floatingactionbutton.*;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.common.collect.Lists;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Predcit_Activity extends AppCompatActivity {
    private ImageView UPloaded_predcit_image;
    private PredcitCombinatonnAdapter combinationAdapter;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private ArrayList<CombinationPoJo> mCombinationList;
    private SharedPreferences pref, sharedpreferences;
    private ArrayList<CombinationPoJo> combinationList;
    private RecyclerView Predict_recycler;
    private ArrayList<PopUpPredcitItems> mpopUpPredcitItems;
    //private Button Predict_image;
    private PopUpPredcitAdapter hpopUpPredcitAdapter;
    private RecyclerView Popup_recycelreView;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predcit_);
        Predict_recycler = findViewById(R.id.Predict_recycler);

        Predict_recycler.setHasFixedSize(true);
        Predict_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        combinationList = new ArrayList<>();
        mCombinationList = new ArrayList<>();
        UPloaded_predcit_image = findViewById(R.id.UPload_predcit_image);
        recycler(v);
        UPloaded_predcit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycler(v);
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

        DisplayDress();
        dialog.show();

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
                        String USer_id = Dress.getString("user_id");
                        int id = Dress.getInt("id");
                        PopUpPredcitItems predcitItems = new PopUpPredcitItems(imageUrl, Type_name, Color, USer_id, id);
                        mpopUpPredcitItems.add(predcitItems);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                hpopUpPredcitAdapter = new PopUpPredcitAdapter(Predcit_Activity.this, mpopUpPredcitItems);
                Popup_recycelreView.setAdapter(hpopUpPredcitAdapter);
                hpopUpPredcitAdapter.notifyDataSetChanged();
                hpopUpPredcitAdapter.setOnItemClickListener(new PopUpPredcitAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        String imageUrl = mpopUpPredcitItems.get(position).getImageUrl();
                        String label = mpopUpPredcitItems.get(position).getType_name();
                        String Color = mpopUpPredcitItems.get(position).getColor();
                        String user_id = mpopUpPredcitItems.get(position).getUser_id();
                        int id = mpopUpPredcitItems.get(position).getId();
                        Picasso.get().load(imageUrl).fit().into(UPloaded_predcit_image);
                        dialog.dismiss();
                        if (label.toLowerCase().equals("t_shirt") || label.toLowerCase().equals("top") || label.toLowerCase().equals("coat") || label.toLowerCase().equals("shirt") || label.toLowerCase().equals("pullover")) {


                            Predcit_bots(imageUrl, label, Color, user_id, id);


                        } else if (label.toLowerCase().equals("trouser")) {
                            Predcit_tops(imageUrl, label, Color, user_id, id);

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

    private void Predcit_bots(final String imageUrl, final String label, final String color, final String Users_id, final int id) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        final String date = simpleDateFormat.format(new Date());
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String Url = "http://192.168.1.4:4000/Dress";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mCombinationList.clear();
                List<DressPoJo> tops = new ArrayList<DressPoJo>();
                List<DressPoJo> bots = new ArrayList<DressPoJo>();
                tops.add(new DressPoJo(imageUrl, label, color, Users_id, id));
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject Dress = response.getJSONObject(i);
                        String dress_type = Dress.getString("type_name");
                        String imageUrl = UrlClass.IMAGE_BASE_URL + Dress.getString("image");
                        String type_name = Dress.getString("type_name");
                        String color = Dress.getString("color");
                        String User_id = Dress.getString("user_id");


                        if (dress_type.toLowerCase().equals("trouser")) {

                            bots.add(new DressPoJo(
                                    imageUrl,
                                    Dress.getString("type_name"),
                                    Dress.getString("color"),
                                    Dress.getString("user_id"),
                                    Dress.getInt("id")

                            ));

                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                List<List<DressPoJo>> collection = new ArrayList<List<DressPoJo>>(2);

                collection.add(tops);
                collection.add(bots);
                List<String> redc = new ArrayList<String>(5);
                redc.add("black");
                redc.add("white");
                redc.add("grey");
                redc.add("blue");
                redc.add("cyan");
                List<String> orangec = new ArrayList<String>(5);
                orangec.add("black");
                orangec.add("white");
                orangec.add("grey");
                orangec.add("violet");
                orangec.add("cyan");
                List<String> yellowc = new ArrayList<String>(4);
                yellowc.add("black");
                yellowc.add("white");
                yellowc.add("violet");
                yellowc.add("grey");
                List<String> greenc = new ArrayList<String>(3);
                greenc.add("black");
                greenc.add("white");
                greenc.add("violet");
                List<String> cyanc = new ArrayList<String>(5);
                cyanc.add("black");
                cyanc.add("white");
                cyanc.add("grey");
                cyanc.add("red");
                cyanc.add("magenta");
                List<String> bluec = new ArrayList<String>(4);
                bluec.add("black");
                bluec.add("white");
                bluec.add("grey");
                bluec.add("orange");
                List<String> violetc = new ArrayList<String>(6);
                violetc.add("black");
                violetc.add("white");
                violetc.add("grey");
                violetc.add("orange");
                violetc.add("yellow");
                violetc.add("green");
                List<String> brownc = new ArrayList<String>(3);
                brownc.add("black");
                brownc.add("white");
                brownc.add("orange");
                brownc.add("grey");
                List<String> magentac = new ArrayList<String>(4);
                magentac.add("white");
                magentac.add("blue");
                magentac.add("black");
                magentac.add("grey");
                List<String> whitec = new ArrayList<String>(8);
                whitec.add("black");
                whitec.add("blue");
                whitec.add("red");
                whitec.add("orange");
                whitec.add("violet");
                whitec.add("magenta");
                whitec.add("yellow");
                whitec.add("green");
                List<String> blackc = new ArrayList<String>(8);
                blackc.add("white");
                blackc.add("blue");
                blackc.add("red");
                blackc.add("orange");
                blackc.add("violet");
                blackc.add("magenta");
                blackc.add("green");
                blackc.add("grey");
                List<String> greyc = new ArrayList<String>(6);
                greyc.add("black");
                greyc.add("blue");
                greyc.add("red");
                greyc.add("orange");
                greyc.add("violet");
                greyc.add("magenta");


                Set<List<DressPoJo>> combset = DressCombinationClass.getCombinations(collection);

                List<List<DressPoJo>> comblist = convertToList(combset);

                for (int i = 0; i < comblist.size(); i++) {

                    List<DressPoJo> mlist = comblist.get(i);
                    boolean q = false;

                    DressPoJo topItem = mlist.get(0);
                    DressPoJo botItem = mlist.get(1);
                    String tcolor = topItem.getColor();
                    System.out.println("top:" + tcolor);
                    String bc = botItem.getColor();
                    String bcolor = bc.toLowerCase();
                    System.out.println(bcolor);
                    if (tcolor.toLowerCase().equals("red"))
                        q = redc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("yellow"))
                        q = yellowc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("orange"))
                        q = orangec.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("green"))
                        q = greenc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("cyan"))
                        q = cyanc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("blue"))
                        q = bluec.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("violet"))
                        q = violetc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("brown"))
                        q = brownc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("black"))
                        q = blackc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("grey"))
                        q = greyc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("white"))
                        q = whitec.contains(bcolor);


                    if (q == true) {
                        CombinationPoJo combItem = new CombinationPoJo(topItem.getImage(), topItem.getColor(), topItem.getType_name(), botItem.getImage(), botItem.getColor(), botItem.getType_name(), date);
                        combinationList.add(combItem);
                    }


                }
                java.util.Random random = new java.util.Random();

                for (int x = 0; x < 3; x++) {
                    int random_computer_card = random.nextInt(combinationList.size());
                    mCombinationList.add(combinationList.get(x));


                }
                combinationList.clear();
                combinationAdapter = new PredcitCombinatonnAdapter(Predcit_Activity.this, mCombinationList);
                Predict_recycler.setAdapter(combinationAdapter);
                combinationAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);

    }

    // Generic function to convert set to list
    public static <T> List<T> convertToList(Set<T> set) {
        return Lists.newArrayList(set);
    }


    private void Predcit_tops(String imageUrl, String label, String color, String Users_id, int id)  {

        final List<DressPoJo> tops = new ArrayList<DressPoJo>();
        final List<DressPoJo> bots = new ArrayList<DressPoJo>();
        bots.add(new DressPoJo(imageUrl, label, color, Users_id, id));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        final String date = simpleDateFormat.format(new Date());
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String Url = "http://192.168.1.4:4000/Dress";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mCombinationList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject Dress = response.getJSONObject(i);
                        String dress_type = Dress.getString("type_name");
                        String imageUrl = UrlClass.IMAGE_BASE_URL + Dress.getString("image");
                        String type_name = Dress.getString("type_name");
                        String color = Dress.getString("color");
                        String User_id = Dress.getString("user_id");


                        if (dress_type.toLowerCase().equals("t_shirt") || dress_type.toLowerCase().equals("top") || dress_type.toLowerCase().equals("coat") || dress_type.toLowerCase().equals("shirt") || dress_type.toLowerCase().equals("pullover")) {
                            //Top

                            tops.add(new DressPoJo(
                                    imageUrl,
                                    Dress.getString("type_name"),
                                    Dress.getString("color"),
                                    Dress.getString("user_id"),
                                    Dress.getInt("id")

                            ));


                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                List<List<DressPoJo>> collection = new ArrayList<List<DressPoJo>>(2);

                collection.add(tops);
                collection.add(bots);
                List<String> redc = new ArrayList<String>(5);
                redc.add("black");
                redc.add("white");
                redc.add("grey");
                redc.add("blue");
                redc.add("cyan");
                List<String> orangec = new ArrayList<String>(5);
                orangec.add("black");
                orangec.add("white");
                orangec.add("grey");
                orangec.add("violet");
                orangec.add("cyan");
                List<String> yellowc = new ArrayList<String>(4);
                yellowc.add("black");
                yellowc.add("white");
                yellowc.add("violet");
                yellowc.add("grey");
                List<String> greenc = new ArrayList<String>(3);
                greenc.add("black");
                greenc.add("white");
                greenc.add("violet");
                List<String> cyanc = new ArrayList<String>(5);
                cyanc.add("black");
                cyanc.add("white");
                cyanc.add("grey");
                cyanc.add("red");
                cyanc.add("magenta");
                List<String> bluec = new ArrayList<String>(4);
                bluec.add("black");
                bluec.add("white");
                bluec.add("grey");
                bluec.add("orange");
                List<String> violetc = new ArrayList<String>(6);
                violetc.add("black");
                violetc.add("white");
                violetc.add("grey");
                violetc.add("orange");
                violetc.add("yellow");
                violetc.add("green");
                List<String> brownc = new ArrayList<String>(3);
                brownc.add("black");
                brownc.add("white");
                brownc.add("orange");
                brownc.add("grey");
                List<String> magentac = new ArrayList<String>(4);
                magentac.add("white");
                magentac.add("blue");
                magentac.add("black");
                magentac.add("grey");
                List<String> whitec = new ArrayList<String>(8);
                whitec.add("black");
                whitec.add("blue");
                whitec.add("red");
                whitec.add("orange");
                whitec.add("violet");
                whitec.add("magenta");
                whitec.add("yellow");
                whitec.add("green");
                List<String> blackc = new ArrayList<String>(8);
                blackc.add("white");
                blackc.add("blue");
                blackc.add("red");
                blackc.add("orange");
                blackc.add("violet");
                blackc.add("magenta");
                blackc.add("green");
                blackc.add("grey");
                List<String> greyc = new ArrayList<String>(6);
                greyc.add("black");
                greyc.add("blue");
                greyc.add("red");
                greyc.add("orange");
                greyc.add("violet");
                greyc.add("magenta");


                Set<List<DressPoJo>> combset = DressCombinationClass.getCombinations(collection);

                List<List<DressPoJo>> comblist = convertToList(combset);

                for (int i = 0; i < comblist.size(); i++) {

                    List<DressPoJo> mlist = comblist.get(i);
                    boolean q = false;

                    DressPoJo topItem = mlist.get(0);
                    DressPoJo botItem = mlist.get(1);
                    String tcolor = topItem.getColor();
                    System.out.println("top:" + tcolor);
                    String bc = botItem.getColor();
                    String bcolor = bc.toLowerCase();
                    System.out.println(bcolor);
                    if (tcolor.toLowerCase().equals("red"))
                        q = redc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("yellow"))
                        q = yellowc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("orange"))
                        q = orangec.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("green"))
                        q = greenc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("cyan"))
                        q = cyanc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("blue"))
                        q = bluec.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("violet"))
                        q = violetc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("brown"))
                        q = brownc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("black"))
                        q = blackc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("grey"))
                        q = greyc.contains(bcolor);
                    else if (tcolor.toLowerCase().equals("white"))
                        q = whitec.contains(bcolor);


                    if (q == true) {
                        CombinationPoJo combItem = new CombinationPoJo(topItem.getImage(), topItem.getColor(), topItem.getType_name(), botItem.getImage(), botItem.getColor(), botItem.getType_name(), date);
                        combinationList.add(combItem);
                    }


                }
                java.util.Random random = new java.util.Random();

                for (int x = 0; x < 3; x++) {
                    int random_computer_card = random.nextInt(combinationList.size());
                    mCombinationList.add(combinationList.get(x));


                }
                combinationList.clear();
                combinationAdapter = new PredcitCombinatonnAdapter(Predcit_Activity.this, mCombinationList);
                Predict_recycler.setAdapter(combinationAdapter);
                combinationAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);


    }

    public void uploadworn(final String top_imageURl1, final String bots_imageURL2, final String date, final String type_name1, final String color1, final String type_name2, final String color2) {
        String dbUrl = "http://192.168.1.4:4000/Wornhis";
        pref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        final String session_id = pref.getString("session", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, dbUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Predcit_Activity.this, "updated", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", session_id);
                params.put("image1", top_imageURl1);
                params.put("type_name1", type_name1);
                params.put("color1", color1);
                params.put("image2", bots_imageURL2);
                params.put("type_name2", type_name2);
                params.put("color2", color2);
                params.put("date", date);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                return header;
            }

        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

}