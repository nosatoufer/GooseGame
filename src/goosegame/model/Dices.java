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
public class Dices {
    private int m_one;
    private int m_two;
    
    public Dices(int one, int two)
    {
        m_one = one;
        m_two = two;
    }
    
    public int one()
    {
        return m_one;
    }
    public int two()
    {
        return m_two;
    }
    public int sum()
    {
        return m_one + m_two;
    }
}
