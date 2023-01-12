package arezzo.whatever;

import arezzo.MainAzerro;
import arezzo.Observateur;
import arezzo.model.Melodie;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MelodieW implements Observateur, Initializable {
    private Melodie melodie;

    @FXML
    private ScrollPane melodiePane;
    @FXML
    private Label titreMelodie;
    @FXML
    private Button bouttonListeNotes;

    private Stage listeNotes;

    @FXML
    private ImageView rotationImage;

    /**
     * Constructeur MelodieW
     * @param melodie
     */
    public MelodieW(Melodie melodie) {
        this.melodie = melodie;
        listeNotes = new Stage();
        this.melodie.ajouterObservateur(this);
    }

    /**
     * Permet d'initialiser la class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Load de la vue Liste
            FXMLLoader loaderList = new FXMLLoader(MainAzerro.class.getResource("view/VueListe.fxml"));
            loaderList.setControllerFactory(iC -> new ListeW(melodie));
            Parent vueListe = loaderList.load();

            Scene listeNotesScene = new Scene(vueListe, 300, 500);
            listeNotes.setScene(listeNotesScene);
            titreMelodie.setText(melodie.getTitre());

            rotateImg();

        }catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Animation d'une rotation d'image
     */
    private void rotateImg(){
        RotateTransition transition = new RotateTransition();
        Duration duration = Duration.millis(5000);
        RotateTransition rotateTransition = new RotateTransition(duration, rotationImage);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.play();
    }

    /**
     * Permet d'ouvrir la fênetre correspondante à la liste des notes
     */
    @FXML
    public void ouvrirListeNotes(){
        listeNotes.show();
    }

    /**
     * Réagit lorsque le model notifie
     */
    @Override
    public void reagir() {
        titreMelodie.setText(melodie.getTitre());
        melodie.getPartition().setPreferedMaxWidth((int)melodiePane.getWidth());
        Image imgMelodie = melodie.getPartition().getImage();
        if(imgMelodie != null) {
            ImageView imgView = new ImageView(imgMelodie);
            if(imgView != null)
            {
                if(imgMelodie.getWidth() > melodiePane.getWidth()) // si l'image est plus large que la ScrollPane, on resize l'ImageView
                    imgView.setFitWidth(melodiePane.getWidth()-15); // -15 au cas où la barre de défilement apparaît pour ne pas cacher les notes
                melodiePane.setContent(imgView);
            }

        }
    }
}