package arezzo.whatever;

import arezzo.Observateur;
import arezzo.enumerations.Duree;
import arezzo.enumerations.Instrument;
import arezzo.enumerations.Notes;
import arezzo.enumerations.Octave;
import arezzo.exceptions.TempsException;
import arezzo.model.Melodie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
;

public class PanneauDeControleW implements Observateur {
    private Melodie melodie;
    @FXML
    private Button nDo, doNoir, nRe, reNoir, nMi, nFa, faNoir, nSol, solNoir, nLa, laNoir, nSi;
    @FXML
    private Button chut;

    @FXML
    private RadioButton aigu, medium, grave;

    @FXML
    private RadioButton ronde, blanche, noire, croche;

    @FXML
    private RadioButton piano, guitare, saxophone, trompette;

    @FXML
    private Slider tempo, volume;

    @FXML
    private Button play;

    /**
     * Constructeur PanneauDeControleW
     * @param melodie
     */
    public PanneauDeControleW(Melodie melodie) {
        this.melodie = melodie;
        this.melodie.ajouterObservateur(this);
    }

    /* Les fonctions qui vont suivre sont les onAction de FXML */


    /* Ajout note */

    /**
     * Permet d'ajouter la note Do à la mélodie
     */
    @FXML
    public void ajouterDo() {
        try {
            this.melodie.ajouterNote(Notes.DO);
         } catch(TempsException e) {e.afficherAlerte(); }
    }

    /**
     * Permet d'ajouter la note DO_NOIR à la mélodie
     */
    @FXML
    public void ajouterDoNoir() {
        try {
            this.melodie.ajouterNote(Notes.DO_NOIR);
        } catch (TempsException e) { e.afficherAlerte(); }
    }
    /**
     * Permet d'ajouter la note RE à la mélodie
     */
    @FXML
    public void ajouterRe() {
        try{
            this.melodie.ajouterNote(Notes.RE);
        } catch (TempsException e) { e.afficherAlerte(); }
}
    /**
     * Permet d'ajouter la note RE_NOIR à la mélodie
     */
    @FXML
    public void ajouterReNoir() {
        try {
            this.melodie.ajouterNote(Notes.RE_NOIR);
        } catch (TempsException e) { e.afficherAlerte(); }
    }
    /**
     * Permet d'ajouter la note MI à la mélodie
     */
    @FXML
    public void ajouterMi() {
        try {
            this.melodie.ajouterNote(Notes.MI);
        }catch (TempsException e) { e.afficherAlerte(); }
    }
    /**
     * Permet d'ajouter la note FA à la mélodie
     */
    @FXML
    public void ajouterFa() {
        try{
            this.melodie.ajouterNote(Notes.FA);
        }catch (TempsException e) { e.afficherAlerte(); }
    }
    /**
     * Permet d'ajouter la note FA_NOIR à la mélodie
     */
    @FXML
    public void ajouterFaNoir() {
        try{
             this.melodie.ajouterNote(Notes.FA_NOIR);
        }catch (TempsException e) { e.afficherAlerte(); }
    }
    /**
     * Permet d'ajouter la note SOL à la mélodie
     */
    @FXML
    public void ajouterSol() {
        try{
            this.melodie.ajouterNote(Notes.SOL);
        }catch (TempsException e) { e.afficherAlerte(); }
    }
    /**
     * Permet d'ajouter la note SOL_NOIR à la mélodie
     */
    @FXML
    public void ajouterSolNoir() {
        try{
         this.melodie.ajouterNote(Notes.SOL_NOIR);
        }catch (TempsException e) { e.afficherAlerte(); }
    }
    /**
     * Permet d'ajouter la note LA à la mélodie
     */
    @FXML
    public void ajouterLa() {
        try{
            this.melodie.ajouterNote(Notes.LA);
        }catch (TempsException e) { e.afficherAlerte(); }
    }
    /**
     * Permet d'ajouter la note LA_NOIR à la mélodie
     */
    @FXML
    public void ajouterLaNoir() {
        try{
            this.melodie.ajouterNote(Notes.LA_NOIR);
        }catch (TempsException e) { e.afficherAlerte(); }
}
    /**
     * Permet d'ajouter la note SI à la mélodie
     */
    @FXML
    public void ajouterSi() {
        try{
            this.melodie.ajouterNote(Notes.SI);
        }catch (TempsException e) { e.afficherAlerte(); }
    }

