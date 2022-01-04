package com.example.fastfoodapp.tabHome;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fastfoodapp.Activity.Home;
import com.example.fastfoodapp.Activity.ShowDetail;
import com.example.fastfoodapp.R;

public class Breakfast extends Fragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_breakfast,container,false);

//        ImageView imgHinh = (ImageView) view.findViewById(R.id.sangmot);
//        imgHinh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Home.this, ShowDetail.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

}