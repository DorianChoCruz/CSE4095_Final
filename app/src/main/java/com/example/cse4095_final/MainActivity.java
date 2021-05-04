package com.example.cse4095_final;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cse4095_final.db.Stock;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    public PortfolioViewModel PortfolioModel;
    private LiveData<List<Stock>> allStocks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PortfolioModel = new ViewModelProvider(this).get(PortfolioViewModel.class);
        BottomNavigationView toolbar = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_stocks, R.id.navigation_Create).build();
        NavController Controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, Controller, appBarConfiguration);
        NavigationUI.setupWithNavController(toolbar, Controller);
        PortfolioModel.getAllStocks().observe(this,
                new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {

                for (Stock stock : stocks) {
                    if (!PortfolioModel.getAllStocks().getValue().contains(stock)){
                        PortfolioModel.getAllStocks().getValue().add(stock);

                    }
                }
                Controller.navigate(R.id.navigation_stocks);
            }
        });

        Controller.navigate(R.id.navigation_Create);

    }
    public boolean isStockInDatabase_faster(String name) {
        // Given Code from Class Github
        boolean inDB = false;
        if (PortfolioModel.getAllStocks().getValue() == null){
            return false;
        }
        for (Stock stock : PortfolioModel.getAllStocks().getValue()) {
            if (name.equals(stock.name)) {
                inDB = true;
                break;
            }
        }

        return inDB;
    }
    public io.reactivex.Observer<Stock> getStockObserver(Stock stock) {
        // Given Code from Class Github
        return new io.reactivex.Observer<Stock>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("DB", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Stock stock) {
                switch(stock.databaseOperations) {
                    case INSERT:
                        if (!isStockInDatabase_faster(stock.name)) {
                            PortfolioModel.getPortfolioDatabase().stockDao().insert(stock);
                        }
                        break;
                    case DELETE:
                        PortfolioModel.getPortfolioDatabase().stockDao().delete(stock);
                        break;
                    case UPDATE:
                        Log.i("DB", "Update");
                        PortfolioModel.getPortfolioDatabase().stockDao().update(stock);
                        break;
                    default:
                        Log.i("DB", "Default");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("DB", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("DB", "All items are emitted!");

            }
        };
    }
}

