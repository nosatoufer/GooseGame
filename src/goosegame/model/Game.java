/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.model;

import java.util.ArrayList;

/**
 *
 * @author nosa
 */
public class Game {

    Board m_board;
    ArrayList<Player> m_players;
    int m_currentPlayer;
    GameState m_state;
    private final Dices m_dices;

    public Game() {
        m_currentPlayer = 0;
        m_board = new Board();
        m_players = new ArrayList<>();
        m_state = GameState.NOTSTARTED;
        m_dices = new Dices(2, 6);
    }

    public void addPlayer(Color col) throws GooseGameException {
        if (m_players.size() < 8) {
            m_players.add(new Player(m_players.size(), col));
        } else {
            throw new GooseGameException("Trop de joueur !");
        }
    }

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
                        //actionGoose(p);
                        goose = true;
                        break;
                    case JAIL:
                        //actionJail(p);
                        break;
                    case INN:
                        //actionInnWell(p, 1);
                        break;
                    case WELL:
                        //actionInnWell(p, 2);
                        break;
                    case BRIDGE:
                        //actionBridge(p);
                        break;
                    case MAZE:
                        //actionMaze(p);
                        break;
                    case DEATH:
                        //actionDeath(p);
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
        
        //m_board[p.position()].setPlayer(null);
        p.setPosition(pos);
        System.out.println("Player " + p.numPlayer() + " moved, position : " + p.position());
        Case c = m_board.getCase(p.position());
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
     * Roll the dice on the board
     *
     * @return the sum of the dices
     */
    public int rollDices() {
        m_dices.roll();
        return m_dices.sum();
    }
}
