package database;

/**
 * Classe che gestisce le eccezioni col database.
 */
public class DatabaseConnectionException extends Exception {

    /**
     * Metodo per gestire l'eccezione del database, che stamba un messaggio.
     @param msg Messaggio da stampare.
     */
    DatabaseConnectionException(String msg){
        super(msg);
    }
}