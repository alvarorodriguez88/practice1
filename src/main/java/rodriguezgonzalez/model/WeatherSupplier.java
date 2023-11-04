package rodriguezgonzalez.model;

import java.io.IOException;
import java.time.Instant;

public interface WeatherSupplier {
    Weather getWeather(Location location, Instant ts) throws IOException;
}
