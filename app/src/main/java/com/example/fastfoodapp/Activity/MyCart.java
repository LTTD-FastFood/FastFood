package com.example.fastfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.fastfoodapp.Adapter.GiohangAdapter;
import com.example.fastfoodapp.Helper.ChangeNumberItemListener;
import com.example.fastfoodapp.Helper.ManagementCard;
import com.example.fastfoodapp.Model.MonAn;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.ActivityMyCartBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MyCart extends AppCompatActivity {

    ActivityMyCartBinding binding;
    MonAn monAn = null;

    TextView btnBack, numberOrderTxt, tvTong, emptyTxt;
    Button btnOrder;
    CheckBox checkBox;

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCard managementCard;
    String tong_format;
    ArrayList<MonAn> monAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managementCard = new ManagementCard(this);

        cainaylaNut();
        AnhXa();
        initList();
        CalculateCard();
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new GiohangAdapter(managementCard.getListCard(), this, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                CalculateCard();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if (managementCard.getListCard().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
        } else {
            emptyTxt.setVisibility(View.GONE);
        }
    }

    private void cainaylaNut() {
        btnBack = (TextView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCart.this, Home.class));
            }
        });
        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCart.this, MyOrder.class));
            }
        });

    }
//    public void itemClicked(View v) {
//        //code to check if this checkbox is checked!
//        CheckBox checkBox = (CheckBox)v;
//        if(checkBox.isChecked()){
//            CalculateCard();
//        }
//    }

    private void CalculateCard() {

        tong_format = NumberFormat.getNumberInstance(Locale.US).format(managementCard.getTotalFee());
        tvTong.setText(tong_format + "đ");
    }

    private void AnhXa() {
        emptyTxt = (TextView) findViewById(R.id.emptyTxt);
        tvTong = (TextView) findViewById(R.id.tvTong);
        recyclerViewList = findViewById(R.id.lvGiohang);
        checkBox = findViewById(R.id.checkBoxAll);
    }
}