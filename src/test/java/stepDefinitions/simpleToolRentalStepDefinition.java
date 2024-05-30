package stepDefinitions;

import Pojo.Utilities;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;

import static io.restassured.RestAssured.*;

public class simpleToolRentalStepDefinition extends Utilities {
    public static String accessToken;
    Response response;
    int id;


    @When("user calls the GET_STATUS API call using GET HTTP REQUEST")
    public void userCallsTheGET_STATUSAPICallUsingGETHTTPREQUEST() throws IOException {
        response = given().log().all().spec(Request()).
                when().get("/Status").
                then().log().all().extract().response();
        System.out.println(response.asString());
    }


    @Then("user verifies the STATUS of GET_STATUS API call as {int}")
    public void userVerifiesTheSTATUSOfGET_STATUSAPICallAs(int arg0) {
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("user extracts the value of the STATUS from the GET_STATUS API Response")
    public void userExtractsTheValueOfTheSTATUSFromTheGET_STATUSAPIResponse() {
        JsonPath js = new JsonPath(response.asString());
        String statusValue = js.get("status");
        System.out.println(statusValue);
    }

    @When("user calls the GET_TOOLS API call using GET HTTP Request")
    public void userCallsTheGET_TOOLSAPICallUsingGETHTTPRequest() throws IOException {
        response = given().log().all().spec(Request()).
                when().get("/tools").
                then().log().all().extract().response();

    }

    @Then("user extracts the details from the GET_TOOLS API call")
    public void userExtractsTheDetailsFromTheGET_TOOLSAPICall() {
        JsonPath js = new JsonPath(response.asString());
        int dataLength = js.get("size()");
        System.out.println(dataLength);
        for (int i = 0; i < dataLength; i++) {
            System.out.println("THE ID IS:" + js.get("[" + i + "].id").toString());
            System.out.println("THE CATEGORY IS:" + js.get("[" + i + "].category").toString());
            System.out.println("THE NAME IS:" + js.get("[" + i + "].name").toString());
            System.out.println("THE IN_STOCK STATUS IS:" + js.get("[" + i + "].inStock").toString());
        }
    }


    @When("user calls the GET_SINGLE_TOOL API call using GET HTTP Request adding path Parameter {string} toolID")
    public void userCallsTheGET_SINGLE_TOOLAPICallUsingGETHTTPRequestAddingPathParameterToolID(String toolId) throws IOException {
        response = given().log().all().spec(Request()).pathParam("toolId", toolId).
                when().get("/tools/{toolId}").
                then().log().all().extract().response();
    }

    @Then("user extracts the details from the GET_SINGLE_TOOL API call")
    public void userExtractsTheDetailsFromTheGET_SINGLE_TOOLAPICall() {
        JsonPath js = new JsonPath(response.asString());
        System.out.println("Id:" + js.getString("id") + "\n"
                + "Category:" + js.getString("category") + "\n"
                + "Name:" + js.getString("name") + "\n"
                + "Manufacturer:" + js.getString("manufacturer") + "\n"
                + "Price:" + js.getString("price") + "\n"
                + "Current-Stock :" + js.getString("current-stock") + "\n"
                + "InStock:" + js.getString("inStock"));

        id = js.get("id");
    }


    @Then("user validates the id extracted with the toolId {string}")
    public void userValidatesTheIdExtractedWithTheToolId(String toolId) {
        Assert.assertEquals(String.valueOf(id), toolId);
    }

    @When("user calls the POST HTTP REQUEST for the AUTHENTICATION REQUEST with {string} and {string} as BODY")
    public void userCallsThePOSTHTTPREQUESTForTheAUTHENTICATIONREQUESTWithAndAsBODY(String clientName, String clientEmail) throws IOException {
        response = given().log().all().spec(Request()).body(Utilities.authBody(clientName, clientEmail)).
                when().post("/api-clients").
                then().log().all().extract().response();
    }

    @Then("user extracts the ACCESS TOKEN generated after POST HTTP REQUEST")
    public void userExtractsTheACCESSTOKENGeneratedAfterPOSTHTTPREQUEST() {
        JsonPath js = new JsonPath(response.asString());
        accessToken = js.get("accessToken");
        System.out.println(accessToken);
    }

    @When("user calls GET_ORDERS HTTP REQUEST with ACCESS Token for AUTHORIZATION")
    public void userCallsGET_ORDERSHTTPREQUESTWithACCESSTokenForAUTHORIZATION() throws IOException {
        response = given().log().all().spec(Request()).headers("Authorization",
                        "Bearer " + accessToken).
                when().get("/orders").
                then().log().all().extract().response();
    }

    @Then("user verifies the STATUS of GET_Orders API call as {int}")
    public void userVerifiesTheSTATUSOfGET_OrdersAPICallAs(int arg0) {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @When("user calls CREATE_NEW_ORDER HTTP POST REQUEST with accessToken And Body having {string} and {string}")
    public void userCallsCREATE_NEW_ORDERHTTPPOSTREQUESTWithAccessTokenAndBodyHavingAnd(String toolId, String customerName) throws IOException {
        response = given().log().all().spec(Request()).
                headers("Authorization", "Bearer " + accessToken).
                body(Utilities.createNewOrderBody(toolId, customerName)).
                when().post("/orders").
                then().log().all().extract().response();
    }

    @Then("user verifies the STATUS of CREATE_NEW_ORDER API call as {int}")
    public void userVerifiesTheSTATUSOfCREATE_NEW_ORDERAPICallAs(int arg0) {
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Then("user extracts the order-Id")
    public void userExtractsTheOrderId() {
        JsonPath js = new JsonPath(response.asString());
        String orderId = js.get("orderId");
        System.out.println(orderId);
    }
}
