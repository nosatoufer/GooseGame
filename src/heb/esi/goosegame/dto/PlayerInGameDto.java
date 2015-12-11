/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.dto;

/**
 * Classe de transfert de données d'un joueur pour un jeu donné
 *
 * @author HONEY
 */
public class PlayerInGameDto {

    private final /*Player*/ String pigPlayerName;
    private final /*Game*/ String pigGameName;
    private final /*PlayerColor*/String pigPlayerColor;
    private final int pigOrder;
    private final int pigPosition;

    /**
     * Constructeur de la classe PlayerGameDto.
     *
     * @param pigPlayerName
     * @param pigGameName
     * @param pigPlayerColor
     * @param pigOrder
     * @param pigPosition
     */
    public PlayerInGameDto(String pigPlayerName, String pigGameName, String pigPlayerColor, int pigOrder, int pigPosition) {
        this.pigPlayerName = pigPlayerName;
        this.pigGameName = pigGameName;
        this.pigPlayerColor = pigPlayerColor;
        this.pigOrder = pigOrder;
        this.pigPosition = pigPosition;
    }
    
    /**
     * retourne le nom du joueur qui est dans la partie
     * @return nom du joueur qui est dans la partie
     */
    public String getPlayerName(){
        return this.pigPlayerName;
    }
    
    /**
     * retourne le nom de la partie où est inscrit le joueur
     * @return nom de la partie où est inscrit le joueur
     */
    public String getGameName(){
        return this.pigGameName;
    }
    
    /**
     * retourne la couleur du joueur
     * @return la couleur du joueur
     */
    public String getPlayerColor(){
        return this.pigPlayerColor;
    }
    
    /**
     * retourne la position du joueur
     * @return la position du joueur
     */
    public int getPosition(){
        return this.pigPosition;
    }
    
    /**
     * retourne la position du joueur
     * @return la position du joueur
     */
    public int getOrder(){
        return this.pigOrder;
    }
}