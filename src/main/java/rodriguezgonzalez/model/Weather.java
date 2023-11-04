package rodriguezgonzalez.model;

import java.time.Instant;

public class Weather {
    private Instant ts;
    private int pop;
    private double windSpeed;
    private double temp;
    private int humidity;
    private Location location;

    public Weather(Instant ts, int pop, double windSpeed, double temp, int humidity, Location location) {
        this.ts = ts;
        this.pop = pop;
        this.windSpeed = windSpeed;
        this.temp = temp;
        this.humidity = humidity;
        this.location = location;
    }

    public Instant getTs() {
        return ts;
    }

    public int getPop() {
        return pop;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public Location getLocation() {
        return location;
    }
}
