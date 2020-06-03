package com.example.minest1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.minest1.HomeAdapter.DressCombinationAdapter;
import com.example.minest1.HomeAdapter.PreviewAdapter;
import com.example.minest1.HomeAdapter.PreviewItem;
import com.example.minest1.util.CombinationPoJo;
import com.example.minest1.util.DataPart;
import com.example.minest1.util.DressCombinationClass;
import com.example.minest1.util.DressPoJo;
import com.example.minest1.util.UrlClass;
import com.example.minest1.util.VolleyMultipartRequest;
import com.google.common.collect.Lists;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class DashbordMain extends Dashboard implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    public static final int CAMERA_REQUEST_CODE = 1996;
    RecyclerView featuredRecycler;
    SharedPreferences pref, sharedpreferences;
    RecyclerView.Adapter adapter;
    Button camera, predict;
    EditText editTextTags;
    String currentPhotoPath;
    String pathFile;
    Bitmap bitmap;
    Uri mCapturedImageURI;
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_STORAGE = 143;
    private File fileName;
    private RecyclerView Preview_recycler;
    private PreviewAdapter mPreviewAdapter;
    private DressCombinationAdapter combinationAdapter;
    private ArrayList<PreviewItem> mPreviewItem;
    private ArrayList<CombinationPoJo> mCombinationList;
    private ArrayList<CombinationPoJo> combinationList;
    private ImageView cameraImage;
    private String encodedString;
    SweetAlertDialog pDialog;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord_main);
        predict = findViewById(R.id.Predict_image);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashbordMain.this, Predcit_Activity.class);
                startActivity(intent);
            }
        });
        EasyPermissions.requestPermissions(this, getString(R.string.rationale_storage), RC_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraTask();


            }
        });

        featuredRecycler = findViewById(R.id.featured_recycler);
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mCombinationList = new ArrayList<>();
        combinationList = new ArrayList<>();
        DisplayCombinations();
        Preview_recycler = findViewById(R.id.preview_recycler);
        Preview_recycler.setHasFixedSize(true);
        Preview_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPreviewItem = new ArrayList<>();

        preview_Recycler();


    }

    private void preview_Recycler() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String Url = "http://192.168.1.4:4000/Dress?_sort=id&_order=desc";
        // UrlClass obj = new UrlClass();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mPreviewItem.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject Dress = response.getJSONObject(i);
                        String imageUrl = UrlClass.IMAGE_BASE_URL + Dress.getString("image");

                        mPreviewItem.add(new PreviewItem(imageUrl));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                mPreviewAdapter = new PreviewAdapter(DashbordMain.this, mPreviewItem);
                //mPreviewAdapter = new PreviewAdapter(DashbordMain.this, mPreviewItem);
                Preview_recycler.setAdapter(mPreviewAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }





    private void DisplayCombinations() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String Url = "http://192.168.1.4:4000/Dress";
        // UrlClass obj = new UrlClass();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        final String date = simpleDateFormat.format(new Date());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //mCombinationList.clear();//shall i commettd i why?

                List<DressPoJo> tops = new ArrayList<DressPoJo>();
                List<DressPoJo> bots = new ArrayList<DressPoJo>();
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject Dress = response.getJSONObject(i);
                        String dress_type = Dress.getString("type_name");
                        String imageUrl = UrlClass.IMAGE_BASE_URL + Dress.getString("image");
                        String type_name = Dress.getString("type_name");
                        String color = Dress.getString("color");
                        String User_id = Dress.getString("user_id");
                        //Toast.makeText(DashbordMain.this, "" + date, Toast.LENGTH_SHORT).show();
                        if (dress_type.toLowerCase().equals("t_shirt") || dress_type.toLowerCase().equals("top") || dress_type.toLowerCase().equals("coat") || dress_type.toLowerCase().equals("shirt") || dress_type.toLowerCase().equals("pullover")) {
                            //Top

                            tops.add(new DressPoJo(
                                    imageUrl,
                                    Dress.getString("type_name"),
                                    Dress.getString("color"),
                                    Dress.getString("user_id"),
                                    Dress.getInt("id")

                            ));


                        } else if (dress_type.toLowerCase().equals("trouser")) {
                            //Bottom// one doubt where we display the images here ??
                            bots.add(new DressPoJo(
                                    imageUrl,
                                    Dress.getString("type_name"),
                                    Dress.getString("color"),
                                    Dress.getString("user_id"),
                                    Dress.getInt("id")
                            ));
                            // Toast.makeText(DashbordMain.this, "" + tops, Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
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

                combinationAdapter = new DressCombinationAdapter(DashbordMain.this, mCombinationList);
                featuredRecycler.setAdapter(combinationAdapter);
                combinationAdapter.notifyDataSetChanged();
                // for(int x=0;x<3;x++)
                //  {

                //  }

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

    public void uploadworn(final String top_imageURl1, final String bots_imageURL2, final String date, final String type_name1, final String color1, final String type_name2, final String color2) {
        String dbUrl = "http://192.168.1.4:4000/Wornhis";
        pref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        final String session_id = pref.getString("session", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, dbUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(DashbordMain.this, "updated", Toast.LENGTH_SHORT).show();

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
                params.put("color1", color2);
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

    @Override

    public void onRationaleAccepted(int requestCode) {

        Log.d("TAG", "onRationaleAccepted:" + requestCode);

    }

    @Override

    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        Log.d("TAG", "onPermissionsGranted:" + requestCode + ":" + perms.size());

    }


    @Override

    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        Log.d("TAG", "onPermissionsDenied:" + requestCode + ":" + perms.size());


        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."

        // This will display a dialog directing them to enable the permission in app settings.

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {

            new AppSettingsDialog.Builder(this).build().show();

        }

    }


    private boolean hasCameraPermission() {

        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA);

    }

    private boolean hasStoragePermission() {
        EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


    }

    @Override

    public void onRationaleDenied(int requestCode) {

        Log.d("TAG", "onRationaleDenied:" + requestCode);

    }


    @Override
    public void onBackPressed() {


        super.onBackPressed();
        Toast.makeText(this, "Press back again to Leave!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finishAffinity();
        finish();
    }



    /*
     * The method is taking Bitmap as an argument
     * then it will return the byte[] array for the given bitmap
     * and we will send this array to the server
     * here we are using PNG Compression with 80% quality
     * you can give quality between 0 to 100
     * 0 means worse quality
     * 100 means best quality
     * */


    @AfterPermissionGranted(RC_CAMERA_PERM)

    public void cameraTask() {

        if (hasCameraPermission()) {

            Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /*if (takepic.resolveActivity(getPackageManager()) != null) {
                ContentValues values= new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, String.valueOf(fileName));
                mCapturedImageURI = getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                takepic.putExtra(MediaStore.EXTRA_OUTPUT,mCapturedImageURI);

                    startActivityForResult(takepic, CAMERA_REQUEST_CODE);

                }*/
            if (takepic.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takepic,
                        CAMERA_REQUEST_CODE);
            }


            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();

        } else {

            // Ask for one permission

            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM, Manifest.permission.CAMERA);


        }

    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
        File storge = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storge);
        } catch (IOException e) {
            Log.d("mylog", "Excep: " + e.toString());

        }

        return image;
    }


    @SuppressLint("StringFormatMatches")
    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE && null != data) {
                Uri uri = data.getData();

                try {

                    // Adding captured image in bitmap.
                    //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    UploadOImage(imageBitmap);

                } catch (Exception e) {

                    e.printStackTrace();
                }

              /* Uri selectedImage=data.getData();
               String[] filePath={MediaStore.Images.Media.DATA};
                Cursor cursor=getContentResolver().query(selectedImage,filePath,null,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();


                Bitmap photo = (Bitmap) data.getExtras().get("data");
               // cameraImage.setImageBitmap(photo);
                String fileNameSegments[] = picturePath.split("/");
                fileName = new File(fileNameSegments[fileNameSegments.length - 1]);

                //Bitmap myImg = BitmapFactory.decodeFile(picturePath);
                //cameraImage.setImageBitmap(myImg);
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                //myImg.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                //byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                //encodedString = Base64.encodeToString(byte_arr, 0);*/


            }

            if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

                String yes = getString(R.string.yes);

                String no = getString(R.string.no);


                // Do something after user returned from app settings screen, like showing a Toast.

                Toast.makeText(this, getString(R.string.returned_from_app_settings_to_activity, hasCameraPermission() ? yes : no, hasStoragePermission() ? yes : no), Toast.LENGTH_LONG).show();
                Toast.makeText(this, getString(R.string.returned_from_app_settings_to_activity, hasStoragePermission() ? yes : no), Toast.LENGTH_LONG).show();

            }
        }


    }

    private void uploadImage() {
        RequestQueue rq = Volley.newRequestQueue(this);
        String url = "http://192.168.0.183:8000/predict";
        Log.d("URL", url);
        long milli = System.currentTimeMillis();
        final String Img_name = "INCI_IMG_" + milli + ".png";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("RESPONSE", response);
                    JSONObject json = new JSONObject(response);

                    Toast.makeText(getBaseContext(),
                            "The image is upload", Toast.LENGTH_SHORT)
                            .show();

                } catch (JSONException e) {
                    Log.d("JSON Exception", e.toString());
                    Toast.makeText(getBaseContext(),
                            "Error while loadin data!",
                            Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", "Error [" + error + "]");
                Toast.makeText(getBaseContext(),
                        "Cannot connect to server", Toast.LENGTH_LONG)
                        .show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("image", encodedString);
                params.put("filename", String.valueOf(fileName));

                return params;

            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            /*@Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                params.put("file", new DataPart(Img_name, getFileDataFromDrawable(bitmap)));
                return params;
            }*/

        };

        rq.add(stringRequest);
    }

    private void UploadOImage(final Bitmap photo) {
        long milli = System.currentTimeMillis();

        final String Img_name = "INCI_IMG_" + milli + ".jpg";
        pDialog = new SweetAlertDialog(DashbordMain.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Uploading");
        pDialog.setCancelable(false);
        pDialog.show();


        String mUrl = "http://192.168.1.4:8000/predict";
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, mUrl, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                pDialog.dismissWithAnimation();
                try {
                    JSONObject obj = new JSONObject(new String(response.data));

                    // JSONArray array = obj.getJSONArray("array");
                    // for(int i=0; i<array.length(); i++){
                    //console.log(i);
                    // }

                    //  boolean boo = obj.getBoolean("boolean" );
                    // double dd = obj.getDouble("doubkekey");
                    String label = obj.getString("label");
                    String color = obj.getString("color");
                    String path = obj.getString("path");
                    // String user_id = obj.getString("user_id");

                    Toast.makeText(DashbordMain.this, "" + color, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), obj.getString("label"), Toast.LENGTH_SHORT).show();
                    Dbupload(label, color, path);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismissWithAnimation();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("tags", tags);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {

                Map<String, DataPart> params = new HashMap<>();
                params.put("file", new DataPart(Img_name, getFileDataFromDrawable(photo)));
                // params.put("user_id", new DataPart(session_id));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void Dbupload(final String label, final String color, final String path) {

        pDialog = new SweetAlertDialog(DashbordMain.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("updating DB");
        pDialog.setCancelable(false);
        pDialog.show();
        pref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        final String session_id = pref.getString("session", null);
        //String dbUrl = "http://192.168.1.3:4000/Dress"+"?type_name="+type_name_data+"&user_id="+user_id_data;
        String dbUrl = "http://192.168.1.4:4000/Dress";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, dbUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismissWithAnimation();
                try {
                    Log.d("TAG", "  response.toString()");
                    JSONObject jsonObject = new JSONObject(response);
                    //jsonObject.put("image", path);
                    // jsonObject.put("type_name", label);
                    // jsonObject.put("color", color);
                    new SweetAlertDialog(DashbordMain.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(" Success ")
                            .setContentText("Images uploaded successfully ")
                            .show();
                    preview_Recycler();

                    Toast.makeText(DashbordMain.this, response.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", session_id);
                params.put("image", path);
                params.put("type_name", label);
                params.put("color", color);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                return header;
            }
        };
        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }


    /*
     * The method is taking Bitmap as an argument
     * then it will return the byte[] array for the given bitmap
     * and we will send this array to the server
     * here we are using PNG Compression with 80% quality
     * you can give quality between 0 to 100
     * 0 means worse quality
     * 100 means best quality
     * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // uplodaing the data to the DBserver


}


