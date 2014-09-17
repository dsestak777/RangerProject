/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangerproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author dsestak
 */
public class MessageTopic {
    
    //list instance variables
    private int postID;
    private String postTitle;
    private String postCreator;
    private int postCreatorID;
    private String postMessage;
    private int postRating;
    private int noRatings;
    private Date postDate;
   
    
    //no argument constructor
    public MessageTopic () {
        
    }
    
    //full constructor
    public MessageTopic (int postID, String postTitle, String postCreator, int postCreatorID, 
            String postMessage, int postRating, int noRatings, Date postDate ) {
        
        this.postID = postID;
        this.postTitle = postTitle;
        this.postCreator = postCreator;
        this.postCreatorID = postCreatorID;
        this.postMessage = postMessage;
        this.postRating = postRating;
        this.noRatings = noRatings;
        this.postDate = postDate;
                
        
    }    

    //output data to string
    public String toString () {
        
       
        
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
        java.util.Date date = new java.util.Date();
        //System.out.println("Current Date : " + dateFormat.format(date));
        
        return "Post ID = " + postID + " Title = " + postTitle + " post Creator = " + postCreator + " rating = " +
                postRating + " post date = " + dateFormat.format(postDate);  
    
    }    
    
    //getters and setters
    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostCreator() {
        return postCreator;
    }

    public void setPostCreator(String postCreator) {
        this.postCreator = postCreator;
    }

    public int getPostCreatorID() {
        return postCreatorID;
    }

    public void setPostCreatorID(int postCreatorID) {
        this.postCreatorID = postCreatorID;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public int getPostRating() {
        return postRating;
    }

    public void setPostRating(int postRating) {
        this.postRating = postRating;
    }

    public int getNoRatings() {
        return noRatings;
    }

    public void setNoRatings(int noRatings) {
        this.noRatings = noRatings;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    
    
}
