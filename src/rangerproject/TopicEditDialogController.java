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
    
    //initialize Post variables
    private int userID;
    private String userName;
    private String newTitle;
    private String newMessage;
    
    // set up a stage for this
    private Stage topicDialogStage;
    // set up submit boolean to know if it has been clicked
    private boolean submitClick;
    //Database Utilities Class
    DBUtilities dbu;
    
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
 //   public void setTopic(MessageTopic newTopic) {
     public void setTopic(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
        
        // prompt the input fields to recieve input
      //  topicTitle.setText(newTopic.getPostTitle());
      //  message.setText(newTopic.getPostMessage());
        
    }
    //TODO need an alert box for below
    //submit button
    @FXML 
    private void onSubmit() {
        if (isInputValid()) {
            // get the info from the input fields and load it 
            // into the object
          newTitle =  topicTitle.getText();
          newMessage =  message.getText();
            
            submitClick = true;
            
            //add New Post to database
            dbu.addPost(newTitle, userName, userID, newMessage);
            
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
