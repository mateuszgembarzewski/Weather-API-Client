import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.time.*;
import java.time.format.*;
import javax.json.*;
import java.util.*;

/* Lessons in this exercise:
 *    Dealing with HTTP
 *    Dealing with REST APIs
 * 	  Parsing JSON
 *    Dealing with timestamps
 *
 * Requirements:
 *    - Appropriately sized try/catch blocks
 *    - Meaningful variable names
 *    - Proper use of documentation
 */
public class WeatherAPI {

	public static final String api = "https://api.weather.gov/points/%s/forecast/hourly";
	public static final String coords = "40.726944,-73.649722";

	/** Retrieve the weather forecast for the next 12 hours
	 * @return A LinkedHashMap containining entries that represent the
	 * LocalDateTime and the temperature forecast for that time. Returns null
	 * on error.
	 */
    public LinkedHashMap<LocalDateTime, Integer> fetch() throws IOException {
        HttpURLConnection con = null;
        LinkedHashMap<LocalDateTime, Integer> out =
                new LinkedHashMap<LocalDateTime, Integer>();

        /* Open the URL */
        try {
            URL url = new URL(String.format(api, coords));
            con = (HttpURLConnection) url.openConnection();


        } catch (MalformedURLException e) {
            System.err.println(String.format("Invalid URL: %s", api));
            return null;

        } catch (IOException e) {
            System.err.println(String.format("IO Error: %s", e.toString()));
            return null;

        }

        /* Confirm that a 200 response code was returned */

        try {

            int status = con.getResponseCode();
            if (status != 200)
                System.err.println(String.format("HTTP Response code : %s ", status));

        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Get the forecast and disconnect */
        JsonArray period = null;

        try {
            JsonReader reader = Json.createReader(con.getInputStream());
            JsonObject root = reader.readObject();
            JsonObject properties = root.getJsonObject("properties");
            period = properties.getJsonArray("periods");

        } catch(IOException e) {

            e.printStackTrace();
        }

        /* Run down the JSon object and build the requested output */

        for (int i = 0; i < 12; i++) {

            JsonObject ob = period.getJsonObject(i);
            Integer temperature = ob.getInt("temperature");
            LocalDateTime timestamp = LocalDateTime.parse(ob.getString("startTime"),DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            out.put(timestamp, temperature);


        }

		return out;
	}


	public static void main(String args[]) throws IOException {
		WeatherAPI api = new WeatherAPI();

		int sum=0;
		HashMap<LocalDateTime, Integer> forecast = api.fetch();
		for (Map.Entry<LocalDateTime, Integer> entry : forecast.entrySet()) {
			System.out.println(String.format("%02d => %02d F", 
				entry.getKey().getHour(), entry.getValue())
			);
			sum += entry.getValue();
		}


		System.out.println(String.format("Average temperature over the next %d hours: %02.02f F",
			forecast.size(), sum / 12.0));
	}
}
