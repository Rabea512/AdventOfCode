package year2023.Day_02;

import year2023.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_02 implements Day {
  public static void main(String[] args) throws IOException {
    Day_02 day_02 = new Day_02();
    day_02.part1();
    day_02.part2();
  }

  @Override
  public void part1() throws IOException {
    int maxBlue = 14;
    int maxRed = 12;
    int maxGreen = 13;

    int possibleGameSum = 0;

    Map<Integer, List<Cubes>> games = readInput();

    for (final Map.Entry<Integer, List<Cubes>> integerListEntry : games.entrySet()) {
      boolean validGame = true;
      for (Cubes cubes : integerListEntry.getValue()) {
        if (cubes.blue() > maxBlue || cubes.red() > maxRed || cubes.green() > maxGreen) {
          validGame = false;
          break;
        }
      }

      if (validGame) {
        possibleGameSum += integerListEntry.getKey();
      }
    }

    System.out.println(possibleGameSum);
  }

  private static Map<Integer, List<Cubes>> readInput() throws IOException {
    Map<Integer, List<Cubes>> games = new HashMap<>();

    BufferedReader reader =
        new BufferedReader(
            new FileReader(
                "/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_02/input.txt"));

    String line = reader.readLine();
    while (line != null) {

      Pattern pattern = Pattern.compile("Game (\\d+):");
      Matcher matcher = pattern.matcher(line);

      if (matcher.find()) {
        int gameNumber = Integer.parseInt(matcher.group(1));

        String[] cubesArray = line.split(":")[1].split(";");
        games.put(gameNumber, new ArrayList<>());

        for (final String cubes : cubesArray) {

          int blue = 0;
          int red = 0;
          int green = 0;

          Pattern bluePattern = Pattern.compile("(\\d+) blue");
          Pattern redPattern = Pattern.compile("(\\d+) red");
          Pattern greenPattern = Pattern.compile("(\\d+) green");

          Matcher blueMatcher = bluePattern.matcher(cubes);
          Matcher redMatcher = redPattern.matcher(cubes);
          Matcher greenMatcher = greenPattern.matcher(cubes);

          if (blueMatcher.find()) {
            blue = Integer.parseInt(blueMatcher.group(1));
          }
          if (redMatcher.find()) {
            red = Integer.parseInt(redMatcher.group(1));
          }
          if (greenMatcher.find()) {
            green = Integer.parseInt(greenMatcher.group(1));
          }

          games.get(gameNumber).add(new Cubes(blue, red, green));
        }
      }

      line = reader.readLine();
    }

    return games;
  }

  @Override
  public void part2() throws IOException {
    int sum = 0;

    Map<Integer, List<Cubes>> games = readInput();

    for (final Map.Entry<Integer, List<Cubes>> integerListEntry : games.entrySet()) {
      int minBlue = 0;
      int minRed = 0;
      int minGreen = 0;

      for (Cubes cubes : integerListEntry.getValue()) {
        if (cubes.blue() > minBlue) {
          minBlue = cubes.blue();
        }
        if (cubes.red() > minRed) {
          minRed = cubes.red();
        }
        if (cubes.green() > minGreen) {
          minGreen = cubes.green();
        }
      }

      sum += minBlue * minRed * minGreen;
    }

    System.out.println(sum);
  }

  record Cubes(int blue, int red, int green) {}
  ;
}
