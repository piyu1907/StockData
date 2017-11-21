package com.priyanka.stockdata.model;

/**
 * Created by priyanka on 11/21/17.
 */

public class StockData {
    String date;
    double open;
    double close;
    double high;
    double low;

    public StockData(String date, double open, double high, double low, double close) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public String getDate() {
        return date;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    @Override
    public String toString() {
        return getDate() + " " + getOpen() + " " + getHigh() + " " + getLow() + " " + getClose();
    }
}
