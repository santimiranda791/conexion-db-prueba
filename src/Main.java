//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRes = null;
        try {
            myConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project",
                    "root",
                    ""
            );
            System.out.println("Genial, nos conectamos");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo salio mal :(");
        }
    }
}