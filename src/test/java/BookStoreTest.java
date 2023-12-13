import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BookStoreTest extends BaseTest {
    LoginHelper loginHelper = new LoginHelper(bookStorePage, loginPage);


    @Test
    public void bookFeatures() {
        List<String> featureValues = Arrays.asList("Title", "Author", "Genre", "Publication date", "Cover", "Image", "Status");
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldBe(visible).shouldHave(text(usernameValue));
        bookStorePage.getBookFeatures().shouldHave(CollectionCondition.exactTextsCaseSensitiveInAnyOrder(featureValues));
    }

    @Test
    public void bookDetails() {
        List<String> expectedValues = Arrays.asList("ISBN : ", "Title : ", "Sub Title : ", "Author : ", "Publisher : ",
                "Total Pages : ", "Description : ", "Website : ", "Genre : ", "Publication Date : ", "Cover : ",
                "Format : ", "Status : ");
        List<SelenideElement> allFields = bookDetailsPage.getAllDetailsFields();
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldBe(visible).shouldHave(text(usernameValue));
        bookStorePage.clickRandomBook();
        bookDetailsPage.bookDetailsIsOpen();
        for (int i = 0; i < allFields.size(); i++) {
            SelenideElement field = allFields.get(i);
            String expectedValue = expectedValues.get(i);
            field.shouldBe(visible).shouldHave(text(expectedValue)); // поштучно делаем
        }
    }

    @Test
    public void addBookToCollection() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        profilePage.clickProfileButton();
        profilePage.clickAllDeleteButtons();
        bookStorePage.clickBookStoreButton();
        bookStorePage.clickRandomBook();
        bookDetailsPage.clickAddToCollectionBook();
        String alertText = switchTo().alert().getText();
        assertEquals("Book added to your collection.", alertText);
        bookDetailsPage.acceptAlert();
    }

    @Test
    public void searchBooksByKeyword() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
            String keyword = "design";
            String excludedLocator = "a[href='https://demoqa.com']";
            bookStorePage.searchForBook(keyword);
            bookStorePage.getBookItems().shouldHave(CollectionCondition.sizeGreaterThan(0))
                    .forEach(book -> book.shouldHave(text(keyword)));
            bookStorePage.getBookItems().shouldHave(CollectionCondition.sizeGreaterThan(0))
                    .filterBy(text(keyword))
                    .excludeWith(text(excludedLocator)).shouldHave(CollectionCondition.sizeGreaterThan(0));
        }

    @Test
    public void searchBooksByInvalidKeyword() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        String invalidKeyword = fakePassword;
        String excludedLocator = "a[href='https://demoqa.com']";
        bookStorePage.searchForBook(invalidKeyword);
        bookStorePage.getBookItems().shouldHave(CollectionCondition.size(0));
        bookStorePage.getBookItems().filterBy(text(invalidKeyword))
                .excludeWith(text(excludedLocator)).shouldHave(CollectionCondition.size(0));
    }

    @Test
    public void quantityPages() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages5();
        assertEquals(2, bookStorePage.getActualQuantityPages());
    }

    @Test
    public void setPageNumber2() {
        int actualQuantityBooksOnThePage1 = 5;
        int actualQuantityBooksOnThePage2 = 3;
        int actualQuantityPagesInTheCatalog = 2;
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages5();
        assertEquals(actualQuantityPagesInTheCatalog, bookStorePage.getActualQuantityPages());
        assertEquals(actualQuantityBooksOnThePage1, bookStorePage.getBooksQuantity());
        bookStorePage.clearValueInPageNumberField();
        bookStorePage.setPageNumberField(2);
        assertEquals(actualQuantityBooksOnThePage2, bookStorePage.getBooksQuantity());
    }

    @Test
    public void naviButtons() {
        int actualQuantityBooksOnThePage1 = 5;
        int actualQuantityBooksOnThePage2 = 3;
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages5();
        bookStorePage.clickNextNaviButton();
        assertEquals("2", bookStorePage.getPageNumberFieldValue());
        assertEquals(actualQuantityBooksOnThePage2, bookStorePage.getBooksQuantity());
        bookStorePage.clickPreviousNaviButton();
        assertEquals("1", bookStorePage.getPageNumberFieldValue());
        assertEquals(actualQuantityBooksOnThePage1, bookStorePage.getBooksQuantity());
    }

    @Test
    public void quantityRowsOnThePages5() {
        int actualQuantityBooksOnThePage1 = 5;
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages5();
        assertEquals(actualQuantityBooksOnThePage1, bookStorePage.getBooksQuantity());
    }

    @Test
    public void quantityRowsOnThePages10() {
        int actualQuantityBooksInTheCatalog = 8;
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages10();
        assertEquals(actualQuantityBooksInTheCatalog, bookStorePage.getBooksQuantity());
    }

    @Test
    public void titleSortByAtoZ() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages10();
        bookStorePage.setTitleSort();
        assertTrue(bookStorePage.checkTitleSortAtoZ());
    }

    @Test
    public void titleSortByZtoA() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages10();
        bookStorePage.setTitleSort();
        bookStorePage.setTitleSort();
        assertTrue(bookStorePage.checkTitleSortZtoA());
    }

    @Test
    public void authorSortByAtoZ() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages10();
        bookStorePage.setAuthorSort();
        assertTrue(bookStorePage.checkAuthorSortAtoZ());
    }

    @Test
    public void authorSortByZtoA() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages10();
        bookStorePage.setAuthorSort();
        bookStorePage.setAuthorSort();
        assertTrue(bookStorePage.checkAuthorSortZtoA());
    }

    @Test
    public void publisherSortByAtoZ() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages10();
        bookStorePage.setPublisherSort();
        assertTrue(bookStorePage.checkPublisherSortAtoZ());
    }

    @Test
    public void publisherSortByZtoA() {
        loginHelper.login(usernameValue, passwordValue);
        bookStorePage.getUsername().shouldHave(visible).shouldHave(text(usernameValue));
        bookStorePage.setQuantityRowsOnThePages10();
        bookStorePage.setPublisherSort();
        bookStorePage.setPublisherSort();
        assertTrue(bookStorePage.checkPublisherSortZtoA());
    }
}
