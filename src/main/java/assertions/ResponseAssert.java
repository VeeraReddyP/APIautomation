package assertions;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import static org.assertj.core.api.Assertions.assertThat;


public class ResponseAssert {

     private Response actual;

    public ResponseAssert checkThat(Response actual) {
        this.actual = actual;
        return this;
    }

    @Step()
    public ResponseAssert hasStatusCode(int code) {
        isNotNull(actual);
        assertThat(actual.getStatusCode()).withFailMessage("The status code %s is not as expected %s. The service returns: %s",
                actual.getStatusCode(), code, actual.getBody().asString()).isEqualTo(code);
        return this;
    }

    @Step()
    public ResponseAssert isSuccessful() {
        return hasStatusCode(ResponseStatusCode.SUCCESSFUL.getStatusCode());
    }


    public void isNotNull(Object actual) {
        assertThat(actual).isNotNull();
    }
}
