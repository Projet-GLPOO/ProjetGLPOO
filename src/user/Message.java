package user;

public class Message {

    int userId;
    int groupId;
    String message;
    String postDate;

    public Message(int userId, int groupId, String message, String postDate){
        this.userId = userId;
        this.groupId = groupId;
        this.message = message;
        this.postDate = postDate;
    }

    public int getUserID() {
        return userId;
    }

    public int getGroupID() {
        return groupId;
    }

    public String getMessage() {
        return message;
    }

    public String getPostDate() {
        return postDate;
    }
}
