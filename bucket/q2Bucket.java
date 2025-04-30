package bucket;
import java.util.*;

public class q2Bucket {
    public static void main(String[] args) {
        String[] initArr = {"apple", "fig", "date", "banana", "cherry"};
        int max_digits = Arrays.stream(initArr).mapToInt(String::length).max().getAsInt();

        for (int digit_place = max_digits - 1; digit_place >= 0; digit_place--) {
            List<List<String>> buckets = new ArrayList<>();
            for (int i = 0; i < 26; i++) {
                buckets.add(new ArrayList<>());
            }
            for (String word : initArr) {
                char c = digit_place < word.length() ? word.charAt(digit_place) : 'a';
                buckets.get(c - 'a').add(word);
            }
            System.out.println(buckets);
            List<String> newArr = new ArrayList<>();
            for (List<String> bucket : buckets) {
                newArr.addAll(bucket);
            }
            initArr = newArr.toArray(new String[0]);
        }

        System.out.println(Arrays.toString(initArr));
    }
}
