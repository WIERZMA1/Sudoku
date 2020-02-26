package main.java.com.sudoku.core;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow {

    private List<Integer> row = new ArrayList<>();

    public List<Integer> getRow() {
        return row;
    }

    public void setRow(List<Integer> row) {
        this.row = row;
    }
}