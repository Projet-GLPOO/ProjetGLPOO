package gui;

import server.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GuiLogin {
    private JFrame frame;
    private JTextField userNameField;
    private JTextField txtProjetGlpoo;
    private JPasswordField passwordField;
    private static GuiLogin window ;



    public static void main(String[] args) {
        launch();
        /*Test test = new Test();
        test.zlatan();*/
    }

    public static void launch() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new GuiLogin();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public GuiLogin() {

        initialize();
    }

    public void initialize() {
        frame = new JFrame();
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

        Button button = new Button("Login");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                Test test = new Test();
                if(test.zlatan(userNameField.getText(), String.valueOf(passwordField.getPassword()))) {
                    GuiRoom guiRoom = new GuiRoom();
                    guiRoom.launch();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Username or password error!");
                }
            }
        });
        button.setBackground(UIManager.getColor("List.dropLineColor"));



        button.setBounds(642, 442, 271, 66);
        frame.getContentPane().add(button);

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
//
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
