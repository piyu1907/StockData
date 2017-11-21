package com.priyanka.stockdata.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.priyanka.stockdata.activity.StockDataCallBack;
import com.priyanka.stockdata.model.MonthlyAverageData;
import com.priyanka.stockdata.model.StockData;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by priyanka on 11/21/17.
 */

public class StockDataUtils {
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

    public static void fetchData(Context context, String url, final StockDataCallBack callback) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    callback.update(processResponse(response));
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.update(null);
            }
        });
        fetchRequestQueue(context).add(stringRequest);

    }

    private static Map<String, List<StockData>> processResponse(String response) throws JSONException {
        Map<String, List<StockData>> monthlyDataList = new HashMap<>();
        List<StockData> stockDataList = new ArrayList<>();
        StockData tempStockData, previousStockData = null;
        JSONObject responseJson = new JSONObject(response);
        int month = 0;
        JSONArray dataArray = responseJson.optJSONObject("dataset_data").optJSONArray("data");
        for (int i = 0; i < dataArray.length(); i++) {
            JSONArray jsonArray = dataArray.optJSONArray(i);
            tempStockData = new StockData(jsonArray.optString(0), jsonArray.optDouble(1),
                    jsonArray.optDouble(2), jsonArray.optDouble(3), jsonArray.optDouble(4));
            Log.d("Date",jsonArray.optString(0));
            Log.d("Open",jsonArray.optString(1));
            Log.d("High",jsonArray.optString(2));
            Log.d("Low",jsonArray.optString(3));
            Log.d("Close",jsonArray.optString(4));

            if (previousStockData == null || isFromSameMonth(previousStockData, tempStockData)) {
                stockDataList.add(tempStockData);
            } else {
                monthlyDataList.put(month + "", stockDataList);
                month++;
                stockDataList = new ArrayList<>();
                stockDataList.add(tempStockData);
            }
            previousStockData = tempStockData;

        }
        if(!stockDataList.isEmpty()) {
            monthlyDataList.put(month + "", stockDataList);
        }

        for (Map.Entry<String, List<StockData>> entry : monthlyDataList.entrySet()){
            for (StockData stockData : entry.getValue()) {
                Log.d("Open",String.valueOf(stockData.getOpen()));
                Log.d("Open",String.valueOf(stockData.getClose()));
            }
        }
        return monthlyDataList;
    }

    public static boolean isFromSameMonth(StockData currentStockData, StockData nextStockData) {
        Date nextDate = DateTimeUtils.formatDate(nextStockData.getDate());
        Date currentDate = DateTimeUtils.formatDate(currentStockData.getDate());
        DateTimeZone timeZone = DateTimeZone.getDefault();
        DateTime nextDateTime = new DateTime(nextDate, timeZone);
        DateTime currentDateTime = new DateTime(currentDate, timeZone);
        if ((currentDateTime.getMonthOfYear() == nextDateTime.getMonthOfYear()) && (currentDateTime.getYear() == nextDateTime.getYear())) {
            return true;
        }

        return false;
    }

    public static List<MonthlyAverageData> processMonthlyData(Map<String, List<StockData>> stockDataList) {
        List<MonthlyAverageData> monthlyAverageDataList = new ArrayList<>();
        for (Map.Entry<String, List<StockData>> entry : stockDataList.entrySet()) {
            Log.d("Month", String.valueOf(entry.getKey()));
            List<StockData> listOfStockData = entry.getValue();
            List<Double> monthlyOpen = new ArrayList<>();
            List<Double> monthlyClose = new ArrayList<>();
            for (StockData stockData : listOfStockData) {
                monthlyOpen.add(stockData.getOpen());
                monthlyClose.add(stockData.getClose());
            }

            double averageOpen = average(monthlyOpen.toArray());
            double averageClose = average(monthlyClose.toArray());
            Log.d(String.valueOf(entry.getKey()), String.valueOf(averageOpen));
            MonthlyAverageData monthlyAverageData = new MonthlyAverageData(entry.getKey(), averageOpen, averageClose);
            monthlyAverageDataList.add(monthlyAverageData);
        }

        return monthlyAverageDataList;
    }

    public static double average(Object[] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += (double) m[i];
        }
        return sum / m.length;
    }
}
