package year2023.Day_05;

import year2023.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_05 implements Day {
  public static void main(String[] args) throws IOException {
    Day_05 day_05 = new Day_05();
    // day_05.part1();
    day_05.part2();
  }

  @Override
  public void part1() throws IOException {
    BufferedReader reader =
        new BufferedReader(
            new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_05/input.txt"));

    String line = reader.readLine();

    String[] seedStrings = line.split("seeds: ")[1].split(" ");
    List<Long> seeds = Arrays.stream(seedStrings).map(Long::parseLong).toList();

    reader.readLine();

    List<Mappy> seedToSoil = new ArrayList<>();
    List<Mappy> soilToFertilizer = new ArrayList<>();
    List<Mappy> fertilizerToWater = new ArrayList<>();
    List<Mappy> waterToLight = new ArrayList<>();
    List<Mappy> lightToTemperature = new ArrayList<>();
    List<Mappy> temperatureToHumidity = new ArrayList<>();
    List<Mappy> humidityToLocation = new ArrayList<>();

    getMap(reader, seedToSoil);
    getMap(reader, soilToFertilizer);
    getMap(reader, fertilizerToWater);
    getMap(reader, waterToLight);
    getMap(reader, lightToTemperature);
    getMap(reader, temperatureToHumidity);
    getMap(reader, humidityToLocation);

    Long minLocation = Long.MAX_VALUE;

    for (Long seed : seeds) {
      final Long soil = getNext(seedToSoil, seed);
      final Long fertilizer = getNext(soilToFertilizer, soil);
      final Long water = getNext(fertilizerToWater, fertilizer);
      final Long light = getNext(waterToLight, water);
      final Long temperature = getNext(lightToTemperature, light);
      final Long humidity = getNext(temperatureToHumidity, temperature);
      final Long location = getNext(humidityToLocation, humidity);

      if (location < minLocation) {
        minLocation = location;
      }
    }

    System.out.println(minLocation);
  }

  private Long getNext(final List<Mappy> map, final Long current) {
    final Optional<Mappy> maybeMappy =
        map.stream()
            .filter(mappy -> mappy.source() <= current && current < mappy.source + mappy.count())
            .findFirst();

    return maybeMappy
        .map(mappy -> mappy.destination() + (current - mappy.source()))
        .orElse(current);
  }

  private static void getMap(final BufferedReader reader, final List<Mappy> soilToFertilizer)
      throws IOException {
    String line = reader.readLine();
    line = reader.readLine();

    while (line != null && !line.isEmpty()) {
      String[] numberStrings = line.split(" ");
      final List<Long> numbers = Arrays.stream(numberStrings).map(Long::parseLong).toList();
      soilToFertilizer.add(new Mappy(numbers.get(1), numbers.get(0), numbers.get(2)));
      line = reader.readLine();
    }
  }

  @Override
  public void part2() throws IOException {
    BufferedReader reader =
        new BufferedReader(
            new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2023/Day_05/input.txt"));

    String line = reader.readLine();

    String[] seedStrings = line.split("seeds: ")[1].split(" ");

    reader.readLine();

    List<Mappy> seedToSoil = new ArrayList<>();
    List<Mappy> soilToFertilizer = new ArrayList<>();
    List<Mappy> fertilizerToWater = new ArrayList<>();
    List<Mappy> waterToLight = new ArrayList<>();
    List<Mappy> lightToTemperature = new ArrayList<>();
    List<Mappy> temperatureToHumidity = new ArrayList<>();
    List<Mappy> humidityToLocation = new ArrayList<>();

    getMap(reader, seedToSoil);
    getMap(reader, soilToFertilizer);
    getMap(reader, fertilizerToWater);
    getMap(reader, waterToLight);
    getMap(reader, lightToTemperature);
    getMap(reader, temperatureToHumidity);
    getMap(reader, humidityToLocation);

    long minLocation = Long.MAX_VALUE;

    for (int i = 0; i < seedStrings.length; i = i + 2) {
      // 10: seed: 3902275369 location: 11554136

      long initialSeed = Long.parseLong(seedStrings[i]);
      long count = Long.parseLong(seedStrings[i + 1]);
      System.out.println(i + ": " + initialSeed + " " + count);
      for (long j = 0; j < count; j++) {
        long seed = initialSeed + j;
        final Long soil = getNext(seedToSoil, seed);
        final Long fertilizer = getNext(soilToFertilizer, soil);
        final Long water = getNext(fertilizerToWater, fertilizer);
        final Long light = getNext(waterToLight, water);
        final Long temperature = getNext(lightToTemperature, light);
        final Long humidity = getNext(temperatureToHumidity, temperature);
        final Long location = getNext(humidityToLocation, humidity);

        if (location < minLocation) {
          minLocation = location;
          System.out.println("seed: " + seed);

          System.out.println("soil: " + soil);
          System.out.println("fertilizer: " + fertilizer);
          System.out.println("water: " + water);
          System.out.println("light: " + light);
          System.out.println("temperature: " + temperature);
          System.out.println("humidity: " + humidity);
          System.out.println(minLocation);
        }
      }
    }

    System.out.println(minLocation);
  }

  record Mappy(long source, long destination, long count) {}
}
