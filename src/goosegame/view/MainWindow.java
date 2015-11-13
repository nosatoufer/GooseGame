/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author nosa
 */
public class Window extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Hello World!");
        Button btn = new CustomButton("Mon super bouton");
        /*btn.setText("Say 'Hello World'");
        btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                System.out.println("Hello World!");
            }
        });*/
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        
        primaryStage.show();
    }
}
