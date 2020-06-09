package com.example.SudokuSolver;

import java.util.HashMap;
import java.util.Map;

public enum SudokuCellValue {
    ONE     (1),
    TWO     (2),
    THREE   (3),
    FOUR    (4),
    FIVE    (5),
    SIX     (6),
    SEVEN   (7),
    EIGHT   (8),
    NINE    (9);

    //Follows approach from: https://codingexplained.com/coding/java/enum-to-integer-and-integer-to-enum

    private int value;
    private static Map map = new HashMap<>();

    SudokuCellValue(int i) {
        this.value = i;
    }

    static {
        for (SudokuCellValue sudokuCellValue : SudokuCellValue.values()) {
            map.put(sudokuCellValue.value, sudokuCellValue);
        }
    }

    public static SudokuCellValue valueOf(int pageType) {
        return (SudokuCellValue) map.get(pageType);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(getValue());
    }
}

