/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangerproject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    
    
    // load some sample data 
    public Rangers() {
        DateFormat dateFormat = new SimpleDateFormat("MM dd yyyy");
        java.util.Date date = new java.util.Date();
        topics.add(new MessageTopic(1000, "Hi", "Andy", 1000, "Hello World!", 1, 0, date));
        topics.add(new MessageTopic(1001, "Hi", "Ben", 1001, "Hello World!", 1, 0, date));
        topics.add(new MessageTopic(1002, "Hi", "James", 1002, "Hello World!", 1, 0, date));
        topics.add(new MessageTopic(1003, "Hi", "Hello", 1003, "Hello World!", 1, 0, date));
        username = "itp220";
        password = "itp220";
        // set up the posts
        posts.add(new MessagePost (1000, "May", "James", "A lost man journeying in the dark!", date));
        posts.add(new MessagePost (1002, "May", "James", "A lost man journeying in the dark!", date));
        posts.add(new MessagePost (1003, "May", "James", "A lost man journeying in the dark!", date));
        posts.add(new MessagePost (1003, "May", "James", "A lost man journeying in the dark!", date));
        posts.add(new MessagePost (1000, "May", "James", "A lost man journeying in the dark!", date));
        posts.add(new MessagePost (1001, "May", "James", "A lost man journeying in the dark!", date));
        posts.add(new MessagePost (1003, "May", "James", "A lost man journeying in the dark!", date));
        posts.add(new MessagePost (1001, "May", "James", "A lost man journeying in the dark!", date));
        
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Rangers Forums");
        
        // set the application icon
        // this.primaryStage.getIcons().add(new Image(""));
        showWelcome();
        
        
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
    public boolean showTopicEditDialog (MessageTopic message) {
        try {
            // load up the FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Rangers.class.getResource("TopicEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
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
            TopicEditDialogController controller = loader.getController();
            // get the stage we want to use
            controller.setDialogStage(dialogStage);
            controller.setTopic(message);
            
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
        try {
            // load up the FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Rangers.class.getResource("PostEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // create the dialog stage that is needed to display the fxml
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Reply");
            // the below is used to prevent user from messing with other windows 
            // until they have finished with this window
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            
            // now we set the topic we want to view in the controller
            // create a new controller
            PostEditDialogController controller = loader.getController();
            // get the stage we want to use
            controller.setDialogStage(dialogStage);
            controller.setPost(message);
            
            //show the dialog and wait until the user closes it out
            dialogStage.showAndWait();
            
            // return the boolean so we know they submitted the message
            
            
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    
}

