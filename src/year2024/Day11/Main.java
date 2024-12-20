package year2024.Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {
    System.out.println(
        part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day11/input_test.txt"));
    System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day11/input01.txt"));

    System.out.println(
        part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day11/input_test.txt"));

    System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day11/input02.txt"));
  }

  public static int part1(final String path) throws IOException {
    List<Stone> stones = readInput(path).stones();

    for (int i = 0; i < 25; i++) {
      stones = blink(stones);
    }

    return stones.size();
  }

  public static Long part2(final String path) throws IOException {
    final List<Stone> stones = readInput(path).stones();
    Map<Stone, Long> stoneMap = new HashMap<>();
    for (Stone stone1 : stones) {
      stoneMap.put(stone1, 1L);
    }

    for (int i = 0; i < 75; i++) {
      final Map<Stone, Long> newStoneMap = new HashMap<>();
      stoneMap.forEach(
          (stone, count) -> {
            List<Stone> blinkedStones = blinkStone(stone);
            blinkedStones.forEach(
                blinkedStone -> {
                  newStoneMap.putIfAbsent(blinkedStone, 0L);
                  newStoneMap.put(blinkedStone, newStoneMap.get(blinkedStone) + count);
                });
          });
      stoneMap = newStoneMap;
    }

    return stoneMap.values().stream().reduce(0L, Long::sum);
  }

  private static List<Stone> blink(final List<Stone> stones) {
    final List<Stone> newStones = new ArrayList<>();

    for (final Stone currentStone : stones) {
      newStones.addAll(blinkStone(currentStone));
    }

    return newStones;
  }

  private static List<Stone> blinkStone(final Stone stone) {
    if (stone.engraving() == 0) {
      return List.of(new Stone(1L));
    } else if (String.valueOf(stone.engraving()).length() % 2 == 0) {
      final String engraving = String.valueOf(stone.engraving());
      return List.of(
          new Stone(Long.parseLong(engraving.substring(0, engraving.length() / 2))),
          new Stone(Long.parseLong(engraving.substring(engraving.length() / 2))));
    } else {
      return List.of(new Stone(stone.engraving() * 2024));
    }
  }

  public static Input readInput(final String path) throws IOException {
    final List<Stone> stones;

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      final String line = reader.readLine();
      stones =
          new ArrayList<>(
              Arrays.stream(line.split(" ")).map(s -> new Stone(Long.parseLong(s))).toList());
    }
    return new Input(stones);
  }

  public record Input(List<Stone> stones) {}

  public record Stone(Long engraving) {}
}
