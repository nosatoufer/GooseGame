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
    private int pId;
    private String pName;
    
    
    /**
     * Constructeur de la classe PlayerDto.
     *
     * @param pId id du joueur.
     * @param pName nom du joueur.
     */
    public PlayerDto(int pId, String pName){
        this.pId = pId;
        this.pName = pName;
    }
    
    /**
    *retourne l'id du joueur
    * @return l'id du joueur
    */
    public int idPlayer(){
        return this.pId;
    }
    
    /**
    *retourne le nom du joueur
    * @return le nom du joueur
    */
    public String namePlayer(){
        return this.pName;
    }
    
}
