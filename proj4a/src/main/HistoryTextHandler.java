package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetServer;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap nGramMap;

    public HistoryTextHandler(NGramMap map) {
        this.nGramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder response = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            response.append(word)
                    .append(": ")
                    .append((nGramMap.weightHistory(word, startYear, endYear)).toString())
                    .append("\n");
//                    .append(System.lineSeparator());
        }

        return response.toString();
    }
}
