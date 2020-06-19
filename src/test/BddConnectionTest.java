package test;

import bdd.BddConnection;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

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
    }

    @Test
    public void giveGroups() {
    }

    @Test
    public void giveGroupUsers() {
    }

    @Test
    public void verifUserGroup() {
    }

    @Test
    public void userIdToPseudo() {
    }

    @Test
    public void allPseudoFromBase() {
    }

    @Test
    public void allIdUserFromBase() {
    }

    @Test
    public void incrementGroupId() {
    }

    @Test
    public void givePseudoFromId() {
    }
}