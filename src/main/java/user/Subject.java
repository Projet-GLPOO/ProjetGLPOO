package user;

public interface Subject {
    /**
     *
     * @param o
     */
    void registerObserver(Observer o);

    /**
     *
     * @param o
     */
    void removeObserver(Observer o);

    /**
     *
     * @param message
     */
    void notifyObservers(Message message);


}
