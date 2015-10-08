package goosegame.model;

/**
 *
 * @author nosa
 */
public enum CaseType
{
    START,
    EMPTY,
    GOOSE,
    JAIL,
    INN,
    WELL,
    BRIDGE,
    MAZE,
    DEATH,
    END;
    
    @Override
    public String toString() { return this.name().substring(0, 3); }
}
