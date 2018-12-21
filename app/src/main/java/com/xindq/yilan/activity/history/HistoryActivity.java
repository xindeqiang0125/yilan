package com.xindq.yilan.activity.history;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xindq.yilan.R;
import com.xindq.yilan.activity.history.chart.HistoryChart;
import com.xindq.yilan.domain.HistoryEntry;
import com.xindq.yilan.domain.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements HistoryPresenter.Callback {
    private static final String TAG = "HistoryActivity";
    private HistoryChart chart;
    private HistoryPresenter presenter;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();

        presenter = new HistoryPresenter(this, this);
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String end = format.format(now);
        String start = format.format(new Date(now.getTime() - 60000*30));
//        presenter.requsetHistory(item.getId(), "2018-12-21T13:24:42", "2018-12-21T13:55:04");
        presenter.requsetHistory(item.getId(), start, end);
    }

    private void init() {
        item = (Item) getIntent().getSerializableExtra("item");
        Log.i(TAG, "init: " + item);
        chart = findViewById(R.id.chart_history);
    }

    @Override
    public void onGetHistory(List<HistoryEntry> historyEntries) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chart.setItem(item).setHistoryEntries(historyEntries).enableLimitLines().show();
            }
        });
    }
}
