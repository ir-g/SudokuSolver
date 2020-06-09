package com.example.SudokuSolverApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("layout1.fxml"));
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        boolean loadGui = args.length == 0 || !args[0].equals("cli");
        if(loadGui) {
            launch(args);
        } else{
            System.out.println("CLI MODE LOADED");
        }
    }
}
