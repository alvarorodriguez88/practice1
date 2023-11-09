package rodriguezgonzalez.model;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;

public class SQLiteWeatherStore implements WeatherStore {
    public SQLiteWeatherStore() {

    }
    public void initTables(Statement statement, ArrayList<Weather> weathers) throws SQLException {
        //createDB(statement);
        createTable(statement, weathers);
    }

    private static void delete(Statement statement, String tableName, String register) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE register = ?";
        PreparedStatement preparedStatement = statement.getConnection().prepareStatement(query);
        preparedStatement.setString(1, register);
        preparedStatement.executeUpdate();
    }
    private static void createDB(Statement statement) throws SQLException {
        statement.execute("CREATE DATABASE Tiempo_Canarias;");
    }

    private static void createTable(Statement statement, ArrayList<Weather> weathers) throws SQLException {
        for (Weather weather : weathers){
            statement.execute("CREATE TABLE IF NOT EXISTS " + weather.getLocation().getIsla() + " (" +
                    "Date TEXT,\n" +
                    "temperature FLOAT NOT NULL,\n" +
                    "precipitation FLOAT NOT NULL,\n" +
                    "humidity INTEGER NOT NULL,\n" +
                    "clouds INTEGER NOT NULL,\n" +
                    "windSpeed FLOAT NOT NULL" +
                    ");");
        }
    }

    private static void dropTable(Statement statement, Weather weather) throws SQLException {
        statement.execute("DROP TABLE IF EXISTS " + weather.getLocation().getIsla() + ";\n");
    }
    private static void dropDataBase(Statement statement, String dbPath) throws SQLException {
        statement.execute("DROP DATABASE " + dbPath + ";");
    }

    public static void insert(Statement statement, ArrayList<Weather> weathers) throws SQLException {
        for (Weather weather : weathers) {
            try {
                statement.execute("INSERT INTO " + weather.getLocation().getIsla() + " (date, temperature, precipitation, humidity, clouds, windSpeed)\n" +
                        "SELECT " + weather.getTs() + ", " + weather.getTemp() + ", " + weather.getPop() + ", " + weather.getHumidity() + ", " + weather.getClouds() + ", " + weather.getWindSpeed() +
                        " WHERE NOT EXISTS (SELECT 1 FROM " + weather.getLocation().getIsla() + " WHERE date = " + weather.getTs() + ");");
                System.out.println("Se agregó un nuevo registro en " + weather.getLocation().getIsla());
            } catch (SQLException e){
                System.out.println("ERROR: " + e);
            }
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


    public Connection connect(String dbPath) {
        Connection conn = null;
        try {
            //TODO hacer archivo para poner dbPath y leerlo
            String url = "jdbc:sqlite:" + dbPath;
            conn = DriverManager.getConnection(url);
            System.out.println("Conexion");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public void save(Statement statement, ArrayList<Weather> weathers) throws SQLException {
        update(statement, weathers);
        insert(statement, weathers);
    }

    @Override
    public String get(Location location, Instant ts) {
        return null;
    }
}
