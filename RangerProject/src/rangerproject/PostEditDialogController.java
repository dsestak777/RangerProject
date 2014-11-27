package rangerproject;

/*
 * This class is the controller for the reply and edit reply GUI
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bunk
 */
public class PostEditDialogController {

    /**
     * Set up textfield and textarea
     */
    @FXML
    private TextField postTitle;
    
    @FXML 
    private TextArea postMessage;
    
    // bring in the messagepost we can load info into
    private MessagePost editPost;
    
    //intitalize reply variables
    private int postID;
    private int topicID;
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
    public PostEditDialogController() {
        
    }
    
    public void initialize() {
        
    }   
    
    // set the dialog for this stage
    public void setDialogStage(Stage postDialogStage) {
        PostEditDialogController.postDialogStage = postDialogStage;
    }
    // find out if submit has been clicked on
    public boolean isSubmitClick() {
        return submitClick;
    }
    // this sets up the input fields to store the information the user inputs
    public void setPost(int postID, int topicID, String userName, MessagePost editPost) {
        this.postID = postID;
        this.topicID = topicID;
        this.userName = userName;
        this.editPost = editPost;
        
     //   postTitle = new TextField();
     //   postMessage = new TextArea();
        
        postTitle.setText(editPost.getReplyTitle());
        postMessage.setText(editPost.getReplyMessage());
        
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
              dbu.editReply(topicID, postID, newTitle, newMessage, userName);
              

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
        if (postTitle.getText() == null || postTitle.getText().length()== 0 
                || postTitle.getText().length() > 75) 
        {
            if (postTitle.getText().length() > 75)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Title Length");
                alert.setHeaderText(null);
                alert.setContentText("Shakespeare said title's should be less than 75 characters, we agree!");

                alert.showAndWait();
            
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Title Length");
                alert.setHeaderText(null);
                alert.setContentText("I'm curious as to why you haven't written anything for a title? Writers Block?");

                alert.showAndWait();
            }
            
            return false;
        }
        if (postMessage.getText() == null || postMessage.getText().length() == 0 ||
                postMessage.getText().length() > 300) {
            if (postMessage.getText().length() > 300)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message Length");
                alert.setHeaderText(null);
                alert.setContentText("One of the great things Nolan Ryan taught me was that messages should always be less than 300 characters!");

                alert.showAndWait();
            
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message Length");
                alert.setHeaderText(null);
                alert.setContentText("Silence only makes sense in space and for performing mimes, not for forums! Please write something.");

                alert.showAndWait();
            }
            
            return false;
        }
        else {
            //TODO get alert box
            return true;
        }
    }
    
}
