package com.example.SudokuSolver;

public class SudokuGrid {

    SudokuCell[][] cellGrid = new SudokuCell[9][9];

    public SudokuGrid(){
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                setCell(x,y,new SudokuCell(x,y,null,this));
            }
        }
    }

    public SudokuGrid(SudokuCell[][] cellGrid){
        this.cellGrid = cellGrid;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if(getCell(x,y)==null) setCell(x,y,new SudokuCell(x,y,null,this));
                else {
                    getCell(x,y).setParentGrid(this);
                    getCell(x,y).setX(x);
                    getCell(x,y).setY(y);
                }
            }
        }
    }

    public SudokuGrid(int[][] intCellGrid) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                int curInt = intCellGrid[x][y];
                if(0 < curInt && curInt < 10) {
                    this.cellGrid[x][y] = new SudokuCell(x,y, SudokuCellValue.valueOf(curInt), this);
                }else{
                    this.cellGrid[x][y] = new SudokuCell(x,y, null, this);
                }
            }
        }
    }

    public boolean solve() {
        System.out.println(toString());
        if(countEmpty()!=0) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (!getCell(x, y).hasValue()) getCell(x,y).addPossibilities();
                }
            }
        }
        while (countEmpty() > 0) {
            int solvedOccurrences = 0;
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if(getCell(x,y).solve()) solvedOccurrences++;
                }
            }
            System.out.println(toString());
            if(solvedOccurrences==0) return false;
        }
        if(countEmpty()==0) return true;
        return false;
    }

    public int countEmpty() {
        int emptyValues = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if(getCell(x,y).getValue()==null) emptyValues++;
            }
        }
        return emptyValues;
    }

    public SudokuCell getCell(int x, int y) {
        return cellGrid[x][y];
    }

    private void setCell(int x, int y, SudokuCell sudokuCell) {
        cellGrid[x][y] = sudokuCell;
    }

    public SudokuCell[][] getCellGrid() {
        return cellGrid;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if(getCell(x,y)==null){
                    stringBuilder.append(" ");
                }else{
                    stringBuilder.append(getCell(x,y).toString());
                }
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
        /*
        return "SudokuGrid{" +
                "cellGrid=" + Arrays.toString(cellGrid) +
                '}';*/
    }
}
