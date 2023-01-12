package arezzo;

import arezzo.whatever.ListeW;
import arezzo.whatever.MelodieW;
import arezzo.whatever.MenuW;
import arezzo.whatever.PanneauDeControleW;
import arezzo.model.Melodie;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class MainAzerro extends Application {

    private MenuW menuW;
    @Override
    public void start(Stage stage) throws IOException {

        VBox root = new VBox();

        // Creation d'une mélodie vide
        Melodie melodie = new Melodie();

        /* Fenêtre principale */

        // Load de la vue Menu
        FXMLLoader loaderMenu = new FXMLLoader(MainAzerro.class.getResource("view/VueMenu.fxml"));
        loaderMenu.setControllerFactory(iC-> (menuW = new MenuW(melodie, stage)));
        Parent vueMenu = loaderMenu.load();

        // Load de la vue Melodie
        FXMLLoader loaderMelodie = new FXMLLoader(MainAzerro.class.getResource("view/VueMelodie.fxml"));
        loaderMelodie.setControllerFactory(iC-> new MelodieW(melodie));
        Parent vueMelodie = loaderMelodie.load();

        // Load de la vue PanneauDeControle
        FXMLLoader loaderPanneauDeControle = new FXMLLoader(MainAzerro.class.getResource("view/VuePanneauDeControle.fxml"));
        loaderPanneauDeControle.setControllerFactory(iC-> new PanneauDeControleW(melodie));
        Parent vuePanneauDeControle = loaderPanneauDeControle.load();

        // Ajout de toutes les vues dans la VBox root
        root.getChildren().addAll(vueMenu, vueMelodie, vuePanneauDeControle);

        // Création de la scène avec la VBox root
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Azerro");
        stage.setResizable(false);

        //Import de la StyleSheet
        scene.getStylesheets().add(getClass().getResource("/style.css").toString());

        // Empêcher la fermeture de la fenêtre avant une sauvegarde potentielle
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                menuW.quitter();
            }
        });

        // Ajout de la scène au stage et affichage du stage
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Permet de définir sa propre fermerture lors de l'arrêt de l'application
     */
    @Override
    public void stop() {
        menuW.quitter();
    }

    public static void main(String[] args) {
        launch();
    }
}