# DAA Assignment 4: Dynamic Programming and String Algorithms

**Student:** Marlen Yerkinbayev  
**Group:** SE2518  
**Course:** Design and Analysis of Algorithms

## Requirements

Java 17 or newer, Maven 3.8+; Python 3 with matplotlib and pandas only if regenerating PNG plots.

## Commands

```bash
mvn test
mvn -q -DskipTests compile
mvn -q exec:java -Dexec.mainClass=kz.aitu.daa.Experiment
python3 plot_results.py
```

Run commands from the project root. `results/results.csv` contains five measured runs
for every (algorithm, input, n) combination; `results/plots/` has the PNG graphs.
Experiment output overwrites the CSV on each run.

## Implementations

- `RecursiveEditDistance`: uncached top-down recursion.
- `TableEditDistance`: full bottom-up DP table.
- `TwoRowsEditDistance`: bottom-up DP with two arrays.
- `NaiveSearch`: character comparisons at every start position.
- `KMPSearch`: public LPS preprocessing and forward-only text scan.

The Edit Distance implementations have the same `distance(String, String)`
interface. `getOperations()` counts recursive calls or interior DP cells.
For searching, `getComparisons()` counts actual character comparisons, including
LPS preprocessing for KMP. `extra_cells` is theoretical **integer cells**
allocated by each DP implementation, not a heap measurement (for search,
12 denotes the KMP LPS array; Naive uses no LPS array). Recursive calls
use stack frames; `0` does **not** mean recursion uses no memory.

## Reproducibility

The random generator has fixed seed `20261108`. Input data and operation
counts repeat. Wall-clock times vary between machines/runs and are not
bit-for-bit reproducible. Plots use the median of five repeats per case.

## Git

Development branch: `main`; release tag: `v1.0`. The included local Git
history can be inspected with `git log --oneline` and `git tag`.

## AI-use acknowledgement

OpenAI ChatGPT was used to assist with implementation, testing, experiment
setup, chart creation and report drafting. Algorithms and measurements
should be reviewed and explained by the student before submission.
