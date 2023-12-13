import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.util.Collections.reverseOrder;

public class BookStorePage {
    private SelenideElement loginButton = $(By.id("login"));

    private SelenideElement username = $(By.id("userName-value"));

    private SelenideElement logoutButton = $(By.id("submit"));

    private SelenideElement bookStoreButton = $(By.xpath("//span[@class='text'][text()='Book Store']"));

    private ElementsCollection bookFeatures = $$("div.rt-resizable-header-content");

    private List<By> booksList = List.of(
            By.cssSelector("a[href=\"/books?book=9781449325862\"]"),
            By.cssSelector("a[href=\"/books?book=9781449331818\"]"),
            By.cssSelector("a[href=\"/books?book=9781449337711\"]"),
            By.cssSelector("a[href=\"/books?book=9781449365035\"]"),
            By.cssSelector("a[href=\"/books?book=9781491904244\"]"),
            By.cssSelector("a[href=\"/books?book=9781491950296\"]"),
            By.cssSelector("a[href=\"/books?book=9781593275846\"]"),
            By.cssSelector("a[href=\"/books?book=9781593277574\"]")
    );

    private ElementsCollection bookItems = $$("a[href^='/books?book=']");

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



    public void clickLoginButton() {
        loginButton.shouldBe(visible).click();
    }

    public SelenideElement getUsername() {
        return username;
    }

    public void clickLogoutButton() {
        logoutButton.shouldBe(visible).click();
    }

    public void clickBookStoreButton() {
        bookStoreButton.shouldBe(visible).click();
    }

    public ElementsCollection getBookFeatures() {
        return bookFeatures;
    }

    public void clickRandomBook() {
        int randomIndex = new Random().nextInt(booksList.size());
        By randomLocator = booksList.get(randomIndex);
        SelenideElement element = Selenide.$(randomLocator);
        element.scrollTo().click();
    }

    public ElementsCollection getBookItems() {
        return bookItems;
    }

    public void searchForBook(String searchTerm) {
        searchBook.val(searchTerm).pressEnter();
    }

    public void setQuantityRowsOnThePages5() {
        quantityRowsOnThePages5.scrollTo().shouldBe(visible).click();
    }

    public void setQuantityRowsOnThePages10() {
        quantityRowsOnThePages10.scrollTo().shouldBe().click();
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

    public int getActualQuantityPages() {
        return Integer.parseInt(actualQuantityPages.scrollTo().shouldBe(visible).getText());
    }

    public void clickNextNaviButton() {
        nextButton.scrollTo().shouldBe(visible).click();
    }

    public void clickPreviousNaviButton() {
        previousButton.scrollTo().shouldBe(visible).click();
    }

    public int getBooksQuantity() {
        return booksQuantity.size();
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