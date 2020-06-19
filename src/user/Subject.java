package user;

public interface Subject {
    /**
     *
     * @param o
     */
    public void registerObserver(Observer o);

    /**
     *
     * @param o
     */
    public void removeObserver(Observer o);

    /**
     *
     * @param message
     */
    public void notifyObservers(String message);
}
