package kz.aitu.daa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Experiment {
    private static final int REPEATS = 5;
    private static final int[] RECURSIVE_SIZES = {2, 4, 6, 8, 10};
    private static final int[] DP_SIZES = {32, 64, 128, 256, 512};
    private static final int[] SEARCH_SIZES = {1000, 2000, 4000, 8000, 16000};

    public static void main(String[] args) throws IOException {
        Path file = Path.of("results", "results.csv");
        Files.createDirectories(file.getParent());
        List<String> rows = new ArrayList<>();
        rows.add("family,algorithm,input,n,run,time_ms,operations,extra_cells,answer");

        for (EditDistance algorithm : new EditDistance[] {
                new RecursiveEditDistance(), new TableEditDistance(),
                new TwoRowsEditDistance()}) {
            int[] sizes = algorithm instanceof RecursiveEditDistance
                    ? RECURSIVE_SIZES : DP_SIZES;
            for (String type : new String[] {"random", "different"}) {
                for (int n : sizes) {
                    String[] input = InputGenerator.editInput(n, type);
                    algorithm.distance(input[0], input[1]); // JIT warm-up.
                    for (int run = 1; run <= REPEATS; run++) {
                        long start = System.nanoTime();
                        int answer = algorithm.distance(input[0], input[1]);
                        long elapsed = System.nanoTime() - start;
                        rows.add(String.format(Locale.US,
                                "edit,%s,%s,%d,%d,%.6f,%d,%d,%d",
                                algorithm.getName(), type, n, run,
                                elapsed / 1_000_000.0, algorithm.getOperations(),
                                algorithm.extraCells(n, n), answer));
                    }
                }
            }
        }

        for (StringSearch algorithm : new StringSearch[] {
                new NaiveSearch(), new KMPSearch()}) {
            for (String type : new String[] {"random", "repetitive"}) {
                for (int n : SEARCH_SIZES) {
                    String[] input = InputGenerator.searchInput(n, type);
                    algorithm.search(input[0], input[1]); // JIT warm-up.
                    for (int run = 1; run <= REPEATS; run++) {
                        long start = System.nanoTime();
                        int matches = algorithm.search(input[0], input[1]).size();
                        long elapsed = System.nanoTime() - start;
                        rows.add(String.format(Locale.US,
                                "search,%s,%s,%d,%d,%.6f,%d,%d,%d",
                                algorithm.getName(), type, n, run,
                                elapsed / 1_000_000.0, algorithm.getComparisons(),
                                algorithm instanceof KMPSearch ? 12 : 0, matches));
                    }
                }
            }
        }
        Files.write(file, rows);
        System.out.println("Saved " + (rows.size() - 1) + " runs to " + file);
    }
}
