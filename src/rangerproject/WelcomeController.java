/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangerproject;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bunk
 */
public class WelcomeController {

    //label to reference the textfields in the program;
    @FXML 
    private TextField name;
    @FXML
    private PasswordField password;
    // stage for this controller
    private Stage welcomeStage;
    // create a Ranger object we can use to set the password and username fields
    private Rangers ranger;
    // boolean to make sure username and password are ok
    private boolean nameCheck;
    
    
    // constructor
    public WelcomeController() {
        
    }
    // reference to this controller
    public void setWelcomeStage(Stage welcomeStage) {
        this.welcomeStage = welcomeStage;
    }
    
    public void initialize() {
        // TODO
    }   
    
    public void setUsername (Rangers ranger) {
        this.ranger = ranger;
        
        password.setText(ranger.getPassword());
        name.setText(ranger.getUsername());
    }
    

    // when the user hits ok this method should add the username and password
    // to the fields in the main Rangers class
    // after checking that their are values and that the values are ok it should launch the program
    
    @FXML
    public void hitEnter() {
        // verify the username and password are correct
        verify();
        if(nameCheck) {
            
            ranger.setUsername(name.getText());
            ranger.setPassword(password.getText());
            boolean enterForums = ranger.showForums();
            if (enterForums == true) {
                System.out.println("Launched properly!");
            }
            else
                System.out.println("There is an issue!");
        }
        else {
            System.out.println("Issue with the input data!");
        }

    }
    
    @FXML
    public void cancel() {
        welcomeStage.close();
    }
    
    //Checks to make sure the name 
    public void verify () {
        if (name.getText().length() > 1 && password.getText().length() > 1) {
            nameCheck = true;
        } else {
            System.out.println("It did not read the inputs properly!");
            nameCheck = false;
        }
    }
}
