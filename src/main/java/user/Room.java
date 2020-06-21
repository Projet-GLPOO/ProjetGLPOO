package user;

import bdd.BddConnection;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Room implements Subject{

    private List<Group> userGroup;
    private DefaultListModel model;
    private BddConnection bddConnection;
    private int idUser;
    private List<Integer>  memberIdGroup;
    private List<String> memberPseudoRoom;
    private List<Integer> memberIdUser;
    private ArrayList<Observer> observers;
    private List<Message> messageList;

    /**
     * Constructeur de Room
     * @param id Id de l'utilisateur de la room
     * @param bddConnection La connection de l'utilisateur à la bdd
     */
    public Room(int id, BddConnection bddConnection ){
        userGroup = new ArrayList<Group>();
        model = new DefaultListModel();
        this.bddConnection = bddConnection;
        idUser = id;
        memberIdGroup = new ArrayList<Integer>();
        memberPseudoRoom = new ArrayList<String>();
        memberIdUser = new ArrayList<Integer>();
        messageList = new ArrayList<Message>();
        observers = new ArrayList<Observer>();
    }


    /**
     * Récupère les messages d'un groupe sélectionné et de les ajoute à messageList
     * @param messageList La list des message du groupe
     * @param groupId L'id du groupe
     * @param userId L'id de l'utilisateur du groupe
     * @param modelMessageGroupRoomSelected La DefaultListModel du groupe
     * @throws SQLException Les erreurs sql
     */
    public void getMessageGroup(List<Message> messageList, int groupId, int userId, DefaultListModel modelMessageGroupRoomSelected) throws SQLException {
        int numberOfMessage;
        numberOfMessage = bddConnection.getMessageNumberOfUser(userId, groupId);
        for(int i = 0; i < numberOfMessage; i++){
            bddConnection.giveUserMessagesfromGroupSelected(userId, groupId, messageList);
            modelMessageGroupRoomSelected.addElement(messageList.get(i).getMessage());
        }

    }


    /**
     * Récupère les groupes de l'utilisateur connecté et les ajoute à une defaultListModel qui sera affiché dans la room
     * @param model La DefaultListModel
     * @throws SQLException Les erreurs sql
     */
    public void getListModel(DefaultListModel model) throws SQLException {
        userGroup.clear();
        bddConnection.giveGroups(userGroup);
        model.clear();
        model.removeAllElements();
        for(int i = 0; i <userGroup.size();i++ ){
            memberIdGroup = bddConnection.giveGroupUsers(userGroup.get(i).getIdGroup());
            if(bddConnection.verifUserGroup(idUser, memberIdGroup)){
                model.addElement(userGroup.get(i).getNomGroupe()+"#"+userGroup.get(i).getIdGroup());
            }
        }
    }


    /**
     * Récupère tous les membres liés au groupe sélectionné dans la guiRoom
     * @param listMemberGroup La JList
     * @param modelParticipantGroup La DefaultListModel
     */
    public void getMembersGroup(JList listMemberGroup, DefaultListModel modelParticipantGroup ){
        String tempIdGrp;
        List<Integer> groupUserId; // les id de tout les membres grp
        List<String> groupUserPseudo;

        modelParticipantGroup.clear();
        tempIdGrp = listMemberGroup.getSelectedValue().toString(); // Récupère le nom du groupe sélectionné
        tempIdGrp =  tempIdGrp.substring(tempIdGrp.indexOf("#")+1,tempIdGrp.length()); //Dans le nom sélectionné récupère l'id du groupe
        try {
            groupUserId = bddConnection.giveGroupUsers(Integer.parseInt(tempIdGrp)); //Récupère tous les id des membres du groupe sélectionné
            groupUserPseudo = bddConnection.userIdToPseudo(groupUserId); // Récupère les Pseudo des membres du groupe sélectionné

            for(int i = 0 ; i < groupUserPseudo.size(); i++){
                modelParticipantGroup.addElement(groupUserPseudo.get(i)+"#"+ groupUserId.get(i)); // Ajoute les pseudo à modelParticipantGroup
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Permet d'ajouter tous les utilisateurs présents dans la base de données (simplifie la création de groupe)
     * @param usersMemberRoom La DefaultListModel
     * @throws SQLException Les erreurs sql
     */
    public void addListUserRoom(DefaultListModel usersMemberRoom) throws SQLException {
        memberPseudoRoom = bddConnection.allPseudoFromBase();
        memberIdUser = bddConnection.allIdUserFromBase();

        for(int i = 0; i< memberPseudoRoom.size() ; i++){
            usersMemberRoom.addElement(memberPseudoRoom.get(i) +"#"+ memberIdUser.get(i));
        }
    }


    /**
     * Permet de créer un groupe
     * @param groupName Le nom du groupe
     * @param groupMember La liste des membres du groupe
     * @throws SQLException Les erreurs sql
     */
    public void createGroup(String groupName , List<String> groupMember ) throws SQLException {
        int idGroup;
        List<Integer> listIdMemberNewGroup = new ArrayList<Integer>();
        listIdMemberNewGroup = idFromPseudo(groupMember);

        idGroup = bddConnection.incrementGroupId();

        bddConnection.insertIntoGroupes(idGroup,groupName);
        bddConnection.insertIntoParticipantGroup(idGroup, listIdMemberNewGroup);

    }


    /**
     * Permet de récupérer le tag (id) d'un membre pour par la suite pouvoir l'insérer dans la base de données
     * @param groupMember La liste des membres d'un groupe
     * @return idMemberNewGroup
     */
    public List<Integer> idFromPseudo ( List<String> groupMember){
        List<Integer> idMemberNewGroup = new ArrayList<Integer>();
        String tempIdMemberGrp;
        for(int i = 0; i < groupMember.size() ; i++){
            tempIdMemberGrp = groupMember.get(i);
            tempIdMemberGrp =  tempIdMemberGrp.substring(tempIdMemberGrp.indexOf("#")+1,tempIdMemberGrp.length());
            idMemberNewGroup.add(Integer.parseInt(tempIdMemberGrp));
        }
        return idMemberNewGroup;
    }


    /**
     * Permet de récupérer le pseudo d'un membre
     * @param groupMember  Le pseudo d'un membre du groupe
     * @return pseudoMemberNewGroup
     */
    public List<String> stringFromPseudo( List<String> groupMember ){
        List<String> pseudoMemberNewGroup = new ArrayList<String>();

        String tempPseudoMemberGrp;

        for(int i = 0; i < groupMember.size() ; i++){
            tempPseudoMemberGrp = groupMember.get(i);
            tempPseudoMemberGrp =  tempPseudoMemberGrp.substring(0,tempPseudoMemberGrp.indexOf("#"));
            pseudoMemberNewGroup.add(tempPseudoMemberGrp);
        }
        return pseudoMemberNewGroup;
    }


    /**
     * Permet de récupérer les messages du groupe sélectionné dans GuiRoom
     * @param idGroupe L'id du groupe
     * @param messagesList Une liste de type Message
     */
    public void getGroupMessages(int idGroupe, List<Message> messagesList){

        bddConnection.giveGroupMessages(idGroupe, messagesList);
    }


    /**
     * Permet de récupérer l'Id du groupe sélectionné dans GuiRoom
     * @param listMemberGroup La JList
     * @return tempIdGrp
     */
    public int getIdSelectedGroup(JList listMemberGroup){
        String tempIdGrp;
        tempIdGrp = listMemberGroup.getSelectedValue().toString(); // Récupère le nom du groupe sélectionné
        tempIdGrp =  tempIdGrp.substring(tempIdGrp.indexOf("#")+1,tempIdGrp.length()); //Dans le nom sélectionné récupère l'id du groupe

        return Integer.parseInt(tempIdGrp);
    }


    /**
     * Récupère le pseudo d'un utilisateur à partir de son ID
     * @param idUser L'id d'un utilisateur
     * @return bddConnection.givePseudoFromId(idUser) ou null
     */
    public String idToPseudo(int idUser){

        try {
            return bddConnection.givePseudoFromId(idUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Appel la BDD pour inserer un message
     * @param id L'id d'un utilisateur
     * @param idSelectedGroup L'id du groupe sélectionné
     * @param message Le message (textuel)
     * @param timeStamp La date d'envoie du message
     */
    public void sendMessageToServerConnection(int id, int idSelectedGroup, String message, String timeStamp) {

        try {
            bddConnection.sendMessageToBDD(id, idSelectedGroup, message);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Envoie une notification à l'observeur et lui envoie un message
     * @param message Le message
     */
    public void sendMessage(Message message) {
        notifyObservers(message);
    }


    /**
     * Envoie une notification à l'observeur et lui envoie idGroup
     * @param idGroup L'id du groupe
     */
    public void sendIdGroup(int idGroup) {
        notifyObservers(idGroup);
    }


    /**
     * Envoie une notification à simpleClient et lui envoie l'idGroup
     * @param idGroup L'id du groupe
     */
    private void notifyObservers(int idGroup) {
        System.out.println("notify observers ");
        for (Observer o : observers) {
            o.sendIdGroup(idGroup);
        }
    }


    /**
     * Ajoute un observers à l'ArrayList observers
     * @param o Observer
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

     /**
     * Envoie une notification à simpleClient et lui envoie le message
     * @param message Le message
     */
    @Override
    public void notifyObservers(Message message) {
        System.out.println("notify observers ");
        for (Observer o : observers) {
            o.send(message);
        }

    }


    /**
     * Appel la BDD pour effacer un message
     * @param message Le message
     */
    public void deleteMessage(Message message) {
        bddConnection.deleteUserMessage(message);
    }
}
