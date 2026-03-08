package main;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private final Map<String, TimeSeries> wordHistoryData;
    private final TimeSeries yearHistoryData;

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In wordHistoryIn = new In(wordHistoryFilename);
        In yearHistoryIn = new In(yearHistoryFilename);
        wordHistoryData = new HashMap<String, TimeSeries>();
        yearHistoryData = new TimeSeries();

        while (!yearHistoryIn.isEmpty()) {
            // int(year) , int(double) total number of words
            String[] splitLine = yearHistoryIn.readLine().split(",");
            yearHistoryData.put(Integer.parseInt(splitLine[0]), Double.parseDouble(splitLine[1]));
        }

        while (!wordHistoryIn.isEmpty()) {
            // string(word)    int(year)    int(double)(#)
            String[] splitLine = wordHistoryIn.readLine().split("\t");
//            if (wordHistoryData.containsKey(splitLine[0])) {
//                wordHistoryData.get(splitLine[0]).put(Integer.parseInt(splitLine[1]), Double.parseDouble((splitLine[2])));
//            } else {
//                TimeSeries newTS = new TimeSeries();
//                newTS.put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
//                wordHistoryData.put(splitLine[0], newTS);
//            }

            wordHistoryData.computeIfAbsent(splitLine[0], k -> new TimeSeries())
                    .put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
        }

        wordHistoryIn.close();
        yearHistoryIn.close();
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
//        TimeSeries returnTS = new TimeSeries();
//        for (int year : wordHistoryData.getOrDefault(word, new TimeSeries()).keySet()) {
//            if (year >= startYear && year <= endYear) {
//                returnTS.put(year, wordHistoryData.get(word).get(year));
//            }
//        }
//        return returnTS;

        return new TimeSeries(wordHistoryData.getOrDefault(word, new TimeSeries()), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return new TimeSeries(wordHistoryData.getOrDefault(word, new TimeSeries()), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        // Defensive copy!
        return new TimeSeries(yearHistoryData, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        return countHistory(word, startYear, endYear).dividedBy(totalCountHistory());
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        return countHistory(word).dividedBy(totalCountHistory());
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries summedCountHistory = new TimeSeries();
        for (String word : words) {
            // Notice that I should accept the return value of plus method!
            summedCountHistory = summedCountHistory.plus(countHistory(word, startYear, endYear));
        }
        return summedCountHistory.dividedBy(totalCountHistory());
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries summedCountHistory = new TimeSeries();
        for (String word : words) {
            summedCountHistory = summedCountHistory.plus(countHistory(word));
        }
        return summedCountHistory.dividedBy(totalCountHistory());
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}



//package main;
//import edu.berkeley.eecs.inst.cs61b.ngrams.StaffNGramMap;
//
//import java.util.TreeMap;
//
///** An object that provides utility methods for making queries on the
// *  Google NGrams dataset (or a subset thereof).
// *
// *  An NGramMap stores pertinent data from a "words file" and a "counts
// *  file". It is not a map in the strict sense, but it does provide additional
// *  functionality.
// *
// *  This is a stripped-down version of the staff solution for 4A. Feel free
// *  to replace this file with your own implementation.
// *
// *  @author Josh Hug
// */
//public class NGramMap extends StaffNGramMap {
//
//    public static final int MIN_YEAR = 1400;
//    public static final int MAX_YEAR = 2100;
//
//    /** Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME. */
//    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
//        super(wordHistoryFilename, yearHistoryFilename);
//    }
//
//    /**
//     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
//     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
//     * words, changes made to the object returned by this function should not also affect the
//     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
//     * returns an empty TimeSeries.
//     */
//    public TreeMap<Integer, Double> countHistory(String word, int startYear, int endYear) {
//        return super.countHistory(word, startYear, endYear);
//    }
//
//    /**
//     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
//     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
//     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
//     * is not in the data files, returns an empty TimeSeries.
//     */
//    public TreeMap<Integer, Double> countHistory(String word) {
//        return countHistory(word, MIN_YEAR, MAX_YEAR);
//    }
//
//    // TODO: Replace this file with your own implementation if you want all the methods of an NGramMap
//}
