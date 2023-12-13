import org.junit.Test;
import static com.codeborne.selenide.Condition.*;


public class LoginTest extends BaseTest {
    LoginHelper loginHelper = new LoginHelper(bookStorePage, loginPage);

    @Test
    public void successLogin() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldBe(visible).shouldHave(text(usernameValue));
    }

    @Test
    public void invalidPassword() {
        loginHelper.login(usernameValue, fakePassword);
        loginPage.getInvalidCredentialsMessage().shouldBe(visible).shouldHave(text("Invalid username or password!"));
    }

    @Test
    public void invalidUserName() {
        loginHelper.login(fakeLogin, passwordValue);
        loginPage.getInvalidCredentialsMessage().shouldBe(visible).shouldHave(text("Invalid username or password!"));
    }

    @Test
    public void emptyUsername() {
        loginHelper.login("", passwordValue);
        loginPage.getUsernameField().shouldHave(cssClass("is-invalid"));
    }

    @Test
    public void emptyUsernameAndPassword() {
        loginHelper.login("", "");
        loginPage.getUsernameField().shouldHave(cssClass("is-invalid"));
        loginPage.getPasswordField().shouldHave(cssClass("is-invalid"));
    }

    @Test
    public void emptyPassword() {
        loginHelper.login(usernameValue, "");
        loginPage.getPasswordField().shouldHave(cssClass("is-invalid"));
    }

    @Test
    public void logout() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldBe(visible).shouldHave(text(usernameValue));
        bookStorePage.clickLogoutButton();
        loginPage.getUsernameField().shouldBe(visible); //input field
    }

    @Test
    public void lockout() {
        for (int i = 0; i < 10; i++) {
            String fakePassword = faker.internet().password();
            loginHelper.login(usernameValue, fakePassword);
            System.out.println("Generated Password: " + fakePassword);
            loginPage.getInvalidCredentialsMessage().shouldBe(visible).shouldHave(text("Invalid username or password!"));
        }
        loginPage.getInvalidCredentialsMessage().shouldBe(visible).shouldHave(text("User is locked out!"));
    }
}
