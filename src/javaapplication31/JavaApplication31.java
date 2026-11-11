// http://localhost/phpmyadmin
package javaapplication31;
 
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 *
 * @author pvc
 */
public class JavaApplication31 {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 
        // user: "root"
        // pass: ""
 
        String url = "jdbc:mysql://localhost:3306/QuanLySinhVien";
        String username = "root";
        String password = "";
 
        System.out.println("Connecting database...");
 
        try  {
            Connection connection = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
 
            Statement statment = connection.createStatement();
            ResultSet resultSet = statment.executeQuery("SELECT * FROM diem");
 
            while (resultSet.next()) {
                System.out.print(resultSet.getString(1) + " - ");
                System.out.println(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
}
 