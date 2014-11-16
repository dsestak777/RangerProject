/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rangerproject;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author dsestak
 */
public class DBUtilities {
    
    //list and initialize variables and arrays
    private static Connection con;
    private static Statement stmt;
    private static Scanner scan = new Scanner(System.in);
    private static String createDBString;
    private static String createUsersString;
    private static String createPostIndexString;
    private static String createPostReplyTable;
    private static String dropTableUsers;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
    private static CallableStatement ca;
    private static ArrayList<MessageTopic> topics = new ArrayList<>();
    private static ArrayList<MessagePost> post = new ArrayList<>();
    
    
    
    public static void main (String Args[]) {
        
       // createDatabase();
        createTables();
        addUser("test2","123");
        addUser("test1", "111");
        
        boolean checkUser = doesUserExist ("test2");
        
        System.out.println("does user exist =" + checkUser);
        
        logINUser ("test2","123");
        logINUser ("test1", "111");
       
        boolean password = checkPassword("test2","123");
        
        boolean log = isUserLoggedIN("test2");
        System.out.println(log);
        
    //    addPost("First Post", "test2", 1, "ALL YOUR BASE ARE BELONG TO US");
    //    addPost("Second Post", "test1", 2, "THE RAIN IN SPAIN");
    //    addPost("Third Post", "test2", 1, "TO INFINITY AND BEYOND");
    //    addPost("Fourth Post", "test1", 2, "WHAT ME WHY WORRY");
    //     addPost("Fifth Post", "test2", 2, "GIVE ME FIVE");
        
      //  topics = viewByUser("test2");
        
      //  for (int i = 0; i<topics.size(); i++) {
            
     //       System.out.println(topics.get(i).toString());
     //   }
        
        
        ratePost(10,3);
        
        topics = viewByDate();
        
        for (int i = 0; i<topics.size(); i++) {
            
            System.out.println(topics.get(i).toString());
        }
        
     //   addReply(3, "Reply Title", "Reply Message", "test2");
     //   addReply(3, "Reply Title2", "Reply Message2", "test2");
        
     //   deletePost(2,"test2","123");
        
    //    addReply(2, "Reply Title", "Reply Message", "test2");
    //    addReply(2, "Reply Title2", "Reply Message2", "test2");
        
        post = viewChosenPost(3);
        
         for (int i = 0; i<post.size(); i++) {
            
            System.out.println(post.get(i).toString());
        }
        
        logOutUser("test2");
        
    }
    
    //check if connection to database exists
    public static void checkConnect() {
        
		// if connection does not exist create one
                if (con==null) {
                    
			con=createConnection();
		}
                //if statement does not exist create one 
		if(stmt==null) {
			try {
				stmt = con.createStatement();
			} catch (SQLException e){
				System.out.println("SQL Error: Cannot create the statement.");
			}					
		}
	}
    
    //close connection to database
    public static void closeConnection() {
		if( con != null ) {
			try { 
				con.close();
				stmt.close();
			} catch( SQLException e ) {
				e.printStackTrace();
				System.out.println("SQL Exception occurred when closing the connection.");
			}
		}
	}
    
    public static Connection createConnection() {
        
        //user name and password
        String user = "itp220";
        String pass = "itp220";
        
        
		try {	
		//	System.out.println("What is the username for the database?");
		//	String user = scan.nextLine();
		//	System.out.println("What is the password?");
		//	String pass = scan.nextLine();
			con=  JDBCConnection.connect(JDBCConnection.MYSQLLOCAL,user,pass);
		} catch (IllegalStateException e) {
        	// Scanner is closed. Open a new one.
        	scan = new Scanner(System.in);
        } catch (NoSuchElementException e) {
        	// If the person enters CTRL-Z this exception occurs.
        //	WorldSeriesDriver.writeRecoveryFilesAndShutDown();
		}
		return con;
	}

     
    //create database method
     public static void createDatabase() {
         
         con = null;
         stmt = null;
         
         createDBString = "CREATE DATABASE IF NOT EXISTS messageboard";
         
         try { 
             
             checkConnect();
             stmt.executeUpdate(createDBString);
         }
         
         catch (SQLException e) {
                        
            //catch exception 
            System.out.println("The game table does not exist!");
        }
        
  
     }
     
