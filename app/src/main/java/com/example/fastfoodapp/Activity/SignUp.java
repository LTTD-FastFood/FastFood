package com.example.fastfoodapp.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fastfoodapp.App.MySingleton;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    TextView btnSignin;
    CheckBox checkBox;
    Button btnRegister;
    TextInputEditText edtName,edtEmail,edtPass,edtPassConf;
    String url = Utils.BASE_URL + "Android/profile/UserRegistration.php";
    String Name,Email,Pass,PassConf;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AnhXa();

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUp.this, Signin.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name = edtName.getText().toString();
                Email = edtEmail.getText().toString();
                Pass = edtPass.getText().toString();
                PassConf = edtPassConf.getText().toString();
                if(TextUtils.isEmpty(Name) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pass)
                    || TextUtils.isEmpty(PassConf)){
                    Toast.makeText(SignUp.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    if (Pass.equals(PassConf) == false){
                        Toast.makeText(SignUp.this, "Các mật khẩu đã nhập không khớp.Hãy thử lại!", Toast.LENGTH_SHORT).show();
                    }else{
                        if(checkBox.isChecked()==false){
                            Toast.makeText(SignUp.this, "Chưa đồng ý!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            registerNewAccount(Name,Email,Pass);
                        }
                    }
                }
            }
        });
    }

    public void AnhXa() {
        edtName = (TextInputEditText) findViewById(R.id.txtName);
        edtEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        edtPass = (TextInputEditText) findViewById(R.id.txtPass);
        edtPassConf = (TextInputEditText) findViewById(R.id.txtPassConf);
        btnRegister = (Button) findViewById(R.id.btnSignUp);
        btnSignin=(TextView) findViewById(R.id.btnSignin);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
    }


    private void registerNewAccount(final String username,final String email,final String password){
        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("You are registered successfully")){
                    Toast.makeText(SignUp.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp.this,Signin.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

                    progressDialog.dismiss();
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SignUp.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("username",username);
                param.put("email",email);
                param.put("psw",password);

                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(SignUp.this).addToRequestQueue(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

        return true;
    }
}