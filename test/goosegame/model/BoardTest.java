/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.model;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author HONEY
 */
public class BoardTest {

    public BoardTest() {

    }

    @Test
    public void testPlay() throws GooseGameException {
        Board board = new Board(3);
        board.play();

    }

}
