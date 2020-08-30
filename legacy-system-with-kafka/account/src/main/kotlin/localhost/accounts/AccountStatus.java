
package localhost.accounts;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de accountStatus.
 * 
 * <p>O seguinte fragmento do esquema especifica o contexFAdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="accountStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="OPEN"/&gt;
 *     &lt;enumeration value="CLOSED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "accountStatus")
@XmlEnum
public enum AccountStatus {

    OPEN,
    CLOSED;

    public String value() {
        return name();
    }

    public static AccountStatus fromValue(String v) {
        return valueOf(v);
    }

}
