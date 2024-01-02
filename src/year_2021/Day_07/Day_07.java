package year_2021.Day_07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_07 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static int[] getPositionsAsList() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_07/input.txt"));
            String line = reader.readLine();
            return Arrays.stream(line.trim().split(",")).mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static int part2() {
        int[] positions = getPositionsAsList();

        int min = Arrays.stream(positions).min().getAsInt();
        int max = Arrays.stream(positions).max().getAsInt();

        int minFuel = Integer.MAX_VALUE;

        for (int i = min; i <= max; i++) {
            int finalI = i;
            final int fuel = Arrays.stream(positions).reduce(0, (old, value) -> getFuel(finalI, value) + old);
            if(fuel < minFuel) {
                minFuel = fuel;
            }
        }
        return minFuel;
    }

    private static int getFuel(int minFuel, int value) {
        int sum = 0;
        for (int i = 1; i <= Math.abs(minFuel - value); i++) {
            sum += i;
        }
        return sum;
    }

    private static int part1() {
        int[] positions = getPositionsAsList();
        int median;
        Arrays.sort(positions);
        if(positions.length % 2 == 0) {
            median = (positions[positions.length / 2] + positions[positions.length / 2 - 1]) / 2;
        } else {
            median = positions[positions.length / 2];
        }

        int sum = 0;

        for (int position : positions) {
            sum += Math.abs(median - position);
        }

        return sum;
    }
}
