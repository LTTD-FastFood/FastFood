package com.example.fastfoodapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.fastfoodapp.App.SessionManager;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private  static  final String TAG = Profile.class.getSimpleName();

    CircleImageView profile_image;
    RelativeLayout btnHome;
    ImageButton edit_photo;
    RelativeLayout btnProduct,btnHistory;
    Button btnSignOut;
    EditText name,birth,phone,mail;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    String getId;
    Menu action;
    String url = Utils.BASE_URL + "Android/profile/read_detail.php";
    String url_edit = Utils.BASE_URL + "Android/profile/edit_detail.php";
    String url_Upload = Utils.BASE_URL + "Android/profile/upload.php";
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbarPr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();

        AnhXa();
        edit_photo.setVisibility(View.INVISIBLE);

        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);


        HashMap<String,String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        edit_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosefile();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.preLoginState),"LoggedOut");
                editor.apply();
                startActivity(new Intent(Profile.this, Signin.class));
                finish();
//                sessionManager.Logout();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Home.class));
            }
        });


        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, ListProduct.class));
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, History.class));
            }
        });
    }


    //getDetail
    private void getUserDetail(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.i(TAG,response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if(success.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String F_name = object.getString("name").trim();
                            String F_email = object.getString("email").trim();
                            String F_phone = object.getString("phone").trim();
                            String F_dateofbirth = object.getString("dateofbirth").trim();
                            String F_img = Utils.BASE_URL + object.getString("photo").trim();

                            name.setText(F_name);
                            mail.setText(F_email);
                            phone.setText(F_phone);
                            birth.setText(F_dateofbirth);
                            Glide.with(Profile.this).load(F_img).into(profile_image);


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(Profile.this, "Error"+ e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Profile.this, "Error"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("id",getId);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action,menu);

        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_edit:

                edit_photo.setVisibility(View.VISIBLE);

                name.setFocusableInTouchMode(true);
                mail.setFocusableInTouchMode(true);
                birth.setFocusableInTouchMode(true);
                phone.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                SaveEditDetail();

                edit_photo.setVisibility(View.INVISIBLE);

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);

                name.setFocusableInTouchMode(false);
                mail.setFocusableInTouchMode(false);
                birth.setFocusableInTouchMode(false);
                phone.setFocusableInTouchMode(false);

                name.setFocusable(false);
                mail.setFocusable(false);
                birth.setFocusable(false);
                phone.setFocusable(false);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    //Edit
    private void SaveEditDetail() {
        final String name = this.name.getText().toString().trim();
        final String email = this.mail.getText().toString().trim();
        final String id = getId;
        final String birth = this.birth.getText().toString().trim();
        final String phone = this.phone.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(Profile.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_edit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(Profile.this, "Thành công", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(name,email,phone,birth,id);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Profile.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Profile.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("phone",phone);
                params.put("dateofbirth",birth);
                params.put("id",id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void choosefile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                profile_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadPicture(getId,getStringImage(bitmap));
        }
    }

    private void UploadPicture(final String id,final String photo) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_Upload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.i(TAG,response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Toast.makeText(Profile.this, "Thành công", Toast.LENGTH_SHORT).show(); }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(Profile.this, "Try Again!"+ e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Profile.this, "Try Again!"+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("photo",photo);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageByteArray,Base64.DEFAULT);

        return  encodeImage;
    }

    private void AnhXa() {
        btnSignOut=(Button) findViewById(R.id.btnSignOut);
        name=(EditText) findViewById(R.id.txtname);
        birth=(EditText) findViewById(R.id.dayofbirth);
        phone=(EditText) findViewById(R.id.phone);
        mail=(EditText) findViewById(R.id.mail);

        btnHistory=(RelativeLayout) findViewById(R.id.btnHistory);
        btnProduct=(RelativeLayout) findViewById(R.id.btnProduct);
        btnHome=(RelativeLayout) findViewById(R.id.btnHome);

        edit_photo = (ImageButton) findViewById(R.id.edit_photo);
        profile_image = findViewById(R.id.profile_image1);
    }

}