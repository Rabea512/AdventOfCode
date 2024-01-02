package year_2023.Day_01;

import year_2023.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_1 implements Day {
  public static void main(String[] args) throws IOException {
    Day_1 day_1 = new Day_1();
    day_1.part1();
    day_1.part2();
  }

  @Override
  public void part1() throws IOException {
    BufferedReader reader =
        new BufferedReader(
            new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2023/year_2021.Day_01/input.txt"));

    int sum = 0;
    String line = reader.readLine();
    while (line != null) {

      // regex matcher to get the numbers
      Pattern pattern = Pattern.compile("[0-9]");
      Matcher matcher = pattern.matcher(line);

      List<String> numbers = new ArrayList<>();

      while (matcher.find()) {
        numbers.add(matcher.group());
      }

      sum += Integer.parseInt(numbers.get(0) + numbers.get(numbers.size() - 1));

      line = reader.readLine();
    }

    System.out.println(sum);
  }

  private String replaceNumberStrings(String line) {
    return line.replaceAll("one", "1")
        .replaceAll("two", "2")
        .replaceAll("three", "3")
        .replaceAll("four", "4")
        .replaceAll("five", "5")
        .replaceAll("six", "6")
        .replaceAll("seven", "7")
        .replaceAll("eight", "8")
        .replaceAll("nine", "9")
        .replaceAll("zero", "0");
  }

  @Override
  public void part2() throws IOException {
    BufferedReader reader =
        new BufferedReader(
            new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_01/input.txt"));

    int sum = 0;
    String line = reader.readLine();
    while (line != null) {
      // regex matcher to get the numbers
      Pattern pattern = Pattern.compile("\\d|one|two|three|four|five|six|seven|eight|nine");
      Matcher matcher = pattern.matcher(line);

      List<String> numbers = new ArrayList<>();

      int start = 0;

      while (matcher.find(start)) {
        numbers.add(replaceNumberStrings(matcher.group()));
        start = matcher.start() + 1;
      }

      sum += Integer.parseInt(numbers.get(0) + numbers.get(numbers.size() - 1));

      line = reader.readLine();
    }

    System.out.println(sum);
  }
}
