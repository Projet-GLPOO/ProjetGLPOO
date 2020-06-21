package gui;

import bdd.BddConnection;
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
    private JFrame messageDeletionFrame;

    private JTextField nomSalonDeDiscussion;
    private JTextArea chatArea;
    private JTextArea messageToSendArea;

    private JButton sendMessageButton;
    private JButton createGroupButton;
    private JButton refreshButton;
    private JButton deleteMessageButton;
    private Room room;
    private User user;
    private List<Message> messagesList;
    private BddConnection bddConnection;
    private JList groupList;
    private JList choiceGroupJList;
    private DefaultListModel modelParticipantGroup = new DefaultListModel();
    private DefaultListModel model;
    private List<Message>messageList;
    private int messageIndex;


    /**
     * Constructeur de GuiRoom où est défini l'utilisateur, la room et appel la création de l'interface graphique de GuiRoom
     * @param userName Le pseudo de l'utilisateur
     * @param mdp Le mot de passe de l'utilisateur
     * @param id L'id de l'utilisateur
     * @param bddConnection La connection à la bdd
     * @throws SQLException erreur sql
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
     * Affiche la fenêtre GuiRoom, initialise tout ce qui est nécessaire à l'utilisateur
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

        sendMessageButton = new JButton("Send");
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




        createGroupButton = new JButton("New Group");
        createGroupButton.setBounds(824, 660, 110, 25);
        createGroupButton.setActionCommand("CreateGroup");
        createGroupButton.addActionListener(this);
        createGroupButton.setEnabled(true);
        frame.getContentPane().add(createGroupButton);


        deleteMessageButton = new JButton("Delete Message");
        deleteMessageButton.setBounds(824, 580, 110, 25);
        deleteMessageButton.setActionCommand("CreateDeleteMessageFrame");
        deleteMessageButton.addActionListener(this);
        deleteMessageButton.setEnabled(false);
        frame.getContentPane().add(deleteMessageButton);


        model = new DefaultListModel();

        //Affiche les membres appartenant au groupe sélectionner
        room.getListModel(model);
        groupList = new JList(model);

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                chatArea.setVisible(true);
                deleteMessageButton.setEnabled(true);
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
        nomSalonDeDiscussion.setText("Chat Room");
        nomSalonDeDiscussion.setBounds(371, 43, 285, 25);
        nomSalonDeDiscussion.setEditable(false);
        nomSalonDeDiscussion.setColumns(10);
        frame.getContentPane().add(nomSalonDeDiscussion);

        JList membersFromGroupList = new JList(modelParticipantGroup);
        membersFromGroupList.setBounds(815, 15, 150, 500);
        frame.getContentPane().add(membersFromGroupList);
    }

    /**
     * Ecoute les boutons présent dans l'interface GuiRoom
     * @param e action event
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
                    //Lance l'interface graphique de la création de groupe
                    createCreationGroupFrame();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                break;

            case "RefreshGroupsList":
                //Permet d'effacer le contenu de groupList
                sendMessageButton.setEnabled(false);
                groupList.removeAll();
                try {
                    //Met à jour le contenu du model
                    room.getListModel(model);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //Met à jour groupList grâce au nouveau contenu du model
                groupList.setModel(model);
                break;

            case "CreateDeleteMessageFrame" :
                try {
                    //Lance l'interface graphique de la suppression de message
                    createMessageDeletionFrame();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                break;

            case "DeleteAMessage":
                //Permet de supprimer un message à partir de son index
                room.deleteMessage(messageList.get(messageIndex));
                //Ferme l'interface graphique de suppression de message
                messageDeletionFrame.dispose();
                //Affiche dans la zone de texte de la room le message reçu si l'utilisateur courant se trouve dans le bon groupe
                if(groupList.getSelectedValue().toString().equals(choiceGroupJList.getSelectedValue().toString())){
                    room.getGroupMessages(room.getIdSelectedGroup(groupList), messagesList);
                    chatArea.setText("");
                    showGroupMessages(messagesList);
                }
                break;
        }
    }

    /**
     * Permet de créer l'interface graphique de création de groupe et gère son contenu
     * @throws SQLException sql erreur
     */
    public void createCreationGroupFrame() throws SQLException {
        final JFrame frameGroup = new JFrame();
        frameGroup.setBounds(200, 200, 500, 500);
        frameGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameGroup.getContentPane().setLayout(null);
        frameGroup.setVisible(true);

        //Creation de la zone de texte dans laquelle, il faut insérer le nom du groupe à créer
        final JTextArea chatAreaCreateGroup = new JTextArea("Choose a name for your group: ");
        chatAreaCreateGroup.setBounds(1, 175, 500, 100);
        frameGroup.getContentPane().add(chatAreaCreateGroup);

        //Création du model servant de contenu pour la JList usersMemberRoom
        DefaultListModel modelUsersMembersRoom = new DefaultListModel();
        room.addListUserRoom(modelUsersMembersRoom);
        final JList usersMemberRoom = new JList(modelUsersMembersRoom);
        usersMemberRoom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        usersMemberRoom.setBounds(0, 0, 175, 150);
        frameGroup.getContentPane().add(usersMemberRoom);

        //Création du model servant de contenu pour la JList copyList
        final DefaultListModel modelcopyList = new DefaultListModel();
        JButton Copygroup = new JButton();
        Copygroup = new JButton("Copy");
        Copygroup.setBounds(175, 0, 135, 50);
        Copygroup.setEnabled(true);
        frameGroup.getContentPane().add(Copygroup);
        final JList<String> copyList = new JList<String>(modelcopyList);
        copyList.setBounds(310, 0, 175, 150);
        frameGroup.getContentPane().add(copyList);

        //Fais une copie des utilisateurs selectionnés dans usersMemberRoom afin de créer un nouveau grouoe
        Copygroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelcopyList.clear();
                for(int i = 0; i < usersMemberRoom.getSelectedValuesList().size(); i++ ){
                    modelcopyList.addElement(usersMemberRoom.getSelectedValuesList().get(i));

                }
            }
        });


        JButton sendMessageCreateGroupButton = new JButton();
        sendMessageCreateGroupButton = new JButton("Send");
        sendMessageCreateGroupButton.setBounds(1, 300, 100, 50);
        sendMessageCreateGroupButton.setEnabled(true);
        frameGroup.getContentPane().add(sendMessageCreateGroupButton);

        //Permet la création du groupe dans la base de données
        final List<String> groupMember = new ArrayList<String>();
        sendMessageCreateGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = chatAreaCreateGroup.getText();
                for(int i =0 ; i < copyList.getModel().getSize(); i++){
                    groupMember.add(copyList.getModel().getElementAt(i));

                }
                try {
                    if(!message.trim().equals("") && message.length() > 0) {
                        //Créer le groupe dans la base de donnée
                        room.createGroup(message, groupMember);
                        //Indique à l'utilisateur que le groupe a bien été créé
                        JOptionPane.showMessageDialog(null, "Group created : " + message);
                        //Ferme la fenêtre de création de groupe
                        frameGroup.dispose();

                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Error detected, no group created !");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    /**
     * Lance l'interface graphique permettant de choisir parmi les messages postés pour les supprimer
     * @throws SQLException erreur sql
     */
    public void createMessageDeletionFrame() throws SQLException {
        messageDeletionFrame = new JFrame();

        messageDeletionFrame.setBounds(200, 200, 650, 500);
        messageDeletionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        messageDeletionFrame.getContentPane().setLayout(null);
        messageDeletionFrame.setVisible(true);


        DefaultListModel modelGroupRoom = new DefaultListModel();
        room.getListModel(modelGroupRoom);
        choiceGroupJList = new JList(modelGroupRoom);
        JScrollPane choiceGroupJListJSchrollPane = new JScrollPane(choiceGroupJList);
        choiceGroupJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        choiceGroupJListJSchrollPane.setBounds(225, 0, 175, 150);
        messageDeletionFrame.getContentPane().add(choiceGroupJListJSchrollPane);

        messageList = new ArrayList<Message>();
        final DefaultListModel modelMessageGroupRoomSelected =  new DefaultListModel();
        final JList choiceMessageGroupJList = new JList(modelMessageGroupRoomSelected);
        final JScrollPane choiceMessageGroupJListJSchrollPane = new JScrollPane(choiceMessageGroupJList);
        choiceMessageGroupJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        choiceMessageGroupJListJSchrollPane.setBounds(0, 175, 640, 100);
        messageDeletionFrame.getContentPane().add(choiceMessageGroupJListJSchrollPane);

        final JButton deleteMessageButton = new JButton("Delete");
        deleteMessageButton.setBounds(400, 300, 200, 20);
        deleteMessageButton.setActionCommand("DeleteAMessage");
        deleteMessageButton.addActionListener(this);
        deleteMessageButton.setEnabled(false);
        messageDeletionFrame.getContentPane().add(deleteMessageButton);

        MouseListener mouseListenerGroup = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                modelMessageGroupRoomSelected.clear();
                String tempIdGrp;
                tempIdGrp = choiceGroupJList.getSelectedValue().toString(); // Récupère le nom du groupe sélectionné
                tempIdGrp = tempIdGrp.substring(tempIdGrp.indexOf("#") + 1, tempIdGrp.length());
                try {
                    room.getMessageGroup(messageList, Integer.parseInt(tempIdGrp), user.getId(), modelMessageGroupRoomSelected);
                    choiceMessageGroupJList.setModel(modelMessageGroupRoomSelected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        };
        choiceGroupJList.addMouseListener(mouseListenerGroup);

        MouseListener mouseListenerMessage = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                deleteMessageButton.setEnabled(true);
                messageIndex = choiceMessageGroupJList.getSelectedIndex();
            }
        };
        choiceMessageGroupJList.addMouseListener(mouseListenerMessage);



    }


    /**
     * Permet d'afficher les messages correpondant au groupe sélectionné
     * @param messageList Liste de message
     */
    public void showGroupMessages(List<Message> messageList){

        Message message;

        for (Message value : messageList) {
            message = value;
            chatArea.append(room.idToPseudo(message.getUserID()) + "#" + message.getUserID() + " " + message.getPostDate().substring(0, message.getPostDate().length() - 4) + "\n" + message.getMessage() + "\n\n");
        }
    }
}