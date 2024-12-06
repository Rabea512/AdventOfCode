package year2024.Day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day06/input_test.txt"));
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day06/input01.txt"));

        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day06/input_test.txt"));
        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day06/input02.txt"));
    }

    public static int part1(String path) throws IOException {
        final Input input = readInput(path);
        Direction currentDirection = Direction.NORTH;
        Coordinate currentCoordinate = input.start;
        Set<Coordinate> visited = new HashSet<>();
        visited.add(currentCoordinate);

        while (currentCoordinate.x() > 0 && currentCoordinate.x() < input.map().size() - 1 &&
               currentCoordinate.y() > 0 && currentCoordinate.y() < input.map().get(0).size() - 1) {
            // check if we can go straight
            if (isStraightFree(input.map(), currentDirection, currentCoordinate)) {
                currentCoordinate = getNextStep(currentDirection, currentCoordinate);
                visited.add(currentCoordinate);
            } else {
                currentDirection = switchDirection(currentDirection);
            }
        }

        return visited.size();
    }

    public static int part2(String path) throws IOException {
        final Input input = readInput(path);
        Set<Coordinate> obstructions = new HashSet<>();
        Map<Integer, Integer> coordinatesOfObstructionsX = new HashMap<>();
        Map<Integer, Integer> coordinatesOfObstructionsY = new HashMap<>();


        for (int i = 0; i < input.map().size(); i++) {
            for (int j = 0; j < input.map().get(i).size(); j++) {
                if (Boolean.FALSE.equals(input.map().get(i).get(j))) {
                    obstructions.add(new Coordinate(i, j));
                    coordinatesOfObstructionsX.put(i, j);
                    coordinatesOfObstructionsY.put(j, i);
                }
            }
        }

        Direction currentDirection = Direction.NORTH;
        Coordinate currentCoordinate = input.start;
        Map<Coordinate, List<Direction>> visited = new HashMap<>();
        visited.put(currentCoordinate, new ArrayList<>(List.of(currentDirection)));

        while (currentCoordinate.x() > 0 && currentCoordinate.x() < input.map().size() - 1 &&
               currentCoordinate.y() > 0 && currentCoordinate.y() < input.map().get(0).size() - 1) {
            // check if we can go straight
            if (isStraightFree(input.map(), currentDirection, currentCoordinate)) {
                currentCoordinate = getNextStep(currentDirection, currentCoordinate);
                if (visited.containsKey(currentCoordinate)) {
                    visited.get(currentCoordinate).add(currentDirection);
                } else {
                    visited.put(currentCoordinate, new ArrayList<>(List.of(currentDirection)));
                }
            } else {
                currentDirection = switchDirection(currentDirection);
            }
        }


        return 0;
    }

    private static Direction switchDirection(final Direction currentDirection) {
        return switch (currentDirection) {
            case NORTH -> Direction.EAST;
            case EAST -> Direction.SOUTH;
            case SOUTH -> Direction.WEST;
            case WEST -> Direction.NORTH;
        };
    }

    private static Coordinate getNextStep(final Direction currentDirection, final Coordinate currentCoordinate) {
        return switch (currentDirection) {
            case NORTH -> new Coordinate(currentCoordinate.x() - 1, currentCoordinate.y());
            case EAST -> new Coordinate(currentCoordinate.x(), currentCoordinate.y() + 1);
            case SOUTH -> new Coordinate(currentCoordinate.x() + 1, currentCoordinate.y());
            case WEST -> new Coordinate(currentCoordinate.x(), currentCoordinate.y() - 1);
        };
    }

    private static boolean isStraightFree(final List<List<Boolean>> map, final Direction currentDirection, final Coordinate currentCoordinate) {
        return switch (currentDirection) {
            case NORTH -> map.get(currentCoordinate.x() - 1).get(currentCoordinate.y());
            case EAST -> map.get(currentCoordinate.x()).get(currentCoordinate.y() + 1);
            case SOUTH -> map.get(currentCoordinate.x() + 1).get(currentCoordinate.y());
            case WEST -> map.get(currentCoordinate.x()).get(currentCoordinate.y() - 1);
        };
    }

    public static Input readInput(String path) throws IOException {
        List<List<Boolean>> map = new ArrayList<>();
        Coordinate start = new Coordinate(0, 0);

        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                // string to array of character
                List<Boolean> row = new ArrayList<>();
                char[] charArray = line.toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    final char c = charArray[i];
                    row.add(c != '#');
                    if (c == '^') {
                        start = new Coordinate(map.size(), i);
                    }
                }

                map.add(row);
                line = reader.readLine();
            }
        }
        return new Input(map, start);
    }

    public record Input(List<List<Boolean>> map, Coordinate start) {
    }

    public record Coordinate(int x, int y) {
    }

    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }
}
