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
public enum CaseType
{
    START,
    EMPTY,
    GOOSE,
    JAIL,
    INN,
    WELL,
    BRIDGE,
    MAZE,
    DEATH,
    END;
    
    @Override
    public String toString() { return this.name().substring(0, 3); }
}
