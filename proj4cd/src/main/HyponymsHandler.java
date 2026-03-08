package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;

    public HyponymsHandler(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    // Return a Set toString
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        // TODO startYear endYear k

        // TODO any other words query

        // Get all ids related to the query word
        Set<Integer> QueryIDs = wordNet.wordToID(words.getFirst());
        // Get all synsets of this word

        Set<String> synsets = new TreeSet<>();
//        Set<Integer> descendantIDs = new TreeSet<>();
        Set<String> descendants = new TreeSet<>(synsets);

        for (int id : QueryIDs) {
            synsets.addAll(wordNet.idToWord(id));
//            descendantIDs.addAll(wordNet.parentIDToDescendantID(id));
            descendants.addAll(wordNet.parentIDToDescendant(id));
        }

        Set<String> response = new TreeSet<>();
        response.addAll(synsets);
        response.addAll(descendants);

        return response.toString();
    }
}
