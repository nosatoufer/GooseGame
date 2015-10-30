package goosegame;

import javafx.application.Application;
import goosegame.view.Window;

/**
 * Test
 *
 * @author nosa
 */
public class GooseGame {

    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        System.out.println("Hellow goose.");
        Controler ctrl = new Controler();
        try {
            ctrl.newPlayer(Color.RED);
            ctrl.newPlayer(Color.BLUE);
            ctrl.newPlayer(Color.BLACK);
            ctrl.startGame();
            for (int i = 0; i < 10; i++) {
                System.out.println("test");
                ctrl.rollDice();
                ctrl.play();
                sleep(100);
            }
        } catch (GooseGameException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
    */
    
    public static void main(String[] args) {
        Application.launch(Window.class, args);
    }

}
