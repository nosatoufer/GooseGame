package goosegame.model;

/**
 * Represent the player with his color, number, position et status.
 * @author nosa
 */
public class Player {
    private final int m_numPlayer;
    private final Color m_color;
    private int m_position;
    private int m_lastPosition;
    private int m_stuck;
    private boolean m_jail;
    
    public Player(int num, Color color)
    {
        m_color = color;
        m_numPlayer = num;
        m_position = 0;
        m_lastPosition = 0;
        m_stuck = 0;
        m_jail = false;
    }
    
    /**
     * Return the current position of the player
     * @return the current position
     */
    public int position()
    {
        return m_position;
    }
    
    /**
     * Return the last position of the player
     * @return the last position
     */
    public int lastPosition()
    {
        return m_lastPosition;
    }
    
    /**
     * Return the turns left in the Inn or in the Well
     * @return the turns left stuck
     */
    public int isStuck()
    {
        return m_stuck;
    }
    
    /**
     * Return if the player is in jail or not
     * @return True if the player is stuck in jail.
     */
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
     * Return the player number
     * @return numPlayer the player number
     */
    public int numPlayer()
    {
        return m_numPlayer;
    }
    
    public String toString()
    {
        String s = m_color.toString();
        return s;
    }
}
