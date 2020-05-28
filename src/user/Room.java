package user;

import server.ServerConnection;

import javax.swing.*;
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

    public Room(int id,ServerConnection serverConnection ){
        contact = new ArrayList<User>();
        userGroup = new ArrayList<Group>();
        model = new DefaultListModel();
        this.serverConnection = serverConnection;
        idUser = id;
        memberIdGroup = new ArrayList<Integer>();

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
                modelParticipantGroup.addElement(groupUserPseudo.get(i)); // Ajoute les pseudo à modelParticipantGroup
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
