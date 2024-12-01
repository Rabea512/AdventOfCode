package year2021.Day_10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day_10 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static Input getInput() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_10/input.txt"));
            String line = reader.readLine();
            List<Character> corrupts = new ArrayList<>();
            Map<String, Stack<Character>> incompleteLines = new HashMap<>();
            while(line != null) {
                Stack<Character> charStack = new Stack<>();
                final long count = line.chars().takeWhile(cInt -> testChar(charStack, corrupts, (char) cInt)).count();

                if(count == line.length()) {
                    incompleteLines.put(line, charStack);
                }
                line = reader.readLine();
            }
            return new Input(incompleteLines, corrupts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int calculateResultPart1(List<Character> corrupts) {
        return corrupts.stream().map(c -> switch (c) {
            case ')' -> 3;
            case ']' -> 57;
            case '}' -> 1197;
            case '>' -> 25137;
            default -> 0;
        }).reduce(0, Integer::sum);
    }

    private static boolean testChar(Stack<Character> charStack, List<Character> corrupts, char c) {
        if(isOpening(c)) {
            charStack.push(c);
            return true;
        }
        if(charStack.peek().equals(getOpeningChar(c))) {
            charStack.pop();
            return true;
        } else {
            corrupts.add(c);
            return false;
        }
    }

    private static Character getOpeningChar(char c) {
        return switch (c) {
            case ')' -> '(';
            case ']' -> '[';
            case '}' -> '{';
            case '>' -> '<';
            default -> null;
        };
    }

    private static Character getClosingChar(Character c) {
        return switch (c) {
            case '(' -> ')';
            case '[' -> ']';
            case '{' -> '}';
            case '<' -> '>';
            default -> null;
        };
    }

    private static boolean isOpening(Character peek) {
        return peek.equals('(') || peek.equals('{') || peek.equals('[') || peek.equals('<');
    }


    private static BigInteger part2() {
        final Input input = getInput();

        assert input != null;
        final List<BigInteger> scores = input.incompleteLines().entrySet().stream().map(line -> getMissingCharacters(line)).peek(System.out::println).map(cLine -> cLine.stream().map(Day_10::getValue).reduce(new BigInteger("0"), (oldScore, v) -> oldScore.multiply(new BigInteger("5")).add(v))).sorted().collect(Collectors.toList());

        return scores.get(scores.size() / 2);
    }

    private static BigInteger getValue(Character c) {
        return switch (c) {
            case ')' -> new BigInteger("1");
            case ']' -> new BigInteger("2");
            case '}' -> new BigInteger("3");
            case '>' -> new BigInteger("4");
            default -> new BigInteger("0");
        };
    }

    private static List<Character> getMissingCharacters(Map.Entry<String, Stack<Character>> line) {
        Stack<Character> stack = line.getValue();
        final List<Character> list = stack.stream().map(c -> getClosingChar(c)).collect(Collectors.toList());
        List<Character> shallowCopy = list.subList(0, list.size());
        Collections.reverse(shallowCopy);
        return shallowCopy;
    }


    private static int part1() {
        final Input input = getInput();

        assert input != null;
        if(input.corrupts().size() > 0) {
            return calculateResultPart1(input.corrupts());
        }
        return 0;
    }
}
