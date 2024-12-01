package year2024.Day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year_2024/Day_01/input_test.txt"));
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year_2024/Day_01/input01.txt"));

        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year_2024/Day_01/input_test.txt"));
        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year_2024/Day_01/input02.txt"));
    }

    public static int part1(String path) throws IOException {
        return 0;
    }

    public static int part2(String path) throws IOException {
        return 0;
    }

    public static Input readInput(String path) throws IOException {

        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                line = reader.readLine();
            }
        }
        return new Input();
    }

    public record Input(/* Add datastructure here */) {
    }
}
