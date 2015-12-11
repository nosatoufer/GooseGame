package heb.esi.goosegame.db;

/**
 * Exception lancée lors d'un probléme avec la base de donnée
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
