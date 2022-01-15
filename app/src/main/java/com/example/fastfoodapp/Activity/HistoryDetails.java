package com.example.fastfoodapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.fastfoodapp.Adapter.LichSuDHApdater;
import com.example.fastfoodapp.Adapter.MonAnListAdapter;
import com.example.fastfoodapp.App.MySingleton;
import com.example.fastfoodapp.App.SessionManager;
import com.example.fastfoodapp.Model.LichSu;
import com.example.fastfoodapp.Model.LichSuDH;
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

public class HistoryDetails extends AppCompatActivity {

//    private static final String TAG = MyOrder.class.getSimpleName();

    ActivityHistoryBinding binding;

    RelativeLayout btnProfile,btnProduct,btnHome;

    String url = Utils.BASE_URL+"Android/ViewHistory.php";
    String urlBase= Utils.BASE_URL;
    LichSuDH lichSuDH;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<LichSu> lichsuList;
    LichSuApdapter lichSuApdapter;
    StringRequest request;
    RequestQueue requestQueue;

    String getId;
    String MaHD;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        final Object object = getIntent().getSerializableExtra("detailOrder");
        Log.d("donhangdsds", lichSuDH.toString());
        if (object instanceof LichSuDH) {
            lichSuDH = (LichSuDH) object;
            Log.d("donhangdsds", lichSuDH.toString());
        }

         MaHD= lichSuDH.getMaHD() +"";
//        TenKH = (TextView) findViewById(R.id.TenKH);
//        SDT = (TextView) findViewById(R.id.SDT);
//        TongTien = (TextView) findViewById(R.id.TongTien);

        CainaylaNut();

        lichsuList = new ArrayList<>();
        recyclerView = findViewById(R.id.recHistory);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(HistoryDetails.this,RecyclerView.VERTICAL,false);
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

                        getLichSuAdapter.setImgFood(jsonObject.getString("imgFood"));

                        getLichSuAdapter.setDonGia(jsonObject.getInt("DonGia"));
                        getLichSuAdapter.setSoluong(jsonObject.getInt("SoLuong"));
                        getLichSuAdapter.setNameFood(jsonObject.getString("nameFood"));

                        lichsuList.add(getLichSuAdapter);

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                lichSuApdapter = new LichSuApdapter(HistoryDetails.this, lichsuList);
                recyclerView.setAdapter(lichSuApdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("MaHD",MaHD);

                return param;
            }
        };
        requestQueue = Volley.newRequestQueue(HistoryDetails.this);
        requestQueue.add(request);
    }

    private void filter(String text) {
        ArrayList<LichSu> filteredList = new ArrayList<>();

        for (LichSu item : lichsuList) {
            if (item.getNameFood().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);

            }
        }

//        recyclerViewAdapter = new CustomerListAdapter(customerList,getActivity());
        lichSuApdapter.filterList(filteredList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void CainaylaNut() {

        btnHome=(RelativeLayout) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryDetails.this, Home.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

            }
        });

        btnProfile=(RelativeLayout) findViewById(R.id.btnProfile1);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryDetails.this, Profile.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });

        btnProduct=(RelativeLayout) findViewById(R.id.btnProduct);
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryDetails.this, ListProduct.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        return true;
    }
}