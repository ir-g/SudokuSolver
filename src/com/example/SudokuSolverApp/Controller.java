package com.example.SudokuSolverApp;


import com.example.SudokuSolver.SudokuGrid;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class Controller {

    @FXML
    GridPane sudokuGridPane;

    public void runSolver(ActionEvent actionEvent) {
        //int[][] gridPaneNodes = new int[gridPaneWidth][gridPaneHeight] ;
        int[][] intCellGrid = new int[9][9];
        for (Node child : sudokuGridPane.getChildren()) { // H/T https://stackoverflow.com/a/31145995/1437706
            Integer column = GridPane.getColumnIndex(child);
            Integer row = GridPane.getRowIndex(child);
            if (column == null) column = 0;
            if (row == null) row = 0;
            if(child instanceof TextField){
                TextField textField = (TextField) child;
                String text = textField.getText();
                text = text.replaceAll("[^\\d.]", "");
                int intVal = 0;
                if(text.length()>0) intVal =  Integer.valueOf(text); // H/T https://beginnersbook.com/2013/12/how-to-convert-string-to-int-in-java/
                intCellGrid[column][row] = intVal;
            }
        }

        /*

         */

        SudokuGrid sudokuGrid = new SudokuGrid(intCellGrid);
        boolean testResult = sudokuGrid.solve();
        if(testResult)  System.out.println("Sudoku solved successfully");
        else            System.out.println("Sudoku not solved");

        for (Node child : sudokuGridPane.getChildren()) { // H/T https://stackoverflow.com/a/31145995/1437706
            Integer column = GridPane.getColumnIndex(child);
            Integer row = GridPane.getRowIndex(child);
            if (column == null) column = 0;
            if (row == null) row = 0;
            if(child instanceof TextField){
                TextField textField = (TextField) child;
                textField.setText(sudokuGrid.getCell(column, row).getValueString());
            }
        }


        /*
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sudoku Solver");
        if(testResult){
            alert.setHeaderText("Sudoku Solved Successfully");
            //alert.setContentText(sudokuGrid.toString());
        }else{
            alert.setHeaderText("Sudoku Not Solved");
            //alert.setContentText(sudokuGrid.toString());
        }
        alert.showAndWait();
         */
    }

    public void doPrefill1(ActionEvent actionEvent){
        runPrefill(actionEvent, 1);
    }

    public void doPrefill2(ActionEvent actionEvent){
        runPrefill(actionEvent, 2);
    }

    public void doPrefill3(ActionEvent actionEvent){
        runPrefill(actionEvent, 3);
    }

    public void runPrefill(ActionEvent actionEvent, int preFill) {
        int[][][] intCellGridCollection = new int[20][9][9];

        //EMPTY PUZZLE
        intCellGridCollection[0] = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        //EASY PUZZLE
        intCellGridCollection[1] = new int[][]{
                {0, 0, 0, 8, 9, 0, 0, 0, 4},
                {9, 8, 5, 1, 3, 4, 6, 2, 0},
                {0, 7, 4, 0, 0, 0, 0, 1, 9},
                {0, 0, 0, 4, 1, 9, 0, 5, 2},
                {0, 0, 9, 7, 6, 5, 1, 0, 0},
                {7, 5, 1, 0, 8, 3, 0, 0, 6},
                {0, 1, 7, 0, 0, 0, 3, 6, 0},
                {6, 9, 0, 5, 0, 1, 2, 4, 0},
                {5, 4, 0, 3, 0, 0, 0, 0, 0}
        };

        //MEDIUM PUZZLE
        intCellGridCollection[2] = new int[][]{
                {0, 1, 0, 0, 8, 4, 0, 5, 0},
                {0, 3, 0, 0, 2, 5, 7, 1, 0},
                {8, 2, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 4, 2, 5, 9, 8, 7, 0},
                {0, 8, 0, 3, 0, 0, 0, 6, 0},
                {0, 0, 1, 0, 0, 0, 2, 0, 0},
                {1, 0, 2, 0, 0, 0, 6, 4, 0},
                {0, 4, 3, 0, 0, 2, 1, 0, 7},
                {5, 0, 8, 0, 0, 1, 0, 0, 0}
        };

        //HARD PUZZLE
        intCellGridCollection[3] = new int[][]{
                {0, 6, 9, 0, 0, 0, 8, 0, 2},
                {0, 0, 8, 0, 3, 2, 0, 0, 6},
                {5, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 5, 0, 0, 0, 7, 0, 8},
                {9, 0, 0, 0, 1, 0, 0, 2, 0},
                {7, 2, 0, 8, 0, 0, 0, 6, 0},
                {0, 0, 7, 3, 0, 6, 4, 0, 9},
                {6, 0, 4, 0, 7, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 9, 0, 0, 0}
        };
        int[][] intCellGrid;
        try {
            intCellGrid = intCellGridCollection[preFill];
        }catch (Exception e){
            e.printStackTrace();
            intCellGrid = intCellGridCollection[0];
        }

        for (Node child : sudokuGridPane.getChildren()) { // H/T https://stackoverflow.com/a/31145995/1437706
            Integer column = GridPane.getColumnIndex(child);
            Integer row = GridPane.getRowIndex(child);
            if (column == null) column = 0;
            if (row == null) row = 0;
            if(child instanceof TextField){
                TextField textField = (TextField) child;
                int value = intCellGrid[row][column];
                if(value!=0) textField.setText(Integer.toString(value));
                else textField.setText("");
            }
        }
    }

    public void runGenerator(ActionEvent actionEvent) {

    }
}
