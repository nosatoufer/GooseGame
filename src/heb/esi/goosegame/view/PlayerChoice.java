package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.model.PlayerColor;
import heb.esi.goosegame.model.GooseGameException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    ComboBox<PlayerColor> colorComboBox;
    Text listOfPlayersText;
    Button addPlayerButton;
    Button closeButton;
    GridPane gridpane;
    
    public PlayerChoice(Stage parent, Controller controler){
        this.controller = controler;
        
        // Création du stage, groupe et scene pour la fenêtre
        this.playerChoiceStage = new Stage();
        this.playerChoiceStage.setTitle("Ajout des joueurs");
        this.playerChoiceStage.setResizable(false);
        this.playerChoiceGroup = new Group();
        this.playerChoiceScene = new Scene(this.playerChoiceGroup);
        this.playerChoiceStage.initOwner(parent);
        this.playerChoiceStage.initModality(Modality.WINDOW_MODAL);
        this.playerChoiceGroup.getChildren().add(this);
        this.playerChoiceStage.setScene(this.playerChoiceScene);
        
        // Création de la liste des couleurs possibles :
        this.colorComboBox = new ComboBox<PlayerColor>();
        this.colorComboBox.getItems().addAll(
            PlayerColor.GREEN, 
            PlayerColor.PINK, 
            PlayerColor.BLUE,
            PlayerColor.YELLOW,
            PlayerColor.PURPLE,
            PlayerColor.RED,
            PlayerColor.BLACK, 
            PlayerColor.WHITE);
        
        // Création du label affichant les joueurs déjà ajoutés :
        this.listOfPlayersText = new Text("");
        this.listOfPlayersText.setFont(new Font(12));
        this.listOfPlayersText.setFill(Color.BLACK);
        
        // Création du bouton permettant d'ajouter un joueur (basé sur la couleur)
        this.addPlayerButton = new Button();
        this.addPlayerButton.setText("Ajouter un joueur");
        this.addPlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    // On sélectione la couleur active de la liste déroulante
                    PlayerColor color = colorComboBox.getSelectionModel().getSelectedItem();
                    
                    // On ajoute la couleur
                    controler.newPlayer(color);
                    
                    // On la supprime de la liste déroulante
                    colorComboBox.getItems().remove(color);
                    
                    // On met à jour la liste des joueurs
                    if (listOfPlayersText.getText().equals("")) {
                        listOfPlayersText.setText("Joueurs : "+color);
                    } else {
                        listOfPlayersText.setText(listOfPlayersText.getText()+", "+color);
                    }
                    
                // Si une exception est levée (pas de couleur sélectionnée
                // ou couleur du joueur déjà utilisée (impossible normalement)),
                // on affiche un message d'erreur.
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
                }
            }
        });
        
        // Création du bouton permettant de terminer la saisie des joueurs,
        // il vérifie que le nombre de joueur est correct et ferme la fenêtre.
        this.closeButton = new Button();
        this.closeButton.setText("Choix des joueurs terminés");
        this.closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    controler.checkPlayers(); // On vérifie que le nombre de joueur est correct
                    playerChoiceStage.close(); // On ferme la fenêtre de choix du joueur
                } catch (GooseGameException ex) {
                    // Si le nombre de joueur est incorrect, une exception est
                    // levée et on lance une exception qui affiche une erreur.
                    Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erreur lors du début de la partie");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                }
            }
        });
        
        // Création du layout en grille pour agencer les widgets
        this.gridpane = new GridPane();
        this.gridpane.setVgap(4);
        this.gridpane.setHgap(10);
        this.gridpane.setPadding(new Insets(5, 5, 5, 5));
        this.gridpane.add(this.colorComboBox, 0, 0);
        this.gridpane.add(this.addPlayerButton, 1, 0);
        this.gridpane.add(this.closeButton, 2, 0);
        this.gridpane.add(this.listOfPlayersText, 0, 1, 3, 1);

        // On ajoute le layout à la fenêtre
        this.getChildren().add(this.gridpane);
        
        // On affiche le tout
        this.playerChoiceStage.show();
    }
}
