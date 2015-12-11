/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.dto;

/**
 * Classe de transfert des données d'un joueur
 * @author HONEY
 */
public class PlayerDto {
    private String pName;
    
    
    /**
     * Constructeur de la classe PlayerDto.
     *
     * @param pName nom du joueur.
     */
    public PlayerDto(String pName){
        this.pName = pName;
    }
    
    /**
    *retourne le nom du joueur
    * @return le nom du joueur
    */
    public String getPlayerName(){
        return this.pName;
    }
}
