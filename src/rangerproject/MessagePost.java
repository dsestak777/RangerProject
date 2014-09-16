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
public class MessagePost {
 
    private int postID;
    private String replyTitle;
    private String replyCreator;
    private String replyMessage;
    private Date replyDate;
 
    public MessagePost () {
        
    }
    
    public MessagePost (int postID, String replyTitle, String replyCreator, 
            String replyMessage, Date replyDate) {
        
        this.postID = postID;
        this.replyTitle = replyTitle;
        this.replyCreator = replyCreator;
        this.replyMessage = replyMessage;
        this.replyDate = replyDate;
        
    }
            
    public String toString () {
        
        return "PostID = " + postID + "Title = " + replyTitle + " creator = " + replyCreator + " Message = "
                + replyMessage + " date = " + replyDate;
                
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getReplyTitle() {
        return replyTitle;
    }

    public void setReplyTitle(String replyTitle) {
        this.replyTitle = replyTitle;
    }

    public String getReplyCreator() {
        return replyCreator;
    }

    public void setReplyCreator(String replyCreator) {
        this.replyCreator = replyCreator;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }
    
    
    
}
