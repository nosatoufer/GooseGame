/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.db;

import heb.esi.goosegame.dto.GameDto;
import heb.esi.goosegame.dto.PlayerDto;
import heb.esi.goosegame.dto.PlayerGameDto;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe utilitaire d'accès aux joueurs et jeux repris dans la base de donnée
 *
 * @author HONEY
 */
public class GooseGameDB {

    /**
     * retourne la collection de tous les joueurs de la base de donnée.
     *
     * @return la collection des joueurs
     * @throws DBException si la requête sql a échoué.
     */
    public static Collection<PlayerDto> getAllPlayers() throws DBException {
        ArrayList<PlayerDto> listPlayers = new ArrayList<PlayerDto>();
        try {
            String query = "SELECT * From Player";
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
     * retourne les informations d'un joueur donné.
     *
     * @param pId id du joueur donné
     * @return les informations du joueur donné
     * @throws DBException si la requête sql échoue
     */
    public static PlayerGameDto getPlayerDetails(int pId) throws DBException {
        PlayerGameDto result;
        try {
            String query = "SELECT pgPlayer, pgGame, pgColor FROM PlayerGame"
                    + "WHERE pId = '" + pId + "'";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();
            // if (rs.next()) {
            result = (new PlayerGameDto(rs.getInt("pgPlayer"), rs.getInt("pgGame"),
                    rs.getString("pgColor")));
            // }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation du joueur impossible:\nSQLException: " + eSQL.getMessage());
        }
        return result;
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
            String query = "SELECT pId FROM Player WHERE pName ='" + pName + "'";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = connexion.prepareStatement(query);
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
            String query = "SELECT pId FROM Player WHERE pName ='" + pId + "'";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = connexion.prepareStatement(query);
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
     * retourne une collection de tous les jeux
     *
     * * @return la collection des joueurs
     * @throws DBException si la requête sql a échoué.
     */
    public static Collection<GameDto> getAllGames() throws DBException {
        ArrayList<GameDto> listGames = new ArrayList();
        try {
            String query = "SELECT * FROM Game";
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
            String query = "SELECT gStartDate FROM Game WHERE gId ='" + gId + "'";
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
            String query = "SELECT gEndDate FROM Game WHERE gId ='" + gId + "'";
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
     * retourne la date de fin d'un jeu donné par son id
     *
     * @param gId l'id du jeu
     * @return la date de fin du jeu donné par son id
     * @throws DBException si la requête échoue
     */
    public static String getWinner(int gId) throws DBException {
        String winner = "";
        try {
            String query = "SELECT gWinner FROM Game WHERE gId ='" + gId + "'";
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
     * Vérifie si un joueur existe déjà dans la table
     *
     * @param nom le nom du joeuer
     * @return trus si le joueur existe déjà dans la table, false si non
     */
    public static boolean checkUser(String name) throws DBException {
        try {
            String query = "SELECT pName FROM Player WHERE pName ='" + name + "' ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation du gagnant impossible:\nSQLException: " + eSQL.getMessage());
        }

        return false;
    }

    /**
     * ajoute un joueur à la table Player
     *
     * @param id id du joueur
     * @param name nom du joueur
     * @throws DBException si la requête échoue
     */
    public static void insertPlayer(int id, String name) throws DBException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            Statement insert = connexion.createStatement();
            String query = "INSERT INTO Player (pId, Pname) VALUES(" + id + ",'" + name + "')";
            insert.executeUpdate(query);
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du joueur a échoué:\nSQLException: " + eSQL.getMessage());
        }
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
    public static void insertGame(int id, Date startDate, Date endDate, String winner) throws DBException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            Statement insert = connexion.createStatement();
            String query = "INSERT INTO Game (gId, gStartDate, gEndDate, gWinner) VALUES(" + id + ", " + startDate + "," + endDate + ",'" + winner + "')";
            insert.executeUpdate(query);
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du jeu a échoué:\nSQLException: " + eSQL.getMessage());
        }
    }
    
    
    /**
     * ajoute un joueur et le jeu auquel il a joué  à la table PlayerGame
     * @param pgPlayer l'id du joueur
     * @param pgGame le jeu auquel le joueur a joué
     * @param pgColor la couleur du joueur
     * @throws DBException si la requête échoue
     */
    public static void insertPlayerGame(int idPlayer, int idGame, String colorPlayer) throws DBException{
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            Statement insert = connexion.createStatement();
            String query = "INSERT INTO PlayerGame (pgPlayer, pgGame, pgColor) VALUES("+ idPlayer + "," + idGame + ",'" + colorPlayer +"')";
            insert.executeUpdate(query);
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("L'ajout du joueur et son jeu a échoué:\nSQLException: " + eSQL.getMessage());
        }
    }

}
