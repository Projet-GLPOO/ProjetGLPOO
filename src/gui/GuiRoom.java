package gui;

import bdd.BddConnection;
import user.Message;
import user.Room;
import user.User;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GuiRoom implements ActionListener{

    private JFrame frame;
    private JTextField nomSalonDeDiscussion;
    private JTextArea chatArea;
    private JTextArea messageToSendArea;

    private JButton sendMessageButton;
    private JButton createGroup;
    private Room room;
    private User user;
    private List<Message> messagesList;
    private BddConnection serverConnection;
    private JList listMemberGroup;












    /**
     * Create the application.
     */
    public GuiRoom(String userName, String mdp, int id, BddConnection serverConnection) throws SQLException {
        user = new User(userName, mdp);
        this.serverConnection = serverConnection;
        room = new Room(id, serverConnection );
        user.setId(id);
        List<Integer> groupUserId = new ArrayList<Integer>();
        List<String> groupUserPseudo = new ArrayList<String>();
        messagesList = new ArrayList<Message>();
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




        messageToSendArea = new JTextArea();
        messageToSendArea.setBounds(12, 615, 766, 64);
        JScrollPane scrollMessageToSend = new JScrollPane(messageToSendArea);
        scrollMessageToSend.setBounds(12, 615, 766, 64);
        frame.getContentPane().add(scrollMessageToSend);

        sendMessageButton = new JButton("Envoyer");
        sendMessageButton.setBounds(824, 620, 110, 25);
        sendMessageButton.setActionCommand("SendAMessage");
        sendMessageButton.addActionListener(this);
        sendMessageButton.setEnabled(true);
        frame.getContentPane().add(sendMessageButton);

        createGroup = new JButton("New Group");
        createGroup.setBounds(824, 660, 110, 25);
        createGroup.setActionCommand("CreateGroup");
        createGroup.addActionListener(this);
        createGroup.setEnabled(true);
        frame.getContentPane().add(createGroup);


        DefaultListModel model = new DefaultListModel();
        DefaultListModel modelParticipantGroup = new DefaultListModel();

        //Affiche les membres appartenant au groupe sélectionner
        room.getDefaultListModel(model);
        listMemberGroup = new JList(model);

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                chatArea.setText("");
                room.getMembersGroup(listMemberGroup,modelParticipantGroup);
                room.getGroupMessages(room.getIdSelectedGroup(listMemberGroup), messagesList);
                showGroupMessages(messagesList);
            }
        };
        listMemberGroup.addMouseListener(mouseListener);

        listMemberGroup.setBounds(140, 15, 80, 500);
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
        nomSalonDeDiscussion.setColumns(10);
        frame.getContentPane().add(nomSalonDeDiscussion);


        JList listContactGroup = new JList(modelParticipantGroup);
        listContactGroup.setBounds(800, 15, 125, 500);
        frame.getContentPane().add(listContactGroup);

    }


    public void actionPerformed(ActionEvent e){

        switch(e.getActionCommand()){

            case "SendAMessage":
                //Récupération du message saisis par l'utilisateur
                String tempIdGrp;
                String timeStamp = "null";
                tempIdGrp = listMemberGroup.getSelectedValue().toString(); // Récupère le nom du groupe sélectionné
                tempIdGrp =  tempIdGrp.substring(tempIdGrp.indexOf("#")+1,tempIdGrp.length()); //Dans le nom sélectionné récupère l'id du groupe
                timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                Message message = new Message(user.getId(),Integer.parseInt(tempIdGrp),messageToSendArea.getText(),timeStamp );
                String textmessage = messageToSendArea.getText();
                try {
                    room.callServerTread(message,user,chatArea);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                //Pour éviter l'envoie de lignes vides
               /* String newline = System.getProperty("line.separator");
                boolean hasNewline = message.contains(newline);
                if((message.trim().length() > 0) && !hasNewline) {
                    //Réupération de l'heure de l'envoi

                    //Affichage du message dans le chatArea
                    int taille;
                    if(message.length() <= 25) {
                        chatArea.append(user.getPseudo()+"#"+user.getId() + "  " + timeStamp + "\n" + message + "\n\n");
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
                }
                else{
                    //Si le message est vide et remplit d'espace, il vaut mieux
                    //que le curseur revienne au début de la zone de texte
                    messageToSendArea.setText("");
                }*/
                room.sendMessageToServerConnection(user.getId(), room.getIdSelectedGroup(listMemberGroup), textmessage, timeStamp);
                break;

            case "CreateGroup":
                try {
                    createFrame();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;






        }
    }


    public void createFrame() throws SQLException {
        JFrame frameGroup = new JFrame();
        frameGroup.setBounds(200, 200, 500, 500);
        frameGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameGroup.getContentPane().setLayout(null);
        frameGroup.setVisible(true);

        JTextArea chatAreaCreateGroup = new JTextArea();
        chatAreaCreateGroup.setBounds(1, 175, 500, 100);
        frameGroup.getContentPane().add(chatAreaCreateGroup);



        DefaultListModel modelusersMembersRoom = new DefaultListModel();
        room.addListUserRoom(modelusersMembersRoom);
        JList usersMemberRoom = new JList(modelusersMembersRoom);
        usersMemberRoom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        usersMemberRoom.setBounds(0, 0, 175, 150);
        frameGroup.getContentPane().add(usersMemberRoom);

        DefaultListModel modelcopyList = new DefaultListModel();

        JButton Copygroup = new JButton();
        Copygroup = new JButton("Copy");
        Copygroup.setBounds(175, 0, 135, 50);
        Copygroup.setEnabled(true);
        frameGroup.getContentPane().add(Copygroup);

        Copygroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelcopyList.clear();
                for(int i = 0; i < usersMemberRoom.getSelectedValuesList().size(); i++ ){
                    modelcopyList.addElement(usersMemberRoom.getSelectedValuesList().get(i));

                }
            }
        });




        JList<String> copyList = new JList<String>(modelcopyList);
        copyList.setBounds(310, 0, 175, 150);
        frameGroup.getContentPane().add(copyList);


        JButton sendMessageCreateGroupButton = new JButton();
        sendMessageCreateGroupButton = new JButton("Envoyer");
        sendMessageCreateGroupButton.setBounds(1, 300, 100, 50);
        sendMessageCreateGroupButton.setEnabled(true);
        frameGroup.getContentPane().add(sendMessageCreateGroupButton);

        List<String> groupMember = new ArrayList<String>();
        sendMessageCreateGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String message = chatAreaCreateGroup.getText();

              for(int i =0 ; i < copyList.getModel().getSize(); i++){
                  groupMember.add(copyList.getModel().getElementAt(i));

              }

                try {
                    room.createGroup(message,groupMember);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }



    public void showGroupMessages(List<Message> messageList){

        Message message;

        for(int i = 0; i < messageList.size(); i++){
            message = messageList.get(i);
            chatArea.append(room.idToPseudo(message.getUserID()) + "#" + message.getUserID() + " " + message.getPostDate().substring(0, message.getPostDate().length()-4) + "\n" + message.getMessage() + "\n\n");
        }
    }








}

