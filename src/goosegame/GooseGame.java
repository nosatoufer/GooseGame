/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
            } catch (GooseGameException gge) {
                System.out.println(gge.toString());
            } catch (InterruptedException e) {

            }
        }
    }

}
