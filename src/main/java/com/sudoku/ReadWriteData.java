package main.java.com.sudoku;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteData {

    public static final String DATA_FILE = "SudokuScoreboard.ser";

    public static void serializeScoreboard(List<SudokuScoreboard> scoreboard) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(DATA_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(scoreboard);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<SudokuScoreboard> deserializeScoreboard() {
        List<SudokuScoreboard> scoreboard = new ArrayList<>();
        if (new File(DATA_FILE).exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(DATA_FILE);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                scoreboard = (List<SudokuScoreboard>) in.readObject();
                in.close();
                fileIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return scoreboard;
    }
}