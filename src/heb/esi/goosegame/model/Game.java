package heb.esi.goosegame.model;


import heb.esi.goosegame.controler.Controler;
import java.util.ArrayList;

import javafx.util.Pair;

/**
 *
 * @author nosa
 */
public class Game {
    private final Controler controler;
    private final Board board;
    private final ArrayList<Player> players;
    private int currentPlayer;
    private GameState gameState;
    private final Dices dices;
    private boolean dicesRolled;

    public Game(Controler controler) {
        this.controler = controler;
        this.currentPlayer = 0;
        this.players = new ArrayList<>();
        this.board = new Board();
        this.gameState = GameState.NOTSTARTED;
        this.dices = new Dices(2, 6);
        this.dicesRolled = false;
    }

    public ArrayList<Pair<Integer, CaseType>> getSpecialCases() {
        return this.board.getSpecialCases();
    }
    
    public void addPlayer(PlayerColor col) throws GooseGameException {
        if (gameState != GameState.NOTSTARTED) {
            throw new GooseGameException("Création de joueur impossible, la partie a déjà commencée.");
        }
        if (players.size() > 8) {
            throw new GooseGameException("Création de joueur impossible, le nombre limite (8) a été atteint.");
        }
        if (!checkColor(col)) {
            throw new GooseGameException("Création de joueur impossible, un joueur de cette couleur existe déjà.");
        }
        players.add(new Player(players.size(), col));
    }

    /**
     * Move the player and manage the swap
     *
     * @param p the player to move
     * @param pos the position to give to the player
     * @return the case where the player landed
     */
    private Case movePlayer(Player p, int pos) {
        board.clearCase(p.position());
        p.setPosition(pos);
        Case c = board.getCase(p.position());
        if (pos != 0) {
            if (c.player() != null) {
                if (c.player().isJailed()) {
                    c.player().setJail();
                }
                movePlayer(c.player(), p.lastPosition());
            }
            c.setPlayer(p);
        }
        return c;
    }

    public void play(int position) throws GooseGameException {
        if (!dicesRolled) {
            throw new GooseGameException("Les dés n'ont pas été jetés!");
        }
        
        Player p = players.get(currentPlayer);
        
        Case c;
        
        int pos = p.position() + dices.sum();
        
        if (pos > 63) {
            pos = 63 - (pos - 63);
        }
        
        if (pos != position) {
            throw new GooseGameException("Déplacement non autorisé");
        }
        
        dicesRolled = false;
            
        if (p.position() == 0) {
            if (dices.diceValue(0) == 6 || dices.diceValue(1) == 6) {
                if (dices.diceValue(1) == 3 || dices.diceValue(0) == 3) {
                    pos = 26;
                }
            }
            if (dices.diceValue(0) == 4 || dices.diceValue(1) == 4) {
                if (dices.diceValue(1) == 5 || dices.diceValue(0) == 5) {
                    pos = 53;
                }
            }
        }/* else {
            if (pos > 63) {
                pos = 63 - (pos - 63);
            }
        }*/
        
        c = movePlayer(p, pos);
        
        if (c.type() != CaseType.END) { // Si on est pas arrivé à la fin, on regarde sur quelle case on est tombé
            
            p.setStuck(c.type().stuck()); // On coince le joueur pour x tours (en fonction de la case, les cases ne coincant pas le joueur laisse la valeur à 0)
            
            if (c.type() == CaseType.JAIL) { 
                p.setJail(); // S'il s'agit de la case prison on met le joueur en prison
            }
            
            if (c.type().move()) { // S'il s'agit d'une case qui déplace le joueur ...
                if (c.type().exit() != -1) { // On double son déplacement s'il s'agit d'une oie
                    movePlayer(p, c.type().exit());
                } else { // On déplace le joueur a destination si il s'agit d'une case prédéfinie
                    movePlayer(p, p.position() + dices.sum());
                }
            }

            // On cherche le joueur suivant (en passant ceux en prison ou bloqués)
            boolean nextPlayerFound = true;
            do {
                currentPlayer = (currentPlayer + 1) % players.size();
                p = players.get(currentPlayer);;
                if (p.isJailed() || p.isStuck() != 0) {
                    p.decStuck();
                    nextPlayerFound = false;
                } else {
                    nextPlayerFound = true;
                }
            } while (!nextPlayerFound);
            
        } else { // Si on est arrivé à la fin, c'est la fin du jeu
            gameState = GameState.OVER;
        }
            
        updateViews();
    }

    /**
     * Roll the dice on the board
     *
     * @throws heb.esi.goosegame.model.GooseGameException
     */
    public void rollDices() throws GooseGameException {
        if (gameState != GameState.STARTED) {
            throw new GooseGameException("La partie n'est pas en cours");
        }
        if (dicesRolled) {
            throw new GooseGameException("Les dés ont déjà été jetés.");
        }
        dices.roll();
        dicesRolled = true;
        
        updateViews();
    }

    private boolean checkColor(PlayerColor col) {
        boolean ok = true;
        int i = 0;
        while (ok && i < players.size()) {
            ok = players.get(i++).color() != col;
        }
        return ok;
    }

    public void start() throws GooseGameException {
        if (gameState != GameState.NOTSTARTED || players.size() < 2 || players.size() > 8) {
            throw new GooseGameException("Démarrage du jeu impossible");
        }
        gameState = GameState.STARTED;
        dicesRolled = false;
        
        updateViews();
    }
    
    public void checkPlayers() throws GooseGameException {
        if (players.size() < 2 || players.size() > 8) {
            throw new GooseGameException("Nombre de joueurs incorrects : entre 2 et 8 compris.");
        }
    }
    
    public int getDicesSum() {
        return dices.sum();
    }
    
    public ArrayList<Pair<PlayerColor, Integer>> getPlayerPos() {
        ArrayList<Pair<PlayerColor, Integer>> players = new ArrayList<>();
        
        for(int i=0; i<this.players.size(); i++) {
            players.add(new Pair<>(this.players.get(i).color(), this.players.get(i).position()));
        }
        
        return players;
    }
    
    public PlayerColor getCurrentPlayerColor() {
        return players.get(currentPlayer).color();
    }
    
    public GameState getGameState() {
        return gameState;
    }
    
    public void updateViews() {
        this.controler.updateViews();
    }
}
