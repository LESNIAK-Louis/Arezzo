package arezzo.exceptions;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class ArezzoException extends Exception {

    public ArezzoException(String msg){
        super(msg);
    }

    public void afficherAlerte(){

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erreur");
        a.setHeaderText(null);
        a.setContentText(getMessage());
        a.show();
        PauseTransition p = new PauseTransition(Duration.seconds(2));
        p.setOnFinished(event -> a.hide());
        p.play();
    }
}