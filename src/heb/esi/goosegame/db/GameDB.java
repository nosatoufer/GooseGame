/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.db;

import heb.esi.goosegame.dto.GameDto;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
                listGames.add(new GameDto(rs.getInt("gId"), rs.getString("gWinner"),
                        rs.getBoolean("gOver"), rs.getString("gName"), rs.getString("gDesc")));
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation de la table jeu impossible:\nSQLException: " + eSQL.getMessage());
        }
        return listGames;
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
            String query = "SELECT gWinner FROM GAME WHERE gId = ?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = connexion.prepareStatement(query);
            stmt.setInt(1, gId);
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
     * ajoute un jeu à la table Game
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
            String query = "INSERT INTO Game (gId, gName, gDesc, gWinner, gOver)"
                    + "VALUES(?, ?, ?, ?, ?)";
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);

            stmt.setInt(1, newGame.idGame());
            stmt.setString(2, newGame.name());
            stmt.setString(3, newGame.desc());
            stmt.setString(4, newGame.winnerGame());
            stmt.setBoolean(5, newGame.isOver());
            stmt.executeUpdate(query);
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du jeu a échoué:\nSQLException: " + eSQL.getMessage());
        }
    }

    public static void updateGame(GameDto newGame) throws DBException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "UPDATE GAME"
                    + "SET gName = ?, gDesc = ? gWinner = ?, gOver = ?"
                    + "WHERE gId = ?";
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(5, newGame.idGame());
            stmt.setString(1, newGame.name());
            stmt.setString(2, newGame.desc());
            stmt.setString(3, newGame.winnerGame());
            stmt.setBoolean(4, newGame.isOver());
            stmt.executeUpdate(query);
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
            String query = "DELETE FROM GAME WHERE gId = ?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, delGame.idGame());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException("La suppression du jeu a échoué :\nSQLException: "
                    + ex.getMessage());
        }

    }
}
