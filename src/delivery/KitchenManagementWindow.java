package delivery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KitchenManagementWindow extends JFrame implements ActionListener {

    private final JTextField dishNameField, dishPriceField;
    private final JTextArea dishDescArea;
    private final JButton addDishButton;
    private final String kitchenName;

    public KitchenManagementWindow(String kitchenName) {
        this.kitchenName = kitchenName;
        setTitle("Kitchen Management");
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));

        JLabel dishNameLabel = new JLabel("Dish Name:");
        dishNameField = new JTextField();
        JLabel dishPriceLabel = new JLabel("Price:");
        dishPriceField = new JTextField();
        JLabel dishDescLabel = new JLabel("Description:");
        dishDescArea = new JTextArea();
        addDishButton = new JButton("Add Dish");
        addDishButton.addActionListener(this);

        add(dishNameLabel);
        add(dishNameField);
        add(dishPriceLabel);
        add(dishPriceField);
        add(dishDescLabel);
        add(new JScrollPane(dishDescArea));
        add(addDishButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDishButton) {
            String dishName = dishNameField.getText();
            String dishPrice = dishPriceField.getText();
            String dishDesc = dishDescArea.getText();

            try {
                Connection connection;
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase", "root", "");

                // Get the next available MENU_ID
                int menuId = getNextMenuId(connection);

                // Insert the dish
                String insertDishSql = "INSERT INTO MENU (UID, MENU_ID, FOOD_NAME, FOOD_ALLERGENS, PRICE) VALUES ((SELECT UID FROM USERS WHERE UNAME = ? AND ROLE = 'KITCHEN'), ?, ?, ?, ?)";
                PreparedStatement insertDishStmt = connection.prepareStatement(insertDishSql);
                insertDishStmt.setString(1, kitchenName); // Όνομα κουζίνας
                insertDishStmt.setInt(2, menuId);          // Νέο ID του μενού
                insertDishStmt.setString(3, dishName);     // Όνομα πιάτου
                insertDishStmt.setString(4, dishDesc);     // Περιγραφή πιάτου
                insertDishStmt.setFloat(5, Float.parseFloat(dishPrice)); // Τιμή πιάτου

                insertDishStmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Dish added successfully!");

                dishNameField.setText("");
                dishPriceField.setText("");
                dishDescArea.setText("");

                connection.close();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(KitchenManagementWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int getNextMenuId(Connection connection) throws SQLException {
        String maxMenuIdSql = "SELECT COALESCE(MAX(MENU_ID), 0) AS max_menu_id FROM MENU WHERE UID = (SELECT UID FROM USERS WHERE UNAME = ? AND ROLE = 'KITCHEN')";
        try (PreparedStatement maxMenuIdStmt = connection.prepareStatement(maxMenuIdSql)) {
            maxMenuIdStmt.setString(1, kitchenName);
            ResultSet rs = maxMenuIdStmt.executeQuery();
            if (rs.next()) {
                int maxMenuId = rs.getInt("max_menu_id");
                return maxMenuId + 1;
            }
        }
        return 1; // Επιστρέφει 1 αν δεν βρεθεί τίποτα
    }
}
