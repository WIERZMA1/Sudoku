package main.java.com.sudoku.gui;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static main.java.com.sudoku.constant.GameColors.*;
import static main.java.com.sudoku.constant.GameConfig.*;

public class GuiTextFieldColorManager {

    private GuiBoard guiBoard = GuiBoard.getInstance();

    public void illuminateNumbers(TextField textField, Map<Integer, TextField> sudokuBoardFields) {
        for (int field = 0; field <= FIELDS_SIZE; field++) {
            if (sudokuBoardFields.get(field) != null && !sudokuBoardFields.get(field).getText().equals("")) {
                if (sudokuBoardFields.get(field).getText().equals(textField.getText())) {
                    changeTextBackgroundColor(sudokuBoardFields.get(field), HIGHLIGHTED_NUMBERS_BACKGROUND);
                }
            }
        }
        changeTextBackgroundColor(textField, HIGHLIGHTED_BACKGROUND);
    }

    public void deilluminateNumbers(TextField textField, Map<Integer, TextField> sudokuBoardFields) {
        for (int field = 0; field <= FIELDS_SIZE; field++) {
            if (sudokuBoardFields.get(field) != null) {
                changeTextBackgroundColor(sudokuBoardFields.get(field), DEFAULT_BACKGROUND);
            }
        }
        changeTextBackgroundColor(textField, DEFAULT_BACKGROUND);
    }

    public void illuminateNeighbours(TextField textField, Map<Integer, TextField> sudokuBoardFields) {
        illuminateBox(textField, sudokuBoardFields);
        illuminateCol(textField, sudokuBoardFields);
        illuminateRow(textField, sudokuBoardFields);
    }

    private void illuminateBox(TextField textField, Map<Integer, TextField> sudokuBoardFields) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        int box = num / GAME_SIZE;
        if (num % GAME_SIZE == 0) {
            box--;
        }
        for (int i = 0; i < FIELDS_SIZE; i++) {
            if (i % GAME_SIZE != 0 && i / GAME_SIZE == box) {
                changeTextBackgroundColor(sudokuBoardFields.get(i), HIGHLIGHTED_FIELDS_BACKGROUND);
            } else if (i % GAME_SIZE == 0 && i / GAME_SIZE == box) {
                changeTextBackgroundColor(sudokuBoardFields.get(i + GAME_SIZE), HIGHLIGHTED_FIELDS_BACKGROUND);
            }
        }
    }

    private void illuminateRow(TextField textField, Map<Integer, TextField> sudokuBoardFields) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        List<Integer> firstCol = new ArrayList<>(Arrays.asList(1, 4, 7, 28, 31, 34, 55, 58, 61));
        int start = num % BOX_JUMP;
        if (start == 0) {
            start = BOX_JUMP;
        }
        if (num <= BOX_JUMP) {
            while (start > GAME_SIZE) {
                start -= GAME_SIZE;
            }
        } else if (num <= 2 * BOX_JUMP) {
            while (start > GAME_SIZE) {
                start -= GAME_SIZE;
            }
            start += BOX_JUMP;
        } else {
            while (start > GAME_SIZE) {
                start -= GAME_SIZE;
            }
            start += 2 * BOX_JUMP;
        }
        while (!firstCol.contains(start)) {
            start -= 1;
        }
        int addBox = 0;
        for (int col = 0; col < BOX_SIZE; col++) {
            int addCol = 0;
            for (int box = 0; box < BOX_SIZE; box++) {
                changeTextBackgroundColor(sudokuBoardFields.get(start + addCol + addBox), HIGHLIGHTED_FIELDS_BACKGROUND);
                addCol++;
            }
            addBox += GAME_SIZE;
        }
    }

    private void illuminateCol(TextField textField, Map<Integer, TextField> sudokuBoardFields) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        List<Integer> firstRow = new ArrayList<>(Arrays.asList(1, 2, 3, 10, 11, 12, 19, 20, 21));
        int col = num % BOX_JUMP;
        int start = 1;
        while (start % BOX_JUMP != col) {
            start++;
        }
        while (!firstRow.contains(start)) {
            start -= BOX_SIZE;
        }
        int addBox = 0;
        for (int row = 0; row < BOX_SIZE; row++) {
            int addRow = 0;
            for (int box = 0; box < BOX_SIZE; box++) {
                changeTextBackgroundColor(sudokuBoardFields.get(start + addRow + addBox), HIGHLIGHTED_FIELDS_BACKGROUND);
                addRow += BOX_SIZE;
            }
            addBox += BOX_JUMP;
        }
    }
}