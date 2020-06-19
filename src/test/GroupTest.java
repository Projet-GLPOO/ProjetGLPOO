package test;

import org.junit.Test;
import user.Group;
import user.User;

import java.util.List;

import static org.junit.Assert.*;

public class GroupTest {
    private String testNomGroupe;
    private int testIdGroup;
    private List<User> listTest;

    @Test
    public void getMemberGroup() {
        Group test = new Group(testNomGroupe, testIdGroup);
        assertEquals(listTest,test.getMemberGroup() );
    }

    @Test
    public void getIdGroup() {
        testNomGroupe = "test";
        testIdGroup = 1;
        Group test = new Group(testNomGroupe, testIdGroup);
        assertEquals(testIdGroup,test.getIdGroup());
    }

    @Test
    public void getNomGroupe() {
        testNomGroupe = "test";
        testIdGroup = 1;
        Group test = new Group(testNomGroupe, testIdGroup);
        assertEquals(testNomGroupe,test.getNomGroupe());
    }
}