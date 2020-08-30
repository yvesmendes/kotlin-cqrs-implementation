
package localhost.accounts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o contexFAdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="requestAccount" type="{http://localhost/accounts/}balanceRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "requestAccount"
})
@XmlRootElement(name = "updateBalanceRequest")
public class UpdateBalanceRequest {

    @XmlElement(required = true)
    protected BalanceRequest requestAccount;

    /**
     * ObtxE9m o valor da propriedade requestAccount.
     * 
     * @return
     *     possible object is
     *     {@link BalanceRequest }
     *     
     */
    public BalanceRequest getRequestAccount() {
        return requestAccount;
    }

    /**
     * Define o valor da propriedade requestAccount.
     * 
     * @param value
     *     allowed object is
     *     {@link BalanceRequest }
     *     
     */
    public void setRequestAccount(BalanceRequest value) {
        this.requestAccount = value;
    }

}
