package year2021.Day_13;

import year2021.Day_02.Instruction;
import year2021.Day_05.Coordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_13 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static TransparentPaper getSystem() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_13/input.txt"));
            String line = reader.readLine();
            List<Coordinate> coordinates = new ArrayList<>();
            List<Instruction> instructions = new ArrayList<>();

            while(!line.equals("")) {
                final String[] split = line.split(",");
                coordinates.add(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1])));

                line = reader.readLine();
            }
            line = reader.readLine();
            while(line != null) {
                final String[] split = line.split("=");
                instructions.add(new Instruction(split[0].substring(split[0].length() - 1), Integer.parseInt(split[1])));

                line = reader.readLine();
            }

            return new TransparentPaper(coordinates, instructions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static int part2() {
        final TransparentPaper transparentPaper = getSystem();
        List<Coordinate> coordinates = transparentPaper.coordinates();

        for (int i = 0; i < transparentPaper.instructions().size(); i++) {
            coordinates = transform(transparentPaper.instructions().get(i), coordinates);
        }

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                Coordinate coordinate = new Coordinate(j, i);
                if(coordinates.contains(coordinate)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }

        return coordinates.size();
    }


    private static int part1() {
        final TransparentPaper transparentPaper = getSystem();

        return transform(transparentPaper.instructions().get(0), transparentPaper.coordinates()).size();
    }

    private static List<Coordinate> transform(Instruction instruction, List<Coordinate> coordinates) {
        List<Coordinate> ret = new ArrayList<>();

        if(instruction.direction().equals("x")) {
            int value = instruction.value();
            for (Coordinate coordinate : coordinates) {
                if (coordinate.x() < value) {
                    if(!ret.contains(coordinate)){
                        ret.add(coordinate);
                    }
                } else {
                    int newX = coordinate.x() - (coordinate.x() - value) * 2;
                    final Coordinate transformedCoordinate = new Coordinate(newX, coordinate.y());
                    if(!ret.contains(transformedCoordinate)) {
                        ret.add(transformedCoordinate);
                    }
                }
            }
        } else {
            int value = instruction.value();
            for (Coordinate coordinate : coordinates) {
                if (coordinate.y() < value) {
                    ret.add(coordinate);
                } else {
                    int newY = coordinate.y() - (coordinate.y() - value) * 2;
                    final Coordinate transformedCoordinate = new Coordinate(coordinate.x(), newY);
                    if(!ret.contains(transformedCoordinate)) {
                        ret.add(transformedCoordinate);
                    }
                }
            }
        }

        return ret;
    }
}
