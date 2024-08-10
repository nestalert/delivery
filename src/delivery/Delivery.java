package delivery;

import java.sql.*;
import org.apache.commons.codec.digest.DigestUtils;
import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
import java.util.logging.Level;
import java.util.logging.Logger;

class CreateLoginForm extends JFrame implements ActionListener  
{  
    //initialize button, panel, label, and text field  
    JButton b1, b2;  
    JPanel newPanel;  
    JLabel userLabel, passLabel, wrongLabel;  
    final JTextField  textField1, textField2;  
      
    //calling constructor  
    CreateLoginForm()  
    {     
          
        //create label for username   
        userLabel = new JLabel();  
        userLabel.setText("Username");      //set label value for textField1  
          
        //create text field to get username from the user  
        textField1 = new JTextField();    //set length of the text  
  
        //create label for password  
        passLabel = new JLabel();  
        passLabel.setText("Password");      //set label value for textField2  
          
        //create text field to get password from the user  
        textField2 = new JPasswordField();    //set length for the password  
        wrongLabel = new JLabel();  
        wrongLabel.setText("Incorrect username or password.");
        //create submit button  
        b1 = new JButton("Login"); //set label to button  
        b2 = new JButton("Create Account");
        //create panel to put form elements  
        newPanel = new JPanel(new GridLayout(4, 1));  
        newPanel.add(userLabel);    //set username label to panel  
        newPanel.add(textField1);   //set text field to panel  
        newPanel.add(passLabel);    //set password label to panel  
        newPanel.add(textField2);   //set text field to panel  
        newPanel.add(b1);           //set button to panel  
        newPanel.add(b2);
        newPanel.add(wrongLabel);
        wrongLabel.setVisible(false);
        //set border to panel   
        add(newPanel, BorderLayout.CENTER);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
        //perform action on button click   
        b1.addActionListener(this); 
        b2.addActionListener(this); //add action listener to button  
        setTitle("Login Or Register");         //set title to the login form  
    }  
      
    //define abstract method actionPerformed() which will be called on button click   
    @Override
    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter  
    {  
        
        try //pass action listener as a parameter
        {
            String userValue = textField1.getText();        //get user entered username from the textField1
            String passValue = textField2.getText();        //get user entered pasword from the textField2
            
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase","root", "");
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
            
            String sha256hex = DigestUtils.sha256Hex(passValue);

            if(ae.getSource()==b1)
            {
                String sql = "SELECT * from USERS WHERE UNAME = '" + userValue + "'" + "AND PWD = '" + sha256hex + "'";
                resultSet = statement.executeQuery(sql);
                //check whether the credentials are authentic or not

                if (resultSet.next()) 
                {  //if authentic, navigate user to a new page
         
                    MainMenu page = new MainMenu(userValue);
                    page.setVisible(true);
                
                    //create a welcome label and set it to the new page
                    page.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                }
                else
                {   
                   wrongLabel.setVisible(true); 
                }
            }
            else if(ae.getSource()==b2)
            {
                RegisterScreen register = new RegisterScreen();
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
}  






public class Delivery 
{
 public static void main(String arg[])
    {
        
        try  
        {  
            //create instance of the CreateLoginForm  
            CreateLoginForm form = new CreateLoginForm();  
            form.setSize(300,100);  //set size of the frame  
            form.setVisible(true);  //make form visible to the user  
        }  
        catch(Exception e)  
        {     
            //handle exception   
            JOptionPane.showMessageDialog(null, e.getMessage());  
        }  
    } // function ends
}