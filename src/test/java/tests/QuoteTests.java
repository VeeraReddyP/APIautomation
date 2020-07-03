package tests;

import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import steps.QuoteSteps;
import utils.XmlUtils;

@RunWith(SerenityRunner.class)

public class QuoteTests {

    @Steps
    QuoteSteps quoteSteps;

    @Test
    public void validateQuoteDetails() {
        quoteSteps.createValidQuoteDetails();
    }

    @Test
    public void validateQuoteDetails_CA2US() {
        quoteSteps.createValidQuoteDetails_CA2US();
    }

    @Test
    public void emptyZipCodeInQuoteDetails() {
        Response response=quoteSteps.inValidQuoteDetails_emptyZipCode();
        String errorResponse = quoteSteps.getResponseBody(response);
        Document doc=XmlUtils.convertStringToXMLDocument(errorResponse);
        doc.getDocumentElement().normalize();
        System.out.println(doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("CarrierErrorMessage");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node node = nList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                System.out.println(eElement.getAttribute("size"));
                System.out.println(eElement.getAttributes());

            }
        }
    }

    @Test
    public void emptyAddressIdInQuoteDetails() {
        Response response = quoteSteps.inValidQuoteDetails_emptyAddressId();
        String errorResponse = quoteSteps.getResponseBody(response);
        Document doc = XmlUtils.convertStringToXMLDocument(errorResponse);
        doc.getDocumentElement().normalize();
    }

}
