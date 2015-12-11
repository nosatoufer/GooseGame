package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.db.DBException;
import heb.esi.goosegame.model.GooseGameException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Classe créant la fenêtre qui permet de choisir les joueurs d'une partie
 *
 * @author Maxime
 */
public class PlayerChoice extends Parent {
    // Lien vers le controleur pour indiquer les joueurs
    Controller controller;
    
    // Elements graphiques de la fenêtre de choix des joueurs
    Stage playerChoiceStage;
    Group playerChoiceGroup;
    Scene playerChoiceScene;
    ColorPicker colorPicker;
    TextField textField;
    Button addPlayerButton;
    Button closeButton;
    GridPane gridpane;
    
    /**
     *
     * @param parent
     * @param controler
     */
    public PlayerChoice(MainWindow parent, Controller controler){
        this.controller = controler;
        
        // Création du stage, groupe et scene pour la fenêtre
        this.playerChoiceStage = new Stage();
        this.playerChoiceStage.setTitle("Ajout des joueurs");
        this.playerChoiceStage.setResizable(false);
        this.playerChoiceStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Commencer la partie");
                alert.setHeaderText(null);
                alert.setContentText("Voulez-vous commencer la partie ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    parent.startGame();
                }
                playerChoiceStage.close();
            }
        });     
        
        this.playerChoiceGroup = new Group();
        this.playerChoiceScene = new Scene(this.playerChoiceGroup);
        this.playerChoiceStage.initOwner(parent.getStage());
        this.playerChoiceStage.initModality(Modality.WINDOW_MODAL);
        this.playerChoiceGroup.getChildren().add(this);
        this.playerChoiceStage.setScene(this.playerChoiceScene);
        
        this.colorPicker = new ColorPicker(Color.BLACK);
        
        // Création du label affichant les joueurs déjà ajoutés :
        this.textField = new TextField();
        
        // Création du bouton permettant d'ajouter un joueur (basé sur la couleur)
        this.addPlayerButton = new Button();
        this.addPlayerButton.setText("Ajouter un joueur");
        this.addPlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    // On ajoute la couleur
                    controler.newPlayer(textField.getText(), colorPicker.getValue());
                    textField.setText("");
                    colorPicker.setValue(Color.BLACK);
                                        
                    // Si une exception est levée (couleur ou nom du joueur déjà 
                    // utilisée on affiche un message d'erreur.
                } catch (GooseGameException ex) {
                    Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erreur lors de l'ajout du joueur");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                } catch (NullPointerException ex) {
                    Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erreur lors de l'ajout du joueur");
                        alert.setHeaderText(null);
                        alert.setContentText("Aucune couleur sélectionnée");

                        alert.showAndWait();
                } catch (DBException ex) {
                    Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erreur lors de la sauvegarde dans la base de donnée");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                }
            }
        });
        
        // Création du bouton permettant de terminer la saisie des joueurs,
        // il vérifie que le nombre de joueur est correct et ferme la fenêtre.
        this.closeButton = new Button();
        this.closeButton.setText("Ok");
        this.closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Commencer la partie");
                alert.setHeaderText(null);
                alert.setContentText("Voulez-vous commencer la partie ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    parent.startGame();
                }
                playerChoiceStage.close(); // On ferme la fenêtre de choix du joueur
            }
        });
        
        // Création du layout en grille pour agencer les widgets
        this.gridpane = new GridPane();
        this.gridpane.setVgap(4);
        this.gridpane.setHgap(10);
        this.gridpane.setPadding(new Insets(5, 5, 5, 5));
        this.gridpane.add(this.colorPicker, 0, 0);
        this.gridpane.add(this.textField, 1, 0);
        this.gridpane.add(this.addPlayerButton, 2, 0);
        this.gridpane.add(this.closeButton, 3, 0);

        // On ajoute le layout à la fenêtre
        this.getChildren().add(this.gridpane);
        
        // On affiche le tout
        this.playerChoiceStage.show();
    }
}
