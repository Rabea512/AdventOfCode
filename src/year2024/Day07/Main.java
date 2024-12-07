package year2024.Day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  public static void main(String[] args) throws IOException {
    System.out.println(
        part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day07/input_test.txt"));
    System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day07/input01.txt"));

    System.out.println(
        part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day07/input_test.txt"));
    System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day07/input02.txt"));
  }

  public static Long part1(String path) throws IOException {
    final Input input = readInput(path);
    long result = 0L;

    for (Equation equation : input.equations()) {
      if (isValid(equation, false)) {
        result += equation.result();
      }
    }

    return result;
  }

  public static long part2(String path) throws IOException {
    final Input input = readInput(path);
    long result = 0L;

    for (Equation equation : input.equations()) {
      if (isValid(equation, true)) {
        result += equation.result();
      }
    }

    return result;
  }

  private static boolean isValid(final Equation equation, final boolean concatenation) {
    return isValidRecursive(
        equation.result(),
        equation.numbers().subList(1, equation.numbers().size()),
        List.of(equation.numbers().getFirst()),
        concatenation);
  }

  private static boolean isValidRecursive(
      final long result,
      final List<Long> numbers,
      final List<Long> bucket,
      final boolean concatenation) {
    if (numbers.isEmpty()) {
      return bucket.stream().anyMatch(number -> result == number);
    }

    List<Long> newBucket = new ArrayList<>();

    for (Long aLong : bucket) {
      newBucket.add(aLong + numbers.getFirst());
      newBucket.add(aLong * numbers.getFirst());
      if (concatenation) {
        newBucket.add(Long.parseLong(aLong + numbers.getFirst().toString()));
      }
    }

    return isValidRecursive(result, numbers.subList(1, numbers.size()), newBucket, concatenation);
  }

  public static Input readInput(String path) throws IOException {
    List<Equation> equations = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line = reader.readLine();
      while (line != null && !line.isEmpty()) {
        Pattern pattern = Pattern.compile("(?<result>\\d+):(?<numbers>( \\d+,?)+)");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
          long result = Long.parseLong(matcher.group("result"));
          final List<Long> numbers =
              Arrays.stream(matcher.group("numbers").trim().split(" "))
                  .map(Long::parseLong)
                  .toList();

          equations.add(new Equation(result, numbers));
        }

        line = reader.readLine();
      }
    }
    return new Input(equations);
  }

  public record Input(List<Equation> equations) {}

  public record Equation(long result, List<Long> numbers) {}
}
