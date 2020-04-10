import java.sql.*;

/**
 * Database is a singleton class, it can have only one object
 * In the constructor we create the connection and MusicAlbums database
 * We create 2 tables using MySql
 *
 */
public class Database {
    private static Database one_instance = null;
    public static final String DB_URL = "jdbc:mysql://localhost:3306";
    static String user = "root";
    static String pass = "root";
    public static Connection con = null;

    private Database() {
        try{
            System.out.println("Connecting to a database..");
            con = DriverManager.getConnection(DB_URL, user, pass);
            System.out.println("Creating database..");

            Statement stmt = con.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS MusicAlbums";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully!");

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getCon() {
        return con;
    }

    public static Database getInstance() {
        if(one_instance == null) {
            one_instance = new Database();
        }
        return one_instance;
    }

    public void createTables() {
        try{
            Statement stmt = con.createStatement();
            String sql = "CREATE USER IF NOT EXISTS dba IDENTIFIED BY 'sql'";
            stmt.executeUpdate(sql);
            System.out.println("\nUSERS:");
            sql = "select user from mysql.user";
            ResultSet r = stmt.executeQuery(sql);
            while(r.next()) {
                String user = r.getString("user");
                System.out.println(user);
            }
            System.out.println("");
            sql = "USE MusicAlbums";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS artists(id integer not null auto_increment," +
                    "name varchar(100) not null," +
                    "country varchar(100)," +
                    "primary key (id));";
            stmt.executeUpdate(sql);
            System.out.println("Table artists created successfully!");

            sql = "CREATE TABLE IF NOT EXISTS albums(id integer not null auto_increment," +
                    "name varchar(100) not null," +
                    "artist_id integer not null references artists on delete restrict," +
                    "release_year integer," +
                    "primary key (id));";
            stmt.executeUpdate(sql);
            System.out.println("Table albums created successfully!");

        } catch(SQLException e) {
            System.out.println(e);
        } catch(NullPointerException e) {
            System.out.println(e);
        }
    }

    public static void closeConnection() {
        try{
            Statement stmt = con.createStatement();
            if(stmt != null)
                stmt.close();
            System.out.println("Connection closed!");
        } catch(SQLException err) {
            System.out.println("ERROR!");
        }
    }

}
