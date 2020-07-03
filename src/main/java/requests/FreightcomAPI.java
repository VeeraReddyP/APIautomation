package requests;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

public class FreightcomAPI {

    private static String baseUrl = "https://test.freightcom.com/rpc2";

    public static Response createQuote(String xmlData){
        RequestSpecification requestSpec = given().
                spec(RequestHelper.getOneRequestSpec()).
                body(xmlData);
        ResponseSpecification respSpec = RequestHelper.getOneStaticResourceResponseSpec();
        return RequestHelper.call(Method.POST,baseUrl,requestSpec,respSpec,true,true);
    }
}
