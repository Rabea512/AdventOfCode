package year_2021.Day_11;

import year_2021.Day_05.Coordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day_11 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static int[][] getOctopuses() {
        BufferedReader reader;
        int[][] octopuses = new int[10][10];

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_11/input.txt"));
            String line = reader.readLine();
            int i = 0;
            while(line != null) {
                final int[] lineInt = line.chars().map(c -> Character.getNumericValue(c)).toArray();
                octopuses[i] = lineInt;
                i++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return octopuses;
    }


    private static int part2() {
        return 0;
    }


    private static int part1() {
        int count = 0;
        int[][] octopuses = getOctopuses();
        int step = 0;
        List<Coordinate> flashes = new ArrayList<>();
        while (flashes.size() < octopuses.length * octopuses.length) {
            flashes = new ArrayList<>();
            final List<int[]> collect = Arrays.stream(octopuses).map(line -> Arrays.stream(line).map(o -> o + 1).toArray()).collect(Collectors.toList());
            int[][] newOctopuses = new int[10][10];
            for (int i = 0; i < collect.size(); i++) {
                newOctopuses[i] = collect.get(i);
            }
            octopuses = newOctopuses;
            for (int i = 0; i < octopuses.length; i++) {
                for (int j = 0; j < octopuses[i].length; j++) {
                    if(octopuses[i][j] > 9) {
                        flashes.addAll(flash(octopuses, i, j));
                    }
                }
            }
            count += flashes.size();
            for (int i = 0; i < flashes.size(); i++) {
                Coordinate flash = flashes.get(i);
                octopuses[flash.x()][flash.y()] = 0;
            }
            System.out.println(count);
            step++;
        }
        return step;
    }

    private static List<Coordinate> flash(int[][] octopuses, int i, int j) {
        List<Coordinate> flashes = new ArrayList<Coordinate>();
        flashes.add(new Coordinate(i, j));
        octopuses[i][j] = 0;
        if(i > 0) {
            octopuses[i - 1][j]++;
            if(octopuses[i - 1][j] > 9) {
                flashes.addAll(flash(octopuses, i - 1, j));
            }
        }
        if(j > 0) {
            octopuses[i][j - 1]++;
            if(octopuses[i][j - 1] > 9) {
                flashes.addAll(flash(octopuses, i, j - 1));
            }
        }
        if(i < octopuses.length - 1) {
            octopuses[i + 1][j]++;
            if(octopuses[i + 1][j] > 9) {
                flashes.addAll(flash(octopuses, i + 1, j));
            }
        }
        if(j < octopuses[i].length - 1) {
            octopuses[i][j + 1]++;
            if(octopuses[i][j + 1] > 9) {
                flashes.addAll(flash(octopuses, i, j + 1));
            }
        }
        if(j > 0 && i > 0) {
            octopuses[i - 1][j - 1]++;
            if(octopuses[i - 1][j - 1] > 9) {
                flashes.addAll(flash(octopuses, i - 1, j - 1));
            }
        }
        if(j < octopuses.length - 1 && i < octopuses.length - 1) {
            octopuses[i + 1][j + 1]++;
            if(octopuses[i + 1][j + 1] > 9) {
                flashes.addAll(flash(octopuses, i + 1, j + 1));
            }
        }
        if(j < octopuses.length - 1 && i > 0) {
            octopuses[i - 1][j + 1]++;
            if(octopuses[i - 1][j + 1] > 9) {
                flashes.addAll(flash(octopuses, i - 1, j + 1));
            }
        }
        if(j > 0 && i < octopuses.length - 1) {
            octopuses[i + 1][j - 1]++;
            if(octopuses[i + 1][j - 1] > 9) {
                flashes.addAll(flash(octopuses, i + 1, j - 1));
            }
        }
        return flashes;
    }
}
