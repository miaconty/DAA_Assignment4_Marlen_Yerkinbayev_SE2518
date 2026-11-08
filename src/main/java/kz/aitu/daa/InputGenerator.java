package kz.aitu.daa;

import java.util.Random;

public class InputGenerator {
    // Every input is derived independently from n and a fixed seed.
    private static final long SEED = 20261108L;

    public static String randomString(int n, long salt) {
        Random random = new Random(SEED + salt * 1000003L + n);
        StringBuilder builder = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            builder.append((char) ('a' + random.nextInt(4)));
        }
        return builder.toString();
    }

    public static String repeat(char c, int n) {
        return String.valueOf(c).repeat(n);
    }

    public static String[] editInput(int n, String type) {
        if (type.equals("random")) {
            return new String[] {randomString(n, 1), randomString(n, 2)};
        }
        if (type.equals("different")) {
            return new String[] {repeat('a', n), repeat('b', n)};
        }
        throw new IllegalArgumentException(type);
    }

    public static String[] searchInput(int n, String type) {
        if (type.equals("random")) {
            return new String[] {randomString(n, 3), randomString(12, 4)};
        }
        if (type.equals("repetitive")) {
            // Many near-matches force Naive to revisit the same characters.
            return new String[] {repeat('a', n), repeat('a', 11) + "b"};
        }
        throw new IllegalArgumentException(type);
    }
}
