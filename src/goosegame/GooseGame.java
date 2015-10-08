package goosegame;

import goosegame.model.Board;
import goosegame.model.GooseGameException;

/**
 *
 * @author nosa
 */
public class GooseGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Board b = new Board(3);
        int i = 0;

        while (!b.isOver()) {
            try {
                b.play();
                System.out.println(b.toString());
                Thread.sleep(1000);
            } catch (GooseGameException | InterruptedException gge) {
                System.out.println(gge.toString());
            }
        }
    }

}
