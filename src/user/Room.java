package user;

import server.ServerConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Room {

    List<User> contact;
    List<Group> userGroup;
    DefaultListModel model;
    ServerConnection serverConnection;
    int idUser;
    List<Integer>  memberIdGroup;
    List<String> memberPseudoRoom;
    List<Integer> memberIdUser;

    public Room(int id,ServerConnection serverConnection ){
        contact = new ArrayList<User>();
        userGroup = new ArrayList<Group>();
        model = new DefaultListModel();
        this.serverConnection = serverConnection;
        idUser = id;
        memberIdGroup = new ArrayList<Integer>();
        memberPseudoRoom = new ArrayList<String>();
        memberIdUser = new ArrayList<Integer>();

    }


    public void addContact(List<User> contact, User newContact) {
        contact.add(newContact);
    }


    public void removeContact(List<User>  contact, User oldContact) {
        contact.remove(oldContact);
    }

    public List<User> getContact() {
        return contact;
    }

    public void setContact(List<User> contact) {
        this.contact = contact;
    }

    public void getDefaultListModel (DefaultListModel model) throws SQLException {
        serverConnection.giveGroups(userGroup);
        for(int i = 0; i <userGroup.size();i++ ){
            memberIdGroup = serverConnection.giveGroupUsers(userGroup.get(i).getIdGroup());
            if(serverConnection.verifUserGroup(idUser, memberIdGroup)){
                model.addElement(userGroup.get(i).getNomGroupe()+"#"+userGroup.get(i).getIdGroup());
            }
        }
    }
    public void showMembersGroup(JList listMemberGroup, DefaultListModel modelParticipantGroup ){
        String tempIdGrp;
        List<Integer> groupUserId; // les id de tout les membres grp
        List<String> groupUserPseudo;

        modelParticipantGroup.clear();
        tempIdGrp = listMemberGroup.getSelectedValue().toString(); // Récupère le nom du groupe sélectionné
        tempIdGrp =  tempIdGrp.substring(tempIdGrp.indexOf("#")+1,tempIdGrp.length()); //Dans le nom sélectionné récupère l'id du groupe
        try {
            groupUserId = serverConnection.giveGroupUsers(Integer.parseInt(tempIdGrp)); //Récupère tous les id des membres du groupe sélectionné
            groupUserPseudo = serverConnection.userIdToPseudo(groupUserId); // Récupère les Pseudo des membres du groupe sélectionné

            for(int i = 0 ; i < groupUserPseudo.size(); i++){
                modelParticipantGroup.addElement(groupUserPseudo.get(i)+"#"+ groupUserId.get(i)); // Ajoute les pseudo à modelParticipantGroup
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addListUserRoom(DefaultListModel usersMemberRoom) throws SQLException {
        memberPseudoRoom = serverConnection.allPseudoFromBase();
        memberIdUser = serverConnection.allIdUserFromBase();

        for(int i = 0; i< memberPseudoRoom.size() ; i++){
            usersMemberRoom.addElement(memberPseudoRoom.get(i) +"#"+ memberIdUser.get(i));
        }
    }

    public void createGroup(String groupName , List<String> groupMember ) throws SQLException {
        Connection conn;



        List<Integer> idMemberNewGroup = new ArrayList<Integer>();
        idMemberNewGroup = idFromPseudo(groupMember);

        List<String> pseudoMemberNewGroup = new ArrayList<String>();
        pseudoMemberNewGroup = stringFromPseudo(groupMember);

       // serverConnection.insertIntoGroupes(idMemberNewGroup,pseudoMemberNewGroup);




    }

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

    public List<String> stringFromPseudo( List<String> groupMember ){
        List<String> pseudoMemberNewGroup = new ArrayList<String>();

        String tempPseudoMemberGrp;

        for(int i = 0; i < groupMember.size() ; i++){
            tempPseudoMemberGrp = groupMember.get(i);
            tempPseudoMemberGrp =  tempPseudoMemberGrp.substring(0,tempPseudoMemberGrp.indexOf("#")-1);
            pseudoMemberNewGroup.add(tempPseudoMemberGrp);
        }
        return pseudoMemberNewGroup;
    }






}
