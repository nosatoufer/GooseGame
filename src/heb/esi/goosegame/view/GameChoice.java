package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.db.DBException;
import heb.esi.goosegame.model.GooseGameException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Classe créant la fenêtre qui permet de choisir une partie enregistrée
 *
 * @author Maxime
 */
public class GameChoice extends Parent {
    // Lien vers le controleur pour indiquer les joueurs
    Controller controller;
    
    // Elements graphiques de la fenêtre de choix des joueurs
    Stage gameChoiceStage;
    Group gameChoiceGroup;
    Scene gameChoiceScene;
    
    ComboBox<String> gamesComboBox;
    ObservableList<String> gameComboBoxData;
    
    ColorPicker colorPicker;
    Button loadGameButton;
    Button closeButton;
    GridPane gridpane;
    
    /**
     *
     * @param parent
     * @param controler
     */
    public GameChoice(MainWindow parent, Controller controler){
        try {
            this.controller = controler;
            
            // Création du stage, groupe et scene pour la fenêtre
            this.gameChoiceStage = new Stage();
            this.gameChoiceStage.setTitle("Chargement d'une partie");
            this.gameChoiceStage.setResizable(false);
            this.gameChoiceStage.setOnCloseRequest(new EventHandler<WindowEvent>() { 
                @Override 
                public void handle(WindowEvent event) { 
                    parent.refresh();
                }
            });
            this.gameChoiceGroup = new Group();
            this.gameChoiceScene = new Scene(this.gameChoiceGroup);
            this.gameChoiceStage.initOwner(parent.getStage());
            this.gameChoiceStage.initModality(Modality.WINDOW_MODAL);
            this.gameChoiceGroup.getChildren().add(this);
            this.gameChoiceStage.setScene(this.gameChoiceScene);
            
            this.gamesComboBox = new ComboBox();
            this.gameComboBoxData = FXCollections.observableArrayList();
            
            // On récupère la liste des parties sauvegardées
            ArrayList<String> savedGames = this.controller.getActiveSavedGames();
            
            for (String game : savedGames) {
                gameComboBoxData.add(game);
            }
            
            gamesComboBox.setItems(gameComboBoxData);
            
            // Création du bouton permettant de charger la partie demandée
            this.loadGameButton = new Button();
            this.loadGameButton.setText("Choisir");
            this.loadGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    try {
                        String result = gamesComboBox.getSelectionModel().getSelectedItem();
                        // On charge la partie dans le controleur
                        controller.loadGame(result);
                        if (result != null) {
                            gameChoiceStage.close(); // On ferme la fenêtre
                        }
                    } catch (DBException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur d'accès à la base de donnée");
                            alert.setHeaderText(null);
                            alert.setContentText(ex.getMessage());

                            alert.showAndWait();
                    } catch (GooseGameException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur lors de la création du jeu");
                            alert.setHeaderText(null);
                            alert.setContentText(ex.getMessage());

                            alert.showAndWait();
                    }
                }
            });
            
            // Création du bouton permettant de quitter le chargement d'une partie chargée
            this.closeButton = new Button();
            this.closeButton.setText("Annuler");
            this.closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    gameChoiceStage.close(); // On ferme la fenêtre de choix du joueur
                }
            });
            
            // Création du layout en grille pour agencer les widgets
            this.gridpane = new GridPane();
            this.gridpane.setVgap(4);
            this.gridpane.setHgap(10);
            this.gridpane.setPadding(new Insets(5, 5, 5, 5));
            this.gridpane.add(this.gamesComboBox, 0, 0);
            this.gridpane.add(this.loadGameButton, 2, 0);
            this.gridpane.add(this.closeButton, 3, 0);
            
            // On ajoute le layout à la fenêtre
            this.getChildren().add(this.gridpane);
            
            // On affiche le tout
            this.gameChoiceStage.show();
        } catch (DBException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur d'accès à la base de donnée");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
        }
    }
}
