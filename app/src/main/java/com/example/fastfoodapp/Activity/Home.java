package com.example.fastfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fastfoodapp.Adapter.MonAnListAdapter;
import com.example.fastfoodapp.Adapter.PhotoAdapter;
import com.example.fastfoodapp.Model.MonAn;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.ActivityHomeBinding;
import com.example.fastfoodapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;

    ViewPager viewPager;
    CircleIndicator circleIndicator;
    PhotoAdapter photoAdapter;
    List<Photo> mlistPhoto;

    Handler mhandler = new Handler();
    Runnable mrunnable = new Runnable() {
        @Override
        public void run() {

            if (viewPager.getCurrentItem() == mlistPhoto.size() - 1){
                viewPager.setCurrentItem(0);
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };


    String urlBase= Utils.BASE_URL;
    String url = Utils.BASE_URL+"Android/list_product/producttop.php";

    MonAnListAdapter monAnListAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    JsonArrayRequest request;
    RequestQueue requestQueue;
    ArrayList<MonAn> monAnList;



    CircleImageView profile_image;
    TextView myCart;
    RelativeLayout btnProfile,btnHistory,btnProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cainaylaNut();
        monAnList = new ArrayList<>();
        recyclerView = findViewById(R.id.lvmonanhome);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(Home.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        call_json();



        viewPager = findViewById(R.id.viewPage);
        circleIndicator = findViewById(R.id.circle_indi);

        mlistPhoto = getlistPhoto();

        photoAdapter = new PhotoAdapter(this, getlistPhoto());

        PhotoAdapter adapter = new PhotoAdapter(mlistPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        //    photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        mhandler.postDelayed(mrunnable,3000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {

                mhandler.removeCallbacks(mrunnable);
                mhandler.postDelayed(mrunnable,3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        EditText edtSearch = findViewById(R.id.txtFind);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(edtSearch.toString());
            }
        });
    }


    private List<Photo> getlistPhoto() {

        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.a4));
        list.add(new Photo(R.drawable.a1));
        list.add(new Photo(R.drawable.a2));
        list.add(new Photo(R.drawable.a3));
        list.add(new Photo(R.drawable.a5));
        list.add(new Photo(R.drawable.a6));

        return list;

    }

    private void filter(String text) {
        ArrayList<MonAn> filteredList = new ArrayList<>();

        for (MonAn item : monAnList) {
            if (item.getTenMon().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);

            }
        }

//        recyclerViewAdapter = new CustomerListAdapter(customerList,getActivity());
        monAnListAdapter.filterList(filteredList);

    }



    private void call_json() {

        request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){
                    MonAn getMonAnAdapter = new MonAn();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);

                        getMonAnAdapter.setTenMon(jsonObject.getString("nameFood"));
                        getMonAnAdapter.setGia(jsonObject.getInt("price"));
                        getMonAnAdapter.setSao(jsonObject.getDouble("star"));
                        getMonAnAdapter.setMoTa(jsonObject.getString("description"));
                        getMonAnAdapter.setCalories(jsonObject.getInt("calories"));

                        getMonAnAdapter.setHinhMon(urlBase.concat(jsonObject.getString("imgFood")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    monAnList.add(getMonAnAdapter);
                }
                monAnListAdapter = new MonAnListAdapter(Home.this,monAnList);
                recyclerView.setAdapter(monAnListAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(Home.this);
        requestQueue.add(request);
    }


    private void cainaylaNut() {

        myCart=(TextView) findViewById(R.id.myCart);
        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, MyCart.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });

        btnProfile=(RelativeLayout) findViewById(R.id.btnProfile1);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Profile.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });

        btnProduct=(RelativeLayout) findViewById(R.id.btnProduct);
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, ListProduct.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });

        btnHistory=(RelativeLayout) findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, History.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });


        profile_image=(CircleImageView) findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Profile.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

    }
}