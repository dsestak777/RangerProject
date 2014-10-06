package rangerproject;

/*
 * This class is the controller for the reply and edit reply GUI
 */
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bunk
 */
public class TopicRatingDialogController {

    private int rating = 5;  //default value
    private String postTopic;
    /**
     * Set up textfield and textarea
     */
    @FXML
    private Label postTitle;
    
    @FXML 
 //   private TextArea postMessage;
    private Slider ratingSlider;
    
    // bring in the messagepost we can load info into
  //  private MessagePost newPost;
    private String messageTopic;
    // set up a new stage
    private static Stage postRatingStage;
    // set up a boolean to know if someone has hit the submit button
    private boolean submitClick = false;
    
    // empty constructor
    public TopicRatingDialogController() {
        
    }
    
    public void initialize() {
        
        
          //loan interest rate slider listener
        ratingSlider.valueProperty().addListener(
                new ChangeListener<Number>()
                {
                    @Override
                    public void changed (ObservableValue<? extends Number> ov,
                            Number oldValue, Number newValue)
                            
                    {
                        //set interest rate based upon slider position
                        rating = (int) (newValue.intValue() / 100.0);
                        
                        //set text based upon loan interest rate selected
                     //   interestRateLabel.setText("Interest Rate = " + percent.format(interestRate));
                    }        
                });
        
    //    postTitle.setText("Post Title");
        String topic=getTopic();
        postTitle.setText(topic);
    }   
    
   
    
    // set the dialog for this stage
    public void setDialogStage(Stage postRatingStage) {
        TopicRatingDialogController.postRatingStage = postRatingStage;
    }
    
    // find out if submit has been clicked on
    public boolean isSubmitClick() {
        return submitClick;
    }
    // this sets the post title
   
    public void setTopic(String messageTopic) {
        this.messageTopic = messageTopic;
        
     //   postTitle = new TextField();
    //    postMessage = new TextArea();
        System.out.println(messageTopic);
    //    postTitle.setText(messageTopic.getPostMessage());
   //     postTitle.setText(messageTopic);
          
    }
    
    public String getTopic(){
        
        return messageTopic;
        
    }
    //TODO set up alert box
    // the submit button
    @FXML 
    private void onRatingSubmit() {
        if (isInputValid()) {
            // load the user info into the post
            
         //   newPost.setReplyTitle(postTitle.getText());
            
         //   newPost.setReplyMessage(postMessage.getText());
            
            // set boolean to true
            submitClick = true;
            //close the dialog window
            postRatingStage.close();
        }
        else {
            // need to set up an alert box of some kind
        }
    }
    
    // the cancel button
    @FXML
    private void onRatingCancel() {
        
        System.out.println("Cancel pushed@!");
        postRatingStage.close();
        
       
        
    }
    
    
   
    
    //TODO setup a new alert box
    //we use this to let the user know there is an error with a field they are inputting
    private boolean isInputValid() {
        // we want to make sure there is something here, but that the topic is not too long
        if (postTitle.getText() == null || postTitle.getText().length() == 0 || postTitle.getText().length() > 75) {
            System.out.println("Need to check the errors");
                    
        }
        // we want to make sure the message has something but is not longer than 300 characters
     //   if (postMessage.getText() == null || postMessage.getText().length() == 0 || postMessage.getText().length() > 300) {
     //       System.out.println("Need to check the errors");
     //       return false;
      //  }
      //  else 
            return true;
    }
    
}
