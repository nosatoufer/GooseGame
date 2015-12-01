/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.dto;

import heb.esi.goosegame.model.Game;
import heb.esi.goosegame.model.Player;
import heb.esi.goosegame.model.Player;

/**
 * Classe de transfert de données d'un joueur pour un jeu donné
 *
 * @author HONEY
 */
public class PlayerGameDto {

    private /*Player*/ int pgPlayer;
    private /*Game*/ int pgGame;
    private /*PlayerColor*/String pgColor;

    /**
     * Constructeur de la classe PlayerGameDto.
     *
     * @param pgPlayer id du joueur pour ce jeu.
     * @param pgGame id du jeu .
     * @param pgColor couleur du joueur
     */
    public PlayerGameDto(int pgPlayer, int pgGame, String pgColor) {
        this.pgPlayer = pgPlayer;
        this.pgGame = pgGame;
        this.pgColor = pgColor;
    }
    
    /**
     * retourne l'id du joueur pour ce jeu
     * @return  l'id du joueur pour ce jeu
     */
    public int player(){
        return this.pgPlayer;
    }
    
    /**
     * retourne l'id du jeu dans lequel le joueur est entrain de jouer
     * @return  l'id du jeu dans lequel le joueur est entrain de jouer
     */
    public int game(){
        return this.pgGame;
    }
    
    /**
     * retourne la couleur du joueur
     * @return la couleur du joueur
     */
    public String color(){
        return this.pgColor;
    }
}
