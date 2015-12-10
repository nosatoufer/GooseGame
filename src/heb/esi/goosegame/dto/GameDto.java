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
    private final boolean gOver;
    private final String gWinner;
    private final String gName;
    private final String gDesc;

    /**
     * Constructeur de la classe GameDto.
     *
     * @param gId id du jeu.
     * @param over
     * @param name
     * @param gWinner gagnant du jeu
     * @param desc
     */
    public GameDto(int gId, String gWinner, Boolean over, String name,String desc) {
        this.gId = gId;
        this.gWinner = gWinner;
        this.gOver = over;
        this.gName = name;
        this.gDesc = desc;
    }

    /**
     * retourne l'id du jeu
     * @return  l'id du jeu
     */
    public int idGame() {
        return this.gId;
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
    
    public String name()
    {
        return gName;
    }

    public String desc()
    {
        return gDesc;
    }
}
