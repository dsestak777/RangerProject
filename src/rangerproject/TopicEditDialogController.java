/*
 * This class allows the user to post a new Topic.
 * TODO, check to see if we can preload some of the information for messages
 */
package rangerproject;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Bunk
 */
public class TopicEditDialogController {
    
    // set up the Textfields and Textarea
    @FXML 
    private TextField topicTitle;
    
    @FXML
    private TextArea message;
    
    
    // bring in a messageTopic we can load the information into
    private MessageTopic newTopic;
    // bring in a messagePost we can load the info into
    private MessagePost newPost;
    // set up a stage for this
    private Stage topicDialogStage;
    //set up a boolean to tell if the submit button has been clicked
    private boolean submitClick = false;

    //empty constructor
    public TopicEditDialogController () {
        
    }
    
    public void initialize() {
                
    }    
    
    // set the stage of this dialog
    public void setDialogStage (Stage topicDialogStage) {
        this.topicDialogStage = topicDialogStage;
    }
    
    // used to find out if submit has been clicked
    public boolean isSubmitClick() {
        return submitClick;
    }
    
    // this is supposed to fire off in the Rangers file
    public void setTopic (MessageTopic newTopic) {
        this.newTopic = newTopic;
        
        
        topicTitle.setText(newTopic.getPostTitle());
        message.setText(newTopic.getPostMessage());
        //TODO see if you need to add the rest of the MessageTopic fields here
        // as I think most of these should be automatically set;  
    }
    
    
    
    
    //TODO setup the submit button
    @FXML
    private void onSubmit() {
        if (isInputValid()) {
            newTopic.setPostTitle(topicTitle.getText());
            newTopic.setPostMessage(message.getText());
            
            
            submitClick = true;
            topicDialogStage.close();
        }
    }
    
    //TODO setup the cancel button
    @FXML
    private void onCancel() {
        topicDialogStage.close();
    }
    
    //TODO find a new alert box for here
    // we use this to let the user know there is an error with a field they are inputting
    private boolean isInputValid() {
        
        // we want to make sure there is something here, but that the topic is not too long
        if (topicTitle.getText() == null || topicTitle.getText().length() == 0 || topicTitle.getText().length() > 75) {
            
            System.out.println("Need to check the errors");
            return false;
        }
        // we want to make sure the message has something but is not longer than 300 characters
        if (message.getText() == null || message.getText().length() == 0 || message.getText().length() > 300) {
            System.out.println("Need to check the errors!");
            return false;
        }
        else {
            return true;
        }
        
    }
    
    
}

