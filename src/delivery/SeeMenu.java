
package delivery;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;

public class SeeMenu extends JFrame implements ActionListener {
    JPanel menuPanel, choicePanel, cartPanel;
    JSpinner quant;
    JList foodStuff, cart;
    JLabel allergyStuff, spinnerLabel, finalPriceLabel;
    JButton commit, bail, finalize;

    DefaultListModel listModel;
    Float[] inPrice;
    float finalPrice = 0;
    String[] pureFood;

    public SeeMenu(String restName, String userName) {
        menuPanel = new JPanel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Menu of " + restName);
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

        try {
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase", "root", "");
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;

            // Query to get the number of menu items for this kitchen
            String sql = "SELECT COUNT(*) FROM MENU, USERS WHERE MENU.UID = USERS.UID AND USERS.UNAME = '" + restName + "'";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            int foodNum = resultSet.getInt(1);

            String[] food = new String[foodNum];
            String[] allergy = new String[foodNum];
            pureFood = new String[foodNum];
            inPrice = new Float[foodNum];

            // Query to get all menu items for this kitchen
            sql = "SELECT MENU.* FROM MENU, USERS WHERE MENU.UID = USERS.UID AND USERS.UNAME = '" + restName + "' ORDER BY MENU_ID ASC";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            for (int i = 0; i < foodNum; i++) {
                pureFood[i] = resultSet.getString(3);
                food[i] = pureFood[i] + ": " + resultSet.getFloat(5);
                allergy[i] = resultSet.getString(4);
                inPrice[i] = resultSet.getFloat(5);
                if (allergy[i] == null)
                    allergy[i] = "None";
                resultSet.next();
            }
            resultSet.close();
            statement.close();
            connection.close();

            quant = new JSpinner(new SpinnerNumberModel(1, 1, 9, 1));
            quant.setVisible(true);
            foodStuff = new JList(food);
            allergyStuff = new JLabel("Potential allergen: None");
            allergyStuff.setVisible(true);

            commit = new JButton("Add to cart (↑)");
            commit.setVisible(true);
            commit.addActionListener(this);
            bail = new JButton("Remove selected item (↓)");
            bail.setVisible(true);
            bail.addActionListener(this);
            finalize = new JButton("Complete order");
            finalize.setVisible(true);
            finalize.addActionListener(this);

            spinnerLabel = new JLabel("No. of items:");
            spinnerLabel.setVisible(true);

            listModel = new DefaultListModel();
            cart = new JList(listModel);
            cart.setVisible(true);

            finalPriceLabel = new JLabel("Total cost: 0.00");
            finalPriceLabel.setVisible(true);

            foodStuff.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    int foodNo = foodStuff.getSelectedIndex();
                    allergyStuff.setText("Potential allergen: " + allergy[foodNo]);
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
            cartPanel.add(finalPriceLabel);

            menuPanel.setVisible(true);
            choicePanel.setVisible(true);
            cartPanel.setVisible(true);

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CreateLoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        String toList;
        int foodNo = 0;
        int quantNo;
        int foodSpot;
        Float tempPrice;
        if (ae.getSource() == commit && (!foodStuff.isSelectionEmpty())) 
        {
            quantNo = (int) quant.getValue();
            foodSpot = foodStuff.getSelectedIndex();
            tempPrice = quantNo * inPrice[foodSpot];
            finalPrice = finalPrice + tempPrice;
            toList = quantNo + "x " + pureFood[foodSpot] + ": " + tempPrice + "\n";
            listModel.addElement(toList);
            finalPriceLabel.setText("Total cost: " + finalPrice);
            quant.setValue(1);
            foodNo++;
        }
        if (ae.getSource() == bail && (!cart.isSelectionEmpty())) 
        {
            foodNo--;
            String selectedCart;
            selectedCart = cart.getSelectedValue().toString();
            int iend = selectedCart.indexOf(":"); // Get the value of the item from the end of the line.
            Float removePrice = Float.valueOf(selectedCart.substring(iend + 1));
            finalPrice = finalPrice - removePrice;
            finalPriceLabel.setText("Total cost: " + finalPrice);
            listModel.remove(cart.getSelectedIndex());
        }
        if (ae.getSource() == finalize) 
        {
            double delCut,serviceCut;
            delCut = finalPrice * 0.15;
            serviceCut = finalPrice * 0.2;
            JOptionPane.showMessageDialog(this, "Order completed! Total cost: " + finalPrice + "\n Deliverer gets: " +delCut+"\n The app gets: " + serviceCut);
            
        }
    }
}
