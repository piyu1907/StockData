package com.priyanka.stockdata.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.priyanka.stockdata.R;
import com.priyanka.stockdata.model.MonthlyAverageData;
import com.priyanka.stockdata.model.StockData;
import com.priyanka.stockdata.util.StockDataUtils;

import java.util.List;
import java.util.Map;

public class StockActivity extends AppCompatActivity implements StockDataCallBack {
    private EditText tickerSymbolEditText;
    private RecyclerView sampleDataRecyclerView;
    private Button submitButton;
    private StockDataCallBack callBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        tickerSymbolEditText = findViewById(R.id.editText);
        sampleDataRecyclerView = findViewById(R.id.recyclerView);
        submitButton = findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StockDataUtils.fetchData(StockActivity.this,
                        StockDataUtils.getURL(tickerSymbolEditText.getText().toString()),
                        StockActivity.this);
            }
        });

    }

    @Override
    public void update(Map<String, List<StockData>> stockDataList) {
        List<MonthlyAverageData> monthlyAverageDataList = StockDataUtils.processMonthlyData(stockDataList);
        for (MonthlyAverageData monthlyAverageData : monthlyAverageDataList) {
            Log.d("Monthly Average Data", monthlyAverageData.toString());
        }
    }
}
