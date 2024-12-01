package year2021.Day_08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_08 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static List<String> getOutputNumbers() {
        return getNumbers().stream().<List<String>>map(numbers -> numbers.output()).reduce(new ArrayList<String>(), (list, numbers) -> {
            list.addAll(numbers);
            return list;
        });
    }

    private static List<Numbers> getNumbers() {
        BufferedReader reader;

        List<Numbers> numbers = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_08/input.txt"));
            String line = reader.readLine();
            while(line != null) {

                final String[] split = line.split(" \\| ");
                final String[] input = split[0].trim().split(" ");
                final String[] output = split[1].trim().split(" ");
                numbers.add(new Numbers(Arrays.stream(input).toList(), Arrays.stream(output).toList()));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbers;
    }


    private static int part2() {
        List<Numbers> numbers = getNumbers();
        Map<Integer, List<Character>> mapping = new HashMap<>();

        return numbers.stream().map(elements -> {
            List<String> input = new ArrayList<>(elements.input());
            input.sort(new NumbersComparator());
            input.forEach(number -> {
                switch(number.length()) {
                    case 2:
                        mapping.put(1, toCharacterList(number));
                        break;
                    case 3:
                        mapping.put(7, toCharacterList(number));
                        break;
                    case 4:
                        mapping.put(4, toCharacterList(number));
                        break;
                    case 7:
                        mapping.put(8, toCharacterList(number));
                        break;
                    case 5:
                        final List<Character> charactersNumber2 = toCharacterList(number);
                        final List<Character> characters6 = mapping.get(6);
                        final List<Character> characters1 = mapping.get(1);
                        if(charactersNumber2.containsAll(characters1)) {
                            mapping.put(3, charactersNumber2);
                        } else if(characters6.containsAll(charactersNumber2)) {
                            mapping.put(5, charactersNumber2);
                        } else {
                            mapping.put(2, charactersNumber2);
                        }
                        break;
                    case 6:
                        final List<Character> charactersNumber = toCharacterList(number);
                        final List<Character> characters_1 = mapping.get(1);
                        final List<Character> characters_4 = mapping.get(4);
                        if(charactersNumber.containsAll(characters_1) && charactersNumber.containsAll(characters_4)) {
                            mapping.put(9, charactersNumber);
                        } else if(charactersNumber.containsAll(characters_1)) {
                            mapping.put(0, charactersNumber);
                        } else {
                            mapping.put(6, charactersNumber);
                        }
                        break;
                    default:
                        break;
                }
            });
            return new NumberMapping(mapping, elements.input(), elements.output());
        })
                .map(element -> element.output().stream().reduce("", (old, number) -> old + getNumber(mapping, number)))
                .mapToInt(Integer::parseInt).reduce(0, (old, number) -> old + number);
    }

    private static int getNumber(Map<Integer, List<Character>> mapping, String number) {
        final List<Character> characters = toCharacterList(number);
        return getKeyByValue(mapping, characters);
    }

    public static Integer getKeyByValue(Map<Integer, List<Character>> map, List<Character> value) {
        for (Map.Entry<Integer, List<Character>> entry : map.entrySet()) {
            if (entry.getValue().containsAll(value) && value.containsAll(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static List<Character> toCharacterList(String number) {
        List<Character> list = new ArrayList<>();
        char[] arr = number.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    private static int part1() {
        final List<String> outputNumbers = getOutputNumbers();
        final List<String> strings1 = outputNumbers.stream().filter(number -> number.length() == 2).toList();
        final List<String> strings2 = outputNumbers.stream().filter(number -> number.length() == 3).toList();
        final List<String> strings3 = outputNumbers.stream().filter(number -> number.length() == 4).toList();
        final List<String> strings4 = outputNumbers.stream().filter(number -> number.length() == 7).toList();

        return (int) outputNumbers.stream().filter(number -> number.length() == 2 || number.length() == 3 || number.length() == 4 || number.length() == 7).count();

    }
}
