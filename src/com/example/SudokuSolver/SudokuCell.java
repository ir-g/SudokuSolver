package com.example.SudokuSolver;

import java.util.EnumMap;

public class SudokuCell {
    private int x, y;
    private SudokuCellValue value;
    private SudokuGrid parentGrid;
    EnumMap<SudokuCellValue, Boolean> potentialValues = new EnumMap<>(SudokuCellValue.class);

    public SudokuCell(int x, int y, SudokuCellValue value, SudokuGrid parentGrid) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.parentGrid = parentGrid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public SudokuCellValue getValue() {
        return value;
    }

    public boolean hasValue() {
        return (value!=null);
    }

    public void setValue(SudokuCellValue value) {
        this.value = value;
    }

    public SudokuGrid getParentGrid() {
        return parentGrid;
    }

    public void setParentGrid(SudokuGrid parentGrid) {
        this.parentGrid = parentGrid;
    }

    public EnumMap<SudokuCellValue, Boolean> getPotentialValues() {
        for (SudokuCellValue value:  SudokuCellValue.values()) {
            potentialValues.put(value, true);
        }
        for (SudokuCell neighbour: getAllNeighbours()){
            if(neighbour.value!=null&&potentialValues.get(neighbour.value)!=null) potentialValues.remove(neighbour.value);
        }
        return potentialValues;
    }

    public SudokuCell[] getColumnNeighbours(){
        SudokuCell[] sudokuCells = new SudokuCell[8];
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if(i!=y) sudokuCells[count++]=parentGrid.getCell(x,i);
        }
        return sudokuCells;
    }

    public SudokuCell[] getRowNeighbours(){
        SudokuCell[] sudokuCells = new SudokuCell[8];
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if(i!=x) sudokuCells[count++]=parentGrid.getCell(i,y);
        }
        return sudokuCells;
    }

    public SudokuCell[] getSubgridNeighbours(){
        SudokuCell[] sudokuCells = new SudokuCell[8];
        int count = 0;
        //Sub-grid offsets
        int xOffset = 0, yOffset = 0;
        if(x>2) xOffset = xOffset + 3;
        if(x>5) xOffset = xOffset + 3;
        if(y>2) yOffset = yOffset + 3;
        if(y>5) yOffset = yOffset + 3;

        //int xOffset = (x+1)-(x+1)%3, yOffset = (y+1)-(y+1)%3;
        for (int i = xOffset; i <xOffset+ 3; i++) {
            for (int j = yOffset; j < yOffset+3; j++) {
                if(i!=x||j!=y){
                    sudokuCells[count]=parentGrid.getCell(i,j);//(coords, new SudokuCell(xOffset+i,yOffset+j));
                    count++;
                }
            }
        }
        return sudokuCells;
    }

    public SudokuCell[] getAllNeighbours(){
        SudokuCell[] sudokuCells = new SudokuCell[20];
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if(i!=y) sudokuCells[count++]=parentGrid.getCellGrid()[x][i];
        }
        for (int i = 0; i < 9; i++) {
            if(i!=x) sudokuCells[count++]=parentGrid.getCellGrid()[i][y];
        }
        //Sub-grid offsets
        int xOffset = 0, yOffset = 0;
        if(x>2) xOffset = xOffset + 3;
        if(x>5) xOffset = xOffset + 3;
        if(y>2) yOffset = yOffset + 3;
        if(y>5) yOffset = yOffset + 3;

        //int xOffset = (x+1)-(x+1)%3, yOffset = (y+1)-(y+1)%3;
        for (int i = xOffset; i <xOffset+ 3; i++) {
            for (int j = yOffset; j < yOffset+3; j++) {
                if(i!=x&&j!=y){
                    sudokuCells[count]=parentGrid.getCell(i,j);//(coords, new SudokuCell(xOffset+i,yOffset+j));
                    count++;
                }
            }
        }
        return sudokuCells;
    }

    public boolean solve() {
        //Look at methods at: https://www.sudokuwiki.org/sudoku.htm

        //Return false if value already set.
        if (getValue()!=null) return false;
        //START CELL PASS
        for (SudokuCellValue cellType : SudokuCellValue.values()) {
            //CREATE A CELL GET NEIGHBOURS FUNCTION (CREATE A LIST OF CO-ORDS?)
            //TEST WHETHER THIS CELL COULD BE
            //  BY CHECKING ALL NEIGHBOURS(UP, DOWN, SUBGRID) for being equal to
            //  CREATE A CELL CHECK FUNCTION FOR THIS

            //TEST WHETHER ANY OTHER CELLS NEARBY COULD BE
            //  BY CHECKING ALL NEIGHBOURS(UP, DOWN, SUBGRID) for their neighbours
            //  APPLY CELL CHECK ON ALL NEIGHBOURS
            if(couldCellBe(cellType)){
                if(countRivalNeighbours(cellType,getAllNeighbours())==0){
                    setValue(cellType);
                    return true;
                }
                if(countEmptyNeighbours(getColumnNeighbours())==0||countRivalNeighbours(cellType,getColumnNeighbours())==0){
                    setValue(cellType);
                    return true;
                }
                if(countRivalNeighbours(cellType,getRowNeighbours())==0){
                    setValue(cellType);
                    return true;
                }
                if(countRivalNeighbours(cellType,getSubgridNeighbours())==0){
                    setValue(cellType);
                    return true;
                }
            }
        }
        if(getPotentialValues().size()==1){
            setValue((SudokuCellValue) getPotentialValues().keySet().toArray()[0]);
            return true;
        }
        //END CELL PASS
        return false;
    }

    /*
    int neighboursWhoCanDoThis = 0;
    for (SudokuCell neighbour : getAllNeighbours()) {
        if(getValue()==cellType){
            testPass = false;
        }
        int subNeighboursWhoCan = 0;

        //Check sub neighbours
        for (SudokuCell subNeighbour : getAllNeighbours()) {
            if(subNeighbour.getValue()==cellType){

            }
        }
    }

    for (SudokuCell neighbour : getColumnNeighbours()) {
        if(getValue()==cellType){
            testPass = false;
        }
        //Check sub neighbours
        for (SudokuCell subNeighbour : neighbour.getAllNeighbours()) {
            if(subNeighbour.getValue()==cellType){

            }
        }
    }*/

    private boolean couldCellBe(SudokuCellValue value){
        boolean couldCellBe = true;
        for (SudokuCell neighbour : getAllNeighbours()) {
            if(neighbour.getValue() == value) couldCellBe = false;
        }
        return couldCellBe;
    }

    private int countRivalNeighbours(SudokuCellValue value, SudokuCell[] neighbours){
        int rivalCount = 0;
        for (SudokuCell neighbour : neighbours) {
            if(neighbour.couldCellBe(value)) rivalCount++;
        }
        return rivalCount;
    }

    private int countEmptyNeighbours(SudokuCell[] neighbours){
        int emptyCount = 0;
        for (SudokuCell neighbour : neighbours) {
            if(neighbour.getValue()==null) emptyCount++;
        }
        return emptyCount;
    }

    @Override
    public String toString() {
        if(value == null){
            return " ";
        }
        return value.toString();
    }

    public void addPossibilities() {
        for (SudokuCellValue cellType : SudokuCellValue.values()) {
            //if(couldCellBe())
        }
    }
}
