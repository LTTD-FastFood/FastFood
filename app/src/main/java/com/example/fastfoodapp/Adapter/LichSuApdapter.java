package com.example.fastfoodapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfoodapp.Activity.ShowDetail;
import com.example.fastfoodapp.Helper.ManagementCard;
import com.example.fastfoodapp.Model.LichSu;
import com.example.fastfoodapp.Model.MonAn;
import com.example.fastfoodapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LichSuApdapter extends RecyclerView.Adapter<LichSuApdapter.ViewHolder>{

    Context context;
    ArrayList<LichSu> lichSuArrayList;
    ArrayList<MonAn> monAnList;
    String gia_format;

    ManagementCard managementCard;
    private int numberOrder = 1;

    public LichSuApdapter(Context context, ArrayList<LichSu> lichSuArrayList) {
        this.context = context;
        this.lichSuArrayList = lichSuArrayList;
    }

    @NonNull
    @Override
    public LichSuApdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dong,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuApdapter.ViewHolder holder, int position) {

        Glide.with(context)
                .load(lichSuArrayList.get(position).getImgFood())
                .into(holder.imgMonHis);

        holder.tvNameHis.setText(lichSuArrayList.get(position).getNameFood());
        holder.tvNgayMuaHis.setText(lichSuArrayList.get(position).getNgayMua());
        holder.tvSoluongHis.setText("x" + String.valueOf(lichSuArrayList.get(position).getSoluong()));

        gia_format = NumberFormat.getNumberInstance(Locale.US).format(lichSuArrayList.get(position).getDonGia());
        holder.tvDonGiaHis.setText(gia_format + "đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(context, ShowDetail.class);
//                intent.putExtra("detail", lichSuArrayList.get(position));
//                context.startActivity(intent);

            }
        });

        holder.ReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                MonAn object;
//                object = monAnList.get(position);
//                managementCard = new ManagementCard(context);
//                object.setNumberInCard(numberOrder);
//                managementCard.insertFood(object);

            }
        });

    }

    @Override
    public int getItemCount() {
        return lichSuArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMonHis;
        Button ReOrder;
        TextView tvNameHis,tvSoluongHis,tvDonGiaHis,tvNgayMuaHis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameHis = (TextView) itemView.findViewById(R.id.tenMonHis);
            tvSoluongHis = (TextView) itemView.findViewById(R.id.soluongHis);
            tvDonGiaHis = (TextView) itemView.findViewById(R.id.dongiaHis);
            tvNgayMuaHis = (TextView) itemView.findViewById(R.id.ngaymuaHis);
            imgMonHis = (ImageView) itemView.findViewById(R.id.imgMonHis);
            ReOrder = (Button) itemView.findViewById(R.id.btnReOrder);

        }
    }
}
