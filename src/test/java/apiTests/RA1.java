package apiTests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RA1 {
	ObjectMapper mapper = new ObjectMapper();
	Gson gson = new Gson();
	String str = "";

	@BeforeClass
	void bc1() {
		RestAssured.baseURI = "http://localhost:3000";
	}

	/**
	 * GET METHOD
	 */
	@Test(enabled = true)
	void testcase001() {

		given()
		.pathParam("variable", "bikes")
		.queryParam("brand", "Royal Enfield").header("Accept", "application/xml").accept(ContentType.XML).when()
				.get("/{variable}").then().log().body();

	}

	/**
	 * POST METHOD
	 * 
	 * @return
	 */
	@Test
	void testcase002() {
		JSONObject map = new JSONObject();
		map.put("brand", "Jemi Cycle");
		map.put("segment", "Bike");

		RequestSpecification rs = given().contentType("application/json").body(map.toString());

		Response res = rs.when().post("/bikes");

		System.out.println(res.asPrettyString());
		str = res.jsonPath().getString("id");
		System.out.println(str);
	}

	/**
	 * DELETE METHOD
	 * 
	 * @return
	 */
	@Test
	void testcase003() {
		int statusCode = given().when().delete("/bikes/" + str).then().extract().statusCode();
		System.out.println(statusCode);
	}

	/**
	 * Checking request is within timeframe
	 * 
	 * @return
	 */
	@Test
	void checkTimeout() {
		given().when().get("/bikes").then().time(lessThan(5000L)); // milliseconds
	}

	/**
	 * Checking request is within timeframe
	 * 
	 * @return
	 */
	@Test
	void extractResponse() {
		Response res = given().when().get("/bikes");
		// res.then().log().body();
		Collection<Object> i = res.jsonPath().get("[0].values()");
		int ii = res.jsonPath().getInt("[0].keySet().size()");
		XmlPath xmlPath = null;
		String category = xmlPath.getString("bookstore.book[0].@category");
		System.out.println(i + " " + ii);
	}

	@Test
	void postUsingJSON() {
		JSONObject json = new JSONObject();
		json.put("brand", "Ragul Cycle");
		json.put("engine_cc", 24.7);
		json.put("fuel", "Feet");

		given().contentType("application/json").body(json.toString()).when().post("/bikes").then().log().all();
	}

	@Test
	void postUsingPOJO() {
		MyCar ptd = new MyCar();
		ptd.setModel("Arul");
		ptd.setTopSpeed("123km/hr");

		MyCar carObj = new MyCar("Bhuvana", "30");
		
		given().contentType("application/json").body(carObj).when().post("/bikes").then().log().all();
	}

	@Test
	void postUsingJSONFile() throws FileNotFoundException {
		JSONObject obj = new JSONObject(new JSONTokener(new FileReader("TestData/sample.json")));

		given().contentType("application/json").body(obj.toString()).when().post("/bikes").then().log().all();
	}

	@Test
	void cookies() throws FileNotFoundException {
		Map<String, String> map = given().baseUri("https://www.youtube.com/").when().get().then().extract().cookies();
		System.out.println(map);
	}

	@Test
	void headers() throws FileNotFoundException {
		Map<String, String> cook = new HashMap<>();
		cook.put("sessionId", "ABC123");
		cook.put("user", "Ragul");
		cook.put("role", "admin");

		Object obj = given().cookies(cook).when().get("/bikes").then().extract().jsonPath().get("brand");
		List<String> list = (ArrayList) obj;
		list.removeIf(Objects::isNull);
		System.out.println(list);
	}

	@Test
	/*
	 * Input - int[] a = { 1, 2, 3 }; // int[] b = { 4, 5, 6 }; // Output: {1+6,
	 * 2+5,3+4};
	 */

	void testcase10() {
		int[] a = { 1, 2, 3 };
		int[] b = { 4, 5, 6 };

		List<Integer> list1 = Arrays.stream(a).boxed().toList();
		List<Integer> list2 = new ArrayList<>(Arrays.stream(b).boxed().toList());
		List<String> list3 = new ArrayList<>();
		Collections.reverse(list2);

		for (int i = 0; i <= 2; i++) {
			list3.add(list1.get(i) + " + " + list2.get(i));
		}
		System.out.println(list3);
	}

	/**
	 * To demonstrate serialization in Jackson & GSON libraries
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	void testcase11() throws JsonProcessingException {
		MyCar car = new MyCar("Bugatti", "90kms");
		ObjectMapper mapper = new ObjectMapper();
		Gson gson = new Gson();

		System.out.println(mapper.writeValueAsString(car));
		System.out.println(gson.toJson(car));
	}

	/**
	 * To demonstrate deserialization in Jackson & GSON libraries
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	void testcase12() throws JsonProcessingException {
		String str = "{\"model\" : \"Tata\", \"topSpeed\" : \"100kms\"}";

		MyCar car1 = mapper.readValue(str, MyCar.class);
		MyCar car2 = gson.fromJson(str, MyCar.class);
		System.out.println(car1.getModel());
		System.out.println(car2.getTopSpeed());
	}

	/**
	 * Complex data deserialization
	 */
	@Test
	void testcase13() {
		MyCar car1 = new MyCar("Bugatti", "90kms");
		MyCar car2 = new MyCar("Tata", "150kms");

		List<MyCar> cars = new ArrayList<>();
		cars.add(car1);
		cars.add(car2);
		gson = new GsonBuilder().setPrettyPrinting().create();

		 System.out.println(gson.toJson(cars));

	}

	/**
	 * Deserialization with arrays
	 */
	@Test
	void testcase14() {
		Type type = new TypeToken<List<MyCar>>() {
		}.getType();
		String input = "[{\"model\":  \"Ragul\"," + "    \"topSpeed\": \"80\"" + "  }," + "  {"
				+ "    \"model\":  \"Stark\"," + "    \"topSpeed\": \"70\"" + "  }" + "]";
		List<MyCar> carss = gson.fromJson(input, type);
		System.out.println(carss);
	}

	/**
	 * File upload
	 */
	@Test
	void testcase15() {
		File file1 = new File("E:\\Learner\\API\\API Master notes1.txt");
		File file2 = new File("E:\\Learner\\API\\API Master notes2.txt");
		File[] myFiles = { file1, file2 };
		given().baseUri("https://postman-echo.com/").multiPart("files", myFiles, "text/plain").when().post("/post")
				.then().statusCode(200);
	}

	/**
	 * File download
	 */
	@Test
	void testcase16() {
		given().when().get("https://example.com/download/report.pdf").then().statusCode(200);
	}

	/**
	 * To validate JSON Schema
	 */
	@Test
	void testcase17() {
		given().when().get("/bikes").then().statusCode(200).contentType(ContentType.JSON)
				.body(matchesJsonSchemaInClasspath("user-schema.json"));
	}
}
