/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.db;

import heb.esi.goosegame.dto.PlayerDto;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe utilitaire d'accès aux joueurs de la table PLAYER
 *
 * @author HONEY
 */
public class PlayerDB {

    /**
     * retourne la collection de tous les joueurs de la base de donnée.
     *
     * @return la collection des joueurs
     * @throws DBException si la requête sql a échoué.
     */
    public static Collection<PlayerDto> getAllPlayers() throws DBException {
        ArrayList<PlayerDto> listPlayers = new ArrayList<PlayerDto>();
        try {
            String query = "SELECT * From PLAYER";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listPlayers.add(new PlayerDto(rs.getInt("pId"), rs.getString("pName")));
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation du joueur impossible:\nSQLException: " + eSQL.getMessage());
        }
        return listPlayers;
    }

    /**
     * retourne l'id du joueur basé sur son nom
     *
     * @param pNom nom du joueur
     * @return l'id du joueur basé sur son nom
     * @throws DBException si la requête sql échoue
     */
    public static int getIdPlayer(String pName) throws DBException {
        int idPlayer = 0;
        try {
            String query = "SELECT pId FROM PLAYER WHERE pName = ?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = connexion.prepareStatement(query);
            stmt.setString(1, pName);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idPlayer = rs.getInt("pId");
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation du joueur impossible:\nSQLException: " + eSQL.getMessage());
        }
        return idPlayer;
    }

    /**
     * retourne le nom du joueur basé sur son id
     *
     * @param pId id du joueur
     * @return le nom du joueur basé sur son id throws DBException si la requête
     * sql échoue
     */
    public static int getNomPlayer(int pId) throws DBException {
        int idPlayer = 0;
        try {
            String query = "SELECT pId FROM PLAYER WHERE pName = ?";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = connexion.prepareStatement(query);
            stmt.setInt(1, pId);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idPlayer = rs.getInt("pId");
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation du joueur impossible:\nSQLException: " + eSQL.getMessage());
        }
        return idPlayer;
    }
    
    
    /**
     * Vérifie si un joueur existe déjà dans la table
     *
     * @param nom le nom du joeuer
     * @return trus si le joueur existe déjà dans la table, false si non
     */
    public static boolean checkPlayer(String pName) throws DBException {
        try {
            String query = "SELECT pName FROM PLAYER WHERE pName = ? ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1,pName);
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
            String query = "INSERT INTO PLAYER (pId, Pname) "
                    + "VALUES( ?,?)";
            
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, newPlayer.idPlayer());
            stmt.setString(2, newPlayer.namePlayer());
            stmt.executeUpdate(query);
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
            String query = ("DELETE FROM PLAYER" + " WHERE pId = ?");
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, delPlayer.idPlayer());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException("La suppression du joueur a échoué :\nSQLException: "
                    + ex.getMessage());
        }

    }

}
