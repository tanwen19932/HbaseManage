
package edu.buaa.nlp.ws.client.dataProvider;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.buaa.nlp.ws.client.dataProvider package. 
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

    private final static QName _FetchData_QNAME = new QName("http://impl.service.nlp.buaa.edu/", "fetchData");
    private final static QName _FetchDataResponse_QNAME = new QName("http://impl.service.nlp.buaa.edu/", "fetchDataResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.buaa.nlp.ws.client.dataProvider
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FetchDataResponse }
     * 
     */
    public FetchDataResponse createFetchDataResponse() {
        return new FetchDataResponse();
    }

    /**
     * Create an instance of {@link FetchData }
     * 
     */
    public FetchData createFetchData() {
        return new FetchData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FetchData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.nlp.buaa.edu/", name = "fetchData")
    public JAXBElement<FetchData> createFetchData(FetchData value) {
        return new JAXBElement<FetchData>(_FetchData_QNAME, FetchData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FetchDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.nlp.buaa.edu/", name = "fetchDataResponse")
    public JAXBElement<FetchDataResponse> createFetchDataResponse(FetchDataResponse value) {
        return new JAXBElement<FetchDataResponse>(_FetchDataResponse_QNAME, FetchDataResponse.class, null, value);
    }

}
