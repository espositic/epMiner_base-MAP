package data;

import java.io.Serializable;

/**
 * La classe astratta Attribute che modella un generico attributo discreto o
 * continuo.
 */
public class Attribute implements Serializable {
    /**
     *  Nome simbolico dell'attributo.
     */
    private String name;
    /**
     * Identificativo numerico dell'attributo(indica la posizione della colonna.
     */
    private int index;

    /**
     * Inizializza i valori dei membri name, index.
     * @param name Nome simbolico dell'attributo.
     * @param index Identificativo numerico dell'attributo.
     */
    Attribute(final String name, final int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Restituisce il valore nel membro name.
     * @return nome dell'attributo.
     */
    String getName() {
        return name;
    }

    /**
     * Restituisce il valore nel membro index.
     * @return Identificativo numerico dell'attributo.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Restituisce il valore del membro name.
     * @return Identificativo.
     */
    @Override
    public String toString() {
        return name;
    }

}
