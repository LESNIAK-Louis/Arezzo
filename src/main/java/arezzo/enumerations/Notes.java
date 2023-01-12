package arezzo.enumerations;

/**
 * Enumération des notes
 */
public enum Notes {
    DO("c"), DO_NOIR("^c"), RE("d"), RE_NOIR("^d"), MI("e"), FA("f"), FA_NOIR("^e"), SOL("g"), SOL_NOIR("^f"), LA("a"), LA_NOIR("^g"), SI("b");


    private final static Notes[] tabNotes = {DO, DO_NOIR, RE, RE_NOIR, MI, FA, FA_NOIR, SOL, SOL_NOIR, LA, LA_NOIR, SI};
    private String note;

    /**
     * Constructeur privé
     * @param note
     */
    private Notes(String note)
    {
        this.note = note;
    }

    /**
     * Getter de la note correspondante
     * @return note
     */
    public String getNote()
    {
        return this.note;
    }

    /**
     * Retourne le tableau de toutes les notes
     * @return tabNotes
     */
    public Notes[] getNotes(){ return tabNotes; }

    /**
     * Retourne la note dans le language courant
     * @return note en français
     */
    @Override
    public String toString()
    {
        if (getNote().contains(DO_NOIR.getNote()))
            return "Do#";
        else if (getNote().contains(RE_NOIR.getNote()))
            return "Re#";
        else if (getNote().contains(FA_NOIR.getNote()))
            return "Fa#";
        else if (getNote().contains(SOL_NOIR.getNote()))
            return "Sol#";
        else if (getNote().contains(LA_NOIR.getNote()))
            return "La#";
        else if (getNote().contains(DO.getNote()))
            return "Do";
        else if (getNote().contains(RE.getNote()))
            return "Re";
        else if (getNote().contains(MI.getNote()))
            return "Mi";
        else if (getNote().contains(FA.getNote()))
            return "Fa";
        else if (getNote().contains(SOL.getNote()))
            return "Sol";
        else if (getNote().contains(LA.getNote()))
            return "La";
        else if (getNote().contains(SI.getNote()))
            return "Si";
        return "";
    }
}
