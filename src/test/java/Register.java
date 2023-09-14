import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Register {
    @Test
    public void registerPositiveCase() {
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"email\": \"rr03@mail.ru\",\n" +
                        "  \"password\": \"pistol\"}")
                .when()
                .post("https://reqres.in/api/register")
                .andReturn();
        response.prettyPrint();
    }

    @Test
    public void registerPositiveCaseWithParamInMap() throws IllegalStateException {
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        body.put("password", "pistol");
        ValidatableResponse response = (ValidatableResponse) RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .andReturn()
                .then()
                .statusCode(200);
        System.out.println(response.extract().asPrettyString());
    }

    @Test
    public void noPasswordCase() throws IllegalStateException {
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        ValidatableResponse response = (ValidatableResponse) RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400);
        System.out.println(response.extract().asPrettyString());
    }

    @Test
    public void emptyPasswordCase() throws IllegalStateException {
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        body.put("password", "");
        ValidatableResponse response = (ValidatableResponse) RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400);
        System.out.println(response.extract().asPrettyString());
    }

    @Test
    public void noPasswordCaseWithResponse() throws IllegalStateException {
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .andReturn();
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        System.out.println("Status code: " + statusCode);
        assertEquals(400, response.statusCode(), "Missing password");
    }

    @Test
    public void testJsonKeys() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"email\": \"rr03@mail.ru\",\n" +
                        "  \"password\": \"pistol\"}")
                .when()
                .post("https://reqres.in/api/register")
                .andReturn();
        response.prettyPrint();
        String[] expectedKeys = {"error"};
        for (String expectedKey : expectedKeys) {
            response.then().assertThat().body("$", hasKey(expectedKey));
        }
    }

    @Test
    public void validDataWrongApiResult() {
        JsonPath response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"email\": \"rr03@mail.ru\",\n" +
                        "  \"password\": \"pistol\"}")
                .when()
                .post("https://reqres.in/api/register")
                .jsonPath();
        assertEquals("Note: Only defined users succeed registration", response.get("error"));
        response.prettyPrint();
    }
}
