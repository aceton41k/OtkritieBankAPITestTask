package org.saushkinpn;

import com.jayway.restassured.response.ValidatableResponseOptions;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertNotNull;

public class Main extends BaseTest {

    private static String apiUsers="/api/users";

    @Test
    public static void getUsers() {
        given()
                .param("page", 2)
        .when()
        .get(apiUsers)
                .then()
                .statusCode(200);
        Users users = get(apiUsers).as(Users.class);
        assertNotNull(users.getTotal());
        assertNotNull(users.getPage());
        assertNotNull(users.getPerPage());
        assertNotNull(users.getTotalPages());

        List<Datum> datum = users.getData();
        for(int i=0; i<datum.size(); i++) {
            assertNotNull(datum.get(i).getAvatar());
            assertNotNull(datum.get(i).getEmail());
            assertNotNull(datum.get(i).getFirstName());
            assertNotNull(datum.get(i).getId());
            assertNotNull(datum.get(i).getLastName());
            assertNotNull(datum.get(i).getId());
        }
    }

    @Test
    public void addUser() {
        int createdId;
        ValidatableResponseOptions vro = given()
                .body("{\"email\":\"william.blake@reqres.in\"," +
                        "\"first_name\":\"William\"," +
                        "\"last_name\":\"Blake\"," +
                        "\"avatar\":\"https://66.media.tumblr.com/625b9293a10c0c23cb6b44458001f3e7/tumblr_pshiyqI8Xg1ypgdufo1_540.jpg\"}")
                .when()
                .post(apiUsers)
                .then()
                .statusCode(200)

//               Нельзя получить добавленного пользователя каким-либо образом, поэтому просто проверяю содержимое ответа.
                .body("id", notNullValue(), "createdAt", notNullValue());
                createdId = vro.extract().jsonPath().getInt("id"); // id добавленного пользователя

    }
}


