package heb.esi.goosegame.business;

import heb.esi.goosegame.db.DBException;
import heb.esi.goosegame.db.GameDB;
import heb.esi.goosegame.db.PlayerDB;
import heb.esi.goosegame.db.PlayerInGameDB;
import heb.esi.goosegame.dto.GameDto;
import heb.esi.goosegame.dto.PlayerDto;
import heb.esi.goosegame.dto.PlayerInGameDto;
import java.util.ArrayList;

/**
 * Classe permettant d'interagir avec la base de donnée.
 * @author HONEY
 */
public class GooseGameFacade {
    
    /**
     * Récupère la liste des noms des parties sauvegardées
     * @return
     * @throws DBException
     */
    
    static public ArrayList<String> getSavedGames() throws DBException {
        ArrayList<GameDto> games = GameDB.getAllGames();
        ArrayList<String> gamesName = new ArrayList<>();
        for(GameDto game : games) {
            gamesName.add(game.getName());
        }
        return gamesName;
    }
    
    /**
     * Récupère une partie sauvegardée
     * @param name
     * @return
     * @throws DBException
     */
    static public GameDto getSavedGame(String name) throws DBException {
        return GameDB.getGame(name);
    }
    
    /**
     * Récupères les liens entre les joueurs participants à la partie dont le
     * nom est passé en paramètre
     * @param name
     * @return
     * @throws DBException
     */
    static public ArrayList<PlayerInGameDto> getAllPlayerFromGame(String name) throws DBException {
        return PlayerInGameDB.getAllPlayerFromGame(name);
    }
    
    /**
     * Crée un joueur dans la base de donnée avec le nom passé en paramètre
     * @param name
     * @throws DBException
     */
    static public void createPlayer(String name) throws DBException {
        if (!PlayerDB.checkPlayer(new PlayerDto(name))) {
            PlayerDB.insertPlayer(new PlayerDto(name));
        }
    }
    
    /**
     * Vérifie si le nom d'un jeu existe déjà
     * @param name
     * @return
     * @throws DBException
     */
    static public boolean checkGameName(String name) throws DBException {
        return GameDB.checkName(new GameDto(name));
    }
    
    /**
     * Sauvegarde une partie du jeu
     * @param game
     * @throws DBException
     */
    static public void saveGame(GameDto game) throws DBException {
        GameDB.insertGame(game);
    }
    
    /**
     * Met à jour la sauvegarde d'une partie en cours
     * @param game
     * @throws DBException
     */
    static public void updateGame(GameDto game) throws DBException {
        GameDB.updateGame(game);
    }
    
    /**
     * supprime une partie 
     * @param game
     * @throws DBException
     */
    static public void deleteGame(GameDto game) throws DBException {
        GameDB.deleteGame(game);
    }
    
    /**
     * Sauvegarde le lien entre un joueur et une partie
     * @param game
     * @throws DBException
     */
    static public void savePlayerInGame(PlayerInGameDto game) throws DBException {
        PlayerInGameDB.insertPlayerInGame(game);
    }
    
    /**
     * Supprime le lien entre un joueur et une partie
     * @param game
     * @throws DBException
     */
    static public void deletePlayerInGame(PlayerInGameDto game) throws DBException {
        PlayerInGameDB.deletePlayerInGame(game);
    }
}
