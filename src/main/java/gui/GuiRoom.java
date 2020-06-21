package gui;

import bdd.BddConnection;
import server.ServerThread;
import user.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GuiRoom implements ActionListener {

    private JFrame frame;
    private JTextField nomSalonDeDiscussion;
    private JTextArea chatArea;
    private JTextArea messageToSendArea;

    private JButton sendMessageButton;
    private JButton createGroup;
    private JButton refreshButton;
    private Room room;
    private User user;
    private List<Message> messagesList;
    private BddConnection bddConnection;
    private JList groupList;
    private DefaultListModel modelParticipantGroup = new DefaultListModel();
    private DefaultListModel model;
    private Observer observer;


    /**
     *
     * @param userName
     * @param mdp
     * @param id
     * @param bddConnection
     * @throws SQLException
     */
    public GuiRoom(String userName, String mdp, int id, BddConnection bddConnection) throws SQLException {
        user = new User(userName, mdp);
        this.bddConnection = bddConnection;
        room = new Room(id, bddConnection );
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
        chatArea = new JTextArea();
        chatArea.setVisible(false);
        Observer a = new SimpleClient(chatArea,"localhost");

        room.registerObserver(a);





        messageToSendArea = new JTextArea();
        messageToSendArea.setBounds(12, 615, 766, 64);
        JScrollPane scrollMessageToSend = new JScrollPane(messageToSendArea);
        scrollMessageToSend.setBounds(12, 615, 766, 64);
        frame.getContentPane().add(scrollMessageToSend);

        sendMessageButton = new JButton("Envoyer");
        sendMessageButton.setBounds(824, 620, 110, 25);
        sendMessageButton.setActionCommand("SendAMessage");
        sendMessageButton.addActionListener(this);
        sendMessageButton.setEnabled(false);
        frame.getContentPane().add(sendMessageButton);

        refreshButton = new JButton("Refresh groups' list");
        refreshButton.setBounds(5, 550, 200, 25);
        refreshButton.setActionCommand("RefreshGroupsList");
        refreshButton.addActionListener(this);
        refreshButton.setEnabled(true);
        frame.getContentPane().add(refreshButton);




        createGroup = new JButton("New Group");
        createGroup.setBounds(824, 660, 110, 25);
        createGroup.setActionCommand("CreateGroup");
        createGroup.addActionListener(this);
        createGroup.setEnabled(true);
        frame.getContentPane().add(createGroup);


        model = new DefaultListModel();

        //Affiche les membres appartenant au groupe sélectionner
        room.getDefaultListModel(model);
        groupList = new JList(model);

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                chatArea.setVisible(true);
                sendMessageButton.setEnabled(true);
                chatArea.setText("");
                room.getMembersGroup(groupList,modelParticipantGroup);
                room.getGroupMessages(room.getIdSelectedGroup(groupList), messagesList);
                String tempIdGrp;
                tempIdGrp = groupList.getSelectedValue().toString(); // Récupère le nom du groupe sélectionné
                tempIdGrp = tempIdGrp.substring(tempIdGrp.indexOf("#") + 1, tempIdGrp.length()); //Dans le nom sélectionné récu
                System.out.println(tempIdGrp);
                room.sendIdGroup(Integer.parseInt(tempIdGrp));
                showGroupMessages(messagesList);
            }
        };
        groupList.addMouseListener(mouseListener);

        groupList.setBounds(50, 15, 150, 500);
        frame.getContentPane().add(groupList);


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


        JList membersFromGroupList = new JList(modelParticipantGroup);
        membersFromGroupList.setBounds(815, 15, 150, 500);
        frame.getContentPane().add(membersFromGroupList);
    }

    /**
     *
     * @param e
     */

    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "SendAMessage":
                //Récupération du message saisis par l'utilisateur
                String tempIdGrp;
                String timeStamp = "null";
                tempIdGrp = groupList.getSelectedValue().toString(); // Récupère le nom du groupe sélectionné
                tempIdGrp = tempIdGrp.substring(tempIdGrp.indexOf("#") + 1, tempIdGrp.length()); //Dans le nom sélectionné récupère l'id du groupe
                timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                Message message = new Message(user, Integer.parseInt(tempIdGrp), messageToSendArea.getText().trim(), timeStamp);
                if (!message.getMessage().equals("")) {
                    //Message qu'on envoie au serveur
                    room.sendMessage(message);
                    messageToSendArea.setText("");

                    //Message qu'on envoie à la base de donnée
                    room.sendMessageToServerConnection(user.getId(), room.getIdSelectedGroup(groupList), message.getMessage().trim(), timeStamp);
                }
                break;

            case "CreateGroup":
                try {
                    createCreationGroupFrame();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case "RefreshGroupsList":
                groupList.removeAll();
                try {
                    room.getDefaultListModel(model);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                groupList.setModel(model);
                break;
        }
    }

    /**
     *
     * @throws SQLException
     */
    public void createCreationGroupFrame() throws SQLException {
        JFrame frameGroup = new JFrame();
        frameGroup.setBounds(200, 200, 500, 500);
        frameGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameGroup.getContentPane().setLayout(null);
        frameGroup.setVisible(true);

        final JTextArea chatAreaCreateGroup = new JTextArea();
        chatAreaCreateGroup.setBounds(1, 175, 500, 100);
        frameGroup.getContentPane().add(chatAreaCreateGroup);



        DefaultListModel modelusersMembersRoom = new DefaultListModel();
        room.addListUserRoom(modelusersMembersRoom);
        final JList usersMemberRoom = new JList(modelusersMembersRoom);
        usersMemberRoom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        usersMemberRoom.setBounds(0, 0, 175, 150);
        frameGroup.getContentPane().add(usersMemberRoom);

        final DefaultListModel modelcopyList = new DefaultListModel();

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




        final JList<String> copyList = new JList<String>(modelcopyList);
        copyList.setBounds(310, 0, 175, 150);
        frameGroup.getContentPane().add(copyList);


        JButton sendMessageCreateGroupButton = new JButton();
        sendMessageCreateGroupButton = new JButton("Envoyer");
        sendMessageCreateGroupButton.setBounds(1, 300, 100, 50);
        sendMessageCreateGroupButton.setEnabled(true);
        frameGroup.getContentPane().add(sendMessageCreateGroupButton);

        final List<String> groupMember = new ArrayList<String>();
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


    /**
     *
     * @param messageList
     */
    public void showGroupMessages(List<Message> messageList){

        Message message;

        for(int i = 0; i < messageList.size(); i++){
            message = messageList.get(i);
            chatArea.append(room.idToPseudo(message.getUserID()) + "#" + message.getUserID() + " " + message.getPostDate().substring(0, message.getPostDate().length()-4) + "\n" + message.getMessage() + "\n\n");
        }
    }
}