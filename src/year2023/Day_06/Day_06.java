package year2023.Day_06;

import year2023.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_06 implements Day {
  public static void main(String[] args) throws IOException {
    Day_06 day_06 = new Day_06();
    // day_06.part1();
    day_06.part2();
  }

  @Override
  public void part1() throws IOException {
    BufferedReader reader =
        new BufferedReader(
            new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_06/input.txt"));

    String timeLine = reader.readLine();
    String distanceLine = reader.readLine();

    Pattern timePattern = Pattern.compile("Time: (?<time>[\\s*\\d+]*)");
    Pattern distancePattern = Pattern.compile("Distance: (?<distance>[\\s*\\d+]*)");

    Matcher timeMatcher = timePattern.matcher(timeLine);
    Matcher distanceMatcher = distancePattern.matcher(distanceLine);

    if (timeMatcher.find() && distanceMatcher.find()) {
      final List<Integer> times =
          Arrays.stream(timeMatcher.group("time").split(" "))
              .filter(s -> !s.isEmpty())
              .map(Integer::parseInt)
              .toList();
      final List<Integer> distances =
          Arrays.stream(distanceMatcher.group("distance").split(" "))
              .filter(s -> !s.isEmpty())
              .map(Integer::parseInt)
              .toList();

      int result = 1;

      for (int i = 0; i < times.size(); i++) {
        int time = times.get(i);
        int distance = distances.get(i);
        int count = 0;
        for (int secondsHolding = 1; secondsHolding < time; secondsHolding++) {
          int checkDistance = (time - secondsHolding) * secondsHolding;
          if (checkDistance > distance) {
            count++;
          }
        }
        result *= count;
      }
      System.out.println("result = " + result);
    }
    reader.close();
  }

  @Override
  public void part2() throws IOException {
    BufferedReader reader =
        new BufferedReader(
            new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_06/input.txt"));

    String timeLine = reader.readLine();
    String distanceLine = reader.readLine();

    Pattern timePattern = Pattern.compile("Time: (?<time>[\\s*\\d+]*)");
    Pattern distancePattern = Pattern.compile("Distance: (?<distance>[\\s*\\d+]*)");

    Matcher timeMatcher = timePattern.matcher(timeLine);
    Matcher distanceMatcher = distancePattern.matcher(distanceLine);

    if (timeMatcher.find() && distanceMatcher.find()) {
      final long time = Long.parseLong(timeMatcher.group("time").replaceAll(" ", ""));
      final long distance = Long.parseLong(distanceMatcher.group("distance").replaceAll(" ", ""));

      long checkDistanceLowerEnd = 0;
      long secondsHoldingLowerEnd = 0;

      while (checkDistanceLowerEnd < distance) {
        secondsHoldingLowerEnd++;
        checkDistanceLowerEnd = (time - secondsHoldingLowerEnd) * secondsHoldingLowerEnd;
      }

      long checkDistanceUpperEnd = 0;
      long secondsHoldingUpperEnd = time;

      while (checkDistanceUpperEnd < distance) {
        secondsHoldingUpperEnd--;
        checkDistanceUpperEnd = (time - secondsHoldingUpperEnd) * secondsHoldingUpperEnd;
      }

      System.out.println("result = " + (secondsHoldingUpperEnd - secondsHoldingLowerEnd + 1));
    }
  }
}
