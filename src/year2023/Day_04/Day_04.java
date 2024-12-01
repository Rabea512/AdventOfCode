package year2023.Day_04;

import year2023.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_04 implements Day {
  public static void main(String[] args) throws IOException {
    Day_04 day_04 = new Day_04();
    day_04.part1();
    day_04.part2();
  }

  @Override
  public void part1() throws IOException {
    List<Card> cards = readInput();

    int points = 0;
    for (Card card : cards) {
      boolean first = true;
      final int sum = getSumOfCard(card, first).sum();
      points += sum;
    }

    System.out.println(points);
  }

  private static CardWin getSumOfCard(final Card card, boolean first) {
    int sum = 0;
    int count = 0;
    for (Integer myNumber : card.myNumbers) {
      if (card.winningNumbers.contains(myNumber)) {
        if (first) {
          first = false;
          sum += 1;
        } else {
          sum *= 2;
        }
        count++;
      }
    }
    return new CardWin(count, sum);
  }

  private static List<Card> readInput() throws IOException {
    final List<Card> cards = new ArrayList<>();
    BufferedReader reader =
        new BufferedReader(
            new FileReader(
                "/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_04/input.txt"));

    String line = reader.readLine();
    while (line != null) {
      Pattern pattern =
          Pattern.compile(
              "Card\\s+(?<cardNumber>\\d+):(?<winningNumbers>[\\s*\\d+]*)\\|(?<myNumbers>[\\s*\\d+]*)");
      Matcher matcher = pattern.matcher(line);

      if (matcher.find()) {
        int cardNumber = Integer.parseInt(matcher.group("cardNumber"));

        final String winningNumbersString = matcher.group("winningNumbers");

        String[] winningNumbersArray = winningNumbersString.split(" ");
        List<Integer> winningNumbers =
            Arrays.stream(winningNumbersArray)
                .filter(n -> !n.isEmpty())
                .map(Integer::parseInt)
                .toList();

        final String myNumbersString = matcher.group("myNumbers");

        String[] myNumbersArray = myNumbersString.split(" ");
        List<Integer> myNumbers =
            Arrays.stream(myNumbersArray).filter(n -> !n.isEmpty()).map(Integer::parseInt).toList();

        cards.add(new Card(cardNumber, winningNumbers, myNumbers));
      }

      line = reader.readLine();
    }
    return cards;
  }

  @Override
  public void part2() throws IOException {
    List<Card> cards = readInput();
    Map<Integer, Integer> copyCards = new HashMap<>();

    for (Card card : cards) {
      boolean first = true;
      final CardWin cardWin = getSumOfCard(card, first);
      int factor =
          copyCards.get(card.cardNumber()) == null ? 1 : copyCards.get(card.cardNumber()) + 1;

      for (int i = 1; i <= cardWin.count(); i++) {
        int copyCardNumber = card.cardNumber() + i;
        if (copyCards.containsKey(copyCardNumber)) {
          copyCards.put(copyCardNumber, copyCards.get(copyCardNumber) + factor);
        } else {
          copyCards.put(copyCardNumber, factor);
        }
      }
    }

    System.out.println(
        copyCards.values().stream()
                .filter(integer -> integer > 0)
                .mapToInt(i -> i)
                .sum()
            + cards.size());
  }

  record Card(int cardNumber, List<Integer> winningNumbers, List<Integer> myNumbers) {}

  record CardWin(int count, int sum) {}
  ;
}
