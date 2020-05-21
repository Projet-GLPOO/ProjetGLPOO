package user;

import java.util.List;
import java.util.UUID;

public class User {
    private String pseudo;
    private String mdp;
    private UUID id;
    private List<Group> groups;




    public User(String pseudo, String mdp, UUID id, List<Group> groups ) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.id = id;
        this.groups= groups;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }





}
