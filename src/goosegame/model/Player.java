/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.model;

/**
 *
 * @author nosa
 */
public class Player {
    private final int m_numPlayer;
    private int m_position;
    private int m_lastPosition;
    private Dices m_dices;
    private int m_stuck;
    private boolean m_jail;
    
    public Player(int num)
    {
        m_numPlayer = num;
        m_position = 0;
        m_lastPosition = 0;
        m_stuck = 0;
        m_jail = false;
        m_dices = null;
    }
    
    
    public int position()
    {
        return m_position;
    }
    
    public int lastPosition()
    {
        return m_lastPosition;
    }
    
    public int isStuck()
    {
        return m_stuck;
    }
    
    public boolean isJailed()
    {
        return m_jail;
    }
    
    /**
     * Change the current and last position.
     * @param pos 
     */
    public void setPosition(int pos)
    {
        m_lastPosition = m_position;
        m_position = pos;
    }
    
    /**
     * Set the m_stuck var to the number of lost turns.
     * @param turns 
     */
    public void setStuck(int turns)
    {
        m_stuck = turns;
    }
    
    /**
     * Reduce the turn stuck left.
     */
    public void decStuck()
    {
        if (m_stuck > 0)
            --m_stuck;
    }
    
    /**
     * Toggle the m_jail state.
     */
    public void setJail()
    {
        m_jail = !m_jail;
    }
    
    /**
     * 
     * @param dices
     */
    public void setDices(Dices dices)
    {
        m_dices = dices;
    }
    
    public Dices dices()
    {
        return m_dices;
    }
}
