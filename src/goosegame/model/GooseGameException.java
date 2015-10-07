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
public class GooseGameException extends Exception {

    /**
     * Creates a new instance of <code>GooseGameException</code> without detail
     * message.
     */
    public GooseGameException() {
    }

    /**
     * Constructs an instance of <code>GooseGameException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public GooseGameException(String msg) {
        super(msg);
    }
}
