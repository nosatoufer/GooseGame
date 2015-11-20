package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.model.GooseGameException;
import heb.esi.goosegame.model.PlayerColor;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * Classe de la fenêtre principale du jeu.
 * Il s'agit de la vue qui reçoit les mises à jour du modèle par le controleur.
 * 
 * @author Maxime
 */
public class MainWindow extends Parent implements View {
    
    private final Controller controller;
    
    private final Button throwDicesButton;
    private final Text dicesSumText;
    private final Text playerText;
    
    private final BoardView board;
    
    private final BorderPane borderpane;
    private final GridPane gridpane;
    
    private final Stage mainStage;
    private final Group mainGroup;
    private final Scene mainScene;
    
    private final MenuBar menuBar;
    private final Menu menuFile;
    private final MenuItem newGame;
    private final MenuItem quit;
    private final Menu menuGame;
    private final MenuItem addPlayer;
    private final MenuItem startGame;
    
    public MainWindow(Controller controller) {
        // Création du controler avec attachement du modèle (pour que le controler puisse agir sur le model)
        this.controller = controller;
        
        // Création des éléments de la fenêtre de base
        this.mainStage = new Stage();
        this.mainStage.setTitle("Goose Game");
        this.mainStage.setResizable(false);
        this.mainGroup = new Group();
        this.mainScene = new Scene(this.mainGroup, 835, 650);
        this.mainStage.setScene(this.mainScene);        
        
        // Barre de menu
        this.menuBar = new MenuBar();
 
        // Menu File de la barre de menu
        this.menuFile = new Menu("File");
        // On ajoute l'item "New Game" au menu "File"
        this.newGame = new MenuItem("New Game");
        this.newGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // Réinitialise le plateau
                board.reset();
                // Crée une nouvelle partie
                controller.newGame();
                // Action : désactive et active les boutons correctement
                startGame.setDisable(true);
                addPlayer.setDisable(false);
                dicesSumText.setText("/");
                playerText.setText("");
                throwDicesButton.setDisable(true);
            }
        });
        this.menuFile.getItems().addAll(newGame);
        // On ajoute l'item "Quit" au menu "File"
        this.quit = new MenuItem("Quit");
        this.quit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // Action : quitte la fenêtre
                mainStage.close();
            }
        });
        this.menuFile.getItems().addAll(quit);
 
        // Menu Game de la barre de menu
        this.menuGame = new Menu("Game");
        // On ajoute l'item "Add players" au menu "Game"
        this.addPlayer = new MenuItem("Add players");
        this.addPlayer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // Action : demande la liste de joueurs qui joue à la partie
                PlayerChoice playerChoice = new PlayerChoice(mainStage, controller);
                // Désactive son MenuItem
                //addPlayer.setDisable(true);
                // Active le MenuItem permettant de commencer la partie
                startGame.setDisable(false);
            }
        });
        this.menuGame.getItems().addAll(addPlayer);
        // On ajoute l'item "Start Game" au menu "Game"
        this.startGame = new MenuItem("Start game");
        this.startGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // Action :
                try {
                    controller.startGame(); // On débute la partie
                    startGame.setDisable(true); // On désactive le MenuItem
                    throwDicesButton.setDisable(false);
                } catch (GooseGameException ex) {
                    // Si une erreur survient au début la partie, on affiche un
                    // message d'erreur.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur lors du début de la partie");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                }
            }
        });
        this.startGame.setDisable(true);
        this.menuGame.getItems().addAll(startGame);
        
        // Ajout des menu à la barre des menus
        this.menuBar.getMenus().addAll(menuFile, menuGame);
        
        // Boutons permettabt de lancer les dés
        this.throwDicesButton = new Button();
        this.throwDicesButton.setText("Lancer le dé");
        this.throwDicesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                // Action
                try {
                    // On fait rouler les dés dans le modèle
                    controller.rollDice();
                    // On désactive le bouton
                    throwDicesButton.setDisable(true);
                    // On allume la case sur lequel le joueur va devoir cliquer
                    // pour se déplacer
                    board.turnOn(controller.getNextCaseToMove());
                } catch (GooseGameException ex) {
                    // Si une erreur survient lors du clic sur le bouton, un
                    // message s'affiche à l'utilisateur.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur lors du lancement des dés de la partie");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                }
            }
        });
        this.throwDicesButton.setDisable(true);
        
        // Texte permettant d'afficher le résultat des dés
        this.dicesSumText = new Text("/");
        this.dicesSumText.setFont(new Font(15));
        
        // Texte permettant d'afficher le joueur suivant ou le gagnant
        this.playerText = new Text("");
        this.playerText.setFont(new Font(15));
        
        // Plateau du jeu
        this.board = new BoardView(controller);
        // On s'ajoute comme Listener de l'attribut playerMoved du plateau (attribut
        // indiquant si un joueur a bougé).
        this.board.playerMovedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                // Si un joueur a bougé, on réinitialise la variable du plateau à false
                board.setPlayerMoved(false);
                // On active le bouton pour lancer les dés du prochain joueur
                throwDicesButton.setDisable(false);
            }
        });
        
        // Layout permettant de mettre en page les éléments de la fenêtre
        this.gridpane = new GridPane();
        this.gridpane.setVgap(4);
        this.gridpane.setHgap(10);
        this.gridpane.setPadding(new Insets(5, 5, 5, 5));
        this.gridpane.add(throwDicesButton, 0, 0);
        this.gridpane.add(dicesSumText, 1, 0);
        this.gridpane.add(playerText, 2, 0);
        this.gridpane.add(board, 0, 1, 3, 1);

        // Layout permettant de mettre en la barre de menu avec le reste des éléments
        this.borderpane = new BorderPane();
        this.borderpane.setTop(menuBar);
        this.borderpane.setCenter(gridpane);
        
        this.mainGroup.getChildren().add(borderpane);
        
        this.mainStage.show();
    }
    
    /**
     * Méthode de mise à jour de l'interface graphique, appelée par le modèle lors d'un changement.
     */
    @Override
    public void refresh() {
        // On met à jour la position des joueurs sur le plateau
        ArrayList<Pair<PlayerColor,Integer>> players = this.controller.getPlayerPos();
        for (Pair<PlayerColor, Integer> player : players) {
            this.board.updatePlayer(player.getKey(), player.getValue());
        }
        
        // Si le jeu n'est pas encore fini :
        if (this.controller.isGameStarted()) {        
            // On affiche le résultat des dés
            this.throwDicesButton.setDisable(false);
            this.dicesSumText.setText(Integer.toString(this.controller.getDicesSum()));

            // On affiche le joueur suivant
            this.playerText.setText("Joueur suivant : "+this.controller.getCurrentPlayerColor());

        // Si le jeu est fini :
        } else if (this.controller.isGameOver()) {
            // On affiche le joueur gagnant
            this.playerText.setText("Joueur gagnant : "+this.controller.getCurrentPlayerColor());
            
            // On désactive le bouton de lancer de dé
            this.throwDicesButton.setDisable(true);
            
            // On le notifie par un message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fin du jeu !");
                alert.setHeaderText(null);
                alert.setContentText("Le jeu est fini !");

                alert.showAndWait();
        }
    }
}
