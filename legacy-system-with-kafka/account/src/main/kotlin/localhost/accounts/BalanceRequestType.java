
package localhost.accounts;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de balanceRequestType.
 * 
 * <p>O seguinte fragmento do esquema especifica o contexFAdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="balanceRequestType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="debit"/&gt;
 *     &lt;enumeration value="credit"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "balanceRequestType")
@XmlEnum
public enum BalanceRequestType {

    @XmlEnumValue("debit")
    DEBIT("debit"),
    @XmlEnumValue("credit")
    CREDIT("credit");
    private final String value;

    BalanceRequestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BalanceRequestType fromValue(String v) {
        for (BalanceRequestType c: BalanceRequestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
