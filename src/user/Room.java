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
    ServerConnection server;
    int idUser;
    List<Integer>  memberIdGroup;

    public Room(int id,ServerConnection server ){
        contact = new ArrayList<User>();
        userGroup = new ArrayList<Group>();
        model = new DefaultListModel();
        this.server = server;
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
        server.giveGroups(userGroup);
        for(int i = 0; i <userGroup.size();i++ ){
            memberIdGroup = server.giveGroupUsers(userGroup.get(i).getIdGroup());
            if( server.verifUserGroup(idUser , memberIdGroup) == true){
                model.addElement(userGroup.get(i).getNomGroupe());
                System.out.println(userGroup.get(i).getNomGroupe());
            }
        }
    }




}
