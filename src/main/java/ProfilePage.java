import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.util.Collections.reverseOrder;

public class ProfilePage {
    private SelenideElement headerProfile = $(By.id("userName-value"));
    private SelenideElement profileButton = $(By.xpath("//span[@class='text'][text()='Profile']"));
    private ElementsCollection buttons = $$("button.btn.btn-primary");
    private SelenideElement deleteBook = $("path[d^='M864']");
    private SelenideElement deleteConfirmMessageAboutAllBooks = $("button#closeSmallModal-ok.btn.btn-primary\n");
    private ElementsCollection booksQuantityInProfile = $$("img[alt=\"image\"]\n");
    private ElementsCollection bookItems = $$("a[href*=\"/profile?book=\"]\n");
    private SelenideElement searchBook = $("#searchBox");
    private SelenideElement quantityRowsOnThePages5 = $("select[aria-label='rows per page'] option[value='5']");
    private SelenideElement quantityRowsOnThePages10 = $("select[aria-label='rows per page'] option[value='10']");
    private SelenideElement pageNumberField = $("input[aria-label='jump to page']");
    private SelenideElement actualQuantityPages = $("span.-totalPages\n");
    private SelenideElement nextButton = $("div.-next button.-btn\n");
    private SelenideElement previousButton = $("div.-previous button.-btn\n");
    private ElementsCollection booksQuantity = $$("img[alt=\"image\"]\n");
    private SelenideElement titleSort = $(By.xpath("//div[@class='rt-resizable-header-content' " +
            "and text()='Title']"));
    private ElementsCollection bookTitles = $$(".mr-2");
    private SelenideElement authorSort = $(By.xpath("//div[@class='rt-resizable-header-content' " +
            "and text()='Author']"));
    private ElementsCollection authorNames = $$(".rt-td[role='gridcell']:nth-child(3)");
    private SelenideElement publisherSort = $(By.xpath("//div[@class='rt-resizable-header-content' " +
            "and text()='Publisher']"));
    private ElementsCollection publisherTitles = $$(".rt-td[role='gridcell']:nth-child(4)");
    private SelenideElement genreSort = $(By.xpath("//div[@class='rt-resizable-header-content' " +
            "and text()='Genre']"));
    private ElementsCollection genreTitles = $$(".rt-td[role='gridcell']:nth-child(5)");
    private SelenideElement publicationDateSort = $(By.xpath("//div[@class='rt-resizable-header-content' " +
            "and text()='Publication Date']"));
    private ElementsCollection publicationDates = $$(".rt-td[role='gridcell']:nth-child(6)");


    public void clickProfileButton() {
        profileButton.shouldBe(visible).click();
    }

    public SelenideElement getDeleteAllBooksButton() {
        return buttons.stream()
                .filter(element -> element.getText().equals("Delete All Books"))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Button 'Delete All Books' not found"));
    }
    public void clickAllDeleteButtons() {
        sleep(1000);
        getDeleteAllBooksButton().shouldHave(visible).click();
        deleteConfirmMessageAboutAllBooks.shouldBe(visible).click();
        switchTo().alert().accept();
    }

    public void clickDeleteBook() {
        deleteBook.shouldBe(visible).click();
        deleteConfirmMessageAboutAllBooks.shouldBe(visible).click();
        switchTo().alert().accept();
    }

    public int getBooksQuantityInProfile() {
        return booksQuantityInProfile.size();
    }

    public void searchForBook(String searchTerm) {
        searchBook.val(searchTerm).pressEnter();
    }

    public ElementsCollection getBookItems() {
        return bookItems;
    }

    public void setQuantityRowsOnThePages5() {
        quantityRowsOnThePages5.scrollTo().shouldBe(visible).click();
    }

    public void setQuantityRowsOnThePages10() {

        quantityRowsOnThePages10.scrollTo().shouldBe().click();
    }
    public int getActualQuantityPages() {
        return Integer.parseInt(actualQuantityPages.scrollTo().shouldBe(visible).getText());
    }

