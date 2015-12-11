package heb.esi.goosegame.db;

import heb.esi.goosegame.dto.GameDto;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe utilitaire d'accès aux jeux repris dans la table GAME
 *
 * @author HONEY
 */
public class GameDB {

    /**
     * retourne une liste de toutes les parties en cours
     *
     * @return la collection de tous les jeux
     * @throws DBException si la requête échoue
     */
    public static ArrayList<GameDto> getActiveGames() throws DBException {
        ArrayList<GameDto> listGames = new ArrayList();
        try {
            String query = "SELECT * FROM GAME Where gOver=false";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listGames.add(new GameDto(rs.getString("gName"), rs.getInt("gCurrentPlayer"), rs.getBoolean("gOver")));
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation de la table jeu impossible (ERR01)\nSQLException: " + eSQL.getMessage());
        }
        return listGames;
    }
    
    /**
     * retourne le jeu dont le nom est passé en paramètre
     *
     * @param name
     * @return la collection de tous les jeux
     * @throws DBException si la requête échoue
     */
    public static GameDto getGame(String name) throws DBException {
        GameDto game = null;
        try {
            String query = "SELECT * FROM GAME WHERE gName=?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, name);
            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                game = new GameDto(rs.getString("gName"), rs.getInt("gCurrentPlayer"), rs.getBoolean("gOver"));
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation de la table jeu impossible (ERR02)\nSQLException: " + eSQL.getMessage());
        }
        return game;
    }

    /**
     *
     * Vérifie si le nom de la partie est déjà pris
     * 
     * @param game
     * @return
     * @throws DBException
     */
    public static boolean checkName(GameDto game) throws DBException {
        try {
            String query = "SELECT gName FROM Game WHERE gName = ? ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1,game.getName());
            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation de la partie impossible:\nSQLException: " + eSQL.getMessage());
        }

        return false;
    }
    
    /**
     * retourne le joueur courant de la partie dont le nom est passé en paramètre
     *
     * @param gName
     * @return le gagnant du jeu donné par son id
     * @throws DBException si la requête échoue
     */
    public static String getCurrentPlayer(String gName) throws DBException {
        String winner = "";
        try {
            String query = "SELECT gCurrentPlayer FROM Game WHERE gName = ?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = connexion.prepareStatement(query);
            stmt.setString(1, gName);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                winner = rs.getString("gCurrentPlayer");
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation du gagnant impossible (ERR03)\nSQLException: " + eSQL.getMessage());
        }
        return winner;
    }

    /**
     * ajoute un jeu à la table Game
     *
     * @param newGame
     * @throws DBException si la requête échoue
     */
    public static void insertGame(GameDto newGame) throws DBException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "INSERT INTO Game (gName, gCurrentPlayer, gOver) VALUES(?, ?, ?)";
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);

            stmt.setString(1, newGame.getName());
            stmt.setInt(2, newGame.getCurrentPlayer());
            stmt.setBoolean(3, newGame.isOver());
            stmt.executeUpdate();
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du jeu a échoué bla (ERR03)\nSQLException: " + eSQL.getMessage());
        }
    }

    /**
     *
     * Met à jour un jeu de la table game
     * 
     * @param newGame
     * @throws DBException
     */
    public static void updateGame(GameDto newGame) throws DBException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "UPDATE GAME SET gCurrentPlayer = ?, gOver = ? WHERE gName = ?";
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, newGame.getCurrentPlayer());
            stmt.setBoolean(2, newGame.isOver());
            stmt.setString(3, newGame.getName());
            stmt.executeUpdate();
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du jeu a échoué (ERR04)\nSQLException: " + eSQL.getMessage());
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
            String query = "DELETE FROM GAME WHERE gName = ?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, delGame.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException("La suppression du jeu a échoué  (ERR05)\nSQLException: "
                    + ex.getMessage());
        }

    }
}
