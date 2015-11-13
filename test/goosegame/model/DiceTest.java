/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.model;

import heb.esi.goosegame.model.Dice;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author HONEY
 */
public class DiceTest {

    public DiceTest() {

    }

    @Test
    public void testRoll() {

    }

    @Test
    public void testValue() {
        int m_Roll = 5;
        Dice d = new Dice(5);
        int val = d.value();
        Assert.assertEquals(0,val);
    }
}


