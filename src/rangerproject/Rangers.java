/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangerproject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static rangerproject.DBUtilities.addUser;
import static rangerproject.DBUtilities.logINUser;
import static rangerproject.DBUtilities.logOutUser;

/**
 *
 * @author dsestak
 */
public class Rangers extends Application {
    
    // we set up our main stage and the rootLayout
    private Stage primaryStage;
    // set up the Observablelists of topics and posts
    private ObservableList<MessageTopic> topics = FXCollections.observableArrayList();
    private ObservableList<MessagePost> posts = FXCollections.observableArrayList();
    // set up password and Username fields
    private String username = "";
    private String password = "";
    private int userid = 0;

    DBUtilities dbu;
    
   
    public Rangers() {
        DateFormat dateFormat = new SimpleDateFormat("MM dd yyyy");
        java.util.Date date = new java.util.Date();
        
      
        
        //preset username & password
        username = "itp220";
        password = "220";
      
        
        
      
    }
    
    //method for testing add data & reset tables
    public void addData() {
        //delete database tables if they exists
        dbu.deleteTables();
        
        //create the database tables
        dbu.createTables();
        
        //add some usesrs
        dbu.addUser("test2","123");
        dbu.addUser("test1", "111");
        dbu.addUser("itp220", "220");
        
        //log in these users 
        dbu.logINUser ("test2","123");
        dbu.logINUser ("test1", "111");
        dbu.logINUser("itp220","220");
       
        //add some posts 
        dbu.addPost("First Post", "test2", 1, "ALL YOUR BASE ARE BELONG TO US");
        dbu.addPost("Second Post", "test1", 2, "THE RAIN IN SPAIN");
        dbu.addPost("Third Post", "test2", 1, "TO INFINITY AND BEYOND");
        dbu.addPost("Fourth Post", "test1", 2, "WHAT ME WHY WORRY");
        dbu.addPost("Fifth Post", "test2", 2, "GIVE ME FIVE");
        
        //add some replies
        dbu.addReply(1, "Reply Title", "Reply Message", "test2");
        dbu.addReply(1, "Reply Title", "Reply Message", "test2");
        dbu.addReply(2, "Reply Title2", "Reply Message2", "test1");
        dbu.addReply(3, "Reply Title3", "Reply Message3", "test2");
        dbu.addReply(4, "Reply Title4", "Reply Message4", "test1");
        dbu.addReply(5, "Reply Title5", "Reply Message5", "test2");
        dbu.addReply(5, "Reply Title6", "Reply Message5", "test2");
        
        
        //logout users 
        dbu.logOutUser("test2");
        dbu.logOutUser("test1");
        
        //update 
        updateMessageTopics();
        
    }    
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Rangers Forums");
        
