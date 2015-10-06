/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.model;

import java.util.Random;

/**
 *
 * @author nosa
 */
public class Dice {
    private int m_value;
    private final int m_mRoll;
    private final Random m_rand;
    
    public Dice(int mRoll)
    {
        m_rand = new Random();
        m_value = 0;
        m_mRoll = mRoll - 1;
    }
    
    public void roll()
    {
        m_value = m_rand.nextInt(m_mRoll) + 1;
    }
    
    public int value()
    {
        return m_value;
    }
}
