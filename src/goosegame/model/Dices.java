package goosegame.model;

import java.util.ArrayList;
import java.util.List;
/**
 * The object containing all the dices
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
    
    /**
     * Return the value of the dice number given in param.
     * @param index the number of the dice
     * @return the value of the dice
     */
    public int diceValue(int index)
    {
        return m_dices.get(index).value();
    }
    
    /**
     * Return the amout of dice
     * @return the amout of dice
     */
    public int nDices()
    {
        return m_dices.size();
    }
    
    /**
     * Return the sum of all dices
     * @return the sum of all dices
     */
    public int sum()
    {
        int sum = 0;
        
        for( Dice d : m_dices )
            sum = d.value();
        
        return sum;
    }
    
    /**
     * Roll all the dices
     */
    public void roll()
    {
        for (Dice d : m_dices) {
	    d.roll();
	}
    }
}
