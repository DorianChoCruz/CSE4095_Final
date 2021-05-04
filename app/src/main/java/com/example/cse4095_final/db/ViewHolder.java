package com.example.cse4095_final.db;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse4095_final.R;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView symbol;
    public TextView fullname;
    public TextView price;
    public ArrayList<Stock> stockList;
    public ViewHolder(View itemView, ArrayList<Stock> stocks){
        super(itemView);
        itemView.setOnClickListener(this);
        symbol = itemView.findViewById(R.id.text_Symbol);
        fullname = itemView.findViewById(R.id.text_FullName);
        price = itemView.findViewById(R.id.text_Price);
        stockList = stocks;

    }

    @Override
    public void onClick(View v) {
        Log.i("TAG", "Clicked" + this.symbol.getText());
        Bundle bundle = new Bundle();
        bundle.putSerializable("lstStocks", (Serializable) stockList);
        bundle.putString("nameStock", this.symbol.getText().toString());






    }
}
