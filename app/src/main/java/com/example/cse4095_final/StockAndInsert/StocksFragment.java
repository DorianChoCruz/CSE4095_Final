package com.example.cse4095_final.StockAndInsert;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse4095_final.MainActivity;
import com.example.cse4095_final.PortfolioViewModel;
import com.example.cse4095_final.R;
import com.example.cse4095_final.controller.ItemRecycleView;
import com.example.cse4095_final.db.Stock;

import java.util.ArrayList;

public class StocksFragment extends Fragment {

    private PortfolioViewModel PortfolioModel;
    public static ItemRecycleView itemRecycleView;
    public ArrayList<Stock> allStocks;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i("Stocks", "ONCREATEVIEW CALLED");
        View start = inflater.inflate(R.layout.fragment_stocks, container, false);
        PortfolioModel = ((MainActivity) getActivity()).PortfolioModel;
        allStocks = (ArrayList<Stock>) PortfolioModel.getAllStocks().getValue();
        if (allStocks != null && (allStocks.size() > 0)) {
            recyclerView = start.findViewById(R.id.stock_recyclerView);
            itemRecycleView = new ItemRecycleView(R.layout.portfolio_layout, allStocks);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(itemRecycleView);
            Log.i("Stocks", "ONCREATEVIEW Finished");
        }
        else{
            TextView empty = start.findViewById(R.id.emptyText);
            empty.setText("Add some stocks to your portfolio!");
        }
        return start;
    }


}