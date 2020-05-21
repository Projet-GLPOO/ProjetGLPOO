package user;

import java.util.ArrayList;
import java.util.List;

public class Room {

    List<User> contact;

    public Room(){
        contact = new ArrayList<User>();

    }


    public void addContact(List<User> contact, User newContact) {
        contact.add(newContact);
    }


    public void removeContact(List<User>  contact, User oldContact) {
        contact.remove(oldContact);
    }

    public List<User> getContact() {
        return contact;
    }

    public void setContact(List<User> contact) {
        this.contact = contact;
    }
}
