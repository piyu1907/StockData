package com.priyanka.stockdata.model;

import java.util.List;

/**
 * Model class to store stock performance summary for respective security
 * after processing the data obtained from API.
 * Created by priyanka on 11/21/17.
 */

public class StockSummary {

    private int loserDaysCounter;

    private double avgVolume;

    private String maxProfitDate;

    private double maxProfit;

    private List<String> highVolumeDate;

    private List<MonthlyStockData> monthlyStockDataList;

    public int getLoserDaysCounter() {
        return loserDaysCounter;
    }

    public void setLoserDaysCounter(int loserDaysCounter) {
        this.loserDaysCounter = loserDaysCounter;
    }

    public double getAvgVolume() {
        return avgVolume;
    }

    public void setAvgVolume(double avgVolume) {
        this.avgVolume = avgVolume;
    }

    public String getMaxProfitDate() {
        return maxProfitDate;
    }

    public void setMaxProfitDate(String maxProfitDate) {
        this.maxProfitDate = maxProfitDate;
    }

    public double getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(double maxProfit) {
        this.maxProfit = maxProfit;
    }

    public List<String> getHighVolumeDate() {
        return highVolumeDate;
    }

    public void setHighVolumeDate(List<String> highVolumeDate) {
        this.highVolumeDate = highVolumeDate;
    }

    public List<MonthlyStockData> getMonthlyStockDataList() {
        return monthlyStockDataList;
    }

    public void setMonthlyStockDataList(List<MonthlyStockData> monthlyStockDataList) {
        this.monthlyStockDataList = monthlyStockDataList;
    }
}