     //create tables for database
     public static void createTables () {
        
        con = null;
        stmt = null;
       
        //SQL string to create users table
        createUsersString = "CREATE TABLE IF NOT EXISTS users "
                + "(userID int(11) NOT NULL AUTO_INCREMENT,"
                + "userName varchar(30) NOT NULL,"
                + "userPassword varchar(30) NOT NULL,"
                + "userLoggedIN boolean DEFAULT FALSE,"
                + "PRIMARY KEY (userID))"
                + "ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
        
        //SQL string to create postindex table
        createPostIndexString = "CREATE TABLE IF NOT EXISTS postindex "
                + "(postID int(11) NOT NULL AUTO_INCREMENT,"
                + "postTitle varchar(30) NOT NULL,"
                + "postCreatorName varchar(30) NOT NULL,"
                + "postCreatorID int(11) NOT NULL,"
                + "postMessage varchar(300) NOT NULL,"
                + "postRating int(11) DEFAULT 0,"
                + "numberOfRatings int(11) DEFAULT 0,"
                + "postDate DATE NOT NULL,"
                + "PRIMARY KEY (postID))"
                + "ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
        
        /**
        alterString = "ALTER TABLE game ADD CONSTRAINT lossTeam FOREIGN KEY"
                + "(lossTeam) REFERENCES team (teamID),"
                + "ADD CONSTRAINT winTeam FOREIGN KEY (`winTeam`) REFERENCES team (teamID)";
        **/
      
        //try creating tables
        try {
            
            checkConnect();
            // create the user and postindex tables
            
            stmt.executeUpdate(createUsersString);
            stmt.executeUpdate(createPostIndexString);
        //    stmt.executeUpdate(alterString);
            System.out.println("Tables created!");
            
        }
        catch (SQLException e) {
            
            System.out.println("execute update error!");
            System.out.print(e);
            
        }
 
    }
    
    public static void deleteTables() { 
        
     con=null;
     stmt=null;
     
     dropTableUsers = "DROP TABLE IF EXISTS users"; 
     String dropTablePostIndex = "DROP TABLE IF EXISTS postindex";
     
     
        try {
         
         checkConnect();
         
         stmt.executeUpdate(dropTableUsers);
         stmt.executeUpdate(dropTablePostIndex);
         
         for (int i=1; i<=100; i++ ) {
            
            String dropPostReplyTable = "DROP TABLE IF EXISTS postreply"+i;        
            stmt.executeUpdate(dropPostReplyTable);     
         }
         
        
         System.out.println("tables deleted!");
         
        }
        catch (SQLException e) {
            
            System.out.println("table deletion error!");
            System.out.print(e);
            
        }
     
        
    }
     
     //method to check if a user is logged in
    public static boolean isUserLoggedIN (String name) {
        
        //set logged in as falsee
        boolean loggedIN = false;
        
        //SQL string to check log in status for user
        String checkLogIN = "SELECT (userLoggedIN) FROM users WHERE userName = ?";
        
        //try querying database
        try {
            
            checkConnect();
            pstmt = con.prepareStatement(checkLogIN);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
            
            //get loggedIN variable from database for this user
            loggedIN = rs.getBoolean("userLoggedIN");
            
                if (loggedIN) {System.out.println("user is logged in!");}
                else {System.out.println("user is not logged in!");}
                
            
            }
            
        }
        catch (SQLException e) {
            
            System.out.println("execute query error!");
            System.out.print(e);
            
        }
        
