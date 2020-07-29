package com.example.SudokuSolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SudokuGrid implements Cloneable {

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
        //TODO: MOVE BACK TO RECURSION
        while (countEmpty() > 0) {
            /*
            //TODO: Check whether a possibility in a subgrid is only in one ROW or COLUMN.
            // i equals the index of the row or column
            // Iterate through subgrids
            for (int i = 0; i < 3; i++) {


            }
            for (int i = 0; i < 9; i++) {
                // j equals the index of the value in that row or column.
                for (int j = 0; j < 9; j++) {


                }
            }*/


            int solvedOccurrences = 0;
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if(getCell(x,y).solve()) solvedOccurrences++;
                }
            }
            System.out.println(toString());

            if(solvedOccurrences==0){
                double permutations = 1;
                int possibleCount = 0;
                //TODO: ADD DATA STRUCTURE
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        SudokuCell cell = getCell(i, j);
                        //TODO: LOG TO STRUCTURE EACH POSSIBILITY
                        int potentialSize = cell.getPotentialValues().size();
                        /*
                        if(potentialSize>0){
                            permutations = permutations * potentialSize;
                            possibleCount = possibleCount + potentialSize;
                            System.out.println(cell.getPossibilityString());
                            //TODO:ADD PREP ATTEMPT METHOD FOR CREATING A NEW SUDOKU GRID
                            for (Map.Entry<SudokuCellValue,Boolean> potentialValue : cell.getPotentialValues().entrySet()) {
                                if(potentialValue.getValue()==false) continue;
                                SudokuGrid attemptGrid = prepAttempt(i,j,potentialValue.getKey());
                                boolean attemptResult = attemptGrid.solve();
                                if(attemptResult == true && attemptGrid.countEmpty()==0){
                                    //TODO: CREATE A SET CELL GRID METHOD
                                    setCellGrid(attemptGrid.getCellGrid());
                                    return true;
                                }
                            }
                        }*/
                    }
                }
                System.out.println(String.format("Permeations possible: %s, Possible Count: %d, Empty Cell count: %d", permutations, possibleCount, countEmpty()));
                return false;
            }
        }
        if(countEmpty()==0) return true;
        return false;
    }

    public SudokuGrid prepAttempt(int x,int y, SudokuCellValue value){
        SudokuGrid newSudokuGrid = new SudokuGrid(getCellGrid().clone());
        try {
            newSudokuGrid = (SudokuGrid) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        newSudokuGrid.getCell(x,y).setValue(value);
        return newSudokuGrid;
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
    public void setCellGrid(SudokuCell[][] cellGrid){
        this.cellGrid = cellGrid.clone();
    }

    public SudokuCell[] getRow(int i){
        SudokuCell[] cells = new SudokuCell[9];
        for (int j = 0; j < 9; j++) {
            cells[j] = getCell(i, j);
        }
        return cells;
    }

    public SudokuCell[] getCol(int i){
        SudokuCell[] cells = new SudokuCell[9];
        for (int j = 0; j < 9; j++) {
            cells[j] = getCell(j, i);
        }
        return cells;
    }

    public SudokuCell[] getSubgrid(int i){
        /*Cells in a subgrid, subgrids in a grid both look like:
            0   1   2
            3   4   5
            6   7   8
         */

        if (i<0||i>8) return new SudokuCell[0];
        SudokuCell[] cells = new SudokuCell[9];
        int subGridXOffset = i%3;
        int subGridYOffset = (i-(i%3))/3;
        int cellsIndex = 0;
        for (int j = 0+subGridXOffset; j < 3+subGridXOffset; j++) {
            for (int k = 0+subGridYOffset; k < 3+subGridYOffset; k++) {
                cells[cellsIndex] = getCell(j,k);
                cellsIndex++;
            }
        }
        return cells;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if(getCell(x,y)==null||getCell(x,y).getValue()==null){
                    stringBuilder.append(" ");
                }else{
                    stringBuilder.append(getCell(x,y).getValue().toString());
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
