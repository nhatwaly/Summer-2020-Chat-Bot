import org.jibble.pircbot.*;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBot extends PircBot {
//constructor 

	static final String defaultLocation = "77072"; // 
	final Pattern regex = Pattern.compile("(\\d{5})"); 
	String temperature;
	String findCity;

	public MyBot() {
		this.setName("NhatPhamChatBot"); // Bot name for Freenode
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		//For OpenWeather API
		if (message.contains("weather")) {

			String city = null;
			String state = null;
			String country = null;
			String[] words = message.split(" ");
			//When encountering the keyword "weather"
			if (words[0].equals("weather")) {
				//input with only <city> or <zipcode>
				if (words.length == 2) {
					city = words[1];
					temperature = startWebRequest(city);
				//input with <city> and <country>
				} else if (words.length == 3) {
					country = words[2];
					city = words[1];
					temperature = startWebRequest(city, country);
				//input with <city>, <country> and <state>
				} else if (words.length == 4) {
					country = words[3];
					state = words[2];
					city = words[1];
					temperature = startWebRequest(city, state, country);
				//in case of not matching anything
				} else {
					Matcher matcher = regex.matcher(message);
					if (matcher.find()) {
						city = matcher.group(1);
					} else {
						sendMessage(channel, "Unable to determine location. Please check the syntax and try again.");
					}
				}
			}
			//send message to the chat bot
			sendMessage(channel, "Hey " + sender + "! " + temperature);
		}
		//For GeoDataSource API
		if (message.contains("finder")) {
			double lon;
			double lat;
			String[] words = message.split(" ");
			if (words[0].equals("finder") && words.length == 3) {
				lon = Double.parseDouble(words[1]);
				lat = Double.parseDouble(words[2]);
				findCity = startGDSRequest(lon, lat);
				sendMessage(channel, "Hey " + sender + "! " + findCity);
			} else
				sendMessage(channel, "Unable to determine location. Please check the syntax and try again.");

		}
		//For Hello
		if (message.contains("Hello") || message.contains("hello")) {
			sendMessage(channel, "Hey " + sender + "! ");
		}
		//For syntax
		if (message.contains("Syntax") || message.contains("syntax")) {
			sendMessage(channel,
					"Welcome to Nhat Pham's chat box. I currently run 2 APIs: OpenWeather API and GeoDataSource API.");
			sendMessage(channel, "All words must be separated by a space.");
			sendMessage(channel, "For OpenWeather API, to find the weather at a location, type:");
			sendMessage(channel, "weather <city> or weather <zipcode> (for city or zipcode), for example: weather richardson or weather 75080");
			sendMessage(channel,
					"weather <city> <country code> (for city and country), for example: weather richardson us");
			sendMessage(channel,
					"weather <city> <state code> <country code> (for city, state and country - only available for the United States ),"
							+ " for example: weather richardson tx us");
			sendMessage(channel, "State code and country code are their corresponding 2 letters abbreviation. For city name with 2 words or above, insert \"%20\" instead of space, for example: los%20angeles for Los Angeles.");
			sendMessage(channel,
					"For GeoDataSource API, to find the nearest city to a specific longitude and latitude, type:");
			sendMessage(channel, "finder <longitude> <latitude>, for example: finder -95.37 29.76");

		}

	}
	//getting the API for the input with only city name or zipcode
	static String startWebRequest(String city) {
		String weatherURL = null;
		if (Character.isDigit(city.charAt(0))) {
			weatherURL = "http://api.openweathermap.org/data/2.5/weather?zip=" + city
					+ "&appid=e15553e6322876279d6cf664bb22140e";
		} else {
			weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=" + city
					+ "&appid=e15553e6322876279d6cf664bb22140e";
		}

		// Get Application ID from OpenWeather API
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(weatherURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return parseJson(result.toString());
		} catch (Exception e) {
			System.out.println(e);
			return " Unable to determine location. Please check the syntax and try again.";
		}
	}
	//getting the API for the input with city name and country code
	static String startWebRequest(String city, String countryCode) {
		String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + countryCode
				+ "&appid=e15553e6322876279d6cf664bb22140e"; // Get Application ID from OpenWeather API
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(weatherURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return parseJson(result.toString());
		} catch (Exception e) {
			System.out.println(e);
			return " Unable to determine location. Please check the syntax and try again.";
		}

	}
	//getting the API for the input with city name , state code and country code (US only)
	static String startWebRequest(String city, String stateCode, String countryCode) {
		String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + stateCode + ","
				+ countryCode + "&appid=e15553e6322876279d6cf664bb22140e"; // Get Application ID from OpenWeather API
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(weatherURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return parseJson(result.toString());
		} catch (Exception e) {
			System.out.println(e);
			return " Unable to determine location. Please check the syntax and try again.";
		}

	}
	//Parsing JSON using GSON
	static String parseJson(String json) {
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		String cityName = object.get("name").getAsString();
		JsonObject sys = object.getAsJsonObject("sys");
		String countryName = sys.get("country").getAsString();
		JsonObject coord = object.getAsJsonObject("coord");
		String longitude = coord.get("lon").getAsString();
		String latitude = coord.get("lat").getAsString();
		JsonObject main = object.getAsJsonObject("main");
		double temp = main.get("temp").getAsDouble();
		temp = (temp - 273.15) * 1.8 + 32;
		double tempMin = main.get("temp_min").getAsDouble();
		tempMin = (tempMin - 273.15) * 1.8 + 32;
		double tempMax = main.get("temp_max").getAsDouble();
		tempMax = (tempMax - 273.15) * 1.8 + 32;
		DecimalFormat df = new DecimalFormat("####0.0");
		// MyBot output
		return "The current temperature in " + cityName + ", " + countryName + " (longitude: " + longitude
				+ ", latitude: " + latitude + ")" + " is " + df.format(temp) + "˚F with a high of " + df.format(tempMax)
				+ "˚F and a low of " + df.format(tempMin) + "˚F.";
	}
	//getting API for the GeoDataSource using longitude and latitude
	static String startGDSRequest(double lng, double lat) {
		StringBuilder result = new StringBuilder();
		try {

			URL url = new URL("https://api.geodatasource.com/city?key=OSVSBPGILEJ0BYOUAHUGEBHQVZEOQA9W&format=json&lng="
					+ lng + "&lat=" + lat);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			
			String output;

			while ((output = br.readLine()) != null) {
				result.append(output);
			}
			
			conn.disconnect();
			return parseJson2(result.toString());

		} catch (Exception e) {
			System.out.println(e);
			return " Unable to determine location. The location is not on the database.";
		}

	}
	//parsing JSON using GSON
	static String parseJson2(String json) {
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		if (object.isJsonNull() == true) {
			return "";
		}
		String country = object.get("country").getAsString();
		String region = object.get("region").getAsString();
		String city = object.get("city").getAsString();
		double lon = object.get("longitude").getAsDouble();
		double lat = object.get("latitude").getAsDouble();

		DecimalFormat df = new DecimalFormat("####0.0");
		// MyBot output
		return "The nearest city/location for longitude: " + df.format(lon) + " and latitude: " + df.format(lat) + " is " + city
				+ ", " + region + ", " + country;
	}
}