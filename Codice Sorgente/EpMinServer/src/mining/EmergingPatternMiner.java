package mining;

import data.Data;
import data.EmptySetException;
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
 * La classe EmergingPatternMiner che modella la scoperta di
 * emerging pattern partire dalla lista di frequent pattern.
 */
public class EmergingPatternMiner implements Iterable<EmergingPattern>, Serializable {

    /**
     * Collezione che contiene riferimenti a oggetti istanza della
     * classe EmergingPattern che definiscono il pattern.
     */
    private LinkedList<EmergingPattern> epList = new LinkedList();

    /**
     * Si scandiscono tutti i frequent pattern in fpList , per
     * ognuno di essi si calcola il grow rate usando dataBackground e se tale
     * valore è maggiore uguale di minG allora il pattern è aggiunto ad epList.
     * @param dataBackground Dataset di background.
     * @param fpList Pattern Frequenti di cui dobbiamo calcolare il growrate.
     * @param minG Growrate minimo affinché un pattern frequente possa essere
     *             considerato emergente.
     * @throws EmptySetException Eccezione set vuoto.
     * @throws EmergingPatternException Eccezione Pattern Emergente non soddisfa
     * il growrate minimo.
     */
    public EmergingPatternMiner(final Data dataBackground, final FrequentPatternMiner fpList, final float minG) throws EmptySetException, EmergingPatternException {
        if (dataBackground == null) {
            throw new EmptySetException();
        }
        int i = 0;
        while (i < fpList.getOutputFP().size()) {
            FrequentPattern fp = (FrequentPattern) fpList.getOutputFP().get(i);
            EmergingPattern ep;
            try {
                ep = computeEmergingPattern(dataBackground, fp, minG);
                if (ep != null) {
                    epList.add(ep);
                }
            } catch (EmergingPatternException e) {
                e.notValidGrowrate();
            }
            i++;
            sort(epList, new ComparatorGrowRate());
        }
    }

    /**
     * Si ottiene da fp il suo supporto relativo al dataset
     * target. Si calcola il supporto di fp relativo al dataset di background. Si
     * calcola il grow rate come rapporto dei due supporti.
     * @param dataBackground L’insieme delle transazioni di background.
     * @param fp Il pattern frequente di cui calcolare il growrate.
     * @return Il growrate del Frequent Pattern.
     */
    float computeGrowRate(final Data dataBackground, final FrequentPattern fp) {
        float supportTarget = fp.getSupport();
        float supportBackground = fp.computeSupport(dataBackground);
        float growrate = supportTarget / supportBackground;
        return growrate;
    }

    /**
     * Verifica che il gorw rate di fp sia maggiore di minGR.
     * In caso affermativo crea un oggetto EmemrgingPattern da fp.
     * @param dataBackground Insieme di transazioni di background.
     * @param fp Frequent Pattern.
     * @param minGR Minimo Growrate.
     * @return Restituisce l'emerging pattern creato da fp se la condizione
     * sul grow rate è soddisfatta, null altrimenti.
     * @throws EmergingPatternException Eccezione Pattern Emergente non soddisfa
     * il growrate minimo.
     */
    EmergingPattern computeEmergingPattern(final Data dataBackground, final FrequentPattern fp, final float minGR) throws EmergingPatternException {
        float growrate = computeGrowRate(dataBackground, fp);
        if (growrate >= minGR) {
            EmergingPattern ep = new EmergingPattern(fp, growrate);
            return ep;
        } else {
            throw new EmergingPatternException();
        }
    }

    /**
     * Scandisce epList al fine di concatenare in un'unica
     * stringa le stringhe rappresentati i pattern emergenti letti.
     * @return Stringa rappresentante il valore di epList.
     */
    @Override
    public String toString() {
        String str = "Emerging patterns\n";
        int i = 0;
        for (EmergingPattern ep: this) {
            str = str + (i + 1) + ":" + ep + "\n";
            i++;
        }
        return str;
    }

    /**
     * Restituisce il riferimento all'oggetto iteratore
     * sulla collezione di Emerging Pattern.
     * @return Oggetto iteratore per Emerging Pattern.
     */
    @Override
    public Iterator<EmergingPattern> iterator() {
        return epList.iterator();
    }

    /**
     * Metodo che ordina epList secondo il metodo Compare().
     * @param ep Collezione da ordinare secondo il growrate.
     * @param cg Istanza di ComparatorGrowRate che ci permette
     *           di fare i confronti fra growrate.
     */
    private void sort(final LinkedList<EmergingPattern> ep, final ComparatorGrowRate cg) {
        Collections.sort(ep, cg);
    }

    /**
     * Metodo che si occupa di serializzare l’oggetto riferito da this nel
     * file il cui nome è passato come parametro.
     * @param nomeFile Nome del File in cui verranno serializzate le
     *                 istanze degli oggetti.
     * @throws FileNotFoundException Eccezione File non trovtato.
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
     * @return Istanza di EmergingPatternMiner.
     * @throws FileNotFoundException Eccezione classe non trovata.
     * @throws IOException Eccezione Input/Output.
     * @throws ClassNotFoundException Eccezione classe non trovata.
     */
    public static EmergingPatternMiner carica(final String nomeFile) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream streamIn = new FileInputStream(nomeFile);
        ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
        EmergingPatternMiner ep = (EmergingPatternMiner) objectinputstream.readObject();
        return ep;
    }

    /**
     * Metodo che ci permette di capire se epList é
     * vuota o meno.
     * @return Restituisce true se epList é vuota, false
     * altrimenti.
     */
    public boolean isEmpty() {
        return epList.isEmpty();
    }

}