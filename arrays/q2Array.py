initArr = ["apple", "fig", "date", "banana", "cherry"]
max_length = len(max(initArr, key=len))  # Find longest word length

# Initialize the arrays
array1 = [[] for _ in range(26)]  # 26 buckets for a-z
array2 = [[] for _ in range(26)]  # 26 buckets for a-z

for pos in range(max_length - 1, -1, -1):  # Start from rightmost char
    print(f"Position: {pos}")
    if pos % 2 == 0:  # Even iterations use array1
        for word in initArr:
            # If word is shorter than current position, put in first bucket (a)
            index = ord(word[pos]) - ord('a') if pos < len(word) else 0
            array1[index].append(word)
        print("Array 1:", array1)
        # Collect words from array1 back to initArr
        initArr = [word for bucket in array1 for word in bucket]
        array1 = [[] for _ in range(26)]
    else:  # Odd iterations use array2
        for word in initArr:
            # If word is shorter than current position, put in first bucket (a)
            index = ord(word[pos]) - ord('a') if pos < len(word) else 0
            array2[index].append(word)
        print("Array 2:", array2)
        # Collect words from array2 back to initArr
        initArr = [word for bucket in array2 for word in bucket]
        array2 = [[] for _ in range(26)]

print("Sorted list:", initArr)