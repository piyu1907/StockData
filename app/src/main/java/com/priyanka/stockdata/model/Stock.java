package com.priyanka.stockdata.model;

/**
 * Created by priyanka on 11/21/17.
 */

public class Stock {
    String date;
    double open;
    double close;
    double high;
    double low;
    double volume;

    public Stock(String date, double open, double high, double low, double close, double volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
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

    public double getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return getDate() + " " + getOpen() + " " + getHigh() + " " + getLow() + " " + getClose();
    }
}
