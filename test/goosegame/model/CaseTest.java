/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.model;

import heb.esi.goosegame.model.Player;
import heb.esi.goosegame.model.Case;
import heb.esi.goosegame.model.CaseType;
import heb.esi.goosegame.model.PlayerColor;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author nosa
 */
public class CaseTest {

    public CaseTest() {
    }

    @Test
    public void testSetPlayer() {
        Case cas = new Case(CaseType.DEATH);
        Player player = new Player(2, PlayerColor.BLUE);
        cas.setPlayer(player);
        Assert.assertEquals(player, player);

    }

    @Test
    public void testPlayer() {
        Case cas = new Case(CaseType.JAIL);
        Player player = new Player(2, PlayerColor.GREEN);
        Player p = cas.player();
        Assert.assertEquals(cas.player(), p);
    }

    @Test
    public void testType() {
        Case cas = new Case(CaseType.JAIL);
        CaseType type = cas.type();
        Assert.assertEquals(CaseType.JAIL, type);

    }
    
    
    @Test
    public void testSetType(){
        Case cas = new Case(CaseType.JAIL);
        CaseType type = cas.type();
        cas.setType(type);
        Assert.assertEquals(type, type);
        
    }

}

 