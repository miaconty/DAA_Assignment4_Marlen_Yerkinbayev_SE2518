package kz.aitu.daa;

public interface EditDistance {
    int distance(String a, String b);

    long getOperations();

    String getName();

    long extraCells(int n, int m);
}
