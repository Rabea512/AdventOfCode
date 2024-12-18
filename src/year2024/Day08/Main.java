package year2024.Day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
  public static void main(String[] args) throws IOException {
    System.out.println(
        part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day08/input_test.txt"));
    System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day08/input01.txt"));

    System.out.println(
        part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day08/input_test.txt"));
    System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day08/input02.txt"));
  }

  public static int part1(String path) throws IOException {
    Input input = readInput(path);
    Set<Coordinate> antinodes = new HashSet<>();
    int maxX = input.map.size() - 1;
    int maxY = input.map.get(0).size() - 1;

    input.antennaMap.forEach(
        (antennaType, coordinates) -> {
          for (int i = 0; i < coordinates.size() - 1; i++) {
            Coordinate currentAntenna = coordinates.get(i);
            for (int j = i + 1; j < coordinates.size(); j++) {
              Coordinate otherAntenna = coordinates.get(j);
              int differenceX = otherAntenna.x() - currentAntenna.x();
              int differenceY = otherAntenna.y() - currentAntenna.y();
              Coordinate difference = new Coordinate(differenceX, differenceY);

              Coordinate antinode1 = otherAntenna.add(difference);
              Coordinate antinode2 = currentAntenna.subtract(difference);

              if (isInBounds(antinode1, maxX, maxY)) {
                antinodes.add(antinode1);
              }

              if (isInBounds(antinode2, maxX, maxY)) {
                antinodes.add(antinode2);
              }
            }
          }
        });

    return antinodes.size();
  }

  public static int part2(String path) throws IOException {
    Input input = readInput(path);
    Set<Coordinate> antinodes = new HashSet<>();
    int maxX = input.map.size() - 1;
    int maxY = input.map.get(0).size() - 1;

    input.antennaMap.forEach(
        (antennaType, coordinates) -> {
          for (int i = 0; i < coordinates.size() - 1; i++) {
            Coordinate currentAntenna = coordinates.get(i);
            antinodes.add(currentAntenna);
            for (int j = i + 1; j < coordinates.size(); j++) {
              Coordinate otherAntenna = coordinates.get(j);
              antinodes.add(otherAntenna);
              int differenceX = otherAntenna.x() - currentAntenna.x();
              int differenceY = otherAntenna.y() - currentAntenna.y();
              Coordinate difference = new Coordinate(differenceX, differenceY);

              Coordinate antinode1 = otherAntenna.add(difference);
              while (isInBounds(antinode1, maxX, maxY)) {
                antinodes.add(antinode1);
                antinode1 = antinode1.add(difference);
              }

              Coordinate antinode2 = currentAntenna.subtract(difference);
              while (isInBounds(antinode2, maxX, maxY)) {
                antinodes.add(antinode2);
                antinode2 = antinode2.subtract(difference);
              }
            }
          }
        });

    return antinodes.size();
  }

  public static Input readInput(String path) throws IOException {
    List<List<Character>> map = new ArrayList<>();
    List<Antenna> antennas = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line = reader.readLine();
      while (line != null && !line.isEmpty()) {
        List<Character> list = new ArrayList<>();
        for (char c : line.toCharArray()) {
          list.add(c);
          Pattern pattern = Pattern.compile("[A-Za-z0-9]");
          if (pattern.matcher(String.valueOf(c)).matches()) {
            antennas.add(new Antenna(c, new Coordinate(map.size(), list.size() - 1)));
          }
        }
        map.add(list);

        line = reader.readLine();
      }
    }

    Map<Character, List<Coordinate>> antennaMap = new HashMap<>();
    antennas.forEach(
        antenna -> {
          antennaMap.putIfAbsent(antenna.type(), new ArrayList<>());
          antennaMap.get(antenna.type()).add(antenna.coordinate());
        });

    return new Input(map, antennaMap);
  }

  private static boolean isInBounds(final Coordinate coordinate, final int maxX, final int maxY) {
    return coordinate.x() >= 0
        && coordinate.x() <= maxX
        && coordinate.y() >= 0
        && coordinate.y() <= maxY;
  }

  public record Input(List<List<Character>> map, Map<Character, List<Coordinate>> antennaMap) {}

  public record Coordinate(int x, int y) {
    public Coordinate add(Coordinate coordinate) {
      return new Coordinate(this.x() + coordinate.x(), this.y() + coordinate.y());
    }

    public Coordinate subtract(Coordinate coordinate) {
      return new Coordinate(this.x() - coordinate.x(), this.y() - coordinate.y());
    }
  }

  public record Antenna(char type, Coordinate coordinate) {}
}
