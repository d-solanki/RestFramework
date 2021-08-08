package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Reusable {
	public static RequestSpecification spec;
	public RequestSpecification requestSpecification() {
		try {
			if(spec == null) {
				PrintStream stream = new PrintStream(new FileOutputStream("logging.txt"));
				spec = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
						.addFilter(RequestLoggingFilter.logRequestTo(stream))
						.addFilter(ResponseLoggingFilter.logResponseTo(stream))
						.setContentType(ContentType.JSON).build();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return spec;
	}

	public String getGlobalValue(String key) {
		Properties prop = new Properties();
		String value = "";
		try {		
			FileInputStream fis = new FileInputStream("src\\test\\java\\resources\\global.properties");
			prop.load(fis);
			value = prop.getProperty(key);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public String getJsonKey(Response response, String key) {
		JsonPath js = new JsonPath(response.asString());
		return js.get(key);
	}
}
