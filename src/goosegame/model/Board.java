package goosegame.model;

import java.util.Vector;

/**
 * Represent the whole board with cases, player, dices.
 * @author nosa
 */
public class Board {

    private final Case m_board[] = new Case[63];
    private Vector<Player> m_players;
    private int m_currentPlayer;
    private boolean m_over;
    private final Dices m_dices;

    public Board(int nPlayers) {
        m_over = false;
        m_dices = new Dices(2, 6);
        m_currentPlayer = 0;
        m_players = new Vector<>();

        m_players.add(new Player(0, Color.GREEN));
        m_players.add(new Player(1, Color.BLACK));
        m_players.add(new Player(2, Color.BLUE));

        for (int i = 0; i < 63; ++i) {
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
            p.decStuck();
        } else {
            m_dices.roll();
            System.out.println("Dices rolled, values : " + m_dices.sum());

            Case c;

            boolean goose;
            do {
                c = movePlayer(p, p.position() + m_dices.sum());
                goose = false;
                System.out.println("Case action management");
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
                        m_over = true;
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
     * Move the player and manage the swap
     *
     * @param p
     * @param pos
     * @return
     */
    private Case movePlayer(Player p, int pos) {
        if (pos > 62) {
            pos = pos - (pos - 62);
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
     * @return true if one playser has reach the last case.
     */
    public boolean isOver() {
        return m_board[62].player() != null;
    }

    /**
     * Return a string displaying the board.
     * @return the board as a string
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < 63; ++i) {
            s += " ";
            if (m_board[i].player() != null) {
                s += m_board[i].player().toString();
            } else {
                s += "   ";
            }
            s += " ";
        }
        s += '\n';
        for (int i = 0; i < 63; ++i) {
            s += m_board[i].toString();
        }
        return s;
    }

}
