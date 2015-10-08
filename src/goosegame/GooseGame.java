package goosegame;

import goosegame.model.Board;
import goosegame.model.GooseGameException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nosa
 */
public class GooseGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Board b = new Board(3); // Construit un nouveau plateau avec 3 joueurs
            int i = 0;
            
            while (!b.isOver()) { // Tant que la partie n'est pas finie
                try {
                    b.play();
                    System.out.println(b.toString());
                    Thread.sleep(1000);
                } catch (GooseGameException | InterruptedException gge) {
                    System.out.println(gge.toString());
                }
            }
        } catch (GooseGameException ex) {
            Logger.getLogger(GooseGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
