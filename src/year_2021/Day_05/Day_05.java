package year_2021.Day_05;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day_05 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static List<Line> getLinesAsList() {
        BufferedReader reader;
        List<Line> lines = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_05/input.txt"));
            String line = reader.readLine();
            while (line != null) {
                final String[] split = line.trim().split("->");
                String[] start = split[0].trim().split(",");
                String[] end = split[1].trim().split(",");
                Line ventLine  = new Line(new Coordinate(Integer.parseInt(start[0]), Integer.parseInt(start[1])), new Coordinate(Integer.parseInt(end[0]), Integer.parseInt(end[1])));
                lines.add(ventLine);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private static int part2() {
        List<Line> lines = getLinesAsList();
        Map<Coordinate, Integer> coordinatesWithLine = new HashMap();

        lines.forEach(line -> {
            if(line.start().x() == line.end().x() || line.start().y() == line.end().y()) {
                int x_start = Math.min(line.start().x(), line.end().x());
                int x_end = Math.max(line.start().x(), line.end().x());
                int y_start = Math.min(line.start().y(), line.end().y());
                int y_end = Math.max(line.start().y(), line.end().y());
                for (int i = x_start; i <= x_end; i++) {
                    for (int j = y_start; j <= y_end; j++) {
                        Coordinate coordinate = new Coordinate(i, j);
                        int value = coordinatesWithLine.getOrDefault(coordinate, 0) + 1;

                        coordinatesWithLine.put(coordinate, value);
                    }
                }
            } else {
                int x_start = line.start().x();
                int x_end = line.end().x();
                int y_start = line.start().y();
                int y_end = line.end().y();
                for (int i = 0; i <= Math.abs(x_end - x_start); i++) {
                    Coordinate coordinate = new Coordinate(x_start < x_end ? x_start + i : x_start - i, y_start < y_end ? y_start + i: y_start - i);
                    int value = coordinatesWithLine.getOrDefault(coordinate, 0) + 1;
                    coordinatesWithLine.put(coordinate, value);
                }
            }
        });

        return (int) coordinatesWithLine.entrySet().stream().filter(coordinate -> coordinate.getValue() >= 2).count();
    }

    private static int part1() {
        List<Line> lines = getLinesAsList();
        Map<Coordinate, Integer> coordinatesWithLine = new HashMap();

        final List<Line> filteredLines = lines.stream().filter(line -> line.start().x() == line.end().x() || line.start().y() == line.end().y()).collect(Collectors.toList());

        filteredLines.forEach(line -> {
            int x_start = Math.min(line.start().x(), line.end().x());
            int x_end = Math.max(line.start().x(), line.end().x());
            int y_start = Math.min(line.start().y(), line.end().y());
            int y_end = Math.max(line.start().y(), line.end().y());
            for (int i = x_start; i <= x_end; i++) {
                for (int j = y_start; j <= y_end; j++) {
                    Coordinate coordinate = new Coordinate(i, j);
                    int value = coordinatesWithLine.getOrDefault(coordinate, 0) + 1;

                    coordinatesWithLine.put(coordinate, value);
                }
            }
        });

        return (int) coordinatesWithLine.entrySet().stream().filter(coordinate -> coordinate.getValue() >= 2).count();
    }
}
