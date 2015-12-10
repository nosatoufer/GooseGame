/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.dto;

import java.util.Date;
import heb.esi.goosegame.model.Player;

/**
 * Classe de transfert des données d'un jeu
 *
 * @author HONEY
 */
public class GameDto {

    private final int gId;
    private final Date gStartDate;
    private final Date gEndDate;
    private final boolean gOver;
    private String gWinner;

    /**
     * Constructeur de la classe GameDto.
     *
     * @param gId id du jeu.
     * @param gDateDebut date de début du jeu.
     * @param gDateFin date de fin du jeu.
     * @param gWinner gagnant du jeu
     */
    public GameDto(int gId, Date gStartDate, Date gEndDate, String gWinner, Boolean over) {
        this.gId = gId;
        this.gStartDate = gStartDate;
        this.gEndDate = gEndDate;
        this.gWinner = gWinner;
        this.gOver = over;
    }

    /**
     * retourne l'id du jeu
     * @return  l'id du jeu
     */
    public int idGame() {
        return this.gId;
    }

    /**
     * retourne la date de début du jeu
     * @return  la date de début du jeu
     */
    public Date startDateGame() {
        return this.gStartDate;
    }

    /**
     * retourne la date de fin du jeu
     * @return  la date de fin du jeu
     */
    public Date endDateGame() {
        return this.gEndDate;
    }

    /**
     * retourne le gagnant du jeu
     * @return  le gagnant du jeu
     */
    public String winnerGame() {
        return this.gWinner;
    }
    
    public boolean isOver()
    {
        return gOver;
    }

}
