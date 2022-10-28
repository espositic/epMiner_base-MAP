package data;

import java.util.Iterator;

/**
 * La classe ContinuousAttributeIterator (nel package data) che
 * implementa l’interfaccia Iterator<Float>. Tale classe realizza l’iteratore
 * che itera sugli elementi della sequenza composta da numValues valori
 * reali equidistanti tra di loro (cut points) compresi tra min e max ottenuti
 * per mezzo di discretizzazione. La classe implementa i metodi della
 * interfaccia generica Iterator<T> tipizzata con Float.
 */
public class ContinuousAttributeIterator implements Iterator<Float> {

    /**
     * Minimo.
     */
    private float min;
    /**
     * Massimo.
     */
    private float max;
    /**
     * Posizione dell’iteratore nella collezione di cut point
     * generati per [min, max[ tramite discretizzazione.
     */
    private int j = 0;
    /**
     * Numero di intervalli di discretizzazione.
     */
    private int numValues;

    /**
     * Avvalora i membri attributo della classe con i
     * parametri del costruttore.
     * @param min Minimo.
     * @param max Massimo.
     * @param numValues Numero di intervalli di discretizzazione.
     */
    ContinuousAttributeIterator(final float min, final float max, final int numValues) {
        this.min = min;
        this.max = max;
        this.numValues = numValues;
    }

    /**
     * Restituisce true se j<=numValues, false altrimenti.
     * @return Esito della verifica.
     */
    @Override
    public boolean hasNext() {
        return (j <= numValues);
    }

    /**
     * Incrementa j, restituisce il cut point in posizione j-1
     * (min + (j-1)*(max-min)/numValues).
     * @return Cut point in posizione j-1.
     */
    public Float next() {
        j++;
        return min + ((max - min) / numValues) * (j - 1);
    }

    /**
     * ???
     */
    public void remove() {

    }

}