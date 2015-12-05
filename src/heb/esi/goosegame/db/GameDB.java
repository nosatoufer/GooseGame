/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.db;

import heb.esi.goosegame.dto.GameDto;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe utilitaire d'accès aux jeux repris dans la table GAME
 *
 * @author HONEY
 */
public class GameDB {

    /**
     * retourne la collection de tous les jeux
     *
     * @return la collection de tous les jeux
     * @throws DBException si la requête échoue
     */
    public static Collection<GameDto> getAllGames() throws DBException {
        ArrayList<GameDto> listGames = new ArrayList();
        try {
            String query = "SELECT * FROM GAME";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listGames.add(new GameDto(rs.getInt("gId"), rs.getDate("gStartDate"),
                        rs.getDate("gEndDate"), rs.getString("gWinner")));
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation de la table jeu impossible:\nSQLException: " + eSQL.getMessage());
        }
        return listGames;
    }

    /**
     * retourne la date de début d'un jeu donné par son id
     *
     * @param gId l'id du jeu
     * @return la date de début du jeu donné par son id
     * @throws DBException si la requête échoue
     */
    public static Date getStartDate(int gId) throws DBException {
        Date startDate = null;
        try {
            String query = "SELECT gStartDate FROM GAME WHERE gId ='" + gId + "'";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                startDate = rs.getDate("gStartDate");
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation de la date impossible impossible:\nSQLException: " + eSQL.getMessage());
        }
        return startDate;
    }

    /**
     * retourne la date de fin d'un jeu donné par son id
     *
     * @param gId l'id du jeu
     * @return la date de fin du jeu donné par son id
     * @throws DBException si la requête échoue
     */
    public static Date getEndDate(int gId) throws DBException {
        Date endDate = null;
        try {
            String query = "SELECT gEndDate FROM GAME WHERE gId ='" + gId + "'";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                endDate = rs.getDate("gStartDate");
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation de la date impossible:\nSQLException: " + eSQL.getMessage());
        }
        return endDate;
    }

    /**
     * retourne le gagnant du jeu donné par son id
     *
     * @param gId l'id du jeu
     * @return le gagnant du jeu donné par son id
     * @throws DBException si la requête échoue
     */
    public static String getWinner(int gId) throws DBException {
        String winner = "";
        try {
            String query = "SELECT gWinner FROM GAME WHERE gId ='" + gId + "'";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                winner = rs.getString("gWinner");
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation du gagnant impossible:\nSQLException: " + eSQL.getMessage());
        }
        return winner;
    }

    /**
     * ajoute un jeu à la table Joueur
     *
     * @param id id du jeu
     * @param startDate la date de début du jeu
     * @param endDate la de fin du jeu
     * @param winner le gagnangt du jeu
     * @throws DBException si la requête échoue
     */
    public static void insertGame(GameDto newGame) throws DBException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            Statement insert = connexion.createStatement();
            String query = "INSERT INTO GAME (gId, gStartDate, gEndDate, gWinner) "
                    + "VALUES(" + newGame.idGame() + ", " + newGame.startDateGame() + "," + newGame.endDateGame() + ",'" + newGame.wiinnerGame() + "')";
            insert.executeUpdate(query);
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du jeu a échoué:\nSQLException: " + eSQL.getMessage());
        }
    }

    /**
     * supprime un jeu donné dans la table Game
     *
     * @param delGame le jeu à modifier
     * @throws DBException si la requête échoue
     */
    public static void deleteGame(GameDto delGame) throws DBException {
        try {
            String query = ("DELETE FROM GAME" + " WHERE gId =" + delGame.idGame());
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException("La suppression du jeu a échoué :\nSQLException: "
                    + ex.getMessage());
        }

    }
}


