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
    public void RegisterPositiveCase() {
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
    public void RegisterPositiveCaseWithParamInMap() throws IllegalStateException{
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        body.put("password", "pistol");
        ValidatableResponse response = (ValidatableResponse) RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200);
    }

    @Test
    public void NoPasswordCase() throws IllegalStateException{
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
    }

    @Test
    public void EmptyPasswordCase() throws IllegalStateException{
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
    }
    @Test
    public void NoPasswordCaseWithResponse() throws IllegalStateException{
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
        for (String expectedKey: expectedKeys) {
            response.then().assertThat().body("$", hasKey(expectedKey));
        }
    }
    @Test
    public void ValidDataWrongApiResult(){
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
