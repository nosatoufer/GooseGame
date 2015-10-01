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
    private Case m_board[];
    private Vector<Player> m_players;
    private int m_currentPlayer;
    
    public Board(int nPlayers)
    {
        for(int i = 0 ; i < nPlayers ; ++i)
        {
            m_players = new Vector<>();
        }
    }
    
    private void actionInn()
    {
        Player curP = m_players.get(m_currentPlayer);
        Player p = m_board[m_players.get(m_currentPlayer).position()].player();
        if (p != null)
        {
            p.setPosition(curP.lastPosition());
        }
        curP.setStuck(2);
    }
}
