package mining;

import java.io.Serializable;

/**
 * La classe Interval che modella un
 * intervallo reale [inf ,sup[.
 */
public class Interval implements Serializable {

    /**
     * Estremo inferiore dell'intervallo.
     */
    private float inf;
    /**
     * Estremo superiore dell'intervallo.
     */
    private float sup;

    /**
     * Avvalora i due attributi inf e sup con i parametri del
     * costruttore.
     * @param inf Estremo inferiore.
     * @param sup Estremo superiore.
     */
    Interval(final float inf, final float sup) {
        this.inf = inf;
        this.sup = (float) (sup + 0.1);
    }

    /**
     * Avvalora inf con il parametro passato.
     * @param inf Estremo inferiore.
     */
    void setInf(final float inf) {
        this.inf = inf;
    }

    /**
     * Avvalora sup con il parametro passato.
     * @param sup Estremo inferiore.
     */
    void setSup(final float sup) {
        this.sup = sup;
    }

    /**
     * Restituisce inf.
     * @return Estremo inferiore.
     */
    float getInf() {
        return inf;
    }

    /**
     * Restituisce sup.
     * @return Estremo superiore.
     */
    float getSup() {
        return sup;
    }

    /**
     * Restituisce vero se il parametro è maggiore uguale di inf
     * e minore di sup, false altrimenti.
     * @param value Valore assunto da una attributo continuo per il quale verificare la
     * appartenenza all’intervallo.
     * @return Esito della verifica.
     */
    boolean checkValueInclusion(final float value) {
        return (value >= inf && value < sup);
    }

    /**
     * Rappresenta in una stringa gli estremi dell’intervallo e
     * restituisce tale stringa.
     * @return Riferimento ad una stringa in cui si rappresenta l’intervallo
     * [inf,sup[.
     */
    @Override
    public String toString() {
        return "[" + inf + "," + sup + "[";
    }

}
