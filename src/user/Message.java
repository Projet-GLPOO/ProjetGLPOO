package user;

public class Message {

    int userId;
    int groupId;
    String message;
    String postDate;

    /**
     *
     * @param userId
     * @param groupId
     * @param message
     * @param postDate
     */
    public Message(int userId, int groupId, String message, String postDate){
        this.userId = userId;
        this.groupId = groupId;
        this.message = message;
        this.postDate = postDate;
    }

    /**
     *
     * @return
     */
    public int getUserID() {
        return userId;
    }

    /**
     *
     * @return
     */

    public int getGroupID() {
        return groupId;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return
     */
    public String getPostDate() {
        return postDate;
    }
}
