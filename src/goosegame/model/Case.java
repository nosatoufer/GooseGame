package goosegame.model;

/**
 * Represent a case on the board
 *
 * @author nosa
 */
public class Case {

    private Player player;
    private CaseType type;

    public Case() {
        this.player = null;
        this.type = CaseType.EMPTY;
    }

    public Case(CaseType type) {
        this.player = null;
        this.type = type;
    }
    
    /**
     * Copy constructor
     * @param c the case to copy
     */
    public Case(Case c)
    {
        this.player = c.player;
        this.type = c.type;
    }

    /**
     * Set the player contained by the case.
     *
     * @param p the player to set in the case
     */
    public void setPlayer(Player p) {
        this.player = p;
    }

    /**
     * Return the player contained by the case.
     *
     * @return
     */
    public Player player() {
        return player;
    }

    /**
     * Return the type of the case.
     *
     * @return the type of the case
     */
    public CaseType type() {
        return type;
    }

    /**
     * Set the type of the case
     *
     * @param type the new type
     */
    public void setType(CaseType type) {
        this.type = type;
    }

    /**
     * Return a string to display a case
     *
     * @return the string to display the case
     */
    @Override
    public String toString() {
        String s = "[" + type.toString() + "]";
        return s;
    }
}
