package delivery;

    //import required classes and packages  
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;   
      
    //create MainMenu class to create a new page on which user will navigate  
    public class MainMenu extends JFrame implements ActionListener  
    {
        JPanel newPanel;
        JButton b1;
        String userName;
        //constructor  
        public MainMenu(String UserValue)             
        {   
            userName = UserValue; 
            newPanel = new JPanel(new GridLayout(3, 1));
            setDefaultCloseOperation(EXIT_ON_CLOSE); 
            setTitle("Welcome, "+userName);  
            setSize(400, 200);  
            b1 = new JButton("Modify Account");
            b1.setVisible(true);
            add(newPanel, BorderLayout.CENTER); 
            newPanel.add(b1);
            newPanel.setVisible(true);
            b1.addActionListener(this); 
        }  
        @Override
        public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter  
        {  
            if(ae.getSource()==b1)
            {
                ModifyAcc mod = new ModifyAcc(userName);
            }
        }
    }   