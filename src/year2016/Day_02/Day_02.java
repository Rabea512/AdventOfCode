package year2016.Day_02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day_02 {

    private static Map<String, Directions> keypad1 = Map.of("1", new Directions("1", "2", "4", "1"),
            "2", new Directions("2", "3", "5", "1"),
            "3", new Directions("3", "3", "6", "2"),
            "4", new Directions("1", "5", "7", "4"),
            "5", new Directions("2", "6", "8", "4"),
            "6", new Directions("3", "6", "9", "5"),
            "7", new Directions("4", "8", "7", "7"),
            "8", new Directions("5", "9", "8", "7"),
            "9", new Directions("6", "9", "9", "8"));

    private static Map<String, Directions> keypad2 = new HashMap<>();

    static {
        keypad2.putAll(Map.of("1", new Directions("1", "1", "3", "1"),
                "2", new Directions("2", "3", "6", "2"),
                "3", new Directions("1", "4", "7", "2"),
                "4", new Directions("4", "4", "8", "3"),
                "5", new Directions("5", "6", "5", "5"),
                "6", new Directions("2", "7", "A", "5"),
                "7", new Directions("3", "8", "B", "6"),
                "8", new Directions("4", "9", "C", "7"),
                "9", new Directions("9", "9", "9", "8")));
        keypad2.putAll(Map.of(
                "A", new Directions("6", "B", "A", "A"),
                "B", new Directions("7", "C", "D", "A"),
                "C", new Directions("8", "C", "C", "B"),
                "D", new Directions("B", "D", "D", "D")));
    }

    public static void main(String[] args) {
        System.out.println(part1(keypad1));

        System.out.println(part1(keypad2));
    }

    private static List<String> getSequence() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/Year_2016/year_2021.Day_02/input.txt"));

            List<String> sequence = new ArrayList<String>();

            String line = reader.readLine();

            while(line != null) {
                sequence.add(line);
                line = reader.readLine();
            }
            return sequence;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String part1(Map<String, Directions> keypad) {
        List<String> instructions = getSequence();
        String currentNumber = "5";
        StringBuilder pin = new StringBuilder();

        for (String instruction : instructions) {

            for (int i = 0; i < instruction.length(); i++) {
                currentNumber = go(String.valueOf(instruction.charAt(i)), currentNumber, keypad);
            }

            pin.append(currentNumber);
        }

        return pin.toString();
    }

    private static String go(String direction, String currentNumber, Map<String, Directions> keypad) {
        return switch (direction) {
            case "U" -> keypad.get(currentNumber).up();
            case "R" -> keypad.get(currentNumber).right();
            case "D" -> keypad.get(currentNumber).down();
            case "L" -> keypad.get(currentNumber).left();
            default -> currentNumber;
        };
    }

    private static int part2() {
        return 0;
    }
}
