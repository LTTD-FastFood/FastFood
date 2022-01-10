package com.example.fastfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fastfoodapp.Helper.ManagementCard;
import com.example.fastfoodapp.Model.MonAn;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.ActivityShowDetailBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class ShowDetail extends AppCompatActivity {

    ActivityShowDetailBinding binding;
    String gia_format;
    MonAn object;
    ManagementCard managementCard;
    private int numberOrder = 1;

    TextView btnBack;
    CardView addCart;
    FrameLayout frameLayout;
    Toolbar toolbar;
    RatingBar ratingBar;

    ImageView foodPic;
    TextView foodTxt, caloriesTxt, tvthanhphan;
    TextView txtSao, txtGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        managementCard = new ManagementCard(this);

        AnhXa();
        cainaylahome();
        cainaylaNut();

    }

    private void cainaylahome() {

        object = (MonAn) getIntent().getSerializableExtra("detail");
        Glide.with(getApplicationContext()).load(object.getHinhMon()).into(foodPic);

        object.getMaSP();
        foodTxt.setText(object.getTenMon());
        caloriesTxt.setText(String.valueOf(object.getCalories()) + " Calories");
        tvthanhphan.setText(String.valueOf(object.getMoTa()));
        txtSao.setText(String.valueOf(object.getSao()));
        ratingBar.setRating(object.getSao().floatValue());

        gia_format = NumberFormat.getNumberInstance(Locale.US).format(object.getGia());
        txtGia.setText(gia_format + "đ");

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ShowDetail.this, String.valueOf(numberOrder), Toast.LENGTH_SHORT).show();
                object.setNumberInCard(numberOrder);
                managementCard.insertFood(object);
            }
        });
//        }
    }


    private void cainaylaNut() {

        addCart = (CardView) findViewById(R.id.addCart);
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDetail.this, MyCart.class));
            }
        });
    }

    private void AnhXa() {
        foodPic = (ImageView) findViewById(R.id.foodPic);
        foodTxt = (TextView) findViewById(R.id.foodTxt);
        caloriesTxt = (TextView) findViewById(R.id.calotxt);
        tvthanhphan = (TextView) findViewById(R.id.thanhphan);
        txtSao = (TextView) findViewById(R.id.txtSao);
        txtGia = (TextView) findViewById(R.id.txtGia);
        frameLayout = (FrameLayout) findViewById(R.id.frameAdd);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}