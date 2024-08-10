package delivery;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import org.apache.commons.codec.digest.DigestUtils;
class RegisterScreen extends JFrame implements ActionListener 
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
    private final JCheckBox term;
    private final JButton sub;
    private final JButton reset;
    private final JTextArea tout;
    private final JLabel res;
    private final JTextArea resadd;
 

 
    // constructor, to initialize the components
    // with default values.
    public RegisterScreen()
    {
        setTitle("Registration Form");
        setBounds(300, 90, 900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
 
        c = getContentPane();
        c.setLayout(null);
 
        title = new JLabel("Registration Form");
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
        c.add(tname);
 
        pwd = new JLabel("Password");
        pwd.setSize(100, 20);
        pwd.setLocation(100, 150);
        c.add(pwd);
 
        tpwd = new JTextField();
        tpwd.setSize(150, 20);
        tpwd.setLocation(200, 150);
        c.add(tpwd);
        
        email = new JLabel("Email");
        email.setSize(100, 20);
        email.setLocation(100, 200);
        c.add(email);
 
        temail = new JTextField();
        temail.setSize(150, 20);
        temail.setLocation(200, 200);
        c.add(temail);
 
        role = new JLabel("Role");
        role.setSize(100, 20);
        role.setLocation(100, 250);
        c.add(role);
 
        customer = new JRadioButton("Customer");
        customer.setSelected(true);
        customer.setSize(90, 20);
        customer.setLocation(200, 250);
        c.add(customer);
 
        kitchen = new JRadioButton("Kitchen");
        kitchen.setSelected(false);
        kitchen.setSize(80, 20);
        kitchen.setLocation(290, 250);
        c.add(kitchen);
        
        deliverer = new JRadioButton("Deliverer");
        deliverer.setSelected(false);
        deliverer.setSize(80, 20);
        deliverer.setLocation(375, 250);
        c.add(deliverer);
 
        gengp = new ButtonGroup();
        gengp.add(customer);
        gengp.add(kitchen);
        gengp.add(deliverer);
        
        hour_s = new JLabel("Starting Hour (HH:MM:SS)");
        hour_s.setSize(150, 20);
        hour_s.setLocation(100, 290);
        c.add(hour_s);
 
        thour_s = new JTextField();
        thour_s.setSize(150, 20);
        thour_s.setLocation(300, 290);
        c.add(thour_s);
        
        hour_e = new JLabel("Ending Hour (HH:MM:SS)");
        hour_e.setSize(150, 20);
        hour_e.setLocation(100, 320);
        c.add(hour_e);
 
        thour_e = new JTextField();
        thour_e.setSize(150, 20);
        thour_e.setLocation(300, 320);
        c.add(thour_e);
        
        add = new JLabel("Address");
        add.setSize(100, 20);
        add.setLocation(100, 365);
        c.add(add);
 
        tadd = new JTextArea();
        tadd.setSize(200, 75);
        tadd.setLocation(200, 365);
        tadd.setLineWrap(true);
        c.add(tadd);
 
        bank = new JLabel("Credit Card");
        bank.setSize(100, 20);
        bank.setLocation(100, 450);
        c.add(bank);
 
        tbank = new JTextField();
        tbank.setSize(150, 20);
        tbank.setLocation(200, 450);
        c.add(tbank);
        
        term = new JCheckBox("Accept Terms And Conditions.");
        term.setSize(250, 20);
        term.setLocation(150, 500);
        c.add(term);
 
        sub = new JButton("Submit");
        sub.setSize(100, 20);
        sub.setLocation(150, 550);
        sub.addActionListener(this);
        c.add(sub);
 
        reset = new JButton("Reset");
        reset.setSize(100, 20);
        reset.setLocation(270, 550);
        reset.addActionListener(this);
        c.add(reset);
 
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
 
        customer.addActionListener((ActionEvent e) -> 
        {
            hour_s.setVisible(false);
            thour_s.setVisible(false);
            hour_e.setVisible(false);
            thour_e.setVisible(false);
        });
        
        kitchen.addActionListener((ActionEvent e) -> 
        {
            hour_s.setVisible(true);
            thour_s.setVisible(true);
            hour_e.setVisible(true);
            thour_e.setVisible(true);
        });
        deliverer.addActionListener((ActionEvent e) -> 
        {
            hour_s.setVisible(true);
            thour_s.setVisible(true);
            hour_e.setVisible(true);
            thour_e.setVisible(true);
        });
        
        setVisible(true);
        hour_s.setVisible(false);
        thour_s.setVisible(false);
        hour_e.setVisible(false);
        thour_e.setVisible(false);


    }
 
    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == sub) 
        {
            if (term.isSelected()) 
            {
                try
                {
                Connection connection;
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase","root", "");
                Statement statement;
                statement = connection.createStatement();
                ResultSet resultSet;
                String uname = tname.getText();
                String passwd = DigestUtils.sha256Hex(tpwd.getText());
                String eml = temail.getText();
                String addr = tadd.getText();
                String rol;
                if (customer.isSelected())
                    rol = "CUSTOMER";
                else if(kitchen.isSelected())
                     rol = "KITCHEN";
                else
                     rol = "DELIVERER";
                String bnk = tbank.getText();
                var sql = "SELECT * FROM USERS WHERE UNAME = '" + uname + "'";
                resultSet = statement.executeQuery(sql);
                if(!resultSet.next())
                {
                PreparedStatement stmt;
                    stmt = connection.prepareStatement("INSERT INTO USERS (UNAME, PWD, EMAIL, ADDR, ROLE, BANK_TOKEN) "
                            + "VALUES (?, ?, ?, ?, ?, ? )");
                stmt.setString(1, uname);
                stmt.setString(2, passwd);
                stmt.setString(3, eml);
                stmt.setString(4, addr);
                stmt.setString(5, rol);
                stmt.setString(6, bnk);
                stmt.executeUpdate();
                sql = "SELECT UID FROM USERS ORDER BY UID DESC LIMIT 1";
                resultSet = statement.executeQuery(sql);
                resultSet.next();
                int uid = resultSet.getInt(1);
                if(kitchen.isSelected())
                {
                stmt = connection.prepareStatement("INSERT INTO KITCHENS (UID,START_HOUR,END_HOUR) "
                        + "VALUES (?,?,?)");
                stmt.setInt(1, uid);
                stmt.setString(2, thour_s.getText());
                stmt.setString(3, thour_e.getText());
                stmt.executeUpdate();
                stmt.close();
                }
                if(deliverer.isSelected())
                {
                stmt = connection.prepareStatement("INSERT INTO DELIVERERS (UID,START_HOUR,END_HOUR) "
                        + "VALUES (?,?,?)");
                stmt.setInt(1, uid);
                stmt.setString(2, thour_s.getText());
                stmt.setString(3, thour_e.getText());
                stmt.executeUpdate();
                stmt.close();
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
                }
                else
                {
                res.setText("Username already exists.");   
                }
                resultSet.close();
                statement.close();
                connection.close();
                
                }
                catch (SQLException | ClassNotFoundException ex) 
                {
                Logger.getLogger(CreateLoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            else {
                tout.setText("");
                resadd.setText("");
                res.setText("Please accept the"
                            + " terms & conditions..");
            }
        }
 
        else if (e.getSource() == reset) 
        {
            String def = "";
            tname.setText(def);
            tadd.setText(def);
            tpwd.setText(def);
            res.setText(def);
            tout.setText(def);
            term.setSelected(false);
            resadd.setText(def);
        }
    }
}
 