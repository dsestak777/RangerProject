/*
 * This controller controls the main forum selection scene.  There are 4 columns
 * that is needs populated. Date Posted, Topic, Author, # of Replies.
 * There are 2 buttons as well, 1 to create a new topic and 1 to view a topic

 */
package rangerproject;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bunk
 */
// add a number of sort functions into the app that work on the 
// different columns
public class ForumsController {
    
    // these are for the topic table
    @FXML
    private TableView<MessageTopic> topicTable;
    
    @FXML // takes the date
    private TableColumn<MessageTopic, Date> date;
    
    @FXML // takes the Post Title
    private TableColumn<MessageTopic, String> title;
    
    @FXML // takes the Post Creator
    private TableColumn<MessageTopic, String> author;
    
    @FXML // takes the Post message
    private TableColumn<MessageTopic, String> message;
    
    
    // these are for the replies table
    @FXML
    private TableView<MessagePost> postTable;
    
    @FXML 
    private TableColumn <MessagePost, Date> replyDate;
    
    @FXML // title of the reply post
    private TableColumn <MessagePost, String> replyTitle;
    
    @FXML 
    private TableColumn <MessagePost, String> replyAuthor;
    
    @FXML 
    private TableColumn <MessagePost, String> replyMessage;
    
    //DataBase Utilities Class. I added this in so when possible
    // we cann dynamically pull the info from the databases.
    private DBUtilities dbUtil;
    
    //Rangers Class
    private Rangers rangers;
    // set up a stage
    private Stage forumStage;
    // boolean to know if program launched properly
    private boolean enterClick;
    
    // empty constructor
    public ForumsController() {
        
    }
    
    
    // set the stage of this dialog
    public void setControllerStage (Stage forumStage) {
        this.forumStage = forumStage;
    }
    
    // allows Rangers to give a reference back to itself
    public void setRangers(Rangers rangers) {
        this.rangers = rangers;
        
        // now add the neccesary data to the tables
        topicTable.setItems(rangers.getTopics());
    }
    
