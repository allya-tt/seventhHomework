import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

public class GetListUsersTest {

    @Test
    public void getListUsers() {
        Response response = RestAssured
                .get("https://reqres.in/api/users?page=2")
                        .andReturn();

        response.prettyPrint();
    }
    @Test
    public void getListUsersWithParam() {
        ValidatableResponse response = RestAssured
                .given()
                .queryParam("page", 2)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200);
    }
}
