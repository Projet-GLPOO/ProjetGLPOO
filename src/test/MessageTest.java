package test;

import org.junit.Test;
import user.Message;

import static org.junit.Assert.*;

public class MessageTest {

    int testUserId = 1;
    int testGroupId = 2;
    String testMessage = " je suis un test";
    String testPostDate = "19/06/2020";

    @Test
    public void getUserID() {
        Message test = new Message(testUserId, testGroupId, testMessage, testPostDate);
        assertEquals(testUserId,test.getUserID());
    }

    @Test
    public void getGroupID() {
        Message test = new Message(testUserId, testGroupId, testMessage, testPostDate);
        assertEquals(testGroupId,test.getGroupID());
    }

    @Test
    public void getMessage() {
        Message test = new Message(testUserId, testGroupId, testMessage, testPostDate);
        assertEquals(testMessage,test.getMessage());
    }

    @Test
    public void getPostDate() {
        Message test = new Message(testUserId, testGroupId, testMessage, testPostDate);
        assertEquals(testPostDate,test.getPostDate());
    }
}