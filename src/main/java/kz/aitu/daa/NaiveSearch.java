package kz.aitu.daa;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch implements StringSearch {
    private long comparisons;

    @Override
    public List<Integer> search(String text, String pattern) {
        comparisons = 0;
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern must not be empty");
        }
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m) {
                comparisons++; // An actual text/pattern character comparison.
                if (text.charAt(i + j) != pattern.charAt(j)) break;
                j++;
            }
            if (j == m) matches.add(i);
        }
        return matches;
    }

    @Override
    public long getComparisons() { return comparisons; }

    @Override
    public String getName() { return "Naive"; }
}
