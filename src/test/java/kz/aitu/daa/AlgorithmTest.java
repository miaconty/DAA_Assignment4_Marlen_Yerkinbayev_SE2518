package kz.aitu.daa;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class AlgorithmTest {
    private final EditDistance[] edits = {
        new RecursiveEditDistance(), new TableEditDistance(),
        new TwoRowsEditDistance()
    };
    private final StringSearch[] searches = {new NaiveSearch(), new KMPSearch()};

    @Test
    void editReferenceCases() {
        for (EditDistance edit : edits) {
            assertEquals(3, edit.distance("kitten", "sitting"));
            assertEquals(2, edit.distance("flaw", "lawn"));
            assertEquals(5, edit.distance("intention", "execution"));
            assertEquals(3, edit.distance("", "abc"));
            assertEquals(3, edit.distance("abc", ""));
            assertEquals(0, edit.distance("", ""));
            assertEquals(0, edit.distance("abc", "abc"));
        }
    }

    @Test
    void hundredRandomEditPairs() {
        Random random = new Random(41);
        for (int i = 0; i < 100; i++) {
            String a = randomString(random, random.nextInt(6));
            String b = randomString(random, random.nextInt(6));
            int expected = edits[0].distance(a, b);
            for (EditDistance edit : edits) {
                assertEquals(expected, edit.distance(a, b), a + " / " + b);
            }
        }
    }

    @Test
    void lpsReferenceCases() {
        KMPSearch kmp = new KMPSearch();
        assertArrayEquals(new int[] {0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA"));
        assertArrayEquals(new int[] {0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB"));
    }

    @Test
    void searchReferenceAndOverlapCases() {
        for (StringSearch search : searches) {
            assertEquals(List.of(10), search.search(
                    "ABABDABACDABABCABAB", "ABABCABAB"));
            assertEquals(List.of(0, 1, 2, 3), search.search("AAAAA", "AA"));
            assertEquals(List.of(0, 2, 4), search.search("ABABABA", "ABA"));
            assertEquals(List.of(), search.search("abcdef", "xyz"));
        }
    }

    @Test
    void hundredRandomSearches() {
        Random random = new Random(72);
        for (int i = 0; i < 100; i++) {
            String text = randomString(random, random.nextInt(31));
            String pattern = randomString(random, 1 + random.nextInt(8));
            assertEquals(searches[0].search(text, pattern),
                    searches[1].search(text, pattern), text + " / " + pattern);
        }
    }

    @Test
    void searchEdgeCases() {
        for (StringSearch search : searches) {
            assertThrows(IllegalArgumentException.class,
                    () -> search.search("abc", ""));
            assertThrows(IllegalArgumentException.class,
                    () -> search.search("", ""));
            assertEquals(List.of(), search.search("hi", "hello"));
            assertEquals(List.of(), search.search("", "a"));
        }
    }

    private static String randomString(Random random, int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append((char) ('a' + random.nextInt(3)));
        }
        return builder.toString();
    }
}
