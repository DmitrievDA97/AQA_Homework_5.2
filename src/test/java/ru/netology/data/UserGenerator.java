package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Value;

import static io.restassured.RestAssured.given;

public class UserGenerator {
    private UserGenerator() {
    }
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
   public static void newUser(UserInfo user) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new Gson().toJson(user)) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }
    public static Faker faker = new Faker();

    public static String generateLogin() {
        return faker.name().username();
    }

    public static String generatePassword() {
        return faker.internet().password(6,15);
    }
    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String status) {
            UserInfo user = new UserInfo(generateLogin(), generatePassword(), status);
            return user;

        }
     }

     @AllArgsConstructor
    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }
}

