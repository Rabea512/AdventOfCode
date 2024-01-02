package year_2021.Day_03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day_03 {
    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }

    private static int getInstructions() {
        BufferedReader reader;
        int ret = 0;
        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_02/input.txt"));
            String line = reader.readLine();
            Map<Integer, Integer> gammas = new HashMap<>();
            Map<Integer, Integer> epsilons = new HashMap<>();
            while (line != null) {
                final char[] chars = line.toCharArray();
                for (int i = 0; i < line.length(); i++) {
                    gammas.put(i, (chars[i] == '1' ? 1 : 0) + getValue(gammas, i));
                    epsilons.put(i, (chars[i] == '0' ? 1 : 0) + getValue(epsilons, i));
                }
                line = reader.readLine();
            }
            StringBuilder gammaBinary = new StringBuilder();
            StringBuilder epsilonBinary = new StringBuilder();
            for (int i = 0; i < gammas.size(); i++) {
                if(getValue(gammas, i) > getValue(epsilons, i)) {
                    gammaBinary.append("1");
                    epsilonBinary.append("0");
                } else {
                    gammaBinary.append("0");
                    epsilonBinary.append("1");
                }
            }

            ret = Integer.parseInt(gammaBinary.toString(), 2) * Integer.parseInt(epsilonBinary.toString(), 2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ret;
    }

    private static Integer getValue(Map<Integer, Integer> gammas, int i) {
        final Integer value = gammas.get(i);

        if(value == null) {
            return 0;
        }

        return value;
    }

    private static Map<Integer, Integer> getRatingValues(List<String> ratings, char c) {
        Map<Integer, Integer> gammas = new HashMap<>();
        for (String line:
             ratings) {
            final char[] chars = line.toCharArray();
            for (int i = 0; i < line.length(); i++) {
                gammas.put(i, (chars[i] == c ? 1 : 0) + getValue(gammas, i));
            }
        }
        return gammas;
    }

    private static int part2() {
        BufferedReader reader;
        int ret = 0;
        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_03/input.txt"));
            String line = reader.readLine();
            List<String> ratings = new ArrayList<>();
            while (line != null) {
                ratings.add(line);
                line = reader.readLine();
            }

            List<String> tempOxygen = ratings;
            List<String> tempC02 = ratings;
            for (int i = 0; i < ratings.get(0).length(); i++) {
                if(tempOxygen.size() > 1) {
                    tempOxygen = getRatings(i, tempOxygen, '1', '0');
                }
                if(tempC02.size() > 1) {
                    tempC02 = getRatings(i, tempC02, '0', '1');
                }
            }
            System.out.println(tempOxygen);
            System.out.println(tempC02);
            ret = Integer.parseInt(tempOxygen.get(0), 2) * Integer.parseInt(tempC02.get(0), 2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ret;
    }

    private static List<String> getRatings(int i, List<String> tempRatings, char c, char c1) {
        Map<Integer, Integer> gammas = getRatingValues(tempRatings, '1');
        Map<Integer, Integer> epsilons = getRatingValues(tempRatings, '0');
        int finalI = i;
        return tempRatings.stream().filter(rating -> gammas.get(finalI) >= epsilons.get(finalI) ? rating.charAt(finalI) == c : rating.charAt(finalI) == c1).collect(Collectors.toList());
    }

    private static int part1() {
        BufferedReader reader;
        int ret = 0;
        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_03/input.txt"));
            String line = reader.readLine();
            Map<Integer, Integer> gammas = new HashMap<>();
            Map<Integer, Integer> epsilons = new HashMap<>();
            while (line != null) {
                final char[] chars = line.toCharArray();
                for (int i = 0; i < line.length(); i++) {
                    gammas.put(i, (chars[i] == '1' ? 1 : 0) + getValue(gammas, i));
                    epsilons.put(i, (chars[i] == '0' ? 1 : 0) + getValue(epsilons, i));
                }
                line = reader.readLine();
            }
            StringBuilder gammaBinary = new StringBuilder();
            StringBuilder epsilonBinary = new StringBuilder();
            for (int i = 0; i < gammas.size(); i++) {
                if(gammas.get(i) > epsilons.get(i)) {
                    gammaBinary.append("1");
                    epsilonBinary.append("0");
                } else {
                    gammaBinary.append("0");
                    epsilonBinary.append("1");
                }
            }

            ret = Integer.parseInt(gammaBinary.toString(), 2) * Integer.parseInt(epsilonBinary.toString(), 2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ret;
    }
}
