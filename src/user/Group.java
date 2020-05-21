package user;

import java.util.List;
import java.util.UUID;

public class Group {

    private List<User> memberGroup;
    private UUID idGroup;


    public Group(List<User> memberGroup , UUID idGroup ) {
        this.memberGroup = memberGroup;
        this.idGroup = idGroup;
    }

    public List<User> getMemberGroup() {
        return memberGroup;
    }

    public void setMemberGroup(List<User> memberGroup) {
        this.memberGroup = memberGroup;
    }

    public UUID getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(UUID idGroup) {
        this.idGroup = idGroup;
    }


}
