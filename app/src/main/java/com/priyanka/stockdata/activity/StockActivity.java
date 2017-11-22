package com.priyanka.stockdata.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.priyanka.stockdata.R;
import com.priyanka.stockdata.adapter.StockAdapter;
import com.priyanka.stockdata.model.MonthlyStockData;
import com.priyanka.stockdata.model.StockSummary;
import com.priyanka.stockdata.model.Stock;
import com.priyanka.stockdata.processor.StockDataProcessor;

import java.util.List;
import java.util.Map;

public class StockActivity extends AppCompatActivity implements StockDataCallBack {
    private RecyclerView cofRecyclerView, googlRecyclerView, msftRecyclerView;
    private TextView cofTextView, googlTextView, msftTextView, cofiMaxProfitTextView,
            googlMaxProfitTextView, msftMaxProfitTextView,
            cofBusyDayTextView, googleBusyDayTextView,
            msftBusyDayTextView, cofBiggestLoserTextView,
            googleBiggestLoserTextView, msftBiggestLoserTextView;
    private StockAdapter cofAdapter, googlAdapter, msftAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        cofRecyclerView = findViewById(R.id.cofRecyclerView);
        googlRecyclerView = findViewById(R.id.googlRecyclerView);
        msftRecyclerView = findViewById(R.id.msftRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        cofTextView = findViewById(R.id.cof);
        googlTextView = findViewById(R.id.googl);
        msftTextView = findViewById(R.id.msft);
        cofiMaxProfitTextView = findViewById(R.id.cofMaxProfitTextView);
        googlMaxProfitTextView = findViewById(R.id.googleMaxProfitTextView);
        msftMaxProfitTextView = findViewById(R.id.msftMaxProfitTextView);
        cofBusyDayTextView = findViewById(R.id.cofBusyDayTextView);
        googleBusyDayTextView = findViewById(R.id.googleBusyDayTextView);
        msftBusyDayTextView = findViewById(R.id.msftBusyDayTextView);
        cofBiggestLoserTextView = findViewById(R.id.cofBiggestLoserTextView);
        googleBiggestLoserTextView = findViewById(R.id.googleBiggestLoserTextView);
        msftBiggestLoserTextView = findViewById(R.id.msftBiggestLoserTextView);

        progressBar.setVisibility(View.VISIBLE);
        //calling API for COF
        StockDataProcessor.fetchData(this, "COF", this);

        //calling API for GOOGL
        StockDataProcessor.fetchData(this, "GOOGL", this);

        //calling API for MSFT
        StockDataProcessor.fetchData(this, "MSFT", this);
    }

    @Override
    public void update(Map<String, List<Stock>> stockDataList, String symbol) {
        if (stockDataList != null) {
            StockSummary analysis = StockDataProcessor.processStockData(stockDataList);
            for (MonthlyStockData monthlyStockData : analysis.getMonthlyStockDataList()) {
                Log.d("Monthly Average Data", monthlyStockData.toString());
            }
            Log.d("Number of Loser Days : ", String.valueOf(analysis.getLoserDaysCounter()));
            Log.d("Average Volume : ", String.valueOf(analysis.getAvgVolume()));
            Log.d("Max Profit : ", String.valueOf(analysis.getMaxProfit()));
            Log.d("Max Profit Date : ", String.valueOf(analysis.getMaxProfitDate()));

            for (String highVolume : analysis.getHighVolumeDate()) {
                Log.d("High Volume Date : ", highVolume);
            }

            switch (symbol) {
                case "COF":
                    cofAdapter = new StockAdapter(analysis.getMonthlyStockDataList());
                    cofTextView.setVisibility(View.VISIBLE);
                    setupRecyclerView(cofRecyclerView, cofAdapter);
                    cofiMaxProfitTextView.setText(String.format(getString(R.string.max_daily_profit), "COF", analysis.getMaxProfit() + "", analysis.getMaxProfitDate() + ""));
                    cofBiggestLoserTextView.setText(String.format(getString(R.string.biggest_loser), "COF", analysis.getLoserDaysCounter() + ""));
                    cofBusyDayTextView.setText(String.format(getString(R.string.busy_day), "COF", analysis.getHighVolumeDate().size() + ""));
                    break;
                case "GOOGL":
                    googlAdapter = new StockAdapter(analysis.getMonthlyStockDataList());
                    googlTextView.setVisibility(View.VISIBLE);
                    setupRecyclerView(googlRecyclerView, googlAdapter);
                    googlMaxProfitTextView.setText(String.format(getString(R.string.max_daily_profit), "GOOGL", analysis.getMaxProfit() + "", analysis.getMaxProfitDate() + ""));
                    googleBiggestLoserTextView.setText(String.format(getString(R.string.biggest_loser), "GOOGL", analysis.getLoserDaysCounter() + ""));
                    googleBusyDayTextView.setText(String.format(getString(R.string.busy_day), "GOOGL", analysis.getHighVolumeDate().size() + ""));
                    break;
                case "MSFT":
                    msftAdapter = new StockAdapter(analysis.getMonthlyStockDataList());
                    msftTextView.setVisibility(View.VISIBLE);
                    setupRecyclerView(msftRecyclerView, msftAdapter);
                    msftMaxProfitTextView.setText(String.format(getString(R.string.max_daily_profit), "MSFT", analysis.getMaxProfit() + "", analysis.getMaxProfitDate() + ""));
                    msftBiggestLoserTextView.setText(String.format(getString(R.string.biggest_loser), "MSFT", analysis.getLoserDaysCounter() + ""));
                    msftBusyDayTextView.setText(String.format(getString(R.string.busy_day), "MSFT", analysis.getHighVolumeDate().size() + ""));
                    break;
                default:
                    break;
            }
        }
        progressBar.setVisibility(View.GONE);
    }

    private void setupRecyclerView(RecyclerView recyclerView, StockAdapter adapter) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
