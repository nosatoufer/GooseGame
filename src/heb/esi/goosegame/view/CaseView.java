package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.model.CaseType;
import heb.esi.goosegame.model.GooseGameException;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * Classe représentant une case dans l'interface graphique. Il s'agit d'un Java
 * Bean qui prévient ses listeners lorsque la couleur du joueur présent sur sa
 * case change. La case peut être "allumée" (jaune) lorsqu'elle requiert une
 * interaction de la part de l'utilisateur (bouger son pion par exemple).
 *
 * @author Maxime
 */
public class CaseView extends Parent implements Serializable {

    Controller controler;

    // Coordonées graphiques de la case sur le plateau :
    private final int x; // Abscisse
    private final int y; // Ordonnée

    // Elements graphiques composants la case :
    private final Rectangle fond;
    private final Text caseTypeText; // Label décrivant l'effet de la case
    private final Text posText; // Label indiquant le numéro de la case
    private final ArrayList<CustomRectangle> players; // Element permettant d'afficher le joueur sur la case

    /**
     *
     */
    protected BooleanProperty playerMoved;

    /**
     *
     * @param controler
     * @param pos
     * @param x
     * @param y
     */
    public CaseView(Controller controler, int pos, int x, int y) {
        this.controler = controler;

        this.x = x;
        this.y = y;

        // Ajout de la couleur de fond de la case
        this.fond = new Rectangle(75, 75, Color.WHITE);
        this.fond.setArcHeight(10);
        this.fond.setArcWidth(10);
        this.getChildren().add(this.fond);
        
        this.playerMoved = new SimpleBooleanProperty(false);

        // Ajout des cases de joueurs
        this.players = new ArrayList<>();
        for (int i=0; i<8; i++) {
            this.players.add(new CustomRectangle(10, 10, Color.TRANSPARENT));
            this.players.get(i).setX(10+(15*(i%4)));
            if (i<4) {
                this.players.get(i).setY(60);
            } else {
                this.players.get(i).setY(45);
            }
            this.players.get(i).colorProperty().addListener(new ChangeListener<Color>() {
                @Override
                public void changed(ObservableValue<? extends Color> ov, Color t, Color t1) {
                    if (t != t1) {
                        switchPlayerMoved();
                    }
                }
            });
            this.getChildren().add(this.players.get(i));
        }

        // Ajout du type de la case sur cette dernière
        this.caseTypeText = new Text("");
        this.caseTypeText.setFont(new Font(15));
        this.caseTypeText.setFill(Color.BLACK);
        this.caseTypeText.setX(10);
        this.caseTypeText.setY(35);
        this.getChildren().add(this.caseTypeText);

        // Création et ajout du numéro de la case sur cette dernière
        this.posText = new Text(Integer.toString(pos));
        this.posText.setFont(new Font(15));
        this.posText.setFill(Color.BLACK);
        this.posText.setX(10);
        this.posText.setY(20);
        this.getChildren().add(this.posText);

        // Positionnement de la case sur le plateau
        this.setTranslateX(this.x);
        this.setTranslateY(this.y);

        // Implémentation de l'action qui a lieu lorsqu'on clique sur la case
        // (pour déplacer son joueur normalement)
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    // On fait appel au controleur pour déplacer le joueur
                    controler.movePlayerToPos(pos);
                    // On "éteind la case" car le joueur a déplacé son pion
                    turnOff();
                } catch (GooseGameException ex) {
                    // Si le joueur a cliqué sur la case par inadvertance (mauvaise
                    // case ou mauvais moment), une exception est levée et est
                    // attrapée ici.
                }
            }
        });
    }

    /**
     *
     * @param type
     */
    public void setType(CaseType type) {
        this.caseTypeText.setText(type.desc());
    }

    /**
     * On "allume" la case pour signaler au joueur qu'il doit déplacer son pion
     * sur cette case.
     */
    public void turnOn() {
        this.fond.setFill(Color.YELLOW);
    }

    /**
     * On "éteind" la case lorsque la case a été cliquée
     */
    public void turnOff() {
        this.fond.setFill(Color.WHITE);
    }
    
    /**
     *
     * @param color
     */
    public void delete(Color color) {
        for (CustomRectangle player : this.players) {
            if (player.getColor().equals(color) && (!player.getColor().equals(Color.TRANSPARENT))) {
                player.setColor(Color.TRANSPARENT);
            }
        }
    }
    
    /**
     *
     * @param color
     * @return
     */
    public boolean isPresent(Color color) {
        for (CustomRectangle rect : this.players) {
            if (rect.getColor().equals(color)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     *
     */
    public void reset() {
        for (CustomRectangle rect : this.players) {
            rect.setColor(Color.TRANSPARENT);
        }
        turnOff();
    }
    
    /**
     *
     * @param color
     */
    public void addPlayer(Color color) {
        if (!this.isPresent(color)) {
            for (CustomRectangle rect : this.players) {
                if (rect.getColor().equals(Color.TRANSPARENT)) {
                    rect.setColor(color);
                    break;
                }
            }
        }
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
