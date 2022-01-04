package com.example.fastfoodapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfoodapp.Activity.MainActivity;
import com.example.fastfoodapp.Activity.ShowDetail;
import com.example.fastfoodapp.Object.MonAn;
import com.example.fastfoodapp.R;

import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MonAnListAdapter extends RecyclerView.Adapter<MonAnListAdapter.ViewHolder> {

    Context context;
    List<MonAn> monAnList;
    String gia_format;

    public MonAnListAdapter(Context context, List<MonAn> monAns) {
        super();
        this.context = context;
        this.monAnList = monAns;
    }

    @NonNull
    @Override
    public MonAnListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_monan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MonAnListAdapter.ViewHolder holder, int position) {

        Glide.with(context)
                .load(monAnList.get(position).getHinhMon())
                .into(holder.imgFood);

        holder.tvName.setText(monAnList.get(position).getTenMon());
        holder.tvStar.setText(String.valueOf(monAnList.get(position).getSao()));
        monAnList.get(position).getMoTa();
        monAnList.get(position).getMoTa();

        gia_format = NumberFormat.getNumberInstance(Locale.US).format(monAnList.get(position).getGia());
        holder.tvPrice.setText(gia_format + " đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowDetail.class);
                intent.putExtra("detail", monAnList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return monAnList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView tvName, tvPrice, tvStar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = (ImageView) itemView.findViewById(R.id.imageviewAnh);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvPrice = (TextView) itemView.findViewById(R.id.gia);
            tvStar = (TextView) itemView.findViewById(R.id.star);
        }
    }

    public void filterList(List<MonAn> filteredList) {
        monAnList = filteredList;
        notifyDataSetChanged();
    }
}