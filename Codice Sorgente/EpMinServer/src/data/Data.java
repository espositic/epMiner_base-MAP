package data;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.List;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableData.TupleData;
import database.TableSchema;

/**
 * Classe che modella  l'insieme delle transazioni.
 */
public class Data {

    /**
     * Una matrice di Object che ha numero di righe pari al numero di
     * transazioni da memorizzare e numero di colonne pari al numero di attributi in
     * ciascuna transazione.
     * @uml.property  name="data" multiplicity="(0 -1)" dimension="2"
     */
    private Object[][] data;
    /**
     * Cardinalità dell’insieme di transazioni.
     * @uml.property  name="numberOfExamples"
     */
    private int numberOfExamples;
    /**
     * Una collezione di attributi, che sono avvalorati in ciascuna
     * transazione.
     * @uml.property  name="attributeSet"
     * @uml.associationEnd  multiplicity="(0 -1)"
     */
    private List<Attribute> attributeSet = new LinkedList<Attribute>();

    /**
     * //
     * @param tableName nome della tabella.
     * @throws DatabaseConnectionException Nel caso vi siano problemi
     * di connessione col database.
     * @throws SQLException Eccezione SQL.
     * @throws NoValueException Eccezione ?.
     */
    public Data(final String tableName) throws DatabaseConnectionException, SQLException, NoValueException {
        // open db connection

        DbAccess db = new DbAccess();
        db.initConnection();

        TableSchema tSchema;
        try {
            tSchema = new TableSchema(tableName, db.getConnection());
            TableData tableData = new TableData(db.getConnection());
            List<TupleData> transSet = tableData.getTransazioni(tableName);

            data = new Object[transSet.size()][tSchema.getNumberOfAttributes()];
            for (int i = 0; i < transSet.size(); i++) {
                for (int j = 0; j < tSchema.getNumberOfAttributes(); j++) {
                    data[i][j] = transSet.get(i).tuple.get(j);
                }
            }

            numberOfExamples = transSet.size();

            for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
                if (tSchema.getColumn(i).isNumber()) {
                    attributeSet.add(
                            new ContinuousAttribute(
                                    tSchema.getColumn(i).getColumnName(),
                                    i,
                                    ((Float) (tableData.getAggregateColumnValue(tableName, tSchema.getColumn(i), QUERY_TYPE.MIN))).floatValue(),
                                    ((Float) (tableData.getAggregateColumnValue(tableName, tSchema.getColumn(i), QUERY_TYPE.MAX))).floatValue()
                            )
                    );
                } else {
                    // avvalora values con i valori distinti della colonna oridinati in maniera crescente
                    List<Object> valueList = tableData.getDistinctColumnValues(tableName,tSchema.getColumn(i));
                    String[] values = new String[valueList.size()];
                    Iterator it = valueList.iterator();
                    int ct = 0;
                    while (it.hasNext()) {
                        values[ct] = (String) it.next();
                        ct++;
                    }
                    attributeSet.add(new DiscreteAttribute(tSchema.getColumn(i).getColumnName(), i, values));
                }
            }
        } catch (Exception e) {
            System.out.println("Errore");
        } finally {
            db.closeConnection();
        }

    }

    /**
     * Restituisce il valore del membro numberOfExamples.
     * @return Cardinalità dell'insieme di transazioni.
     * @uml.property  name="numberOfExamples"
     */
    public int getNumberOfExamples() {
        return numberOfExamples;
    }

    /**
     * Restituisce la cardinalità del membro attributeSet.
     * @return Cardinalità dell'insieme degli attributi.
     */
    public int getNumberOfAttributes() {
        return attributeSet.size();
    }

    /**
     * Restituisce il valore dell' attributo attributeIndex per la transazione
     * exampleIndex meomorizzata in data.
     * @param exampleIndex Indice di riga per la matrice data che corrisponde ad una specifica
     * transazione.
     * @param attributeIndex Indice di colonna per un attributo.
     * @return Valore assunto dall’attributo identificato da attributeIndex nella transazione
     * identificata da exampleIndex nel membro data.
     */
    public Object getAttributeValue(final int exampleIndex, final int attributeIndex) {
        return data[exampleIndex][attributeSet.get(attributeIndex).getIndex()];
    }

    /**
     * Restituisce l’attributo in posizione attributeIndex di attributeSet.
     * @param index Indice di colonna per un attributo.
     * @return Attributo con indice attributeIndex.
     */
    public Attribute getAttribute(final int index) {
        return attributeSet.get(index);
    }

    /**
     * Comportamento: per ogni transazione memorizzata in data, concatena i valori
     * assunti dagli attributi nella transazione separati da virgole in una stringa. Le stringhe
     * che rappresentano ogni transazione sono poi concatenate in un’unica stringa da
     * restituire in output.
     * @return Le transazioni concatenate in una sola stringa.
     */
    @Override
    public String toString() {
        String value = "";
        for (int i = 0; i < numberOfExamples; i++) {
            value += (i + 1) + ":";
            for (int j = 0; j < attributeSet.size() - 1; j++) {
                value += data[i][j] + ",";
            }
            value += data[i][attributeSet.size() - 1] + "\n";
        }
        return value;
    }

}
