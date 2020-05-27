package user;

import java.util.List;
import java.util.UUID;

public class Group {

    private List<User> memberGroup;
    private int idGroup;
    private String nomGroupe;
    private String dateCreation;


    public Group(List<User> memberGroup , int idGroup ) {
        this.memberGroup = memberGroup;
        this.idGroup = idGroup;
    }

    public List<User> getMemberGroup() {
        return memberGroup;
    }

    public void setMemberGroup(List<User> memberGroup) {
        this.memberGroup = memberGroup;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }


}
