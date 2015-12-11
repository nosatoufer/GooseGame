package heb.esi.goosegame.model;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.db.DBException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;

/**
 *
 * @author nosa
 */
public class Game {

    private final Controller controller; // Controleur
    private String name;
    private final Board board; // Plateau du jeu
    private final ArrayList<Player> players; // Liste des joueurs
    private int currentPlayer; // Indice du joueur actuel
    private GameState gameState; // Etat de la partie
    private final Dices dices; // Classe représentant les dés
    private boolean dicesRolled; // Booléen indiquant si les dés ont été jeté (pour
    // déterminer si le joueur actif doit lancer les dés ou se déplacer).
    private int activePlayerNextPos; // Entier indiquant la case sur laquelle va
    // tomber le joueur actif (mise à jour après le lancé de dés).

    /**
     *
     * @param controler
     */
    public Game(Controller controler) {
        this.name = null;
        this.controller = controler;
        this.currentPlayer = 0;
        this.players = new ArrayList<>();
        this.board = new Board();
        this.gameState = GameState.NOTSTARTED;
        this.dices = new Dices(2, 6);
        this.dicesRolled = false;
        this.activePlayerNextPos = -1;
    }
    
    /**
     *
     * @param controler
     * @param name
     */
    public Game(Controller controler, String name) {
        this.name = name;
        this.controller = controler;
        this.currentPlayer = 0;
        this.players = new ArrayList<>();
        this.board = new Board();
        this.gameState = GameState.NOTSTARTED;
        this.dices = new Dices(2, 6);
        this.dicesRolled = false;
        this.activePlayerNextPos = -1;
    }

    /**
     * Retourne une liste des indices des cases spéciales avec leur type.
     *
     * @return liste des indindices des cases spéciales avec leur type.
     */
    public ArrayList<Pair<Integer, CaseType>> getSpecialCases() {
        return this.board.getSpecialCases();
    }

    /**
     * Ajoute un joueur de la couleur passée en paramètre à la partie.
     *
     * @param name
     * @param color
     * @throws heb.esi.goosegame.model.GooseGameException
     */
    public void addPlayer(String name, String color) throws GooseGameException {
        if (this.gameState != GameState.STARTED && this.gameState != GameState.NOTSTARTED) {
            throw new GooseGameException("Création de joueur impossible, la partie a déjà commencée.");
        }
        if (this.players.size() > 8) {
            throw new GooseGameException("Création de joueur impossible, le nombre limite (8) a été atteint.");
        }
        if (!this.checkPlayer(name, color)) {
            throw new GooseGameException("Création de joueur impossible, un joueur de cette couleur ou de ce nom existe déjà dans la partie.");
        }
        this.players.add(new Player(name, color));
    }

