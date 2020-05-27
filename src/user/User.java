package user;

import java.util.List;

public class User {
    private String pseudo;
    private String mdp;
    private int id;



    public User(String pseudo, String mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    public User(){

    }

    public void addGroup(List<Group>  groups, Group newGroup) {
        groups.add(newGroup);
    }

    public void removeGroup(List<Group>  groups, Group oldGroup) {
        groups.remove(oldGroup);
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
