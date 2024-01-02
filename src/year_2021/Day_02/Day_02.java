package year_2021.Day_02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_02 {
    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }

    private static List<Instruction> getInstructions() {
        BufferedReader reader;
        List<Instruction> instructions = new ArrayList<Instruction>();
        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_02/input.txt"));
            String line = reader.readLine();
            while (line != null) {
                final String[] split = line.split(" ");
                instructions.add(
                        new Instruction(split[0], Integer.parseInt(split[1]))
                );

                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return instructions;
    }

    private static int part2() {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;
        final List<Instruction> instructions = getInstructions();

        for (Instruction instruction :
                instructions) {
            switch (instruction.direction()) {
                case "forward":
                    horizontalPosition += instruction.value();
                    depth += aim * instruction.value();
                    break;
                case "down":
                    aim += instruction.value();
                    break;
                case "up":
                    aim -= instruction.value();
                    break;
            }
        }
        return depth * horizontalPosition;
    }

    private static int part1() {
        int horizontalPosition = 0;
        int depth = 0;
        final List<Instruction> instructions = getInstructions();

        for (Instruction instruction :
                instructions) {
            switch (instruction.direction()) {
                case "forward":
                    horizontalPosition += instruction.value();
                    break;
                case "down":
                    depth += instruction.value();
                    break;
                case "up":
                    depth -= instruction.value();
                    break;
            }
        }
        return depth * horizontalPosition;
    }
}
