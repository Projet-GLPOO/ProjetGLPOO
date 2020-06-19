package user;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String pseudo;
    private String mdp;
    private int id;


    /**
     *
     * @param pseudo
     * @param mdp
     */
    public User(String pseudo, String mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }



    /**
     *
     * @param groups
     * @param newGroup
     */
    public void addGroup(List<Group>  groups, Group newGroup) {
        groups.add(newGroup);
    }

    /**
     *
     * @param groups
     * @param oldGroup
     */
    public void removeGroup(List<Group>  groups, Group oldGroup) {
        groups.remove(oldGroup);
    }

    /**
     *
     * @return
     */

    public String getPseudo() {
        return pseudo;
    }

    /**
     *
     * @param pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     *
     * @return
     */
    public String getMdp() {
        return mdp;
    }

    /**
     *
     * @param mdp
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}
