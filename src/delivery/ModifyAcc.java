/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package delivery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import org.apache.commons.codec.digest.DigestUtils;

public class ModifyAcc extends JFrame implements ActionListener 
{
  
    // Components of the Form
    private final Container c;
    private final JLabel title;
    private final JLabel name;
    private final JTextField tname;
    private final JLabel pwd;
    private final JTextField tpwd;
    private final JLabel email;
    private final JTextField temail;
    private final JLabel bank;
    private final JTextField tbank;
    private final JLabel role;
    private final JRadioButton customer;
    private final JRadioButton kitchen;
    private final JRadioButton deliverer;
    private final JLabel hour_s;
    private final JTextField thour_s;
    private final JLabel hour_e;
    private final JTextField thour_e;
    private final ButtonGroup gengp;
    private final JLabel add;
    private final JTextArea tadd;
    private final JButton sub;
    private final JTextArea tout;
    private final JLabel res;
    private final JTextArea resadd;
    public int uid;
    public String passwd;
    public String hexpass;
    public String eml;
    public String addr;
    public String bnk;
    public String rol;
    public String shour = "NULL";
    public String ehour = "NULL";
 
    // constructor, to initialize the components
    // with default values.
    public ModifyAcc(String userName)
    {
        try
                {
                Connection connection;
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase","root", "");
                Statement statement;
                statement = connection.createStatement();
                ResultSet resultSet;
                var sql = "SELECT * FROM USERS WHERE UNAME = '" + userName + "'";
                resultSet = statement.executeQuery(sql);
                resultSet.next();
                uid = resultSet.getInt(1);
                passwd = "(unchanged)";
                hexpass = resultSet.getString(3);
                eml = resultSet.getString(4);
                addr = resultSet.getString(5);
                rol = resultSet.getString(6);
                bnk = resultSet.getString(7);
                if(rol.equals("DELIVERER"))
                {
                    sql = "SELECT UID,START_HOUR,END_HOUR FROM DELIVERERS WHERE UID = "+ uid;
                    resultSet = statement.executeQuery(sql);
                    resultSet.next();
                    shour = resultSet.getString(2);
                    ehour = resultSet.getString(3);
                }
                if(rol.equals("KITCHEN"))
                {
                    sql = "SELECT UID,START_HOUR,END_HOUR FROM KITCHENS WHERE UID = "+ uid;
                    resultSet = statement.executeQuery(sql);
                    resultSet.next();
                    shour = resultSet.getString(2);
                    ehour = resultSet.getString(3);
                }
                resultSet.close();
                statement.close();
                connection.close();
                }
        catch (SQLException | ClassNotFoundException ex) 
                {
                Logger.getLogger(CreateLoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        
        setTitle("Modify Account");
        setBounds(300, 90, 900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
 
        c = getContentPane();
        c.setLayout(null);
 
        title = new JLabel("Modify Account");
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);
 
        name = new JLabel("Username");
        name.setSize(100, 20);
        name.setLocation(100, 100);
        c.add(name);
 
        tname = new JTextField();
        tname.setSize(190, 20);
        tname.setLocation(200, 100);
        tname.setText(userName);
        c.add(tname);
 
        pwd = new JLabel("Password");
        pwd.setSize(100, 20);
        pwd.setLocation(100, 150);
        c.add(pwd);
 
        tpwd = new JTextField();
        tpwd.setSize(150, 20);
        tpwd.setLocation(200, 150);
        tpwd.setText(passwd);
        c.add(tpwd);
        
        email = new JLabel("Email");
        email.setSize(100, 20);
        email.setLocation(100, 200);
        c.add(email);
 
        temail = new JTextField();
        temail.setSize(150, 20);
        temail.setLocation(200, 200);
        temail.setText(eml);
        c.add(temail);
 
        role = new JLabel("Role");
        role.setSize(100, 20);
        role.setLocation(100, 250);
        c.add(role);
 
        customer = new JRadioButton("Customer");
        customer.setSize(90, 20);
        customer.setLocation(200, 250);
        c.add(customer);
 
        kitchen = new JRadioButton("Kitchen");
        kitchen.setSize(80, 20);
        kitchen.setLocation(290, 250);
        c.add(kitchen);
        
        deliverer = new JRadioButton("Deliverer");
        deliverer.setSize(80, 20);
        deliverer.setLocation(375, 250);
        c.add(deliverer);
 
        gengp = new ButtonGroup();
        gengp.add(customer);
        gengp.add(kitchen);
        gengp.add(deliverer);
        
        switch (rol) 
            {
                case "CUSTOMER" -> customer.setSelected(true);
                case "KITCHEN" -> kitchen.setSelected(true);
                case "DELIVERER" -> deliverer.setSelected(true);
            }   
        
        hour_s = new JLabel("Starting Hour (HH:MM:SS)");
        hour_s.setSize(150, 20);
        hour_s.setLocation(100, 290);
        c.add(hour_s);
 
        thour_s = new JTextField();
        thour_s.setSize(150, 20);
        thour_s.setLocation(300, 290);
        thour_s.setText(shour);
        c.add(thour_s);
        
        hour_e = new JLabel("Ending Hour (HH:MM:SS)");
        hour_e.setSize(150, 20);
        hour_e.setLocation(100, 320);
        c.add(hour_e);
 
        thour_e = new JTextField();
        thour_e.setSize(150, 20);
        thour_e.setLocation(300, 320);
        thour_e.setText(ehour);
        c.add(thour_e);
        
        add = new JLabel("Address");
        add.setSize(100, 20);
        add.setLocation(100, 365);
        c.add(add);
 
        tadd = new JTextArea();
        tadd.setSize(200, 75);
        tadd.setLocation(200, 365);
        tadd.setLineWrap(true);
        tadd.setText(addr);
        c.add(tadd);
 
        bank = new JLabel("Credit Card");
        bank.setSize(100, 20);
        bank.setLocation(100, 450);
        c.add(bank);
 
        tbank = new JTextField();
        tbank.setSize(150, 20);
        tbank.setLocation(200, 450);
        tbank.setText(bnk);
        c.add(tbank);
 
        sub = new JButton("Submit");
        sub.setSize(100, 20);
        sub.setLocation(150, 550);
        sub.addActionListener(this);
        c.add(sub);
 
        tout = new JTextArea();
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        c.add(tout);
 
        res = new JLabel("");
        res.setSize(500, 25);
        res.setLocation(200, 600);
        c.add(res);
 
        resadd = new JTextArea();
        resadd.setSize(200, 75);
        resadd.setLocation(580, 250);
        resadd.setLineWrap(true);
        c.add(resadd);
 
        setVisible(true);
        
    }
 
    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String userName;
        if (e.getSource() == sub) 
        {
                try
                {
                Connection connection;
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase","root", "");
                if(tpwd.getText().equals("(unchanged)")) //if the password actually was changed.
                {
                    passwd = hexpass;
                }
                else
                {
                    passwd = DigestUtils.sha256Hex(tpwd.getText()); //null passwords should not be allowed.
                }
                userName = tname.getText();
                eml = temail.getText();
                addr = tadd.getText();
                if (customer.isSelected())
                    rol = "CUSTOMER";
                else if(kitchen.isSelected())
                     rol = "KITCHEN";
                else
                     rol = "DELIVERER";
                bnk = tbank.getText();
                PreparedStatement stmt;
                stmt = connection.prepareStatement("UPDATE USERS SET UNAME=?, PWD=?, EMAIL=?, ADDR=?, ROLE=?, BANK_TOKEN=?"
                            + " WHERE UID=?");
                stmt.setString(1, userName);
                stmt.setString(2, passwd);
                stmt.setString(3, eml);
                stmt.setString(4, addr);
                stmt.setString(5, rol);
                stmt.setString(6, bnk);
                stmt.setInt(7, uid);
                System.out.println(stmt);
                stmt.executeUpdate();
                if(kitchen.isSelected())
                {
                stmt = connection.prepareStatement("UPDATE KITCHENS SET START_HOUR=?,END_HOUR=?"
                        + " WHERE UID=?");
                stmt.setString(1, thour_s.getText());
                stmt.setString(2, thour_e.getText());
                stmt.setInt(3, uid);
                System.out.println(stmt);
                stmt.executeUpdate();
                }
                if(deliverer.isSelected())
                {
                stmt = connection.prepareStatement("UPDATE DELIVERERS SET START_HOUR=?,END_HOUR=?"
                        + "WHERE UID=?");
                stmt.setString(1, thour_s.getText());
                stmt.setString(2, thour_e.getText());
                stmt.setInt(3, uid);
                System.out.println(stmt);
                stmt.executeUpdate();
                }
                String data1;
                String data
                    = "Username : "
                      + tname.getText() + "\n"
                      + "Password : "
                      + tpwd.getText() + "\n"
                      + "Email address :"
                      + temail.getText() + "\n";
                if (customer.isSelected())
                    data1 = """
                            Role: Customer
                            """;
                else if(kitchen.isSelected())
                    data1 = """
                            Role: Kitchen
                            """;
                else
                    data1 = """
                            Role: Deliverer
                            """;
                String data3 = "Address : " + tadd.getText();
                tout.setText(data + data1 + data3);
                tout.setEditable(false);
                res.setText("Succesful registration");
                connection.close();
                stmt.close();
                }
                catch (SQLException | ClassNotFoundException ex) 
                {
                Logger.getLogger(CreateLoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }

         }     

     }
 }   
