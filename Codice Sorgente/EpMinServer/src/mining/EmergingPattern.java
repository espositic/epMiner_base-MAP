package mining;

import java.io.Serializable;

/**
 * La classe EmergingPattern che estende FrequenPattern
 * e modella un pattern emergente.
 */
public class EmergingPattern extends FrequentPattern implements Serializable {

    /**
     * Grow rate del pattern.
     */
    private float growrate;

    /**
     * Chiama il costruttore della superclasse passandogli fp
     * e inizializza il membro growrate con l’argomento del costruttore.
     * @param fp Frequent pattern da passare al costruttore di copia.
     * @param growrate Growrate del pattern.
     */
    EmergingPattern(final FrequentPattern fp, final float growrate) {
        super(fp);
        this.growrate = growrate;
    }

    /**
     * Restituisce il valore del membro growrate.
     * @return Growrate del pattern
     */
    float getGrowrate() {
        return growrate;
    }

    /**
     * Si crea e restituisce la stringa che rappresenta la il
     * pattern,il suo supporto e il suo growrate.
     * @return Pattern Emergente sottoforma di Stringa.
     */
    @Override
    public String toString() {
        String value = "";
        for (Item it: this) {
            value += it + " AND ";
        }
        value = value.substring(1, value.lastIndexOf('A'));
        value = value + "[" + getSupport() + "] [" + growrate + "]";
        return value;
    }
}
