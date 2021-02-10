package com.compactit.compactit.compactisolution.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.compactit.compactit.compactisolution.R;
import com.compactit.compactit.compactisolution.model.Books_Model;
import com.compactit.compactit.compactisolution.model.Order_Model;
import com.compactit.compactit.compactisolution.viewholder.userOrder_VH;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class UserOrders_adapter extends RecyclerView.Adapter<userOrder_VH> {

    Context context;
    List<Order_Model> list;

    public UserOrders_adapter() {
    }

    public UserOrders_adapter(Context context, List<Order_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public userOrder_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_order_vh,parent,false);
        return new userOrder_VH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull userOrder_VH holder, int position) {
        final Order_Model data = list.get(position);
        if (data.getCover() != null && !data.getCover().equals("")) {
            Picasso.get().load(data.getCover()).placeholder(R.drawable.place_holder).error(R.drawable.place_holder).into(holder.cover);
        } else Picasso.get().load(R.drawable.place_holder).into(holder.cover);

        holder.tnxid.setText("TnxID: "+data.getTxid()+"");
        holder.bname.setText("Book Name: "+data.getbName()+"");
        holder.aname.setText("Author: "+data.getaName()+"");
        holder.quantity.setText("Quantity: "+data.getItemNum()+"");
        holder.price.setText("Total Price: "+data.getAmount()+"");
        holder.status.setText("Status: "+data.getStatus()+"");


        if (data.getStatus().equals("approved")){
            holder.status.setTextColor(Color.parseColor("#0AD025"));
        } else holder.status.setTextColor(Color.parseColor("#FF5733"));


        if (data.getStatus().equals("pending")) {
            holder.status.setTextColor(Color.parseColor("#F4D03F"));
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        holder.date.setText(dateFormat.format(data.getTime())+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
