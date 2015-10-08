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
public class PlayerTest {

    public PlayerTest() {

    }

    @Test
    public void testPosition() {
        Player p = new Player(6, Color.GREEN);
        p.setPosition(52);
        int position = p.position();
        Assert.assertEquals(52, position);

    }

    /*@Test
     public void testLastPosition() {
     Case cas = new Case(CaseType.INN);//case numero 19
     Player p = cas.player();
     int cuurentPos = p.position();
     int lastPos = p.lastPosition();
     Assert.assertEquals(, lastPos);
     }*/
    @Test
    public void testIsStuck() {
        Player p = new Player(1, Color.PINK);
        p.setStuck(2);
        int stuck = p.isStuck();
        Assert.assertEquals(2, stuck);

    }

    @Test
    public void testIsJailed() {
        Player p = new Player(1, Color.PINK);
        boolean jail = p.isJailed();
        if (jail) {
            Assert.assertEquals(true, jail);
        }
    }

    /*@Test
    public void testSetPosition() {
        Player p = new Player(2, Color.WHITE);
        p.setPosition(42);
        int position = p.position();
        int lastPosition = p.lastPosition();
        Assert.assertEquals(42, position);
        Assert.assertEquals(position, lastPosition);
    }*/

    @Test
    public void testSetStuck() {
        Player p = new Player(3, Color.YELLOW);
        p.setStuck(3);
        int stuck = p.isStuck();
        Assert.assertEquals(3, stuck);
    }

    @Test
    public void testDecStuck() {
        Player p = new Player(1, Color.PINK);
        p.setStuck(4);
        int stuck = p.isStuck();
        p.decStuck();
        int stuckLeft = p.isStuck();
        Assert.assertEquals(3, stuckLeft);
    }

    @Test
    public void testSetJail() {
        Player p = new Player(6, Color.GREEN);
        boolean jail = p.isJailed();
        p.setJail();
        if (jail) {
            Assert.assertEquals(false, jail);
        }
    }

    @Test
    public void testNumPlayer() {
        Case cas = new Case(CaseType.INN);//case numero 19
        Player p = new Player(4, Color.BLUE);
        int num = p.numPlayer();
        Assert.assertEquals(4, num);

    }

}
