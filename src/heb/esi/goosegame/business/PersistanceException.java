/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.business;

/**
 *Exception générée lorsque la couche de persistance généère une Exception
 * @author HONEY
 */
public class PersistanceException extends Exception{
    
    /**
     * Creates a new instance of
     * <code>PersistanceException</code> without detail message.
     */
    public PersistanceException() {
    }

    /**
     * Constructs an instance of
     * <code>PersistanceException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PersistanceException(String msg) {
        super(msg);
    }
    
}
