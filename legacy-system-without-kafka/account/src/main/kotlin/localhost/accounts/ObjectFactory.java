
package localhost.accounts;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the localhost.accounts package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteAccountResponse_QNAME = new QName("http://localhost/accounts/", "deleteAccountResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: localhost.accounts
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAccountRequest }
     * 
     */
    public GetAccountRequest createGetAccountRequest() {
        return new GetAccountRequest();
    }

    /**
     * Create an instance of {@link GetAccountResponse }
     * 
     */
    public GetAccountResponse createGetAccountResponse() {
        return new GetAccountResponse();
    }

    /**
     * Create an instance of {@link Account }
     * 
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * Create an instance of {@link GetCustomerAccountRequest }
     * 
     */
    public GetCustomerAccountRequest createGetCustomerAccountRequest() {
        return new GetCustomerAccountRequest();
    }

    /**
     * Create an instance of {@link GetCustomerAccountResponse }
     * 
     */
    public GetCustomerAccountResponse createGetCustomerAccountResponse() {
        return new GetCustomerAccountResponse();
    }

    /**
     * Create an instance of {@link DeleteAccountRequest }
     * 
     */
    public DeleteAccountRequest createDeleteAccountRequest() {
        return new DeleteAccountRequest();
    }

    /**
     * Create an instance of {@link UpdateBalanceRequest }
     * 
     */
    public UpdateBalanceRequest createUpdateBalanceRequest() {
        return new UpdateBalanceRequest();
    }

    /**
     * Create an instance of {@link BalanceRequest }
     * 
     */
    public BalanceRequest createBalanceRequest() {
        return new BalanceRequest();
    }

    /**
     * Create an instance of {@link UpdateBalanceResponse }
     * 
     */
    public UpdateBalanceResponse createUpdateBalanceResponse() {
        return new UpdateBalanceResponse();
    }

    /**
     * Create an instance of {@link CreateAccountRequest }
     * 
     */
    public CreateAccountRequest createCreateAccountRequest() {
        return new CreateAccountRequest();
    }

    /**
     * Create an instance of {@link CreateAccountResponse }
     * 
     */
    public CreateAccountResponse createCreateAccountResponse() {
        return new CreateAccountResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     */
    @XmlElementDecl(namespace = "http://localhost/accounts/", name = "deleteAccountResponse")
    public JAXBElement<Object> createDeleteAccountResponse(Object value) {
        return new JAXBElement<Object>(_DeleteAccountResponse_QNAME, Object.class, null, value);
    }

}
