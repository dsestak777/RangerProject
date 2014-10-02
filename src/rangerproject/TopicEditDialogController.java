package rangerproject;

/*
 * This class allows the user to post a new topic or edit an existing one.
 * TODO, see if we can preload some info for messages.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
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
    
    // bring in a messageTopic we can load info into
    private MessageTopic newTopic;
    // set up a stage for this
    private Stage topicDialogStage;
    // set up submit boolean to know if it has been clicked
    private boolean submitClick;
    
    //empty constructor
    public TopicEditDialogController () {
        
    }
    
    public void initialize() {
        
    }    
    // set the stage
    public void setDialogStage (Stage topicDialogStage) {
        this.topicDialogStage = topicDialogStage;
    }
    // used to find out if submit has been clicked
    public boolean isSubmitClick() {
        return submitClick;
    }
    // this is supposed to fire off in the rangers file
    public void setTopic(MessageTopic newTopic) {
        this.newTopic = newTopic;
        // prompt the input fields to recieve input
        topicTitle.setText(newTopic.getPostTitle());
        message.setText(newTopic.getPostMessage());
        
    }
    //TODO need an alert box for below
    //submit button
    @FXML 
    private void onSubmit() {
        if (isInputValid()) {
            // get the info from the input fields and load it 
            // into the object
            newTopic.setPostTitle(topicTitle.getText());
            newTopic.setPostMessage(message.getText());
            
            submitClick = true;
            topicDialogStage.close();
        }
        else {
            //TODO set up an alert box here
        }
    }
    // cancel button
    @FXML
    private void onCancel() {
        topicDialogStage.close();
    }
    
    // check to see if input is valid
    //TODO need to get new alert box setup here
    private boolean isInputValid() {
        if (topicTitle.getText() == null || topicTitle.getText().length()== 0 
                || topicTitle.getText().length() > 75) 
        {
            System.out.println("Need to check the errors!");
            return false;
        }
        if (message.getText() == null || topicTitle.getText().length() == 0 ||
                topicTitle.getText().length() > 300) {
            System.out.println("Check the errors!");
            return false;
        }
        else {
            //TODO get alert box
            return true;
        }
    }
    
    
}
