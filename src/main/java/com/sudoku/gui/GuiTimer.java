package main.java.com.sudoku.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;

import static javafx.scene.layout.GridPane.setHalignment;
import static main.java.com.sudoku.constant.GameColors.DEFAULT_BACKGROUND;
import static main.java.com.sudoku.constant.GameColors.changeTextBackgroundColor;

public class GuiTimer {

    private static GuiTimer guiTimer = null;
    private int time;
    private Timer timer;
    private TextField timeShow = new TextField();


    public GuiTimer() {
        timeShow.setEditable(false);
    }

    public static GuiTimer getInstance() {
        if (guiTimer == null) {
            synchronized (GuiBoard.class) {
                if (guiTimer == null) {
                    guiTimer = new GuiTimer();
                }
            }
        }
        return guiTimer;
    }

    public void createTimer() {
        timeShow.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        timeShow.setFont(Font.font("Verdana", FontWeight.BOLD, 18.0));
        timeShow.setAlignment(Pos.CENTER);
        changeTextBackgroundColor(timeShow, DEFAULT_BACKGROUND);
        setHalignment(timeShow, HPos.CENTER);
    }

    public int getTime() {
        return time;
    }

    public Timer getTimer() {
        return timer;
    }

    public TextField getTimeShow() { return timeShow; }

    public void timeCounter() {
        timer = new Timer(1000, e -> {
            time++;
            int seconds = time % 60;
            int minutes = (time / 60) % 60;
            int hours = (time / 3600) % 24;
            String display = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timeShow.setText(display);
        });
    }

    public void timerStart() {
        time = 0;
        timer.start();
    }

    public void timerPause() {
        timer.stop();
    }

    public void timerResume() {
        timer.start();
    }
}