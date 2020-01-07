package main.java.com.sudoku;

import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class GameColors {

    public final static double[] USER_INPUT_COLOR = {0, 70, 165};
    public final static double[] GAME_INPUT_COLOR = {0, 0, 0};
    public final static double[] DEFAULT_BACKGROUND = {229, 245, 245};
    public final static double[] HIGHLIGHTED_FAULTY_NUMBERS = {240, 128, 128};
    public final static double[] HIGHLIGHTED_BACKGROUND = {56, 183, 252};
    public final static double[] HIGHLIGHTED_NUMBERS_BACKGROUND = {153, 229, 255};
    public final static double[] HIGHLIGHTED_FIELDS_BACKGROUND = {190, 230, 255};

    public static void changeTextColor(TextField textField, double[] color) {
        textField.setStyle("-fx-text-inner-color: rgb(" + color[0] + ", " + color[1] + ", " + color[2] + ");");
    }

    public static void changeTextBackgroundColor(TextField textField, double[] color) {
        textField.setBackground(new Background(new BackgroundFill(new Color(color[0] / 255, color[1] / 255, color[2] / 255, 1), null, null)));
    }
}