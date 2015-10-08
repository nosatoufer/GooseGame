package goosegame.model;

/**
 * Represent a case on the board
 * @author nosa
 */
public class Case {

    private Player m_player;
    private CaseType m_type;

    public Case() {
        m_player = null;
        m_type = CaseType.EMPTY;
    }

    public Case(CaseType type) {
        m_player = null;
        m_type = type;
    }

    /**
     * Set the player contained by the case.
     * @param p the player to set in the case
     */
    public void setPlayer(Player p) {
        m_player = p;
    }

    /**
     * Return the player contained by the case.
     * @return 
     */
    public Player player() {
        return m_player;
    }

    /**
     * Return the type of the case.
     * @return the type of the case
     */
    public CaseType type() {
        return m_type;
    }

    /**
     * Set the type of the case
     * @param type the new type
     */
    public void setType(CaseType type) {
        m_type = type;
    }

    /**
     * Return a string to display a case
     * @return the string to display the case
     */
    public String toString() {
        String s = "[" +m_type.toString()+"]";
        return s;
    }
}
