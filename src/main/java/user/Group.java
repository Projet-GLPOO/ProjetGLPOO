package user;

import java.util.List;


public class Group {

    private List<User> memberGroup;
    private int idGroup;
    private String nomGroupe;


    /**
     *
     * @param nomGroupe
     * @param idGroup
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
