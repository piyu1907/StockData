package com.priyanka.stockdata.model;

/**
 * Created by priyanka on 11/21/17.
 */

public class MonthlyAverageData {
    String month;
    double averageOpen;
    double averageClose;

    public MonthlyAverageData(String month, double averageOpen, double averageClose) {
        this.month = month;
        this.averageOpen = averageOpen;
        this.averageClose = averageClose;
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

    @Override
    public String toString() {
        return month + " " + averageOpen + " " + averageClose;
    }
}
