package rodriguezgonzalez.model;

import java.sql.*;
import java.time.Instant;

public class SQLiteWeatherStore implements WeatherStore{
    /*public static void main(String[] args) {
        String dbPath = "./Tiempo_Canarias.db";
        try(Connection connection = connect(dbPath)) {
            Statement statement = connection.createStatement();
            createTable(statement);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     */
    private static void delete(Statement statement, String tableName, String register) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE register = ?";
        PreparedStatement preparedStatement = statement.getConnection().prepareStatement(query);
        preparedStatement.setString(1, register);
        preparedStatement.executeUpdate();
    }


    private static void createTable(Statement statement, Weather weather) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS Gran_Canaria (" +
                "Date FLOAT NOT NULL,\n" +
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

    private static boolean insert(Statement statement, Weather weather) throws SQLException {
        return statement.execute("INSERT INTO " + weather.getLocation().getIsla() + " (temperature, precipitation, humidity, clouds, windSpeed)\n" +
                "VALUES(" + weather.getTemp() + ", " + weather.getPop() + ", " + weather.getHumidity() + ", " + weather.getClouds() + ", " + weather.getWindSpeed() + ");");
    }

    private static void update(Statement statement) throws SQLException {
        statement.execute("UPDATE products\n" +
                "SET name = 'orbea500' \n" +
                "WHERE" + " name='orbea';");
        System.out.println("Table products updated");
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
    public void save(Weather weather) throws RuntimeException {
        String dbPath = "./Tiempo_Canarias.db";
        try(Connection connection = connect(dbPath)) {
            Statement statement = connection.createStatement();
            insert(statement, weather);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(Location location, Instant ts) {
        return null;
    }
}
