package main.java.com.sudoku.core;

import main.java.com.sudoku.constant.GameDifficulty;

import java.io.Serializable;

public class SudokuScoreboard implements Serializable {

    private String name;
    private int time;
    private GameDifficulty difficulty;
    private int difficultyScore;
    private String date;

    public SudokuScoreboard(String name, int time, GameDifficulty difficulty, String date) {
        this.name = name;
        this.time = time;
        this.difficulty = difficulty;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        int seconds = time % 60;
        int minutes = (time / 60) % 60;
        int hours = (time / 3600) % 24;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getDifficulty() {
        String difficultyName = "";
        switch (difficulty) {
            case EASY:
                difficultyName = "Easy";
                difficultyScore = 2;
                break;
            case MEDIUM:
                difficultyName = "Medium";
                difficultyScore = 3;
                break;
            case HARD:
                difficultyName = "Hard";
                difficultyScore = 4;
                break;
            case EXTREME:
                difficultyName = "Extreme";
                difficultyScore = 5;
        }
        return difficultyName;
    }

    public String getScore() {
        return time <= 3600 ?  String.valueOf((3600 - time) * (difficultyScore)): "0";
    }

    public String getDate() { return date; }
}