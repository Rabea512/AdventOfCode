package year2024.Day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day03/inputTest.txt"));
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day03/input01.txt"));

        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day03/inputTest.txt"));
        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day03/input02.txt"));
    }

    public static int part1(String path) throws IOException {
        final Input input = readInput(path, false);

        int result = 0;

        for (Input.Mul mul : input.muls()) {
            result += mul.left() * mul.right();
        }

        return result;
    }

    public static int part2(String path) throws IOException {
        final Input input = readInput(path, true);

        int result = 0;

        for (Input.Mul mul : input.muls()) {
            result += mul.left() * mul.right();
        }

        return result;
    }

    public static Input readInput(String path, boolean withDos) throws IOException {
        List<Input.Mul> muls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {
            String line = reader.readLine();
            boolean enabled = true;
            while (line != null && !line.isEmpty()) {
                Pattern pattern =
                        Pattern.compile(
                                "mul\\((?<left>\\d+),(?<right>\\d+)\\)|(?<do>do\\(\\))|(?<dont>don't\\(\\))");
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    final boolean doMatch = matcher.group("do") != null;
                    final boolean dontMatch = matcher.group("dont") != null;
                    final Integer parsedLeft = matcher.group("left") != null ? Integer.parseInt(matcher.group("left")) : null;
                    final Integer parsedRight = matcher.group("right") != null ? Integer.parseInt(matcher.group("right")) : null;

                    if (doMatch) {
                        enabled = true;
                    }
                    if (dontMatch) {
                        enabled = false;
                    }

                    if ((!withDos || enabled) && parsedLeft != null && parsedRight != null) {

                        muls.add(new Input.Mul(parsedLeft, parsedRight));
                    }

                }

                line = reader.readLine();
            }
            return new Input(muls);
        }
    }

    public record Input(List<Mul> muls) {
        public record Mul(int left, int right) {
        }
    }
}
