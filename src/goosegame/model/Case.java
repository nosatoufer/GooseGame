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
public class Case {
    private Player m_player;
    final private CaseType m_type;
    
    
    public Case()
    {
        m_player = null;
        m_type = null;
    }
    
    public Case(CaseType type)
    {
        m_player = null;
        m_type = type;
    }
    
    public void setPlayer(Player p)
    {
        m_player = p;
    }
    
    public Player player()
    {
        return m_player;
    }
    
    public CaseType type()
    {
        return m_type;
    }   
}