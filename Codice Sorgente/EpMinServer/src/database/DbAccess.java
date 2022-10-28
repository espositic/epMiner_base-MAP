package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce l'accesso al DB per la lettura dei dati di training.
 * @author Map Tutor
 */
public class DbAccess {

    /**
     * Per utilizzare questo Driver scaricare e aggiungere al
     * classpath il connettore mysql connector).
     */
    private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    /**
     * Descrive il nome del DBMS.
     */
    private final String DBMS = "jdbc:mysql";
    /**
     * Contiene l’identificativo del server su cui
     * risiede la base di dati (per esempio localhost).
     */
    private final String SERVER = "localhost";
    /**
     * La porta su cui il DBMS MySQL accetta le connessioni.
     */
    private final int PORT = 3306;
    /**
     * Contiene il nome della base di dati.
     */
    private final String DATABASE = "Map";
    /**
     * Contiene il nome dell’utente per l’accesso
     * alla base di dati.
     */
    private final String USER_ID = "Student";
    /**
     * Contiene la password di autenticazione per
     * l’utente identificato da USER_ID.
     */
    private final String PASSWORD = "map";
    /**
     * Gestisce una connessione.
     */
    private Connection conn;

    /**
     * impartisce al class loader l’ordine di caricare il driver
     * mysql, inizializza la connessione riferita da conn.
     @throws DatabaseConnectionException Eccezione database.
     */
    public void initConnection() throws DatabaseConnectionException {
        String connectionString =  DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        try {
            Class.forName(DRIVER_CLASS_NAME).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException(e.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException(e.toString());
        } catch (ClassNotFoundException e) {
            System.out.println("Impossibile trovare il Driver: " + DRIVER_CLASS_NAME);
            throw new DatabaseConnectionException(e.toString());
        }
        try {
            conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Impossibile connettersi al DB");
            e.printStackTrace();
            throw new DatabaseConnectionException(e.toString());
        }
    }

    /**
     * Restituisce conn.
     * @return Oggetto di tipo connection.
     */
    public  Connection getConnection() {
        return conn;
    }

    /**
     * Chiude la connessione conn.
     */
    public  void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Impossibile chiudere la connessione");
        }
    }

}