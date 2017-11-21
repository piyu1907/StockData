package com.priyanka.stockdata.model;

/**
 * Created by priyanka on 11/21/17.
 */

public class MonthlyAverageData {

    private String month;
    private double averageOpen;
    private double averageClose;
    private String security;

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
}
