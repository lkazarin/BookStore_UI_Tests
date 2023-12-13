import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import dto.ValidAddListOfBookRequest;
import dto.ValidUserRequest;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class BaseTest {
    final String BASE_URL = "https://demoqa.com/books";
    final static String BASE_URI = "https://demoqa.com/";
    static RequestSpecification specification;
    LoginPage loginPage = new LoginPage();
    ProfilePage profilePage = new ProfilePage();
    BookStorePage bookStorePage = new BookStorePage();
    BookDetailsPage bookDetailsPage = new BookDetailsPage();
    String usernameValue = "Leo";
    String passwordValue = "mp0p7wCcYJfnSQG_3!";
    String userIdValue = "b01943a8-cf91-4028-a45c-70a894fb88a4";
    String createdToken;
    Faker faker = new Faker();
    String fakePassword = faker.internet().password();
    String fakeLogin = faker.name().username();
    protected String tokenEndpoint = "Account/v1/GenerateToken";
    protected String booksEndpoint = "BookStore/v1/Books";


    @Before
    public void setUp() {
        Configuration.browserSize = "900x1600";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open(BASE_URL);
        specification = new RequestSpecBuilder()
                .setUrlEncodingEnabled(false)
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
    }

    @After
    public void tearDown() {closeWebDriver();}

    public Response getRequest(String endPoint, Integer responseCode) {
        Response response = given()
                .spec(specification)
                .when()
                .log().all()
                .get(endPoint)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }

    public Response postRequest(String endPoint, Integer responseCode, Object body) {
        Response response = given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .post(endPoint)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }

    public void deleteRequestWithoutParamAndAssert(String endPoint, int responseCode) {
        given()
                .spec(specification)
                .when()
                .log().all()
                .delete(endPoint)
                .then().log().all()
                .extract().response();
    }

    public void addAllBooksInUserProfile() {
        // Generate token
        ValidUserRequest tokenGenerationBody = ValidUserRequest.builder()
                .userName(usernameValue)
                .password(passwordValue)
                .build();
        Response generateTokenResponse = postRequest(tokenEndpoint, 200, tokenGenerationBody);
        createdToken = generateTokenResponse.jsonPath().getString("token");

        // Get information about books in store
        Response booksResponse = getRequest(booksEndpoint, 200);
        List<ValidAddListOfBookRequest.BookItem> bookItems = createBookItemListFromResponse(booksResponse);

        // Add all books in user collection
        specification = new RequestSpecBuilder()
                .setUrlEncodingEnabled(false)
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
        given()
                .spec(specification);
        specification.header("Authorization", "Bearer " + createdToken);
        addListOfBookInUserCollection(bookItems, userIdValue, 201);
    }

    protected List<String> getIsbnListFromResponse(Response response) {
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        return jsonPath.getList("books.isbn");
    }
    protected List<ValidAddListOfBookRequest.BookItem> createBookItemListFromResponse(Response response) {
        List<String> isbnList = getIsbnListFromResponse(response);
        List<ValidAddListOfBookRequest.BookItem> bookItemList = new ArrayList<>();
        for (String isbn : isbnList) {
            ValidAddListOfBookRequest.BookItem bookItem = new ValidAddListOfBookRequest.BookItem(isbn);
            bookItemList.add(bookItem);
        }
        return bookItemList;
    }
    public void addListOfBookInUserCollection(List<ValidAddListOfBookRequest.BookItem> isbnList, String userId, int responseCode) {
        ValidAddListOfBookRequest addListOfBookRequest = ValidAddListOfBookRequest.builder()
                .userId(userId)
                .collectionOfIsbns(isbnList)
                .build();
        postRequest(booksEndpoint, responseCode, addListOfBookRequest);
    }

    public void deleteAllBookFromUserProfile() {
        // Generate token
        ValidUserRequest tokenGenerationBody = ValidUserRequest.builder()
                .userName(usernameValue)
                .password(passwordValue)
                .build();
        Response generateTokenResponse = postRequest(tokenEndpoint, 200, tokenGenerationBody);
        String createdToken = generateTokenResponse.jsonPath().getString("token");

        // Delete all books from user profile
        specification = new RequestSpecBuilder()
                .setUrlEncodingEnabled(false)
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
        given()
                .spec(specification);
        specification.header("Authorization", "Bearer " + createdToken);
        deleteRequestWithoutParamAndAssert(booksEndpoint+ "?UserId=" + userIdValue, 204);
    }

}
