package goosegame.model;

/**
 *
 * @author nosa
 */
public enum PlayerColor {
    
    GREEN("vert", 0),
    PINK("rose", 1),
    BLUE("bleu", 2),
    YELLOW("jaune", 3),
    PURPLE("pourpre", 4),
    RED("rouge", 5),
    BLACK("noir", 6),
    WHITE("blanc", 7);
    
    
    private final String desc;
    private final int value;
    PlayerColor(String desc, int value)
    {
        this.desc = desc;
        this.value = value;
    }
    
    String desc()
    {
        return this.desc;
    }
    
    int value()
    {
        return this.value;
    }
    
    @Override
    public String toString() { return this.desc; }
}

