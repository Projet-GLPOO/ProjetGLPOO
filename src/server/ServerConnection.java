package server;
import user.Group;
import user.User;

import java.awt.print.PrinterAbortException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServerConnection {

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



    public List<Group> giveGroups(List<Group> listGroup) throws SQLException {
        int groupeid;
        String nomGroupe;
        String dateCreation;
        Group group;
        List<User> roomMembers = new ArrayList<User>();

        PreparedStatement stmt = conn.prepareStatement("Select * from Groupes");
        PreparedStatement stmt2 = conn.prepareStatement("Select * from ParticipantsGroupe");

        try{
            ResultSet r = stmt.executeQuery();
            ResultSet r2 = stmt2.executeQuery();

            while(r.next() && r2.next()){

                groupeid = r.getInt("GroupeID");
                nomGroupe = r.getString("NomGroupe");
                dateCreation = r.getString("DateCreation");


            }
                conn.commit();
            }
            catch(Exception e){
                conn.rollback();
            }
            stmt.close();

        return listGroup;
    }
}

