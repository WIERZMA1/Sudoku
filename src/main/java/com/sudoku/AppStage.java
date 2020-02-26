package main.java.com.sudoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.com.sudoku.gui.GuiBoard;
import main.java.com.sudoku.gui.GuiButtons;
import main.java.com.sudoku.gui.GuiTextFieldProcessor;
import main.java.com.sudoku.gui.GuiTimer;

public class AppStage extends Application {

    private VBox createBox() {
        VBox box = new VBox();
        GuiTimer guiTimer = GuiTimer.getInstance();
        GuiBoard guiBoard = GuiBoard.getInstance();
        GuiButtons guiButtons = new GuiButtons();
        GuiTextFieldProcessor guiTextFieldProcessor = new GuiTextFieldProcessor();
        box.getChildren().addAll(guiTimer.getTimeShow(), guiBoard.boardPanel(), guiButtons.menuPanel());
        guiButtons.addButtonFunctions();
        guiTextFieldProcessor.addTextFieldOperations(guiBoard.getSudokuBoardFields());
        return box;
    }

    public void start(Stage primaryStage) {
        Scene scene = new Scene(createBox());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Sudoku.png")));
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(520);
        primaryStage.setMaxWidth(630);
        primaryStage.setMaxHeight(650);
        primaryStage.show();
    }
}