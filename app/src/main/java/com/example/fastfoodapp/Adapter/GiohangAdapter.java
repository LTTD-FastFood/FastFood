package com.example.fastfoodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfoodapp.Helper.ChangeNumberItemListener;
import com.example.fastfoodapp.Helper.ManagementCard;
import com.example.fastfoodapp.Model.MonAn;
import com.example.fastfoodapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GiohangAdapter extends RecyclerView.Adapter<GiohangAdapter.ViewHolder> {

    private ArrayList<MonAn> monAns;
    private ManagementCard managementCard;
    private ChangeNumberItemListener changeNumberItemListener;
    String gia_format, tong_format;

    public GiohangAdapter(ArrayList<MonAn> foodDomains, Context context, ChangeNumberItemListener changeNumberItemListener) {
        this.monAns = foodDomains;
        managementCard=new ManagementCard(context);
        this.changeNumberItemListener = changeNumberItemListener;
    }

    @NonNull
    @Override
    public GiohangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View intflate = LayoutInflater.from((parent.getContext())).inflate(R.layout.layout_giohang, parent, false);

        return new ViewHolder(intflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GiohangAdapter.ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext()).load(monAns.get(position).getHinhMon()).into(holder.hinhMon);

        holder.TenSP.setText(monAns.get(position).getTenMon());
        holder.SoLuong.setText(String.valueOf(monAns.get(position).getNumberInCard()));


        gia_format = NumberFormat.getNumberInstance(Locale.US).format(monAns.get(position).getGia());
        holder.giaBan.setText(gia_format + "đ");

        tong_format = NumberFormat.getNumberInstance(Locale.US).format(monAns.get(position).getNumberInCard() * monAns.get(position).getGia());
        holder.tong.setText(tong_format + "đ");

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCard.plusNumberFood(monAns, position, new ChangeNumberItemListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemListener.changed();
                    }
                });
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCard.MinusNumberFood(monAns, position, new ChangeNumberItemListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemListener.changed();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return monAns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView TenSP, giaBan, SoLuong, tong;
        ImageView plus, minus, hinhMon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TenSP = (TextView) itemView.findViewById(R.id.ten);
            SoLuong = (TextView) itemView.findViewById(R.id.soluong);
            giaBan = (TextView) itemView.findViewById(R.id.giaMon);
            tong = (TextView) itemView.findViewById(R.id.tongMon);
            plus = itemView.findViewById(R.id.plusBtn);
            hinhMon = itemView.findViewById(R.id.imgMon);
            minus = itemView.findViewById(R.id.minusBtn);
        }
    }

    public void filterList(ArrayList<MonAn> filteredList) {
        monAns = filteredList;
        notifyDataSetChanged();
    }
}
