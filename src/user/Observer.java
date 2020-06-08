package user;

import javax.swing.*;

public interface Observer{

    public void update(String message, JTextArea chatArea);
}
