/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter.main;

import iwaiter.view.*;
import iwaiter.controller.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    
    public static String screen1ID = "mainView";
    public static String screen1File = "iwait.fxml";
    public static String screen2ID = "newItem";
    public static String screen2File = "newItem.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "Screen3.fxml";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        
            
        ScreenView mainContainer = new ScreenView();
        mainContainer.loadScreen(Main.screen1ID, Main.screen1File);
        mainContainer.loadScreen(Main.screen2ID, Main.screen2File);
        //mainContainer.loadScreen(Main.screen3ID, Main.screen3File);
        
        mainContainer.setScreen(Main.screen1ID);
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Bar-Application iWAITER");
        primaryStage.show();
           /* AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("iwait.fxml"));
            Scene scene = new Scene(page);
           primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Bar-Application iWAITER");
            primaryStage.show();*/
        
    }
}

