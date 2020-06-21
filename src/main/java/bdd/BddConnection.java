package bdd;
import user.Group;
import user.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class BddConnection {

    private Connection conn;

    /**
     * Lance la connection à la BDD
     * @throws SQLException sql erreur
     */
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

    /**
     * Ferme la connection à la BDD
     */
    public void closeBddConnection(){
        if(conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Vérifie si le pseudo et le mot de passe correspondent à ceux entrés dans la base de données
     * @param userName Le nom de l'utilisateur
     * @param password Le mot de passe de l'utilisateur
     * @return true/false
     */
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


    /**
     * Rétourne L'id du pseudo correspondant
     * @param pseudo Le pseudo de l'utilisateur
     * @return id
     */
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



    /**
     * Récupère tous les groupes existant
     * @param listGroup La liste des groupes
     * @return listGroup
     * @throws SQLException
     */
    public List<Group> giveGroups(List<Group> listGroup) throws SQLException {
        int groupeid;
        String nomGroupe;
        Group group;

        PreparedStatement stmt = conn.prepareStatement("Select * from Groupes");

        try{
            ResultSet r = stmt.executeQuery();

            while(r.next()){

                groupeid = r.getInt("GroupeID");
                nomGroupe = r.getString("NomGroupe");
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


    /**
     * Récupère tous les ID utilisateurs d'un groupe
     * @param groupId L'id du groupe
     * @return groupMembers
     * @throws SQLException erreur sql
     */
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

    /**
     * Vérifie si un utilisateur appartient à un groupe
     * @param userId L'id de l'utilisateur
     * @param groupMembers Les id membres du groupes
     * @return true/false
     */
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

    /**
     * Retoune une liste de pseudo à partir d'une liste d'id
     * @param groupMembers La liste des id des membres du groupe
     * @return listPseudo
     * @throws SQLException erreur sql
     */
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

    /**
     * Rétoune tous les pseudo de la Base de données
     * @return listPseudo
     * @throws SQLException erreur sql
     */
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

    /**
     * Rétoune tous les id de la base de données
     * @return listIdUser
     * @throws SQLException erreur sql
     */
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

    /**
     * Insert dans la base de données le nouveau groupe
     * @param idGroup L'id du groupe
     * @param groupName Le nom du groupe
     * @throws SQLException erreur sql
     */
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

    /**
     * Insert dans la base de données les participants d'un groupe
     * @param idGroup l'id du groupe
     * @param listUserIdNewGroup La liste des id des membre du groupe
     * @throws SQLException erreur sql
     */
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

    /**
     * Récupère l'id d'un nouveau groupe
     * @return newId
     * @throws SQLException erreur sql
     */
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

    /**
     * Récupère tout les message de la base de données appartenant à un groupe et l'ajoute à la liste messageList
     * @param groupId L'id du groupe
     * @param messageList Liste des messages de la base de données
     */
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


    /**
     * Envoie les messages de l'utilisateur pour le groupe sélectionné
     * @param userId L'id de l'utilisateur
     * @param groupId L'id du groupe
     * @param messageList La liste des messages de l'utilisateur dans un groupe
     */
    public void giveUserMessagesfromGroupSelected(int userId, int groupId, List<Message>messageList) {

        String text;
        String date;
        Message message;
        try {
            messageList.clear();
            PreparedStatement stmt = conn.prepareStatement("Select * from Messages WHERE Groupeid = ? AND utilisateurId = ? order by dateposte desc");
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



    /**
     * Permet de supprimer le message de l'utilisateur
     * @param message Le message
     */
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

    /**
     * Retoune le pseudo à partir d'un id
     * @param userid L'id de l'utilisateur
     * @return userPseudo
     * @throws SQLException erreur sql
     */
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

    /**
     * Insert dans la base de données les message écrits par les utilisateurs
     * @param userid L'id de l'utilisateur
     * @param selectedGroupId L'id du groupe sélectionner
     * @param message Le message (textuel)
     * @param timeStamp La date de l'envoie du message
     * @throws SQLException erreur sql
     */
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

    /**
     * Retourne le nombre de message écrit par l'utilisateur dans un groupe
     * @param userId L'id de l'utilisateur
     * @param groupId L'id du groupe
     * @return numberOfMessage
     * @throws SQLException erreur sql
     */
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