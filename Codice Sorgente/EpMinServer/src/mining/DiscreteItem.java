package mining;
import data.DiscreteAttribute;

/**
 * La classe concreta DiscreteItem che estende la classe Item e
 * rappresenta la coppia <Attributo discreto - valore discreto>.
 */
public class DiscreteItem extends Item {

    /**
     * Invoca il costruttore della classe madre per avvalora i
     * membri.
     * @param attribute Attributo discreto per avvalorare il membro.
     * @param value Valore discreto per avvalorare il membro.
     */
    DiscreteItem(final DiscreteAttribute attribute, final Object value) {
        super(attribute, value);
    }

    /**
     * Verifica che il membro value sia uguale (nello stato)
     * all’argomento passato come parametro della funzione.
     * @param value Oggetto cui effettuare i controlli.
     * @return True se i due valori sono uguali, else altrimenti.
     */
    @Override
    boolean checkItemCondition(final Object value) {
        return this.getValue().equals(value);
    }
}
