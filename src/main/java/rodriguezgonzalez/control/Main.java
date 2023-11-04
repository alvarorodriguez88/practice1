package rodriguezgonzalez.control;

import rodriguezgonzalez.model.Location;
import rodriguezgonzalez.model.OpenWeatherMapSupplier;

import java.io.IOException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws IOException {
        Location location = new Location(27.959881195014365, 15.58253996413194, "Gran Canaria");
        Instant ts = Instant.now();
        OpenWeatherMapSupplier openWeatherMapSupplier = new OpenWeatherMapSupplier("a30482732a67c586a31137c790f955fd");
        openWeatherMapSupplier.getWeather(location, ts);
    }
}