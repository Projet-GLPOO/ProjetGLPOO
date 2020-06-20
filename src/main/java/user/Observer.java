package user;

import javax.swing.*;

public interface Observer{
    /**
     *
     * @param message
     */
    public void send(String message);
}
