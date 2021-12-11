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

public class ShowDetail extends AppCompatActivity {

    ActivityShowDetailBinding binding;

    TextView btnBack;
    CardView addCart;

    ImageView foodPic,imgTp1,imgTp2,imgTp3,imgTp4;
    TextView foodTxt,caloriesTxt,txtMota;
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

        Intent intent = this.getIntent();
        if (intent != null){

            int hinh = intent.getIntExtra("hinh",R.drawable.buger);
            String ten = intent.getStringExtra("ten");
            Double sao = intent.getDoubleExtra("sao",4.5);
            int calo = intent.getIntExtra("calo",150);
            int gia = intent.getIntExtra("gia",130);
            String mota = intent.getStringExtra("Mota");
            int anhMot = intent.getIntExtra("anhMot",R.drawable.cachua);
            int anhHai = intent.getIntExtra("anhHai",R.drawable.cachua);
            int anhBa = intent.getIntExtra("anhBa",R.drawable.cachua);
            int anhBon = intent.getIntExtra("anhBon",R.drawable.cachua);

            binding.foodPic.setImageResource(hinh);
            binding.foodTxt.setText(ten);
            binding.txtSao.setText(sao.toString());
            binding.calotxt.setText(calo + " Calories");
            binding.txtGia.setText(gia+"");
            binding.txtMota.setText(mota);
            binding.imgTp1.setImageResource(anhMot);
            binding.imgTp2.setImageResource(anhHai);
            binding.imgTp3.setImageResource(anhBa);
            binding.imgTp4.setImageResource(anhBon);
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
        imgTp1=(ImageView) findViewById(R.id.imgTp1);
        imgTp2=(ImageView) findViewById(R.id.imgTp2);
        imgTp3=(ImageView) findViewById(R.id.imgTp3);
        imgTp4=(ImageView) findViewById(R.id.imgTp4);
        foodTxt=(TextView) findViewById(R.id.foodTxt);
        caloriesTxt=(TextView) findViewById(R.id.calotxt);
        txtMota=(TextView) findViewById(R.id.txtMota);
        txtSao=(TextView) findViewById(R.id.txtSao);
        txtGia=(TextView) findViewById(R.id.txtGia);
    }
}