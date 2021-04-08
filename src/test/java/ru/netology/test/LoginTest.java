package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.UserGenerator.*;

public class LoginTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginActiveUser() {
       UserInfo user = Registration.generateUser("active");
       newUser(user);
       $("[data-test-id=login] input").setValue(user.getLogin());
       $("[data-test-id=password] input").setValue(user.getPassword());
       $("[data-test-id=action-login]").click();
       $(".heading").shouldHave(Condition.exactText("Личный кабинет"));
    }
    @Test
    void shouldLoginBlockedUser() {
        UserInfo user = Registration.generateUser("blocked");
        newUser(user);
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $(".notification__content").shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    @Test
    void shouldInvalidLogin() {
        UserInfo user = Registration.generateUser("active");
        newUser(user);
        $("[data-test-id=login] input").setValue(generateLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $(".notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));

    }
    @Test
    void shouldInvalidPassword() {
        UserInfo user = Registration.generateUser("active");
        newUser(user);
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(generatePassword());
        $("[data-test-id=action-login]").click();
        $(".notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));

    }

}
