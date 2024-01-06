package year_2023.Day_07;

import year_2023.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_07 implements Day {
  public static void main(String[] args) throws IOException {
    Day_07 day07 = new Day_07();
    day07.part1(); // wrong: 248628681 too low, 248812215 correct
    day07.part2();
  }

  @Override
  public void part1() throws IOException {
    BufferedReader reader =
        new BufferedReader(
            new FileReader(
                "/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_07/testinput.txt"));

    List<Hand> hands = new ArrayList<>();

    String line = reader.readLine();

    while (line != null) {
      String[] splitted = line.split(" ");
      String handString = splitted[0];
      int bid = Integer.parseInt(splitted[1]);
      Card[] cards = new Card[5];

      for (int i = 0; i < handString.length(); i++) {
        cards[i] = Card.fromValue(handString.charAt(i));
      }

      hands.add(new Hand(cards, bid, getScore(cards)));
      line = reader.readLine();
    }

    hands.sort(
        (o1, o2) -> {
          if (o1.score() == o2.score()) {
            return getWinnerSameScore(o1, o2);
          } else {
            return o1.score() - o2.score();
          }
        });

    int totalWinnings = 0;

    for (int i = 0; i < hands.size(); i++) {
      Hand hand = hands.get(i);
      totalWinnings += hand.bid() * (i + 1);
    }
    System.out.println("totalWinnings = " + totalWinnings);
  }

  private int getWinnerSameScore(final Hand o1, final Hand o2) {
    for (int i = 0; i < 5; i++) {
      if (o1.cards()[i].getValue() != o2.cards()[i].getValue()) {
        return o1.cards()[i].getValue() - o2.cards()[i].getValue();
      }
    }
    return 0;
  }

  private int getWinnerSameScoreWithJ(final Hand o1, final Hand o2) {
    for (int i = 0; i < 5; i++) {
      int valueA = o1.cards()[i].getValue() == 11 ? 1 : o1.cards()[i].getValue();
      int valueB = o2.cards()[i].getValue() == 11 ? 1 : o2.cards()[i].getValue();

      if (valueA != valueB) {
        return valueA - valueB;
      }
    }
    return 0;
  }

  private int getScore(final Card[] cards) {
    Map<Card, Integer> cardCounts = new HashMap<>();

    for (final Card card : cards) {
      if (cardCounts.containsKey(card)) {
        cardCounts.put(card, cardCounts.get(card) + 1);
      } else {
        cardCounts.put(card, 1);
      }
    }

    if (cardCounts.size() == 1) {
      // Five of a kind
      return 6;
    } else if (cardCounts.size() == 2) {
      if (cardCounts.containsValue(4)) {
        // Four of a kind
        return 5;
      } else {
        // Full house
        return 4;
      }
    } else if (cardCounts.size() == 3) {
      if (cardCounts.containsValue(3)) {
        // Three of a kind
        return 3;
      } else {
        // Two pair
        return 2;
      }
    } else if (cardCounts.size() == 4) {
      // One pair
      return 1;
    } else {
      // High card
      return 0;
    }
  }

  private int getScoreWithJ(final Card[] cards) {
    Map<Card, Integer> cardCounts = new HashMap<>();

    for (final Card card : cards) {
      if (cardCounts.containsKey(card)) {
        cardCounts.put(card, cardCounts.get(card) + 1);
      } else {
        cardCounts.put(card, 1);
      }
    }

    boolean hasJoker = cardCounts.containsKey(Card.JACK);

    if (cardCounts.size() == 1) {
      // Five of a kind
      return 6;
    } else if (cardCounts.size() == 2) {
      if (cardCounts.containsValue(4)) {
        if (hasJoker) {
          // Make five of a kind
          return 6;
        }

        // Four of a kind
        return 5;
      } else {
        if (hasJoker) {
          // In both cases make five of a kind
          return 6;
        }

        // Full house
        return 4;
      }
    } else if (cardCounts.size() == 3) {
      if (cardCounts.containsValue(3)) {
        if (hasJoker) {
          // Make four of a kind in all cases
          return 5;
        }

        // Three of a kind
        return 3;
      } else {
        if (hasJoker) {
          if (cardCounts.get(Card.JACK) == 2) {
            // Make four of a kind
            return 5;
          }

          if (cardCounts.get(Card.JACK) == 1) {
            // Make full house
            return 4;
          }
        }

        // Two pair
        return 2;
      }
    } else if (cardCounts.size() == 4) {

      if (hasJoker) {
        // Make three of a kind
        return 3;
      }
      // One pair
      return 1;
    } else {

      if (hasJoker) {
        // Make one pair
        return 1;
      }
      // High card
      return 0;
    }
  }

  @Override
  public void part2() throws IOException {
    // 250035606 too low
    // 250057090 correct

    BufferedReader reader =
        new BufferedReader(
            new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_07/input.txt"));

    List<Hand> hands = new ArrayList<>();

    String line = reader.readLine();

    while (line != null) {
      String[] splitted = line.split(" ");
      String handString = splitted[0];
      int bid = Integer.parseInt(splitted[1]);
      Card[] cards = new Card[5];

      for (int i = 0; i < handString.length(); i++) {
        cards[i] = Card.fromValue(handString.charAt(i));
      }

      hands.add(new Hand(cards, bid, getScoreWithJ(cards)));
      line = reader.readLine();
    }

    hands.sort(
        (o1, o2) -> {
          if (o1.score() == o2.score()) {
            return getWinnerSameScoreWithJ(o1, o2);
          } else {
            return o1.score() - o2.score();
          }
        });

    int totalWinnings = 0;

    for (int i = 0; i < hands.size(); i++) {
      Hand hand = hands.get(i);
      totalWinnings += hand.bid() * (i + 1);
    }
    System.out.println("totalWinnings = " + totalWinnings);
  }

  record Hand(Card[] cards, int bid, int score) {
    @Override
    public boolean equals(final Object obj) {
      return false;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public String toString() {
      return "Hand{"
          + "cards="
          + Arrays.toString(cards)
          + ", bid="
          + bid
          + ", score="
          + score
          + '}';
    }
  }

  enum Card {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14);

    private final int value;

    Card(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public static Card fromValue(char c) {
      return switch (c) {
        case '2' -> TWO;
        case '3' -> THREE;
        case '4' -> FOUR;
        case '5' -> FIVE;
        case '6' -> SIX;
        case '7' -> SEVEN;
        case '8' -> EIGHT;
        case '9' -> NINE;
        case 'T' -> TEN;
        case 'J' -> JACK;
        case 'Q' -> QUEEN;
        case 'K' -> KING;
        case 'A' -> ACE;
        default -> throw new IllegalArgumentException("Unknown card: " + c);
      };
    }
  }
}
