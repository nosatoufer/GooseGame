package heb.esi.goosegame.model;

/**
 *
 * @author nosa
 */
public enum GameState {
    NOTSTARTED("Pas commencée", 0),
    STARTED("En cours", 1),
    OVER("Finie", 2);
    
    private final String desc;
    private final int state;

    GameState(String desc, int state)
    {
        this.desc = desc;
        this.state = state;
    }
}
