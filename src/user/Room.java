package user;

import server.ServerConnection;

import javax.swing.*;
import java.sql.Connection;
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

    private List<Message> messageList;

    public Room(int id,ServerConnection serverConnection ){
        contact = new ArrayList<User>();
        userGroup = new ArrayList<Group>();
        model = new DefaultListModel();
        this.serverConnection = serverConnection;
        idUser = id;
        memberIdGroup = new ArrayList<Integer>();
        memberPseudoRoom = new ArrayList<String>();
        memberIdUser = new ArrayList<Integer>();
        messageList = new ArrayList<Message>();
    }



    //Récupère les groupes de l'utilisateur onnecté et les ajoute à une defaultListModel qui sera affiché dans la room
    public void getDefaultListModel (DefaultListModel model) throws SQLException {
        serverConnection.giveGroups(userGroup);
        for(int i = 0; i <userGroup.size();i++ ){
            memberIdGroup = serverConnection.giveGroupUsers(userGroup.get(i).getIdGroup());
            if(serverConnection.verifUserGroup(idUser, memberIdGroup)){
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
            groupUserId = serverConnection.giveGroupUsers(Integer.parseInt(tempIdGrp)); //Récupère tous les id des membres du groupe sélectionné
            groupUserPseudo = serverConnection.userIdToPseudo(groupUserId); // Récupère les Pseudo des membres du groupe sélectionné

            for(int i = 0 ; i < groupUserPseudo.size(); i++){
                modelParticipantGroup.addElement(groupUserPseudo.get(i)+"#"+ groupUserId.get(i)); // Ajoute les pseudo à modelParticipantGroup
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    //Permet d'ajouter tous les utilisateurs présents dans la base de données (simplifie la création de groupe)
    public void addListUserRoom(DefaultListModel usersMemberRoom) throws SQLException {
        memberPseudoRoom = serverConnection.allPseudoFromBase();
        memberIdUser = serverConnection.allIdUserFromBase();

        for(int i = 0; i< memberPseudoRoom.size() ; i++){
            usersMemberRoom.addElement(memberPseudoRoom.get(i) +"#"+ memberIdUser.get(i));
        }
    }


    //Permet de créer un groupe
    public void createGroup(String groupName , List<String> groupMember ) throws SQLException {
        Connection conn;



        List<Integer> idMemberNewGroup = new ArrayList<Integer>();
        idMemberNewGroup = idFromPseudo(groupMember);

        List<String> pseudoMemberNewGroup = new ArrayList<String>();
        pseudoMemberNewGroup = stringFromPseudo(groupMember);

       // serverConnection.insertIntoGroupes(idMemberNewGroup,pseudoMemberNewGroup);

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
            tempPseudoMemberGrp =  tempPseudoMemberGrp.substring(0,tempPseudoMemberGrp.indexOf("#")-1);
            pseudoMemberNewGroup.add(tempPseudoMemberGrp);
        }
        return pseudoMemberNewGroup;
    }

    //Permet de récupérer les messages du groupe sélectionner dans GuiRoom
    public void getGroupMessages(int idGroupe, List<Message> messagesList){

        serverConnection.giveGroupMessages(idGroupe, messagesList);
    }


    //Permet de récupérer l'Id du groupe sélectioner dans GuiRoom
    public int getIdSelectedGroup(JList listMemberGroup){
        String tempIdGrp;
        tempIdGrp = listMemberGroup.getSelectedValue().toString(); // Récupère le nom du groupe sélectionné
        tempIdGrp =  tempIdGrp.substring(tempIdGrp.indexOf("#")+1,tempIdGrp.length()); //Dans le nom sélectionné récupère l'id du groupe

        return Integer.parseInt(tempIdGrp);
    }


    public String idToPseudo(int idUser){

        try {
            return serverConnection.givePseudoFromId(idUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
