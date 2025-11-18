import db.DBConnection;
import ui.LoginFrame;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        // DB Test
        Connection con = DBConnection.getConnection();
        if (con != null)
            System.out.println("Database Connected Successfully!");
        else
            System.out.println("Database Connection Failed!");

        new LoginFrame().setVisible(true);
    }
}
