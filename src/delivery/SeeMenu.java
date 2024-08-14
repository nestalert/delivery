
package delivery;

import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;

public class SeeMenu extends JFrame implements ActionListener
{
    JPanel mainPanel;
    JSpinner quant;
    
    public SeeMenu(String restName)
    {
        try
        {
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase","root", "");
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            var sql = "SELECT COUNT(*) FROM MENU,USERS WHERE MENU.UID = USERS.UID AND USERS.UNAME = '" + restName + "'";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            int foodNum = resultSet.getInt(1);
            sql = "SELECT MENU.* FROM MENU,USERS WHERE MENU.UID = USERS.UID AND USERS.UNAME = '" + restName + "'";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            
        }
        catch (SQLException | ClassNotFoundException ex) 
        {
            Logger.getLogger(CreateLoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
