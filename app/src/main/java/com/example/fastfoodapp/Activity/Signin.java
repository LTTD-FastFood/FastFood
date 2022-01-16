package com.example.fastfoodapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fastfoodapp.App.MySingleton;
import com.example.fastfoodapp.App.SessionManager;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signin extends AppCompatActivity {

    TextView btnSignup;
    TextInputEditText txtEmaillLogin,txtPassLogin;
    CheckBox checkBox;
    Button btnSignIn;
    String Password,Email;
    SharedPreferences sharedPreferences;
    String url = Utils.BASE_URL + "Android/profile/UserLogin.php";
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        sessionManager= new SessionManager(this);
//        sessionManager.checked();
        AnhXa();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email=txtEmaillLogin.getText().toString().trim();
                Password=txtPassLogin.getText().toString().trim();
                if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){
                    Toast.makeText(Signin.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else
                    logIn(Email,Password);
            }
        });

        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.preLoginState),"");
        if(loginStatus.equals("LoggedIn")){
            startActivity(new Intent(Signin.this,Home.class));
        }
        cainaylaNut();
    }
    public void cainaylaNut() {
        btnSignup=(TextView) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signin.this, SignUp.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });

    }

    public void logIn(final String email,final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(Signin.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success.equals("1")){
                        for(int i= 0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String name = object.getString("name").trim();
                            String email = object.getString("email").trim();
                            String phone = object.getString("phone").trim();
                            String dateofbirth = object.getString("dateofbirth").trim();

                            String address = object.getString("address").trim();
                            String addressSpecific = object.getString("addressSpecific").trim();
                            String id = object.getString("id").trim();
                            Utils.getId  = object.getString("id").trim();

                            Utils.GET_NAME = name ;
                            Utils.GET_IMG= Utils.BASE_URL + object.getString("photo").trim();

                            sessionManager.createSession(name,email,phone,dateofbirth,id);
                            sessionManager.createSession2(name,phone,address,addressSpecific,id);

                            progressDialog.dismiss();
                            Toast.makeText(Signin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if(checkBox.isChecked()){
                                editor.putString(getResources().getString(R.string.preLoginState),"LoggedIn");
                            }else{
                                editor.putString(getResources().getString(R.string.preLoginState),"LoggedOut");
                            }
                            editor.apply();

                            startActivity(new Intent(Signin.this,Home.class));
                        }
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(Signin.this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(Signin.this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Signin.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("email",email);
                param.put("psw",password);

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void AnhXa() {
        btnSignIn=(Button) findViewById(R.id.btnSignin);
        txtEmaillLogin=(TextInputEditText) findViewById(R.id.txtEmaillLogin);
        txtPassLogin=(TextInputEditText) findViewById(R.id.txtPassLogin);
        checkBox = (CheckBox) findViewById(R.id.cbBox);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        return true;
    }
}