    public boolean isEnterClick() {
        enterClick = true;
        return enterClick;
    }
    
    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        /**
         * The setCellValueFactory(...) that we set on the table columns are used to determine which field
         * inside the MessageTopic objects should be used for the particular column. 
        */
        // set up the first table
        // set the first column info        
        date.setCellValueFactory(cellData -> cellData.getValue().getPostDateProperty());
        // set the second column info
        title.setCellValueFactory(cellData -> cellData.getValue().getPostTitleProperty());
        // set the third column info
        author.setCellValueFactory(cellData -> cellData.getValue().getPostCreatorProperty());
        // fourth column
        message.setCellValueFactory(cellData -> cellData.getValue().getPostMessageProperty());
        // below is an event listener that allows us to "listen" for clicks on the topic table
        // as a user clicks on the table it allows them to see the replies using the handleViewTopic 
        // method below
        topicTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> handleViewTopic(newValue));
        
        
    }    
    
    //
    
    
    // allow the user to edit the topic title and their message but this will update 
    // their postDate, this also uses the TopicEditDialog.fxml
    @FXML
    private void handleEditTopic() {       
        MessageTopic chosenTopic = topicTable.getSelectionModel().getSelectedItem();
        if (chosenTopic != null) {
            if (chosenTopic.getPostCreator().equals(rangers.getUsername())) {
                boolean newTopicClick = rangers.showTopicEditDialog(chosenTopic);
                if (newTopicClick) {
                    updateTopicTable();
                
                }
            }
        }
        else
            System.out.println("There is an issue loading the data!");
    }
    
    //This method launches the TopicEditDialog.fxml which allows the user to add
    // a new topic by inputting certain basic information. 
    @FXML
    private void handleNewTopic() {
        MessageTopic tempTopic = new MessageTopic();
        showTopicInfo(tempTopic);
        boolean newTopicClick = rangers.showTopicEditDialog(tempTopic);
        if(newTopicClick) {
            // add the topic to the dynamic list
            rangers.getTopics().add(tempTopic);
            //update the table
            
            updateTopicTable();
        }
    }
    
    // this is the reply version of the newTopic but is used for replies...
    @FXML
    private void handleNewReply() {
       // create a new reply
       MessagePost tempReply = new MessagePost();
       // set the tempReply's id
       tempReply.setPostID(topicTable.getSelectionModel().getSelectedItem().getPostID());
       //load the info
       showPostInfo(tempReply);
       //launch the new display
       boolean newReply = rangers.showPostEditDialog(tempReply);
       
       if (newReply) {
           rangers.getPosts().add(tempReply);
           updateReplyTable();
       }
    }
    // this is the edit version for the replies, need to set alert
    @FXML
    private void handleEditReply() {
        MessagePost chosenPost = postTable.getSelectionModel().getSelectedItem();
        if (chosenPost != null){
            if (chosenPost.getReplyCreator().equals(rangers.getUsername())) {
                boolean newPost = rangers.showPostEditDialog(chosenPost);
                if (newPost) {
                    updateReplyTable();
                }
            }
        }
        
        
    }
    //TODO add topic rating button
    @FXML
    private void handleRateTopic() {
        
    }
    
    
    //TODO add reply rating button
    @FXML
    private void handleRateReply() {
    
    }
    
    //this method dyamically sets up the reply posts so that the user can select a post  
    // then the reply table will populate with the replies to the initial post
    private void handleViewTopic(MessageTopic topic) {
        // get the topic we want to get replies for
        MessageTopic selectedTopic = topicTable.getSelectionModel().getSelectedItem();
        // get the id of the topic we want to get replies for
         // check to see if the id above matches a reply, if it does then we load that into a 
        // new observable list that we will then load into the table;
        int id = selectedTopic.getPostID();
        ObservableList<MessagePost> replies = FXCollections.observableArrayList();
        // now see if this was the most recent message
        // if it was set replies to 0 
       
        
        for (int i = 0; i < rangers.getPosts().size(); ++i) {
            if (id == rangers.getPosts().get(i).getPostID()) {
                replies.add(rangers.getPosts().get(i));
            }
            else {
                //TODO set up a dialog box that lets them know there are no replies
                System.out.println("No replies!");
            }
        }
            // now we need to load the table if the reply size is correct
        if (replies.size() != 0) {
            postTable.setItems(replies);
            // set up the first table
            // set the first column info        
            updateReplyTable();
        }
        else {
            // update the table if there are no replies
            postTable.setItems(replies);
            updateReplyTable();
            System.out.println("No posts!");
        }
        
    }    
    
    // this is a utility method we use to update the topic tabls after the tables 
    // are modified
    public void updateTopicTable() {
        date.setCellValueFactory(cellData -> cellData.getValue().getPostDateProperty());
        // set the second column info
        title.setCellValueFactory(cellData -> cellData.getValue().getPostTitleProperty());
        // set the third column info
        author.setCellValueFactory(cellData -> cellData.getValue().getPostCreatorProperty());
        // fourth column
        message.setCellValueFactory(cellData -> cellData.getValue().getPostMessageProperty());
    }
    // this is a utility method we use to update the replies tables after the tables 
    // are modified
    public void updateReplyTable() {
        replyDate.setCellValueFactory(cellData -> cellData.getValue().getReplyDateProperty());
            // set the second column info
        replyTitle.setCellValueFactory(cellData -> cellData.getValue().getReplyTitleProperty());
            // set the third column info
        replyAuthor.setCellValueFactory(cellData -> cellData.getValue().getReplyCreatorProperty());
            // fourth column
        replyMessage.setCellValueFactory(cellData -> cellData.getValue().getReplyMessageProperty());
    }
    
    //TODO delete this before final build 
    // this method will fill in the topic with some base info if it is null
    private void showTopicInfo(MessageTopic topic) {
        
        topic.setPostCreatorID(1);
        topic.setPostID(0);
        topic.setPostMessage("");
        topic.setPostRating(0);
        topic.setPostTitle("ab");
        topic.setNoRatings(1);
                    
    }
    // we need this to set the reply messages to a value otherwise we get null exceptions
    private void showPostInfo(MessagePost post) {
        post.setReplyMessage("a");
        post.setReplyTitle("ab");
        post.setReplyCreator(rangers.getUsername());
    }

}
