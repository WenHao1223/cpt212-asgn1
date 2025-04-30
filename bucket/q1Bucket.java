package bucket;
import java.util.*;

public class q1Bucket {
    public static void main(String[] args) {
        int[] initArr = {275, 87, 426, 61, 409, 170, 677, 503};
        int max_digits = Integer.toString(Arrays.stream(initArr).max().getAsInt()).length();

        for (int digit_place = 0; digit_place < max_digits; digit_place++) {
            List<List<Integer>> buckets = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                buckets.add(new ArrayList<>());
            }
            for (int number : initArr) {
                int digit = (number / (int) Math.pow(10, digit_place)) % 10;
                buckets.get(digit).add(number);
            }
            System.out.println(buckets);
            int index = 0;
            for (List<Integer> bucket : buckets) {
                for (int num : bucket) {
                    initArr[index++] = num;
                }
            }
        }

        System.out.println(Arrays.toString(initArr));
    }
}
