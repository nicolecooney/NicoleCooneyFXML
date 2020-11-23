/**
 * Sample Skeleton for 'DetailModelView.fxml' Controller Class
 */
package controller;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Likemodel;

public class DetailedModelViewController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
//
    @FXML // fx:id="image"
    private ImageView image; // Value injected by FXMLLoader
  
    
    @FXML
    private Button backButton;

    @FXML
    private Label ID;

    @FXML
    private Label Name;

    @FXML
    private Label LikePost;

    @FXML
    private Label LikeID;

    @FXML
    void goBack(ActionEvent event) {
        // option 1: get current stage -- from event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        //  option 2: get current stage -- from backbutton        
        // Stage stage = (Stage)backButton.getScene().getWindow();
        
        if (previousScene != null) {
            stage.setScene(previousScene);
        }

    } 


    Likemodel selectedModel;
    Scene previousScene;

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);

    }


    public void initData(Likemodel model) {
        selectedModel = model;
        ID.setText(model.getId().toString());
        Name.setText(model.getName());
        LikePost.setText(model.getLikepost().toString());
        LikeID.setText(model.getLikeid().toString());

        try {
            // path points to /resource/images/
            String imagename = "/resource/images/" + model.getName() + ".png";
            Image profile = new Image(getClass().getResourceAsStream(imagename));
            image.setImage(profile);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backButton != null : "fx:id=\"backButtong\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert ID != null : "fx:id=\"labelID\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert Name!= null : "fx:id=\"labelValue\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert LikePost != null : "fx:id=\"image\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert LikeID != null : "fx:id=\"image\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        backButton.setDisable(true);

    }
    
}

