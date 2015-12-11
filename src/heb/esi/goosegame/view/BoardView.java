package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.model.CaseType;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

/**
 * Classe représentant le plateau du jeu (liste de CaseView).
 * Il s'agit d'un Java Beans qui permet d'indiquer lorsqu'un joueur vient de
 * se déplacer (utile notamment à la fenêtre pour activer le bouton permettant
 * au joueur suivant de lancer le dé).
 *
 * @author Maxime
 */
public class BoardView extends Parent implements Serializable {
    
    private final CaseView[] cases;
    
    private final Rectangle fond;
    
    private final Controller controller;
    
    // Boléen indiquant si un joueur vient de bouger, attribut du Beans

    /**
     *
     */
    protected BooleanProperty playerMoved;
    
    /**
     *
     * @param controller
     */
    public BoardView(Controller controller) {
        // Liaison avec le controleur
        this.controller = controller;
        
        this.playerMoved = new SimpleBooleanProperty(false);
        
        // Création et ajout de la couleur de fond du plateau
        this.fond = new Rectangle();
        this.fond.setWidth(835);
        this.fond.setHeight(595);
        this.fond.setArcWidth(30);
        this.fond.setArcHeight(30);
        this.fond.setFill(Color.GREEN);
        this.getChildren().add(fond);
        
        
        // Initialisation du tableau contenant les représentations graphiques des cases
        this.cases = new CaseView[64];
        
        // Création des cases du plateau grâce au plateau modèle
        for (int i=0; i<64; ++i) {
            this.cases[i] = new CaseView(controller, i, 80*(i%10)+20, 80*(i/10)+20);
            
            // On s'attache en tant que Listener à une case, ainsi lorsqu'un joueur
            // se sera déplacé on peut changer la valeur de playerMoved pour indiquer
            // à nos listener (notamment la fenêtre principale) qu'un déplacement vient
            // d'avoir lieu.
            this.cases[i].playerMovedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    // Si un joueur a bougé, on réinitialise la variable du plateau à false
                    if (t != t1) {
                        setPlayerMoved(true);
                    }
                }
            });
            
            this.getChildren().add(this.cases[i]);
        }
        
        // Ajout des cases spéciales sur le plateau graphique
        ArrayList<Pair<Integer, CaseType>> specialCases = controller.getSpecialCases();
        for (int i=0; i<specialCases.size(); ++i) {
            this.cases[specialCases.get(i).getKey()].setType(specialCases.get(i).getValue());
        }
    }
    
    /**
     * Mise à jour de la position du joueur de couleur color.
     * @param color
     * @param pos
     */
    public void updatePlayer(Color color, int pos) {
        // On efface son ancien pion s'il est déjà affiché (en parcourant le tableau) :
        for (int i=0; i<64; ++i) {
            this.cases[i].delete(color);
        }
        
        // On recolorie son nouvel emplacement
        this.cases[pos].addPlayer(color);
    }
    
    /**
     * Réinitalisation du plateau (on efface les pions des joueurs).
     */
    public void reset() {
        for (int i=0; i<64; ++i) {
            this.cases[i].reset();
        }
    }
    
    /**
     * Permet "d'allumer" la case située à la position pos.
     * @param pos
     */
    public void turnOn(int pos) {
        this.cases[pos].turnOn();
    }
    
    /**
     * Permet de modifier l'attribut playerMoved du Bean.
     * @param bool
     */
    public final void setPlayerMoved(boolean bool){
        this.playerMoved.set(bool);
    }
     
    /**
     * Permet d'inverser la valeur de playerMoved
     */
    public final void switchPlayerMoved(){
        if (this.playerMoved.get() == true) {
            this.playerMoved.set(false);
        } else {
            this.playerMoved.set(true);
        }
    }
     
    /**
     * Permet d'accéder à l'attribut playerMoved du Bean.
     * @return Est-ce qu'un joueur a bougé ?
     */
    public final boolean isPlayerMoved(){
        return this.playerMoved.get();
    }
     
    /**
     * Permet d'accrocher un Listener à l'attribut playerMoved du Bean.
     * @return BooleanProperty
     */
    public final BooleanProperty playerMovedProperty(){
        return this.playerMoved;
    }
}
