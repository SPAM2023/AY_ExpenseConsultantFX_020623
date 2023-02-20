package gui_v1.login;
import db_connectors.Connectivity;
import gui_v1.automation.GUI_ElementCreator;
import gui_v1.mainWindows.MainGUIWindow;
import gui_v1.settings.GUI_LoginSignUpWiindows_Settings;
import gui_v1.testers.GUI_MainWindowTester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GUI_LogInP extends JPanel implements GUI_LoginSignUpWiindows_Settings, ActionListener {
    private JFrame loginFrame;
    private JTextField jtfLogInName;
    private JPasswordField jtfPass;
    private  JPasswordField jtfPass2;


    public GUI_LogInP(JFrame loginFrame){
        this.loginFrame=loginFrame;
        setLayout(new BorderLayout());
//        add(new Label(strLogInHeadTitle), BorderLayout.NORTH);
        add(GUI_ElementCreator.newTitle(strLogInHeadTitle), BorderLayout.NORTH);

        JPanel inputBoxP = new JPanel();
        inputBoxP.setLayout(new GridLayout(3,2));
        jtfLogInName =  new JTextField();
        jtfLogInName.setText("");
        jtfLogInName.selectAll();
        jtfPass =  new JPasswordField();
        jtfPass.setText("");


        JButton jbtOk = new JButton("OK");
//        jbtOk.addActionListener(new LogIN_Actons());


        jbtOk.addActionListener(this);
        inputBoxP.add(new JLabel("Login Name:"));

        inputBoxP.add(jtfLogInName);
        inputBoxP.add(new JLabel("Password:"));
        inputBoxP.add(jtfPass);


        add(inputBoxP, BorderLayout.CENTER);
        add(jbtOk, BorderLayout.SOUTH);

    }


    @Override
    public void actionPerformed(ActionEvent a) {
        if (a.getActionCommand().compareToIgnoreCase("OK")==0) {



            Connectivity connectivity = new Connectivity();
            Connection connection = connectivity.getConnection();
            String query = "select count(1) from  users where email = ? and password = ? ";
            PreparedStatement statement = null;
            System.out.println(String.valueOf(jtfPass.getPassword()));
            System.out.println(String.valueOf(jtfLogInName.getText()));
            try {
                statement = connection.prepareStatement(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                statement.setString(1, jtfLogInName.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                statement.setString(2, String.valueOf(jtfPass.getPassword()));
            } catch (SQLException e) {
                throw new RuntimeException(e);


            }
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    if (resultSet.getInt(1) == 1) {
                       MainGUIWindow mg= new MainGUIWindow();
                       mg.setVisible(true);
                    } else {
                      JOptionPane.showMessageDialog(null,"Wrong Email or Password!");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }






//            JOptionPane.showMessageDialog(null, userInfo,"Confirm", JOptionPane.INFORMATION_MESSAGE);
//            loginFrame.setVisible(false);
            //new MainGUIWindow();

        }
    }

