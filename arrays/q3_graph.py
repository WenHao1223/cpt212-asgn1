import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.cm as cm
import numpy as np

# Load and prepare data
df_digit = pd.read_csv("radix_digit_ops.csv").rename(columns={"k": "length"})
df_string = pd.read_csv("radix_string_ops.csv").rename(columns={"maxLength": "length"})
df_digit["type"] = "Digit"
df_string["type"] = "String"
df_all = pd.concat([df_digit, df_string], ignore_index=True)

# Setup color maps
digit_lengths = sorted(df_digit["length"].unique())
string_lengths = sorted(df_string["length"].unique())

digit_colors = cm.get_cmap("Greys", len(digit_lengths))(np.linspace(0.6, 1, len(digit_lengths)))  # darker shades
# Manually defined rich/dark colors for string sort
string_color_list = [
    "#1f77b4",  # dark blue
    "#ff7f0e",  # dark orange
    "#2ca02c",  # dark green
    "#d62728",  # dark red
    "#9467bd",  # dark purple
]

# ---------- 1. DIGIT SORT GRAPH ----------
plt.figure(figsize=(8, 5))
for i, length in enumerate(digit_lengths):
    group = df_digit[df_digit["length"] == length]
    plt.plot(group["n"], group["opCount"],
             label=f"Length {length}",
             marker="X", linestyle="--", color=digit_colors[i])
plt.title("Digit-based Radix Sort")
plt.xlabel("Input Size (n)")
plt.ylabel("Primitive Operations")
plt.legend(title="Digit Length")
plt.grid(True)
plt.tight_layout()
plt.show()

# ---------- 2. STRING SORT GRAPH ----------
plt.figure(figsize=(8, 5))
for i, length in enumerate(string_lengths):
    group = df_string[df_string["length"] == length]
    plt.plot(group["n"], group["opCount"],
             label=f"Length {length}",
             marker="+", linestyle="-", color=string_color_list[i])
plt.title("String-based Radix Sort")
plt.xlabel("Input Size (n)")
plt.ylabel("Primitive Operations")
plt.legend(title="String Length")
plt.grid(True)
plt.tight_layout()
plt.show()

# ---------- 3. COMBINED COMPARISON GRAPH ----------
plt.figure(figsize=(10, 6))
for i, length in enumerate(digit_lengths):
    group = df_digit[df_digit["length"] == length]
    plt.plot(group["n"], group["opCount"],
             label=f"Digit - Len {length}",
             marker="X", linestyle="--", color=digit_colors[i])

for i, length in enumerate(string_lengths):
    group = df_string[df_string["length"] == length]
    plt.plot(group["n"], group["opCount"],
             label=f"String - Len {length}",
             marker="+", linestyle="-", color=string_color_list[i])

plt.title("Combined Radix Sort: Digit vs String")
plt.xlabel("Input Size (n)")
plt.ylabel("Primitive Operations")
plt.legend(title="Type & Length")
plt.grid(True)
plt.tight_layout()
plt.show()
