package requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.ResponseSpecification;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.lang3.StringUtils;

public class RequestHelper {

    private static String baseUrl ="https://test.freightcom.com/rpc2";
    private final static int MAX_RETRIES = 3;
    private final static int RETRY_DELAY_SEC = 3;

    public static RequestSpecification getOneRequestSpec() {
        return getOneRequestSpec("text/xml");
    }

    public static RequestSpecification getOneRequestSpec(String contentType) {

        // The following configuration change configures the Jackson2 JSON parser to accept unknown properties
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                (aClass, s) -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return objectMapper;
                }
        ));

        RequestSpecBuilder specBuilder = new RequestSpecBuilder()
                .setConfig(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setBaseUri(baseUrl)
                .log(LogDetail.URI)
                .log(LogDetail.BODY)
                .log(LogDetail.HEADERS)
                .log(LogDetail.METHOD);

        if(contentType != null) {
            specBuilder.setContentType(contentType);
        }

        return specBuilder.build();
    }

    public static Response call(Method httpMethod, String path, RequestSpecification requestSpecification, ResponseSpecification responseSpecification, boolean retry, boolean withLog) {
        int retryCount = 0;
        Response response = null;



        while (retryCount <= MAX_RETRIES) {
            if (withLog) {
                response = callWithLog(httpMethod, path, requestSpecification, responseSpecification);
            } else {
                response = callWithoutLog(httpMethod, path, requestSpecification, responseSpecification);
            }

            if (response.getBody().asString().contains("\"retriable\":true") && retry) {
                retryCount++;
            } else {
                // No retries
                break;
            }
        }

        return response;
    }

    protected static Response callWithLog(Method httpMethod, String path, RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        return invoke(httpMethod, path, requestSpecification).then().log().all().spec(responseSpecification).
                extract().response();
    }

    protected static Response callWithoutLog(Method httpMethod, String path, RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        return invoke(httpMethod, path, requestSpecification).then().log().ifError().spec(responseSpecification).
                extract().response();
    }

    private static Response invoke(Method httpMethod, String path, RequestSpecification requestSpecification) {

        switch (httpMethod) {
            case POST:
                return SerenityRest.given(requestSpecification).when().post(path);
            case PUT:
                return SerenityRest.given(requestSpecification).when().put(path);
            case GET:
                return SerenityRest.given(requestSpecification).when().get(path);
            case DELETE:
                return SerenityRest.given(requestSpecification).when().delete(path);
            case PATCH:
                return SerenityRest.given(requestSpecification).when().patch(path);
            default:
                throw new RuntimeException("Invalid / not supported request type: " + httpMethod);
        }
    }

    public static ResponseSpecification getOneStaticResourceResponseSpec() {
        return getOneResponseSpec("text/xml");
    }

    public static ResponseSpecification getOneResponseSpec(String contentType) {
        return new ResponseSpecBuilder()
                .expectContentType(contentType)
                .build();
    }
}
