/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.dto;

/**
 * Classe de transfert des données d'un jeu
 *
 * @author HONEY
 */
public class GameDto {

    private int gCurrentPlayer;
    private final String gName;
    private boolean gOver;

    /**
     * Constructeur de la classe GameDto.
     *
     * @param name
     * @param gCurrentPlayer
     * @param gOver
     */
    public GameDto(String name, int gCurrentPlayer, boolean gOver) {
        this.gCurrentPlayer = gCurrentPlayer;
        this.gOver = gOver;
        this.gName = name;
    }

    /**
     *
     * @param name
     */
    public GameDto(String name) {
        this.gName = name;
    }

    /**
     * retourne le gagnant du jeu
     * @return  le gagnant du jeu
     */
    public int getCurrentPlayer() {
        return this.gCurrentPlayer;
    }
    
    /**
     *
     * @return
     */
    public boolean isOver()
    {
        return gOver;
    }
    
    /**
     *
     * @return
     */
    public String getName()
    {
        return gName;
    }
}
