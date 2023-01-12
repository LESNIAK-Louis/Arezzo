package arezzo.whatever;

import arezzo.Observateur;
import arezzo.model.Melodie;
import arezzo.model.Note;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;



public class ListeW implements Observateur, Initializable {
    private Melodie melodie;
    @FXML
    private ListView<HBox> listNotes;
    @FXML
    private ContextMenu contextMenu;

    /**
     * Constructeur ListeW
     * @param melodie
     */
    public ListeW(Melodie melodie){
        this.melodie = melodie;
        this.contextMenu = new ContextMenu();
        listNotes = new ListView<>();
        melodie.ajouterObservateur(this);
    }

    /**
     * Fonciton d'initialisaiton de la class
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        listNotes.setContextMenu(contextMenu);
        listNotes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listNotes.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    for (HBox box : listNotes.getSelectionModel().getSelectedItems()) {
                        Note note = (Note) box.getUserData();
                        //melodie.getPartition().setCouleurs(Color.PALEVIOLETRED, 0);
                    }
                });

        MenuItem effacer = new MenuItem("Effacer");
        effacer.setOnAction(event -> effacer());
        MenuItem monter = new MenuItem("Monter d'un 1/2 ton");
        monter.setOnAction(event -> monter());
        MenuItem descendre = new MenuItem("Descendre d'un 1/2 ton");
        descendre.setOnAction(event -> descendre());

        this.contextMenu.getItems().addAll(effacer, monter, descendre);
    }


    /**
     * Remplace les notes sélectionnés par un silence, supprime tous les silences en fin de mélodie
     */
    public void effacer() {
        for (HBox box : listNotes.getSelectionModel().getSelectedItems()) {
            Note note = (Note)(box.getUserData());
            if(note != null)
                note.changeToSilence();
        }
        melodie.supprimerSilencesFin();
    }

    /**
     * Monte les notes sélectionnées de 1/2 ton
     */
    public void monter(){
        for (HBox box : listNotes.getSelectionModel().getSelectedItems()) {
            Note note = (Note)(box.getUserData());
            if(note != null)
                note.transposer(1);
        }
        melodie.updateMelodie();
    }

    /**
     * Descend les notes sélectionnées de 1/2 ton
     */
    public void descendre(){
        for (HBox box : listNotes.getSelectionModel().getSelectedItems()) {
            Note note = (Note)(box.getUserData());
            if(note != null)
                note.transposer(11); // Descendre de 1 revient à monter de 11 (Math cyclique)
        }
        melodie.updateMelodie();
    }

    /**
     * Réagir quand le modèle notifie
     */
    @Override
    public void reagir() {
        listNotes.getItems().clear();

        for(Note note : melodie) {
            if(!note.isSeparation()) { // Ce n'est pas une séparation
                HBox noteHBox = new HBox();
                noteHBox.setSpacing(25);

                ImageView imgView = new ImageView();
                Image img = note.getImage();
                if(img != null) {
                    imgView.setImage(img);
                    imgView.setFitWidth(25);
                    imgView.setFitHeight(25);
                }
                noteHBox.setUserData(note);

                Text octave;
                if(note.isSilence())
                    octave = new Text("");
                else
                    octave = new Text(note.getOctave().toString());
                noteHBox.getChildren().addAll(octave, imgView, new Text(note.getNotationFr()));

                listNotes.getItems().add(noteHBox);
            }
        }
    }
}
