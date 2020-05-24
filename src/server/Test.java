package server;
import java.sql.*;

public class Test {

    Connection conn;

    public void zlatan(){
        System.out.println("dfdfdfd");
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            try{
                ResultSet r = stmt.executeQuery("Select * from Membres;");
                while(r.next()){
                    String membre = r.getString("Prenom");
                    System.out.println(membre);
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
    }
}

