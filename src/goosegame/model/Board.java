/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.model;

import java.util.Vector;

/**
 *
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
        for (int i = 9; i < 63; i += i) {
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
     */
    public void play() {
        Player p = m_players.get(m_currentPlayer);
        if (p.isJailed() || p.isStuck() != 0) {
            p.decStuck();
        } else {
            m_dices.roll();
            m_board[p.position()].setPlayer(null);
            p.setPosition(p.position() + m_dices.sum());
            Case c = m_board[p.position()];
            if (c.player() != null) {
                c.player().setPosition(p.lastPosition());
            }
            c.setPlayer(p);
            boolean goose = false;
            do {
                switch (c.type()) {
                    case GOOSE:
                        actionGoose();
                        goose = true;
                        break;
                    case JAIL:
                        actionJail();
                        break;
                    case INN:
                        actionInnWell(1);
                        break;
                    case WELL:
                        actionInnWell(2);
                        break;
                    case BRIDGE:
                        actionBridge();
                        break;
                    case MAZE:
                        actionMaze();
                        break;
                    case DEATH:
                        actionDeath();
                        break;
                    case END:
                        m_over = true;
                        break;
                    case EMPTY:
                        break;
                    default:
                }
            } while (goose);
        }
        m_currentPlayer = (m_currentPlayer + 1) % m_players.size();

    }

    /**
     * Manage de action Case or Well
     * @param turns the amount of turns lost
     */
    private void actionInnWell(int turns) {
        Player curP = m_players.get(m_currentPlayer);
        Player p = m_board[m_players.get(m_currentPlayer).position()].player();
        if (p != null) {
            p.setPosition(curP.lastPosition());
        }
        curP.setStuck(turns);
    }

    /**
     * Manage the Bridge
     */
    private void actionBridge() {
        m_players.get(m_currentPlayer).setPosition(12);
    }

    /**
     * Manage the Death case
     */
    private void actionDeath() {
        m_players.get(m_currentPlayer).setPosition(0);
    }

    /**
     * Manage the Maze case
     */
    private void actionMaze() {
        m_players.get(m_currentPlayer).setPosition(30);
    }

    /**
     * Manage the Jail case
     */
    private void actionJail() {
        m_players.get(m_currentPlayer).setJail();
    }

    /**
     * Manage Goose case
     */
    private void actionGoose() {
        Player p = m_players.get(m_currentPlayer);
        p.setPosition(m_dices.sum() + p.position());
    }

}
