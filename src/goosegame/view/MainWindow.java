package goosegame.view;

import goosegame.controler.Controler;
import goosegame.model.GooseGameException;
import goosegame.model.PlayerColor;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author Maxime
 */
public class MainWindow extends Parent implements View {
    
    private final Controler controler;
    
    private Button throwDicesButton;
    private Button playButton;
    private final Text dicesSumText;
    private final Text playerText;
    
    private final BoardView board;
    
    private final GridPane gridpane;
    
    private final Stage mainStage;
    
    public MainWindow(Controler controler) {
        // Création du controler avec attachement du modèle (pour que le controler puisse agir sur le model)
        this.controler = controler;
        
        mainStage = new Stage();
        mainStage.setTitle("Goose Game");
        Group mainGroup = new Group();
        Scene mainScene = new Scene(mainGroup, 845, 635);
        
        //mainGroup.getChildren().add(this);
        
        mainStage.setScene(mainScene);        
        
        throwDicesButton = new Button();
        throwDicesButton.setText("Lancer le dé");
        throwDicesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    // On fait rouler les dés
                    controler.rollDice();
                    
                    throwDicesButton.setDisable(true);
                    playButton.setDisable(false);
                } catch (GooseGameException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur lors du lancement des dés de la partie");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                }
            }
        });
        
        // Texte permettant d'afficher le résultat des dés
        dicesSumText = new Text("/");
        dicesSumText.setFont(new Font(15));
        
        playButton = new Button();
        playButton.setText("Déplacer le pion");
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    // On fait jouer le joueur
                    controler.play();
                    
                    if (controler.isGameOver()) {
                        throwDicesButton.setDisable(true);
                    } else {
                        throwDicesButton.setDisable(false);
                    }
                    playButton.setDisable(true);
                } catch (GooseGameException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur lors du déplacement du pion");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                }
            }
        });
        playButton.setDisable(true);
        
        // Texte permettant d'afficher le joueur suivant le gagnant
        playerText = new Text("");
        playerText.setFont(new Font(15));
        
        board = new BoardView(controler.getSpecialCases());
        
        gridpane = new GridPane();
        gridpane.setVgap(4);
        gridpane.setHgap(10);
        gridpane.setPadding(new Insets(5, 5, 5, 5));
        gridpane.add(throwDicesButton, 0, 0);
        gridpane.add(dicesSumText, 1, 0);
        gridpane.add(playButton, 2, 0);
        gridpane.add(playerText, 3, 0);
        gridpane.add(board, 0, 1, 4, 1);

        mainGroup.getChildren().add(gridpane);
        
        mainStage.show();
    
        PlayerChoice playerChoice = new PlayerChoice(mainStage, controler);
    }
    
    @Override
    public void refresh() {
        // Méthode de mise à jour de l'interface graphique, appelée par le modèle lors d'un changement

        // On met à jour la position des joueurs sur le plateau
        ArrayList<Pair<PlayerColor,Integer>> players = controler.getPlayerPos();
        for (Pair<PlayerColor, Integer> player : players) {
            board.updatePlayer(player.getKey(), player.getValue());
        }
        
        // Si le jeu n'est pas encore fini :
        if (controler.isGameStarted()) {        
            // On affiche le résultat des dés
            dicesSumText.setText(Integer.toString(controler.getDicesSum()));

            // On affiche le joueur suivant
            playerText.setText("Joueur suivant : "+controler.getCurrentPlayerColor());

        // Si le jeu est fini :
        } else if (controler.isGameOver()) {
            // On affiche le joueur gagnant
            playerText.setText("Joueur gagnant : "+controler.getCurrentPlayerColor());
            
            // On le notifie par un message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fin du jeu !");
                alert.setHeaderText(null);
                alert.setContentText("Le jeu est fini !");

                alert.showAndWait();
        }
    }
}
