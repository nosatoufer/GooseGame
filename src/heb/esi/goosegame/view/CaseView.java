package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.model.CaseType;
import heb.esi.goosegame.model.GooseGameException;
import java.io.Serializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
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

    // Numéro de la case
    private final int pos;

    // Coordonées graphiques de la case sur le plateau :
    private final int x; // Abscisse
    private final int y; // Ordonnée

    // Elements graphiques composants la case :
    private final Rectangle fond;
    private final Text caseTypeText; // Label décrivant l'effet de la case
    private final Text posText; // Label indiquant le numéro de la case
    private final Rectangle player; // Element permettant d'afficher le joueur sur la case

    protected ObjectProperty<Color> playerColor; // Couleur du joueur présent sur la case

    public CaseView(Controller controler, int pos, int x, int y) {
        this.controler = controler;

        this.pos = pos;

        this.x = x;
        this.y = y;

        // Propriété prise en compte par le bean (couleur du joueur présent
        // sur la case)
        this.playerColor = new ObjectPropertyBase<Color>(null) {
            @Override
            public Object getBean() {
                return this;
            }

            @Override
            public String getName() {
                return "Color property";
            }
        };

        // Ajout de la couleur de fond de la case
        this.fond = new Rectangle(75, 75, Color.WHITE);
        this.fond.setArcHeight(10);
        this.fond.setArcWidth(10);
        this.getChildren().add(this.fond);

        // Création du rectangle représentant le pion d'un futur joueur sur la case
        this.player = new Rectangle(10, 10, Color.WHITE);
        this.player.setX(60);
        this.player.setY(60);
        this.getChildren().add(this.player);

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
     * Retourne la couleur du joueur présent sur la case
     *
     * @return Couleur du joueur présent sur la case
     */
    public final Color getPlayerColor() {
        return this.playerColor.get();
    }

    /**
     * On place le joueur de couleur color sur la case
     *
     * @param color
     */
    public final void setPlayerColor(Color color) {
        this.playerColor.set(color);

        if (color != null) {
            player.setFill(color);
            player.setStroke(Color.BLACK);
        } else {
            player.setFill(Color.WHITE);
            player.setStroke(Color.WHITE);
        }
    }

    /**
     * Méthode du beans permettant d'attacher un listener sur l'attribut
     * PlayerColor
     *
     * @return ObjectProperty
     */
    public final ObjectProperty<Color> playerColorProperty() {
        return this.playerColor;
    }
}
