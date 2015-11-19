package heb.esi.goosegame;

import heb.esi.goosegame.controler.Controller;
import heb.esi.goosegame.view.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author nosa
 */
public class GooseGame extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(GooseGame.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Création du controleur
        Controller gooseGameController = new Controller();
        
        // Création de la partie
        gooseGameController.newGame();
        
        // Création de la vue avec attachement du controler (pour envoyer les interactions de l'utilisateur)
        MainWindow mw = new MainWindow(gooseGameController);
        
        // Attachement de la vue au modèle (pour la mettre à jour lors du changement du modèle)
        gooseGameController.attachView(mw);
    }
}
