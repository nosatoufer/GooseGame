package heb.esi.goosegame.db;

/**
 * Exception lancée lors d'un probléme avec la base de donnée
 * @author HONEY
 */
public class DBException extends Exception{
    
     /**
     * Creates a new instance of DBException without detail message.
     */
    public DBException() {
    }

    /**
     * Constructs an instance of DBException with the specified detail message.
     * @param msg the detail message.
     */
    public DBException(String msg) {
        super(msg);
    }
    
}
