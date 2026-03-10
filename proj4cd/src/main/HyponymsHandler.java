package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import org.checkerframework.checker.units.qual.K;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final HandlerHelper helper;

    public HyponymsHandler(HandlerHelper helper) {
        this.helper = helper;
    }

    @Override
    public String handle(NgordnetQuery q) {
        return helper.hyponymsHandler(q.words(), q.startYear(), q.endYear(), q.k()).toString();
    }
}

class HandlerHelper {
    private final WordNet wordNet;
    private final NGramMap nGramMap;

    public HandlerHelper(WordNet wordNet, NGramMap nGramMap) {
        this.wordNet = wordNet;
        this.nGramMap = nGramMap;
    }

    Set<String> hyponymsHandler(List<String> words, int startYear, int endYear, int k) {
        Set<String> response;

        if (k == 0) {
            // ignore the startYear and endYear
            response = new TreeSet<>(wordNet.severalToSingle(words));
        } else {
            Set<String> candidates = new HashSet<>(wordNet.severalToSingle(words));
            // TimeSeries -> TreeSet<Integer, Double>
            PriorityQueue<Pair<String, Double>> minHeap = new PriorityQueue<>(getComparator());
            for (String word : candidates) {
                TimeSeries ts = nGramMap.countHistory(word, startYear, endYear);
                double sum = 0;
                for (double value : ts.values()) {
                    sum += value;
                }
                if (sum != 0) {
                    Pair<String, Double> pair = new Pair<>(word, sum);
                    minHeap.add(pair);
                }
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }

            response = new TreeSet<>();
            while(!minHeap.isEmpty()) {
                Pair<String, Double> pair = minHeap.poll();
                response.add(pair.k());
            }
        }

        return response;
    }

    private record Pair<K, V>(K k, V v) {}

    private Comparator<Pair<String, Double>> getComparator() {
        return new Comparator<Pair<String, Double>>() {
            @Override
            public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
                int cmp = o1.v().compareTo(o2.v());
                if (cmp == 0) {
                    cmp = o2.k().compareTo(o1.k());
                }
                return cmp;
            }
        };
    }

    TimeSeries HypohistHandler(String word, int startYear, int endYear) {
        return new TimeSeries(nGramMap.weightHistory(word, startYear, endYear), startYear, endYear);
    }
}
