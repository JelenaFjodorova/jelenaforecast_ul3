package ee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import ee.api.Forecast;
import ee.api.List;
import ee.api.WeatherForecast;




public class ConsoleRunner {

    public static void main(String[] ignored) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("enter city,country code: ");
        String cityName = br.readLine();

        WeatherForecastService service = new WeatherForecastService();
        WeatherForecast request = new WeatherForecast();
        WeatherForecast currentWeather = service.getCurrentWeather(request, cityName);
        Forecast forecast = service.getForecastForThreeDays(request, cityName);

        LocalDateTime currentDate = LocalDateTime.now();
        double  firstDayMaxTemp = -100;
        double  firstDayMinTemp = 100;
        LocalDate firstDayDate = null;
        double  secondDayMaxTemp = -100 ;
        double  secondDayMinTemp = 100;
        LocalDate secondDayDate = null;
        double  thirdDayMaxTemp = -100 ;
        double  thirdDayMinTemp = 100;
        LocalDate thirdDayDate = null;
        for (List list : forecast.getList()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(list.getDtTxt(), formatter);

            if (dateTime.getDayOfMonth() == currentDate.getDayOfMonth()) {
                continue;
            }

            if (dateTime.getDayOfMonth() == currentDate.getDayOfMonth() + 1) {
                if (list.getMain().getTempMax() > firstDayMaxTemp) {
                    firstDayMaxTemp = list.getMain().getTempMax();

                }
                if (list.getMain().getTempMin() < firstDayMaxTemp) {
                    firstDayMinTemp = list.getMain().getTempMin();
                }
                firstDayDate = LocalDate.from(dateTime);

            }

            if (dateTime.getDayOfMonth() == currentDate.getDayOfMonth() + 2) {
                if (list.getMain().getTempMax() > secondDayMaxTemp) {
                    secondDayMaxTemp = list.getMain().getTempMax();

                }
                if (list.getMain().getTempMin() < secondDayMaxTemp) {
                    secondDayMinTemp = list.getMain().getTempMin();
                }
                secondDayDate = LocalDate.from(dateTime);

            }

            if (dateTime.getDayOfMonth() == currentDate.getDayOfMonth() + 3) {
                if (list.getMain().getTempMax() > thirdDayMaxTemp) {
                    thirdDayMaxTemp = list.getMain().getTempMax();

                }
                if (list.getMain().getTempMin() < thirdDayMinTemp) {
                    thirdDayMinTemp = list.getMain().getTempMin();
                }
                thirdDayDate = LocalDate.from(dateTime);

            }


        }

        System.out.println("Today is: " + currentDate);
        System.out.println("Сurrent temperature is: " + currentWeather.getMain().getTemp());
        System.out.println("Forecast for three days: ");
        System.out.printf("%s - Max: %f/Min: %f\n" , firstDayDate.toString(), firstDayMaxTemp, firstDayMinTemp);
        System.out.printf("%s - Max: %f/Min: %f\n" , secondDayDate.toString(), secondDayMaxTemp, secondDayMinTemp);
        System.out.printf("%s - Max: %f/Min: %f\n" , thirdDayDate.toString(), thirdDayMaxTemp, thirdDayMinTemp);
        Coordinates coordinates = service.getGeoCoordinates(cityName);
        System.out.printf("Latitude:%f , Longitude:%f\n" , coordinates.lat,coordinates.lon );

    }
}
