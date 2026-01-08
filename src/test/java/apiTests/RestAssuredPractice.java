package apiTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class RestAssuredPractice {

	@Test
	void check1() {
		RestAssured.baseURI = "https://reqres.in/api/users/";
		int expID = 2;

		given()
					.pathParam("id", expID) 
		.when()
					.get("/{id}")
		.then() 
					.statusCode(200)
					.body("data.id", equalTo(expID))
					.log()
					.body();
	}
}
