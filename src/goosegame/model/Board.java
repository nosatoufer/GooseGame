package goosegame.model;

import java.util.Vector;

/**
 * Represent the whole board with cases, player, dices.
 *
 * @author nosa
 */
public class Board {

    private final Case m_board[] = new Case[64];

    public Board(){
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
     * Returns a copy of the case at the given position
     * @param index the position of the case
     * @return a copy of the case
     */
    public Case getCase(int index)
    {
        return new Case(m_board[index]);
    }
    /**
     * Return a string displaying the board.
     *
     * @return the board as a string
     */
    @Override
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
