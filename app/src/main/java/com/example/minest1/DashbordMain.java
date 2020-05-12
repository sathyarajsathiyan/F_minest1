package com.example.minest1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.minest1.HomeAdapter.FeaturedAdapter;
import com.example.minest1.HomeAdapter.FeaturedHelperClass;
import com.example.minest1.util.DataPart;
import com.example.minest1.util.UrlClass;
import com.example.minest1.util.VolleyMultipartRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class DashbordMain extends Dashboard implements EasyPermissions.PermissionCallbacks,

        EasyPermissions.RationaleCallbacks {
    public static final int CAMERA_REQUEST_CODE = 1996;
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    FloatingActionButton camera;
    EditText editTextTags;
    String currentPhotoPath;
    String pathFile;
    Bitmap bitmap;
    Uri   mCapturedImageURI;
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_STORAGE=143;
    private File fileName;
    private ImageView cameraImage;
    private String encodedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord_main);
        EasyPermissions.requestPermissions(this,getString(R.string.rationale_storage),RC_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraTask();


            }
        });

        featuredRecycler = findViewById(R.id.featured_recycler);
        featuredRecycler();
    }

    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.comb_1, R.drawable.comb_1_2, "Like", "Dislike", "Wear"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.comb_2, R.drawable.comb_1_2, "Like", "Dislike", "Wear"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.comb_3_top, R.drawable.comb_3, "Like", "DisLike", "Wear"));
        // featuredLocations.add(new FeaturedHelperClass(R.drawable.add,R.drawable.add,"Like","DisLike","Wear"));
        //featuredLocations.add(new FeaturedHelperClass(null,null,"like","dislke","ware"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);


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
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
        super.onBackPressed();
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
            if(takepic.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takepic,
                        CAMERA_REQUEST_CODE);
            }



            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();

        } else {

            // Ask for one permission

            EasyPermissions.requestPermissions(this,getString(R.string.rationale_camera),RC_CAMERA_PERM, Manifest.permission.CAMERA);


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
        if(resultCode == RESULT_OK ){
            if ( requestCode == CAMERA_REQUEST_CODE && null!= data    ) {
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

                Toast.makeText(this, getString(R.string.returned_from_app_settings_to_activity, hasCameraPermission() ? yes : no, hasStoragePermission() ? yes : no),Toast.LENGTH_LONG).show();
                Toast.makeText(this, getString(R.string.returned_from_app_settings_to_activity,  hasStoragePermission() ? yes : no),Toast.LENGTH_LONG).show();

            }
        }



    }

    private void uploadImage() {
        RequestQueue rq = Volley.newRequestQueue(this);
        String url="http://0.0.0.0:9000/predict";
        Log.d("URL", url);
        long milli = System.currentTimeMillis();
        final String Img_name = "INCI_IMG_"+ milli + ".png";
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
        })

        {
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

    private  void UploadOImage(final Bitmap photo){
        long milli = System.currentTimeMillis();
        final String Img_name = "INCI_IMG_"+ milli + ".png";
        //String mUrl = "http://192.168.1.5:8000/files";
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UrlClass.PREDICT_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                           // JSONArray array = obj.getJSONArray("array");
                           // for(int i=0; i<array.length(); i++){
                                //console.log(i);
                            //}

                          //  boolean boo = obj.getBoolean("boolean" );
                           // double dd = obj.getDouble("doubkekey");
                           // String color = obj.getString("color");



                            //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
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

}
