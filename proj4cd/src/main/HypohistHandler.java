package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.*;

public class HypohistHandler extends NgordnetQueryHandler {
    private final HandlerHelper helper;

    public HypohistHandler(HandlerHelper helper) {
        this.helper = helper;
    }

    @Override
    public String handle(NgordnetQuery q) {
        Set<String> words = helper.hyponymsHandler(q.words(), q.startYear(), q.endYear(), q.k());

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : words) {
            labels.add(word);
            TimeSeries ts = helper.HypohistHandler(word, q.startYear(), q.endYear());
            lts.add(ts);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
