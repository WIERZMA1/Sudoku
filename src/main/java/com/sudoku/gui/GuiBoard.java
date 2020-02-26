package main.java.com.sudoku.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.java.com.sudoku.core.SudokuBoard;
import main.java.com.sudoku.core.SudokuProcessor;
import main.java.com.sudoku.core.SudokuRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.java.com.sudoku.constant.GameColors.*;
import static main.java.com.sudoku.constant.GameConfig.*;

public class GuiBoard {

    private static GuiBoard guiBoard = null;
    private boolean generated = false;
    private Map<Integer, TextField> sudokuBoardFields = new HashMap<>();
    private Map<TextField, Integer> sudokuBoardFieldsNumbers = new HashMap<>();
    private SudokuBoard sudokuBoard = SudokuBoard.getInstance();
    private SudokuProcessor sudokuProcessor = SudokuProcessor.getInstance();
    private GuiTimer guiTimer = GuiTimer.getInstance();
    private ImageView iconWin = new ImageView(getClass().getResource("/Win.png").toExternalForm());
    private ImageView iconSolution = new ImageView(getClass().getResource("/Solution.png").toExternalForm());
    private ImageView iconNoSolution = new ImageView(getClass().getResource("/NoSolution.png").toExternalForm());

    private GuiBoard() {
    }

