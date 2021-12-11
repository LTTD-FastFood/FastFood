package com.example.fastfoodapp.tabHome;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fastfoodapp.R;

public class Dinner extends Fragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dinner,container,false);

//        ImageView imgHinh = (ImageView) view.findViewById(R.id.imageView);
//        imgHinh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(fragment_ont.this,Second.class));
//            }
//        });
        return view;
    }

}