package main.java.com.sudoku;


import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuiButtons {

    private List<Button> buttons = new ArrayList<Button>() {
        {
            add(new Button("New"));
            add(new Button("Check"));
            add(new Button("Solve"));
            add(new Button("Pause"));
            add(new Button("Scoreboard"));
            add(new Button("Reset"));
            add(new Button("Clear"));
            add(new Button("Exit"));
        }
    };
    private GuiBoard guiBoard = GuiBoard.getInstance();
    private GuiProcessor guiProcessor = GuiProcessor.getInstance();
    private GuiTimer guiTimer = GuiTimer.getInstance();

    public GridPane menuPanel() {
        final GridPane menuPanel = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(25);
        menuPanel.getColumnConstraints().addAll(column, column, column, column);
        setButtonsSize();
        int col = 0;
        int row = 0;
        for (Button button : buttons) {
            menuPanel.add(button, col, row);
            GridPane.setFillWidth(button, true);
            row = col == 3 ? 1 : row;
            col = col == 3 ? 0 : col + 1;
        }
        return menuPanel;
    }

    private void setButtonsSize() {
        for (Button button : buttons) {
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }
    }

    public void addButtonFunctions() {
        Button pause = buttons.get(3);
        buttons.get(0).setOnAction((event) -> guiProcessor.generateSudoku());
        buttons.get(1).setOnAction(event -> guiBoard.checkBoardPanel());
        buttons.get(2).setOnAction(event -> guiBoard.solveBoardPanel());
        pause.setOnAction(event -> {
            if (guiTimer.getTimer() != null) {
                if (pause.getText().equals("Pause")) {
                    pause.setText("Resume");
                    guiBoard.setVisible(false);
                    guiTimer.timerPause();
                } else if (pause.getText().equals("Resume")) {
                    pause.setText("Pause");
                    guiBoard.setVisible(true);
                    guiTimer.timerResume();
                }
            }
        });
        buttons.get(4).setOnAction(event -> guiProcessor.showScoreboard());
        buttons.get(5).setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You sure You want to reset?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Reset");
            alert.setHeaderText("Reset");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                guiBoard.resetBoardPanel();
            }
        });
        buttons.get(6).setOnAction(event -> guiBoard.clearBoardPanel());
        buttons.get(7).setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to quit?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Exit");
            alert.setHeaderText("Exit");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                Platform.exit();
            }
        });
    }
}