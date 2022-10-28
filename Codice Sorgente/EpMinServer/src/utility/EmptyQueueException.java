package utility;

/**
 * La classe eccezione EmptyQueueException serve
 * per modellare l’eccezione che occorre qualora si cerca di
 * leggere/cancellare da una coda è vuota.
 */
public class EmptyQueueException extends Exception {

    /**
     * ?
     */
    private static final long serialVersionUID = 1L;

    /**
     * Metodo che stampa "la coda é vuoto",
     * se viene generata l'eccezione.
     */
    public void queueEmpty() {
        System.out.println("The queue is empty");
    }

}