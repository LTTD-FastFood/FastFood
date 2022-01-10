package com.example.fastfoodapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fastfoodapp.Adapter.LichSuApdapter;
import com.example.fastfoodapp.Adapter.MonAnListAdapter;
import com.example.fastfoodapp.App.MySingleton;
import com.example.fastfoodapp.App.SessionManager;
import com.example.fastfoodapp.Model.LichSu;
import com.example.fastfoodapp.Model.MonAn;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.ActivityHistoryBinding;
import com.example.fastfoodapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class History extends AppCompatActivity {

    private static final String TAG = MyOrder.class.getSimpleName();

    ActivityHistoryBinding binding;

    RelativeLayout btnProfile,btnProduct,btnHome;
    TextView btnBackList;

    String url = Utils.BASE_URL+"Android/ViewHistory.php";
    String urlBase= Utils.BASE_URL;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<LichSu> lichsuList;
    LichSuApdapter lichSuApdapter;
    StringRequest request;
    RequestQueue requestQueue;

    String getId;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        CainaylaNut();

        lichsuList = new ArrayList<>();
        recyclerView = findViewById(R.id.recHistory);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(History.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        call_json();

    }

    private void call_json() {

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray j = new JSONArray(response);

                    for (int i = 0; i < j.length(); i++) {

                        JSONObject jsonObject = null;
                        jsonObject = j.getJSONObject(i);

                        LichSu getLichSuAdapter = new LichSu();

                        getLichSuAdapter.setNameFood(jsonObject.getString("nameFood"));
                        getLichSuAdapter.setDonGia(jsonObject.getInt("GiaTong"));
                        getLichSuAdapter.setNgayMua(jsonObject.getString("NgayBan"));
                        getLichSuAdapter.setSoluong(jsonObject.getInt("SoLuong"));

                        getLichSuAdapter.setImgFood(urlBase.concat(jsonObject.getString("imgFood")));

                        lichsuList.add(getLichSuAdapter);

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                lichSuApdapter = new LichSuApdapter(History.this, lichsuList);
                recyclerView.setAdapter(lichSuApdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(History.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("MaKH",getId);

                return param;
            }
        };
        requestQueue = Volley.newRequestQueue(History.this);
        requestQueue.add(request);
    }

    private void CainaylaNut() {

        btnBackList=(TextView) findViewById(R.id.btnBackList);
        btnBackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(History.this, Home.class));
            }
        });

        btnHome=(RelativeLayout) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(History.this, Home.class));
            }
        });

        btnProfile=(RelativeLayout) findViewById(R.id.btnProfile1);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(History.this, Profile.class));
            }
        });

        btnProduct=(RelativeLayout) findViewById(R.id.btnProduct);
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(History.this, ListProduct.class));
            }
        });
    }


}