package heb.esi.goosegame.model;

/**
 *
 * @author nosa
 */
public enum CaseType
{

    /**
     *
     */
    START("Start", 0, 0, false, 0),

    /**
     *
     */
    EMPTY("", 1, 0, false, 0),

    /**
     *
     */
    GOOSE("Goose", 2, 0, true, -1),

    /**
     *
     */
    JAIL("Jail", 3, 0, false, 0),

    /**
     *
     */
    INN("Inn", 4, 1, false, 0),

    /**
     *
     */
    WELL("Well", 5, 2, false, 0),

    /**
     *
     */
    BRIDGE("Bridge", 6, 0, true, 20),

    /**
     *
     */
    MAZE("Maze", 7, 0, true, 30),

    /**
     *
     */
    DEATH("Death", 8, 0, true, 0),

    /**
     *
     */
    END("End", 9, 0, false, 0);
    
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
    
    /**
     *
     * @return
     */
    public String desc()
    {
        return this.desc;
    }
    
    /**
     *
     * @return
     */
    public int value()
    {
        return this.value;
    }
    
    /**
     *
     * @return
     */
    public int stuck()
    {
        return this.stuck;
    }
    
    /**
     *
     * @return
     */
    public boolean move()
    {
        return this.move;
    }
    
    /**
     *
     * @return
     */
    public int exit()
    {
        return this.exit;
    }
    
    
    @Override
    public String toString() { return this.name().substring(0, 3); }
}
