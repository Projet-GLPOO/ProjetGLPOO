package user;

import bdd.BddConnection;
import server.ServerThread;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Room implements Subject{

    List<User> contact;
    List<Group> userGroup;
    DefaultListModel model;
    BddConnection bddConnection;
    int idUser;
    List<Integer>  memberIdGroup;
    List<String> memberPseudoRoom;
    List<Integer> memberIdUser;
    ServerThread serverThread;

    ArrayList<Observer> observers;

    private List<Message> messageList;

    public Room(int id, BddConnection bddConnection ){
        contact = new ArrayList<User>();
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

    //listMessage
    //groupeid
    //userid
    //model
    public void getMessageGroup(List<Message> messageList, int groupId, int userId, DefaultListModel modelMessageGroupRoomSelected) throws SQLException {
        int numberOfMessage;
        numberOfMessage = bddConnection.getMessageNumberOfUser(userId, groupId);
        for(int i = 0; i < numberOfMessage; i++){
            bddConnection.giveUserMessagesfromGroupSelected(userId, groupId, messageList);
            modelMessageGroupRoomSelected.addElement(messageList.get(i).getMessage());
        }

    }


    //Récupère les groupes de l'utilisateur connecté et les ajoute à une defaultListModel qui sera affiché dans la room
    public void getDefaultListModel (DefaultListModel model) throws SQLException {
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

    //Récupère tous les membres liés au groupe sélectionné dans la guiRoom
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


    //Permet d'ajouter tous les utilisateurs présents dans la base de données (simplifie la création de groupe)
    public void addListUserRoom(DefaultListModel usersMemberRoom) throws SQLException {
        memberPseudoRoom = bddConnection.allPseudoFromBase();
        memberIdUser = bddConnection.allIdUserFromBase();

        for(int i = 0; i< memberPseudoRoom.size() ; i++){
            usersMemberRoom.addElement(memberPseudoRoom.get(i) +"#"+ memberIdUser.get(i));
        }
    }


    //Permet de créer un groupe
    public void createGroup(String groupName , List<String> groupMember ) throws SQLException {
        Connection conn;
        int idGroup;
        List<Integer> listIdMemberNewGroup = new ArrayList<Integer>();
        listIdMemberNewGroup = idFromPseudo(groupMember);

        /*List<String> pseudoMemberNewGroup = new ArrayList<String>();
        pseudoMemberNewGroup = stringFromPseudo(groupMember);*/

        idGroup = bddConnection.incrementGroupId();

        bddConnection.insertIntoGroupes(idGroup,groupName);
        bddConnection.insertIntoParticipantGroup(idGroup, listIdMemberNewGroup);

    }

    //Permet de récupérer le tag (id) d'un membre pour par la suite pouvoir l'insérer dans la base de données
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

    //Permet de récupérer le pseudo d'un membre
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

    //Permet de récupérer les messages du groupe sélectionner dans GuiRoom
    public void getGroupMessages(int idGroupe, List<Message> messagesList){

        bddConnection.giveGroupMessages(idGroupe, messagesList);
    }

    /**
     *
     * @param listMemberGroup
     * @return
     */
    //Permet de récupérer l'Id du groupe sélectioner dans GuiRoom
    public int getIdSelectedGroup(JList listMemberGroup){
        String tempIdGrp;
        tempIdGrp = listMemberGroup.getSelectedValue().toString(); // Récupère le nom du groupe sélectionné
        tempIdGrp =  tempIdGrp.substring(tempIdGrp.indexOf("#")+1,tempIdGrp.length()); //Dans le nom sélectionné récupère l'id du groupe

        return Integer.parseInt(tempIdGrp);
    }

    /**
     *
     * @param idUser
     * @return
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
     *
     * @param id
     * @param idSelectedGroup
     * @param message
     * @param timeStamp
     */
    public void sendMessageToServerConnection(int id, int idSelectedGroup, String message, String timeStamp) {

        try {
            bddConnection.sendMessageToBDD(id, idSelectedGroup, message, timeStamp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param message
     */
    public void sendMessage(Message message) {
        notifyObservers(message);
    }
    public void sendIdGroup(int idGroup) {
        notifyObservers(idGroup);
    }

    private void notifyObservers(int idGroup) {
        System.out.println("notify observers ");
        for (Observer o : observers) {
            o.sendIdGroup(idGroup);
        }
    }

    /**
     *
     * @param o
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    /**
     *
     * @param o
     */
    @Override
    public void removeObserver(Observer o) {

    }

    /**
     *
     * @param message
     */
    @Override
    public void notifyObservers(Message message) {
        System.out.println("notify observers ");
        for (Observer o : observers) {
            o.send(message);
        }

    }
}
