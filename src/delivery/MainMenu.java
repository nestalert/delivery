package delivery;

    //import required classes and packages  
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;   
import java.time.*;  
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

    //create MainMenu class to create a new page on which user will navigate  
    public class MainMenu extends JFrame implements ActionListener  
    {
        JPanel mainPanel,kitchenPanel;
        JButton b1,b2;
        String userName,time;
        JLabel clock,tutorial;
        JList restList;
        //constructor  
        public MainMenu(String UserValue)             
        {   
            userName = UserValue; 
            mainPanel = new JPanel(new GridLayout(3, 1));
            setDefaultCloseOperation(EXIT_ON_CLOSE); 
            setTitle("Welcome, "+userName);  
            setSize(300, 500); 
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            time = LocalTime.now().format(dtf);
            clock = new JLabel("You logged in at: "+ time);
            clock.setVisible(true);
            b1 = new JButton("Modify Account");
            b1.setVisible(true);
            b2 = new JButton("Browse Kitchens");
            b2.setVisible(true);
            add(mainPanel, BorderLayout.CENTER);
            mainPanel.add(clock);
            clock.setHorizontalAlignment(JLabel.CENTER);
            mainPanel.add(b1);
            mainPanel.add(b2);
            mainPanel.setVisible(true);
            b1.addActionListener(this); 
            b2.addActionListener(this); 

        }  
    @Override
    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter  
    {  
        if(ae.getSource()==b1)
        {
            ModifyAcc mod = new ModifyAcc(userName);
        }
        if(ae.getSource()==b2)
        {
            mainPanel.setVisible(false);
            kitchenPanel = new JPanel();
            add(kitchenPanel);
            try
            {
                Connection connection;
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase","root", "");
                ResultSet resultSet;
                Statement statement;
                statement = connection.createStatement();
                    
                var sql = "SELECT COUNT(UID) FROM KITCHENS WHERE START_HOUR < '" 
                            + time + "'AND END_HOUR > '" + time + "'";
                resultSet = statement.executeQuery(sql);
                resultSet.next();
                int no_of_rest = resultSet.getInt(1);
                sql = "SELECT USERS.UNAME,KITCHENS.* FROM KITCHENS,USERS WHERE START_HOUR < '" 
                            + time + "'AND END_HOUR > '" + time + "' AND KITCHENS.UID = USERS.UID";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next())
                {
                    int count = 0;
                    String[] rest = new String[no_of_rest];
                    do
                    {
                        String temp = resultSet.getString(1) + "(Cuisine:" + resultSet.getString(3) + " Rating:" + resultSet.getFloat(5) + ")";
                        rest[count] = temp;
                        count++;    
                    }while(resultSet.next());
                        
                    restList = new JList(rest);
                    restList.setVisible(true);
                    kitchenPanel.add(restList,BorderLayout.CENTER);
                    tutorial = new JLabel("Double click on any of the available restaurants.");
                    tutorial.setVisible(true);
                    kitchenPanel.add(tutorial,BorderLayout.CENTER);
                    restList.addMouseListener(new MouseAdapter() 
                    {
                        @Override
                        public void mouseClicked(MouseEvent evt) 
                        {
                            if (evt.getClickCount() == 2) 
                            {
                                kitchenPanel.setVisible(false);
                                mainPanel.setVisible(true);
                                String selectedRest;
                                selectedRest = restList.getSelectedValue().toString();
                                int iend = selectedRest.indexOf("(");
                                String restName = selectedRest.substring(0 , iend);
                                SeeMenu menu = new SeeMenu(restName);   
                            }
                        }
                    });
                }
                else
                {
                JLabel badnews;
                badnews = new JLabel("No available kitchens at this time.");
                badnews.setVisible(true);
                kitchenPanel.add(badnews); 
                }
                resultSet.close();
                statement.close();
                connection.close();
                kitchenPanel.setVisible(true);
                }
                catch (SQLException | ClassNotFoundException ex) 
                {
                Logger.getLogger(CreateLoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    
    }
