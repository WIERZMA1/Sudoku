package main.java.com.sudoku.core;

import main.java.com.sudoku.constant.GameConfig;
import main.java.com.sudoku.gui.GuiBoard;

import static main.java.com.sudoku.constant.GameConfig.*;

public class SudokuProcessor {

    private static SudokuProcessor sudokuProcessor = null;

    private SudokuProcessor() {
    }

    public static SudokuProcessor getInstance() {
        if (sudokuProcessor == null) {
            synchronized (GuiBoard.class) {
                if (sudokuProcessor == null) {
                    sudokuProcessor = new SudokuProcessor();
                }
            }
        }
        return sudokuProcessor;
    }

    public boolean solveSudoku(SudokuBoard board) {
        for (int row = 0; row < GameConfig.GAME_SIZE; row++) {
            for (int col = 0; col < GAME_SIZE; col++) {
                if (board.getNumber(row, col) == EMPTY) {
                    return checkNumberForCell(board, row, col);
                }
            }
        }
        return checkSudoku(board);
    }

    public boolean checkSudoku(SudokuBoard board) {
        boolean result = true;
        for (int row = EMPTY; row < GAME_SIZE; row++) {
            for (int col = EMPTY; col < GAME_SIZE; col++) {
                int num = board.getNumber(row, col);
                if (num != EMPTY) {
                    board.setNumber(row, col, EMPTY);
                    if (!check(board, row, col, num)) {
                        result = false;
                        board.setNumber(row, col, num);
                        break;
                    }
                    board.setNumber(row, col, num);
                }
            }
        }
        return result;
    }

    private boolean checkNumberForCell(SudokuBoard board, int row, int col) {
        for (int num = 1; num <= GAME_SIZE; num++) {
            if (check(board, row, col, num)) {
                board.setNumber(row, col, num);
                if (solveSudoku(board)) {
                    return true;
                }
            }
            board.setNumber(row, col, EMPTY);
        }
        return false;
    }

    private boolean checkRow(SudokuBoard board, int row, int num) {
        return checkLine(col -> board.getNumber(row, col) == num);
    }

    private boolean checkCol(SudokuBoard board, int col, int num) {
        return checkLine(row -> board.getNumber(row, col) == num);
    }

    private boolean checkLine(LineChecker lineChecker) {
        for (int i = 0; i < GAME_SIZE; i++) {
            if (lineChecker.check(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkBox(SudokuBoard board, int boxRow, int boxCol, int num) {
        for (int row = 0; row < BOX_SIZE; row++) {
            for (int col = 0; col < BOX_SIZE; col++) {
                if (board.getNumber((boxRow - (boxRow % BOX_SIZE) + row), (boxCol - (boxCol % BOX_SIZE) + col)) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean check(SudokuBoard board, int row, int col, int num) {
        return checkRow(board, row, num) &&
                checkCol(board, col, num) &&
                checkBox(board, row, col, num);
    }
}