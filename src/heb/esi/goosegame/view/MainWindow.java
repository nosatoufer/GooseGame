package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controler;
import heb.esi.goosegame.model.GooseGameException;
import heb.esi.goosegame.model.PlayerColor;

import java.util.ArrayList;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
    private final Text dicesSumText;
    private final Text playerText;
    
    private final BoardView board;
    
    private final BorderPane borderpane;
    private final GridPane gridpane;
    
    private final Stage mainStage;
    
    private final MenuBar menuBar;
    private final Menu menuFile;
    private final MenuItem newGame;
    private final MenuItem quit;
    private final Menu menuGame;
    private final MenuItem addPlayer;
    private final MenuItem startGame;
    
    public MainWindow(Controler controler) {
        // Création du controler avec attachement du modèle (pour que le controler puisse agir sur le model)
        this.controler = controler;
        
        mainStage = new Stage();
        mainStage.setTitle("Goose Game");
        mainStage.setResizable(false);
        Group mainGroup = new Group();
        Scene mainScene = new Scene(mainGroup, 835, 650);
        
        mainStage.setScene(mainScene);        
        
        // Barre de menu
        menuBar = new MenuBar();
 
        // --- Menu File
        menuFile = new Menu("File");
        newGame = new MenuItem("New Game");
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                startGame.setDisable(false);
                addPlayer.setDisable(false);
                board.reset();
                controler.newGame();
            }
        });
        menuFile.getItems().addAll(newGame);
        quit = new MenuItem("Quitter");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainStage.close();
            }
        });
        menuFile.getItems().addAll(quit);
 
        // --- Menu Game
        menuGame = new Menu("Game");
        addPlayer = new MenuItem("Add player");
        addPlayer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                PlayerChoice playerChoice = new PlayerChoice(mainStage, controler);
                addPlayer.setDisable(true);
            }
        });
        menuGame.getItems().addAll(addPlayer);
        startGame = new MenuItem("Start game");
        startGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    controler.startGame(); // On débute la partie
                    startGame.setDisable(true);
                } catch (GooseGameException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur lors du début de la partie");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                }
            }
        });
        menuGame.getItems().addAll(startGame);
        
        // Ajout des menu à la barre des menus
        menuBar.getMenus().addAll(menuFile, menuGame);
        
        // Boutons de l'interface de jeu
        throwDicesButton = new Button();
        throwDicesButton.setText("Lancer le dé");
        throwDicesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    // On fait rouler les dés
                    controler.rollDice();
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
        
        // Texte permettant d'afficher le joueur suivant ou le gagnant
        playerText = new Text("");
        playerText.setFont(new Font(15));
        
        board = new BoardView(controler);
        
        gridpane = new GridPane();
        gridpane.setVgap(4);
        gridpane.setHgap(10);
        gridpane.setPadding(new Insets(5, 5, 5, 5));
        gridpane.add(throwDicesButton, 0, 0);
        gridpane.add(dicesSumText, 1, 0);
        gridpane.add(playerText, 2, 0);
        gridpane.add(board, 0, 1, 3, 1);

        borderpane = new BorderPane();
        borderpane.setTop(menuBar);
        borderpane.setCenter(gridpane);
        
        mainGroup.getChildren().add(borderpane);
        
        mainStage.show();
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
