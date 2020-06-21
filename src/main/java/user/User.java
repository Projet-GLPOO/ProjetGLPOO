package user;

import java.io.Serializable;


public class User implements Serializable {
    private String pseudo;
    private String mdp;
    private int id;


    /**
     *
     * @param pseudo pseudo de l'utilisateur
     * @param mdp mot de passe de l'utilisateur
     */
    public User(String pseudo, String mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }


    /**
     *
     * @return pseudo
     */
    public String getPseudo() {
        return pseudo;
    }


    /**
     *
     * @param pseudo Pseudo de l'utilisateur
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }


    /**
     *
     * @return mdp
     */
    public String getMdp() {
        return mdp;
    }


    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     *
     * @param id identifiant de l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }
}
