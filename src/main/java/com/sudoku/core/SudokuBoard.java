package main.java.com.sudoku.core;

import main.java.com.sudoku.constant.GameDifficulty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static main.java.com.sudoku.constant.GameConfig.EMPTY;
import static main.java.com.sudoku.constant.GameConfig.GAME_SIZE;

public class SudokuBoard {

    private static SudokuBoard sudokuBoard = null;
    private List<SudokuRow> board = new ArrayList<>();
    private List<SudokuRow> clonedBoard = new ArrayList<>();
    private List<SudokuRow> actualBoard = new ArrayList<>();

    private SudokuBoard() {
    }

    public static SudokuBoard getInstance() {
        if (sudokuBoard == null) {
            synchronized (SudokuBoard.class) {
                if (sudokuBoard == null) {
                    sudokuBoard = new SudokuBoard();
                }
            }
        }
        return sudokuBoard;
    }

    public List<SudokuRow> getBoard() {
        return board;
    }

    public List<SudokuRow> getClonedBoard() {
        return clonedBoard;
    }

    public List<SudokuRow> getActualBoard() {
        return actualBoard;
    }

    public void setBoard(List<SudokuRow> board) {
        this.board = board;
    }

    public void setClonedBoard(List<SudokuRow> clonedBoard) {
        this.clonedBoard = clonedBoard;
    }

    public void setActualBoard(List<SudokuRow> actualBoard) {
        this.actualBoard = actualBoard;
    }

    public int getNumber(int row, int col) {
        return board.get(row).getRow().get(col);
    }

    public void setNumber(int row, int col, int num) {
        board.get(row).getRow().set(col, num);
    }

    public List<SudokuRow> deepCopy(List<SudokuRow> board) {
        List<SudokuRow> clonedBoard = new ArrayList<>(board.size());
        for (SudokuRow rows : board) {
            SudokuRow clonedRow = new SudokuRow();
            for (Integer number : rows.getRow()) {
                clonedRow.getRow().add(number);
            }
            clonedBoard.add(clonedRow);
        }
        return clonedBoard;
    }

    public void clearBoard() {
        List<SudokuRow> rows = Stream.generate(SudokuRow::new)
                .limit(GAME_SIZE)
                .collect(Collectors.toList());
        setBoard(rows);
    }

    public void generateBoard() {
        List<SudokuRow> rows = new ArrayList<>();
        SudokuRow row1 = new SudokuRow();
        Random random = new Random();
        for (int i = 0; i < GAME_SIZE; i++) {
            int number = random.nextInt(GAME_SIZE) + 1;
            while (row1.getRow().contains(number)) {
                number = random.nextInt(GAME_SIZE) + 1;
            }
            row1.getRow().add(number);
        }
        rows.add(row1);

        for (int i = 1; i < GAME_SIZE; i++) {
            SudokuRow sudokuRow = new SudokuRow();
            sudokuRow.setRow(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
            rows.add(sudokuRow);
        }
        setBoard(rows);
    }

    public void makeSudoku(GameDifficulty difficulty) {
        Random random = new Random();
        int dif = 0;

        switch (difficulty) {
            case EASY:
                dif = 52;
                break;
            case MEDIUM:
                dif = 59;
                break;
            case HARD:
                dif = 66;
                break;
            case EXTREME:
                dif = 73;
                break;
        }

        for (int i = 0; i < dif; i++) {
            int row = random.nextInt(GAME_SIZE);
            int col = random.nextInt(GAME_SIZE);
            if (getNumber(row, col) != EMPTY) {
                setNumber(row, col, EMPTY);
            }
        }
    }

    public boolean findEmpty() {
        for (int row = 0; row < GAME_SIZE; row++) {
            for (int col = 0; col < GAME_SIZE; col++) {
                if (board.get(row).getRow().get(col) == EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }
}