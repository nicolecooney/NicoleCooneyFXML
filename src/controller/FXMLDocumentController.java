/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
