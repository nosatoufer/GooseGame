/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.view;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import goosegame.view.CustomButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author nosa
 */
public class Window extends Application {
    @Override
    public void start(Stage primaryStage) {
        List<String> choices = new ArrayList<>();
choices.add("a");
choices.add("b");
choices.add("c");

ChoiceDialog<String> dialog = new ChoiceDialog<>("b", choices);
dialog.setTitle("Choice Dialog");
dialog.setHeaderText("Look, a Choice Dialog");
dialog.setContentText("Choose your letter:");

Optional<String> result = dialog.showAndWait();
// The Java 8 way to get the response value (with lambda expression).
result.ifPresent(letter -> System.out.println("Your choice: " + letter));

        
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
