package rodriguezgonzalez.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

public interface WeatherStore {
    void save(Weather weather) throws RuntimeException;

    String get(Location location, Instant ts);
}
