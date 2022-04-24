package com.serdadu.adminserdadu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serdadu.adminserdadu.R;
import com.serdadu.adminserdadu.UpdatePulauAct;
import com.serdadu.adminserdadu.model.Travel;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Travel> list;


    public MyAdapter(Context context, ArrayList<Travel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_travel,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        int positions = holder.getAdapterPosition();

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        formatRupiah.setMaximumFractionDigits(0);

        Travel travel = list.get(position);
        holder.travel.setText(travel.getTravel());
        holder.deskrispi.setText(travel.getDeskripsi());
//        holder.harga.setText("Rp"+travel.getHarga()+ " PerPax") ;

        holder.harga.setText(formatRupiah.format((Double) travel.getHarga()));
        holder.id.setText(travel.getId());
        Picasso.get()
                .load(travel.getGambar().toString())
                .fit()
                .centerCrop()
                .error(R.drawable.ic_launcher_foreground)
                .fit().into(holder.gambar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, UpdatePulauAct.class);
                i.putExtra("IDDB", list.get(position).getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView travel,deskrispi,id,harga;
        ImageView gambar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            harga = itemView.findViewById(R.id.harga);
            travel = itemView.findViewById(R.id.title_travel);
            deskrispi = itemView.findViewById(R.id.subtitle_travel);
            gambar = itemView.findViewById(R.id.img_travel);
            id = itemView.findViewById(R.id.id_db);

        }
    }

}