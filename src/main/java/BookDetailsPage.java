import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BookDetailsPage {

    private SelenideElement isbnField = $(By.id("ISBN-label"));
    private SelenideElement titleField = $(By.id("title-label"));
    private SelenideElement subTitleField = $(By.id("subtitle-label"));
    private SelenideElement authorField = $(By.id("author-label"));
    private SelenideElement publisherField = $(By.id("publisher-label"));
    private SelenideElement totalPagesField = $(By.id("pages-label"));
    private SelenideElement descriptionField = $(By.id("description-label"));
    private SelenideElement websiteField = $(By.id("website-label"));
    private SelenideElement genreField = $(By.id("genre-label"));
    private SelenideElement publicationDateField = $(By.id("publication-date-label"));
    private SelenideElement coverImageField = $(By.id("cover-image-label"));
    private SelenideElement formatField = $(By.id("format-label"));
    private SelenideElement statusField = $(By.id("status-label"));

    private SelenideElement addToCollectionButton = $(By.xpath("//button[@id='addNewRecordButton' " +
            "and text()='Add To Your Collection']"));


    public void bookDetailsIsOpen() {
        isbnField.shouldHave(text("ISBN : "));
    }

    public List<SelenideElement> getAllDetailsFields() {
        return Arrays.asList(isbnField, titleField, subTitleField, authorField, publisherField, totalPagesField,
                descriptionField, websiteField, genreField, publicationDateField, coverImageField, formatField, statusField);
    }

    public void clickAddToCollectionBook() {
        addToCollectionButton.shouldBe(visible).click();
    }

    public void acceptAlert() {
        switchTo().alert().accept();
    }

}
