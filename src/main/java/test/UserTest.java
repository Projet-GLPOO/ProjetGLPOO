package test;

import user.User;

import static org.junit.Assert.*;

public class UserTest {

    private String testPseudo;
    private String testMdp;
    private int testId;

    @org.junit.Test
    public void getPseudo() {
        testPseudo = "JeanPierre";
        testMdp = "ABCDE";
       User test = new User(testPseudo, testMdp);
       assertEquals(testPseudo,test.getPseudo());
    }

    @org.junit.Test
    public void getMdp() {
        testPseudo = "JeanPierre";
        testMdp = "ABCDE";
        User test = new User(testPseudo, testMdp);
        assertEquals(testMdp,test.getMdp());
    }

    @org.junit.Test
    public void getId() {
        testId = 5;
        testPseudo = "JeanPierre";
        testMdp = "ABCDE";
        User test = new User(testPseudo, testMdp);
        test.setId(testId);
        assertEquals(testId,test.getId());
    }


}