import com.codeborne.selenide.CollectionCondition;
import org.junit.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ProfileTest extends BaseTest {
    LoginHelper loginHelper = new LoginHelper(bookStorePage, loginPage);


    @Test
    public void deleteBookFromCollection() {
        deleteAllBookFromUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.clickBookStoreButton();
        bookStorePage.clickRandomBook();
        bookDetailsPage.clickAddToCollectionBook();
        String alertText = switchTo().alert().getText();
        assertEquals("Book added to your collection.", alertText);
        bookDetailsPage.acceptAlert();
        profilePage.clickProfileButton();
        profilePage.clickDeleteBook();
        assertEquals(0, profilePage.getBooksQuantityInProfile());
    }

    @Test
    public void searchBooksByKeyword() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        String keyword = "design";
        String excludedLocator = "a[href='https://demoqa.com']";
        profilePage.clickProfileButton();
        profilePage.searchForBook(keyword);
        profilePage.getBookItems().shouldHave(CollectionCondition.sizeGreaterThan(0))
                .forEach(book -> book.shouldHave(text(keyword)));
        profilePage.getBookItems().shouldHave(CollectionCondition.sizeGreaterThan(0))
                .filterBy(text(keyword))
                .excludeWith(text(excludedLocator)).shouldHave(CollectionCondition.sizeGreaterThan(0));
        }

    @Test
    public void searchBooksByInvalidKeyword() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        String invalidKeyword = fakePassword;
        String excludedLocator = "a[href='https://demoqa.com']";
        profilePage.clickProfileButton();
        profilePage.searchForBook(invalidKeyword);
        profilePage.getBookItems().shouldHave(CollectionCondition.size(0));
        profilePage.getBookItems().filterBy(text(invalidKeyword))
                .excludeWith(text(excludedLocator)).shouldHave(CollectionCondition.size(0));
    }

    @Test
    public void quantityPages() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages5();
        assertEquals(2, profilePage.getActualQuantityPages());
    }

    @Test
    public void setPageNumber2() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        int actualQuantityBooksOnThePage1 = 5;
        int actualQuantityBooksOnThePage2 = 3;
        int actualQuantityPagesInTheCatalog = 2;
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages5();
        assertEquals(actualQuantityPagesInTheCatalog, profilePage.getActualQuantityPages());
        assertEquals(actualQuantityBooksOnThePage1, profilePage.getBooksQuantity());
        profilePage.clearValueInPageNumberField();
        profilePage.setPageNumberField(2);
        assertEquals(actualQuantityBooksOnThePage2, profilePage.getBooksQuantity());
    }

    @Test
    public void naviButtons() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        int actualQuantityBooksOnThePage1 = 5;
        int actualQuantityBooksOnThePage2 = 3;
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages5();
        profilePage.clickNextNaviButton();
        assertEquals("2", profilePage.getPageNumberFieldValue());
        assertEquals(actualQuantityBooksOnThePage2, profilePage.getBooksQuantity());
        bookStorePage.clickPreviousNaviButton();
        assertEquals("1", profilePage.getPageNumberFieldValue());
        assertEquals(actualQuantityBooksOnThePage1, profilePage.getBooksQuantity());
    }

    @Test
    public void quantityRowsOnThePages5() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        int actualQuantityBooksOnThePage1 = 5;
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages5();
        assertEquals(actualQuantityBooksOnThePage1, profilePage.getBooksQuantity());
    }

    @Test
    public void quantityRowsOnThePages10() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        int actualQuantityBooksInTheCatalog = 8;
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages10();
        assertEquals(actualQuantityBooksInTheCatalog, profilePage.getBooksQuantity());
    }

    @Test
    public void titleSortByAtoZ() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages10();
        profilePage.setTitleSort();
        assertTrue(profilePage.checkTitleSortAtoZ());
    }

    @Test
    public void titleSortByZtoA() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages10();
        profilePage.setTitleSort();
        profilePage.setTitleSort();
        assertTrue(profilePage.checkTitleSortZtoA());
    }

    @Test
    public void authorSortByAtoZ() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages10();
        profilePage.setAuthorSort();
        assertTrue(profilePage.checkAuthorSortAtoZ());
    }

    @Test
    public void authorSortByZtoA() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages10();
        profilePage.setAuthorSort();
        profilePage.setAuthorSort();
        assertTrue(profilePage.checkAuthorSortZtoA());
    }

    @Test
    public void publisherSortByAtoZ() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages10();
        profilePage.setPublisherSort();
        assertTrue(profilePage.checkPublisherSortAtoZ());
    }

    @Test
    public void publisherSortByZtoA() {
        deleteAllBookFromUserProfile();
        addAllBooksInUserProfile();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.setQuantityRowsOnThePages10();
        profilePage.setPublisherSort();
        profilePage.setPublisherSort();
        assertTrue(profilePage.checkPublisherSortZtoA());
    }
}
