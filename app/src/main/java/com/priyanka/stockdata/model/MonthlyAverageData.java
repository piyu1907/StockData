package com.priyanka.stockdata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priyanka on 11/21/17.
 */

public class MonthlyAverageData {

    private String month;
    private double averageOpen;
    private double averageClose;
    private String security;
    private double maxDailyProfit;
    private double volume;
    private List<String> highVolumeDays = new ArrayList<>();
    private int loserDays;

    public MonthlyAverageData(String month, double averageOpen, double averageClose, int loserDays) {
        this.month = month;
        this.averageOpen = averageOpen;
        this.averageClose = averageClose;
        this.loserDays = loserDays;
    }

    public String getMonth() {
        return month;
    }

    public double getAverageOpen() {
        return averageOpen;
    }

    public double getAverageClose() {
        return averageClose;
    }

    public String getSecurity() {
        return security;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setAverageOpen(double averageOpen) {
        this.averageOpen = averageOpen;
    }

    public void setAverageClose(double averageClose) {
        this.averageClose = averageClose;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    @Override
    public String toString() {
        return month + " " + averageOpen + " " + averageClose;
    }

    public double getMaxDailyProfit() {
        return maxDailyProfit;
    }

    public void setMaxDailyProfit(double maxDailyProfit) {
        this.maxDailyProfit = maxDailyProfit;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public List<String> getHighVolumeDays() {
        return highVolumeDays;
    }

    public void setHighVolumeDays(List<String> highVolumeDays) {
        this.highVolumeDays = highVolumeDays;
    }

    public int getLoserDays() {
        return loserDays;
    }

    public void setLoserDays(int loserDays) {
        this.loserDays = loserDays;
    }
}
