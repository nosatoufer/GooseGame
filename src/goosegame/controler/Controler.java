/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.controler;
import goosegame.model.Game;
import goosegame.model.Color;
import goosegame.model.GooseGameException;

/**
 *
 * @author nosa
 */
public class Controler {
    
    private Game m_game;
    
    public Controler ()
    {
        m_game = new Game();
    }
    
    public void newPlayer(Color c) throws GooseGameException
    {
        m_game.addPlayer(c);
    }

    public void play() throws GooseGameException
    {
        m_game.play();
    }    
    public void rollDice() throws GooseGameException
    {
        m_game.rollDices();
    }
    
    public void startGame()
    {
        m_game.start();
    }
    
}
