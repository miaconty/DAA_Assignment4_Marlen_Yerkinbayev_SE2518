"""Rebuild all three PNG charts from measured results.csv."""
from pathlib import Path
import pandas as pd
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

root = Path(__file__).resolve().parent
rows = pd.read_csv(root / "results/results.csv")
out = root / "results/plots"
out.mkdir(parents=True, exist_ok=True)
med = (rows.groupby(["family", "algorithm", "input", "n"], as_index=False)
       .agg(time_ms=("time_ms", "median"),
            operations=("operations", "median"),
            extra_cells=("extra_cells", "median")))

fig, ax = plt.subplots(figsize=(7.8, 4.5))
for algorithm in ["Recursive", "Table", "TwoRows"]:
    d = med[(med.algorithm == algorithm) & (med.input == "different")]
    ax.plot(d.n, d.operations, marker="o", label=algorithm)
ax.set_yscale("log")
ax.set_xlabel("String length n (characters per string)")
ax.set_ylabel("Operations (calls or DP cells, log scale)")
ax.set_title("Edit Distance: operation growth on different strings")
ax.legend(); ax.grid(alpha=.25); fig.tight_layout()
fig.savefig(out / "01_edit_operations_log.png", dpi=180)
plt.close(fig)

fig, ax = plt.subplots(figsize=(7.8, 4.5))
for algorithm in ["Naive", "KMP"]:
    for kind in ["random", "repetitive"]:
        d = med[(med.algorithm == algorithm) & (med.input == kind)]
        ax.plot(d.n, d.operations, marker="o", label=f"{algorithm}, {kind}")
ax.set_xlabel("Text length n (characters)")
ax.set_ylabel("Character comparisons (including LPS for KMP)")
ax.set_title("String search: random and repetitive inputs")
ax.legend(); ax.grid(alpha=.25); fig.tight_layout()
fig.savefig(out / "02_search_comparisons.png", dpi=180)
plt.close(fig)

fig, ax = plt.subplots(figsize=(7.8, 4.5))
for algorithm in ["Table", "TwoRows"]:
    d = med[(med.algorithm == algorithm) & (med.input == "different")]
    ax.plot(d.n, d.extra_cells, marker="o", label=algorithm)
ax.set_xlabel("String length n (characters per string)")
ax.set_ylabel("Allocated DP integer cells (count)")
ax.set_title("Edit Distance: DP storage comparison")
ax.legend(); ax.grid(alpha=.25); fig.tight_layout()
fig.savefig(out / "03_dp_storage.png", dpi=180)
plt.close(fig)
print("Saved 3 PNG plots")
