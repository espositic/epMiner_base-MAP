package mining;

import java.util.Comparator;

/**
 * La classe comparatore ComparatorGrowRate implementa
 * l'interfaccia generica Comparator<T> (T istanziato come
 * EmergingPattern).
 */
public class ComparatorGrowRate implements Comparator<EmergingPattern> {

    /**
     * Il metodo permette il confronto tra due emerging
     * pattern rispetto al grow rate.
     * @param o1 primo Emerging Pattern
     * @param o2 secondo Emerging Pattern
     * @return Ritorna un valore di tipo intero
     * maggiore, uguale o minore se il growrate
     * del primo Emerging Pattern
     * è rispettivamente maggiore, uguale o minore
     * al growrate del secondo Emerging Pattern.
     */
    @Override
    public int compare(EmergingPattern o1, EmergingPattern o2) {
        return (int) (o1.getGrowrate()*1000-o2.getGrowrate()*1000);
    }
}
