package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    // synsets: ID, words, (description)
    // hyponyms: parentID, childID

    // single ID to several words
    private final Map<Integer, Set<String>> idToWord;
    // sing WORD to several ids (different meanings)
    private final Map<String, Set<Integer>> wordToID;
    // hyponyms
    // Exactly is the Graph implementation
    private final Map<Integer, Set<Integer>> parentIDToChildID;

    public WordNet(String synsetsFileName, String hyponymsFileName) {
        In synsetsIn = new In(synsetsFileName);
        In hyponymsIn = new In(hyponymsFileName);
        idToWord = new HashMap<>();
        wordToID = new HashMap<>();
        parentIDToChildID = new HashMap<>();

        while (!synsetsIn.isEmpty()) {
            String[] splitLine = synsetsIn.readLine().split(",");
            int id = Integer.parseInt(splitLine[0]);
            String[] words = splitLine[1].split(" ");
            Set<String> wordSet = new HashSet<>();

            for (String word : words) {
                wordSet.add(word);
//                if (wordToID.containsKey(word)) {
//                    wordToID.get(word).add(id);
//                } else {
//                    wordToID.put(word, new TreeSet<>(Arrays.asList(id)));
//                }
                wordToID.computeIfAbsent(word, k -> new HashSet<>()).add(id);
            }
            idToWord.put(id, wordSet);
        }

        while (!hyponymsIn.isEmpty()) {
            String[] splitLine = hyponymsIn.readLine().split(",");
            int parentID = Integer.parseInt(splitLine[0]);
            Set<Integer> childIDs = new HashSet<>();
            for (int i = 1; i < splitLine.length; i++) {
                childIDs.add(Integer.parseInt(splitLine[i]));
            }
            parentIDToChildID.computeIfAbsent(parentID, k -> new HashSet<>()).addAll(childIDs);
        }

        synsetsIn.close();
        hyponymsIn.close();
    }

    public Set<Integer> wordToID(String word) {
        return new HashSet<>(wordToID.getOrDefault(word, new HashSet<>()));
    }

    // synsets
    public Set<String> idToWord(int id) {
        return new HashSet<>(idToWord.getOrDefault(id, new HashSet<>()));
    }

    public Set<Integer> parentIDToChildID(int parentID) {
        return new HashSet<>(parentIDToChildID.getOrDefault(parentID, new HashSet<>()));
    }

    public Set<Integer> parentIDToDescendantID(int parentID) {
        return parentIDToDescendantID(parentID, new HashSet<>());
    }

    private Set<Integer> parentIDToDescendantID(int parentID, Set<Integer> descendantIDs) {
        if (!descendantIDs.add(parentID)) {
            return descendantIDs;
        }
        for (int childID : parentIDToChildID.getOrDefault(parentID, Collections.emptySet())) {
            parentIDToDescendantID(childID, descendantIDs);
        }
        return descendantIDs;
    }

    public Set<String> parentIDToDescendant(int parentID) {
        Set<String> descendants = new HashSet<>();
        for (int id : parentIDToDescendantID(parentID)) {
            descendants.addAll(this.idToWord(id));
        }
        return descendants;
    }

    /**
     * return word -> result
     * (contain synsets and hyponyms)
     */
    public Set<String> wordToResult(String word) {
        Set<Integer> queryIDs = wordToID(word);
        Set<String> result = new HashSet<>();

        for (int id : queryIDs) {
            result.addAll(parentIDToDescendant(id));
        }

        return result;
    }

    public Set<String> severalToSingle(List<String> words) {
        if (words == null || words.isEmpty()) {
            return new HashSet<>();
        }

        Set<String> result = null;
        for (String word : words) {
            Set<String> current = wordToResult(word);
            if (result == null) {
                result = new HashSet<>(current);
            } else {
                result.retainAll(current);
            }
            if (result.isEmpty()) {
                return result;
            }
        }
        return result;
    }
}
