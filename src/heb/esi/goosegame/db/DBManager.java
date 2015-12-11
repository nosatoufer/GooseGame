package heb.esi.goosegame.db;

import java.sql.*;

/**
 * Offre les outils de connexion et de gestion de transaction.
 *
 * @author HONEY
 */
public class DBManager {

    private static Connection connection;
    private static DBInfo db;

    /**
     * Cree une connexion a partir des elements donnes
     *
     * @throws DBException si le driver n'est pas
     * trouve ou que la connexion n'a pu etre etablie
     */
    private static void setConnection() throws DBException {
        try {
            db = DBInfo.JAVADB;
            
            connection = DriverManager.getConnection(db.getUrl(), db.getUid(), db.getPsw());

            System.out.flush();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            throw new DBException("Probleme de connexion. " + ex.getMessage());
        }
    }
    
    /**
     * Retourne la connexion etablie ou a defaut, l'etablit
     *
     * @return la connexion etablie.
     * @throws heb.esi.goosegame.db.DBException
     */
    public static Connection getConnection() throws DBException {
        if (connection == null) {
            setConnection();
        }
        return connection;
    }
    
    /**
     * debute une transaction
     *
     * @throws heb.esi.goosegame.db.DBException
     */
    public static void startTransaction() throws DBException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DBException("Impossible de demarrer une transaction");
        }
    }
    
    /**
     * valide la transaction courante
     * @throws heb.esi.goosegame.db.DBException
     */
    public static void valideTransaction() throws  DBException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new DBException("Impossible de valider la transaction");
        }
    }
    
    /**
     * annule la transaction courante
     * @throws heb.esi.goosegame.db.DBException
     */
    public static void annuleTransaction() throws  DBException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new DBException("Impossible d'annuler la transaction");
        }
    }

}
