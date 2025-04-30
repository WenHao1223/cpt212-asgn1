initArr = ["apple", "fig", "date", "banana", "cherry"]

max_digits = len(str(max(initArr)))

for digit_place in range(max_digits):
    buckets = [[] for _ in range(26)]
    for word in initArr:
        digit = word[(10 ** digit_place) % 10]
        buckets[ord(digit) - 97].append(word)
    print(buckets)
    
    initArr = [word for bucket in buckets for word in bucket]

print(initArr)

