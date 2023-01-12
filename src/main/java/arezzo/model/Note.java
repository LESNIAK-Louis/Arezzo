package arezzo.model;

import arezzo.MainAzerro;
import arezzo.enumerations.Duree;
import arezzo.enumerations.Notes;
import arezzo.enumerations.Octave;
import arezzo.enumerations.Silence;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.scene.image.Image;

public class Note {

    private Duree duree;
    private Octave octave;
    private Notes note;
    private String notationABC;

    /**
     * Constructeur par défaut : initalise un séparateur
     */
    public Note(){initializeNote(null,null,null);}

    /**
     * Constructeur d'une note
     * @param duree
     * @param octave
     * @param note
     */
    public Note(Duree duree, Octave octave, Notes note)
    {
        initializeNote(duree, octave, note);
    }

    /**
     * Permet d'initialiser ou de modifier une note
     * @param duree
     * @param octave
     * @param note
     */
    public void initializeNote(Duree duree, Octave octave, Notes note){
        setNote(note);
        setDuree(duree);
        setOctave(octave);

        if(isSeparation())
            setNotationABC("|");
        else if (isSilence())
        {
            switch (duree) {
                case CROCHE:
                    setNotationABC(Silence.DEMI_SOUPIR.getSilence());
                    break;
                case NOIRE:
                    setNotationABC(Silence.SOUPIR.getSilence());
                    break;
                case BLANCHE:
                    setNotationABC(Silence.DEMI_PAUSE.getSilence());
                    break;
                case RONDE:
                    setNotationABC(Silence.PAUSE.getSilence());
                    break;
            }
        } else { // Note
            switch (octave)
            {
                case AIGU:
                    setNotationABC(note.getNote() + this.duree.getDuree());
                    break;
                case MEDIUM:
                    setNotationABC(note.getNote().toUpperCase()+ this.duree.getDuree());
                    break;
                case GRAVE:
                    setNotationABC(note.getNote().toUpperCase()  + "," + this.duree.getDuree());
                    break;
            }
        }
    }

    /**
     * Getter de l'octave de la note
     * @return octave
     */
    public Octave getOctave() {
        return octave;
    }

    /**
     * Getter de la duree de la note
     * @return duree
     */
    public Duree getDuree() {
        return duree;
    }

    /**
     * Getter de la notation ABC de la note afin de pouvoir l'ajouter à la partition
     * @return notationABC
     */
    public String getNotationABC() {
        return notationABC;
    }

    /**
     * Getter de la Notation de la note initiale (avant transformation) de cette note actuelle
     * @return
     */
    @JsonGetter("note")
    public Notes getNote() {
        return note;
    }

    /**
     * Retourne l'image correspondante à la note actuelle
     * @return image
     */
    @JsonIgnore
    public Image getImage(){
        if(isSeparation())
            return null;
        else if(isSilence()) {
            String res = "";
            switch(getDuree()){
                case RONDE:
                    res = "pause";
                    break;
                case NOIRE:
                    res = "soupir";
                    break;
                case BLANCHE:
                    res = "demiPause";
                    break;
                case CROCHE:
                    res = "demiSoupir";
                    break;

            }
            return new Image(MainAzerro.class.getResourceAsStream("/images/" + res + ".png"));
        }
        else // Note
        {
            String res = "";
            switch(getDuree()){
                case RONDE:
                    res = "ronde";
                    break;
                case NOIRE:
                    res = "noire";
                    break;
                case BLANCHE:
                    res = "blanche";
                    break;
                case CROCHE:
                    res = "croche";
                    break;

            }
            return new Image(MainAzerro.class.getResourceAsStream("/images/" + res + ".png"));
        }
    }

    /**
     * Retourne la notation en français de la note actuelle
     * @return string en notation fr
     */
    @JsonIgnore
    public String getNotationFr(){
        if(isNote())
            return this.note.toString();
        return "";
    }

    /**
     * Setter durée
     * @param duree
     */
    public void setDuree(Duree duree) {
        this.duree = duree;
    }

    /**
     * Setter de la notation ABC
     * @param notationABC
     */
    public void setNotationABC(String notationABC) {
        this.notationABC = notationABC;
    }

    /**
     * Setter de l'octave
     * @param octave
     */
    public void setOctave(Octave octave) {
        this.octave = octave;
    }

    /**
     * Setter de la note initiale
     * @param note
     */
    @JsonSetter("note")
    public void setNote(Notes note) {
        this.note = note;
    }

    /**
     * Permet de modifier la note actuelle en son silence correspondant
     */
    public void changeToSilence(){
        if(!isSilence()) {
            initializeNote(duree, null, null);
        }
    }

    /**
     * Permet de transposer la note actuelle de nbDemiTon
     * @param nbDemiTon
     */
    public void transposer(int nbDemiTon){
        if(isNote()) {
            int foundIndexCurrentNote = 0;
            Notes[] tabNotes = note.getNotes();
            for (int i = 0; i < 12; i++) // 12 = nb notes
            {
                if(tabNotes[i].toString().equals(note.toString()))
                    foundIndexCurrentNote = i;
            }
            initializeNote(duree, octave, tabNotes[(foundIndexCurrentNote + nbDemiTon) %12]);
        }
    }

    /**
     * Retourne vrai si la note est une séparation "|"
     * @return vrai si la note est une séparation "|"
     */
    @JsonIgnore
    public boolean isSeparation(){
        return duree == null && note == null && octave == null;
    }

    /**
     * Retourne vrai si la note est un silence
     * @return vrai si la note est une silence
     */
    @JsonIgnore
    public boolean isSilence(){
        return note == null && octave == null;
    }

    /**
     * Retourne vrai si la note est une note normale
     * @return vrai si la note est une  note normale
     */
    @JsonIgnore
    public boolean isNote(){
        return !isSilence() && !isSeparation();
    }

    @Override
    public String toString() {
        if(isNote()) return "Note : " + getNotationFr() + ", Octave : " + octave.toString() + ", Duree" + duree.toString() + "";
        else if (isSilence()) return "Silence de durée : " + duree.toString();
        else return "Séparation : | ";
    }
}
