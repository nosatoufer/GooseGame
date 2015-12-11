package heb.esi.goosegame.model;

import java.util.Random;

/**
 * Represent a dice
 * @author nosa
 */
public class Dice {
    private int m_value;
    private final int m_maxValue;
    private final Random m_rand;
    
    /**
     *
     * @param maxValue
     */
    public Dice(int maxValue)
    {
        m_rand = new Random();
        m_value = 0;
        m_maxValue = maxValue;
    }
    
    /**
     * Roll the dice
     */
    public void roll()
    {
        m_value = m_rand.nextInt(m_maxValue - 1) + 1;
    }
    
    /**
     * Return the value of the dice
     * @return the value of the dice
     */
    public int value()
    {
        return m_value;
    }
}
