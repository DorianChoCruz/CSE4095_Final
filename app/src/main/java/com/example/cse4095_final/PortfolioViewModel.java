package com.example.cse4095_final;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cse4095_final.db.PortfolioDatabase;
import com.example.cse4095_final.db.Stock;

import java.util.List;

public class PortfolioViewModel extends AndroidViewModel {

    private LiveData<List<Stock>> allStocks;
    private static PortfolioDatabase portfolioDatabase;

    public PortfolioViewModel(@NonNull Application application) {
        super(application);

        portfolioDatabase = PortfolioDatabase.getInstance(application);
        allStocks = portfolioDatabase.stockDao().getAll();
    }

    public LiveData<List<Stock>> getAllStocks() {

        return allStocks;
    }

    public PortfolioDatabase getPortfolioDatabase(){
        return portfolioDatabase;
    }
}
