package stepDefinations;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.Reusable;
import resources.TestDataBuild;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefination1 extends Reusable{
	
	ResponseSpecification resp;
	RequestSpecification res;
	Response response;
	TestDataBuild tdb = new TestDataBuild();
	static String place_id;
	
	@Given("Add Place payload with {string} {string} {string}")
	public void add_place_payload(String name, String language, String address) {
		

		res=given().spec(requestSpecification())
				.body(tdb.addPlacePayload(name,language,address));
	}
	@When("user calls {string} with {string} http request")
	public void user_calls_with_post_http_request(String resource, String method) {
		resp = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if(method.equalsIgnoreCase("post")) {
		response =res.when().post(APIResources.valueOf(resource).getResourceAPI()).
				then().spec(resp).extract().response();
		} else if(method.equalsIgnoreCase("get")) {
			response =res.when().get(APIResources.valueOf(resource).getResourceAPI()).
					then().spec(resp).extract().response();
		}
	}
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
		assertEquals(getJsonKey(response, key),value);
	}
	
	@Then("place_id should be stored in maps")
	public void place_id_should_be_stored_in_maps() {
		place_id = getJsonKey(response, "place_id");
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		
	}
	
	@Given("Delete Place payload")
	public void delete_place_payload() {
		res=given().spec(requestSpecification())
				.body(tdb.getDeletePlacePayload(place_id));
	}
}