    public static GuiBoard getInstance() {
        if (guiBoard == null) {
            synchronized (GuiBoard.class) {
                if (guiBoard == null) {
                    guiBoard = new GuiBoard();
                }
            }
        }
        return guiBoard;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public Map<Integer, TextField> getSudokuBoardFields() {
        return sudokuBoardFields;
    }

    public Map<TextField, Integer> getSudokuBoardFieldsNumbers() {
        return sudokuBoardFieldsNumbers;
    }

    public GridPane boardPanel() {
        GridPane boardPanel = new GridPane();
        boardPanel.gridLinesVisibleProperty();
        boardPanel.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        boardPanel.setPrefSize(550, 550);
        int field = 1;
        for (int row = 0; row < BOX_SIZE; ++row) {
            for (int col = 0; col < BOX_SIZE; ++col) {
                GridPane box = new GridPane();
                box.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                box.setGridLinesVisible(true);
                box.setPrefSize(boardPanel.getPrefWidth() / 3, boardPanel.getPrefHeight() / 3);
                for (int cell = 0; cell < GAME_SIZE; ++cell) {
                    TextField textField = new TextField();
                    box.add(textField, cell % 3, cell / 3);
                    textField.setPrefSize(box.getPrefWidth() / 3, box.getPrefHeight() / 3);
                    sudokuBoardFields.put(field, textField);
                    sudokuBoardFieldsNumbers.put(textField, field);
                    field++;
                }
                addConstraints(box);
                boardPanel.add(box, col, row);
                box.setAlignment(Pos.CENTER);
            }
        }
        addConstraints(boardPanel);
        return boardPanel;
    }

    private void addConstraints(GridPane gridPane) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setFillHeight(true);
        rowConstraints.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().addAll(rowConstraints, rowConstraints, rowConstraints);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);
    }

    public void fillBoardPanel() {
        for (int row = 0; row < GAME_SIZE; row++) {
            for (int col = 0; col < GAME_SIZE; col++) {
                int number = sudokuBoard.getNumber(row, col);
                int boxMultiplier = (row / BOX_SIZE) + (row / BOX_SIZE) * 2 + col / BOX_SIZE;
                int boxAdder = (row % BOX_SIZE * BOX_SIZE) + col % BOX_SIZE;
                int result = (boxMultiplier * GAME_SIZE + boxAdder) + 1;
                if (number != EMPTY) {
                    String text = Integer.toString(number);
                    sudokuBoardFields.get(result).setText(text);
                    sudokuBoardFields.get(result).setEditable(false);
                    changeTextColor(sudokuBoardFields.get(result), GAME_INPUT_COLOR);
                    changeTextBackgroundColor(sudokuBoardFields.get(result), DEFAULT_BACKGROUND);
                } else {
                    sudokuBoardFields.get(result).setEditable(true);
                }
            }
        }
    }

    public void readBoardPanel() {
        List<TextField> fields = new ArrayList<>();
        List<SudokuRow> rows = new ArrayList<>();
        fillList(fields);
        int add = 0;
        for (int row = 0; row < GAME_SIZE; row++) {
            SudokuRow sudokuRow = new SudokuRow();
            List<Integer> nums = new ArrayList<>();
            for (int num = add; num < GAME_SIZE + add; num++) {
                if (fields.get(num).getText().equals("") || fields.get(num).getText()==null) {
                    nums.add(EMPTY);
                } else {
                    nums.add(Integer.parseInt(fields.get(num).getText()));
                }
            }
            sudokuRow.setRow(nums);
            rows.add(sudokuRow);
            add += GAME_SIZE;
        }
        sudokuBoard.clearBoard();
        sudokuBoard.setBoard(rows);
        if (!generated) {
            sudokuBoard.setClonedBoard(sudokuBoard.deepCopy(sudokuBoard.getBoard()));
        }
        sudokuBoard.setActualBoard(sudokuBoard.deepCopy(sudokuBoard.getBoard()));
    }

    public void fillList(List<TextField> fields) {
        int begin = 1;
        for (int box = 0; box < BOX_SIZE; box++) {
            int addRow = 0;
            for (int row = 0; row < BOX_SIZE; row++) {
                int add = 0;
                for (int boxRow = 0; boxRow < BOX_SIZE; boxRow++) {
                    fields.add(sudokuBoardFields.get(begin + add + addRow));
                    fields.add(sudokuBoardFields.get(begin + 1 + add + addRow));
                    fields.add(sudokuBoardFields.get(begin + 2 + add + addRow));
                    add += GAME_SIZE;
                }
                addRow += BOX_SIZE;
            }
            begin += GAME_SIZE * BOX_SIZE;
        }
    }

    public boolean checkBoardPanel() {
        boolean check = false;
        if (sudokuBoardFields.size() == FIELDS_SIZE) {
            readBoardPanel();
            if (sudokuBoard.findEmpty() && sudokuProcessor.solveSudoku(sudokuBoard)) {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setGraphic(iconSolution);
                info.setTitle("Check ok");
                info.setContentText("Solution available.");
                info.showAndWait();
                sudokuBoard.setBoard(sudokuBoard.deepCopy(sudokuBoard.getActualBoard()));
            } else if (sudokuProcessor.checkSudoku(sudokuBoard)) {
                if (guiTimer.getTimer() != null) {
                    guiTimer.getTimer().stop();
                }
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setGraphic(iconWin);
                info.setTitle("Sudoku solved!");
                info.setContentText("Good job!");
                info.showAndWait();
                check = true;
            } else if (!sudokuProcessor.checkSudoku(sudokuBoard)) {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setGraphic(iconNoSolution);
                info.setTitle("No solution, please try again.");
                info.setContentText("Failed!");
                info.showAndWait();
            }
        }
        return check;
    }

    public void solveBoardPanel() {
        readBoardPanel();
        if (!sudokuProcessor.solveSudoku(sudokuBoard)) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setGraphic(iconNoSolution);
            info.setTitle("No solution, please try again.");
            info.setContentText("Failed!");
            info.showAndWait();
        }
        sudokuProcessor.solveSudoku(sudokuBoard);
        fillBoardPanel();
    }

    public void resetBoardPanel() {
        if (sudokuBoard.getBoard().size() != EMPTY) {
            clearBoardPanel();
            sudokuBoard.setBoard(sudokuBoard.deepCopy(sudokuBoard.getClonedBoard()));
            fillBoardPanel();
        }
    }

    public void clearBoardPanel() {
        if (sudokuBoardFields.size() != EMPTY) {
            sudokuBoardFields.values().forEach(e -> e.setText(""));
            sudokuBoardFields.values().forEach(e -> e.setEditable(true));
        }
    }

    public void setVisible(boolean visible) {
        if (sudokuBoardFields.size() != EMPTY) {
            sudokuBoardFields.values().forEach(e -> e.setVisible(visible));
        }
    }

    public void deleteBoardPanel() {
        sudokuBoard.clearBoard();
        clearBoardPanel();
    }
}