/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.view;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author G41486
 */
public class CustomButton extends Button {
    
    public CustomButton(String string) {
        this.setText(string);
        
        this.setOnMouseEntered(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                System.out.println("Hello World!");
            }
        });
    }
}
