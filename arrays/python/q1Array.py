initArr = [275, 87, 426, 61, 409, 170, 677, 503]
max_digits = len(str(max(initArr)))

# Initialize the arrays
array1 = [[] for _ in range(10)]
array2 = [[] for _ in range(10)]

for digit_place in range(max_digits):
    print(f"Digit place: {digit_place}")
    if digit_place % 2 == 0:  # Even iterations (0,2,4...) use array1
        for number in initArr:
            # Get the digit at the current place value
            digit = (number // (10 ** digit_place)) % 10
            array1[digit].append(number)
        print("Array 1:", array1)
        # Collect numbers from array1 back to initArr
        initArr = [num for bucket in array1 for num in bucket]
        array1 = [[] for _ in range(10)]
    else:  # Odd iterations (1,3,5...) use array2
        for number in initArr:
            # Get the digit at the current place value
            digit = (number // (10 ** digit_place)) % 10
            array2[digit].append(number)
        print("Array 2:", array2)
        # Collect numbers from array2 back to initArr
        initArr = [num for bucket in array2 for num in bucket]
        array2 = [[] for _ in range(10)]

print("Sorted list:", initArr)