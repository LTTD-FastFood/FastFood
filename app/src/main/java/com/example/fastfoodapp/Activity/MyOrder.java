package com.example.fastfoodapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.bumptech.glide.Glide;
import com.example.fastfoodapp.Adapter.GiohangAdapter;
import com.example.fastfoodapp.Adapter.OrderAdapter;
import com.example.fastfoodapp.App.MySingleton;
import com.example.fastfoodapp.App.SessionManager;
import com.example.fastfoodapp.Helper.ChangeNumberItemListener;
import com.example.fastfoodapp.Helper.ManagementCard;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.ActivityMyOrderBinding;
import com.example.fastfoodapp.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MyOrder extends AppCompatActivity {

    private static final String TAG = MyOrder.class.getSimpleName();

    ActivityMyOrderBinding binding;

    LinearLayout btnAddress, btnCheckOut;
    TextView tvPhiGH, tenkh, sdtkh, diachi, diachicuthe;
    TextView tvTongOrder, txtEmpty,tvOrder;

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCard managementCard;
    String tong_format;

    String[] dayOfWeek = {"Thanh toán khi nhận hàng", "Khác..."};

    String getId;
    SessionManager sessionManager;
    String url = Utils.BASE_URL + "Android/profile/read_detail.php";
    String url_order = Utils.BASE_URL + "Android/Order.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        managementCard = new ManagementCard(this);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        init();
        cainaylaNut();
        initList();
        CalculateCard();

        Spinner MySpinner2 = (Spinner) findViewById(R.id.spinnerDC);
        ArrayAdapter<String> myAdapter2 = new
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dayOfWeek);
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MySpinner2.setAdapter(myAdapter2);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new OrderAdapter(managementCard.getListCard(), this, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                CalculateCard();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if (managementCard.getListCard().isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.GONE);
        }

    }

    private void CalculateCard() {

        int Tong = managementCard.getTotalFee() + 15000;
        tong_format = NumberFormat.getNumberInstance(Locale.US).format(Tong);
        tvTongOrder.setText(tong_format + "đ");
    }

    //getDetail
    private void getUserDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.i(TAG, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String F_name = object.getString("name").trim();
                            String F_phone = object.getString("phone").trim();
                            String F_address = object.getString("address").trim();
                            String F_addressSpecific = object.getString("addressSpecific").trim();

                            tenkh.setText(F_name);
                            sdtkh.setText(" _ " + F_phone);
                            diachi.setText(F_address);
                            diachicuthe.setText(F_addressSpecific);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(MyOrder.this, "Error" + e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MyOrder.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", getId);
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

    private void NewOrder(final String MaKH, final String DonGia, final String ChiTietDonHang) {
        final ProgressDialog progressDialog = new ProgressDialog(MyOrder.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url_order, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("You are successfully")) {
                    Toast.makeText(MyOrder.this, "Thanh Toán thành công", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MyOrder.this, "Thanh Toán thành công", Toast.LENGTH_SHORT).show();
                    managementCard.DeleteListCard();
                    startActivity(new Intent(MyOrder.this,Home.class));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MyOrder.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("MaKH", MaKH);
                param.put("DonGia", DonGia);
                param.put("ChiTietDonHang", ChiTietDonHang);
                Log.d("data", ChiTietDonHang);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(MyOrder.this).addToRequestQueue(request);
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

        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("test",new Gson().toJson(managementCard.getListCard()));

                String Str_DonGia=String.valueOf(managementCard.getTotalFee());
                String Str_CTDH=new Gson().toJson(managementCard.getListCard());

                NewOrder(getId,Str_DonGia,Str_CTDH);
            }
        });
    }

    public void init() {
        tenkh = (TextView) findViewById(R.id.tenkh);
        sdtkh = findViewById(R.id.sdtkh);
        diachi = findViewById(R.id.dc);
        diachicuthe = findViewById(R.id.dccuthe);
        tvTongOrder = findViewById(R.id.tvTongOrder);
        txtEmpty = findViewById(R.id.emptyTxt);
        tvOrder = findViewById(R.id.tvOrder);
        recyclerViewList = findViewById(R.id.recOrder);
        tvPhiGH = findViewById(R.id.phiGH);
    }
}