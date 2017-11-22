package com.priyanka.stockdata.activity;

import com.priyanka.stockdata.model.Stock;

import java.util.List;
import java.util.Map;

/**
 * Created by priyanka on 11/21/17.
 */

public interface StockDataCallBack {
    void update(Map<String, List<Stock>> list, String symbol);
}
