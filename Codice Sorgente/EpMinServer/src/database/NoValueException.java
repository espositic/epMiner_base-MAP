package database;

/**
 * Classe NoValueException che estende Exception per modellare l’assenza
 * di un valore all’interno di un resultset.
 */
public class NoValueException extends Exception {

    /**
     * Metodo costruttore.
     * @param msg Messaggio passato al metodo
     *            costruttore della superclasse.
     */
    public NoValueException(final String msg) {
        super(msg);
    }

}