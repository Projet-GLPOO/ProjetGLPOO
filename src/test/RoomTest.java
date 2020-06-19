package test;

import bdd.BddConnection;
import org.junit.Test;
import user.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoomTest {






    @Test
    public void idFromPseudo() throws SQLException {
        List<Integer> testIdMemberNewGroup = new ArrayList<Integer>();
        List<String> testGroupMember= new ArrayList<String>();
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        Room testroom = new Room(4,testbdd);
        testGroupMember.add("test#1");
        testGroupMember.add("test#2");
        testGroupMember.add("test#3");
        testIdMemberNewGroup.add(1);
        testIdMemberNewGroup.add(2);
        testIdMemberNewGroup.add(3);
        assertEquals(testIdMemberNewGroup,testroom.idFromPseudo(testGroupMember));


    }

    @Test
    public void stringFromPseudo() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        Room testroom = new Room(4,testbdd);


    }

    @Test
    public void getIdSelectedGroup() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        Room testroom = new Room(4,testbdd);

    }

    @Test
    public void idToPseudo() throws SQLException {
        BddConnection testbdd = new BddConnection();
        testbdd.launchBddConnection();
        Room testroom = new Room(4,testbdd);
        assertEquals("a",testroom.idToPseudo(4));
    }
}