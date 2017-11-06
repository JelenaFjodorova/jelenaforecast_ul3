package ee;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.api.Forecast;
import ee.api.WeatherForecast;
import java.net.URL;

public class WeatherForecastService {

    public static URL url;

    public static final String API_KEY = "02de4f9608f50b67b16dbb2370997657";

    public static final String httpCurrent = "http://api.openweathermap.org/data/2.5/weather?q=";

    public static final String httpForecast = "http://api.openweathermap.org/data/2.5/forecast?q=";


    private static final ObjectMapper mapper = new ObjectMapper();


    public WeatherForecast getCurrentWeather(WeatherForecast request, String cityName) throws Exception {
        String url = appendApiKey(httpCurrent, cityName);
        WeatherForecast weatherForecast = mapper.readValue(new URL(url), WeatherForecast.class);
        return weatherForecast;
    }

    public Forecast getForecastForThreeDays(WeatherForecast request, String cityName) throws Exception {
        String url = appendApiKey(httpForecast, cityName);
        Forecast forecast = mapper.readValue(new URL(url), Forecast.class);
        return forecast;

    }

    public Coordinates getGeoCoordinates(String cityName) throws Exception {
        String url = appendApiKey(httpCurrent, cityName);
        WeatherForecast weatherForecast = mapper.readValue(new URL(url), WeatherForecast.class);
        Coordinates coordinates = new Coordinates();
        coordinates.lat = weatherForecast.getCoord().getLat();
        coordinates.lon = weatherForecast.getCoord().getLon();
        return coordinates;


    }

    private String appendApiKey(String url, String cityName) {
        return url + cityName + "&units=metric" + "&appid=" + API_KEY;
    }

}
