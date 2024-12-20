package year2024.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    System.out.println(
        part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day10/input_test.txt"));
    System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day10/input01.txt"));

    System.out.println(
        part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day10/input_test.txt"));
    System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day10/input02.txt"));
  }

  public static int part1(String path) throws IOException {
    final Input input = readInput(path);



    return 0;
  }

  public static int part2(String path) throws IOException {
    return 0;
  }

  public static Input readInput(String path) throws IOException {
    final List<List<Integer>> map = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line = reader.readLine();
      while (line != null && !line.isEmpty()) {
        final List<Integer> row = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
          row.add(Integer.parseInt(line.substring(i, i + 1)));
        }
        map.add(row);
        line = reader.readLine();
      }
    }
    return new Input(map);
  }

  public record Input(List<List<Integer>> map) {}
}
