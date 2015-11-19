package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controler;
import heb.esi.goosegame.model.CaseType;
import heb.esi.goosegame.model.GooseGameException;
import heb.esi.goosegame.model.PlayerColor;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

/**
 *
 * @author Maxime
 */
public class BoardView extends Parent {
    
    private final CaseView[] cases;
    
    private final Rectangle fond;
    
    private final Controler controler;
    
    public BoardView(Controler controler) {
        // Liaison avec le controleur
        this.controler = controler;
        
        // Création et ajout de la couleur de fond du plateau
        fond = new Rectangle();
        fond.setWidth(835);
        fond.setHeight(595);
        fond.setArcWidth(30);
        fond.setArcHeight(30);
        fond.setFill(Color.GREEN);
        this.getChildren().add(fond);
        
        
        // Initialisation du tableau contenant les représentations graphiques des cases
        cases = new CaseView[64];
        
        // Création des cases du plateau grâce au plateau modèle
        for (int i=0; i<64; ++i) {
            cases[i] = new CaseView(controler, i, 80*(i%10)+20, 80*(i/10)+20);
            this.getChildren().add(cases[i]);
        }
        // Ajout des cases spéciales sur le plateau graphique
        ArrayList<Pair<Integer, CaseType>> specialCases = controler.getSpecialCases();
        for (int i=0; i<specialCases.size(); ++i) {
            cases[specialCases.get(i).getKey()].setType(specialCases.get(i).getValue());
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
    
    public void reset() {
        for (int i=0; i<64; ++i) {
            cases[i].setPlayer(null);
        }
    }
}
