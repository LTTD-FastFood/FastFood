package com.example.fastfoodapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.Activity.HistoryDetails;
import com.example.fastfoodapp.Helper.ManagementCard;
import com.example.fastfoodapp.Model.LichSuDH;
import com.example.fastfoodapp.Model.MonAn;
import com.example.fastfoodapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LichSuDHApdater extends RecyclerView.Adapter<LichSuDHApdater.ViewHolder>{

    Context context;
    ArrayList<LichSuDH> lichSuDHArrayList;
//    ArrayList<MonAn> monAnList;
    String gia_format;

    ManagementCard managementCard;
    private int numberOrder = 1;

    public LichSuDHApdater(Context context, ArrayList<LichSuDH> lichSuDHArrayList) {
        this.context = context;
        this.lichSuDHArrayList = lichSuDHArrayList;
    }

    @NonNull
    @Override
    public LichSuDHApdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hoadon_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichSuDH lichSuDH = lichSuDHArrayList.get(position);
        holder.MaHD.setText("HD000".concat(String.valueOf(lichSuDH.getMaHD())));
        holder.NgayBan.setText(String.valueOf(lichSuDH.getNgayBan()));
        String dongia_format = NumberFormat.getNumberInstance(Locale.US).format(lichSuDH.getDonGia());
        holder.DonGia.setText(dongia_format + "đ");
        holder.HoTen.setText(lichSuDH.getTenKH());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HistoryDetails.class);
                intent.putExtra("detailOrder",lichSuDH);

                context.startActivity(intent);



            }
        });

    }


//    @Override
//    public void onBindViewHolder(@NonNull LichSuApdapter.ViewHolder holder, int position) {
//
//
//
//
//        holder.ReOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                MonAn object;
////                object = monAnList.get(position);
////                managementCard = new ManagementCard(context);
////                object.setNumberInCard(numberOrder);
////                managementCard.insertFood(object);
//
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {
        return lichSuDHArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView DonGia,MaHD,NgayBan,HoTen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            DonGia = (TextView) itemView.findViewById(R.id.DonGia);
            MaHD = (TextView) itemView.findViewById(R.id.MaHD);
            NgayBan = (TextView) itemView.findViewById(R.id.NgayBan);
            HoTen = (TextView) itemView.findViewById(R.id.HoTen);


        }
    }

    public void filterList(ArrayList<LichSuDH> filteredList) {
        lichSuDHArrayList = filteredList;
        notifyDataSetChanged();
    }

}
