/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.db;

import heb.esi.goosegame.dto.PlayerDto;
import heb.esi.goosegame.dto.PlayerInGameDto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Classe utilitaire d'accès aux joueurs et aux jeux auxquels ils ont joué
 *
 * @author HONEY
 */
public class PlayerInGameDB {

    /**
     *
     * Récupère toutes les infos de joueurs présents dans une partie dont le nom
     * est passé en paramètre
     * 
     * @param name
     * @return
     * @throws DBException
     */
    public static ArrayList<PlayerInGameDto> getAllPlayerFromGame(String name) throws DBException {
        ArrayList<PlayerInGameDto> listPlayers = new ArrayList<>();
        try {
            String query = "Select * from PlayerInGame"
                    + " WHERE pigGameName = ? ORDER BY pigOrder";
            Connection con = DBManager.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet res = stmt.executeQuery();
            while(res.next())
            {
                listPlayers.add(new PlayerInGameDto(res.getString("pigPlayerName"), res.getString("pigGameName"), res.getString("pigPlayerColor"), res.getInt("pigOrder"), res.getInt("pigPosition")));
            }
        } catch (SQLException e) {

        }
        return listPlayers;
    }

    /**
     * ajoute un joueur et le jeu auquel il a joué (ou joue) dans la table PLAYERINGAME
     *
     * @param newPlayerGame le joueur et son jeu
     * @throws heb.esi.goosegame.db.DBException
     */
    public static void insertPlayerInGame(PlayerInGameDto newPlayerGame) throws DBException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "INSERT INTO PlayerInGame (pigPlayerName, pigGameName, pigPlayerColor, pigOrder, pigPosition) VALUES(?,?,?,?,?)";
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, newPlayerGame.getPlayerName());
            stmt.setString(2, newPlayerGame.getGameName());
            stmt.setString(3, newPlayerGame.getPlayerColor());
            stmt.setInt(4, newPlayerGame.getOrder());
            stmt.setInt(5, newPlayerGame.getPosition());
            stmt.executeUpdate();
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du joueur a échoué:\nSQLException: " + eSQL.getMessage());
        }
    }

    /**
     * supprime un lien entre un joueur et une partie du jeu 
     *
     * @param delPlayer le jeu à modifier
     * @throws DBException si la requête échoue
     */
    public static void deletePlayerInGame(PlayerInGameDto delPlayer) throws DBException {
        try {
            String query = "DELETE FROM PlayerInGame WHERE pigPlayerName = ? AND pigGameName = ?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, delPlayer.getPlayerName());
            stmt.setString(2, delPlayer.getGameName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException("La suppression du joueur a échoué :\nSQLException: " + ex.getMessage());
        }

    }

}