    public int getBooksQuantity() {
        return booksQuantity.size();
    }

    public void clearValueInPageNumberField() {
        pageNumberField.scrollTo().shouldBe(visible).setValue("");
    }

    public int setPageNumberField(int setNumberValue) {
        String stringValue = String.valueOf(setNumberValue);
        pageNumberField.shouldBe(visible).setValue(stringValue).pressEnter();
        return setNumberValue;
    }

    public String getPageNumberFieldValue() {
        return pageNumberField.scrollTo().shouldBe(visible).getAttribute("value");
    }

    public void clickNextNaviButton() {
        nextButton.scrollTo().shouldBe(visible).click();
    }

    public void setTitleSort() {
        titleSort.shouldBe(visible).click();
    }

    public boolean checkTitleSortAtoZ() {
        List<String> actualTitles = new ArrayList<>();
        for (SelenideElement title : bookTitles) {
            actualTitles.add(title.getText());
        }
        List<String> sortedExpectedTitles = new ArrayList<>(actualTitles);
        Collections.sort(sortedExpectedTitles);
        Assert.assertEquals(sortedExpectedTitles, actualTitles);
        return true;
    }

    public boolean checkTitleSortZtoA() {
        List<String> actualTitles = new ArrayList<>();
        for (SelenideElement title : bookTitles) {
            actualTitles.add(title.getText());
        }
        List<String> sortedExpectedTitles = new ArrayList<>(actualTitles);
        Collections.sort(sortedExpectedTitles, reverseOrder());
        Assert.assertEquals(sortedExpectedTitles, actualTitles);
        return true;
    }

    public void setAuthorSort() {
        authorSort.shouldBe(visible).click();
    }

    public boolean checkAuthorSortAtoZ() {
        List<String> actualAuthors = new ArrayList<>();
        for (SelenideElement author : authorNames) {
            actualAuthors.add(author.getText());
        }
        actualAuthors.removeIf(author -> author.isEmpty() || author.equals(" "));
        List<String> sortedExpectedAuthors = new ArrayList<>(actualAuthors);
        Collections.sort(sortedExpectedAuthors);
        Assert.assertEquals(sortedExpectedAuthors, actualAuthors);
        return true;
    }

    public boolean checkAuthorSortZtoA() {
        List<String> actualAuthors = new ArrayList<>();
        for (SelenideElement author : authorNames) {
            actualAuthors.add(author.getText());
        }
        actualAuthors.removeIf(author -> author.isEmpty() || author.equals(" "));
        List<String> sortedExpectedAuthors = new ArrayList<>(actualAuthors);
        Collections.sort(sortedExpectedAuthors, reverseOrder());
        Assert.assertEquals(sortedExpectedAuthors, actualAuthors);
        return true;
    }

    public void setPublisherSort() {

        publisherSort.shouldBe(visible).click();
    }

    public boolean checkPublisherSortAtoZ() {
        List<String> actualPublishers = new ArrayList<>();
        for (SelenideElement publisher : publisherTitles) {
            actualPublishers.add(publisher.getText());
        }
        actualPublishers.removeIf(publisher -> publisher.isEmpty() || publisher.equals(" "));
        List<String> sortedExpectedPublisher = new ArrayList<>(actualPublishers);
        Collections.sort(sortedExpectedPublisher);
        Assert.assertEquals(sortedExpectedPublisher, actualPublishers);
        return true;
    }
    public boolean checkPublisherSortZtoA() {
        List<String> actualPublishers = new ArrayList<>();
        for (SelenideElement publisher : publisherTitles) {
            actualPublishers.add(publisher.getText());
        }
        actualPublishers.removeIf(publisher -> publisher.isEmpty() || publisher.equals(" "));
        List<String> sortedExpectedPublisher = new ArrayList<>(actualPublishers);
        Collections.sort(sortedExpectedPublisher, reverseOrder());
        Assert.assertEquals(sortedExpectedPublisher, actualPublishers);
        return true;
    }
}
