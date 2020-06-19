package test;

import bdd.BddConnection;
import org.junit.Test;
import user.Group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class BddConnectionTest {
    private String testName;
    private String testPassword;
    @Test
    public void loginConnection() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        testName ="a";
        testPassword = "a";
        assertTrue(testbdd.loginConnection(testName,testPassword));
    }

    @Test
    public void giveId() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        assertEquals(4,testbdd.giveId("a"));
    }
    @Test
    public void giveGroups() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        List<Group> testListGroup2 = new ArrayList<>();
        assertEquals(testListGroup2,testbdd.giveGroups(testListGroup2));
        assertNotNull(testbdd.giveGroups(testListGroup2));
    }

    @Test
    public void giveGroupUsers() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        List<Integer> groupMembers = new ArrayList<Integer>();
        groupMembers.add(1);
        groupMembers.add(3);
        assertEquals(groupMembers,testbdd.giveGroupUsers(4));
        assertNotNull(testbdd.giveGroupUsers(4));
    }

    @Test
    public void verifUserGroup() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        List<Integer> testGroupMembers = new ArrayList<Integer>();
        testGroupMembers.add(1);
        assertTrue(testbdd.verifUserGroup(1,testGroupMembers));
        assertFalse(testbdd.verifUserGroup(2,testGroupMembers));
        assertNotNull(testbdd.verifUserGroup(1,testGroupMembers));
    }

    @Test
    public void userIdToPseudo() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        List<Integer> testIdGroupMembers = new ArrayList<Integer>();
        testIdGroupMembers.add(4);
        List<String> testGroupMembers = new ArrayList<String>();
        testGroupMembers.add("a");
        assertEquals(testGroupMembers, testbdd.userIdToPseudo(testIdGroupMembers));
        assertNotNull(testbdd.userIdToPseudo(testIdGroupMembers));

    }

    @Test
    public void allPseudoFromBase() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        List<String> testPseudoFromBase = new ArrayList<String>();
        testPseudoFromBase.add("Lostvayne");
        testPseudoFromBase.add("Cherrval");
        testPseudoFromBase.add("Razorga");
        testPseudoFromBase.add("a");
        assertEquals(testPseudoFromBase,testbdd.allPseudoFromBase());
        assertNotNull(testbdd.allPseudoFromBase());

    }

    @Test
    public void allIdUserFromBase() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        List<Integer> testIdFromBase = new ArrayList<Integer>();
        testIdFromBase.add(1);
        testIdFromBase.add(2);
        testIdFromBase.add(3);
        testIdFromBase.add(4);
        assertEquals(testIdFromBase,testbdd.allIdUserFromBase());
        assertNotNull(testbdd.allIdUserFromBase());
    }
    @Test
    public void incrementGroupId() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        assertNotNull(testbdd.incrementGroupId());
    }

    @Test
    public void givePseudoFromId() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        assertEquals("a",testbdd.givePseudoFromId(4));
    }
}