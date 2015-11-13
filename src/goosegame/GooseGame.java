package goosegame;

import goosegame.controler.Controler;
import goosegame.model.Game;
import goosegame.view.MainWindow;
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
        // Création du model : le jeu
        Game game = new Game();
        
        // Création du controler avec attachement du modèle (pour que le controler puisse agir sur le model)
        Controler controler = new Controler(game);
        
        // Création de la vue avec attachement du controler (pour envoyer les interactions de l'utilisateur)
        MainWindow mw = new MainWindow(controler, game);
        
        // Attachement de la vue au modèle (pour la mettre à jour lors du changement du modèle)
        game.attachView(mw);
    }
}
