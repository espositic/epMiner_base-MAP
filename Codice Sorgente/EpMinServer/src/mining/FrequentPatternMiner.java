package mining;

import data.Attribute;
import data.ContinuousAttribute;
import data.Data;
import data.DiscreteAttribute;
import data.EmptySetException;
import utility.EmptyQueueException;
import utility.Queue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * La classe FrequentPatternMiner che include i metodi per la
 * scoperta di pattern frequenti con Algoritmo APRIOR.
 */
public class FrequentPatternMiner implements Iterable<FrequentPattern>, Serializable {

    /**
     * Lista che contiene riferimenti a oggetti istanza della
     * classe FrequentPattern che definiscono il pattern.
     */
    private LinkedList<FrequentPattern> outputFP = new LinkedList<FrequentPattern>();

    /**
     * Costruttore che genera tutti i pattern k=1 frequenti e
     * per ognuno di questi genera quelli con k>1 richiamando
     * expandFrequentPatterns().
     * I pattern sono memorizzati nel membro OutputFP.
     * @param data L’insieme delle transazioni.
     * @param minSup Il minimo supporto.
     * @throws EmptySetException Per gestire l'eccezione nel caso
     * l'insieme data fosse vuoto.
     */
    public FrequentPatternMiner(final Data data, final float minSup) throws EmptySetException {
        if (data == null) {
            throw new EmptySetException();
        }
        Queue<FrequentPattern> fpQueue = new Queue<FrequentPattern>();
        for (int i = 0; i < data.getNumberOfAttributes(); i++) {
            Attribute currentAttribute = data.getAttribute(i);
            if (currentAttribute instanceof DiscreteAttribute) {
                for (int j = 0; j < ((DiscreteAttribute) currentAttribute).getNumberOfDistinctValues(); j++) {
                    DiscreteItem item = new DiscreteItem(
                            (DiscreteAttribute) currentAttribute,
                            ((DiscreteAttribute) currentAttribute).getValue(j));
                    FrequentPattern fp = new FrequentPattern();
                    fp.addItem(item);
                    fp.setSupport(fp.computeSupport(data));
                    if (fp.getSupport() >= minSup) {
                        fpQueue.enqueue(fp);
                        outputFP.add(fp);
                    }
                }
            } else if (currentAttribute instanceof ContinuousAttribute) {
                ContinuousAttribute continuousAttribute = (ContinuousAttribute) currentAttribute;
                Iterator<Float> it = continuousAttribute.iterator();
                float min = it.next();
                while (it.hasNext()) {
                    float next = it.next();
                    ContinuousItem item = new ContinuousItem(continuousAttribute, new Interval(min, next));
                    FrequentPattern fp = new FrequentPattern();
                    fp.addItem(item);
                    fp.setSupport(fp.computeSupport(data));
                    if (fp.getSupport() >= minSup) {
                        fpQueue.enqueue(fp);
                        outputFP.add(fp);
                    }
                    min = next;
                }
            }
        }
        outputFP = expandFrequentPatterns(data, minSup, fpQueue, outputFP);
        sort(outputFP);
    }

    /**
     * Crea un nuovo pattern a cui aggiunge tutti gli item di
     * FP e il parametro item.
     * @param FP Pattern FP da raffinare.
     * @param item Item da aggiungere ad FP.
     * @return Nuovo pattern ottenuto per effetto del raffinamento.
     */
    private FrequentPattern refineFrequentPattern(final FrequentPattern FP, final Item item) {
        int length = FP.getPatternLength();
        FrequentPattern fp = new FrequentPattern();
        for (int i = 0; i < length; i++) {
            fp.addItem(FP.getItem(i));
        }
        fp.addItem(item);
        fp.setSupport(FP.getSupport());
        return fp;
    }

