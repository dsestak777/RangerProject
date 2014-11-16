/*
 * This is the main topic class.
 */
package rangerproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class MessageTopic {
    
    //list instance variables in Property wrappers so they can be used by JavaFX
    private final IntegerProperty postID;
    private final StringProperty postTitle;
    private final StringProperty postCreator;
    private final IntegerProperty postCreatorID;
    private final StringProperty postMessage;
    private final IntegerProperty postRating;
    private final IntegerProperty noRatings;
    private final ObjectProperty<Date> postDate; 
    
   
    
    //no argument constructor
    public MessageTopic () {
        // modified this so as to allow the user to auto set the creator name on their messages
        Rangers ranger  = new Rangers();
        java.util.Date date = new java.util.Date();
        this.postID = new SimpleIntegerProperty();
        this.postTitle = new SimpleStringProperty();
        this.postCreator = new SimpleStringProperty(ranger.getUsername());
        this.postCreatorID = new SimpleIntegerProperty();
        this.postMessage = new SimpleStringProperty();
        this.postRating = new SimpleIntegerProperty();
        this.noRatings = new SimpleIntegerProperty();
        this.postDate = new SimpleObjectProperty(date); 
    }
    
    //full constructor utilizing property wrappers
    public MessageTopic (int postID, String postTitle, String postCreator, int postCreatorID, 
            String postMessage, int postRating, int noRatings, Date postDate) {
        
        this.postID = new SimpleIntegerProperty(postID);
        this.postTitle = new SimpleStringProperty(postTitle);
        this.postCreator = new SimpleStringProperty(postCreator);
        this.postCreatorID = new SimpleIntegerProperty(postCreatorID);
        this.postMessage = new SimpleStringProperty(postMessage);
        this.postRating = new SimpleIntegerProperty(postRating);
        this.noRatings = new SimpleIntegerProperty(noRatings);
        this.postDate = new SimpleObjectProperty(postDate);
        
                
        
    }    

    //output data to string
    public String toString () {
        
       
        
        DateFormat dateFormat = new SimpleDateFormat("MM dd yyyy");
        java.util.Date date = new java.util.Date();
        //System.out.println("Current Date : " + dateFormat.format(date));
        
        return "Post ID = " + postID + " Title = " + postTitle + " post Creator = " + postCreator + " rating = " +
                postRating + " post date = " + dateFormat.format(postDate);  
    
    }    
    
    //getters and setters modified
    public int getPostID() {
        return postID.get();
    }
    
    public void setPostID(int postID)
    {
        this.postID.set(postID);
    }

    public IntegerProperty getPostIDProperty() {
        return postID;
    }
    
    public String getPostTitle() {
        return postTitle.get();
    }
    
    public void setPostTitle (String postTitle) {
        this.postTitle.set(postTitle);
    }

    public StringProperty getPostTitleProperty() {
        return postTitle;
    }
    
    public String getPostCreator() {
        return postCreator.get();
    }
    
    public void setPostCreator (String postCreator) {
        this.postCreator.set(postCreator);
    }

    public StringProperty getPostCreatorProperty() {
        return postCreator;
    }
    
    public int getPostCreatorID() {
        return postCreatorID.get();
    }
    
    public void setPostCreatorID(int postCreatorID) {
        this.postCreatorID.set(postCreatorID);
    }

    public IntegerProperty getPostCreatorIDProperty() {
        return postCreatorID;
    }
    
    public String getPostMessage() {
        return postMessage.get();
    }
    
    public void setPostMessage(String message) {
        this.postMessage.set(message);
    }

    public StringProperty getPostMessageProperty() {
        return postMessage;
    }
    
    public int getPostRating() {
        return postRating.get();
    }
    
    public void setPostRating(int rating) {
        this.postRating.set(rating);
    }

    public IntegerProperty getPostRatingProperty() {
        return postRating;
    }
    
    public int getNoRatings () {
        return noRatings.get();
    }
    
    public void setNoRatings(int noRating) {
        this.noRatings.set(noRating);
    }

    public IntegerProperty getNoRatingsProperty() {
        return noRatings;
    }
    
    public Date getPostDate() {
        return postDate.get();
    }
    
    public void setPostDate(Date postDate) {
        this.postDate.set(postDate);
    }

    public ObjectProperty<Date> getPostDateProperty() {
        return postDate;
    }
    
    
    
    
}
