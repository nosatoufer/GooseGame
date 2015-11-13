package goosegame.controler;

import goosegame.model.Game;
import goosegame.model.PlayerColor;
import goosegame.model.GooseGameException;

/**
 *
 * @author nosa
 */
public class Controler {
    
    private final Game game;
    
    public Controler (Game game)
    {
        this.game = game;
    }
    public void newPlayer(PlayerColor c) throws GooseGameException
    {
        this.game.addPlayer(c);
    }
    public void play() throws GooseGameException
    {
        this.game.play();
    }    
    public void rollDice() throws GooseGameException
    {
        this.game.rollDices();
    }
    public void startGame() throws GooseGameException
    {
        this.game.start();
    }
    
}
