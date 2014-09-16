/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangerproject;

import java.util.Date;

/**
 *
 * @author dsestak
 */
public class MessageTopic {
    
    private int postID;
    private String postTitle;
    private String postCreator;
    private int postCreatorID;
    private String postMessage;
    private int postRating;
    private int noRatings;
    private Date postDate;
    
    
    public MessageTopic () {
        
    }
    
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

    public String toString () {
        
        return "Post ID = " + postID + " Title = " + postTitle + " post Creator = " + postCreator + " rating = " + postRating + " post date = " + postDate;  
    
    }    
    
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
