package mining;

import data.ContinuousAttribute;

/**
 * La classe concreta ContinuousItem che estende la classe
 * astratta Item e modella la coppia <Attributo continuo -
 * Intervallo di valori >
 */
public class ContinuousItem extends Item {

    /**
     * Chiama il costruttore della superclasse passandogli
     * come argomenti attribute e value.
     * @param attribute Attributo continuo.
     * @param value Intervallo.
     */
    ContinuousItem(final ContinuousAttribute attribute, final Object value) {
        super(attribute, value);
        ContinuousAttribute ca = (ContinuousAttribute) attribute;
    }

    /**
     * Verifica che il parametro value rappresenti
     * un numero reale incluso tra gli estremi dell’intervallo associato allo item
     * in oggetto.
     * @param value Un valore ( al run time sarà di tipo Float).
     * @return L'esito della verifica.
     * @throws ClassNotFoundException eccezione che viene lanciata nel
     * caso l'oggetto non sia di tipo ContinuousItem.
     */
    @Override
    boolean checkItemCondition(final Object value) throws ClassNotFoundException {
        if (value instanceof Float && this.getValue() instanceof Interval) {
            Interval interval = (Interval) this.getValue();
            return interval.checkValueInclusion((Float) value);
        }
        throw new ClassNotFoundException();
    }

    /**
     * Avvalora la stringa che rappresenta lo stato
     * dell’oggetto e ne restituisce il riferimento.
     * @return Stringa che rappresenta lo stato dell’oggetto nella forma
     * <nome attributo> in [inf,sup[.
     */
    @Override
    public String toString() {
        String str = " <" + getAttribute() + "> in " + getValue();
        return str;
    }
}