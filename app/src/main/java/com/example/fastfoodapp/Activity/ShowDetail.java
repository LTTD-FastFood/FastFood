package com.example.fastfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fastfoodapp.Object.MonAn;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.ActivityShowDetailBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class ShowDetail extends AppCompatActivity {

    ActivityShowDetailBinding binding;
    MonAn monAn = null;
    String gia_format;

    TextView btnBack;
    CardView addCart;

    ImageView foodPic,imgTp1,imgTp2,imgTp3,imgTp4;
    TextView foodTxt,caloriesTxt,tvthanhphan;
    TextView txtSao,txtGia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AnhXa();
        cainaylahome();
        cainaylaNut();

    }

    private void cainaylahome() {
        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof MonAn) {
            monAn = (MonAn) object;
        }
        if (monAn != null) {
            Glide.with(getApplicationContext()).load(monAn.getHinhMon()).into(foodPic);
            foodTxt.setText(monAn.getTenMon());
            caloriesTxt.setText(String.valueOf(monAn.getCalories()));
            tvthanhphan.setText(String.valueOf(monAn.getMoTa()));
            txtSao.setText(String.valueOf(monAn.getSao()));

            gia_format = NumberFormat.getNumberInstance(Locale.US).format(monAn.getGia());
            txtGia.setText(gia_format + " đ");

        }
    }


    private void cainaylaNut() {
        btnBack=(TextView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDetail.this, ListProduct.class));
            }
        });
        addCart=(CardView) findViewById(R.id.addCart);
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDetail.this, MyCart.class));
            }
        });
    }

    private void AnhXa() {
        foodPic=(ImageView) findViewById(R.id.foodPic);
        foodTxt=(TextView) findViewById(R.id.foodTxt);
        caloriesTxt=(TextView) findViewById(R.id.calotxt);
        tvthanhphan=(TextView) findViewById(R.id.thanhphan);
        txtSao=(TextView) findViewById(R.id.txtSao);
        txtGia=(TextView) findViewById(R.id.txtGia);
    }
}