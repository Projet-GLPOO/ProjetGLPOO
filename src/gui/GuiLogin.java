package gui;

import bdd.BddConnection;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class GuiLogin {
    public JFrame frame;
    private JTextField userNameField;
    private JTextField txtProjetGlpoo;
    private JPasswordField passwordField;
    private BddConnection bddConnection;


    public GuiLogin() {
        initialize();
    }

    public void initialize() {

        frame = new JFrame();
        frame.setVisible(true);
        bddConnection = new BddConnection();
        try {
            bddConnection.launchBddConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame.getContentPane().setForeground(new Color(0, 0, 0));
        frame.getContentPane().setBackground(new Color(240, 248, 255));
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(24, 149, 210));
        panel.setBounds(0, 0, 555, 690);
        frame.getContentPane().add(panel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("./files/image/loginImage.png"));
        panel.add(lblNewLabel_1);

        Button buttonLogin = new Button("Login");
        buttonLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if(bddConnection.loginConnection(userNameField.getText(), String.valueOf(passwordField.getPassword())) && arg0.getSource()==buttonLogin) {
                    try {
                        GuiRoom guiRoom = new GuiRoom(userNameField.getText(), String.valueOf(passwordField.getPassword()), bddConnection.giveId(userNameField.getText()), bddConnection);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Username or password error!");
                }
            }
        });
        buttonLogin.setBackground(UIManager.getColor("List.dropLineColor"));



        buttonLogin.setBounds(642, 442, 271, 66);
        frame.getContentPane().add(buttonLogin);

        userNameField = new JTextField();
        userNameField.setBackground(Color.LIGHT_GRAY);
        userNameField.setBounds(642, 216, 347, 46);
        frame.getContentPane().add(userNameField);
        userNameField.setColumns(10);

        JSeparator separator = new JSeparator();
        separator.setBackground(Color.BLACK);
        separator.setBounds(642, 275, 401, 2);
        frame.getContentPane().add(separator);

        JLabel lblNewLabel = new JLabel("User Name");
        lblNewLabel.setBounds(642, 172, 85, 24);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(642, 290, 85, 24);
        frame.getContentPane().add(lblPassword);

        txtProjetGlpoo = new JTextField();
        txtProjetGlpoo.setBackground(new Color(240, 248, 255));
        txtProjetGlpoo.setFont(new Font("Calibri Light", Font.BOLD, 34));
        txtProjetGlpoo.setText("Projet GLPOO");
        txtProjetGlpoo.setBounds(642, 36, 367, 51);
        txtProjetGlpoo.setEditable(false);
        frame.getContentPane().add(txtProjetGlpoo);
        txtProjetGlpoo.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBackground(Color.LIGHT_GRAY);
        passwordField.setBounds(642, 332, 347, 46);
        frame.getContentPane().add(passwordField);
        frame.setBackground(new Color(240, 248, 255));
        frame.setBounds(100, 100, 1073, 737);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
