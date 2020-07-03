package steps;

import assertions.ResponseAssert;
import data.TestData;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import requests.FreightcomAPI;
import utils.XmlUtils;

public class QuoteSteps {


    public ResponseAssert responseAssert = new ResponseAssert();

    @Step
    public void createValidQuoteDetails() {
        String fileName = TestData.xmlPath(TestData.validQuoteFile);
        String quoteXmlData = XmlUtils.getXmlFileAsString(fileName);
        Response response = FreightcomAPI.createQuote(quoteXmlData);
        responseAssert.checkThat(response).isSuccessful();
    }

    @Step
    public void createValidQuoteDetails_CA2US() {
        String fileName = TestData.xmlPath(TestData.validQuoteFile_CA2US);
        String quoteXmlData = XmlUtils.getXmlFileAsString(fileName);
        Response response = FreightcomAPI.createQuote(quoteXmlData);
        responseAssert.checkThat(response).isSuccessful();
    }

    @Step
    public Response inValidQuoteDetails_emptyZipCode() {
        String fileName = TestData.xmlPath(TestData.invalidZipFile);
        String quoteXmlData = XmlUtils.getXmlFileAsString(fileName);
        Response response = FreightcomAPI.createQuote(quoteXmlData);
        responseAssert.checkThat(response).isSuccessful();
        return response;
    }

    @Step
    public Response inValidQuoteDetails_emptyAddressId() {
        String fileName = TestData.xmlPath(TestData.invalidAddressFile);
        String quoteXmlData = XmlUtils.getXmlFileAsString(fileName);
        Response response = FreightcomAPI.createQuote(quoteXmlData);
        responseAssert.checkThat(response).isSuccessful();
        return response;
    }

    @Step
    public int getStatusCode(Response response) {
        return response.getStatusCode();
    }

    @Step
    public String getResponseBody(Response response) {
        return response.getBody().asString();
    }

}
