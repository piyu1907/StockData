package com.priyanka.stockdata.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.priyanka.stockdata.R;
import com.priyanka.stockdata.model.MonthlyAverageData;

import java.util.List;

/**
 * Created by priyanka on 11/21/17.
 */

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    List<MonthlyAverageData> monthlyAverageDataList;

    public StockAdapter(List<MonthlyAverageData> monthlyAverageDataList) {
        this.monthlyAverageDataList = monthlyAverageDataList;
    }

    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.stock_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StockAdapter.ViewHolder holder, int position) {
        MonthlyAverageData monthlyAverageData = monthlyAverageDataList.get(position);
        holder.month.setText(monthlyAverageData.getMonth());
        holder.averageHigh.setText(monthlyAverageData.getAverageOpen() + "");
        holder.averageLow.setText(monthlyAverageData.getAverageClose() + "");
    }

    @Override
    public int getItemCount() {
        return monthlyAverageDataList.size();
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
