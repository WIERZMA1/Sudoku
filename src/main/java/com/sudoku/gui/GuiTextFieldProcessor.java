package main.java.com.sudoku.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Map;

import static main.java.com.sudoku.constant.GameColors.*;

public class GuiTextFieldProcessor {

    private GuiBoard guiBoard = GuiBoard.getInstance();
    private GuiProcessor guiProcessor = GuiProcessor.getInstance();
    private GuiTextFieldColorManager colorManager = new GuiTextFieldColorManager();
    private GuiTextFieldKeyboardManager keyboardManager = new GuiTextFieldKeyboardManager();

    public void addTextFieldOperations(Map<Integer, TextField> sudokuBoardFields) {
        for (Map.Entry<Integer, TextField> field : sudokuBoardFields.entrySet()) {
            addOperations(field.getValue(), sudokuBoardFields);
        }
    }

    private void addOperations(TextField textField, Map<Integer, TextField> sudokuBoardFields) {
        textField.setOnKeyReleased(e -> {
            KeyCode inputCode = e.getCode();
            if (e.getText().equals("")) {
                keyboardManager.nonAlphanumericKeys(textField, inputCode);
            } else {
                char inputChar = e.getText().charAt(0);
                if (Character.isDigit(inputChar)) {
                    if (!textField.getText().equals("")) {
                        changeTextColor(textField, USER_INPUT_COLOR);
                        colorManager.illuminateNumbers(textField, sudokuBoardFields);
                        guiBoard.readBoardPanel();
                        guiProcessor.finish();
                    }
                } else if (Character.isLetter(inputChar) || Character.isDefined(inputChar)) {
                    if (textField.getText().equals("") || !Character.isDigit(textField.getText().charAt(0)) || inputCode == KeyCode.DELETE) {
                        changeTextColor(textField, GAME_INPUT_COLOR);
                        textField.setText("");
                    }
                } else {
                    keyboardManager.nonAlphanumericKeys(textField, inputCode);
                }
            }
        });

        textField.setOnKeyTyped(e -> {
            if (textField.getText().length() >= 1) {
                e.consume();
            }
        });

        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    colorManager.illuminateNeighbours(textField, sudokuBoardFields);
                    colorManager.illuminateNumbers(textField, sudokuBoardFields);
                } else {
                    colorManager.deilluminateNumbers(textField, sudokuBoardFields);
                }
            }
        });

        textField.setFont(Font.font("Verdana", FontWeight.BOLD, textField.getFont().getSize() + 10));
        textField.setAlignment(Pos.CENTER);
        changeTextBackgroundColor(textField, DEFAULT_BACKGROUND);
    }
}