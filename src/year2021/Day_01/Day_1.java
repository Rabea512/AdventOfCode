package year2021.Day_01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_1 {
    public static void main(String[] args) {
        int sum = part1();
        System.out.println(sum);
        
        System.out.println(part2());
    }

    private static List<Integer> getMeasurementsAsList() {
        BufferedReader reader;
        List<Integer> measurements = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_01/input.txt"));
            String line = reader.readLine();
            while (line != null) {
                measurements.add(Integer.parseInt(line));
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return measurements;
    }

    private static int part2() {
        List<Integer> measurements = getMeasurementsAsList();
        int sum = 0;
        int lastMeasurement = Integer.MAX_VALUE;
        for (int i = 1; i < measurements.size() -1; i++) {
            int value = measurements.get(i-1) + measurements.get(i) + measurements.get(i+1);
            if(value > lastMeasurement) {
                sum++;
            }
            lastMeasurement = value;
        }

        return sum;
    }

    private static int part1() {
        List<Integer> measurements = getMeasurementsAsList();
        int lastMeasurement = Integer.MAX_VALUE;
        int sum = 0;
        for (int measurement :
                measurements) {
            if(measurement > lastMeasurement) {
                sum++;
            }
            lastMeasurement = measurement;
        }
        return sum;
    }
}
