package gui;

import server.ServerConnection;
import user.Room;
import user.User;

import javax.swing.*;
import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.sql.Date;

public class GuiRoom implements ActionListener{

    private JFrame frame;
    private JTextField nomSalonDeDiscussion;
    private JTextArea chatArea;
    private JTextArea messageToSendArea;
    private JButton sendMessageButton;
    private Room room;
    private User user;
    private ServerConnection serverConnection;
    private int index;









    /**
     * Create the application.
     */
    public GuiRoom(String userName, String mdp, int id,ServerConnection serverConnection) throws SQLException {
        user = new User(userName, mdp);
        this.serverConnection = serverConnection;
        room = new Room(id, serverConnection );
        user.setId(id);
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() throws SQLException {
        frame = new JFrame();
        frame.setBounds(100, 100, 1012, 751);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        JList listContactUser = new JList();
        listContactUser.setBounds(12, 337, 187, -305);
        frame.getContentPane().add(listContactUser);


        messageToSendArea = new JTextArea();
        messageToSendArea.setBounds(12, 615, 766, 64);
        JScrollPane scrollMessageToSend = new JScrollPane(messageToSendArea);
        scrollMessageToSend.setBounds(12, 615, 766, 64);
        frame.getContentPane().add(scrollMessageToSend);

        sendMessageButton = new JButton("Envoyer");
        sendMessageButton.setBounds(824, 626, 97, 25);
        sendMessageButton.setActionCommand("SendAMessage");
        sendMessageButton.addActionListener(this);
        sendMessageButton.setEnabled(true);
        frame.getContentPane().add(sendMessageButton);


        DefaultListModel model = new DefaultListModel();
        model.addElement(user.getPseudo());
        room.getDefaultListModel(model);
        JList listMemberGroup = new JList(model);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                index = listMemberGroup.locationToIndex(e.getPoint());
                System.out.println("clicked on Item " + index);
                System.out.println(listMemberGroup.getSelectedValue().toString());
            }
        };
        listMemberGroup.addMouseListener(mouseListener);

        listMemberGroup.setBounds(100, 15, 125, 500);
        frame.getContentPane().add(listMemberGroup);

        chatArea = new JTextArea();
        chatArea.setBounds(240, 136, 538, 418);
        chatArea.setEditable(false);
        JScrollPane scrollChat = new JScrollPane(chatArea);
        scrollChat.setBounds(240, 136, 538, 418);
        frame.getContentPane().add(scrollChat);

        nomSalonDeDiscussion = new JTextField();
        nomSalonDeDiscussion.setText("Salon de discussion");
        nomSalonDeDiscussion.setBounds(371, 43, 285, 25);
        nomSalonDeDiscussion.setEditable(false);
        frame.getContentPane().add(nomSalonDeDiscussion);
        nomSalonDeDiscussion.setColumns(10);

        JList listContactGroup = new JList();
        listContactGroup.setBounds(800, 15, 125, 500);
        frame.getContentPane().add(listContactGroup);

    }


    public void actionPerformed(ActionEvent e){

        switch(e.getActionCommand()){

            case "SendAMessage":
                //Récupération du message saisis par l'utilisateur
                String message = messageToSendArea.getText();
                //Pour éviter l'envoie de lignes vides
                String newline = System.getProperty("line.separator");
                boolean hasNewline = message.contains(newline);
                if((message.trim().length() > 0) && !hasNewline) {
                    //Réupération de l'heure de l'envoi
                    String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                    //Affichage du message dans le chatArea
                    int taille;
                    if(message.length() <= 25) {
                        chatArea.append(user.getPseudo() + "  " + timeStamp + "\n" + message + "\n\n");
                    }
                    if(message.length() > 25) {
                        taille = 93;
                        chatArea.append("Pseudo" + "  " + timeStamp + "\n" + message.substring(0,93) +"\n");
                        while (taille <message.length())
                            if (taille >= message.length()) {
                                chatArea.append(message.substring(93, message.length()) + "\n");
                            }else {
                                taille+=93;
                                chatArea.append(message.substring(taille-92, taille) + "\n");
                            }
                    }
                    try {
                        chatArea.setCaretPosition(chatArea.getLineEndOffset(chatArea.getLineCount() - 1));
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }

                    //Suppression du contenu de la zone de texte "messageToSendArea"
                    messageToSendArea.setText("");
                    break;
                }
                else{
                    //Si le message est vide et remplit d'espace, il vaut mieux
                    //que le curseur revienne au début de la zone de texte
                    messageToSendArea.setText("");
                }
        }
    }
}
