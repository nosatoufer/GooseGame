package goosegame.model;

/**
 *
 * @author nosa
 */
public enum CaseType
{
    START("Case de départ", 0),
    EMPTY("Case vide", 1),
    GOOSE("Case d'oie", 2),
    JAIL("Case prison", 3),
    INN("Case auberge", 4),
    WELL("Case puits", 5),
    BRIDGE("Case pont", 6),
    MAZE("Case labyrinthe", 7),
    DEATH("Case mort", 8),
    END("Case finale", 9);
    
    private final String desc;
    private final int value;
    
    CaseType(String desc, int value)
    {
        this.desc = desc;
        this.value = value;
    }
    
    @Override
    public String toString() { return this.name().substring(0, 3); }
}
