package arezzo.enumerations;

/**
 * Enumération des instruments
 */
public enum Instrument{
    PIANO("Piano"), GUITARE("Guitare"), SAXOPHONE("Saxophone"), TROMPETTE("Trompette");

    private String instrument;

    /**
     * Constructeur privé
     * @param instrument
     */
    private Instrument(String instrument){
        this.instrument = instrument;
    }

    /**
     * Retourne le string corresponcant à l'instrument
     * @return
     */
    public String getInstrument() {
        return instrument;
    }

    @Override
    public String toString() {
        return instrument;
    }
}