    /**
     * Finché fpQueue contiene elementi, si estrae un
     * elemento dalla coda fpQueue, si generano i raffinamenti per questo
     * (aggiungendo un nuovo item non incluso). Per ogni raffinamento si
     * verifica se è frequente e, in caso affermativo, lo si aggiunge sia ad
     * fpQueue sia ad outputFP.
     * @param data L’insieme delle transazioni.
     * @param minSup Minimo supporto.
     * @param fpQueue Coda contente i pattern da valutare.
     * @param outputFP Lista dei pattern
     * frequenti già estratti.
     * @return Lista linkata popolata con pattern frequenti a k>1.
     */
    private LinkedList<FrequentPattern> expandFrequentPatterns(final Data data, final float minSup, final Queue fpQueue, final LinkedList<FrequentPattern> outputFP) {
        while (!fpQueue.isEmpty()) {
            FrequentPattern fp = (FrequentPattern) fpQueue.first();
            try {
                fpQueue.dequeue();
            } catch (EmptyQueueException e) {
                e.queueEmpty();
            }
            for (int i = 0; i < data.getNumberOfAttributes(); i++) {
                boolean found = false;
                for (int j = 0; j < fp.getPatternLength(); j++) {
                    if (fp.getItem(j).getAttribute().equals(data.getAttribute(i))) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (data.getAttribute(i) instanceof DiscreteAttribute) {
                        for (int j = 0; j < ((DiscreteAttribute) data.getAttribute(i)).getNumberOfDistinctValues(); j++) {
                            DiscreteItem item = new DiscreteItem(
                                    (DiscreteAttribute) data.getAttribute(i),
                                    ((DiscreteAttribute) (data.getAttribute(i))).getValue(j));
                            FrequentPattern newFP = refineFrequentPattern(fp, item);
                            newFP.setSupport(newFP.computeSupport(data));
                            if (newFP.getSupport() >= minSup) {
                                fpQueue.enqueue(newFP);
                                outputFP.add(newFP);
                            }
                        }
                    } else if (data.getAttribute(i) instanceof ContinuousAttribute) {
                        ContinuousAttribute continuousAttribute = (ContinuousAttribute) data.getAttribute(i);
                        Iterator<Float> it = continuousAttribute.iterator();
                        float min = it.next();
                        while (it.hasNext()) {
                            float next = it.next();
                            ContinuousItem item = new ContinuousItem(continuousAttribute, new Interval(min, next));
                            FrequentPattern newFP = refineFrequentPattern(fp, item);
                            newFP.setSupport(newFP.computeSupport(data));
                            if (newFP.getSupport() >= minSup) {
                                fpQueue.enqueue(newFP);
                                outputFP.add(newFP);
                            }
                            min = next;
                        }
                    }
                }
            }
        }
        return outputFP;
    }

    /**
     * Scandisce OutputFp al fine di concatenare in un'unica
     * stringa i pattern frequenti letti.
     * @return Stringa rappresentante il valore di OutputFP.
     */
    @Override
    public String toString() {
        String str = "Frequent patterns\n";
        int i = 0;
        for (FrequentPattern fp: this) {
            str = str + (i + 1) + ":" + fp + "\n";
            i++;
        }
        return str;
    }

    /**
     * Restituisce il riferimento di OutputFP.
     * @return Il riferimento di OutputFP.
     */
    protected LinkedList<FrequentPattern> getOutputFP() {
        return outputFP;
    }

    /**
     * Restituisce il riferimento all'oggetto iteratore
     * sulla collezione di Frequent Pattern.
     * @return Oggetto iteratore per Frequent Pattern.
     */
    @Override
    public Iterator<FrequentPattern> iterator() {
        return outputFP.iterator();
    }

    /**
     * Metodo che ordina OutputFP secondo il metodo CompareTo().
     * @param outputFP Collezione da ordinare secondo i supporti
     *                 dei FrequentPattern.
     */
    private void sort(final LinkedList<FrequentPattern> outputFP) {
        Collections.sort(outputFP);
    }

    /**
     * Metodo che si occupa di serializzare l’oggetto riferito da this nel
     * file il cui nome è passato come parametro.
     * @param nomeFile Nome del File in cui verranno serializzate le
     *                 istanze degli oggetti.
     * @throws FileNotFoundException Eccezione file non trovato.
     * @throws IOException Eccezione Input/Output.
     */
    public void salva(final String nomeFile) throws FileNotFoundException, IOException {
        FileOutputStream outFile = new
                FileOutputStream(nomeFile);
        ObjectOutputStream outStream = new
                ObjectOutputStream(outFile);
        outStream.writeObject(this);
    }

    /**
     * Metodo che si occupa di leggere e restituire l’oggetto come è memorizzato nel file il
     * cui nome è passato come parametro.
     * @param nomeFile Nome del File da cui verranno serializzate le
     *                 istanze degli oggetti.
     * @return Istanza di FrequentPatternMiner.
     * @throws FileNotFoundException Eccezione file non trovato.
     * @throws IOException Eccezione Input/Output.
     * @throws ClassNotFoundException Eccezione classe non trovata.
     */
    public static FrequentPatternMiner carica(final String nomeFile) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream streamIn = new FileInputStream(nomeFile);
        ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
        FrequentPatternMiner fp = (FrequentPatternMiner) objectinputstream.readObject();
        return fp;
    }

    /**
     * Metodo che ci permette di capire se OutputFP é
     * vuoto o meno.
     * @return Restituisce true se OutputFP é vuoto, false
     * altrimenti.
     */
    public boolean isEmpty() {
        return outputFP.isEmpty();
    }
}