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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class TopicRatingDialogController  {

    Rangers rangers;
    private int rating = 5;  //default value
    private String postTopic;
    /**
     * Set up textfield and textarea
     */
    @FXML
    private Label postTitle;
    
    @FXML 
    private Slider ratingSlider;
    
    // bring in the messagepost we can load info into
    private MessageTopic topic;
 //   private String messageTopic;
    // set up a new stage
    private static Stage postRatingStage;
    // set up a boolean to know if someone has hit the submit button
    private boolean submitClick = false;
    //Database Utilities Class
    DBUtilities dbu;
   
    // empty constructor
    public TopicRatingDialogController () {
        
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
                        //set rating based upon slider position
                        rating = (int) (newValue.intValue() );
                 
                      
                       
                    }        
                });
        
        postTitle.setText("Post Title");
        
              
        postTitle.textProperty().addListener(
                new ChangeListener<String>()
                {
             @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
              
                   
              //   postTitle.textProperty().bind(observable);    
            }
          });
        
     //   String topic = rangers.getCurrentTopic();
    //    postTitle.setText(messageTopic);
      //   postTitle.textProperty().bind(messageTopic);
     //   ObservableValue<String> topic = "test";
      //  postTitle.textProperty().bind(messageTopic);    
            
    //    postTitle.setText(getMessageTopic());
          
    //     messageTopic = Rangers.getTopicTitle();
        
                    
      //   System.out.println("topic="+messageTopic);
        
    }   
    
    
   
    
    // set the dialog for this stage
    public void setDialogStage(Stage postRatingStage) {
        TopicRatingDialogController.postRatingStage = postRatingStage;
     //     this.postRatingStage = postRatingStage; 
    }
    
    // find out if submit has been clicked on
    public boolean isSubmitClick() {
        return submitClick;
    }
    // this sets the post title
   
    @FXML
 //   public void setTopic(String messageTopic) {
     public void setTopic(MessageTopic messageTopic) {
    //    TopicRatingDialogController.messageTopic = messageTopic;
       //   this.messageTopic = messageTopic;
         this.topic = messageTopic;
      //      String topicName = topic;
        
     //   ObservableValue<String> topic = messageTopic.getPostMessage();
     //     postTitle = new TextField();
    //    postMessage = new TextArea();
    //    postTitle = new Label(messageTopic);
         
          
           
        postTitle.setText(topic.getPostTitle());
        
       
    //    postTitle.setText(messageTopic.getPostMessage());
    //    postTitle.setText(messageTopic);
          
    }
    
    
    //TODO set up alert box
    // the submit button
    @FXML 
    private void onRatingSubmit() {
        if (isInputValid()) {
            // load the user info into the post
            
         //   newPost.setReplyTitle(postTitle.getText());
            
         //   newPost.setReplyMessage(postMessage.getText());
            
            dbu.ratePost(rating, topic.getPostID());
            System.out.println(rating);
            
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