        // set the application icon
        // this.primaryStage.getIcons().add(new Image(""));
        showWelcome();
        
        
    }
    
    //method to update MessageTopic arraylist from Database
    public void updateMessageTopics() {
       
       ArrayList<MessageTopic> tempArray = dbu.viewByDate(); 
     
       
       //cast arraylist to observablearraylist
       topics = FXCollections.observableArrayList(tempArray);
       
       setTopics(topics);
        
    }
    
    
    //method to update MessageTopic arraylist from Database
    public void updateMessagePosts(int id) {
       
        
        
       //get replies from database 
       ArrayList<MessagePost> tempArray = dbu.viewChosenPost(id);

       //cast arraylist to observablearraylist
       posts = FXCollections.observableArrayList(tempArray);
       
       
       setPosts(posts);
        
    }
    
    //method to show the main topic window
    public boolean showForums() {
        try {
            // load the forum overview
            FXMLLoader loader = new FXMLLoader();
            // set the location of the fxml file
            loader.setLocation(Rangers.class.getResource("Forums.fxml"));
            // create an anchorpane that will load the person overview
            AnchorPane forumOverview = (AnchorPane) loader.load();
            
            // create a new scene
            Scene scene = new Scene(forumOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
        
            // allow the controller access to the forum
            ForumsController controller = loader.getController();
            
            controller.setRangers(this);
        
            
            
            return controller.isEnterClick();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // use this to open the newTopic box if clicked on in the forums.fxml
    public boolean showNewTopicDialog (MessageTopic message) {
       
        String userName = getUsername();
        /**  need to add method to set userID */
        int userID = getUserID();
        
        
        try {
            // load up the FXML
            FXMLLoader newTopicLoader = new FXMLLoader();
            newTopicLoader.setLocation(Rangers.class.getResource("NewTopicDialog.fxml"));
            AnchorPane page = (AnchorPane) newTopicLoader.load();
            
            // create the dialog stage that is needed to display the fxml
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Topic");
            // the below is used to prevent user from messing with other windows 
            // until they have finished with this window
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            
            // now we set the topic we want to view in the controller
            // create a new controller
         //   TopicEditDialogController controller = loader.getController();
            NewTopicDialogController controller = newTopicLoader.<NewTopicDialogController>getController();
            // get the stage we want to use
            controller.setDialogStage(dialogStage);
          
            
            controller.setTopic(userID, userName);
            
            //show the dialog and wait until the user closes it out
            dialogStage.showAndWait();
            
            // return the boolean so we know they submitted the message
            
            
            return controller.isSubmitClick();
            
        
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    // use this to open the newTopic box if clicked on in the forums.fxml
    public boolean showTopicEditDialog (MessageTopic message) {
       
        String userName = getUsername();
        
        
        
        try {
            // load up the FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Rangers.class.getResource("TopicEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // create the dialog stage that is needed to display the fxml
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Topic");
            // the below is used to prevent user from messing with other windows 
            // until they have finished with this window
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            
            // now we set the topic we want to view in the controller
            // create a new controller
         //   TopicEditDialogController controller = loader.getController();
            TopicEditDialogController controller = loader.<TopicEditDialogController>getController();
            // get the stage we want to use
            controller.setDialogStage(dialogStage);
          
            
            controller.setTopic(userName, message);
            
            //show the dialog and wait until the user closes it out
            dialogStage.showAndWait();
            
            // return the boolean so we know they submitted the message
            
            
            return controller.isSubmitClick();
            
        
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // use this to open the newReply box if clicked on in the forums.fxml
    public boolean showPostEditDialog (MessagePost message) {
        int postID = message.getPostID();
        int topicID = message.getTopicID();
        String userName = message.getReplyCreator();
        
        try {
            // load the FXML
            FXMLLoader postLoader = new FXMLLoader();
            postLoader.setLocation(Rangers.class.getResource("PostEditDialog.fxml"));
            AnchorPane page = (AnchorPane) postLoader.load();
            
            //create the dialog stage that is needed to display the FXML
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Reply");
            
            // the below is used to prevent user from messing with other windows
            // until they have finished with this window
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // set the post we want to view in the controller
            // create a dialog  
       //   PostEditDialogController controller = new PostEditDialogController();
            PostEditDialogController controller = postLoader.<PostEditDialogController>getController();
            // get the stage we want to use
            controller.setDialogStage(dialogStage);
            
            
            
            controller.setPost(postID, topicID, userName, message);
            
            //show the dialog and wait until the user closes it out
            dialogStage.showAndWait();    
            
            //return the boolean so we know they submitted the msg
            return controller.isSubmitClick();
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    
    // use this to open the newReply box if clicked on in the forums.fxml
    public boolean showNewPostDialog (MessagePost message) {
        int postID = message.getPostID();
        String userName = message.getReplyCreator();
        
        try {
            // load the FXML
            FXMLLoader newPostLoader = new FXMLLoader();
            newPostLoader.setLocation(Rangers.class.getResource("NewPostDialog.fxml"));
            AnchorPane page = (AnchorPane) newPostLoader.load();
            
            //create the dialog stage that is needed to display the FXML
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Reply");
            
            // the below is used to prevent user from messing with other windows
            // until they have finished with this window
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // set the post we want to view in the controller
            // create a dialog  
       //   PostEditDialogController controller = new PostEditDialogController();
            NewPostDialogController controller = newPostLoader.<NewPostDialogController>getController();
            // get the stage we want to use
            controller.setDialogStage(dialogStage);
            
            
            
            controller.setPost(postID, userName);
            
            //show the dialog and wait until the user closes it out
            dialogStage.showAndWait();    
            
            //return the boolean so we know they submitted the msg
            return controller.isSubmitClick();
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    
 //    public boolean showRatingDialog (int TopicID, String topicTitle) {
     public boolean showRatingDialog (MessageTopic topic) {     
    
      //  System.out.println(topicTitle);
         
        try {
            // load the FXML
            FXMLLoader ratingLoader = new FXMLLoader();
            ratingLoader.setLocation(Rangers.class.getResource("TopicRatingDialog.fxml"));
            AnchorPane page = (AnchorPane) ratingLoader.load();
            
            //create the dialog stage that is needed to display the FXML
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Rate This Topic");
            
            // the below is used to prevent user from messing with other windows
            // until they have finished with this window
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // set the post we want to view in the controller
            // create a dialog  
         //   TopicRatingDialogController controller = new TopicRatingDialogController();
            // get the stage we want to use
            TopicRatingDialogController controller = ratingLoader.<TopicRatingDialogController>getController();
            
            controller.setDialogStage(dialogStage);
             
            controller.setTopic(topic);
       //     controller.setTopic(topicTitle);
            
               
            //show the dialog and wait until the user closes it out
            dialogStage.showAndWait();    
            
            //return the boolean so we know they submitted the msg
            return controller.isSubmitClick();
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    // need to fix
    public void showWelcome() {
        try {
            // load the forum overview
            FXMLLoader loader = new FXMLLoader();
            // set the location of the fxml file
            loader.setLocation(Rangers.class.getResource("Welcome.fxml"));
            // create an anchorpane that will load the person overview
            AnchorPane forumWelcome = (AnchorPane) loader.load();
            
            // create a new scene
            Scene scene = new Scene(forumWelcome);
            primaryStage.setScene(scene);
            primaryStage.show();
        
            // allow the controller access to the forum
            WelcomeController controller = loader.getController();
            controller.setWelcomeStage(primaryStage);
            
            controller.setUsername(this);
            setUserID(3);
            
            addData();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    // getters and setters
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public ObservableList<MessageTopic> getTopics() {
        return topics;
    }

    public void setTopics(ObservableList<MessageTopic> topics) {
        this.topics = topics;
    }

    public ObservableList<MessagePost> getPosts() {
        return posts;
    }

    public void setPosts(ObservableList<MessagePost> posts) {
        this.posts = posts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        
        return userid;
    }
    
    public void setUserID (int userID) {
        
        this.userid = userID;
        
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
   
}

