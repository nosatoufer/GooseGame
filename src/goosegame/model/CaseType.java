package goosegame.model;

/**
 *
 * @author nosa
 */
public enum CaseType
{
    START("Case de départ", 0, 0, false, 0),
    EMPTY("Case vide", 1, 0, false, 0),
    GOOSE("Case d'oie", 2, 0, true, -1),
    JAIL("Case prison", 3, 0, false, 0),
    INN("Case auberge", 4, 1, false, 0),
    WELL("Case puits", 5, 2, false, 0),
    BRIDGE("Case pont", 6, 0, true, 20),
    MAZE("Case labyrinthe", 7, 0, true, 30),
    DEATH("Case mort", 8, 0, true, 0),
    END("Case finale", 9, 0, false, 0);
    
    private final String desc;
    private final int value;
    private final int stuck;
    private final boolean move;
    private final int exit;
    
    CaseType(String desc, int value, int stuck, boolean move, int exit )
    {
        this.desc = desc;
        this.value = value;
        this.stuck = stuck;
        this.move = move;
        this.exit = exit;
    }
    
    public String desc()
    {
        return this.desc;
    }
    
    public int value()
    {
        return this.value;
    }
    
    public int stuck()
    {
        return this.stuck;
    }
    
    public boolean move()
    {
        return this.move;
    }
    
    public int exit()
    {
        return this.exit;
    }
    
    
    @Override
    public String toString() { return this.name().substring(0, 3); }
}
