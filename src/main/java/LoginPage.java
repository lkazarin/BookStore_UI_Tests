import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private SelenideElement usernameField = $(By.id("userName"));

    private SelenideElement passwordField = $(By.id("password"));

    private SelenideElement loginButton = $(By.id("login"));

    private SelenideElement invalidCredentialsMessage = $(By.id("name"));



    @Step("Enter username of login page")
    public void enterUsername(String usernameValue) {
        usernameField.shouldBe(visible).setValue(usernameValue);
    }

    @Step("Enter user password")
    public void enterPassword(String passwordValue) {
        passwordField.shouldBe(visible).setValue(passwordValue);
    }

    public void clickLoginButton() {
        loginButton.shouldBe(visible).click();
    }

    public SelenideElement getInvalidCredentialsMessage() {
        return invalidCredentialsMessage;
    }

    public SelenideElement getUsernameField() {
        return usernameField;
    }

    public SelenideElement getPasswordField() {
        return passwordField;
    }
}
