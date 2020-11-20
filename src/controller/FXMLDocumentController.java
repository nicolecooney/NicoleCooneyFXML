/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Likemodel;


/**
 *
 * @author nicole
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private Button buttonCreate;

    @FXML
    private Button buttonRead;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;
    
    @FXML
    private Button buttonReadByNameID;
    
    @FXML
    private Button search;

    @FXML
    private Button buttonReadByNameLikeID;

    @FXML
    private TableView<Likemodel> likeTable;

    @FXML
    private TableColumn<Likemodel, Integer> ID;

    @FXML
    private TableColumn<Likemodel, String> likeName;

    @FXML
    private TableColumn<Likemodel, Boolean> likePost;

    @FXML
    private TableColumn<Likemodel, Integer> likeID;

    @FXML
    private TextField likeField;

    //quiz 4 begin
    
   private ObservableList<Likemodel> likeData;

    // add the proper data to the observable list to be rendered in the table
    public void setTableData(List<Likemodel> likeList) {

        // initialize the studentData variable
        likeData = FXCollections.observableArrayList();

        // add the student objects to an observable list object for use with the GUI table
        likeData.forEach(l -> {
            likeData.add(l);
        });

        // set the the table items to the data in studentData; refresh the table
        likeTable.setItems(likeData);
        likeTable.refresh();
    }

    @FXML
    void searchByNameAction(ActionEvent event) {
        System.out.println("clicked");

        // getting the name from input box        
        String name = likeField.getText();

        // calling a db read operaiton, readByName
        List<Likemodel> likes = readByName(name);

        if (likes == null || likes.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");// line 2
            alert.setHeaderText("This is header section to write heading");// line 3
            alert.setContentText("No like");// line 4
            alert.showAndWait(); // line 5
        } else {

            // setting table data
            setTableData(likes);
            System.out.println(likes);
        }

    }
        @FXML
    void actionShowDetails(ActionEvent event) throws IOException {
        System.out.println("clicked");

        
        // pass currently selected model
       Likemodel selectedLike = likeTable.getSelectionModel().getSelectedItem();
        
        // fxml loader
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailModelView.fxml"));

        // load the ui elements
        Parent detailedModelView = loader.load();

        // load the scene
        Scene tableViewScene = new Scene(detailedModelView);

        //access the detailedControlled and call a method
        detail detailedControlled = loader.getController();


       detailedControlled.initData(selectedLike);

        // create a new state
        Stage stage = new Stage();
        stage.setScene(tableViewScene);
        stage.show();

    }

    @FXML
    void actionShowDetailsInPlace(ActionEvent event) throws IOException {
        System.out.println("clicked");

        
                // pass currently selected model
        Likemodel selectedLikemodel = likeTable.getSelectionModel().getSelectedItem();

        
        // fxml loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailModelView.fxml"));

        // load the ui elements
        Parent detailedModelView = loader.load();

        // load the scene
        Scene tableViewScene = new Scene(detailedModelView);

        //access the detailedControlled and call a method
        detail detailedControlled = loader.getController();


        detailedControlled.initData(selectedLikemodel);

        // pass current scene to return
        Scene currentScene = ((Node) event.getSource()).getScene();
        //detailedControlled.setPreviousScene(currentScene);

        //This line gets the Stage information
        Stage stage = (Stage) currentScene.getWindow();

        stage.setScene(tableViewScene);
        stage.show();
    }
    
    //quiz 4 end

    @FXML
    void createLike(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter LikePost:");
        boolean likePost = input.nextBoolean();
        
        System.out.println("Enter LikeID:");
        int likeID = input.nextInt();
        
        // create a student instance
        Likemodel like = new Likemodel();
        
        // set properties
        like.setId(id);
        like.setName(name);
        like.setLikepost(likePost);
        like.setLikeid(likeID);
        
        // save this student to databse by calling Create operation        
        create(like);
        
        //test

    }

    @FXML
    void deleteLike(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
         // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Likemodel l = readById(id);
        System.out.println("we are deleting this like: "+ l.toString());
        delete(l);
    }
    
      void readByID(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Likemodel l = readById(id);
        System.out.println(l.toString());

    }
        @FXML
        void readByName(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter Name:");
        String name = input.next();

        List<Likemodel> s = readByName(name);
        System.out.println(s.toString());

    }
        
     @FXML
    void readByNameLikeID(ActionEvent event) {
        // name and cpga
        
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter LikeID:");
        double likeId = input.nextDouble();
        
        // create a student instance      
        List<Likemodel> likes =  readByNameLikeID(name, likeId);

    }
    @FXML
    void readbyNameID(ActionEvent event) {
        // 
        
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("ID:");
        int id = input.nextInt();
        
        // create a student instance      
        List<Likemodel> likes =  readByNameLikeID(name, id);
    
    }
    @FXML
    void searchLike(ActionEvent event) {
     System.out.println("Clicked!");
        label.setText("Clicked!");
        
        Query query = manager.createNamedQuery("Likemodel.findAll");
        List<Likemodel> data = query.getResultList();
        
        for (Likemodel s : data) {            
            System.out.println(s.getId() + " " + s.getName()+ " " + s.getLikepost() + " " + s.getLikeid());   
        }   
    }

//    @FXML
//    void handleButtonAction(ActionEvent event) {
//
//    }

    @FXML
    void readLike(ActionEvent event) {
        readAll();
    }

    @FXML
    void updateLike(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
         System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter LikePost:");
        boolean likePost = input.nextBoolean();
        
        System.out.println("Enter LikeID:");
        int likeID = input.nextInt();
        
        // create a student instance
        Likemodel like = new Likemodel();
        
        // set properties
        like.setId(id);
        like.setName(name);
        like.setLikepost(likePost);
        like.setLikeid(likeID);
        
        // save this student to databse by calling Create operation        
        update(like);
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
        Query query = manager.createNamedQuery("Likemodel.findAll");
        List<Likemodel> data = query.getResultList();
        
        for (Likemodel s : data) {            
            System.out.println(s.getId() + " " + s.getName()+ " " + s.getLikepost() + " " + s.getLikeid());   
        }
    }
    
    EntityManager manager;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // loading data from database
        //database reference: "IntroJavaFXPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("NicoleCooneyFXMLPU").createEntityManager();
        
        ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        likeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        likePost.setCellValueFactory(new PropertyValueFactory<>("Likepost"));
        likeID.setCellValueFactory(new PropertyValueFactory<>("Likeid"));

        //eanble row selection
        // (SelectionMode.MULTIPLE);
        likeTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    } 
     /*
    Implementing CRUD operations
    */
    
    // Create operation
    public void create(Likemodel like) {
        try {
            // begin transaction
            manager.getTransaction().begin();
            
            // sanity check
            if (like.getId() != null) {
                
                // create student
                manager.persist(like);
                
                // end transaction
                manager.getTransaction().commit();
                
                System.out.println(like.toString() + " is created");
                
            }
           
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Read Operations
    public List<Likemodel> readAll(){
        Query query = manager.createNamedQuery("Likemodel.findAll");
        List<Likemodel> likes = query.getResultList();

        for (Likemodel l : likes) {
            System.out.println(l.getId() + " " + l.getName() + " " + l.getLikepost() + " " + l.getLikeid());
        }
        
        return likes;
    }
    public Likemodel readById(int id){
        Query query = manager.createNamedQuery("Likemodel.findById");
        
        // setting query parameter
        query.setParameter("id", id);
        
        // execute query
        Likemodel like = (Likemodel) query.getSingleResult();
        if (like != null) {
            System.out.println(like.getId() + " " + like.getName() + " " + like.getLikepost() +" "+ like.getLikeid());
        }
        
        return like;
    }  
    
        public List<Likemodel> readByName(String name) {
        Query query = manager.createNamedQuery("Likemodel.findByName");

        // setting query parameter
        query.setParameter("name", name);

        // execute query
        List<Likemodel> likes = query.getResultList();
        for (Likemodel like : likes) {
        System.out.println(like.getId() + " " + like.getName() + like.getLikepost() +" "+ like.getLikeid());
        }

        return likes;
    }
    public List<Likemodel> readByNameLikeID(String name, double likeId){
        Query query = manager.createNamedQuery("Likemodel.findByNameAndlikeId");
        
        // setting query parameter
        query.setParameter("likeID", likeId);
        query.setParameter("name", name);
        
        
        // execute query
        List<Likemodel> likes =  query.getResultList();
        for (Likemodel l: likes) {
            System.out.println(l.getId() + " " + l.getName() + " " + l.getLikepost() + " " + l.getLikeid());
        }
        
        return likes;
    }
      public List<Likemodel> readbyNameID(String name, int id){
        Query query = manager.createNamedQuery("Likemodel.findByNameAndID");
        
        // setting query parameter
        query.setParameter("id", id);
        query.setParameter("name", name);
        
        
        // execute query
        List<Likemodel> likes =  query.getResultList();
        for (Likemodel l: likes) {
            System.out.println(l.getId() + " " + l.getName() + " " + l.getLikepost() + " " + l.getLikeid());
        }
        
        return likes;
      }
    
    // Update operation
    public void update(Likemodel model) {
        try {

            Likemodel existingLike = manager.find(Likemodel.class, model.getId());

            if (existingLike != null) {
                // begin transaction
                manager.getTransaction().begin();
                
                // update all atttributes
                existingLike.setName(model.getName());
                existingLike.setLikepost(model.getLikepost());
                existingLike.setLikeid(model.getLikeid());
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete operation
    public void delete(Likemodel like) {
        try {
            Likemodel existingLike = manager.find(Likemodel.class, like.getId());

            // sanity check
            if (existingLike != null) {
                
                
                // begin transaction
                manager.getTransaction().begin();
                
                //remove student
                manager.remove(existingLike);
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            
        }
    }
    
}
