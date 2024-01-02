package year_2021.Day_14;

import year_2021.Day_02.Instruction;
import year_2021.Day_05.Coordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day_14 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static PolymerSystem getSystem() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_14/input.txt"));

            List<InsertionRule> insertionRules = new ArrayList<InsertionRule>();

            String polymerTemplateString = reader.readLine();
            List<Character> polymerTemplate = new ArrayList<>();
            for (int i = 0; i < polymerTemplateString.length(); i++) {
                polymerTemplate.add(polymerTemplateString.charAt(i));
            }
            reader.readLine();
            String line = reader.readLine();
            while(line != null) {
                final String[] split = line.split(" -> ");
                insertionRules.add(new InsertionRule(split[0].charAt(0), split[0].charAt(1), split[1].charAt(0)));
                line = reader.readLine();
            }

            return new PolymerSystem(polymerTemplate, insertionRules);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static int part2() {
        return 0;
    }


    private static long part1() {
        final PolymerSystem system = getSystem();

        List<Character> polymerTemplate = system.polymerTemplate();
        for (int step = 0; step < 40; step++) {
            List<Character> newPolymerTemplate = new ArrayList<>();
            newPolymerTemplate.add(polymerTemplate.get(0));
            for (int i = 0; i < polymerTemplate.size() - 1; i++) {
                char a = polymerTemplate.get(i);
                char b = polymerTemplate.get(i + 1);
                char insertion = getInsertion(a, b, system.insertionRules());

                newPolymerTemplate.add(insertion);
                newPolymerTemplate.add(b);
            }
            polymerTemplate = newPolymerTemplate;
        }

        final HashSet<Character> distinctChars = new HashSet<>(polymerTemplate);
        long maxCount = 0;
        long leastCount = Integer.MAX_VALUE;
        for (char c :
                distinctChars) {
            final long count = polymerTemplate.stream().filter(ch -> ch == c).count();
            if(maxCount < count) {
                maxCount = count;
            }

            if(leastCount > count) {
                leastCount = count;
            }
        }

        return maxCount - leastCount;
    }

    private static char getInsertion(char a, char b, List<InsertionRule> rules) {
        for (InsertionRule rule :
                rules) {
            if (rule.a() == a && rule.b() == b) {
                return rule.insertion();
            }
        }
        return 0;
    }
}
