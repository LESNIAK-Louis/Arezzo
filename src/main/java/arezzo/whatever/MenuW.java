package arezzo.whatever;

import arezzo.MainAzerro;
import arezzo.exceptions.FichierException;
import arezzo.exceptions.InputException;
import arezzo.model.Melodie;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MenuW {

    private Melodie melodie;
    private Stage stage;

    /**
     * Constructeur MenuW
     * @param melodie
     * @param stage
     */
    public MenuW(Melodie melodie, Stage stage) {
        this.melodie = melodie;
        this.stage = stage;
    }

    /**
     * Permet de créer une nouvelle mélodie
     */
    @FXML
    public void nouvelleMelodie(){
        melodie.initMelodie();
    }

    /**
     * Permet de charger une mélodie
     */
    @FXML
    public void chargerMelodie(){
        try {
            if(!melodie.isAlreadySaved() && confirmation("Voulez-vous sauvegardez la mélodie actuelle avant d'en charger une autre ?"))
            {
                if(sauvegarderMelodie())
                {
                    File fichier = chooseFile(true);
                    melodie.chargerMelodie(fichier);
                }
            }
            File fichier = chooseFile(true);
            melodie.chargerMelodie(fichier);
        } catch (FichierException e) { }
    }

    /**
     * Permet de sauvegarder une mélodie
     * @return
     */
    @FXML
    public boolean sauvegarderMelodie(){
        try {
            File fichier = chooseFile(false);
            if(fichier.exists()) {
                melodie.sauvegarderMelodie(fichier);
            }
            else {
                fichier.createNewFile();
                melodie.sauvegarderMelodie(fichier);
            }
        } catch (IOException e ) { e.printStackTrace(); return false;}
          catch (FichierException e) { return false; }
        return true;
    }

    /**
     * Permet de quitter l'applicaiton proprement
     */
    @FXML
    public void quitter(){
        if(!melodie.isAlreadySaved() && confirmation("Voulez vous sauvegarder la mélodie avant de quitter ?"))
        {
            if(sauvegarderMelodie()) // On ne quitte l'application que lorsque la sauvegarde est un succès
            {
                this.melodie.getPartition().close(); // On arrête la mélodie actuelle et ferme le synthétiseur
                Platform.exit();
                System.exit(0);
            }
        } else {
            this.melodie.getPartition().close(); // On arrête la mélodie actuelle et ferme le synthétiseur
            Platform.exit();
            System.exit(0);
        }

    }

    /**
     * Permet de renommer une mélodie
     */
    @FXML
    public void renommerMelodie(){
        try {
            String res = createDialog("Titre Mélodie", "Nouveau titre : ", false);
            if(res.length() != 0)
                melodie.setTitre(res);
        } catch (InputException e ) { e.afficherAlerte(); }
    }

    /**
     * Permet de transposer une mélodie (décaler le ton de chaque note)
     */
    @FXML
    public void transposerMelodie(){
        try {
            String res = createDialog("Transposer Mélodie", "Nombre de demi-tons (0-11) : ", true);
            if(res.length() != 0)
                melodie.transposerMelodie(Integer.parseInt(res));
        } catch (InputException e ) { e.afficherAlerte(); }
          catch (NumberFormatException e ) { transposerMelodie(); }
    }

    /**
     * Créer une boite de dialogue demandant un input
     * @param titre
     * @param askFor
     * @param entier true si le résultat demandé est un entier, false: string
     * @return intput de l'utilisateur
     * @throws InputException
     */
    private String createDialog(String titre, String askFor, boolean entier) throws InputException
    {
        TextInputDialog dialogue = new TextInputDialog();
        dialogue.setTitle(titre);
        dialogue.setHeaderText(null);
        dialogue.setGraphic(null);
        dialogue.setContentText(askFor);

        // Supprime les caractères autres que des chiffres entrées via un regex
        if(entier)
            dialogue.getEditor().textProperty().addListener((o, oldValue, newValue) -> {
                if (!newValue.matches("[0-9]+")) {
                    dialogue.getEditor().textProperty().setValue((newValue.replaceAll("[^[0-9]]", "")));
                }
                else{ // Set max 11
                if(!newValue.equals("") && Integer.parseInt(newValue) > 11)  dialogue.getEditor().textProperty().setValue("11");}
            });

        Optional<String> nom = dialogue.showAndWait();

        StringBuilder res = new StringBuilder("");
        nom.ifPresent(str -> {
            res.append(str);
        });
        if(res.length() == 0 && nom.isPresent())
            throw new InputException("L'input est invalide (vide).");

        return res.toString();
    }

    /**
     * Créer une boite de confirmation Oui/Non
     * @param msg
     * @return choix de l'utilisateur
     */
    private boolean confirmation(String msg) {
        ButtonType oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.getButtonTypes().clear();
        a.getButtonTypes().addAll(oui, non);
        a.setTitle("Confirmation");
        a.setHeaderText(null);
        a.setContentText(msg);
        Optional<ButtonType> choose = a.showAndWait();
        if (choose.isPresent() && choose.get().equals(oui))
            return true;
        return false;
    }

    /**
     * Permet de choisir un path de fichier
     * @param load Si on veut charger (true) ou sauvegarder (false) un fichier
     * @return endroit choisi
     * @throws FichierException
     */
    private File chooseFile(boolean load) throws FichierException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // Endroit actuel
        fileChooser.initialFileNameProperty().setValue("sauvegardeMelodie.json");
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json"));

        File file;
        if(load)
             file = fileChooser.showOpenDialog(this.stage);
        else
             file = fileChooser.showSaveDialog(this.stage);

        if(file == null)
            throw new FichierException("Opération de sauvegarde annulé par l'utilisateur.");

        if (!file.getAbsolutePath().contains(".json"))
            file = new File(file.getAbsolutePath() + ".json");
        if (load && !file.exists())
            throw new FichierException("Le fichier choisi n'existe pas.");
        return file;

    }
}
