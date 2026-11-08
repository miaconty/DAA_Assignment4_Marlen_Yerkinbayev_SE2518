package kz.aitu.daa;

public class RecursiveEditDistance implements EditDistance {
    private long operations;

    // State: solve(i, j) is the distance between prefixes a[0..i) and b[0..j).
    // Transition: equal last chars -> solve(i-1,j-1); otherwise 1 + minimum
    // of deletion, insertion, and replacement. Base case: empty prefix -> other length.
    @Override
    public int distance(String a, String b) {
        operations = 0;
        return solve(a, b, a.length(), b.length());
    }

    private int solve(String a, String b, int i, int j) {
        operations++; // Count actual recursive calls, including base cases.
        if (i == 0) return j;
        if (j == 0) return i;
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return solve(a, b, i - 1, j - 1);
        }
        int delete = solve(a, b, i - 1, j);
        int insert = solve(a, b, i, j - 1);
        int replace = solve(a, b, i - 1, j - 1);
        return 1 + Math.min(replace, Math.min(delete, insert));
    }

    @Override
    public long getOperations() { return operations; }

    @Override
    public String getName() { return "Recursive"; }

    @Override
    public long extraCells(int n, int m) { return 0; }
}
