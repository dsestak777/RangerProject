package rangerproject;

/*
 * This class is the controller for the reply and edit reply GUI
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bunk
 */
public class NewPostDialogController {

    /**
     * Set up textfield and textarea
     */
    @FXML
    private TextField postTitle;
    
    @FXML 
    private TextArea postMessage;
    
    // bring in the messagepost we can load info into
    private MessagePost newPost;
    
    //intitalize reply variables
    private int postID;
    private String userName;
    private String newTitle;
    private String newMessage;
    
    // set up a new stage
    private static Stage postDialogStage;
    // set up a boolean to know if someone has hit the submit button
    private boolean submitClick = false;
    //Database Utilities Class
    DBUtilities dbu;
    
    // empty constructor
    public NewPostDialogController() {
        
    }
    
    public void initialize() {
        
    }   
    
    // set the dialog for this stage
    public void setDialogStage(Stage postDialogStage) {
        NewPostDialogController.postDialogStage = postDialogStage;
    }
    // find out if submit has been clicked on
    public boolean isSubmitClick() {
        return submitClick;
    }
    // this sets up the input fields to store the information the user inputs
    public void setPost(int postID, String userName) {
        this.postID = postID;
        this.userName = userName;
        
     //   postTitle = new TextField();
     //   postMessage = new TextArea();
        
     //   postTitle.setText(newPost.getReplyTitle());
     //   postMessage.setText(newPost.getReplyMessage());
        
    }
    //TODO set up alert box
    // the submit button
    @FXML 
    private void onSubmit() {
        if (isInputValid()) {
            // load the user info into the post
           // newPost.setReplyTitle(postTitle.getText());
           // newPost.setReplyMessage(postMessage.getText());
              newTitle = postTitle.getText();
              newMessage = postMessage.getText();
              
              //add new reply to database
              dbu.addReply(postID, newTitle, newMessage, userName);
              

            // set boolean to true
            submitClick = true;
            //close the dialog window
           
            
            
            postDialogStage.close();
        }
        else {
            // need to set up an alert box of some kind
        }
    }
    // the cancel button
    @FXML
    private void onCancel() {
        postDialogStage.close();
    }
    
    //TODO setup a new alert box
    //we use this to let the user know there is an error with a field they are inputting
    private boolean isInputValid() {
        // we want to make sure there is something here, but that the topic is not too long
        if (postTitle.getText() == null || postTitle.getText().length() == 0 || postTitle.getText().length() > 75) {
            System.out.println("Need to check the errors");
                    
        }
        // we want to make sure the message has something but is not longer than 300 characters
        if (postMessage.getText() == null || postMessage.getText().length() == 0 || postMessage.getText().length() > 300) {
            System.out.println("Need to check the errors");
            return false;
        }
        else 
            return true;
    }
    
}
