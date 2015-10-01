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
    
    public Board(int nPlayers)
    {
        for(int i = 0 ; i < nPlayers ; ++i)
        {
            m_players = new Vector<>();
        }
    }
}
