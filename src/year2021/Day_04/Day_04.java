package year2021.Day_04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Day_04 {
    public static void main(String[] args) {
        System.out.println(part(1));
        System.out.println(part(2));
    }

    private static int part(int part) {
        BufferedReader reader;
        int ret = 0;
        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_04/input.txt"));
            String line = reader.readLine();

            int[] drawnNumbers = stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            line = reader.readLine();
            List<BingoBoard> bingoBoards = new ArrayList<BingoBoard>();
            while (line != null) {
                line = reader.readLine();
                BingoBoard bingoBoard = new BingoBoard();
                for (int i = 0; i < 5; i++) {
                    bingoBoard.board[i] = stream(line.trim().split(" +")).mapToInt(Integer::parseInt).toArray();
                    line = reader.readLine();
                }
                bingoBoards.add(bingoBoard);
            }
            List<Integer> scores = new ArrayList<>();
            for (int drawnNumber : drawnNumbers) {
                List<BingoBoard> winners = new ArrayList<BingoBoard>();
                for (BingoBoard bingoBoard : bingoBoards) {
                    final boolean bingo = bingoBoard.drawNumber(drawnNumber);
                    if(bingo) {
                        scores.add(bingoBoard.countBoard() * drawnNumber);
                        if(bingoBoards.size() < 10) {
                            System.out.println(bingoBoard);
                        }
                        winners.add(bingoBoard);
                    }
                }
                bingoBoards = bingoBoards.stream().filter(bingoBoard -> !winners.contains(bingoBoard)).collect(Collectors.toList());
            }
            return part == 1 ? scores.get(0) : scores.get(scores.size()-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int part2() {

        int ret = 0;

        return ret;
    }
}
