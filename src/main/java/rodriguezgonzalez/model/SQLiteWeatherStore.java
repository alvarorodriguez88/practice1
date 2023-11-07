package rodriguezgonzalez.model;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;

public class SQLiteWeatherStore implements WeatherStore{
    public static void main(String[] args) {
        String dbPath = "./Tiempo_Canarias.db";
        try(Connection connection = connect(dbPath)) {
            Statement statement = connection.createStatement();
            createTable(statement);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void delete(Statement statement, String tableName, String register) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE register = ?";
        PreparedStatement preparedStatement = statement.getConnection().prepareStatement(query);
        preparedStatement.setString(1, register);
        preparedStatement.executeUpdate();
    }


    private static void createTable(Statement statement) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS La_Palma (" +
                "Date TEXT,\n" +
                "temperature FLOAT NOT NULL,\n" +
                "precipitation FLOAT NOT NULL,\n" +
                "humidity INTEGER NOT NULL,\n" +
                "clouds INTEGER NOT NULL,\n" +
                "windSpeed FLOAT NOT NULL" +
                ");");
    }

    private static void dropTable(Statement statement, Weather weather) throws SQLException {
        statement.execute("DROP TABLE IF EXISTS " + weather.getLocation().getIsla() + ";\n");
    }

    public static void insert(Statement statement, ArrayList<Weather> weathers) throws SQLException {
        for (Weather weather : weathers) {
            statement.execute("INSERT INTO " + weather.getLocation().getIsla() + " (date, temperature, precipitation, humidity, clouds, windSpeed)\n" +
                    "SELECT " + weather.getTs() + ", " + weather.getTemp() + ", " + weather.getPop() + ", " + weather.getHumidity() + ", " + weather.getClouds() + ", " + weather.getWindSpeed() +
                    " WHERE NOT EXISTS (SELECT 1 FROM " + weather.getLocation().getIsla() + " WHERE date = " + weather.getTs() + ");");
        }
    }

    private static void update(Statement statement, ArrayList<Weather> weathers) throws SQLException {
        for (Weather weather : weathers) {
            String query = "UPDATE " + weather.getLocation().getIsla() +
                    " SET temperature = " + weather.getTemp() +
                    ", precipitation = " + weather.getPop() +
                    ", humidity = " + weather.getHumidity() +
                    ", clouds = " + weather.getClouds() +
                    ", windSpeed = " + weather.getWindSpeed() +
                    " WHERE Date = '" + weather.getTs() + "';";
            statement.execute(query);
        }
    }


    public static Connection connect(String dbPath) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + dbPath;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public void save(ArrayList<Weather> weathers) throws RuntimeException {
        try(Connection connection = connect("./Tiempo_Canarias.db")) {
            Statement statement = connection.createStatement();
            update(statement, weathers);
            insert(statement, weathers);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(Location location, Instant ts) {
        return null;
    }
}
