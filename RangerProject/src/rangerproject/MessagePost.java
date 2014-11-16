/*
 * This is the reply class.  I modified the types and wrapped them in the 
 * property wrappers so they can be interacted with.
 */
package rangerproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dsestak, A.Bunk
 */
public class MessagePost {
 
    //list variables we need to wrap these in property wrappers
    private final IntegerProperty postID;
    private final StringProperty replyTitle;
    private final StringProperty replyCreator;
    private final StringProperty replyMessage;
    private final ObjectProperty<Date> replyDate;
    private final IntegerProperty topicID;
 
    //no argument contructor
    public MessagePost () {
        Rangers ranger = new Rangers();
        java.util.Date date = new java.util.Date();
        postID = new SimpleIntegerProperty();
        replyTitle = new SimpleStringProperty();
        replyCreator = new SimpleStringProperty(ranger.getUsername());
        replyMessage = new SimpleStringProperty();
        replyDate = new SimpleObjectProperty(date);
        topicID = new SimpleIntegerProperty();
    }
    
    //full constructor
    public MessagePost (int postID, String replyTitle, String replyCreator, 
            String replyMessage, Date date, int topicID) {
        
        
        this.postID = new SimpleIntegerProperty(postID);
        this.replyTitle = new SimpleStringProperty(replyTitle);
        this.replyCreator = new SimpleStringProperty(replyCreator);
        this.replyMessage = new SimpleStringProperty(replyMessage);
        this.replyDate = new SimpleObjectProperty(date);
        this.topicID = new SimpleIntegerProperty(topicID);
        
    }
    
    //output data to String 
    public String toString () {
        
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
        java.util.Date date = new java.util.Date();
        
        return "PostID = " + postID + " Title = " + replyTitle + " creator = " + replyCreator + " Message = "
                + replyMessage + " date = " + dateFormat.format(replyDate);
                
    }

    //getters and setters modified 
    public int getPostID () {
        return postID.get();
    }
    
    public void setPostID(int postID) {
        this.postID.set(postID);
    }
    
    public int getTopicID () {
        return topicID.get();
    }
    
    public void setTopicID(int topicID) {
        this.topicID.set(topicID);
    }

    public IntegerProperty getPostIDProperty() {
        return postID;
    }
    
    public String getReplyTitle() {
        return replyTitle.get();
    }
    
    public void setReplyTitle(String replyTitle) {
        this.replyTitle.set(replyTitle);
    }

    public StringProperty getReplyTitleProperty() {
        return replyTitle;
    }
    
    public String getReplyCreator () {
        return replyCreator.get();
    }
    
    public void setReplyCreator(String replyCreator) {
        this.replyCreator.set(replyCreator);
    }

    public StringProperty getReplyCreatorProperty() {
        return replyCreator;
    }
    
    public String getReplyMessage() {
        return replyMessage.get();
    }
    
    public void setReplyMessage(String replyMessage) {
        this.replyMessage.set(replyMessage);
    }

    public StringProperty getReplyMessageProperty() {
        return replyMessage;
    }
    
    public Date getReplyDate() {
        return replyDate.get();
    }
    
    public void setReplyDate(Date replyDate) {
        this.replyDate.set(replyDate);
    }

    public ObjectProperty<Date> getReplyDateProperty() {
        return replyDate;
    }
    
    
    
    
    
}
