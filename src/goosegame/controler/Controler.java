package goosegame.controler;

import goosegame.model.CaseType;
import goosegame.model.Game;
import goosegame.model.GameState;
import goosegame.model.PlayerColor;
import goosegame.model.GooseGameException;
import java.util.ArrayList;
import javafx.util.Pair;

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
    
    public ArrayList<Pair<PlayerColor, Integer>> getPlayerPos() {
        return this.game.getPlayerPos();
    }
    
    public boolean isGameStarted() {
        return (this.game.getGameState() == GameState.STARTED);
    }
    
    public boolean isGameOver() {
        return (game.getGameState() == GameState.OVER);
    }
    
    public int getDicesSum() {
        return game.getDicesSum();
    }
    
    public PlayerColor getCurrentPlayerColor() {
        return game.getCurrentPlayerColor();
    }
    
    public ArrayList<Pair<Integer, CaseType>> getSpecialCases() {
        return game.getSpecialCases();
    }
}
