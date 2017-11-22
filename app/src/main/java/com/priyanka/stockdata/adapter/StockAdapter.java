package com.priyanka.stockdata.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.priyanka.stockdata.R;
import com.priyanka.stockdata.model.MonthlyStockData;

import java.util.List;

/**
 * Created by priyanka on 11/21/17.
 */

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    List<MonthlyStockData> monthlyStockDataList;

    public StockAdapter(List<MonthlyStockData> monthlyStockDataList) {
        this.monthlyStockDataList = monthlyStockDataList;
        this.monthlyStockDataList.add(0, new MonthlyStockData("Month", -1, -1));
    }

    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.stock_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StockAdapter.ViewHolder holder, int position) {
        MonthlyStockData monthlyStockData = monthlyStockDataList.get(position);
        if (position == 0) {
            holder.month.setText(monthlyStockData.getMonth());
            holder.averageHigh.setText("Average Open");
            holder.averageLow.setText("Average Close");
        } else {
            holder.month.setText(monthlyStockData.getMonth());
            holder.averageHigh.setText(monthlyStockData.getAverageOpen() + "");
            holder.averageLow.setText(monthlyStockData.getAverageClose() + "");
        }
    }

    @Override
    public int getItemCount() {
        return monthlyStockDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView month, averageHigh, averageLow;

        public ViewHolder(View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.monthTextView);
            averageHigh = itemView.findViewById(R.id.averageHighTextView);
            averageLow = itemView.findViewById(R.id.averageLowTextView);
        }
    }
}
