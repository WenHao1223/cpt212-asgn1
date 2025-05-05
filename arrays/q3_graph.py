import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Load CSV
df = pd.read_csv(r"d:\Documents\CPT212\cpt212-asgn1\arrays\radix_ops.csv")

# Lineplot: one line per k
plt.figure(figsize=(10, 6))
sns.lineplot(data=df, x="n", y="opCount", hue="k", marker="o")

plt.title("Primitive Operations vs n for Different Digit Lengths (k)")
plt.xlabel("Input Size (n)")
plt.ylabel("Primitive Operations")
plt.legend(title="Digit Length (k)")
plt.grid(True)
plt.tight_layout()
plt.show()
