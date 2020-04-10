import java.sql.*;

/**
 * First, we create a connection throw Database singleton class
 * db.getCon() -> we get the db Connection from Database in order to use it for ArtistController and AlbumController
 * We insert 2 queries in order to use fintByName and findByArtist
 * We close the connection
 */
public class JDBC {
    public static void main (String[] args) {

        Database db = Database.getInstance();
        db.createTables();
        Connection con = db.getCon();
        ArtistController art = new ArtistController(con);
        art.create("Marius", "Timisoara");
        art.findByName("Marius");
        art.findByName("Ionut");

        AlbumController alb = new AlbumController(con);
        alb.create("Smiley", 10, 2005);
        alb.findByArtist(10);
        alb.findByArtist(2);
        db.closeConnection();
    }
}
