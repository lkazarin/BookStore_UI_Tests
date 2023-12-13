public class LoginHelper {
    private final BookStorePage bookStorePage;
    private final LoginPage loginPage;

    public LoginHelper(BookStorePage bookStorePage, LoginPage loginPage) {
        this.bookStorePage = bookStorePage;
        this.loginPage = loginPage;
    }

    public void login(String username, String password) {
        bookStorePage.clickLoginButton();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }
}
