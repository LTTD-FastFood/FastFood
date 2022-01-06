package com.example.fastfoodapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.fastfoodapp.databinding.ActivityMyOrderBinding;
import com.example.fastfoodapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyOrder extends AppCompatActivity {

    private  static  final String TAG = MyOrder.class.getSimpleName();

    ActivityMyOrderBinding binding;
    LinearLayout btnAddress, btnCheckOut;
    TextView btnBack,tenkh,sdtkh,diachi,diachicuthe;

    String getId;
    SessionManager sessionManager;
    String url = Utils.BASE_URL + "Android/profile/read_detail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        HashMap<String,String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        init();
        cainaylaNut();

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
                            String F_phone = object.getString("phone").trim();
                            String F_address = object.getString("address").trim();
                            String F_addressSpecific = object.getString("addressSpecific").trim();

                            tenkh.setText(F_name);
                            sdtkh.setText(" _ " +F_phone);
                            diachi.setText(F_address);
                            diachicuthe.setText(F_addressSpecific);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(MyOrder.this, "Error"+ e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MyOrder.this, "Error"+error.toString(), Toast.LENGTH_LONG).show();
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

    private void cainaylaNut() {
        btnAddress = (LinearLayout) findViewById(R.id.btnAddress);
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyOrder.this, Address.class));
            }
        });
        btnCheckOut = (LinearLayout) findViewById(R.id.btnCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyOrder.this, CheckOut.class));
            }
        });
        btnBack = (TextView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyOrder.this, MyCart.class));
            }
        });
    }

    public void init(){
        tenkh =(TextView) findViewById(R.id.tenkh);
        sdtkh = findViewById(R.id.sdtkh);
        diachi = findViewById(R.id.dc);
        diachicuthe = findViewById(R.id.dccuthe);
    }
}