package user;

public interface Observer{
    /**
     *
     * @param message message
     */
    void send(Message message);

    /**
     *
     * @param idGroup identifiant du groupe
     */
    void sendIdGroup(int idGroup);
}
