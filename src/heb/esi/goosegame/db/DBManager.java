/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.db;

import java.sql.*;

/**
 * Offre les outils de connexion et de gestion de transaction.
 *
 * @author HONEY
 */
public class DBManager {

    private static Connection connection;
    private static MesParametresDeConnexion dbChoisie;

    /**
     * Cree une connexion a partir des elements donnes
     *
     * @throws DBException <ul><li>si le driver n'est pas
     * trouve</li><li>si la connexion n'a pu etre etablie</li><ul>
     */
    private static void setConnection() throws DBException {
        try {
            dbChoisie = MesParametresDeConnexion.JAVADB;
            connection = DriverManager.getConnection(dbChoisie.getUrl(),
                    dbChoisie.getUid(), dbChoisie.getPsw());

            System.out.flush();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            throw new DBException("Probleme de connexion.  \n " + ex.getMessage());

        }
    }
    
    /**
     * Retourne la connexion etablie ou a defaut, l'etablit
     *
     * @return la connexion etablie.
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
     * @throws FourchetteDBException Transformation de la SQLException en
     * FourchetteDBException
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
     * @throws FourchetteDbException validation non réalisée
     */
    public static void valideTransaction() throws  DBException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DBException("Impossible de valider la transaction");
        }
    }
    
    /**
     * annule la transaction courante
     * @throws FourchetteDbException annulation non realisee
     */
    public static void annuleTransaction() throws  DBException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DBException("Impossible d'annuler la transaction");
        }
    }

}
