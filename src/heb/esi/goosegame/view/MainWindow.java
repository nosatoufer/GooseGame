package heb.esi.goosegame.view;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.db.DBException;
import heb.esi.goosegame.model.GooseGameException;

import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
    private final Text playerText1;
    private final Text playerText2;
    
    private final BoardView board;
    
    private final BorderPane borderpane;
    private final GridPane gridpane;
    
    private final Stage mainStage;
    private final Group mainGroup;
    private final Scene mainScene;
    
    private final MenuBar menuBar;
    private final Menu menuFile;
    private final MenuItem newGame;
    private final MenuItem loadGame;
    private final MenuItem saveGame;
    private final MenuItem quit;
    private final Menu menuHelp;
    private final MenuItem rules;
    private final MenuItem about;
    private final Menu menuGame;
    private final MenuItem addPlayer;
    private final MenuItem startGame;
    
    /**
     *
     * @param controller
     */
    public MainWindow(Controller controller) {
        // Création du controler avec attachement du modèle (pour que le controler puisse agir sur le model)
        this.controller = controller;
        
        // Création des éléments de la fenêtre de base
        this.mainStage = new Stage();
        this.mainStage.setTitle("Goose Game");
        this.mainStage.setResizable(false);
        this.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() { 
            @Override 
            public void handle(WindowEvent event) { 
                if (controller.isGameStarted()) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Voulez vous vraiment quitter la partie en cours ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        mainStage.close();
                    } else {
                        event.consume();
                    }
                } else {
                    mainStage.close();
                }
            } 
        });
        this.mainGroup = new Group();
        this.mainScene = new Scene(this.mainGroup, 835, 650);
        this.mainStage.setScene(this.mainScene);        
        
        // Barre de menu
        this.menuBar = new MenuBar();
 
        // Menu File de la barre de menu
        this.menuFile = new Menu("File");
        // On ajoute l'item "New Game" au menu "File"
        this.newGame = new MenuItem("New game");
        this.newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (controller.isGameStarted()) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Voulez vous vraiment quitter la partie en cours ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        // Réinitialise le plateau
                        board.reset();
                        // Crée une nouvelle partie
                        controller.newGame();
                        // Action : désactive et active les boutons correctement
                        startGame.setDisable(true);
                        addPlayer.setDisable(false);
                        dicesSumText.setText("/");
                        playerText1.setText("");
                        playerText2.setText("");
                        throwDicesButton.setDisable(true);
                    } else {
                        event.consume();
                    }
                } else {
                    // Réinitialise le plateau
                    board.reset();
                    // Crée une nouvelle partie
                    controller.newGame();
                    // Action : désactive et active les boutons correctement
                    startGame.setDisable(true);
                    addPlayer.setDisable(false);
                    dicesSumText.setText("/");
                    playerText1.setText("");
                    playerText2.setText("");
                    throwDicesButton.setDisable(true);
                }
                menuGame.setDisable(false);
            }
        });
        this.menuFile.getItems().addAll(this.newGame);
        // On ajoute l'item "Load Game" au menu "File"
        this.loadGame = new MenuItem("Load game");
        this.loadGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (controller.isGameStarted()) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Voulez vous vraiment quitter la partie en cours ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        // Action : demande la liste de joueurs qui joue à la partie
                        GameChoice gameChoice = new GameChoice(getObject(), controller);
                        board.reset();
                        throwDicesButton.setDisable(false);
                    } else {
                        event.consume();
                    }
                } else {
                    GameChoice gameChoice = new GameChoice(getObject(), controller);
                    throwDicesButton.setDisable(false);
                }
                menuGame.setDisable(false);
                startGame.setDisable(true);
            }
        });
        this.menuFile.getItems().addAll(this.loadGame);
        // On ajoute l'item "Save Game" au menu "File"
        this.saveGame = new MenuItem("Save game");
        this.saveGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (controller.getGameName() == null) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Sauvegarde de partie");
                        dialog.setHeaderText(null);
                        dialog.setContentText("Veuillez entrer le nom de sauvegarde de votre partie");

                        // Traditional way to get the response value.
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()){
                                if (!controller.checkGameName(result.get())) {
                                    controller.saveGame();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Erreur lors de la sauvegarde de la partie");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Le nom de partie est déjà pris");

                                    alert.showAndWait();
                                }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Erreur lors de la sauvegarde de la partie");
                                alert.setHeaderText(null);
                                alert.setContentText("Le nom de sauvegarde est vide");

                                alert.showAndWait();
                        }
                    } else {
                        controller.updateGame();
                    }
                } catch (DBException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur lors d'accès à la base de donnée");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                } catch (GooseGameException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de logique");
                        alert.setHeaderText(null);
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                }
            }
        });
        this.menuFile.getItems().addAll(this.saveGame);
        // On ajoute l'item "Quit" au menu "File"
        this.quit = new MenuItem("Quit");
        this.quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (controller.isGameStarted()) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Voulez vous vraiment quitter la partie en cours ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        mainStage.close();
                    } else {
                        event.consume();
                    }
                } else {
                    mainStage.close();
                }
            }
        });
        this.menuFile.getItems().addAll(quit);
        
        // Menu Game de la barre de menu
        this.menuGame = new Menu("Game");
        // On ajoute l'item "Add players" au menu "Game"
        this.addPlayer = new MenuItem("Add players");
        this.addPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // Action : demande la liste de joueurs qui joue à la partie
                PlayerChoice playerChoice = new PlayerChoice(getObject(), controller);
            }
        });
        this.menuGame.getItems().addAll(addPlayer);
        // On ajoute l'item "Start Game" au menu "Game"
        this.startGame = new MenuItem("Start game");
        this.startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // Action :
                startGame();
                startGame.setDisable(true);
                throwDicesButton.setDisable(false);
            }
        });
        this.menuGame.getItems().addAll(startGame);
        this.menuGame.setDisable(true);
        
        // Menu File de la barre de menu
        this.menuHelp = new Menu("Help");
        // On ajoute l'item "Rules" au menu "Help"
        this.rules = new MenuItem("Rules");
        this.rules.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Rules");
                alert.setHeaderText("Rules of Goose Game");
                alert.setContentText("Number of goose players : Two or more\n\n"
                        + "To start : Each player chooses a distinct color and name.\n\n"
                        + "Goal : The object of the game is to travel along the spiral from field 1 to field 63, and the first player who successfully lands exactly on field 63 is the winner.\n\n"
                        + "Rules : On each turn, a player rolls the two dice and advances the counter along the spiral by as many fields as the sum of the two dice. The player must deal with any situation on the space landed on, be they hazards or benefits.\n"
                        + "Two playing pieces may not occupy the same field at the same time. Whenever you land on an occupied field, that player's counter goes back to the space you came from, and you get the vacated space. (In brief, you trade places.)\n"
                        + "Whenever you land on a field with a goose, you double your move. You must arrive on field 63 by an exact count of the dice. If you overthrow the required number, you must step forward into 63 and then move backwards the surplus number of points. If this lands you on a goose, continue moving backwards the same count again.\n\n"
                        + "The Special Fields:\n6 The Bridge -- If you land on 6, advance immediately to field 12.\n19 The Inn -- The good food and drink makes you sleepy, and you lose I turn. (Exception: if another player lands at the Inn within the same turn, you change places and you go back to the space that player just came from.)\n"
                        + "31 The Well -- If you fall in the Well, lose 2 turns—unless another player landing there releases you sooner, sending you back to the field that player just arrived from.\n42 The Maze -- You get lost and go back to field 30\n52 The Prison -- If you land in prison, you stay there until another player landing there relieves you and you go back to that player's last field.\n"
                        + "58 Death -- Your goose is cooked. Go back to the beginning and start all over."
                );
                alert.showAndWait();
            }
        });
        this.menuHelp.getItems().addAll(rules);
        
        // On ajoute l'item "About" au menu "Help"
        this.about = new MenuItem("About");
        this.about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("About");
                alert.setHeaderText("About Goose Game");
                alert.setContentText("Developped by Maxime Van Damme, Guillaume Du Four and Joselyne Nduwayezu");
                alert.showAndWait();
            }
        });
        this.menuHelp.getItems().addAll(about);
        
        // Ajout des menu à la barre des menus
        this.menuBar.getMenus().addAll(menuFile, menuGame, menuHelp);
        
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
        this.dicesSumText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        
        // Texte permettant d'afficher le joueur suivant ou le gagnant
        this.playerText1 = new Text("");
        this.playerText1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        // Texte permettant d'afficher le joueur suivant ou le gagnant
        this.playerText2 = new Text("");
        this.playerText2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        this.playerText2.setStroke(Color.BLACK);
        
        // Plateau du jeu
        this.board = new BoardView(controller);
        // On s'ajoute comme Listener de l'attribut playerMoved du plateau (attribut
        // indiquant si un joueur a bougé).
        this.board.playerMovedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t != t1) {
                    // On active le bouton pour lancer les dés du prochain joueur
                    throwDicesButton.setDisable(false);
                }
            }
        });
        
        // Layout permettant de mettre en page les éléments de la fenêtre
        this.gridpane = new GridPane();
        this.gridpane.setVgap(4);
        this.gridpane.setHgap(10);
        this.gridpane.setPadding(new Insets(5, 5, 5, 5));
        this.gridpane.add(throwDicesButton, 0, 0);
        this.gridpane.add(dicesSumText, 1, 0);
        this.gridpane.add(playerText1, 2, 0);
        this.gridpane.add(playerText2, 3, 0);
        this.gridpane.add(board, 0, 1, 4, 1);

        // Layout permettant de mettre en la barre de menu avec le reste des éléments
        this.borderpane = new BorderPane();
        this.borderpane.setTop(menuBar);
        this.borderpane.setCenter(gridpane);
        
        this.mainGroup.getChildren().add(borderpane);
        
        this.mainStage.show();
    }
    
    /**
     *
     */
    public void startGame() {
        try {
            controller.startGame(); // On débute la partie
        } catch (GooseGameException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors du lancement des dés de la partie");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
        }
    }
    
    /**
     *
     * @return
     */
    public Stage getStage() {
        return this.mainStage;
    }
    
    /**
     *
     * @return
     */
    public MainWindow getObject() {
        return this;
    }
    
    /**
     * Méthode de mise à jour de l'interface graphique, appelée par le modèle lors d'un changement.
     */
    @Override
    public void refresh() {
        // On met à jour la position des joueurs sur le plateau
        ArrayList<Pair<Color,Integer>> players = this.controller.getPlayerPos();
        for (Pair<Color, Integer> player : players) {
            this.board.updatePlayer(player.getKey(), player.getValue());
        }
        
        // Si le jeu n'est pas encore fini :
        if (this.controller.isGameStarted()) {        
            // On affiche le résultat des dés
            this.throwDicesButton.setDisable(false);
            this.dicesSumText.setText(Integer.toString(this.controller.getDicesSum()));

            // On affiche le joueur suivant
            Pair<String, Color> player = this.controller.getCurrentPlayer();
            this.playerText1.setText("  Joueur suivant : ");
            this.playerText2.setText(player.getKey());
            this.playerText2.setFill(player.getValue());

        // Si le jeu est fini :
        } else if (this.controller.isGameOver()) {
            // On affiche le joueur gagnant
            Pair<String, Color> player = this.controller.getCurrentPlayer();
            this.playerText1.setText("  Joueur gagnant");
            this.playerText2.setText(player.getKey());
            this.playerText2.setFill(player.getValue());
            
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
