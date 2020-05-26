package server;
import java.sql.*;

public class Test {

    Connection conn;

    public boolean zlatan(String userName, String password){
        System.out.println("dfdfdfd");
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/bdd_projet", "sa", "glpoo");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            try{
                ResultSet r = stmt.executeQuery("Select * from Utilisateurs WHERE Pseudonyme = 'Lostvayne';");
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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(conn !=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}

