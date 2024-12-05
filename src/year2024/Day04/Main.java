package year2024.Day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day04/input_test.txt"));
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day04/input01.txt"));

        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day04/input_test.txt"));
        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day04/input02.txt"));
    }

    public static int part1(String path) throws IOException {
        Input input = readInput(path);


        return getCountOfXmas(input.horizontal()) + getCountOfXmas(input.vertical()) + getCountOfXmas(input.diagonal1()) + getCountOfXmas(input.diagonal2());
    }

    private static int getCountOfXmas(final List<String> input) {
        int counter = 0;
        for (String line : input) {
            Pattern pattern = Pattern.compile("(?=(XMAS|SAMX))");
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                counter++;
            }
        }
        return counter;
    }

    public static int part2(String path) throws IOException {
        Input input = readInput(path);

        List<List<Character>> grid = input.grid();
        int counter = 0;

        for (int i = 1; i < grid.size() - 1; i++) {
            for (int j = 1; j < grid.get(i).size() - 1; j++) {
                if (grid.get(i).get(j).equals('A')) {
                    if (grid.get(i - 1).get(j - 1).equals('M') && grid.get(i + 1).get(j + 1).equals('S') || grid.get(i - 1).get(j - 1).equals('S') && grid.get(i + 1).get(j + 1).equals('M')) {
                        if (grid.get(i - 1).get(j + 1).equals('M') && grid.get(i + 1).get(j - 1).equals('S') || grid.get(i - 1).get(j + 1).equals('S') && grid.get(i + 1).get(j - 1).equals('M')) {
                            counter++;
                        }
                    }
                }
            }
        }

        return counter;
    }

    public static Input readInput(String path) throws IOException {
        List<String> horizontal = new ArrayList<>();
        List<String> vertical = new ArrayList<>();
        List<String> diagonal1 = new ArrayList<>();
        List<String> diagonal2 = new ArrayList<>();
        List<List<Character>> grid = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {
            String line = reader.readLine();
            int i = 0;
            while (line != null && !line.isEmpty()) {
                horizontal.add(line);
                grid.add(new ArrayList<>());
                grid.get(i).addAll(line.chars().mapToObj(c -> (char) c).toList());
                i++;
                line = reader.readLine();
            }
        }
        for (int i = 0; i < grid.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < grid.get(i).size(); j++) {
                sb.append(grid.get(j).get(i));
            }
            vertical.add(sb.toString());
        }

        for (int i = 0; i < grid.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                sb.append(grid.get(i - j).get(j));
            }
            diagonal1.add(sb.toString());
        }

        for (int i = 1; i < grid.get(0).size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(grid.get(grid.size() - 1 - j).get(grid.get(0).size() - i + j));
            }
            diagonal1.add(sb.toString());
        }

        for (int i = 0; i < grid.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = grid.get(i).size() - 1; j >= i; j--) {
                sb.append(grid.get(j - i).get(j));
            }
            diagonal2.add(sb.toString());
        }

        for (int i = 0; i < grid.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                sb.append(grid.get(grid.size() - 1 - i + j).get(j));
            }
            diagonal2.add(sb.toString());
        }

        return new Input(grid, horizontal, vertical, diagonal1, diagonal2);
    }

    public record Input(List<List<Character>> grid, List<String> horizontal, List<String> vertical,
                        List<String> diagonal1,
                        List<String> diagonal2) {
    }
}
