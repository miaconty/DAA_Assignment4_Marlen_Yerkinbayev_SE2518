package kz.aitu.daa;

import java.util.ArrayList;
import java.util.List;

public class KMPSearch implements StringSearch {
    private long comparisons;

    // Public because the assignment requires tests to access it.
    public int[] lps(String pattern) {
        int[] result = new int[pattern.length()];
        int prefixLength = 0;
        int i = 1;
        while (i < pattern.length()) {
            comparisons++; // Count preprocessing comparisons too.
            if (pattern.charAt(i) == pattern.charAt(prefixLength)) {
                result[i++] = ++prefixLength;
            } else if (prefixLength > 0) {
                prefixLength = result[prefixLength - 1];
            } else {
                result[i++] = 0;
            }
        }
        return result;
    }

    @Override
    public List<Integer> search(String text, String pattern) {
        comparisons = 0;
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern must not be empty");
        }
        List<Integer> matches = new ArrayList<>();
        if (pattern.length() > text.length()) return matches;
        int[] prefix = lps(pattern);
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            comparisons++; // An actual search-stage comparison.
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pattern.length()) {
                    matches.add(i - j);
                    j = prefix[j - 1]; // Enables overlapping matches.
                }
            } else if (j > 0) {
                j = prefix[j - 1]; // Never move i backward.
            } else {
                i++;
            }
        }
        return matches;
    }

    @Override
    public long getComparisons() { return comparisons; }

    @Override
    public String getName() { return "KMP"; }
}
