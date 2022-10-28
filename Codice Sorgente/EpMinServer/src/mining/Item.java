package mining;

import data.Attribute;
import java.io.Serializable;

/**
 * La classe astratta Item che modella un generico item (coppia
 * attributo-valore).
 */
public abstract class Item implements Serializable {

    /**
     * Attributo coinvolto nell'item.
     */
    private Attribute attribute;
    /**
     * Valore assegnato all'attributo.
     */
    private Object value;

    /**
     * Inizializza i valori dei membri attributi con i parametri
     * passati come argomento al costruttore.
     * @param attribute Riferimento per inizializzare il campo attribute.
     * @param value Riferimento per inizializzare il campo value.
     */
    Item(final Attribute attribute, final Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Restituisce il membro attribute.
     * @return Attributo membro dell'item.
     */
    Attribute getAttribute() {
        return attribute;
    }

    /**
     * Restituisce il membro value.
     * @return Valore coinvolto nell'item.
     */
    Object getValue() {
        return value;
    }

    /**
     * Da realizzare nelle sottoclassi.
     * @param value Oggetto cui effettuare i controlli.
     * @return Un valore di tipo boolean.
     * @throws Exception Per gestire eventuali eccezioni.
     */
    abstract boolean checkItemCondition(Object value) throws Exception;

    /**
     * Restituisce una stringa nella forma
     * <attribute>=<value>.
     * @return Restituisce l'item in una stringa.
     */
    @Override
    public String toString() {
        return " <" + attribute + ">" + "<" + value + ">";
    }
}
