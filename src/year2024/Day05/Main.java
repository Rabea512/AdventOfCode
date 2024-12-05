package year2024.Day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day05/input_test.txt"));
        System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day05/input01.txt"));

        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day05/input_test.txt"));
        System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day05/input02.txt"));
    }

    public static int part1(String path) throws IOException {
        final Input input = readInput(path);
        final List<Integer> middlePages = new ArrayList<>();

        input.verifiedUpdates().correctOrderedPages().forEach(update -> middlePages.add(update.get((int) Math.floor(update.size() / 2.))));

        return middlePages.stream().mapToInt(Integer::intValue).sum();
    }

    public static int part2(String path) throws IOException {
        final Input input = readInput(path);
        final List<Integer> middlePages = new ArrayList<>();
        final List<List<Integer>> correctedUpdates = new ArrayList<>();

        input.verifiedUpdates().incorrectOrderedPages().forEach(update -> {
            final List<Integer> correctedUpdate = new ArrayList<>();
            correctedUpdate.add(update.get(0));
            for (int i = 1; i < update.size(); i++) {
                boolean sorted = false;
                for (int j = 0; j < correctedUpdate.size(); j++) {
                    final int currentSortedPage = correctedUpdate.get(j);
                    final int toBeSortedPage = update.get(i);
                    if (input.mustComeBefore().containsKey(toBeSortedPage) && input.mustComeBefore().get(toBeSortedPage).contains(currentSortedPage)) {
                        correctedUpdate.add(j, toBeSortedPage);
                        sorted = true;
                        break;
                    }
                }
                if (!sorted) {
                    correctedUpdate.add(update.get(i));
                }
            }
            correctedUpdates.add(correctedUpdate);
        });

        correctedUpdates.forEach(update -> middlePages.add(update.get((int) Math.floor(update.size() / 2.))));

        return middlePages.stream().mapToInt(Integer::intValue).sum();
    }

    private static VerifiedUpdates verifyUpdates(final Map<Integer, List<Integer>> mustComeBefore, final Map<Integer, List<Integer>> mustComeAfter, final List<List<Integer>> updates) {
        List<List<Integer>> correctOrderedPages = new ArrayList<>();
        List<List<Integer>> incorrectOrderedPages = new ArrayList<>();

        updates.forEach(update -> {
            boolean correctlyOrdered = isListCorrectlyOrdered(update, mustComeBefore, mustComeAfter, updates);


            if (correctlyOrdered) {
                correctOrderedPages.add(update);

            } else {
                incorrectOrderedPages.add(update);
            }
        });

        return new VerifiedUpdates(correctOrderedPages, incorrectOrderedPages);
    }

    private static boolean isListCorrectlyOrdered(final List<Integer> update, final Map<Integer, List<Integer>> mustComeBefore, final Map<Integer, List<Integer>> mustComeAfter, final List<List<Integer>> updates) {
        List<Integer> alreadyUpdated = new ArrayList<>();
        alreadyUpdated.add(update.get(0));
        boolean correctUpdate = true;
        for (int i = 1; i < update.size(); i++) {
            final Integer integer = update.get(i);
            // x must come before y, y is not allowed before x
            if (mustComeBefore.containsKey(integer)) {
                if (alreadyUpdated.stream().anyMatch(already -> mustComeBefore.get(integer).contains(already))) {
                    return false;
                }
            }
            // x must come after y, y is not allowed after x
            if (mustComeAfter.containsKey(integer)) {
                if (new HashSet<>(update.subList(i, update.size())).stream().anyMatch(next -> mustComeAfter.get(integer).contains(next))) {
                    return false;
                }
            }
            alreadyUpdated.add(integer);
        }

        return true;
    }

    public static Input readInput(String path) throws IOException {
        Map<Integer, List<Integer>> mustComeBefore = new HashMap<>();
        Map<Integer, List<Integer>> mustComeAfter = new HashMap<>();

        List<List<Integer>> updates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {

                final String[] split = line.split("\\|");
                final int before = Integer.parseInt(split[0]);
                final int after = Integer.parseInt(split[1]);
                if (mustComeBefore.containsKey(before)) {
                    mustComeBefore.get(before).add(after);
                } else {
                    final ArrayList<Integer> values = new ArrayList<>();
                    values.add(after);
                    mustComeBefore.put(before, values);
                }

                if (mustComeAfter.containsKey(after)) {
                    mustComeAfter.get(after).add(before);
                } else {
                    final ArrayList<Integer> values = new ArrayList<>();
                    values.add(before);
                    mustComeAfter.put(after, values);
                }

                line = reader.readLine();
            }

            line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                final String[] split = line.split(",");
                updates.add(Arrays.stream(split).map(Integer::parseInt).toList());

                line = reader.readLine();
            }
        }
        return new Input(mustComeBefore, mustComeAfter, updates, verifyUpdates(mustComeBefore, mustComeAfter, updates));
    }

    public record Input(Map<Integer, List<Integer>> mustComeBefore, Map<Integer, List<Integer>> mustComeAfter,
                        List<List<Integer>> updates, VerifiedUpdates verifiedUpdates) {
    }

    public record VerifiedUpdates(List<List<Integer>> correctOrderedPages, List<List<Integer>> incorrectOrderedPages) {
    }
}
