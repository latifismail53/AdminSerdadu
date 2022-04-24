package com.serdadu.adminserdadu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serdadu.adminserdadu.R;
import com.serdadu.adminserdadu.UpdateOrderAct;
import com.serdadu.adminserdadu.UpdateUserAct;
import com.serdadu.adminserdadu.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    ArrayList<Order> list;


    public OrderAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        int positions = holder.getAdapterPosition();

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        formatRupiah.setMaximumFractionDigits(0);

        Order order = list.get(position);
        holder.item_id.setText(order.getId().toString());
        holder.item_totalhari.setText(order.getTotalhari()+" Hari");
        holder.item_username.setText(order.getUsernamepemesan()+"");
//        holder.item_totalharga.setText(order.getTotalharga().toString());
        holder.item_tanggal.setText(order.getTanggal());
        holder.item_tanggalorder.setText("Tanggal Pemesanan : "+order.getTanggalbooking());
        holder.item_totalharga.setText(formatRupiah.format((Double) order.getTotalharga()));
        holder.item_namapulau.setText(order.getNamapulau());
        holder.item_namapemesan.setText(order.getNamapemesan());
        holder.item_hpppemesan.setText(order.getHppemesan());
        holder.item_emailpemesan.setText(order.getEmailpemesan());
        holder.item_status.setText(order.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, UpdateOrderAct.class);
                i.putExtra("IDDB_tiket", list.get(position).getId().toString());
                i.putExtra("IDDB_username", list.get(position).getUsernamepemesan());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_id,item_namapemesan,item_emailpemesan,item_namapulau,item_hpppemesan,
        item_totalhari,item_tanggal,item_totalharga,item_status,item_username,item_tanggalorder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_id = itemView.findViewById(R.id.item_id);
            item_namapemesan = itemView.findViewById(R.id.item_namapemesan);
            item_emailpemesan = itemView.findViewById(R.id.item_emailpemesan);
            item_hpppemesan = itemView.findViewById(R.id.item_hppemesan);
            item_namapulau = itemView.findViewById(R.id.item_namapulau);
            item_totalhari = itemView.findViewById(R.id.item_totalhari);
            item_tanggal = itemView.findViewById(R.id.item_tanggal);
            item_totalharga = itemView.findViewById(R.id.item_totalharga);
            item_status = itemView.findViewById(R.id.item_status);
            item_username = itemView.findViewById(R.id.item_username);
            item_tanggalorder = itemView.findViewById(R.id.tanggalorder);

        }
    }
}