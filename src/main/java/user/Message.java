package user;

import java.io.Serializable;

public class Message implements Serializable {

    User user;
    int userId;
    int groupId;
    String message;
    String postDate;

    /**
     * Constructeur de Message
     * @param userId Id de l'utilisateur du message
     * @param groupId Id du groupe du message
     * @param message Le message (textuel)
     * @param postDate La date du message
     */
    public Message(int userId, int groupId, String message, String postDate){
        this.userId = userId;
        this.groupId = groupId;
        this.message = message;
        this.postDate = postDate;
    }

    /**
     * Constructeur de Message
     * @param user L'expéditeur du message
     * @param groupId L'id du groupe du message
     * @param message le message (textuel)
     * @param postDate La date du message
     */
    public Message(User user, int groupId, String message, String postDate){
        this.user = user;
        this.groupId = groupId;
        this.message = message;
        this.postDate = postDate;
    }

    /**
     *
     * @return userId
     */
    public int getUserID() {
        return userId;
    }

    /**
     *
     * @return groupId
     */

    public int getGroupID() {
        return groupId;
    }

    /**
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return postDate
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     *
     * @return user
     */
    public User getUser(){
        return user;
    }
}
