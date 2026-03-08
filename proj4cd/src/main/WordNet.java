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
            Set<String> wordSet = new TreeSet<>();

            for (String word : words) {
                wordSet.add(word);
//                if (wordToID.containsKey(word)) {
//                    wordToID.get(word).add(id);
//                } else {
//                    wordToID.put(word, new TreeSet<>(Arrays.asList(id)));
//                }
                wordToID.computeIfAbsent(word, k -> new TreeSet<>()).add(id);
            }
            idToWord.put(id, wordSet);
        }

        while (!hyponymsIn.isEmpty()) {
            String[] splitLine = hyponymsIn.readLine().split(",");
            int parentID = Integer.parseInt(splitLine[0]);
            Set<Integer> childIDs = new TreeSet<>();
            for (int i = 1; i < splitLine.length; i++) {
                childIDs.add(Integer.parseInt(splitLine[i]));
            }
            parentIDToChildID.computeIfAbsent(parentID, k -> new TreeSet<>()).addAll(childIDs);
        }

        synsetsIn.close();
        hyponymsIn.close();
    }

    public Set<Integer> wordToID(String word) {
        return new TreeSet<>(wordToID.getOrDefault(word, new TreeSet<>()));
    }

    // synsets
    public Set<String> idToWord(int id) {
        return new TreeSet<>(idToWord.getOrDefault(id, new TreeSet<>()));
    }

    public Set<Integer> parentIDToChildID(int parentID) {
        return new TreeSet<>(parentIDToChildID.getOrDefault(parentID, new TreeSet<>()));
    }

    public Set<Integer> parentIDToDescendantID(int parentID) {
        return parentIDToDescendantID(parentID, new TreeSet<>());
    }

    private Set<Integer> parentIDToDescendantID(int parentID, Set<Integer> descendantIDs) {
        if (!descendantIDs.add(parentID)) {
            return descendantIDs;
        }
        for (int childID : parentIDToChildID(parentID)) {
            parentIDToDescendantID(childID, descendantIDs);
        }
        return descendantIDs;
    }

    public Set<String> parentIDToDescendant(int parentID) {
        Set<String> descendants = new TreeSet<>();
        for (int id : parentIDToDescendantID(parentID)) {
            descendants.addAll(this.idToWord(id));
        }
        return descendants;
    }
}
