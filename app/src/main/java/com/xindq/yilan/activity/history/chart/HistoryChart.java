package com.xindq.yilan.activity.history.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.xindq.yilan.domain.HistoryEntry;
import com.xindq.yilan.domain.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryChart extends LineChart {
    private static final String TAG = "HistoryChart";
    private Item item;
    private LimitLine minLimitLine;
    private LimitLine maxLimitLine;
    private LimitLine normal;
    private List<Entry> entries;
    private long startTime = 0;

    public HistoryChart(Context context) {
        super(context);
        initChartExterior();
    }

    public HistoryChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChartExterior();
    }

    public HistoryChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initChartExterior();
    }

    private void initChartExterior() {
        XAxis xAxis = getXAxis();
        YAxis yAxisLeft = getAxisLeft();
        YAxis yAxisRight = getAxisRight();
        xAxis.setEnabled(false);
        yAxisRight.setEnabled(false);
//        xAxis.setValueFormatter(new XValueFormatter());
        yAxisLeft.setValueFormatter(new YValueFormatter());
//        yAxisRight.setValueFormatter(new YValueFormatter());
        setDrawBorders(true);
    }

    public HistoryChart setHistoryEntries(List<HistoryEntry> historyEntries) {
        entries = new ArrayList<>();
        if (historyEntries != null && historyEntries.size() > 0)
            startTime = historyEntries.get(0).getLongTime();
        for (HistoryEntry historyEntry : historyEntries) {
            entries.add(new Entry(historyEntry.getLongTime() - startTime, historyEntry.getValue()));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, item.getItemName() + "前30分钟数据");
        LineData data = new LineData(lineDataSet);
        setData(data);
        return this;
    }

    public HistoryChart setItem(Item item) {
        this.item = item;
        return this;
    }

    public HistoryChart enableLimitLines() {
        YAxis yAxis = getAxisLeft();
        if (item.getMin() != null) {
            float minValue = item.getMin().floatValue();
            minLimitLine = new LimitLine(minValue, "下限");
            minLimitLine.setLineWidth(2f); //宽度
            minLimitLine.setTextSize(10f);
            minLimitLine.setTextColor(Color.BLUE);  //颜色
            minLimitLine.setLineColor(Color.BLUE);
            yAxis.addLimitLine(minLimitLine);
            yAxis.setAxisMinimum(Math.min(yAxis.getAxisMinimum(), minValue));
        }
        if (item.getMax() != null) {
            float maxValue = item.getMax().floatValue();
            maxLimitLine = new LimitLine(maxValue, "上限");
            maxLimitLine.setLineWidth(2f); //宽度
            maxLimitLine.setTextSize(10f);
            maxLimitLine.setTextColor(Color.RED);  //颜色
            maxLimitLine.setLineColor(Color.RED);
            yAxis.addLimitLine(maxLimitLine);
            yAxis.setAxisMaximum(Math.max(yAxis.getAxisMaximum(), maxValue));
        }
        if (item.getNormal() != null) {
            normal = new LimitLine(item.getNormal().floatValue(), "标准值");
            normal.setLineWidth(2f); //宽度
            normal.setTextSize(10f);
            normal.setTextColor(Color.GREEN);  //颜色
            normal.setLineColor(Color.GREEN);
            yAxis.addLimitLine(normal);
        }
        return this;
    }

    public void show() {
        invalidate();
    }

    private class XValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(new Date((long) value + startTime));
            return time;
        }
    }

    private class YValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return value + item.getUnit();
        }
    }
}
