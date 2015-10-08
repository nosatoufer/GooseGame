package goosegame.model;

/**
 *
 * @author nosa
 */
public enum Color {
    GREEN,
    PINK,
    BLUE,
    YELLOW,
    PURPLE,
    RED,
    BLACK,
    WHITE;
    
    @Override
    public String toString() { return this.name().substring(0, 3); }
}

