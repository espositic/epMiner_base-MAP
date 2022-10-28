package data;

/**
 * La classe DiscreteAttribute che estende la classe Attribute e modella un
 * attributo discreto rappresentando l’insieme di valori distinti del relativo dominio.
 */
public class DiscreteAttribute extends Attribute {

    /**
     * Array di stringhe, una per ciascun valore discreto , che rappresenta
     * il domino dell’attributo.
     */
    private String[] values;

    /**
     * Invoca il costruttore della classe madre e
     * avvalora l'array values[ ] con i valori discreti in input.
     * @param name Nome simbolico dell'attributo.
     * @param index Identificativo numerico dell'attributo.
     * @param values Valori discreti che ne costituiscono il dominio.
     */
    DiscreteAttribute(final String name, final int index, final String[] values) {
        super(name, index);
        this.values = values;
    }

    /**
     * Restituisce la cardinalità del membro values.
     * @return Numero di valori discreti dell'attributo.
     */
    public int getNumberOfDistinctValues() {
        return values.length;
    }

    /**
     * Restituisce il valore in posizione i del membro values.
     * @param index Indice di tipo intero.
     * @return Restituisce un valore nel dominio dell’attributo.
     */
    public String getValue(final int index) {
        return values[index];
    }
}