    /* Silence */
    /**
     * Permet d'ajouter un silence à la mélodie
     */
    @FXML
    public void chut(){
        try {
            melodie.ajouterSilence();
        }catch (TempsException e) { e.afficherAlerte(); }
    }

    /* Octave */

    /**
     * Permet de changer l'octave de la mélodie en AIGU
     */
    @FXML
    public void octaveAigu(){
        melodie.setOctave(Octave.AIGU);
    }


    /**
     * Permet de changer l'octave de la mélodie en MEDIUM
     */
    @FXML
    public void octaveMedium(){
        melodie.setOctave(Octave.MEDIUM);
    }

    /**
     * Permet de changer l'octave de la mélodie en GRAVE
     */
    @FXML
    public void octaveGrave(){
        melodie.setOctave(Octave.GRAVE);
    }

    /* Instrument */

    /**
     * Permet de changer l'instrument de la mélodie en Piano
     */
    @FXML
    public void instrumentPiano(){
        this.melodie.setInstrument(Instrument.PIANO);
    }

    /**
     * Permet de changer l'instrument de la mélodie en GUITARE
     */
    @FXML
    public void instrumentGuitare(){
        this.melodie.setInstrument(Instrument.GUITARE);
    }

    /**
     * Permet de changer l'instrument de la mélodie en SAXOPHONE
     */
    @FXML
    public void instrumentSaxophone(){
        this.melodie.setInstrument(Instrument.SAXOPHONE);
    }

    /**
     * Permet de changer l'instrument de la mélodie en TROMPETTE
     */
    @FXML
    public void instrumentTrompette(){
        this.melodie.setInstrument(Instrument.TROMPETTE);
    }

    /* Durées */

    /**
     * Permet de changer la durée des prochaines notes en RONDE
     */
    @FXML
    public void dureeRonde(){
        this.melodie.setDuree(Duree.RONDE);
    }

    /**
     * Permet de changer la durée des prochaines notes en CROCHE
     */
    @FXML
    public void dureeCroche(){
        this.melodie.setDuree(Duree.CROCHE);
    }

    /**
     * Permet de changer la durée des prochaines notes en BLANCHE
     */
    @FXML
    public void dureeBlanche(){
        this.melodie.setDuree(Duree.BLANCHE);
    }

    /**
     * Permet de changer la durée des prochaines notes en NOIRE
     */
    @FXML
    public void dureeNoire(){
        this.melodie.setDuree(Duree.NOIRE);
    }

    /* Sliders */

    /**
     * Permet de changer le tempo de la mélodie
     */
    @FXML
    public void tempo(){
        this.melodie.setTempo((int)tempo.getValue());
    }

    /**
     * Permet de changer le volume de la mélodie
     */
    @FXML
    public void volume(){
        this.melodie.setVolume(volume.getValue()* 0.01); // Volume de 0 à 1 (1 = 100%)
    }

    /* Play */

    /**
     * Permet de jouer la mélodie
     */
    @FXML
    public void play(){
        this.melodie.jouerPartition();
    }

    /**
     * Réagit quand le modele le notifie
     */
    @Override
    public void reagir() {
        aigu.selectedProperty().setValue(melodie.getOctave().equals(Octave.AIGU));
        medium.selectedProperty().setValue(melodie.getOctave().equals(Octave.MEDIUM));
        grave.selectedProperty().setValue(melodie.getOctave().equals(Octave.GRAVE));

        croche.selectedProperty().setValue(melodie.getDuree().equals(Duree.CROCHE));
        blanche.selectedProperty().setValue(melodie.getDuree().equals(Duree.BLANCHE));
        noire.selectedProperty().setValue(melodie.getDuree().equals(Duree.NOIRE));
        ronde.selectedProperty().setValue(melodie.getDuree().equals(Duree.RONDE));

        tempo.adjustValue(melodie.getTempo());
        volume.adjustValue(melodie.getVolume());

        piano.selectedProperty().setValue(melodie.getInstrument().equals(Instrument.PIANO));
        guitare.selectedProperty().setValue(melodie.getInstrument().equals(Instrument.GUITARE));
        saxophone.selectedProperty().setValue(melodie.getInstrument().equals(Instrument.SAXOPHONE));
        trompette.selectedProperty().setValue(melodie.getInstrument().equals(Instrument.TROMPETTE));
    }
}
