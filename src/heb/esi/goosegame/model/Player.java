package heb.esi.goosegame.model;

/**
 * Represent the player with his color, number, position et status.
 * 
 * @author nosa
 */
public class Player {
    private final int numPlayer;
    private final PlayerColor color;
    private int position;
    private int lastPosition;
    private int stuck;
    private boolean jail;
    
    public Player(int num, PlayerColor color)
    {
        this.color = color;
        this.numPlayer = num;
        this.position = 0;
        this.lastPosition = 0;
        this.stuck = 0;
        this.jail = false;
    }
    
    /**
     * Return the current position of the player
     * @return the current position
     */
    public int position()
    {
        return this.position;
    }
    
    /**
     * Return the last position of the player
     * @return the last position
     */
    public int lastPosition()
    {
        return this.lastPosition;
    }
    
    /**
     * Return the turns left in the Inn or in the Well
     * @return the turns left stuck
     */
    public int isStuck()
    {
        return this.stuck;
    }
    
    /**
     * Return if the player is in jail or not
     * @return True if the player is stuck in jail.
     */
    public boolean isJailed()
    {
        return this.jail;
    }
    
    /**
     * Change the current and last position.
     * @param pos 
     */
    public void setPosition(int pos)
    {
        this.lastPosition = this.position;
        this.position = pos;
    }
    
    /**
     * Set the m_stuck var to the number of lost turns.
     * @param turns 
     */
    public void setStuck(int turns)
    {
        this.stuck = turns;
    }
    
    /**
     * Reduce the turn stuck left.
     */
    public void decStuck()
    {
        if (this.stuck > 0)
            --this.stuck;
        
    }
    
    /**
     * Toggle the m_jail state.
     */
    public void setJail()
    {
        this.jail = !this.jail;
    }
    
    /**
     * Return the player number
     * @return numPlayer the player number
     */
    public int numPlayer()
    {
        return this.numPlayer;
    }
    public PlayerColor color()
    {
        return this.color;
    }
    
    @Override
    public String toString()
    {
        String s = this.color.toString();
        return s;
    }
}
