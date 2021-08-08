package stepDefinations;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeSceanrio() {

		StepDefination1 stepDef = new StepDefination1();
		if(StepDefination1.place_id == null) {
			stepDef.add_place_payload("AAHouse", "French-IN", "Gurugram, Haryana");
			stepDef.user_calls_with_post_http_request("AddPlaceAPI", "POST");
			stepDef.place_id_should_be_stored_in_maps();
		}
	}
}
