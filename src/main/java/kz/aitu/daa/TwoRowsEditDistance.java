package kz.aitu.daa;

public class TwoRowsEditDistance implements EditDistance {
    private long operations;

    // State: previous[j] and current[j] store consecutive DP table rows.
    // Transition: same recurrence as full-table DP, using previous[j],
    // current[j-1] and previous[j-1]. Base: first row 0..m; first column i.
    @Override
    public int distance(String a, String b) {
        operations = 0;
        // Keep the shorter input in the columns to minimize space.
        if (b.length() > a.length()) {
            String temp = a;
            a = b;
            b = temp;
        }
        int n = a.length();
        int m = b.length();
        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];
        for (int j = 0; j <= m; j++) previous[j] = j;

        for (int i = 1; i <= n; i++) {
            current[0] = i;
            for (int j = 1; j <= m; j++) {
                operations++; // One computed interior DP cell.
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    current[j] = previous[j - 1];
                } else {
                    current[j] = 1 + Math.min(previous[j - 1],
                            Math.min(previous[j], current[j - 1]));
                }
            }
            int[] temp = previous;
            previous = current;
            current = temp;
        }
        return previous[m];
    }

    @Override
    public long getOperations() { return operations; }

    @Override
    public String getName() { return "TwoRows"; }

    @Override
    public long extraCells(int n, int m) {
        return 2L * (Math.min(n, m) + 1);
    }
}
