# initArr = [275, 87, 426, 61, 409, 170, 677, 503, 5, 1234]
initArr = [275, 87, 426, 61, 409, 170, 677, 503]
max_digits = len(str(max(initArr)))

for digit_place in range(max_digits):
    buckets = [[] for _ in range(10)]
    for number in initArr:
        digit = (number // (10 ** digit_place)) % 10
        buckets[digit].append(number)
    print(buckets)
    
    initArr = [num for bucket in buckets for num in bucket]

print(initArr)

