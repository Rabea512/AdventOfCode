package year2024.Day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year_2024/Day_01/input_test.txt"));
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year_2024/Day_01/input01.txt"));

        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year_2024/Day_01/input_test.txt"));
        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year_2024/Day_01/input02.txt"));
    }

    public static int part1(String path) throws IOException {
        final Input input = readInput(path);

        int result = 0;
        final List<Integer> leftSorted = input.left().stream().sorted().toList();
        final List<Integer> rightSorted = input.right().stream().sorted().toList();

        for (int i = 0; i < leftSorted.size(); i++) {
            result += Math.abs(leftSorted.get(i) - rightSorted.get(i));
        }

        return result;
    }

    public static int part2(String path) throws IOException {
        final Input input = readInput(path);

        final Set<Integer> distinctLeft = new HashSet<>(input.left());
        final Map<Integer, Integer> leftToCountRight = distinctLeft.stream().collect(Collectors.toMap(
                left -> left,
                left -> input.right().stream().filter(right -> right.equals(left)).toList().size()
        ));


        return input.left().stream().reduce(0, (acc, left) -> acc + left * leftToCountRight.get(left));
    }

    public static Input readInput(String path) throws IOException {
        List<Integer> left;
        List<Integer> right;
        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {

            left = new ArrayList<>();
            right = new ArrayList<>();

            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                final String[] leftRight = line.split(" {3}");
                left.add(Integer.parseInt(leftRight[0]));
                right.add(Integer.parseInt(leftRight[1]));

                line = reader.readLine();
            }
        }
        return new Input(left, right);
    }

    public record Input(List<Integer> left, List<Integer> right) {
    }
}
