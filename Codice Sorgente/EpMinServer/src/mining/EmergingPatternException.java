package mining;

/**
 * La classe eccezione EmergingPatternException serve
 * per modellare l’eccezione che occorre qualora il pattern corrente
 * non soddisfa la condizione di minimo grow rate.
 */
public class EmergingPatternException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Metodo che stampa "Il growrate non é valido",
     * se viene generata l'eccezione.
     */
    public void notValidGrowrate() {
        System.out.println("The growrate is not valid.");
    }
}