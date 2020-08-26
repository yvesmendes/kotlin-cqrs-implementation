
package localhost.accounts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de balanceRequest complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o contexFAdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="balanceRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="accountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="balanceRequestType" type="{http://localhost/accounts/}balanceRequestType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "balanceRequest", propOrder = {
    "amount",
    "accountNumber",
    "customerId",
    "balanceRequestType"
})
public class BalanceRequest {

    protected double amount;
    @XmlElement(required = true)
    protected String accountNumber;
    protected long customerId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected BalanceRequestType balanceRequestType;

    /**
     * ObtxE9m o valor da propriedade amount.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Define o valor da propriedade amount.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * ObtxE9m o valor da propriedade accountNumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Define o valor da propriedade accountNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * ObtxE9m o valor da propriedade customerId.
     * 
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Define o valor da propriedade customerId.
     * 
     */
    public void setCustomerId(long value) {
        this.customerId = value;
    }

    /**
     * ObtxE9m o valor da propriedade balanceRequestType.
     * 
     * @return
     *     possible object is
     *     {@link BalanceRequestType }
     *     
     */
    public BalanceRequestType getBalanceRequestType() {
        return balanceRequestType;
    }

    /**
     * Define o valor da propriedade balanceRequestType.
     * 
     * @param value
     *     allowed object is
     *     {@link BalanceRequestType }
     *     
     */
    public void setBalanceRequestType(BalanceRequestType value) {
        this.balanceRequestType = value;
    }

}
