package heb.esi.goosegame.db;

import heb.esi.goosegame.dto.PlayerDto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe utilitaire d'accès aux joueurs de la table PLAYER
 *
 * @author HONEY
 */
public class PlayerDB {

    /**
     * retourne une liste de tous les joueurs de la base de donnée.
     *
     * @return la collection des joueurs
     * @throws DBException si la requête sql a échoué.
     */
    public static ArrayList<PlayerDto> getAllPlayers() throws DBException {
        ArrayList<PlayerDto> listPlayers = new ArrayList<>();
        try {
            String query = "SELECT * From PLAYER";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listPlayers.add(new PlayerDto(rs.getString("pName")));
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation du joueur impossible:\nSQLException: " + eSQL.getMessage());
        }
        return listPlayers;
    }
    
    /**
     * Vérifie si un joueur existe déjà dans la table
     *
     * @param player
     * @return true si le joueur existe déjà dans la table, false si non
     * @throws heb.esi.goosegame.db.DBException
     */
    public static boolean checkPlayer(PlayerDto player) throws DBException {
        try {
            String query = "SELECT pName FROM Player WHERE pName = ? ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1,player.getPlayerName());
            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation du joueur impossible:\nSQLException: " + eSQL.getMessage());
        }

        return false;
    }
    
    
    /**
     * ajoute un joueur à la table Player
     *
     * @param newPlayer le joueur joueur à ajouter
     * @throws DBException si la requête échoue
     */
    public static void insertPlayer(PlayerDto newPlayer) throws DBException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "INSERT INTO Player(pName) VALUES(?)";
            
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, newPlayer.getPlayerName());
            stmt.executeUpdate();
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du joueur a échoué:\nSQLException: " + eSQL.getMessage());
        }
    }
    
    /**
     * supprime un joueur donné dans la table Player
     *
     * @param delPlayer le jeu à modifier
     * @throws DBException si la requête échoue
     */
    public static void deletePlayer(PlayerDto delPlayer) throws DBException {
        try {
            String query = ("DELETE FROM PLAYER WHERE pName = ?");
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, delPlayer.getPlayerName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException("La suppression du joueur a échoué :\nSQLException: "
                    + ex.getMessage());
        }

    }

}
