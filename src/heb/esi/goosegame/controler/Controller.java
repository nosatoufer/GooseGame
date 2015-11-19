package heb.esi.goosegame.controler;

import heb.esi.goosegame.model.CaseType;
import heb.esi.goosegame.model.Game;
import heb.esi.goosegame.model.GameState;
import heb.esi.goosegame.model.PlayerColor;
import heb.esi.goosegame.model.GooseGameException;
import heb.esi.goosegame.view.View;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author nosa
 */
public class Controller {
    
    private Game game;

    private final ArrayList<View> views;
    
    public Controller ()
    {
        this.views = new ArrayList<>();
    }
    
    public void attachView(View view) {
        this.views.add(view);
    }
    
    public void updateViews() {
        for(int i=0; i<views.size(); i++) {
            views.get(i).refresh();
        }
    }
    
    public void newGame() {
        this.game = new Game(this);
    }
    
    public void newPlayer(PlayerColor c) throws GooseGameException
    {
        this.game.addPlayer(c);
    }
    
    public void movePlayerToPos(int pos) throws GooseGameException
    {
        this.game.play(pos);
    }
    
    public void checkPlayers() throws GooseGameException
    {
        this.game.checkPlayers();
    }
    
    public void rollDice() throws GooseGameException
    {
        this.game.rollDices();
    }
    
    public void startGame() throws GooseGameException
    {
        this.game.start();
    }
    
    public int getNextCaseToMove() throws GooseGameException
    {
        return this.game.getNextCaseToMove();
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
