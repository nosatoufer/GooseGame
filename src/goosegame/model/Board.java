package goosegame.model;

import java.util.Vector;

/**
 * Represent the whole board with cases, player, dices.
 *
 * @author nosa
 */
public class Board {

    private final Case m_board[] = new Case[64];
    private Vector<Player> m_players;
    private int m_currentPlayer;
    private final Dices m_dices;

    public Board(int nPlayers) throws GooseGameException {
        if (nPlayers < 1 || nPlayers > 8) {
            throw new GooseGameException("Invalid number of players");
        }

        m_dices = new Dices(2, 6); // On joue avec 2 dés à 6 faces
        m_currentPlayer = 0;
        m_players = new Vector<>();

        for (int i = 0; i < nPlayers; ++i) {
            switch (i) {
                case 0:
                    m_players.add(new Player(i, Color.GREEN));

                    break;
                case 1:
                    m_players.add(new Player(i, Color.PINK));

                    break;
                case 2:
                    m_players.add(new Player(i, Color.BLUE));

                    break;
                case 3:
                    m_players.add(new Player(i, Color.YELLOW));
                    break;
                case 4:
                    m_players.add(new Player(i, Color.PURPLE));
                    break;
                case 5:
                    m_players.add(new Player(i, Color.RED));

                    break;
                case 6:
                    m_players.add(new Player(i, Color.BLACK));
                    break;
                case 7:
                    m_players.add(new Player(i, Color.WHITE));
                    break;
            }
        }

        for (int i = 0; i < 64; ++i) {
            m_board[i] = new Case();
        }
        for (int i = 9; i < 61; i += i) {
            m_board[i].setType(CaseType.GOOSE);
        }
        m_board[6].setType(CaseType.BRIDGE);
        m_board[19].setType(CaseType.INN);
        m_board[31].setType(CaseType.WELL);
        m_board[42].setType(CaseType.MAZE);
        m_board[52].setType(CaseType.JAIL);
        m_board[58].setType(CaseType.DEATH);
    }

    /**
     * Manage a turn of the game
     *
     * @throws goosegame.model.GooseGameException
     */
    public void play() throws GooseGameException {
        Player p = m_players.get(m_currentPlayer);
        if (p.isJailed() || p.isStuck() != 0) {
            System.out.println("Player " + p.numPlayer() + " is stuck.");
            p.decStuck();
        } else {
            Case c;

            boolean goose;
            do {
                c = movePlayer(p, p.position() + m_dices.sum());
                goose = false;
                switch (c.type()) {
                    case GOOSE:
                        actionGoose(p);
                        goose = true;
                        break;
                    case JAIL:
                        actionJail(p);
                        break;
                    case INN:
                        actionInnWell(p, 1);
                        break;
                    case WELL:
                        actionInnWell(p, 2);
                        break;
                    case BRIDGE:
                        actionBridge(p);
                        break;
                    case MAZE:
                        actionMaze(p);
                        break;
                    case DEATH:
                        actionDeath(p);
                        break;
                    case END:
                        break;
                    case EMPTY:
                        break;
                    default:
                        throw new GooseGameException("Case with no defined type");

                }
            } while (goose);
        }
        m_currentPlayer = (m_currentPlayer + 1) % m_players.size();

    }

    /**
     * Roll the dice on the board
     *
     * @return the sum of the dices
     */
    public int rollDices() {
        m_dices.roll();
        return m_dices.sum();
    }

    /**
     * Move the player and manage the swap
     *
     * @param p the player to move
     * @param pos the position to give to the player
     * @return the case where the player landed
     */
    private Case movePlayer(Player p, int pos) {
        if (pos > 62) {
            pos = pos - (pos - 62);
        }
        if (m_dices.diceValue(0) == 6 || m_dices.diceValue(1) == 6) {
            if (m_dices.diceValue(1) == 3 || m_dices.diceValue(0) == 3) {
                System.out.println("You rolled 6 and 3.");
                pos = 26;
            }
        }
        if (m_dices.diceValue(0) == 4 || m_dices.diceValue(1) == 4) {
            if (m_dices.diceValue(1) == 5 || m_dices.diceValue(0) == 5) {
                System.out.println("You rolled 5 and 4.");
                pos = 53;
            }
        }

        m_board[p.position()].setPlayer(null);
        p.setPosition(pos);
        System.out.println("Player " + p.numPlayer() + " moved, position : " + p.position());
        Case c = m_board[p.position()];
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
     * Manage de action Case or Well
     *
     * @param turns the amount of turns lost
     */
    private void actionInnWell(Player p, int turns) {
        System.out.println("Vous êtes bloqué pour les " + turns + " tours.");
        p.setStuck(turns);
    }

    /**
     * Manage the Bridge
     */
    private void actionBridge(Player p) {
        System.out.println("Vous prenez le pont et allez directement sur la"
                + " case 20");
        movePlayer(p, 20);
    }

    /**
     * Manage the Death case
     */
    private void actionDeath(Player p) {
        System.out.println("Vous êtes cuit ! Retournez à la case départ.");
        movePlayer(p, 0);
    }

    /**
     * Manage the Maze case
     */
    private void actionMaze(Player p) {
        System.out.println("Vous êtes entré dans le labyrinthe et "
                + "resortez à la case 30.");
        movePlayer(p, 30);
    }

    /**
     * Manage the Jail case
     */
    private void actionJail(Player p) {
        System.out.println("Vous êtes en prison, vous devez attendre que "
                + "quelqu'un vous sauve.");
        p.setJail();
    }

    /**
     * Manage Goose case
     */
    private void actionGoose(Player p) {
        System.out.println("Vous êtes tombé sur une oie.");
        //movePlayer(p, p.position() + m_dices.sum());
    }

    /**
     * Return true if the game is over
     *
     * @return true if one player has reach the last case.
     */
    public boolean isOver() {
        return m_board[63].player() != null;
    }

    /**
     * Return a string displaying the board.
     *
     * @return the board as a string
     */
    public String toString() {
        String s = "";
        for (int i = 1; i < 64; ++i) {
            s += " ";
            if (m_board[i].player() != null) {
                s += m_board[i].player().toString();
            } else {
                s += "   ";
            }
            s += " ";
        }
        s += '\n';
        for (int i = 1; i < 64; ++i) {
            s += m_board[i].toString();
        }
        return s;
    }
}
