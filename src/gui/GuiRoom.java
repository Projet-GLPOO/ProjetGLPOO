package gui;

import user.Room;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuiRoom {

    private JFrame frame;
    private JTextField txtSalonDeDiscussion;
    private Room room;



    /**************************************************************************/
    private List<User> listTest;

    public void temp() {
        listTest = new ArrayList<User>();
        listTest = room.getContact();
        User userTest1 =  new User("rapido", "razmo", UUID.randomUUID(), null);
        User userTest2 =  new User("razmo", "rapido", UUID.randomUUID(), null);
        room.addContact(listTest,userTest1);
        //frefriefjeifjqezlfjqklfjdqklfqjfkldjfsd//
    }




    /******************************************************************************/


    public void launch() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GuiRoom window = new GuiRoom();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GuiRoom() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1012, 751);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JList listContactUser = new JList();
        listContactUser.setBounds(12, 337, 187, -305);
        frame.getContentPane().add(listContactUser);


        JTextArea textArea = new JTextArea();
        textArea.setBounds(12, 615, 766, 64);
        frame.getContentPane().add(textArea);

        JButton btnNewButton = new JButton("Envoyer");
        btnNewButton.setBounds(824, 626, 97, 25);
        frame.getContentPane().add(btnNewButton);

        JList listMemberGroup = new JList();
        listMemberGroup.setBounds(825, 586, 125, -549);
        frame.getContentPane().add(listMemberGroup);

        JTextArea textArea_1 = new JTextArea();
        textArea_1.setBounds(240, 136, 538, 418);
        frame.getContentPane().add(textArea_1);

        txtSalonDeDiscussion = new JTextField();
        txtSalonDeDiscussion.setText("Salon de discussion");
        txtSalonDeDiscussion.setBounds(371, 43, 285, 25);
        txtSalonDeDiscussion.setEditable(false);
        frame.getContentPane().add(txtSalonDeDiscussion);
        txtSalonDeDiscussion.setColumns(10);

        JList listContactGroup = new JList();
        listContactGroup.setBounds(12, 586, 187, -205);
        frame.getContentPane().add(listContactGroup);

    }

}
