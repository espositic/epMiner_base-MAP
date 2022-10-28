package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe MultiServer modella un server in grado di accettare la richiesta
 * trasmesse da un generico Client e istanzia un oggetto della classe ServerOneClient che si
 * occupera di servire le richieste del client in un thred dedicato. Il Server sarà registrato su una
 * porta predefinita.
 */
public class MultiServer {

    /**
     * Porta su cui sará registrato il server.
     */
    private static final int PORT = 8080;

    /**
     * Crea un oggetto istanza di MultiServer.
     * @param args Array che contiene gli input per il programma.
     */
    public static void main(final String[] args) {
        MultiServer ms = new MultiServer();
    }

    /**
     * Costruttore che invoca il metodo privato run.
     */
    MultiServer() {
        try {
            run();
        } catch (IOException e) {
        }
    }

    /**
     * Assegna ad una variabile locale s il riferimento ad una istanza della
     * classe ServerSocket creata usando la porta PORT. s si pone in attesa di richieste di
     * connessione da parte di client in risposta alle quali viene restituito l’oggetto Socket
     * da passare come argomento al costruttore della classe ServeOneClient.
     * @throws IOException Eccezione Input/Output.
     */
    private void run() throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = s.accept();
                new ServeOneClient(socket);
            }
        } finally {
            s.close();
        }
    }

}
