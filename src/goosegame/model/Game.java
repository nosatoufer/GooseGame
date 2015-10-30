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

    private Board m_board;
    private ArrayList<Player> m_players;
    private int m_currentPlayer;
    private GameState m_state;
    private final Dices m_dices;
    private boolean m_dicesRolled;

    public Game() {
        m_currentPlayer = 0;
        m_players = new ArrayList<>();
        m_state = GameState.NOTSTARTED;
        m_dices = new Dices(2, 6);
        m_dicesRolled = false;
    }

    public void addPlayer(Color col) throws GooseGameException {
        if (m_state != GameState.NOTSTARTED || m_players.size() > 8 || !checkColor(col)) {
            throw new GooseGameException("Création de joueur impossible.");
        }
        m_players.add(new Player(m_players.size(), col));
    }

    /**
     * Move the player and manage the swap
     *
     * @param p the player to move
     * @param pos the position to give to the player
     * @return the case where the player landed
     */
    private Case movePlayer(Player p, int pos) {
        m_board.clearCase(p.position());
        p.setPosition(pos);
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

    public void play() throws GooseGameException {
        if (!m_dicesRolled) {
            throw new GooseGameException("Les dés n'ont pas été jetés!");
        }
        m_dicesRolled = false;
        Player p = m_players.get(m_currentPlayer);
        if (p.isJailed() || p.isStuck() != 0) {
            p.decStuck();
        } else {
            Case c;
            do {
                int pos = p.position() + m_dices.sum();
                if (p.position() == 0) {
                    if (m_dices.diceValue(0) == 6 || m_dices.diceValue(1) == 6) {
                        if (m_dices.diceValue(1) == 3 || m_dices.diceValue(0) == 3) {
                            pos = 26;
                        }
                    }
                    if (m_dices.diceValue(0) == 4 || m_dices.diceValue(1) == 4) {
                        if (m_dices.diceValue(1) == 5 || m_dices.diceValue(0) == 5) {
                            pos = 53;
                        }
                    }
                } else {
                    if (pos > 62) {
                        pos = pos - (pos - 62);
                    }
                }
                c = movePlayer(p, pos);

                p.setStuck(c.type().stuck());
                if (c.type() == CaseType.JAIL) {

                }
                if (c.type().move()) {
                    if (c.type().exit() != -1) {
                        movePlayer(p, c.type().exit());
                    } else {
                        movePlayer(p, p.position() + m_dices.sum());
                    }
                }
            } while (c.type() == CaseType.GOOSE);

            m_currentPlayer = (m_currentPlayer + 1) % m_players.size();

        }
    }

    /**
     * Roll the dice on the board
     *
     * @throws goosegame.model.GooseGameException
     */
    public void rollDices() throws GooseGameException {
        if (m_dicesRolled) {
            throw new GooseGameException("Les dés ont déjà été jetés.");
        }
        m_dices.roll();
        m_dicesRolled = true;
        //return m_dices.sum();
    }

    private boolean checkColor(Color col) {
        boolean ok = true;
        int i = 0;
        while (ok && i < m_players.size()) {
            ok = m_players.get(i++).color() != col;
        }
        return ok;
    }

    public void start() {
        m_state = GameState.STARTED;
        m_board = new Board();
        m_dicesRolled = false;
    }
}
