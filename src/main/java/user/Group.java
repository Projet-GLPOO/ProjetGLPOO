package user;

import java.util.List;
import java.util.UUID;

public class Group {

    private List<User> memberGroup;
    private int idGroup;
    private String nomGroupe;
    private String dateCreation;



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
     * @return
     */
    public List<User> getMemberGroup() {
        return memberGroup;
    }

    /**
     *
     * @param memberGroup
     */

    public void setMemberGroup(List<User> memberGroup) {
        this.memberGroup = memberGroup;
    }

    /**
     *
     * @return
     */
    public int getIdGroup() {
        return idGroup;
    }

    /**
     *
     * @param idGroup
     */

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    /**
     *
     * @return
     */
    public String getNomGroupe() {
        return nomGroupe;
    }

}
