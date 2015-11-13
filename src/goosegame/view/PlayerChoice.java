package goosegame.view;

import goosegame.controler.Controler;
import goosegame.model.PlayerColor;
import goosegame.model.GooseGameException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 *
 * @author Maxime
 */
public class PlayerChoice extends Parent {
    
    Controler controler;
    
    public PlayerChoice(Stage parent, Controler controler){
        this.controler = controler;
        
        Stage playerChoiceStage = new Stage();
        playerChoiceStage.setTitle("Ajout des joueurs");
        Group playerChoiceGroup = new Group();
        Scene playerChoiceScene = new Scene(playerChoiceGroup);
        playerChoiceStage.initOwner(parent);
        playerChoiceStage.initModality(Modality.WINDOW_MODAL);
        playerChoiceGroup.getChildren().add(this);
        playerChoiceStage.setScene(playerChoiceScene);
        
        // Création de la liste déroulante contenant les couleurs des joueurs
        ComboBox<String> colorComboBox = new ComboBox();
        ObservableList<String> myComboBoxData = FXCollections.observableArrayList();
        myComboBoxData.add("Vert");
        myComboBoxData.add("Rose");
        myComboBoxData.add("Bleu");
        myComboBoxData.add("Jaune");
        myComboBoxData.add("Pourpre");
        myComboBoxData.add("Rouge");
        myComboBoxData.add("Noir");
        myComboBoxData.add("Blanc");
        colorComboBox.setItems(myComboBoxData);
        
        // Création du label affichant les joueurs déjà ajoutés :
        Text listOfPlayersText = new Text("");
        listOfPlayersText.setFont(new Font(12));
        listOfPlayersText.setFill(Color.BLACK);
        
        // Création du bouton permettant d'ajouter le joueur de la couleur sélectionnée dans la liste déroulante
        Button addPlayerButton = new Button();
        addPlayerButton.setText("Ajouter un joueur");
        addPlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    String result = colorComboBox.getSelectionModel().getSelectedItem();
                    PlayerColor color = null; 
                    
                    switch (result) {
                        case "Vert":
                            myComboBoxData.remove("Vert");
                            color = PlayerColor.GREEN;
                        break;
                        case "Rose":
                            myComboBoxData.remove("Rose");
                            color = PlayerColor.PINK;
                        break;
                        case "Bleu":
                            myComboBoxData.remove("Bleu");
                            color = PlayerColor.BLUE;
                        break;
                        case "Jaune":
                            myComboBoxData.remove("Jaune");
                            color = PlayerColor.YELLOW;
                        break;
                        case "Pourpre":
                            myComboBoxData.remove("Pourpre");
                            color = PlayerColor.PURPLE;
                        break;
                        case "Rouge":
                            myComboBoxData.remove("Rouge");
                            color = PlayerColor.RED;
                        break;
                        case "Noir":
                            myComboBoxData.remove("Noir");
                            color = PlayerColor.BLACK;
                        break;
                        case "Blanc":
                            myComboBoxData.remove("Blanc");
                            color = PlayerColor.WHITE;
                        break;
                    }
                    
                    controler.newPlayer(color);
                    if (listOfPlayersText.getText().equals("")) {
                        listOfPlayersText.setText("Joueurs : "+color);
                    } else {
                        listOfPlayersText.setText(listOfPlayersText.getText()+", "+color);
                    }
                    
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
        
        // Création du bouton permettant de commencer la partie
        Button startButton = new Button();
        startButton.setText("Commencer la partie");
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    controler.startGame(); // On débute la partie
                    playerChoiceStage.close(); // On ferme la fenêtre de choix du joueur
                } catch (GooseGameException ex) {
                    Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erreur lors du début de la partie");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                }
            }
        });
        
        // Création du layout en grille pour agencer les widgets
        GridPane gridpane = new GridPane();
        gridpane.setVgap(4);
        gridpane.setHgap(10);
        gridpane.setPadding(new Insets(5, 5, 5, 5));
        gridpane.add(colorComboBox, 0, 0);
        gridpane.add(addPlayerButton, 1, 0);
        gridpane.add(startButton, 2, 0);
        gridpane.add(listOfPlayersText, 0, 1, 3, 1);

        this.getChildren().add(gridpane);
        
        playerChoiceStage.show();
    }
}
