package user;

import java.io.Serializable;


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
