/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author HONEY
 */
public class DicesTest {

    public DicesTest() {

    }

    @Test
    public void testDiceValue() {
        List<Dice> list = new ArrayList<>();
        Dices dices = new Dices(2, 5);
        Dice d1 = new Dice(3);
        Dice d2 = new Dice(4);
        list.add(d1);
        list.add(d2);
        int val1 = d1.value();
        int val2 = d2.value();
        Assert.assertEquals(dices.diceValue(1), val2);

    }

    @Test
    public void testNDices() {
        List<Dice> list = new ArrayList<>();
        Dices dices = new Dices(2, 5);
        Dice d1 = new Dice(3);
        Dice d2 = new Dice(4);
        list.add(d1);
        list.add(d2);
        int nDice = dices.nDices();
        Assert.assertEquals(2, nDice);

    }

    @Test
    public void testSum() {
        List<Dice> list = new ArrayList<>();
        Dices dices = new Dices(2, 5);
        Dice d1 = new Dice(3);
        Dice d2 = new Dice(4);
        list.add(d1);
        list.add(d2);
        int val1 = d1.value();
        int val2 = d2.value();
        int mSum = dices.sum();
        Assert.assertEquals(val1+val2, mSum);

    }

    @Test
    public void testRoll() {

    }

}
