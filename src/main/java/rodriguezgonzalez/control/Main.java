package rodriguezgonzalez.control;

import rodriguezgonzalez.model.*;

import java.io.IOException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws IOException {
        //SQLiteWeatherStore.connect("./example.db");
        SQLiteWeatherStore sqlite = new SQLiteWeatherStore();
        Location location = new Location(28.031901645086513, -15.498588169655084, "Gran_Canaria");
        Instant ts = Instant.now();
        OpenWeatherMapSupplier openWeatherMapSupplier = new OpenWeatherMapSupplier("a30482732a67c586a31137c790f955fd");
        Weather weather = openWeatherMapSupplier.getWeather(location, ts);
        //sqlite.save(weather);
        System.out.println(weather.getClouds());
    }
}