package ee;

import ee.api.Forecast;
import ee.api.List;
import ee.api.WeatherForecast;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Runner {

    public static final String input = (System.getProperty("user.dir") + "//src//ee//input.txt");
    public static final String output = (System.getProperty("user.dir") + "//src//ee//output.txt");

    public static void main(String[] args) throws Exception {

        FileReader in = new FileReader(input);
        BufferedReader br = new BufferedReader(in);
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

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            Coordinates coordinates = service.getGeoCoordinates(cityName);
            String content = (
                    ("Today is: " + currentDate) + "\r" +
                    ("Сurrent temperature is: " + currentWeather.getMain().getTemp()) + "\n" +
                    ("Forecast for three days: ") + "\r" +
                    ("The date is " + firstDayDate.toString() + " Maximum temperature is: " + firstDayMaxTemp +
                            " Minimum temperature is: " + firstDayMinTemp)  + "\n" +
                    ("The date is "  + secondDayDate.toString() + " Maximum temperature is: " + secondDayMaxTemp +
                            " Minimum temperature is: " + secondDayMinTemp) + "\n" +
                    ("The date is " + thirdDayDate.toString() + " Maximum temperature is: " + thirdDayMaxTemp +
                            " Minimum temperature is: "+ thirdDayMinTemp) + "\n" +
                    ("Latitude: , Longitude:" + coordinates.lat + coordinates.lon));

            fw = new FileWriter(output);
            bw = new BufferedWriter(fw);
            bw.write(content);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }


    }
}


