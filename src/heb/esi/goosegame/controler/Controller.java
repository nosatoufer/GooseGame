package heb.esi.goosegame.controler;

import heb.esi.goosegame.business.GooseGameFacade;
import heb.esi.goosegame.db.DBException;
import heb.esi.goosegame.dto.GameDto;
import heb.esi.goosegame.dto.PlayerInGameDto;
import heb.esi.goosegame.model.CaseType;
import heb.esi.goosegame.model.Game;
import heb.esi.goosegame.model.GameState;
import heb.esi.goosegame.model.GooseGameException;
import heb.esi.goosegame.view.View;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
 *
 * @author nosa
 */
public class Controller {
    
    private Game game;

    private final ArrayList<View> views;
    
    /**
     *
     */
    public Controller ()
    {
        this.views = new ArrayList<>();
    }
    
    /**
     *
     * @param view
     */
    public void attachView(View view) {
        this.views.add(view);
    }
    
    /**
     *
     */
    public void updateViews() {
        for(int i=0; i<views.size(); i++) {
            views.get(i).refresh();
        }
    }
    
    /**
     *
     */
    public void newGame() {
        this.game = new Game(this);
    }
    
    /**
     *
     * @param name
     * @param c
     * @throws GooseGameException
     * @throws DBException
     */
    public void newPlayer(String name, Color c) throws GooseGameException, DBException
    {
        GooseGameFacade.createPlayer(name);
        this.game.addPlayer(name, c.toString());
    }
    
    /**
     *
     * @param pos
     * @throws GooseGameException
     */
    public void movePlayerToPos(int pos) throws GooseGameException
    {
        this.game.play(pos);
    }
    
    /**
     *
     * @throws GooseGameException
     */
    public void checkPlayers() throws GooseGameException
    {
        this.game.checkPlayers();
    }
    
    /**
     *
     * @throws GooseGameException
     */
    public void rollDice() throws GooseGameException
    {
        this.game.rollDices();
    }
    
    /**
     *
     * @throws GooseGameException
     */
    public void startGame() throws GooseGameException
    {
        this.game.start();
    }
    
    /**
     *
     * @return
     * @throws GooseGameException
     */
    public int getNextCaseToMove() throws GooseGameException
    {
        return this.game.getNextCaseToMove();
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Pair<Color, Integer>> getPlayerPos() {
        
        ArrayList<Pair<Color, Integer>> convertedPlayers = new ArrayList<>();
        ArrayList<Pair<String, Integer>> players = this.game.getPlayerPos();

        for (Pair<String, Integer> player : players) {
            convertedPlayers.add(new Pair<>(Color.valueOf(player.getKey()), player.getValue()));
        }
        
        return convertedPlayers;
    }
    
    /**
     *
     * @return
     */
    public boolean isGameStarted() {
        return (this.game.getGameState() == GameState.STARTED);
    }
    
    /**
     *
     * @return
     */
    public boolean isGameOver() {
        if (game.getGameState() == GameState.OVER) {
            
        }
        return (game.getGameState() == GameState.OVER);
    }
    
    /**
     *
     * @return
     */
    public int getDicesSum() {
        return game.getDicesSum();
    }
    
    /**
     *
     * @return
     */
    public Pair<String,Color> getCurrentPlayer() {
        Pair<String,String> gamePlayer = game.getCurrentPlayer();
        Pair<String,Color> player = new Pair<>(gamePlayer.getKey(), Color.valueOf(gamePlayer.getValue()));
        
        return player;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Pair<Integer, CaseType>> getSpecialCases() {
        return game.getSpecialCases();
    }
    
    /**
     * On récupère les noms des parties sauvegardées
     * @return
     * @throws DBException
     */
    public ArrayList<String> getSavedGames() throws DBException
    {
        return GooseGameFacade.getSavedGames();
    }
    
    /**
     * Permet de charger une partie avec ses joueurs depuis la base de donnée
     * @param name
     * @throws DBException
     * @throws GooseGameException
     */
    public void loadGame(String name) throws DBException, GooseGameException {
        try {
            // On récupère une partie
            GameDto game = GooseGameFacade.getSavedGame(name);
            // On la crée dans le controleur
            this.game = new Game(this, game.getName());
            // On récupère les associations des joueurs avec cette partoe
            ArrayList<PlayerInGameDto> players = GooseGameFacade.getAllPlayerFromGame(name);
            for(PlayerInGameDto player : players) {
                // On les ajoute dans le jeu et on les place au bon endroit
                this.game.addPlayer(player.getPlayerName(), player.getPlayerColor());
                this.game.setPlayerPosition(player.getPlayerName(), player.getPosition());
            }
            // On donne le tour au bout joueur
            this.game.setCurrentPlayer(game.getCurrentPlayer());
        } catch (DBException ex) {
            throw ex;
        }
        // On met à jour la vue
        updateViews();
    }
    
    /**
     * Permet de sauvegarder une partie et ses joueurs dans la base de donnée
     * @throws DBException
     */
    public void saveGame() throws DBException, GooseGameException {
        if (this.game.getName() != null) {
            GooseGameFacade.saveGame(new GameDto(this.game.getName(), this.game.getCurrentPlayerId(), this.isGameOver()));
            ArrayList<Pair<String, Pair<String, Integer>>> players = game.getPlayers();
            int i=0;
            for (Pair<String, Pair<String, Integer>> player : players) {
                GooseGameFacade.savePlayerInGame(new PlayerInGameDto(player.getKey(), this.game.getName(), player.getValue().getKey(), i, player.getValue().getValue()));
                i++;
            }
        } else {
            throw new GooseGameException("Le partie que vous essayez de sauvegarder ne possède pas de nom.");
        }
    }
    
    /**
     * Permet de mettre à jour une partie et ses joueurs dans la base de donnée
     * @throws DBException
     */
    public void updateGame() throws DBException {
        GooseGameFacade.updateGame(new GameDto(this.game.getName(), this.game.getCurrentPlayerId(), this.isGameOver()));
        ArrayList<Pair<String, Pair<String, Integer>>> players = game.getPlayers();
        int i=0;
        for (Pair<String, Pair<String, Integer>> player : players) {
            // Suppression puis création pour gérer le cas des nouveaux joueurs ajoutés au cours de partie
            GooseGameFacade.deletePlayerInGame(new PlayerInGameDto(player.getKey(), this.game.getName(), player.getValue().getKey(), i, player.getValue().getValue()));
            GooseGameFacade.savePlayerInGame(new PlayerInGameDto(player.getKey(), this.game.getName(), player.getValue().getKey(), i, player.getValue().getValue()));
            i++;
        }
    }
    
    /**
     * Permet de vérifier si un nom de partie existe déjà ou pas
     * @param name
     * @return
     * @throws DBException
     */
    public boolean checkGameName(String name) throws DBException {
        if (!GooseGameFacade.checkGameName(name)) {
            // Si le nom de partie n'existe pas, on l'associe à la partie actuelle
            // (on sous entend que cette action vise à sauvegarder la partie par la suite,
            // il faut donc attribuer son nom à la partie).
            this.game.setName(name);
        }
        return GooseGameFacade.checkGameName(name);
    }
    
    /**
     *
     * @return
     */
    public String getGameName() {
        return game.getName();
    }
}
