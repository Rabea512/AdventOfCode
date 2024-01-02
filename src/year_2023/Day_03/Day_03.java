package year_2023.Day_03;

import year_2023.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_03 implements Day {
  public static void main(String[] args) throws IOException {
    Day_03 day_03 = new Day_03();
    day_03.part1();
    day_03.part2();
  }

  @Override
  public void part1() throws IOException {
    Map<Coordinate, Integer> numbers = new HashMap<>();
    List<Symbol> symbols = new ArrayList<>();
    readInput(numbers, symbols);

    List<Integer> adjacentNumbers = new ArrayList<>();
    Set<Coordinate> takenCoordinates = new HashSet<>();

    symbols.forEach(
        symbol -> {
          // above
          if (!putAdjacentNumber(
              takenCoordinates,
              adjacentNumbers,
              numbers,
              new Coordinate(symbol.coordinate.x - 1, symbol.coordinate.y - 1))) {

            putAdjacentNumber(
                takenCoordinates,
                adjacentNumbers,
                numbers,
                new Coordinate(symbol.coordinate.x - 1, symbol.coordinate.y));
            putAdjacentNumber(
                takenCoordinates,
                adjacentNumbers,
                numbers,
                new Coordinate(symbol.coordinate.x - 1, symbol.coordinate.y + 1));
          }

          // same row
          putAdjacentNumber(
              takenCoordinates,
              adjacentNumbers,
              numbers,
              new Coordinate(symbol.coordinate.x, symbol.coordinate.y - 1));
          putAdjacentNumber(
              takenCoordinates,
              adjacentNumbers,
              numbers,
              new Coordinate(symbol.coordinate.x, symbol.coordinate.y + 1));

          // below
          if (!putAdjacentNumber(
              takenCoordinates,
              adjacentNumbers,
              numbers,
              new Coordinate(symbol.coordinate.x + 1, symbol.coordinate.y - 1))) {
            putAdjacentNumber(
                takenCoordinates,
                adjacentNumbers,
                numbers,
                new Coordinate(symbol.coordinate.x + 1, symbol.coordinate.y));
            putAdjacentNumber(
                takenCoordinates,
                adjacentNumbers,
                numbers,
                new Coordinate(symbol.coordinate.x + 1, symbol.coordinate.y + 1));
          }
        });

    System.out.println(adjacentNumbers.stream().reduce(0, Integer::sum));
  }

  private boolean putAdjacentNumber(
      final Set<Coordinate> takenCoordinates,
      final List<Integer> adjacentNumbers,
      final Map<Coordinate, Integer> numbers,
      final Coordinate coordinate) {
    if (takenCoordinates.contains(coordinate)) {
      return true;
    }
    Integer number = numbers.get(coordinate);

    if (number == null) {
      return false;
    }

    Coordinate anotherCoordinate = new Coordinate(coordinate.x, coordinate.y - 1);
    while (!anotherCoordinate.equals(new Coordinate(coordinate.x, coordinate.y))) {
      if (number.equals(numbers.get(anotherCoordinate))) {
        takenCoordinates.add(anotherCoordinate);
        anotherCoordinate = new Coordinate(anotherCoordinate.x, anotherCoordinate.y - 1);
      } else {
        anotherCoordinate = new Coordinate(coordinate.x, coordinate.y);
      }
    }
    while (anotherCoordinate != null) {
      if (number.equals(numbers.get(anotherCoordinate))) {
        takenCoordinates.add(anotherCoordinate);
        anotherCoordinate = new Coordinate(anotherCoordinate.x, anotherCoordinate.y + 1);
      } else {
        anotherCoordinate = null;
      }
    }

    adjacentNumbers.add(number);

    return true;
  }

  private static void readInput(final Map<Coordinate, Integer> numbers, final List<Symbol> symbols)
      throws IOException {

    BufferedReader reader =
        new BufferedReader(
            new FileReader(
                "/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_03/input.txt"));

    String line = reader.readLine();
    int i = 0;
    while (line != null) {
      StringBuilder currentNumber = new StringBuilder();

      for (int j = 0; j < line.length(); j++) {
        char c = line.charAt(j);

        if (Character.isDigit(c)) {
          currentNumber.append(c);
        } else {
          if (c != '.') {
            symbols.add(new Symbol(new Coordinate(i, j), c));
          }
          putNumber(currentNumber.toString(), numbers, i, j);
          currentNumber = new StringBuilder();
        }
      }

      line = reader.readLine();
      i++;
    }
  }

  private static void putNumber(
      final String currentNumber,
      final Map<Coordinate, Integer> numbers,
      final int i,
      final int j) {
    if (!currentNumber.isEmpty()) {
      int number = Integer.parseInt(currentNumber);
      for (int x = 0; x < currentNumber.length(); x++) {
        numbers.put(new Coordinate(i, j - x - 1), number);
      }
    }
  }

  @Override
  public void part2() throws IOException {}

  record Symbol(Coordinate coordinate, char symbol) {}

  record Coordinate(int x, int y) {}
}
