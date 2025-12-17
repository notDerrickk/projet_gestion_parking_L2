//Rodéric
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnexion {

    private static Connection conn = null;
    private static Statement stmt = null;

    public static Statement connexion() {
        try {
            String url = "jdbc:mysql://localhost:3306/projet_parking?useSSL=false&useUnicode=true";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "user", "user");
            System.out.println("connexion réussi");
            
            stmt = conn.createStatement();
            return stmt;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur");
            System.exit(0);
            return null;
        }
    }

    public static void closeConnexion() {
        try {
            if (stmt != null) stmt.close();
        } catch (Exception ignore) {}
        try {
            if (conn != null) conn.close();
        } catch (Exception ignore) {}
    }
}