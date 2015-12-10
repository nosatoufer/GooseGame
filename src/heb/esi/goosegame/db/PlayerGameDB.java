/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.db;

import heb.esi.goosegame.dto.PlayerGameDto;
import java.sql.SQLException;

/**
 * Classe utilitaire d'accès aux joueurs et aux jeux auxquels ils ont joué
 * @author HONEY
 */
public class PlayerGameDB {
    
    /**
     * retourne le joueur, sa couleur et le jeu auqeul il a joué
     * @param newPlayerGame 
     * @return le joueur, sa couleur et le jeu auqeul il a joué
     */
    public static PlayerGameDto getDetailsPlayer(PlayerGameDto newPlayerGame) throws DBException{
        PlayerGameDto result = null;
        try{
            String query = "SELECT pgPlayer, pgGame, pgColor,gWinner FROM PLAYERGAME"
                    + "JOIN GAME on pgGame = gId"
                    + "WHERE pId = ?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, newPlayerGame.player());
            java.sql.ResultSet rs = stmt.executeQuery();
            result = (new PlayerGameDto(rs.getInt("pgPlayer"), rs.getInt("pgGame"), rs.getString("pgColor"), rs.getInt("pgPosition")));
        }catch(java.sql.SQLException eSQL){
            throw new DBException("accès impossible aux données");
        }
        return result;
    }
    
    /**
     * ajoute un joueur et le jeu auquel il ajoué dans la table PLAYERGAME
     * @param newPlayerGame le joueur et son jeu
     * @param throws DBException si la requête échoue
     */
    public static void insertPlayerGame(PlayerGameDto newPlayerGame) throws DBException{
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "INSERT INTO PLAYERGAME (pgPlayer, pgGame, pgColor) "
                    + "VALUES(" + newPlayerGame.player() + "," + newPlayerGame.game()
                    + "," + "'" + newPlayerGame.color() + "'";
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, newPlayerGame.player());
            stmt.setInt(2, newPlayerGame.game());
            stmt.setString(3, newPlayerGame.color());
            stmt.executeUpdate(query);
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du joueur a échoué:\nSQLException: " + eSQL.getMessage());
        }
        
    }
    
    
     /**
     * supprime un joueur et son jeu auquel il joué dans la table PLAYERGAME
     *
     * @param delPlayer le jeu à modifier
     * @throws DBException si la requête échoue
     */
    public static void deletePlayer(PlayerGameDto delPlayer) throws DBException {
        try {
            String query = "DELETE FROM PLAYERGAME WHERE pId = ?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, delPlayer.player());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException("La suppression du joueur a échoué :\nSQLException: "
                    + ex.getMessage());
        }

    }

    
    
   
   
}
