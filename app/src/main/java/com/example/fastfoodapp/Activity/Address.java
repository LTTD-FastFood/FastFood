package com.example.fastfoodapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fastfoodapp.App.Custom_dialog;
import com.example.fastfoodapp.App.SessionManager;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Address extends AppCompatActivity implements Custom_dialog.ExampleDialogListener{

    private  static  final String TAG = Profile.class.getSimpleName();

    TextView txtName,txtPhone,txtAddress,txtAddressSpecific;
    SessionManager sessionManager;
    String getId;
    Menu action;

    String url = Utils.BASE_URL + "Android/profile/read_detail.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        AnhXa();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sessionManager = new SessionManager(this);

        HashMap<String,String> user = sessionManager.getUserDetail2();
        getId = user.get(sessionManager.ID);

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

                            txtName.setText(F_name);
                            txtPhone.setText(F_phone);
                            txtAddress.setText(F_address);
                            txtAddressSpecific.setText(F_addressSpecific);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(Address.this, "Error"+ e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Address.this, "Error"+error.toString(), Toast.LENGTH_LONG).show();
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

        switch (item.getItemId()){
            case R.id.menu_edit:

                DiaLogin();

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                saveEditAddress();

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveEditAddress() {

        final String name = txtName.getText().toString().trim();
        final String phone = txtPhone.getText().toString().trim();
        final String addressSpecific = txtAddressSpecific.getText().toString().trim();
        final String address = txtAddress.getText().toString().trim();
        final String id = getId;

        final ProgressDialog progressDialog = new ProgressDialog(Address.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        String urlEdit = Utils.BASE_URL + "Android/profile/edit_address.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlEdit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Toast.makeText(Address.this, "Success", Toast.LENGTH_SHORT).show();
                        sessionManager.createSession2(name,phone,address,addressSpecific,id);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(Address.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Address.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("address",address);
                params.put("addressSpecific",addressSpecific);
                params.put("id",id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void DiaLogin(){
        Custom_dialog dialog = new Custom_dialog();
        dialog.show(getSupportFragmentManager(),"example dialog");
    }

    @Override
    public void applyText(String state, String district, String ward,String addressCustom) {
        txtAddressSpecific.setText(addressCustom);
        txtAddress.setText(ward +", "+district+", "+state);
    }
    private void AnhXa() {
        txtName = (TextView) findViewById(R.id.txtname1);
        txtPhone = (TextView) findViewById(R.id.txtphone);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtAddressSpecific = (TextView) findViewById(R.id.txtAddressSpecific);
    }
}