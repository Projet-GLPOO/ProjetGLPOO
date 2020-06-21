package user;

public interface Subject {
    /**
     *
     * @param o Observer
     */
    void registerObserver(Observer o);

    /**
     *
     * @param message Message
     */
    void notifyObservers(Message message);


}
