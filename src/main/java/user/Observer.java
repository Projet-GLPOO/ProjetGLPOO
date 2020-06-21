package user;

public interface Observer{
    /**
     *
     * @param message
     */
    void send(Message message);

    /**
     *
     * @param idGroup
     */
    void sendIdGroup(int idGroup);
}
