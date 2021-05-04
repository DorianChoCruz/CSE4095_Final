package com.example.cse4095_final.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse4095_final.db.Stock;
import com.example.cse4095_final.db.ViewHolder;

import java.util.ArrayList;

public class ItemRecycleView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int layout_id;
    private ArrayList<Stock> stockList;

    public ItemRecycleView(int id, ArrayList<Stock> stocks) {
        this.layout_id = id;
        this.stockList = stocks;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        ViewHolder v_Holder = new ViewHolder(v, stockList);
        return v_Holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView name = ((ViewHolder) holder).symbol;
        TextView full = ((ViewHolder) holder).fullname;
        TextView price = ((ViewHolder) holder).price;
        name.setText(stockList.get(position).name);
        full.setText("Company Name: "+ stockList.get(position).fullName);
        price.setText("Price: " + stockList.get(position).price);
    }

    @Override
    public int getItemCount() {
        return (stockList == null) ? 0 : stockList.size();
    }


}
