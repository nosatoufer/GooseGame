package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controler;
import heb.esi.goosegame.model.CaseType;
import heb.esi.goosegame.model.GooseGameException;
import heb.esi.goosegame.model.PlayerColor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Maxime
 */
public class CaseView extends Parent {
    
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
    
    private PlayerColor playerColor; // Couleur du joueur présent sur la 
    
    Controler controler;
    
    public CaseView(Controler controler, int pos, int x, int y) {
        this.controler = controler;
        
        this.pos = pos;
        
        this.x = x;
        this.y = y;
        this.playerColor = null;
        
        // Ajout de la couleur de fond de la case
        fond = new Rectangle(75,75,Color.WHITE);
        fond.setArcHeight(10);
        fond.setArcWidth(10);
        this.getChildren().add(fond);
        
        // Création du rectangle représentant le pion d'un futur joueur sur la case
        player = new Rectangle(10,10,Color.WHITE);
        player.setX(60);
        player.setY(60);
        this.getChildren().add(player);
        
        // Ajout du type de la case sur cette dernière
        caseTypeText = new Text("");
        caseTypeText.setFont(new Font(15));
        caseTypeText.setFill(Color.BLACK);
        caseTypeText.setX(10);
        caseTypeText.setY(35);
        this.getChildren().add(caseTypeText);
        
        // Création et ajout du numéro de la case sur cette dernière
        posText = new Text(Integer.toString(pos));
        posText.setFont(new Font(15));
        posText.setFill(Color.BLACK);
        posText.setX(10);
        posText.setY(20);
        this.getChildren().add(posText);
        
        // Positionnement de la case sur le plateau
        this.setTranslateX(this.x);
        this.setTranslateY(this.y);
        
        this.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                try {
                    controler.movePlayerToPos(pos);
                } catch (GooseGameException ex) {
                    Logger.getLogger(BoardView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void setType(CaseType type) {
        switch (type) {
            case GOOSE:
                caseTypeText.setText("Goose");
            break;
            case BRIDGE:
                caseTypeText.setText("Bridge");
            break;
            case INN:
                caseTypeText.setText("Inn");
            break;
            case WELL:
                caseTypeText.setText("Well");
            break;
            case MAZE:
                caseTypeText.setText("Maze");
            break;
            case JAIL:
                caseTypeText.setText("Jail");
            break;
            case DEATH:
                caseTypeText.setText("Death");
            break;
            case START:
                caseTypeText.setText("Start");
            break;
            case END:
                caseTypeText.setText("End");
            break;
        }
    }
    
    public void setPlayer(PlayerColor color) {
        this.playerColor = color;
        if (color != null) {
            switch (color) {
                case GREEN:
                    player.setFill(Color.GREEN);
                    player.setStroke(Color.BLACK);
                break;
                case PINK:
                    player.setFill(Color.PINK);
                    player.setStroke(Color.BLACK);
                break;
                case BLUE:
                    player.setFill(Color.BLUE);
                    player.setStroke(Color.BLACK);
                break;
                case YELLOW:
                    player.setFill(Color.YELLOW);
                    player.setStroke(Color.BLACK);
                break;
                case PURPLE:
                    player.setFill(Color.PURPLE);
                    player.setStroke(Color.BLACK);
                break;
                case RED:
                    player.setFill(Color.RED);
                    player.setStroke(Color.BLACK);
                break;
                case BLACK:
                    player.setFill(Color.BLACK);
                    player.setStroke(Color.BLACK);
                break;
                case WHITE:
                    player.setFill(Color.WHITE);
                    player.setStroke(Color.BLACK);
                break;
            }
        } else {
            player.setFill(Color.WHITE);
            player.setStroke(Color.WHITE);
        }
    }
    
    public PlayerColor getPlayer() {
        return this.playerColor;
    }
}