        return loggedIN;
    } 
     
    
    //method to check if a user already exists in the database
    public static boolean doesUserExist(String name) {
        
        //set user found to false
        boolean userFound = false;
        
        //SQL string to check if user name exists
        String doesUserExist = "SELECT * FROM users WHERE userName = ?";
        
        //try to query database for user name
        try {
            
            checkConnect();
            pstmt = con.prepareStatement(doesUserExist);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            
            
            //if name found set to true
            if (rs.next()) {
                
                userFound = true;
                
            //if not found set to false    
            } else {
                
                userFound = false;
            }
            
        } 
        catch (SQLException e ) {
            
            System.out.println("execute query error!");
            System.out.print(e);
            
        }
        
        //return results of query
        return userFound;
    }
    
    //method used to add new user
    public static void addUser(String name, String password) {
        
        //SQL string to add new user into users database (also checks for duplicates)
        String addUserString =  "INSERT INTO users (userName, userPassword) SELECT * FROM (SELECT ?, ?) AS tmp WHERE NOT EXISTS (SELECT userName FROM users WHERE userName = ? ) LIMIT 1";
       
    //    String addUserString = "INSERT INTO users (userName, userPassword) VALUES (? , ?)";
        
        //try to add new user to database
        try {
            
            checkConnect();
            pstmt = con.prepareStatement(addUserString);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.executeUpdate();
            
           // stmt.executeUpdate(addUserString);
            
            
            System.out.println("New User added!");
            
            
            
        } catch (SQLException e) {
            
            System.out.println("execute update error!");
            System.out.print(e);
        }
        
        
    } 
    
    //method used to check if password entered is correct
    public static boolean checkPassword (String name, String password) {
        
        //list and initialize local variables
        String realPass=null;
        
        //set pass word correct to false
        boolean passwordOK = false;
        
        //SQL string to check to get password for user name
        String check = "SELECT userPassword FROM users WHERE userName = ?";
        
        //try to get password based on user name
        try {
            
            checkConnect();
            pstmt = con.prepareStatement(check);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            
           if(rs.next()) {
            
            //assign password to string
            realPass = rs.getString("userPassword");
           
           }
            //if password matches set to true
             if (realPass.equals(password)) {
                
                System.out.println("password is correct!"); 
                passwordOK = true;
            
            //if password does not match set to false    
            } else {
                 
                System.out.println("password is incorrect!");
                passwordOK = false;
            }
            
        }
        catch (SQLException e) {
            
            System.out.println("execute query error!");
            System.out.print(e);
        }
        
        //return results of query 
        return passwordOK;
    }
    
    //method used to log in a user
    public static void logINUser (String name, String password) {
        
        //check if password is correct
        boolean checkPass = checkPassword (name, password);
        
        System.out.println("checkpass =" + checkPass);
     //   System.out.println(name);
        
        //if password is incorrect return
        if (checkPass == false) {
     
            return;
        }
        //if password is correct log user in 
        else { 
            
            //SQL string to set logged in to true
            String login = "UPDATE users SET userLoggedIN = 1 WHERE userName = ?";
        
            //try to update table 
            try {
                
                checkConnect();
                pstmt = con.prepareStatement(login);
             //   pstmt.setInt(1, 1);
                pstmt.setString(1, name);
                pstmt.executeUpdate();
        
            System.out.println("User is now logged in!");
        
            }
            catch (SQLException e) {
            
                System.out.println("execute login error!");
                System.out.print(e);
            }
            
        }
    }
    
    //method used to log out a user
    public static void logOutUser (String user) {
        
          //assume no password is required to log out a user
     //   boolean checkPass = checkPassword (name, password);
            
            //SQL string to change logged in to false
            String logout = "UPDATE users SET userLoggedIN = 0 WHERE userName = ?";
        
            //try to update logged in variablee for user
            try {
                checkConnect();
                pstmt = con.prepareStatement(logout);
             //   pstmt.setInt(1, 1);
                pstmt.setString(1, user);
                pstmt.executeUpdate();
        
            System.out.println("User is now logged out!");
        
            }
            catch (SQLException e) {
            
                System.out.println("execute logout error!");
                System.out.print(e);
            }
          
    }
    
    //method used to add a new Topic to the database
    public static void addPost (String title, String userName, int userID, String message) {
        
        //get the current data 
        java.sql.Date date = getCurrentJavaSqlDate();
        
        //check if the user is logged in
        Boolean log = isUserLoggedIN(userName);
        
        //list & initializ local variable
        int postNum = 0;
        
        //if not logged in disregard
        if (log == false ) {
            
            // user not logged in
            System.out.println("User not logged in!!");
            
            return;
            
        //if user is logged in add new post     
        } else {
            
            //try to add new post to postindex
            try { 
            
                //SQL string to add new post to postindex
                String addNewPost = "INSERT INTO postindex (postTitle, postCreatorName, postCreatorID, postMessage, postDate) VALUES (?,?,?,?,?)";
        
                checkConnect();
                pstmt = con.prepareStatement(addNewPost);
                pstmt.setString(1, title);
                pstmt.setString(2, userName);
                pstmt.setInt(3, userID);
                pstmt.setString(4, message);
             //   pstmt.setDate(5,date.valueOf("1998-1-17"));
                pstmt.setDate(5,date);
                pstmt.executeUpdate();
                
                System.out.println("New Topic Added!");
                
            }
            catch (SQLException e ) {
                
                System.out.println("execute update error!");
                System.out.print(e);
                
                
            }
            
            //SQL string to get id of last inserted record (postID)
            String getPostID = "SELECT LAST_INSERT_ID()"; 
         
            try {
                
                checkConnect();
                pstmt = con.prepareStatement(getPostID);
                rs = pstmt.executeQuery();
               
                
                if(rs.next()) {
                
                //assign last id to postID for new table creation
                postNum = rs.getInt("last_insert_id()");
                
                }
                
                System.out.println("post# =" + postNum);
                
                //create a seperate table for this post to hold replies
                createReplyTable(postNum);
            }
            catch (SQLException e ) {
                
                System.out.println("execute query error!");
                System.out.print(e);
                
                
            }
            
            
        }
    }
    
    //method used to edit a Topic the database
    public static void editPost (int postNum, String title, String userName, String message) {
        
        //get the current data 
        java.sql.Date date = getCurrentJavaSqlDate();
        
        //check if the user is logged in
        Boolean log = isUserLoggedIN(userName);
        
        //if not logged in disregard
        if (log == false ) {
            
            // user not logged in
            System.out.println("User not logged in!!");
            
            return;
            
        //if user is logged in add new post     
        } else {
            
            //try to add new post to postindex
            try { 
            
                //SQL string to add new post to postindex
                String topicEdit = "UPDATE postindex SET postTitle=?, postMessage=? WHERE postID=?";
        
                checkConnect();
                pstmt = con.prepareStatement(topicEdit);
                pstmt.setString(1, title);
                pstmt.setString(2, message);
                pstmt.setInt(3, postNum);
               
                pstmt.executeUpdate();
                
                System.out.println("Topic Edit Success!");
                
            }
            catch (SQLException e ) {
                
                System.out.println("execute update error!");
                System.out.print(e);
                
                
            }
            
          
        }
    }
    
    
    //method used to add replies to a message post
     public static void addReply (int topicID, String title, String message, String userName) {
        
        // get the current data
        java.sql.Date date = getCurrentJavaSqlDate();
        
        //check if the user is logged in 
        Boolean log = isUserLoggedIN(userName);
        
        //if the user is not logged in then return
        if (log == false ) {
            
            // user not logged in
            System.out.println("User not logged in!!");
            
            return;
        
        //if the user is logged in then add the reply    
        } else {
        
            
            try { 
                
                //use postID to get name of new reply table
                String table = "postreply"+topicID;
                
                 //SQL string to insert new reply into table 
                String addNewPost = "INSERT INTO " +table+ " (replyTitle, replyMessage, replyCreator, replyDate, topicID) VALUES (?,?,?,?,?)";
        
                checkConnect();
                pstmt = con.prepareStatement(addNewPost);
                pstmt.setString(1, title);
                pstmt.setString(2, message);
                pstmt.setString(3, userName);
             //   pstmt.setDate(5,date.valueOf("1998-1-17"));
                pstmt.setDate(4,date);
                pstmt.setInt(5, topicID);
                pstmt.executeUpdate();
                
                System.out.println("New Reply Added!");
                
            }
            catch (SQLException e ) {
                
                System.out.println("execute add reply update error!");
                System.out.print(e);
                
                
            }
            
            
        }
    }
    
    //method used to add replies to a message post
     public static void editReply (int topicID, int replyID, String title, String message, String userName) {
        
        // get the current data
        java.sql.Date date = getCurrentJavaSqlDate();
        
        //check if the user is logged in 
        Boolean log = isUserLoggedIN(userName);
        
        //if the user is not logged in then return
        if (log == false ) {
            
            // user not logged in
            System.out.println("User not logged in!!");
            
            return;
        
        //if the user is logged in then add the reply    
        } else {
        
            
            try { 
                
                //use postID to get name of new reply table
                String table = "postreply"+topicID;
                
                 //SQL string to insert new reply into table 
            //    String addNewPost = "INSERT INTO " +table+ " (replyTitle, replyMessage, replyCreator, replyDate, topicID) VALUES (?,?,?,?,?)";
                String editPost = "UPDATE " +table+ " SET replyTitle=?, replyMessage=? WHERE replyID=?";
           
                checkConnect();
                pstmt = con.prepareStatement(editPost);
                pstmt.setString(1, title);
                pstmt.setString(2, message);
                pstmt.setInt(3, replyID);
                pstmt.executeUpdate();
                
                System.out.println("New Reply Added!");
                
            }
            catch (SQLException e ) {
                
                System.out.println("execute add reply update error!");
                System.out.print(e);
                
                
            }
            
            
        }
    } 
     
     
     //method used to create a reply table for each new post
    public static void createReplyTable (int postNum) {
        
         //create table name based upon postID
         String tableName = "postreply"+postNum;
        
         //SQL string to create a new reply table
         createPostReplyTable = "CREATE TABLE IF NOT EXISTS "+ tableName+" "
                + "(replyID int(11) NOT NULL AUTO_INCREMENT,"
                + "replyTitle varchar(30) NOT NULL," 
                + "replyMessage varchar(300) NOT NULL,"
                + "replyCreator varchar(30) NOT NULL,"
                + "replyDate date NOT NULL," 
                + "topicID int(11) NOT NULL,"  
                + "PRIMARY KEY (replyID))"
                + "ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
         
         try {
             
             checkConnect();
             pstmt = con.prepareStatement(createPostReplyTable);
           //  pstmt.setString(1, tableName);
             pstmt.executeUpdate();
             
         } 
         catch (SQLException e ) {
                
                System.out.println("execute query error!");
                System.out.print(e);
                
                
         }
         
      
    }
    
    //method to view all posts by a specific user
    public static ArrayList<MessageTopic> viewByUser (String user) {
        
        //initialize arraylist to hold topics
        ArrayList<MessageTopic> messageIndex = new ArrayList<>();
        
        //SQL string to get all posts for a user from postindex table
        String viewUser = "SELECT * FROM postindex WHERE postCreatorName = ?";
        
        //try to query databased 
        try {
            
            checkConnect();
            pstmt = con.prepareStatement(viewUser);
            pstmt.setString(1, user);
            rs = pstmt.executeQuery();
            
            //while we have results get data from each line
            while (rs.next()) {
                
                int postnum = rs.getInt("postID");
                String title = rs.getString("postTitle");
                String creator = rs.getString("postCreatorName");
                int creatorID = rs.getInt("postCreatorID");
                String message = rs.getString("postMessage");
                int rating = rs.getInt("postRating");
                int noRate = rs.getInt("numberOfRatings");
                Timestamp timestamp = rs.getTimestamp("postDate");
         
             //   Date date = rs.getObject("postDate", Date.valueOf(LocalDate.MIN));
                
                //create a messagetopic object
                MessageTopic topic = new MessageTopic (postnum, title, creator, creatorID, message, rating, noRate, timestamp);
                
                //add new object to arraylist
                messageIndex.add(topic);
               
                
                
            }
            
        }
         catch (SQLException e ) {
                
                System.out.println("execute view by user query error!");
                System.out.print(e);
                
                
         }
        //return arraylist of message topics
        return messageIndex;
    }
    
    
    //method used to get all message posts by a certain topic
    public static ArrayList<MessageTopic> viewByTopic (String postTopic) {
        
        //initialize arraylist to hold message topics
        ArrayList<MessageTopic> messageIndex = new ArrayList<>();
        
        //SQL string to get all matching topics by title
        String viewUser = "SELECT * FROM postindex WHERE postTitle = ?";
        
        //try to query database 
        try {
            
            checkConnect();
            pstmt = con.prepareStatement(viewUser);
            pstmt.setString(1, postTopic);
            rs = pstmt.executeQuery();
            
            //while we have reulsts assign data to variables
            while (rs.next()) {
                
                int postnum = rs.getInt("postID");
                String title = rs.getString("postTitle");
                String creator = rs.getString("postCreatorName");
                int creatorID = rs.getInt("postCreatorID");
                String message = rs.getString("postMessage");
                int rating = rs.getInt("postRating");
                int noRate = rs.getInt("numberOfRatings");
                Timestamp timestamp = rs.getTimestamp("postDate");
                
         
             //   Date date = rs.getObject("postDate", Date.valueOf(LocalDate.MIN));
                
                //create new messagetopic object
                MessageTopic topic = new MessageTopic (postnum, title, creator, creatorID, message, rating, noRate, timestamp);
                
                //add object to arraylist
                messageIndex.add(topic);
        
            }
            
        }
         catch (SQLException e ) {
                
                System.out.println("execute view by user query error!");
                System.out.print(e);
                
         }
        //return arraylist 
        return messageIndex;
    }
    
    //method used to view all message posts created by date
    public static ArrayList<MessageTopic> viewByDate () {
        
        //initialize arraylist to hold message topics
        ArrayList<MessageTopic> messageIndex = new ArrayList<>();
        
        //SQL string to find all posts by date in descending order 
        String viewByDate = "SELECT * FROM postindex ORDER BY postDate DESC";
        
        //try to query the database 
        try {
            
            checkConnect();
            pstmt = con.prepareStatement(viewByDate);
            rs = pstmt.executeQuery();
            
            //while we have results assign them to variables
            while (rs.next()) {
                
                int postnum = rs.getInt("postID");
                String title = rs.getString("postTitle");
                String creator = rs.getString("postCreatorName");
                int creatorID = rs.getInt("postCreatorID");
                String message = rs.getString("postMessage");
                int rating = rs.getInt("postRating");
                int noRate = rs.getInt("numberOfRatings");
                Timestamp timestamp = rs.getTimestamp("postDate");
         
             //   Date date = rs.getObject("postDate", Date.valueOf(LocalDate.MIN));
                
                //create a new messagetopic object
                MessageTopic topic = new MessageTopic (postnum, title, creator, creatorID, message, rating, noRate, timestamp);
                
                //add new object to arraylist
                messageIndex.add(topic);
        
            }
            
        }
         catch (SQLException e ) {
                
                System.out.println("execute view by user query error!");
                System.out.print(e);
                
         }
        
        //return arraylist of message topics
        return messageIndex;
    }
    
    //method used to get SQL format date 
    public static java.sql.Date getCurrentJavaSqlDate() {
        
        java.util.Date today = new java.util.Date();
        
        return new java.sql.Date(today.getTime());
  }

    //method used to delete a message post 
    public static void deletePost (int postNum, String userName, String userPassword ) {
    
        //set delete OK to false
        boolean deleteOK = false;
        
        //check user  password to see if correct
        deleteOK = checkPassword(userName, userPassword);
        
        //if password correct proceeed 
        if (deleteOK) {
    
        //get table name based upon postID    
        String tableName = "postreply"+postNum;    
        
        //SQL string to delete topic from postindex
        String deleteIndex = "DELETE FROM postindex WHERE postID = ? AND postCreatorName = ?";
        
        //SQL string to drop reply table associated with postID
        String deletePost = "DROP TABLE "+tableName;
        
        //try to delete the topic from postindex
        try {
          
            checkConnect();
            pstmt = con.prepareStatement(deleteIndex);
            pstmt.setInt(1, postNum);
            pstmt.setString(2, userName);
            pstmt.executeUpdate();
            
            
            
        }
        catch (SQLException e ) {
                
                System.out.println("execute delete index update error!");
                System.out.print(e);
         }
        
         //try to delete the reply table
         try {
             
             stmt = con.createStatement();
             stmt.executeUpdate(deletePost);
             
         }  
        catch (SQLException e ) {
                
                System.out.println("execute delete post update error!");
                System.out.print(e);
         }
        
        }
        
        else {
            
            System.out.println("incorrect user name or password");
            
        }
    }  
    
    //methode used to view a specific message and all replies
    public static ArrayList<MessagePost> viewChosenPost (int postID) {
        
        //list all local variables 
        ArrayList<MessagePost> messagePost = new ArrayList<>();
        int postnum;
        int topicnum;
        String title;
        String creator;
        String message;
        Timestamp timestamp;
        MessagePost post;
  
        //SQL string to get the message from postindex table
        String getTopic = "SELECT * FROM postindex WHERE postID = ?";
        
        //SQL string to get all data from associated reply table
        String viewPost = "SELECT * FROM postreply"+postID;
        
        //try to get topic from postindex and postreply tables
        try {
            
        //    checkConnect();
        //    pstmt = con.prepareStatement(getTopic);
        //    pstmt.setInt(1, postID);
        //    rs = pstmt.executeQuery();
            
            //while there is results get the data 
        //    while (rs.next()) {
            
        //        postnum = rs.getInt("postID");
        //        title = rs.getString("postTitle");
        //        creator = rs.getString("postCreatorName");
        //        message = rs.getString("postMessage");
        //        timestamp = rs.getTimestamp("postDate");
                
                //create a new messagepost object
        //        post = new MessagePost (postnum, title, creator, message, timestamp);
                
                //add new object to arraylist
        //        messagePost.add(post);
        //     }   
                
            checkConnect();
            pstmt = con.prepareStatement(viewPost);
            rs = pstmt.executeQuery();
            
            
            //while there is results get the data 
            while (rs.next()) {
                
                postnum = rs.getInt("replyID");
                title = rs.getString("replyTitle");
                message = rs.getString("replyMessage");
                creator = rs.getString("replyCreator");
                timestamp = rs.getTimestamp("replyDate");
                topicnum = rs.getInt("topicID");
                
                //create a new messagepost object
                post = new MessagePost (postnum, title, creator, message, timestamp, topicnum);
                
                //add new object to the arraylist
                messagePost.add(post);
                
            }
            
        }
         catch (SQLException e ) {
                
                System.out.println("execute view chosen post query error!");
                System.out.print(e);
                
                
         }
        
        //return array of messagepost
        return messagePost;
    }
    
    //method used to rate a post
    public static void ratePost (int rating, int postID) {
        
        //initialize local variables
        int curRating = 0;
        int numRatings = 0;
        
        //SQL string to get # of ratings 
        String getNumRatings = "SELECT numberOfRatings FROM postindex WHERE postID = ?";
        
        //SQL string to get current rating
        String getCurrentRating = "SELECT postRating FROM postindex WHERE postID = ?";        
     
        //try to query database table for rating and # of ratings
        try{ 
            
            checkConnect();
            pstmt = con.prepareStatement(getNumRatings);
            pstmt.setInt(1, postID);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
            numRatings = rs.getInt("numberOfRatings");
            }
            
            pstmt = con.prepareStatement(getCurrentRating);
            pstmt.setInt(1, postID);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
            curRating = rs.getInt("postRating");
            }
           
 
        }   catch (SQLException e ) {
                
                System.out.println("execute delete post update error!");
                System.out.print(e);
         }  
        
         //calculate grand total
         double grandtotal = curRating * numRatings; 
        
        //increase #ratings by 1
         numRatings++;
         
         //add current rating to existing 
         grandtotal+=rating;
         
         //calculate new rating 
         int newRating = (int)(grandtotal/numRatings); 
         
         //SQL string to update post rating
         String updateRating = "UPDATE postindex SET postRating = ? WHERE postID = ?";
         
         //SQL string to update # of ratings
         String updateNumRatings = "UPDATE postindex SET numberOfRatings = ? WHERE postID = ?";
         
         //try to update database table
         try { 
             
             //update rating 
             checkConnect();
             pstmt = con.prepareStatement(updateRating);
             pstmt.setInt(1, newRating);
             pstmt.setInt(2, postID);
             pstmt.executeUpdate();
             
             //update # of ratings
             pstmt = con.prepareStatement(updateNumRatings);
             pstmt.setInt(1, numRatings);
             pstmt.setInt(2, postID);
             pstmt.executeUpdate();
             
             
             
         } catch (SQLException e ) {
                
                System.out.println("execute delete post update error!");
                System.out.print(e);
         }    
    }
}