    /**
     * Move the player and manage the swap
     *
     * @param p the player to move
     * @param pos the position to give to the player
     * @return the case where the player landed
     */
    private Case movePlayer(Player p, int pos) {
        this.board.clearCase(p.position());
        p.setPosition(pos);
        Case c = this.board.getCase(p.position());
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

    /**
     * Déplacer le joueur actif en le déplacant à la position passée en
     * paramètre (normalement la même que this.activePlayerNextPos calculé après
     * le lancé de dés).
     *
     * @param position
     * @throws heb.esi.goosegame.model.GooseGameException
     */
    public void play(int position) throws GooseGameException {
        // Il faut que les dés aient bien été jetés
        if (!this.dicesRolled) {
            throw new GooseGameException("Les dés n'ont pas été jetés!");
        }
        // Et que la position passée en paramètre soit bien celle autorisée
        if (position != this.activePlayerNextPos) {
            throw new GooseGameException("Déplacement non autorisé");
        }

        Player p = this.players.get(this.currentPlayer);

        // On change la variable pour indiquer que le joueur suivant peut lancer
        // les dés.
        this.dicesRolled = false;

        // On récupère la case sur laquelle le joueur est arrivé
        Case c = movePlayer(p, position);

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
                this.currentPlayer = (this.currentPlayer + 1) % this.players.size();
                p = this.players.get(this.currentPlayer);;
                if (p.isJailed() || p.isStuck() != 0) {
                    p.decStuck();
                    nextPlayerFound = false;
                } else {
                    nextPlayerFound = true;
                }
            } while (!nextPlayerFound);

        } else { // Si on est arrivé à la fin, c'est la fin du jeu
            this.gameState = GameState.OVER;
            try {
                this.controller.updateGame();
            } catch (DBException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        updateViews();
    }

    /**
     * Roll the dice on the board
     *
     * @throws heb.esi.goosegame.model.GooseGameException
     */
    public void rollDices() throws GooseGameException {
        if (this.gameState != GameState.STARTED) {
            throw new GooseGameException("La partie n'est pas en cours");
        }
        if (this.dicesRolled) {
            throw new GooseGameException("Les dés ont déjà été jetés.");
        }
        this.dices.roll();
        this.dicesRolled = true;

        // On calcule la prochaine position du joueur actif et on la stocke dans
        // activePlayerNextPos
        Player p = this.players.get(currentPlayer);

        this.activePlayerNextPos = p.position() + this.dices.sum();

        if (p.position() == 0) {
            if (dices.diceValue(0) == 6 || dices.diceValue(1) == 6) {
                if (dices.diceValue(0) == 3 || dices.diceValue(1) == 3) {
                    this.activePlayerNextPos = 26;
                }
            }
            if (dices.diceValue(0) == 4 || dices.diceValue(1) == 4) {
                if (dices.diceValue(0) == 5 || dices.diceValue(1) == 5) {
                    this.activePlayerNextPos = 53;
                }
            }
        }

        if (this.activePlayerNextPos > 63) {
            this.activePlayerNextPos = 63 - (this.activePlayerNextPos - 63);
        }

        updateViews();
    }

    /**
     * Retourne la position de la case suivante sur lequel le joueur actif doit
     * se déplacer
     *
     * @return prochaine position du joueur actif
     * @throws heb.esi.goosegame.model.GooseGameException
     */
    public int getNextCaseToMove() throws GooseGameException {
        if (this.gameState != GameState.STARTED || (!this.dicesRolled)) {
            throw new GooseGameException("Erreur de logique dans l'appel de la méthode getNextCaseToMove()");
        }
        return this.activePlayerNextPos;
    }

    /**
     * Vérifie si un joueur de la même couleur est déjà inscrit dans la partie.
     */
    private boolean checkPlayer(String name, String color) {
        boolean ok = true;
        int i = 0;
        while (ok && i < this.players.size()) {
            ok = !this.players.get(i).getColor().equals(color);
            if (ok) {
                ok = !this.players.get(i).getName().equals(name);
            }
            i++;
        }
        return ok;
    }

    /**
     * Début la partie.
     *
     * @throws heb.esi.goosegame.model.GooseGameException
     */
    public void start() throws GooseGameException {
        // Si elle est dans un autre état que "NOTSTARTED" ou que le nombre de joueur
        // n'est pas correct, on lève une exception
        if (this.gameState != GameState.NOTSTARTED || this.players.size() < 2 || this.players.size() > 8) {
            throw new GooseGameException("Démarrage du jeu impossible");
        }
        // Sinon, on change l'état de la partie
        this.gameState = GameState.STARTED;
        // On indique que les dés n'ont pas encore été lancé
        this.dicesRolled = false;
        // On met à jour les vues
        updateViews();
    }

    /**
     * Vérifie que le nombre de joueur soit correct.
     *
     * @throws heb.esi.goosegame.model.GooseGameException
     */
    public void checkPlayers() throws GooseGameException {
        if (this.players.size() < 2 || this.players.size() > 8) {
            throw new GooseGameException("Nombre de joueurs incorrects : entre 2 et 8 compris.");
        }
    }

    /**
     * Retourne la somme des dés.
     *
     * @return somme des deux dés
     */
    public int getDicesSum() {
        return this.dices.sum();
    }

    /**
     * Retourne la liste des couleurs des joueurs associés à leur position.
     * @return 
     */
    public ArrayList<Pair<String, Integer>> getPlayerPos() {
        ArrayList<Pair<String, Integer>> players = new ArrayList<>();

        for (Player player : this.players) {
            players.add(new Pair<>(player.getColor(), player.position()));
        }

        return players;
    }
    
    /**
     * Retourne la liste des couleurs des joueurs associés à leur position.
     * @return 
     */
    public ArrayList<Pair<String, Pair<String, Integer>>> getPlayers() {
        ArrayList<Pair<String, Pair<String, Integer>>> players = new ArrayList<>();

        for (Player player : this.players) {
            players.add(new Pair<>(player.getName(), new Pair<>(player.getColor(), player.position())));
        }

        return players;
    }
    
    /**
     *
     * @param name
     * @param pos
     */
    public void setPlayerPosition(String name, int pos) {
        for (Player player : this.players) {
            if (player.getName().equals(name)) {
                player.setPosition(pos);
                break;
            }
        }
    }
    
    /**
     *
     * @param nb
     */
    public void setCurrentPlayer(int nb) {
        if (nb < this.players.size()) {
            this.currentPlayer = nb;
            this.gameState = GameState.STARTED;
        }
    }

    /**
     * Retourne la couleur du joueur actuel.
     *
     * @return couleur du joueur actif
     */
    public Pair<String, String> getCurrentPlayer() {
        
        return new Pair<>(this.players.get(this.currentPlayer).getName(), this.players.get(this.currentPlayer).getColor());
    }
    
    /**
     * Retourne la couleur du joueur actuel.
     *
     * @return couleur du joueur actif
     */
    public int getCurrentPlayerId() {
        
        return this.currentPlayer;
    }

    /**
     * Retourne l'état actuel de la partie.
     *
     * @return état de la partie
     */
    public GameState getGameState() {
        return this.gameState;
    }
    
    /**
     * Retourne l'état actuel de la partie.
     *
     * @return état de la partie
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Retourne l'état actuel de la partie.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Met à joueur les vues du controleur.
     */
    public void updateViews() {
        this.controller.updateViews();
    }
}
