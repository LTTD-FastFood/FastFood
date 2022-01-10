package com.example.fastfoodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfoodapp.Helper.ChangeNumberItemListener;
import com.example.fastfoodapp.Helper.ManagementCard;
import com.example.fastfoodapp.Model.MonAn;
import com.example.fastfoodapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<MonAn> monAns;
    private ManagementCard managementCard;
    private ChangeNumberItemListener changeNumberItemListener;
    String gia_format;

    public OrderAdapter(ArrayList<MonAn> monAns,Context context, ChangeNumberItemListener changeNumberItemListener) {
        this.monAns = monAns;
        managementCard=new ManagementCard(context);
        this.changeNumberItemListener = changeNumberItemListener;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_myorder,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(monAns.get(position).getHinhMon()).into(holder.imgOrder);

        holder.tvTenMon.setText(monAns.get(position).getTenMon());
        holder.tvSoluong.setText(String.valueOf(monAns.get(position).getNumberInCard()));

        gia_format = NumberFormat.getNumberInstance(Locale.US).format(monAns.get(position).getGia());
        holder.tvGiaMon.setText(gia_format + "đ");

    }

    @Override
    public int getItemCount() {
        return monAns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgOrder;
        TextView tvTenMon,tvGiaMon,tvSoluong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOrder = itemView.findViewById(R.id.imgOrder);
            tvTenMon = itemView.findViewById(R.id.tenOrder);
            tvGiaMon = itemView.findViewById(R.id.giaOrder);
            tvSoluong = itemView.findViewById(R.id.soluongOrder);
        }
    }
}