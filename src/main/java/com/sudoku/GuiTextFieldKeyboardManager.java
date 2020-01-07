package main.java.com.sudoku;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import static main.java.com.sudoku.GameConfig.*;

public class GuiTextFieldKeyboardManager {

    private GuiBoard guiBoard = GuiBoard.getInstance();

    public void nonAlphanumericKeys(TextField textField, KeyCode inputCode) {
        switch (inputCode) {
            case UP:
                selectionUp(textField);
                break;
            case DOWN:
                selectionDown(textField);
                break;
            case LEFT:
                selectionLeft(textField);
                break;
            case RIGHT:
                selectionRight(textField);
                break;
            default:
        }
    }

    private void selectionUp(TextField textField) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        if (num % GAME_SIZE > BOX_SIZE || num % GAME_SIZE == 0) {
            guiBoard.getSudokuBoardFields().get(num - BOX_SIZE).requestFocus();
            guiBoard.getSudokuBoardFields().get(num - BOX_SIZE).deselect();
        } else if (num % GAME_SIZE <= BOX_SIZE && num > BOX_JUMP) {
            guiBoard.getSudokuBoardFields().get(num - 21).requestFocus();
        }
    }

    private void selectionDown(TextField textField) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        if (num % GAME_SIZE < 7 && num % GAME_SIZE != 0) {
            guiBoard.getSudokuBoardFields().get(num + BOX_SIZE).requestFocus();
        } else if ((num % GAME_SIZE >= 7 || num % GAME_SIZE == 0) && num < 55) {
            guiBoard.getSudokuBoardFields().get(num + 21).requestFocus();
        }
    }

    private void selectionLeft(TextField textField) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        if (num % BOX_SIZE > 1 || num % BOX_SIZE == 0) {
            guiBoard.getSudokuBoardFields().get(num - 1).requestFocus();
        } else if (num % BOX_SIZE == 1 && num % BOX_JUMP != 1 && num % BOX_JUMP != 4 && num % BOX_JUMP != 7) {
            guiBoard.getSudokuBoardFields().get(num - 7).requestFocus();
        }
    }

    private void selectionRight(TextField textField) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        if (num % BOX_SIZE != 0) {
            guiBoard.getSudokuBoardFields().get(num + 1).requestFocus();
        } else if (num % BOX_JUMP != 0 && num % BOX_JUMP != 21 && num % BOX_JUMP != 24) {
            guiBoard.getSudokuBoardFields().get(num + 7).requestFocus();
        }
    }
}