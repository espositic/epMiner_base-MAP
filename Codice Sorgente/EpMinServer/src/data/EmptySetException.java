package data;

/**
 * La classe eccezione EmptySetException serve per
 * modellare l’eccezione che occorre qualora l'insieme di
 * training fosse vuoto.
 */
public class EmptySetException extends Exception {
    /**
     * ?
     */
    private static final long serialVersionUID = 1L;

    /**
     * Metodo che stampa "il training set é vuoto",
     * se viene generata l'eccezione.
     */
    public void trainingSetEmpty() {
        System.out.println("The training set is empty");
    }

}