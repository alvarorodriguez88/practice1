package rodriguezgonzalez.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherController {
    private String apiKey;
    private OpenWeatherMapSupplier supplier;
    private List<Location> locations;
    public WeatherController() {

    }
    public void init() throws IOException {
        SQLiteWeatherStore sqlite = new SQLiteWeatherStore();
        List<Location> locations = new ArrayList<Location>() {{
            add(new Location(27.976897166863406, -15.581220101642044, "Gran_Canaria"));
            add(new Location(28.573841603162755, -13.976919911584199, "Fuerteventura"));
            add(new Location(29.006908264835392, -13.614044825597858, "Lanzarote"));
            add(new Location(29.25335827172253, -13.511141076072796, "La_Graciosa"));
            add(new Location(28.25803082700884, -16.598234004431564, "Tenerife"));
            add(new Location(28.115730902110283, -17.222529980788273, "La_Gomera"));
            add(new Location(27.74369055621178, -17.99302465337035, "El_Hierro"));
            add(new Location(28.741658780092063, -17.86465798737256, "La_Palma"));
        }};
        OpenWeatherMapSupplier openWeatherMapSupplier = new OpenWeatherMapSupplier("./apiKey.txt");
        openWeatherMapSupplier.findKey();
        for (Location loc : locations){
            ArrayList<Weather> weathers = openWeatherMapSupplier.getWeather(loc);
            sqlite.save(weathers);
        }
    }

}