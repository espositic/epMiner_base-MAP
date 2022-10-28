package mining;

import data.ContinuousAttribute;
import data.Data;
import data.DiscreteAttribute;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * La classe FrequentPattern che rappresenta un itemset (o
 * pattern) frequente.
 */
class FrequentPattern implements Iterable<Item>, Comparable<FrequentPattern>, Serializable {

    /**
     * Collezione che contiene riferimenti a oggetti istanza della classe
     * Item che definiscono il pattern.
     * @uml.property  name="fp"
     * @uml.associationEnd  multiplicity="(0 -1)"
     */
    private LinkedList<Item> fp;
    /**
     * Valore di supporto calcolato per il pattern fp.
     * @uml.property  name="support"
     */
    private float support;

    /**
     * Costruttore che alloca fp come LinkedList.
     */
    FrequentPattern() {
        fp = new LinkedList<>();
    }

    /**
     * Costruttore che alloca fp e support come copia del
     * frequent pattern FP passato.
     * @param FP Frequent pattern da copiare nel nuovo Frequent Pattern.
     */
    FrequentPattern(final FrequentPattern FP) {
        int length = FP.getPatternLength();
        fp = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            fp.add(i, FP.getItem(i));
        }
        support = FP.getSupport();
    }

    /**
     * Si estende la dimensione di fp di 1 e si inserisce il
     * ultima posizione l’argomento della procedura.
     * @param item Oggetto Item da aggiungere al pattern.
     */
    void addItem(final Item item) {
        int length = fp.size();
        LinkedList<Item> temp = (LinkedList<Item>) fp.clone();
        temp.add(length, item);
        fp = (LinkedList<Item>) temp.clone();
    }

    /**
     * Restituisce l'item in posizione index di fp.
     * @param index Posizione in fp.
     * @return Item che occupa la posizione indicata in fp.
     */
    Item getItem(final int index) {
        return fp.get(index);
    }

    /**
     * Restituisce il membro support.
     * @return Restituisce il support del Frequent Pattern.
     */
    float getSupport() {
        return support;
    }

    /**
     * Calcola il supporto del pattern rappresentato
     * dall'oggetto this rispetto al dataset data passato come argomento.
     * @param data Valore di supporto del pattern nel dataset data.
     * @return Valore del support del Pattern Frequente.
     */
    float computeSupport(final Data data) {
        int suppCount = 0;
        for (int i = 0; i < data.getNumberOfExamples(); i++) {
            boolean isSupporting = true;
            for (Item it: this) {
                if (it instanceof DiscreteItem) {
                    DiscreteItem item = (DiscreteItem) it;
                    DiscreteAttribute attribute = (DiscreteAttribute) item.getAttribute();
                    Object valueInExample = data.getAttributeValue(i, attribute.getIndex());
                    if (!item.checkItemCondition(valueInExample)) {
                        isSupporting = false;
                        break;
                    }
                } else {
                    try {
                        ContinuousItem item = (ContinuousItem) it;
                        ContinuousAttribute attribute = (ContinuousAttribute) item.getAttribute();
                        Object valueInExample = data.getAttributeValue(i, attribute.getIndex());
                        if (!item.checkItemCondition(valueInExample)) {
                            isSupporting = false;
                            break;
                        }
                    } catch (Exception e) { }
                }
            }
            if (isSupporting) {
                suppCount++;
            }
        }
        return ((float) suppCount) / (data.getNumberOfExamples());
    }

    /**
     * Restituisce la dimensione (lunghezza) di fp.
     * @return Lunghezza del pattern.
     */
    int getPatternLength() {
        return fp.size();
    }

    /**
     * Assegna al membro support il parametro della
     * procedura.
     * @param support Valore di supporto del pattern.
     */
    void setSupport(final float support) {
        this.support = support;
    }

    /**
     * Si scandisce fp al fine di concatenare in una stringa
     * la rappresentazione degli item; alla fine si concatena il supporto.
     * @return Stringa ripresentante lo item set e il suo supporto.
     */
    @Override
    public String toString() {
        String value = "";
        for (Item it:this) {
            value += it + " AND ";
        }
        value = value.substring(1, value.lastIndexOf('A'));
        value += "[" + this.getSupport() + "]";
        return value;
    }

    /**
     * Restituisce l'iteratore di Frequent Pattern.
     * @return Oggetto iteratore.
     */
    @Override
    public Iterator<Item> iterator() {
        return fp.iterator();
    }

    /**
     * Metodo che compara due Frequent Pattern confrontando i supporti.
     * @param o Frequent pattern da confrontare.
     * @return Restituisce un valore minore, uguale o maggiore a 0 se
     * il supporto del primo pattern frequente é minore, uguale o maggiore
     * del supporto del secondo pattern frequente.
     */
    @Override
    public int compareTo(final FrequentPattern o) {
        return (int) (this.getSupport() * 1000 - o.getSupport() * 1000);
    }
}