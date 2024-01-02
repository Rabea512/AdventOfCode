package year_2021.Day_09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_09 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static List<List<Integer>> getInputAsListMatrix() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_09/input.txt"));
            String line = reader.readLine();
            List<List<Integer>> inputs = new ArrayList<>();
            while(line != null) {
                List<Integer> intLine = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    intLine.add(Integer.parseInt(line, i, i+1, 10));
                }
                inputs.add(intLine);
                line = reader.readLine();
            }
            return inputs;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static int part2() {
        final List<List<Integer>> inputs = getInputAsListMatrix();
        List<Integer> lowPoints = getLowPoints(inputs);

        List<Integer> basinSizes = getBasinSizes(inputs, lowPoints);
        return 0;
    }

    private static List<Integer> getBasinSizes(List<List<Integer>> inputs, List<Integer> lowPoints) {

        return null;
    }

    private static int part1() {
        final List<List<Integer>> inputs = getInputAsListMatrix();
        List<Integer> lowPoints = getLowPoints(inputs);

        return lowPoints.stream().map(value -> value +1).reduce(Integer::sum).orElse(0);
    }

    private static List<Integer> getLowPoints(List<List<Integer>> inputs) {
        List<Integer> lowPoints = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i++) {
            for (int j = 0; j < inputs.get(i).size(); j++) {
                if(i == 0) {
                    if(j == 0 ) {
                        int value = inputs.get(i).get(j);

                        if(value < inputs.get(i).get(j + 1) && value < inputs.get(i + 1).get(j)) {
                            lowPoints.add(value);
                        }
                    } else if (j == inputs.get(i).size() - 1) {
                        int value = inputs.get(i).get(j);

                        if(value < inputs.get(i).get(j - 1) && value < inputs.get(i + 1).get(j)) {
                            lowPoints.add(value);
                        }
                    } else {
                        int value = inputs.get(i).get(j);

                        if(value < inputs.get(i).get(j + 1) && value < inputs.get(i).get(j - 1) && value < inputs.get(i + 1).get(j)) {
                            lowPoints.add(value);
                        }
                    }
                } else if(i == inputs.size() - 1) {
                    if(j == 0 ) {
                        int value = inputs.get(i).get(j);

                        if(value < inputs.get(i).get(j + 1) && value < inputs.get(i - 1).get(j)) {
                            lowPoints.add(value);
                        }
                    } else if (j == inputs.get(i).size() - 1) {
                        int value = inputs.get(i).get(j);

                        if(value < inputs.get(i).get(j - 1) && value < inputs.get(i - 1).get(j)) {
                            lowPoints.add(value);
                        }
                    } else {
                        int value = inputs.get(i).get(j);

                        if(value < inputs.get(i).get(j + 1) && value < inputs.get(i).get(j - 1) && value < inputs.get(i - 1).get(j)) {
                            lowPoints.add(value);
                        }
                    }
                } else {
                    if(j == 0 ) {
                        int value = inputs.get(i).get(j);

                        if(value < inputs.get(i).get(j + 1) && value < inputs.get(i + 1).get(j) && value < inputs.get(i - 1).get(j)) {
                            lowPoints.add(value);
                        }
                    } else if (j == inputs.get(i).size() - 1) {
                        int value = inputs.get(i).get(j);

                        if(value < inputs.get(i).get(j - 1) && value < inputs.get(i + 1).get(j) && value < inputs.get(i - 1).get(j)) {
                            lowPoints.add(value);
                        }
                    } else {
                        int value = inputs.get(i).get(j);

                        if(value < inputs.get(i).get(j + 1) && value < inputs.get(i).get(j - 1) && value < inputs.get(i + 1).get(j) && value < inputs.get(i - 1).get(j)) {
                            lowPoints.add(value);
                        }
                    }
                }
            }
        }
        return lowPoints;
    }
}
