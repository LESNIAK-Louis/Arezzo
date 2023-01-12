package arezzo.model;

import arezzo.Observateur;
import arezzo.enumerations.*;
import arezzo.exceptions.FichierException;
import arezzo.exceptions.TempsException;
import partition.Partition;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Melodie implements Iterable<Note> {
    @JsonIgnore
    private Partition partition;
    @JsonIgnore
    private ArrayList<Observateur> obs;
    @JsonIgnore
    boolean alreadySaved;
    private String titre;

    private ArrayList<Note> notes;

    private Octave octave;

    private Duree duree;
    private int tempo;
    private  double volume;

    private Instrument instrument;

    private int mesurex2;

    /**
     * Constructeur d'une Melodie
     */
    public Melodie(){
        try {
            partition = new Partition(MidiSystem.getSynthesizer());
            obs = new ArrayList<>();
            notes = new ArrayList<>();
            initMelodie();
        } catch (MidiUnavailableException e) { e.printStackTrace(); }
    }

    /**
     * Retourne si la méloidie à subi une modification depuis son état initial ou de sa dernière sauvegarde
     */
    public boolean isAlreadySaved() {
        return alreadySaved;
    }

    /**
     * Return l'ArrayList des notes
     * @return notes
     */
    public ArrayList<Note> getNotes() {
        return notes;
    }

    /**
     * Getter du compteur mesure actuel multiplié par 2
     * @return mesure
     */
    public int getMesurex2() {
        return mesurex2;
    }

    /**
     * Getter du titre de la melodie
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * Getter du volume
     * @return volume
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Getter de la duree
     * @return duree
     */
    public Duree getDuree() {
        return duree;
    }

    /**
     * Getter de l'instrument
     * @return instrument
     */
    public Instrument getInstrument() {
        return instrument;
    }

    /**
     * Getter du tempo
     * @return tempo
     */
    public int getTempo() {
        return tempo;
    }

    /**
     * Getter de l'octave
     * @return octave
     */
    public Octave getOctave() {
        return octave;
    }

    /**
     * Construit la mélodie avec les notes
     * @return melodie
     */
    @JsonIgnore
    public String getMelodie() {
        StringBuilder melodie = new StringBuilder("");
        for(Note n : notes)
            melodie.append(n.getNotationABC() + " ");
        return melodie.toString();
    }

    /**
     * Getter de la partition
     * @return partition
     */
    public Partition getPartition() {
        return partition;
    }

    /**
     * Set l'ArrayList des notes
     * @param notes
     */
    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    /**
     * Setter de la mesure actuelle multiplié par 2
     * @param mesurex2
     */
    public void setMesurex2(int mesurex2) {
        this.mesurex2 = mesurex2%8; // Remise à 0 lorsqu'on arrive aux 4 temps
    }

    /**
     * Set le tempo
     * @param tempo (int)
     */
    public void setTempo(int tempo) {
        this.tempo = tempo;
        setAlreadySaved(false);
    }

    /**
     * Set le volume
     * @param volume (double)
     */
    public void setVolume(double volume) {
        this.volume = volume;
        setAlreadySaved(false);
    }

    /**
     * Set l'instrument
     * @param instrument
     */
    public void setInstrument(Instrument instrument){
        this.instrument = instrument;
        setAlreadySaved(false);
    }

    /**
     * Change l'octave des prochaines notes ajoutées
     * @param octave
     */
    public void setOctave(Octave octave){
        this.octave = octave;
        setAlreadySaved(false);
    }

    /**
     * Change la duree des prochaines notes ajoutées
     * @param duree
     */
    public void setDuree(Duree duree){
        this.duree = duree;
        setAlreadySaved(false);
    }

    /**
     * Set le titre de la mélodie
     * @param titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
        setAlreadySaved(false);
        notifierObservateurs();
    }

    /**
     * Met à jour la mélodie dans la partition
     */
    public void updateMelodie(){
        partition.setMelodie(getMelodie());
        notifierObservateurs();
    }

    /**
     * Setter alreadySaved
     * @param alreadySaved (boolean)
     */
    public void setAlreadySaved(boolean alreadySaved) {
        this.alreadySaved = alreadySaved;
    }

    /**
     * Check temps valide
     * @throw TempsException si l'ajout de la note/silence dépasse les 4 temps
     * @param type "de la note" ou "du silence"
     */
    private void checkTempsValide(String type) throws TempsException
    {
        if(getMesurex2() + duree.getDureex2() > 8) // La durée est supérieure à 4 temps
            throw new TempsException("L'ajout " + type + " dépasse les 4 temps");

        setMesurex2(getMesurex2() + duree.getDureex2()); // On met à jour la mesure
    }

    /**
     * Check si l'ajout d'une barre de séparation est nécessaire, si oui on l'ajoute à la mélodie
     */
    private void checkSeparation()
    {
        if(getMesurex2() == 0)
            notes.add(new Note(null, null, null));

    }

    /**
     * Ajoute la note Do à la mélodie selon l'octave et la durée
     * @param note
     */
    public void ajouterNote(Notes note) throws TempsException {
        checkTempsValide("de la note");
        notes.add(new Note(duree, octave, note));
        setAlreadySaved(false);
        jouerNote(notes.get(notes.size()-1).getNotationABC());
        checkSeparation();
        updateMelodie();
    }

    /**
     * Ajoute un silence à la mélodie selon la durée
     */
    public void ajouterSilence() throws TempsException {

        checkTempsValide("du silence");
        notes.add(new Note(duree, null, null));
        setAlreadySaved(false);
        checkSeparation();
        updateMelodie();
    }

    /**
     * Supprime les silences en fin de mélodie
     */
    public void supprimerSilencesFin()
    {
        if(this.notes.size() > 0){
            int totalTempsRetire = 0;
            ArrayList<Note> toDelete = new ArrayList<>();
            for(int i = 1; notes.size()-i >= 0; i++)
            {
                Note n = this.notes.get(notes.size()-i);
                if(n.isSilence() || n.isSeparation()) {
                    toDelete.add(n);
                    if(n.getDuree() != null)
                    {
                        totalTempsRetire += n.getDuree().getDureex2();
                    }

                }
                else i = notes.size();
            }
            this.notes.removeAll(toDelete);

            if(getMesurex2()-totalTempsRetire%8 < 0){
                setMesurex2(-(getMesurex2()-totalTempsRetire%8));
            }
            else
                setMesurex2(getMesurex2()-totalTempsRetire%8);

            if(getNotes().size() != 0)
                checkSeparation();

            setAlreadySaved(false);
            updateMelodie();
        }
    }

    /**
     * Transpose la mélodie entière de nbDemiTon
     */
    public void transposerMelodie(int nbDemiTon){
        for(Note n : notes)
            transposerNote(n, nbDemiTon);
    }

    /**
     * Transpose la note de nbDemiTon
     */
    public void transposerNote(Note n, int nbDemiTon){
        n.transposer(nbDemiTon);
        setAlreadySaved(false);
        updateMelodie();
    }

    /**
     * Joue la note donné en cours en utilisant le tempo, l'instrument et le volume précédemment choisi
     * @param note (String)
     */
    public void jouerNote(String note){
        this.partition.setInstrument(instrument.getInstrument());
        this.partition.setVolume(this.volume);
        partition.play(note);
    }

    /**
     * Joue la partition en cours en utilisant la mélodie avec le tempo, l'instrument et le volume précédemment choisi
     */
    public void jouerPartition(){
        this.partition.setTempo(this.tempo);
        this.partition.setInstrument(instrument.getInstrument());
        this.partition.setVolume(this.volume);
        partition.play();
    }

    /**
     * Ajoute un observateur
     * @param o
     */
    public void ajouterObservateur(Observateur o)
    {
        this.obs.add(o);
    }

    /**
     * Notifie les observateurs
     */
    public void notifierObservateurs() {
        for(Observateur o : obs)
            o.reagir();
    }

    /**
     * Met la mélodie au paramètres de base
     */
    public void initMelodie(){
        titre = "Nouvelle Partition";
        octave = Octave.AIGU;
        duree = Duree.RONDE;
        instrument = Instrument.PIANO;
        tempo = 180;
        volume = 25 * 0.01;
        mesurex2 = 0;
        alreadySaved = true;
        this.notes.clear();
        updateMelodie(); // Ne sert que lorsqu'on initialise à nouveau la mélodie
    }

    /**
     * Sauvegarde la mélodie au format .json
     * @param fichier où sauvegarder la mélodie
     */
    public void sauvegarderMelodie(File fichier){
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            ow.writeValue(fichier, this);
            setAlreadySaved(true);
            notifierObservateurs();
        } catch (JsonMappingException e) { e.printStackTrace(); }
          catch (JsonGenerationException e ) { e.printStackTrace(); }
          catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Charge une mélodie au format .json
     * @param fichier fichier à load
     */
    public void chargerMelodie(File fichier) throws FichierException {

        try {
            Melodie melo = new ObjectMapper().readValue(fichier, Melodie.class);
            this.setTitre(melo.getTitre());
            this.setInstrument(melo.getInstrument());
            this.setTempo(melo.getTempo());
            this.setMesurex2(melo.getMesurex2());
            this.setDuree(melo.getDuree());
            this.setVolume(melo.getVolume());
            this.setNotes(melo.getNotes());
            this.setOctave(melo.getOctave());
            this.setAlreadySaved(true);
            updateMelodie();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FichierException("Format .json ne correspondant pas à une mélodie.");
        }
    }

    @Override
    public String toString() {
        return "titre : " + titre + ", melodie : " + getMelodie() + ", ocatave actuelle : " + this.octave;
    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }
}
