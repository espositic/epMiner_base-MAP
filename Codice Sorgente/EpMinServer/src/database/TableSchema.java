package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Table_Schema modella lo schema di una tabella nel database
 * relazionale.
 */
public class TableSchema {
    /**
     * Connessione.
     */
    private Connection connection;

    /**
     * Metodo costruttore di TableSchema.
     * @param tableName Nome della tabella.
     * @param connection Connessione.
     * @throws SQLException Eccezione di tipo SQL.
     */
    public TableSchema(final String tableName, final Connection connection) throws SQLException {
        this.connection = connection;
        HashMap<String, String> mapSQL_JAVATypes = new HashMap();
        mapSQL_JAVATypes.put("CHAR","string");
        mapSQL_JAVATypes.put("VARCHAR","string");
        mapSQL_JAVATypes.put("LONGVARCHAR","string");
        mapSQL_JAVATypes.put("BIT","string");
        mapSQL_JAVATypes.put("SHORT","number");
        mapSQL_JAVATypes.put("INT","number");
        mapSQL_JAVATypes.put("LONG","number");
        mapSQL_JAVATypes.put("FLOAT","number");
        mapSQL_JAVATypes.put("DOUBLE","number");

        DatabaseMetaData meta = connection.getMetaData();
        ResultSet res = meta.getColumns(null, null, tableName, null);

        while (res.next()) {
            if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME"))) {
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
                );
            }
        }
        res.close();

    }

    /**
     * Classe che modella la colonna di una tabella.
     */
    public class Column {

        /**
         * Nome della colonna.
         */
        private String name;
        /**
         * Tipo di dato contenuto nella colonna.
         */
        private String type;

        /**
         * Metodo costruttore della colonna.
         * @param name nome della colonna.
         * @param type tipo di dato contenuto nella
         *             colonna.
         */
        Column(final String name, final String type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Restituisce il nome della colonna.
         * @return Nome della colonna.
         */
        public String getColumnName() {
            return name;
        }

        /**
         * Controlla che type sia uguale a "number".
         * @return Valore booleano se type sia uguale a
         * "number" o meno.
         */
        public boolean isNumber() {
            return type.equals("number");
        }

        /**
         * Metodo che rende trasforma il dato in stringa.
         * @return Stringa contenente nome e tipo.
         */
        public String toString() {
            return name + ":" + type;
        }

    }

    /**
     * Tabella creata come insieme di Colonne.
     */
    List<Column> tableSchema = new ArrayList();

    /**
     * Restituisce il numero di attributi della tabella.
     * @return Numero di attributi.
     */
    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * Restituisce la colonna dalla tabella dato
     * l'indice.
     * @param index indice della tabella.
     * @return Colonna all'indice dato in input.
     */
    public Column getColumn(final int index) {
        return tableSchema.get(index);
    }

}