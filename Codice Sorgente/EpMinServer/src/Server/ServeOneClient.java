package Server;

import data.Data;
import data.EmptySetException;
import database.DatabaseConnectionException;
import database.NoValueException;
import mining.EmergingPatternException;
import mining.EmergingPatternMiner;
import mining.FrequentPatternMiner;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Definire la classe ServerOneClient che estende la classe Thread che modella la
 * comunicazione con un unico client.
 */
public class ServeOneClient extends Thread {

    /**
     * Terminale lato server del canale tramite cui avviene lo scambio di
     * oggetti client-server.
     */
    private Socket socket;
    /**
     * Flusso di oggetti in input al server.
     */
    private ObjectInputStream in;
    /**
     *  Flusso di oggetti in output dal server al client.
     */
    private ObjectOutputStream out;

    /**
     * Costruttore. Inizia il membro
     * this.socket con il parametro in input al costruttore. Inizializza in e out, avvia il
     * thread invocando il metodo start() (ereditato da Thread).
     * @param socket Socket da inizializzare.
     * @throws IOException Eccezione Input/Output.
     */
    ServeOneClient(final Socket socket) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    /**
     * Ridefinisce il metodo run della classe Thread.
     * Gestisce le richieste del client (apprendere pattern/regole e popolare con queste
     * archive; salvare archive in un file, avvalorare archive con oggetto serializzato nel
     * file).
     */
    public void run() {
        int opzione = 0;
        float minSup;
        float minGr;
        String targetName;
        String backgroundName;
        boolean clientConnesso = true;
        while (clientConnesso) {
            try {
                opzione = (int) in.readObject();
                minSup = (float) in.readObject();
                minGr = (float) in.readObject();
                targetName = (String) in.readObject();
                backgroundName = (String) in.readObject();
                if (opzione == 1) {
                    Data dataTarget = new Data(targetName);
                    Data dataBackground = new Data(backgroundName);
                    try {
                        FrequentPatternMiner fpMiner = new FrequentPatternMiner(dataTarget, minSup);
                        try {
                            fpMiner.salva("FP_playtennis_minSup" + minSup + ".dat");
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        if (!fpMiner.isEmpty()) {
                            out.writeObject(fpMiner.toString());
                        } else {
                            out.writeObject("Errore: Nessun pattern frequente trovato!\n");
                        }
                        try {
                            EmergingPatternMiner epMiner = new EmergingPatternMiner(dataBackground, fpMiner, minGr);
                            try {
                                epMiner.salva("EP_playtennis_minSup" + minSup + "_minGr" + minGr + ".dat");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            if (!epMiner.isEmpty()) {
                                out.writeObject(epMiner.toString());
                            } else {
                                out.writeObject("Errore: Nessun pattern emergente trovato!\n");
                            }
                        } catch (EmptySetException e) {
                            System.out.println(e);
                        } catch (EmergingPatternException e) {
                            e.printStackTrace();
                        }
                    } catch (EmptySetException e) {
                        System.out.println(e);
                    }
                } else {
                    try {
                        FrequentPatternMiner fpMiner = FrequentPatternMiner.carica("FP_playtennis_minSup" + minSup + ".dat");
                        out.writeObject(fpMiner.toString());
                        EmergingPatternMiner epMiner = EmergingPatternMiner.carica("EP_playtennis_minSup" + minSup + "_minGr" + minGr + ".dat");
                        out.writeObject(epMiner.toString());
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException | NoValueException e) {
                e.printStackTrace();
                clientConnesso = false;
            } catch (DatabaseConnectionException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
