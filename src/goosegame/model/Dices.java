/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goosegame.model;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author nosa
 */
public class Dices {
    List<Dice> m_dices;
    
    public Dices(int nDice, int mRoll)
    {
        m_dices = new ArrayList<>();
        for ( int i = 0 ; i < nDice ; ++i)
        {
            m_dices.add(new Dice(mRoll));
        }
    }
    
    public int diceValue(int index)
    {
        return m_dices.get(index).value();
    }
    
    public int nDices()
    {
        return m_dices.size();
    }
    
    public int sum()
    {
        int sum = 0;
        
        for( Dice d : m_dices )
            sum = d.value();
        
        return sum;
    }
    
    public void roll()
    {
        for (Dice d : m_dices) {
	    d.roll();
	}
    }
}
