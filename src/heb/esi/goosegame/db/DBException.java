/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.db;

/**
 * Exception lancée lors d'un probléme d'accès au gestionnaire de persistance
 * @author HONEY
 */
public class DBException extends Exception{
    
     /**
     * Creates a new instance of <code>PenduDbException</code> without detail message.
     */
    public DBException() {
    }


    /**
     * Constructs an instance of <code>PenduDbException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DBException(String msg) {
        super(msg);
    }
    
}
