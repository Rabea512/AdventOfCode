package Year_2016.Day_01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_01 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static List<Instruction> getSequence() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/Year_2016/year_2021.Day_01/input.txt"));

            List<Instruction> sequence = new ArrayList<Instruction>();

            String line = reader.readLine();

            final String[] split = line.split(", ");
            for (String instructionString : split) {
                sequence.add(new Instruction(instructionString.substring(0,1), Integer.parseInt(instructionString.substring(1))));
            }

            return sequence;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int part1() {
        List<Instruction> sequence = getSequence();

        Coordinate currentPosition = new Coordinate(0,0);
        Direction direction = new Direction();

        for (Instruction step : sequence) {
            if(step.direction().equals("R")) {
                direction.turnRight();
            } else {
                direction.turnLeft();
            }

            currentPosition = direction.go(currentPosition, step.steps(), new ArrayList<>());
        }

        return Math.abs(currentPosition.x()) + Math.abs(currentPosition.y());
    }

    private static int part2() {
        List<Instruction> sequence = getSequence();

        Coordinate currentPosition = new Coordinate(0,0);
        Direction direction = new Direction();

        Set<Coordinate> visitedPositions = new HashSet<>();
        visitedPositions.add(currentPosition);

        for (Instruction step : sequence) {
            if(step.direction().equals("R")) {
                direction.turnRight();
            } else {
                direction.turnLeft();
            }
            List<Coordinate> nowVisited = new ArrayList<>();
            currentPosition = direction.go(currentPosition, step.steps(), nowVisited);
            for (Coordinate coordinate : nowVisited) {
                final boolean added = visitedPositions.add(coordinate);
                if(!added) {
                    return Math.abs(coordinate.x()) + Math.abs(coordinate.y());
                }
            }
        }

        return Math.abs(currentPosition.x()) + Math.abs(currentPosition.y());
    }
}