package rodriguezgonzalez.model;
import com.google.gson.JsonArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.Instant;

public class OpenWeatherMapSupplier implements WeatherSupplier{
    private String apiKey;
    private String url;
    private Location location;

    public OpenWeatherMapSupplier(String apiKey) {
        this.apiKey = apiKey;
    }

    public void entireUrl(Location location){
        this.url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLat() + "&lon=" + location.getLon() + "&appid=" + apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public Weather getWeather(Location location, Instant ts) throws IOException {
        entireUrl(location);
        String url = getUrl();
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String json = doc.body().text();

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonArray arrayObject = (JsonArray) jsonObject.get("list");
        for (int i = 0; i < arrayObject.size(); i++){
            if (i == 2){
                JsonObject jsonObject1 = (JsonObject) arrayObject.get(i);

                String popString = jsonObject1.get("pop").toString();
                int pop = Integer.parseInt(popString);
                JsonObject windJson = jsonObject1.getAsJsonObject("wind");
                String windString = windJson.get("speed").toString();
                double windSpeed = Double.parseDouble(windString);
                JsonObject mainJson = jsonObject1.getAsJsonObject("main");
                String tempString = mainJson.get("temp").toString();
                double temp = Double.parseDouble(tempString);
                String humidityString = mainJson.get("humidity").toString();
                int humidity = Integer.parseInt(humidityString);

                Weather weather = new Weather(ts, pop, windSpeed, temp, humidity, location);
                return weather;
            }
        }
        return null;
    }
}
