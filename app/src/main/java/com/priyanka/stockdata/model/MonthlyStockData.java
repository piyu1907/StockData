package com.priyanka.stockdata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priyanka on 11/21/17.
 */

public class MonthlyStockData {

    private String month;
    private double averageOpen;
    private double averageClose;

    public MonthlyStockData(String month, double averageOpen, double averageClose) {
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
