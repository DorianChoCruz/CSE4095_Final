package com.example.cse4095_final.StockAndInsert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.cse4095_final.AlreadyInDatabase;
import com.example.cse4095_final.MainActivity;
import com.example.cse4095_final.PortfolioViewModel;
import com.example.cse4095_final.R;
import com.example.cse4095_final.db.DatabaseOperations;
import com.example.cse4095_final.db.Stock;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


public class InsertFragment extends Fragment {
    private Button submit;
    private EditText fullName;
    private EditText price;
    private EditText symbol;

    private PortfolioViewModel PortfolioModel;
    private final String TAG = "I_F";
    private Observable<Stock> Observe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View start = inflater.inflate(R.layout.content_main,
                container, false);

        submit = start.findViewById(R.id.submitUpdateButton);
        fullName = start.findViewById(R.id.editTextFullName);
        fullName.setHint(R.string.Full_label);
        price = start.findViewById(R.id.editTextPrice);
        price.setHint(R.string.Price_label);
        symbol = start.findViewById(R.id.editTextStockSymbol);
        symbol.setHint(R.string.Symbol_label);
        PortfolioModel = ((MainActivity) getActivity()).PortfolioModel;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String symbol_string = symbol.getText().toString();
                try {
                    double price_string = Double.valueOf(price.getText().toString());
                    String fullName_string = fullName.getText().toString();
                    Stock stock = new Stock(symbol_string, price_string, fullName_string, null);
                    if (((MainActivity) getActivity()).isStockInDatabase_faster(stock.name)){
                        inDataBaseAlert();
                        return;
                    }

                    stock.databaseOperations = DatabaseOperations.INSERT;
                    Observe = Observable.just(stock);
                    io.reactivex.Observer<Stock> observer = ((MainActivity) getActivity()).getStockObserver(stock);
                    Observe.observeOn(Schedulers.io()).subscribe(observer);
                } catch (NumberFormatException e){
                }

            }
        });
        return start;
    }
    private boolean isStockInDatabase_faster(String name) {
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
    private void inDataBaseAlert() {
        new AlreadyInDatabase().show(getActivity().getSupportFragmentManager(), TAG);
    }

}