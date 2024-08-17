
package delivery;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;

public class SeeMenu extends JFrame implements ActionListener
{
    JPanel menuPanel,choicePanel,cartPanel;
    JSpinner quant;
    JList foodStuff;
    JLabel allergyStuff,spinnerLabel;
    JButton commit,bail,finalize;
    JTextArea cart;
    Float[] inPrice;
    float finPrice = 0;
    
    public SeeMenu(String restName, String userName)
    {
        menuPanel = new JPanel();
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setTitle("Menu of "+restName);  
        setSize(500, 300);
        setLayout(new GridLayout(3, 1));
        menuPanel.setLayout(new FlowLayout());
        choicePanel = new JPanel();
        choicePanel.setLayout(new FlowLayout());
        cartPanel = new JPanel();
        cartPanel.setLayout(new FlowLayout());
        add(menuPanel);
        add(choicePanel);
        add(cartPanel);
        setVisible(true);

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
            
            String food[] = new String[foodNum];
            String allergy[] = new String[foodNum];
            inPrice = new Float[foodNum];
            
            sql = "SELECT MENU.* FROM MENU,USERS WHERE MENU.UID = USERS.UID AND USERS.UNAME = '" + restName + "' ORDER BY MENU_ID ASC";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            int i;
            for(i=0; i<foodNum; i++)
            {
                food[i] = resultSet.getString(3) + ": " + resultSet.getFloat(5);
                allergy[i] = resultSet.getString(4);
                inPrice[i] = resultSet.getFloat(5);
                if(allergy[i] == null)
                    allergy[i]="None";
                resultSet.next();
            }
            resultSet.close();
            statement.close();
            connection.close();
            
            quant = new JSpinner(new SpinnerNumberModel(1,1,9,1));
            quant.setVisible(true);
            foodStuff = new JList(food);
            allergyStuff = new JLabel("Potential allergen: None");
            allergyStuff.setVisible(true);
            
            commit = new JButton("Add to cart");
            commit.setVisible(true);
            commit.addActionListener(this);
            bail = new JButton("Clear cart");
            bail.setVisible(true);
            bail.addActionListener(this);
            finalize = new JButton("Complete order");
            finalize.setVisible(true);
            finalize.addActionListener(this);
            
            spinnerLabel = new JLabel("No. of items:");
            spinnerLabel.setVisible(true);
            
            cart = new JTextArea();
            cart.setVisible(true);
            
            foodStuff.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent evt) 
                {
                    if (evt.getClickCount() == 1) 
                    {
                        int foodNo = foodStuff.getSelectedIndex();
                        allergyStuff.setText("Potential allergen: " + allergy[foodNo]);
                    }
                }
            });
            menuPanel.add(foodStuff);
            menuPanel.add(allergyStuff);
            
            choicePanel.add(spinnerLabel);
            choicePanel.add(quant);
            choicePanel.add(commit);
            choicePanel.add(bail);
            choicePanel.add(finalize);
            
            cartPanel.add(cart);
            
            menuPanel.setVisible(true);
            choicePanel.setVisible(true);
            cartPanel.setVisible(true);
            
        }
        catch (SQLException | ClassNotFoundException ex) 
        {
            Logger.getLogger(CreateLoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        String toList;
        int foodNo = 0;
        int quantNo;
        if(ae.getSource()==commit)
        {
            quantNo = (int) quant.getValue();
            toList = quantNo + "x " + foodStuff.getSelectedValue() + "\n";
            cart.setText(cart.getText() + toList);
            finPrice = finPrice + (quantNo*inPrice[foodStuff.getSelectedIndex()]);
            System.out.println(finPrice);
            foodNo++;
        }
        if(ae.getSource()==bail)
        {
            cart.setText(null);
            foodNo = 0;
            finPrice = 0;
        }
        if(ae.getSource()==finalize)
        {
            
            try
            {
                Connection connection;
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase","root", "");
                PreparedStatement stmt;
                stmt = connection.prepareStatement("idk do something");
                stmt.executeUpdate();
            }
            catch (SQLException | ClassNotFoundException ex) 
            {
                Logger.getLogger(CreateLoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }    
    }
}
