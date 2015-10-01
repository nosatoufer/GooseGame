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

    private Case m_board[] = new Case[63];
    private Vector<Player> m_players;
    private int m_currentPlayer;
    private boolean m_over;

    public Board(int nPlayers) {
        m_over = false;

        for (int i = 0; i < nPlayers; ++i) {
            m_players = new Vector<>();
        }
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
        m_board[52].setType(CaseType.JAIl);
        m_board[58].setType(CaseType.DEATH);

    }

    private void play(Dices dices) {
        if (dices != null) {
            Player p = m_players.get(m_currentPlayer);
            p.setDices(dices);
            p.setPosition(p.position() + dices.sum());
            Case c = m_board[p.position()];
            boolean goose = false;
            do {
                switch (c.type()) {
                    case GOOSE:
                        actionGoose();
                        goose = true;
                        break;
                    case JAIl:
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
                    default:;
                }
            } while (goose == true);
            m_currentPlayer = (m_currentPlayer + 1) % m_players.size();
        }
    }

    private void actionInnWell(int turns) {
        Player curP = m_players.get(m_currentPlayer);
        Player p = m_board[m_players.get(m_currentPlayer).position()].player();
        if (p != null) {
            p.setPosition(curP.lastPosition());
        }
        curP.setStuck(turns);
    }

    private void actionBridge() {
        m_players.get(m_currentPlayer).setPosition(12);
    }

    private void actionDeath() {
        m_players.get(m_currentPlayer).setPosition(0);
    }

    private void actionMaze() {
        m_players.get(m_currentPlayer).setPosition(30);
    }

    private void actionJail() {
        m_players.get(m_currentPlayer).setJail();
    }

    private void actionGoose() {
        Player p = m_players.get(m_currentPlayer);
        p.setPosition(p.position() + p.dices().sum());
    }
}
