package year2024.Day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day02/input_test.txt"));
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day02/input01.txt"));

        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day02/input_test.txt"));
        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day02/input02.txt"));
    }

    public static int part1(String path) throws IOException {
        Input input = readInput(path);
        final AtomicInteger result = new AtomicInteger();

        input.lists().forEach(list -> {
            if (isListValid(list)) {
                result.addAndGet(1);
            }
        });
        return result.get();
    }

    private static boolean isListValid(final List<Integer> list) {
        boolean increasing = list.get(0) < list.get(1);

        for (int i = 1; i < list.size(); i++) {
            int difference = list.get(i) - list.get(i - 1);

            if (increasing && difference <= 0 || !increasing && difference >= 0) {
                return false;
            }

            if (Math.abs(difference) > 3) {
                return false;
            }
        }

        return true;
    }

    public static int part2(String path) throws IOException {
        Input input = readInput(path);
        int result = 0;

        for (List<Integer> list : input.lists()) {
            final List<Integer> variant1 = list.subList(1, list.size());
            final List<Integer> variant2 = new ArrayList<>();

            variant2.addAll(list.subList(0, 1));
            variant2.addAll(list.subList(2, list.size()));

            if (isListValid2(list) || isListValid(variant1) || isListValid(variant2)) {
                result++;
            }
        }
        return result;
    }

    private static boolean isListValid2(final List<Integer> list) {
        boolean increasing = list.get(0) < list.get(1);
        boolean hasOneWrong = false;

        for (int i = 1; i < list.size(); i++) {
            int difference = list.get(i) - list.get(i - 1);

            if (increasing && difference <= 0 || !increasing && difference >= 0 || Math.abs(difference) > 3) {
                if (!hasOneWrong) {
                    final List<Integer> variant1 = new ArrayList<>();
                    final List<Integer> variant2 = new ArrayList<>();

                    variant1.addAll(list.subList(0, i - 1));
                    variant1.addAll(list.subList(i, list.size()));

                    variant2.addAll(list.subList(0, i));
                    variant2.addAll(list.subList(i + 1, list.size()));

                    return isListValid(variant1) || isListValid(variant2);
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public static Input readInput(String path) throws IOException {
        List<List<Integer>> lists = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {
            String line = reader.readLine();


            while (line != null && !line.isEmpty()) {
                final List<Integer> list = Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList();
                lists.add(list);

                line = reader.readLine();
            }
        }
        return new Input(lists);
    }

    public record Input(List<List<Integer>> lists) {
    }
}
