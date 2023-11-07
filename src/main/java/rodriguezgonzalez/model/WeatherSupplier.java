package rodriguezgonzalez.model;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

public interface WeatherSupplier {
    ArrayList<Weather> getWeather(Location location) throws IOException;
}
