package bdd;
import user.Group;
import user.Message;
import user.User;

import java.awt.print.PrinterAbortException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class BddConnection {

    private Connection conn;

    public void launchBddConnection() throws SQLException {

        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/bdd_projet", "sa", "glpoo");
            conn.setAutoCommit(false);
        }
        catch(Exception e){
            conn.rollback();
        }
    }

    public void closeBddConnection(){
        if(conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean loginConnection(String userName, String password){

        try {
            PreparedStatement stmt = conn.prepareStatement("Select * from Utilisateurs WHERE Pseudonyme = ?");
            try{
                stmt.setString(1, userName);
                ResultSet r = stmt.executeQuery();
                while(r.next()){
                    String mdp = r.getString("Mdp");
                    String pseudo = r.getString("Pseudonyme");
                    System.out.println(mdp + " " + pseudo + "\n");
                    if(userName.equals(pseudo) && password.equals(mdp))
                        return true;
                    else
                        return false;
                }
                conn.commit();
            }
            catch(Exception e){
                conn.rollback();
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public int giveId(String pseudo) {
        int id = 0;
        try {
            PreparedStatement stmt = conn.prepareStatement("Select * from Utilisateurs WHERE Pseudonyme = ?");
            try{
                stmt.setString(1, pseudo);
                ResultSet r = stmt.executeQuery();
                while(r.next()){
                    id = r.getInt("utilisateurid");
                    return(id);
                }
                conn.commit();
            }
            catch(Exception e){
                conn.rollback();
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //récupère tous les groupes existant (david+rayan)

    public List<Group> giveGroups(List<Group> listGroup) throws SQLException {
        int groupeid;
        String nomGroupe;
        //String dateCreation;
        Group group;
        //List<User> roomMembers = new ArrayList<User>();

        PreparedStatement stmt = conn.prepareStatement("Select * from Groupes");
        //PreparedStatement stmt2 = conn.prepareStatement("Select * from ParticipantsGroupe");

        try{
            ResultSet r = stmt.executeQuery();
            //ResultSet r2 = stmt2.executeQuery();

            while(r.next() /*&& r2.next()*/){

                groupeid = r.getInt("GroupeID");
                nomGroupe = r.getString("NomGroupe");
                //dateCreation = r.getString("DateCreation");

                group = new Group(nomGroupe, groupeid);
                listGroup.add(group);

            }
                conn.commit();
            }
            catch(Exception e){
                conn.rollback();
            }
        stmt.close();

        return listGroup;
    }

    //Récupère tous les ID utilisateurs d'un groupe (david+rayan)

    public List<Integer> giveGroupUsers(int groupId) throws SQLException{
        int participantID;
        List<Integer> groupMembers = new ArrayList<Integer>();

        PreparedStatement stmt = conn.prepareStatement("Select * from ParticipantsGroupe where GroupeId = ?");

        try{
            stmt.setInt(1, groupId);
            ResultSet r = stmt.executeQuery();


            while(r.next()){

                participantID = r.getInt("UtilisateurId");
                groupMembers.add(participantID);
            }
            conn.commit();
        }
        catch(Exception e){
            conn.rollback();
        }
        stmt.close();

        return groupMembers;

    }

    /*TODO une fonction booleen qui prend en entré le user ID et la liste ID utilisateur précédente pour vérifier si l'utilisateur appartient au groupe (renvoie true si oui)*/

    public boolean verifUserGroup(int userId, List<Integer> groupMembers){
        ListIterator<Integer> it = groupMembers.listIterator();
        while (it.hasNext()){
            int currentUserId = it.next();
            if(currentUserId == userId){
                return true;
            }
        }
        return false;
    }


    //TODO une fonction qui prend en entré groupmembers(list INT) et qui renvoie les pseudo associés (pour affichage plus tard) (list String)

    public List<String> userIdToPseudo(List<Integer> groupMembers) throws SQLException {
        List<String> listPseudo = new ArrayList<String>();
        String userPseudo;


        for (Integer groupMember : groupMembers) {

            PreparedStatement stmt = conn.prepareStatement("Select * from Utilisateurs where UtilisateurId = ?");
            try {

                stmt.setInt(1, groupMember);

                ResultSet r = stmt.executeQuery();
                while (r.next()) {
                    userPseudo = r.getString("Pseudonyme");
                    listPseudo.add(userPseudo);
                }


                conn.commit();
            } catch (Exception e) {
                conn.rollback();
            }
            stmt.close();
        }
        return listPseudo;
    }

    public List<String> allPseudoFromBase() throws SQLException{
        List<String> listPseudo = new ArrayList<String>();
        String userPseudo;

        PreparedStatement stmt = conn.prepareStatement("Select * from Utilisateurs");
        try{
            ResultSet r = stmt.executeQuery();
            while(r.next()){
                userPseudo = r.getString("Pseudonyme");
                listPseudo.add(userPseudo);
            }
            conn.commit();
        }
        catch(Exception e){
            conn.rollback();
        }
        stmt.close();

        return listPseudo;
    }

    public List<Integer> allIdUserFromBase() throws SQLException{
        List<Integer> listIdUser = new ArrayList<Integer>();
        int userId;

        PreparedStatement stmt = conn.prepareStatement("Select * from Utilisateurs");
        try{
            ResultSet r = stmt.executeQuery();
            while(r.next()){
                userId = r.getInt("UtilisateurID");
                listIdUser.add(userId);
            }
            conn.commit();
        }
        catch(Exception e){
            conn.rollback();
        }
        stmt.close();

        return listIdUser;
    }

    public void insertIntoGroupes(int idGroup, String groupName) throws SQLException {
        PreparedStatement  stmt = conn.prepareStatement("Insert Into groupes values(?, ?, sysdate, null)");
        try{

            stmt.setInt(1, idGroup);
            stmt.setString(2, groupName);
            stmt.executeUpdate();

            conn.commit();
        }
        catch(Exception e){
            conn.rollback();
        }
        stmt.close();

    }

    public void insertIntoParticipantGroup( int idGroup , List<Integer> listUserIdNewGroup) throws SQLException{

        for (Integer integer : listUserIdNewGroup) {
            System.out.println(integer);
            PreparedStatement stmt = conn.prepareStatement("Insert Into PARTICIPANTSGROUPE values(?, ?)");
            try {
                stmt.setInt(1, integer);
                stmt.setInt(2, idGroup);
                stmt.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
            }
            stmt.close();
        }
    }

    public int incrementGroupId() throws SQLException {
        int newId = 0;

        PreparedStatement stmt = conn.prepareStatement("Select count(*) from groupes");

        try{
            ResultSet r = stmt.executeQuery();


            while(r.next()){
                newId = r.getInt("count(*)");
            }
            conn.commit();
        }
        catch(Exception e){
            conn.rollback();
        }
        stmt.close();
        newId += 1;
        return newId;
    }

    public void giveGroupMessages(int groupId, List<Message>messageList) {

        int utilisateurID;
        String text;
        String date;
        Message message;
        try {
            messageList.clear();
            PreparedStatement stmt = conn.prepareStatement("Select * from Messages WHERE Groupeid = ?");
            try {
                stmt.setInt(1, groupId);
                ResultSet r = stmt.executeQuery();
                while (r.next()) {
                    utilisateurID = r.getInt("UtilisateurID");
                    text = r.getString("message");
                    date = r.getString("datePoste");
                    message = new Message(utilisateurID, groupId, text, date);

                    messageList.add(message);
                }
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Envoie les messages de l'utilisateur pour  le groupe séelectionné
    public void giveUserMessagesfromGroupSelected(int userId, int groupId, List<Message>messageList) {

        String text;
        String date;
        Message message;
        try {
            messageList.clear();
            PreparedStatement stmt = conn.prepareStatement("Select * from Messages WHERE Groupeid = ? AND utilisateurId = ?");
            try {
                stmt.setInt(1, groupId);
                stmt.setInt(2, userId);
                ResultSet r = stmt.executeQuery();
                while (r.next()) {

                    text = r.getString("message");
                    date = r.getString("datePoste");
                    message = new Message(userId, groupId, text, date);

                    messageList.add(message);
                }
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Permet de supprimer le message de l'utilisateur
    public void deleteUserMessage(Message message) {

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Messages WHERE (utilisateurId = ? AND groupeid = ? AND message = ? AND dateposte = ?)");
            try {
                stmt.setInt(1, message.getUserID());
                stmt.setInt(2, message.getGroupID());
                stmt.setString(3, message.getMessage());
                stmt.setString(4, message.getPostDate());
                int r = stmt.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String givePseudoFromId(int userid) throws SQLException {

        String userPseudo = "null";

        PreparedStatement stmt = conn.prepareStatement("Select pseudonyme from Utilisateurs where UtilisateurId = ?");
        try{

            stmt.setInt(1, userid);

            ResultSet r = stmt.executeQuery();
            while(r.next()) {
                userPseudo = r.getString("Pseudonyme");
            }
            conn.commit();
        }
        catch(Exception e){
            conn.rollback();
        }
        stmt.close();

        return userPseudo;
    }

    public void sendMessageToBDD(int userid, int selectedGroupId, String message, String timeStamp) throws SQLException {

        PreparedStatement  stmt = conn.prepareStatement("Insert Into Messages values(?, ?, ?, sysdate)");
        try{
            stmt.setInt(1, userid);
            stmt.setInt(2, selectedGroupId);
            stmt.setString(3, message);
            stmt.executeUpdate();

            conn.commit();
        }
        catch(Exception e){
            conn.rollback();
        }
        stmt.close();

    }

    public int getMessageNumberOfUser(int userId, int groupId) throws SQLException {
        int numberOfMessage = 0;
        PreparedStatement stmt = conn.prepareStatement("Select count(*) from Messages where groupeid = ? AND UtilisateurId = ?");
        try{

            stmt.setInt(1, groupId);
            stmt.setInt(2, userId);

            ResultSet r = stmt.executeQuery();

            while(r.next()) {
                numberOfMessage = r.getInt("COUNT(*)");
            }
            conn.commit();
        }
        catch(Exception e){
            conn.rollback();
        }
        stmt.close();
        return numberOfMessage;
    }
}

