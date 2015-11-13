package goosegame.view;

import goosegame.model.Board;
import goosegame.model.PlayerColor;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Maxime
 */
public class BoardView extends Parent {
    
    private final CaseView[] cases;
    
    private final Rectangle fond;
    
    public BoardView(Board board) {
        // Création et ajout de la couleur de fond du plateau
        fond = new Rectangle();
        fond.setWidth(835);
        fond.setHeight(595);
        fond.setArcWidth(30);
        fond.setArcHeight(30);
        fond.setFill(Color.GREY);
        this.getChildren().add(fond);
        
        // Initialisation du tableau contenant les représentations graphiques des cases
        cases = new CaseView[64];
        
        // Création des cases du plateau grâce au plateau modèle
        for (int i=0; i<64; ++i) {
            cases[i] = new CaseView(i, board.getCase(i).type(), 80*(i%10)+20, 80*(i/10)+20);
            this.getChildren().add(cases[i]);
        }
    }
    
    public void updatePlayer(PlayerColor color, int pos) {
        // Mise à jour de la position du joueur de couleur color :
        
        // On efface son ancien pion s'il est déjà affiché (en parcourant le tableau) :
        for (int i=0; i<64; ++i) {
            if (cases[i].getPlayer() == color) {
                cases[i].setPlayer(null);
            }
        }
        
        // On recolorie son nouvel emplacement
        cases[pos].setPlayer(color);
    }
}
