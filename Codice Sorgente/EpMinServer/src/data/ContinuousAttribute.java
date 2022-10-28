package data;

import java.util.Iterator;

/**
 * La classe ContinuousAttribute che estende la classe Attribute e modella
 * un attributo continuo (numerico) rappresentandone il dominio [min,max].
 */
public class ContinuousAttribute  extends  Attribute implements Iterable<Float> {

    /**
     * Rappresenta l'estremo maggiore di un intervallo.
     */
    private float max;
    /**
     * Rappresenta l'estremo minore di un intervallo.
     */
    private float min;

    /**
     * Invoca il costruttore della classe madre e avvalora i membri.
     * @param name Nome dell’attributo.
     * @param index Identificativo numerico dell'attributo.
     * @param min Estremo minore dell'intervallo.
     * @param max Estremo maggiore dell'intervallo.
     */
    ContinuousAttribute(final String name, final int index, final float min, final float max) {
        super(name, index);
        this.max = max;
        this.min = min;
    }

    /**
     * Restituisce il valore del membro min.
     * @return Estremo inferiore dell'intervallo.
     */
    public float getMin() {
        return min;
    }

    /**
     * Restituisce il valore del membro max.
     * @return Estremo superiore dell'intervallo.
     */
    public float getMax() {
        return max;
    }

    /**
     * Istanzia e restituisce un riferimento ad oggetto di
     * classe ContinuousAttributeIterator con numero di intervalli di
     * discretizzazione pari a 5.
     * @return Riferimento a una istanza di ContinuousAttributeIterator.
     */
    @Override
    public Iterator<Float> iterator() {
        return new ContinuousAttributeIterator(min, max, 5);
    }
}
