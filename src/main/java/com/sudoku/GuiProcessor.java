package main.java.com.sudoku;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class GuiProcessor {

    private static GuiProcessor guiProcessor = null;
    private String name;
    private GameDifficulty difficulty;

    private List<SudokuScoreboard> scoreboardList = ReadWriteData.deserializeScoreboard();

    private GuiBoard guiBoard = GuiBoard.getInstance();
    private GuiTimer guiTimer = GuiTimer.getInstance();
    private SudokuBoard sudokuBoard = SudokuBoard.getInstance();
    private SudokuProcessor sudokuProcessor = SudokuProcessor.getInstance();

    private GuiProcessor() {
    }

    public static GuiProcessor getInstance() {
        if (guiProcessor == null) {
            synchronized (GuiBoard.class) {
                if (guiProcessor == null) {
                    guiProcessor = new GuiProcessor();
                }
            }
        }
        return guiProcessor;
    }

    public void finish() {
        if (!sudokuBoard.findEmpty() && sudokuProcessor.checkSudoku(sudokuBoard)) {
            if (guiBoard.checkBoardPanel()) {
                SudokuScoreboard sudokuScoreboard = new SudokuScoreboard(name, guiTimer.getTime(), difficulty,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
                scoreboardList.add(sudokuScoreboard);
                ReadWriteData.serializeScoreboard(scoreboardList);
            }
        }
    }

    public void generateSudoku() {
        setUserChoices();
        if (name != null) {
            guiBoard.deleteBoardPanel();
            sudokuBoard.generateBoard();
            sudokuProcessor.solveSudoku(sudokuBoard);
            sudokuBoard.makeSudoku(difficulty);
            sudokuBoard.setClonedBoard(sudokuBoard.deepCopy(sudokuBoard.getBoard()));
            guiBoard.fillBoardPanel();
            if (sudokuBoard.findEmpty()) {
                if (guiTimer.getTimer() != null) {
                    guiTimer.getTimer().stop();
                }
                guiTimer.timeCounter();
                guiTimer.createTimer();
                guiTimer.timerStart();
            }
            guiBoard.setGenerated(true);
        }
    }

    public void setUserChoices() {
        ButtonType easy = new ButtonType("Easy");
        ButtonType medium = new ButtonType("Medium");
        ButtonType hard = new ButtonType("Hard");
        ButtonType extreme = new ButtonType("Extreme");
        Alert alert = new Alert(Alert.AlertType.NONE, "Select difficulty:", easy, medium, hard, extreme);
        alert.setTitle("Difficulty");
        alert.setContentText("Select difficulty:");
        alert.showAndWait().ifPresent(response -> {
            difficulty = GameDifficulty.valueOf(response.getText().toUpperCase());
        });

        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setHeaderText("Enter Your Name");
        name = inputDialog.showAndWait().orElse("Guest");
        name = name.isEmpty() ? "Guest" : name;
    }

    public void showScoreboard() {
        if (scoreboardList.isEmpty()) {
            Alert empty = new Alert(Alert.AlertType.INFORMATION, "There are no results yet.");
            empty.setTitle("Scoreboard");
            empty.setHeaderText("Scoreboard empty");
            empty.show();
        } else {
            TableColumn<SudokuScoreboard, String> name = new TableColumn<>("Name");
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<SudokuScoreboard, String> difficulty = new TableColumn<>("Difficulty");
            difficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
            TableColumn<SudokuScoreboard, String> time = new TableColumn<>("Time");
            time.setCellValueFactory(new PropertyValueFactory<>("time"));
            TableColumn<SudokuScoreboard, String> score = new TableColumn<>("Score");
            score.setCellValueFactory(new PropertyValueFactory<>("score"));
            TableColumn<SudokuScoreboard, String> date = new TableColumn<>("Date");
            date.setCellValueFactory(new PropertyValueFactory<>("date"));

            ObservableList<SudokuScoreboard> list = FXCollections.observableList(scoreboardList);
            TableView<SudokuScoreboard> scoreboardTable = new TableView<>(list);
            scoreboardTable.getColumns().addAll(Arrays.asList(name, difficulty, time, score, date));
            scoreboardTable.setItems(list);
            scoreboardTable.getSortOrder().add(score);

            StackPane layout = new StackPane(scoreboardTable);
            Scene scene = new Scene(layout);

            Stage scoreboard = new Stage();
            scoreboard.setTitle("Scoreboard");
            scoreboard.setScene(scene);

            scoreboard.show();
        }
    }
}