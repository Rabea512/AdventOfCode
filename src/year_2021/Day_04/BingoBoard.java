package year_2021.Day_04;

public class BingoBoard {
    public int[][] board;
    public int[] hitRow;
    public int[] hitColumn;

    public BingoBoard() {
        this.hitColumn = new int[5];
        this.hitRow = new int[5];
        this.board = new int[5][5];
    }

    public boolean drawNumber(int number) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(number == board[i][j]) {
                    hitRow[i]++;
                    hitColumn[j]++;
                    board[i][j] = -1;
                    return hitRow[i] == 5 || hitColumn[j] == 5;
                }
            }
        }
        return false;
    }

    public int countBoard() {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                sum += board[i][j] != -1 ? board[i][j] : 0;
            }
        }
        return sum;
    }
}
