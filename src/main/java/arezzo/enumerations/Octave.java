package arezzo.enumerations;

/**
 * Enumération des octaves
 */
public enum Octave{
    GRAVE(0), MEDIUM(1), AIGU(2);

    private int octave;

    /**
     * Constructeur privé
     * @param octave
     */
    private Octave(int octave){
        this.octave = octave;
    }

    /**
     * Retourne l'entier correspondant à l'octave
     * @return
     */
    public int getOctave() {
        return octave;
    }

    /**
     * Retourne l'octave en notation française
     * @return
     */
    @Override
    public String toString() {
        switch(octave){
            case 0:
                return "GRAVE";
            case 1:
                return "MEDIUM";
            case 2:
                return "AIGU";
            case 3:
                return "SEPARATION";
        }
        return "AIGU";
    }
}