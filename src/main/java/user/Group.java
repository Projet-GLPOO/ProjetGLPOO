package user;

import java.util.List;


public class Group {

    private List<User> memberGroup;
    private int idGroup;
    private String nomGroupe;


    /**
     * Constructeur de Group
     * @param nomGroupe Le nom du groupe
     * @param idGroup L'id du groupe
     */
    public Group(String nomGroupe , int idGroup ) {
        this.nomGroupe = nomGroupe;
        this.idGroup = idGroup;
    }

    /**
     *
     * @return memberGroup
     */
    public List<User> getMemberGroup() {
        return memberGroup;
    }


    /**
     *
     * @return idGroup
     */
    public int getIdGroup() {
        return idGroup;
    }

    /**
     *
     * @return nomGroupe
     */
    public String getNomGroupe() {
        return nomGroupe;
    }
}
