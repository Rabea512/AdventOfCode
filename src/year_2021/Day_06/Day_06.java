package year_2021.Day_06;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day_06 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static List<Fish> getLinesAsList() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_06/input.txt"));
            String line = reader.readLine();
            return Arrays.stream(line.trim().split(",")).map(number -> new Fish(Integer.parseInt(number))).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<Fish>();
    }

    private static Map<Integer, Integer> getCleanMappedFish() {
        return new HashMap<>(Map.of(0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8, 0));
    }

    private static Map<Integer, BigInteger> getCleanMappedFishBig() {
        return new HashMap<Integer, BigInteger>(Map.of(0, new BigInteger("0"), 1, new BigInteger("0"), 2, new BigInteger("0"), 3, new BigInteger("0"), 4, new BigInteger("0"), 5, new BigInteger("0"), 6, new BigInteger("0"), 7, new BigInteger("0"), 8, new BigInteger("0")));
    }

    private static BigInteger part2() {
        List<Fish> initialFish = getLinesAsList();
        Map<Integer, BigInteger> mappedFish = getCleanMappedFishBig();

        Map<Integer, BigInteger> finalMappedFish = mappedFish;
        initialFish.forEach(fish -> {
            finalMappedFish.put(fish.getTimer(), finalMappedFish.get(fish.getTimer()).add(new BigInteger("1")));
        });

        mappedFish = finalMappedFish;

        for (int day = 0; day < 256; day++) {
            Map<Integer, BigInteger> newMappedFish = getCleanMappedFishBig();
            for (int i = 1; i <= 8; i++) {
                newMappedFish.put(i-1, mappedFish.get(i));
            }
            newMappedFish.put(8, mappedFish.get(0));

            newMappedFish.put(6, newMappedFish.get(6).add(mappedFish.get(0)));

            mappedFish = newMappedFish;
        }

        return mappedFish.values().stream().reduce(new BigInteger("0"), (old, value) -> old.add(value));
    }

    private static int part1() {
        List<Fish> initialFish = getLinesAsList();
        for (int day = 0; day < 80; day++) {
            List<Fish> newFish = new ArrayList<Fish>();
            initialFish.forEach(fish -> {
                if(fish.getTimer() == 0) {
                    newFish.add(new Fish());
                }
                fish.newDay();
            });
            initialFish = new ArrayList<Fish>(initialFish);
            initialFish.addAll(newFish);
        }

        return initialFish.size();
    }
}
