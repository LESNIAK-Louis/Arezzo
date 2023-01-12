package arezzo.enumerations;

import arezzo.MainAzerro;
import javafx.scene.image.Image;

/**
 * Enumération des silences
 */
public enum Silence{
    DEMI_SOUPIR("z1/2"), SOUPIR("z1"), DEMI_PAUSE("z2"), DEMI_PAUSE_POINTEE("z3"), PAUSE("z4");

    private String silence;

    /**
     * Constructeur privé
     * @param silence
     */
    private Silence(String silence)
    {
        this.silence = silence;
    }

    /**
     * Retourne la notation correspondant au silence
     * @return
     */
    public String getSilence()
    {
        return this.silence;
    }

    @Override
    public String toString()
    {
        return this.silence;
    }
}