package com.priyanka.stockdata.processor;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.priyanka.stockdata.activity.StockDataCallBack;
import com.priyanka.stockdata.model.MonthlyStockData;
import com.priyanka.stockdata.model.StockSummary;
import com.priyanka.stockdata.model.Stock;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Stock Data Processor to analyse the performance of the securities.
 *
 * Created by priyanka on 11/21/17.
 */

public class StockDataProcessor {

    public static String URL = "\n" +
            "https://www.quandl.com/api/v3/datasets/WIKI/";
    public static String URL_QUERY =
            "/data.json?api_key=s-GMZ_xkw6CrkGYUWs1p&start_date=2017-01-01&end_date=2017-06-30&order=asc&collapse=daily";

    public static String getURL(String tickerSymbol) {
        return URL + tickerSymbol + URL_QUERY;
    }

    public static RequestQueue fetchRequestQueue(Context context) {
        return Volley.newRequestQueue(context);
    }

    public static void fetchData(Context context, final String tickerSymbol, final StockDataCallBack callback) {
        String url = getURL(tickerSymbol);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    callback.update(processResponse(response), tickerSymbol);
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.update(null, tickerSymbol);
            }
        });
        fetchRequestQueue(context).add(stringRequest);

    }

    public static Map<String, List<Stock>> processResponse(String response) throws JSONException {
        Map<String, List<Stock>> monthlyDataList = new LinkedHashMap<>();
        List<Stock> stockList = new ArrayList<>();
        Stock tempStock, previousStock = null;
        JSONObject responseJson = new JSONObject(response);
        int month = 0;
        JSONArray dataArray = responseJson.optJSONObject("dataset_data").optJSONArray("data");
        for (int i = 0; i < dataArray.length(); i++) {
            JSONArray jsonArray = dataArray.optJSONArray(i);
            tempStock = new Stock(jsonArray.optString(0), jsonArray.optDouble(1),
                    jsonArray.optDouble(2), jsonArray.optDouble(3), jsonArray.optDouble(4),
                    jsonArray.optDouble(5));

            if (previousStock == null || isFromSameMonth(previousStock, tempStock)) {
                stockList.add(tempStock);
            } else {
                String monthString = Month.of(month + 1).name();
                monthlyDataList.put(monthString, stockList);
                month++;
                stockList = new ArrayList<>();
                stockList.add(tempStock);
            }
            previousStock = tempStock;

        }
        if (!stockList.isEmpty()) {
            String monthString = Month.of(month + 1).name();
            monthlyDataList.put(monthString, stockList);
        }

        return monthlyDataList;
    }

    public static boolean isFromSameMonth(Stock currentStock, Stock nextStock) {
        Date nextDate = DateTimeUtils.formatDate(nextStock.getDate());
        Date currentDate = DateTimeUtils.formatDate(currentStock.getDate());
        DateTimeZone timeZone = DateTimeZone.getDefault();
        DateTime nextDateTime = new DateTime(nextDate, timeZone);
        DateTime currentDateTime = new DateTime(currentDate, timeZone);
        if ((currentDateTime.getMonthOfYear() == nextDateTime.getMonthOfYear()) && (currentDateTime.getYear() == nextDateTime.getYear())) {
            return true;
        }

        return false;
    }

    public static StockSummary processStockData(Map<String, List<Stock>> stockDataList) {
        List<MonthlyStockData> monthlyStockDataList = new ArrayList<>();
        StockSummary stockSummary = new StockSummary();
        int loserDays = 0;
        Map<String, Double> profitableDates = new HashMap<>();
        Map<String, Double> volumeDates = new HashMap<>();
        if (stockDataList == null) {
            return stockSummary;
        }

        for (Map.Entry<String, List<Stock>> entry : stockDataList.entrySet()) {
            List<Stock> listOfStock = entry.getValue();
            List<Double> monthlyOpen = new ArrayList<>();
            List<Double> monthlyClose = new ArrayList<>();

            for (Stock stock : listOfStock) {
                monthlyOpen.add(stock.getOpen());
                monthlyClose.add(stock.getClose());

                //To find number of loser days
                if (stock.getClose() < stock.getOpen()) {
                    loserDays++;
                }

                //Map to get profitable date with corresponding profit
                profitableDates.put(stock.getDate(), round((stock.getHigh() - stock.getLow()), 2));
                //Map to find date with high volume data
                volumeDates.put(stock.getDate(), stock.getVolume());
            }

            double averageOpen = average(monthlyOpen.toArray());
            double averageClose = average(monthlyClose.toArray());

            MonthlyStockData monthlyStockData = new MonthlyStockData(entry.getKey(), averageOpen,
                    averageClose);
            monthlyStockDataList.add(monthlyStockData);
        }

        stockSummary.setMonthlyStockDataList(monthlyStockDataList);
        stockSummary.setLoserDaysCounter(loserDays);

        Map.Entry<String, Double> profitEntry = max(profitableDates);
        stockSummary.setMaxProfit(profitEntry.getValue());
        stockSummary.setMaxProfitDate(profitEntry.getKey());

        Double avgVolume = average(volumeDates.values().toArray());
        stockSummary.setAvgVolume(avgVolume);
        stockSummary.setHighVolumeDate(getHighVolumeDates(volumeDates, avgVolume));

        return stockSummary;
    }

    public static double average(Object[] m) {
        double sum = 0;
        double average = 0;
        for (int i = 0; i < m.length; i++) {
            sum += (double) m[i];
        }
        average = sum / m.length;
        return round(average, 2);
    }

    public static Map.Entry<String, Double> max(Map<String, Double> profitMap) {

        Double maxProfit = 0.0;
        Map.Entry<String, Double> maxProfitEntry = null;

        for (Map.Entry<String, Double> profitEntry : profitMap.entrySet()
                ) {
            if (profitEntry.getValue() > maxProfit) {
                maxProfit = profitEntry.getValue();
                maxProfitEntry = profitEntry;
            }
        }
        return maxProfitEntry;
    }

    public static List<String> getHighVolumeDates(Map<String, Double> volumeMap, double avgVolume) {
        List<String> highVolumeList = new ArrayList<>();

        for (Map.Entry<String, Double> volumeEntry : volumeMap.entrySet()
                ) {
            if (volumeEntry.getValue() - avgVolume > 1.1 * avgVolume) {
                highVolumeList.add(volumeEntry.getKey());
            }
        }
        return highVolumeList;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
