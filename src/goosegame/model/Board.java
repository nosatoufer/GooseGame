package goosegame.model;

/**
 * Represent the whole board with cases, player, dices.
 *
 * @author nosa
 */
public class Board {

    private final Case board[] = new Case[64];

    public Board(){
        for (int i = 0; i < 64; ++i) {
            this.board[i] = new Case();
        }
        for (int i = 9; i < 61; i += 9) {
            this.board[i].setType(CaseType.GOOSE);
        }
        this.board[0].setType(CaseType.START);
        this.board[6].setType(CaseType.BRIDGE);
        this.board[19].setType(CaseType.INN);
        this.board[31].setType(CaseType.WELL);
        this.board[42].setType(CaseType.MAZE);
        this.board[52].setType(CaseType.JAIL);
        this.board[58].setType(CaseType.DEATH);
        this.board[63].setType(CaseType.END);
    }
    
    /**
     * Removes the player contained at the given position
     * @param index the position on the board
     */
    public void clearCase(int index)
    {
        this.board[index].setPlayer(null);
    }
    
    /**
     * Returns a copy of the case at the given position
     * @param index the position of the case
     * @return a copy of the case
     */
    public Case getCase(int index)
    {
        return this.board[index];
    }
    
    /**
     * Return a string displaying the board.
     *
     * @return the board as a string
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 1; i < 64; ++i) {
            s += " ";
            if (this.board[i].player() != null) {
                s += this.board[i].player().toString();
            } else {
                s += "   ";
            }
            s += " ";
        }
        s += '\n';
        for (int i = 1; i < 64; ++i) {
            s += this.board[i].toString();
        }
        return s;
    }
}
