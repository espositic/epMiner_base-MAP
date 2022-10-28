package utility;

/**
 * la classe Queue che modella una struttura coda che è poi
 * usata come contenitore a modalità FIFO per i pattern frequenti scoperti
 * a livello k da usare per generare i pattern candidati a livello k+1 (classe
 * fornita dal docente).
 * @param <T> Classe da assegnare alla coda.
 */
public class Queue<T> {

    /**
     * Oggetto generico da assegnare alla coda.
     */
    private T t;

    /**
     * Restituisce la classe T.
     * @return Nome della classe T.
     */
    public T get() {
        return t;
    }

    /**
     * Modifica la classe T.
     * @param t Nuova classe per modificare T.
     */
    public void set(final T t) {
        this.t = t;
    }

    /**
     * Istanziamo l'oggetto begin di tipo Record.
     */
    private Record begin = null;
    /**
     * Istanziamo l'oggetto end di tipo Record.
     */
    private Record end = null;

    /**
     * Classe interna Record.
     */
    private class Record {
        /**
         * Istanziamo elem di tipo Object.
         */
        private Object elem;
        /**
         * Istanziamo next di tipo Record.
         */
        private Record next;

        /**
         * Metodo costruttore della classe Record.
         * @param e Oggetto da salvare all'interno di elem.
         */
        Record(final Object e) {
            this.elem = e;
            this.next = null;
        }
    }

    /**
     * Metodo che ci permette di capire se la coda é
     * vuota o meno.
     * @return True se la coda é vuota, false altrimenti.
     */
    public boolean isEmpty() {
        return this.begin == null;
    }

    /**
     * Inserisce un nuovo oggetto alla coda.
     * @param e Oggetto da inserire.
     */
    public void enqueue(final Object e) {
        if (this.isEmpty()) {
            this.begin = this.end = new Record(e);
        } else {
            this.end.next = new Record(e);
            this.end = this.end.next;
        }
    }

    /**
     * Restituisce il primo elemento della coda.
     * @return Primo elemento della coda.
     */
    public Object first() {
        return this.begin.elem;
    }

    /**
     * Metodo che va a togliere il primo elemento dalla coda.
     * @throws EmptyQueueException Eccezione coda vuota.
     */
    public void dequeue() throws EmptyQueueException {
        if (this.begin == this.end) {
            if (this.begin == null) {
                throw new EmptyQueueException();
            } else {
                this.begin = this.end = null;
            }
        } else {
            begin = begin.next;
        }
    }
}