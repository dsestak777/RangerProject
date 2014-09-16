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
    
    private static Connection con;
    private static Statement stmt;
    private static Scanner scan = new Scanner(System.in);
    private static String createDBString;
    private static String createUsersString;
    private static String createPostIndexString;
    private static String createPostReplyTable;
    private static String createString1;
    private static String createString2;
    private static String dropString1;
    private static String dropString2;
    private static String alterString;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
    private static CallableStatement ca;
    private static ArrayList<MessageTopic> topics = new ArrayList<>();
    
    
    
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
        
     //   addPost("First Post", "test2", 1, "ALL YOUR BASE ARE BELONG TO US");
     //   addPost("Second Post", "test1", 2, "THE RAIN IN SPAIN");
        
      //  topics = viewByUser("test2");
        
      //  for (int i = 0; i<topics.size(); i++) {
            
     //       System.out.println(topics.get(i).toString());
     //   }
        
        topics = viewByTopic("First Post");
        
        for (int i = 0; i<topics.size(); i++) {
            
            System.out.println(topics.get(i).toString());
        }
        
        deletePost(2,"test2","123");
        
    }
    
    
    public static void checkConnect() {
		if (con==null) {
			con=createConnection();
		}
		if(stmt==null) {
			try {
				stmt = con.createStatement();
			} catch (SQLException e){
				System.out.println("SQL Error: Cannot create the statement.");
			}					
		}
	}
    
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
		try {	
			System.out.println("What is the username for the database?");
			String user = scan.nextLine();
			System.out.println("What is the password?");
			String pass = scan.nextLine();
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
     
     public static void createTables () {
        con = null;
        stmt = null;
        
     //   dropString1 = "Drop table if exists game";
     //   dropString2 = "Drop table if exists team";
        
        createUsersString = "CREATE TABLE IF NOT EXISTS users "
                + "(userID int(11) NOT NULL AUTO_INCREMENT,"
                + "userName varchar(30) NOT NULL,"
                + "userPassword varchar(30) NOT NULL,"
                + "userLoggedIN boolean DEFAULT FALSE,"
                + "PRIMARY KEY (userID))"
                + "ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";
        
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
        

       
        
        try {
            
            checkConnect();
            // create the game and team tables
            
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
    
    public static boolean isUserLoggedIN (String name) {
        
        boolean loggedIN = false;
        
        String checkLogIN = "SELECT (userLoggedIN) FROM users WHERE userName = ?";
        
        try {
            
            stmt = con.createStatement();
            pstmt = con.prepareStatement(checkLogIN);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            
            rs.next();
            
            loggedIN = rs.getBoolean("userLoggedIN");
            
        }
        catch (SQLException e) {
            
            System.out.println("execute query error!");
            System.out.print(e);
            
        }
        
        return loggedIN;
    } 
     
    public static boolean doesUserExist(String name) {
        
        boolean userFound = false;
        
        String doesUserExist = "SELECT * FROM users WHERE userName = ?";
        
        try {
            
            stmt = con.createStatement();
            pstmt = con.prepareStatement(doesUserExist);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            //rs.next();
            
            if (rs.next()) {
                
                userFound = true;
                
            } else {
                
                userFound = false;
            }
            
        } 
        catch (SQLException e ) {
            
            System.out.println("execute query error!");
            System.out.print(e);
            
        }
        
        return userFound;
    }
    
    public static void addUser(String name, String password) {
        
        
        String addUserString =  "INSERT INTO users (userName, userPassword) SELECT * FROM (SELECT ?, ?) AS tmp WHERE NOT EXISTS (SELECT userName FROM users WHERE userName = ? ) LIMIT 1";
       
    //    String addUserString = "INSERT INTO users (userName, userPassword) VALUES (? , ?)";
        
        try {
            
            stmt = con.createStatement();
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
    
    public static boolean checkPassword (String name, String password) {
        
        boolean passwordOK = false;
        
        String check = "SELECT userPassword FROM users WHERE userName = ?";
        
        try {
            
            stmt = con.createStatement();
            pstmt = con.prepareStatement(check);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            
            rs.next();
            
            String realPass = rs.getString("userPassword");
            
             if (realPass.equals(password)) {
                
                System.out.println("password is correct!"); 
                passwordOK = true;
             
            } else {
                 
                System.out.println("password is incorrect!");
                passwordOK = false;
            }
            
        }
        catch (SQLException e) {
            
            System.out.println("execute query error!");
            System.out.print(e);
        }
        
        return passwordOK;
    }
    
    
    public static void logINUser (String name, String password) {
        
        boolean checkPass = checkPassword (name, password);
        
        System.out.println("checkpass =" + checkPass);
     //   System.out.println(name);
        
        if (checkPass == false) {
     
            return;
        }
        else { 
            String login = "UPDATE users SET userLoggedIN = 1 WHERE userName = ?";
        
            try {
                stmt = con.createStatement();
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
    
    public static void addPost (String title, String userName, int userID, String message) {
        
       // java.util.Date today = Calendar.getInstance().getTime();
      //  Date date = new Date(00-00-0000);
      //  java.util.Date now = new java.util.Date();
         java.sql.Date date = getCurrentJavaSqlDate();
        
        Boolean log = isUserLoggedIN(userName);
        
        if (log == false ) {
            
            // user not logged in
            System.out.println("User not logged in!!");
            
            return;
            
        } else {
        
            try { 
            
                String addNewPost = "INSERT INTO postindex (postTitle, postCreatorName, postCreatorID, postMessage, postDate) VALUES (?,?,?,?,?)";
        
                stmt = con.createStatement();
                pstmt = con.prepareStatement(addNewPost);
                pstmt.setString(1, title);
                pstmt.setString(2, userName);
                pstmt.setInt(3, userID);
                pstmt.setString(4, message);
             //   pstmt.setDate(5,date.valueOf("1998-1-17"));
                pstmt.setDate(5,date);
                pstmt.executeUpdate();
                
                System.out.println("New Post Added!");
                
            }
            catch (SQLException e ) {
                
                System.out.println("execute update error!");
                System.out.print(e);
                
                
            }
            
            String getPostID = "SELECT LAST_INSERT_ID()"; 
            
            try {
                
                stmt = con.createStatement();
                pstmt = con.prepareStatement(getPostID);
                rs = pstmt.executeQuery();
                rs.next();
                
                int postNum = rs.getInt("last_insert_id()");
                
                System.out.println("post# =" + postNum);
                
                createReplyTable(postNum);
            }
            catch (SQLException e ) {
                
                System.out.println("execute query error!");
                System.out.print(e);
                
                
            }
            
            
        }
    }
    
    public static void createReplyTable (int postNum) {
        
        
         String tableName = "postreply"+postNum;
        
         createPostReplyTable = "CREATE TABLE IF NOT EXISTS "+ tableName+" "
                + "(postID int(11) NOT NULL,"
                + "replyMessage varchar(300) NOT NULL,"
                + "replyCreator varchar(30) NOT NULL,"
                + "PRIMARY KEY (postID))"
                + "ENGINE=InnoDB DEFAULT CHARSET=latin1";
         
         try {
             
             stmt = con.createStatement();
             pstmt = con.prepareStatement(createPostReplyTable);
           //  pstmt.setString(1, tableName);
             pstmt.executeUpdate();
             
         } 
         catch (SQLException e ) {
                
                System.out.println("execute query error!");
                System.out.print(e);
                
                
         }
         
      
    }
    
    public static ArrayList<MessageTopic> viewByUser (String user) {
        
        ArrayList<MessageTopic> messageIndex = new ArrayList<>();
        
        String viewUser = "SELECT * FROM postindex WHERE postCreatorName = ?";
        
        try {
            
            stmt = con.createStatement();
            pstmt = con.prepareStatement(viewUser);
            pstmt.setString(1, user);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                
                int postnum = rs.getInt("postID");
                String title = rs.getString("postTitle");
                String creator = rs.getString("postCreatorName");
                int creatorID = rs.getInt("postCreatorID");
                String message = rs.getString("postMessage");
                int rating = rs.getInt("postRating");
                int noRate = rs.getInt("numberOfRatings");
                Timestamp timestamp = rs.getTimestamp("postDate");
           //     String date = rs.getString("postDate");
           //     Timestamp timestamp = rs.getTimestamp(date);
         
             //   Date date = rs.getObject("postDate", Date.valueOf(LocalDate.MIN));
                
                MessageTopic topic = new MessageTopic (postnum, title, creator, creatorID, message, rating, noRate, timestamp);
                
                messageIndex.add(topic);
               
                
                
            }
            
        }
         catch (SQLException e ) {
                
                System.out.println("execute view by user query error!");
                System.out.print(e);
                
                
         }
        
        return messageIndex;
    }
    
    
    public static ArrayList<MessageTopic> viewByTopic (String postTopic) {
        
        ArrayList<MessageTopic> messageIndex = new ArrayList<>();
        
        String viewUser = "SELECT * FROM postindex WHERE postTitle = ?";
        
        try {
            
            stmt = con.createStatement();
            pstmt = con.prepareStatement(viewUser);
            pstmt.setString(1, postTopic);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                
                int postnum = rs.getInt("postID");
                String title = rs.getString("postTitle");
                String creator = rs.getString("postCreatorName");
                int creatorID = rs.getInt("postCreatorID");
                String message = rs.getString("postMessage");
                int rating = rs.getInt("postRating");
                int noRate = rs.getInt("numberOfRatings");
                Timestamp timestamp = rs.getTimestamp("postDate");
           //     String date = rs.getString("postDate");
           //     Timestamp timestamp = rs.getTimestamp(date);
         
             //   Date date = rs.getObject("postDate", Date.valueOf(LocalDate.MIN));
                
                MessageTopic topic = new MessageTopic (postnum, title, creator, creatorID, message, rating, noRate, timestamp);
                
                messageIndex.add(topic);
               
                
                
            }
            
        }
         catch (SQLException e ) {
                
                System.out.println("execute view by user query error!");
                System.out.print(e);
                
                
         }
        
        return messageIndex;
    }
    
    public static java.sql.Date getCurrentJavaSqlDate() {
        
        java.util.Date today = new java.util.Date();
        
        return new java.sql.Date(today.getTime());
  }

    
    public static void deletePost (int postNum, String userName, String userPassword ) {
    
        boolean deleteOK = false;
        
        deleteOK = checkPassword(userName, userPassword);
        
        if (deleteOK) {
    
        String tableName = "postreply"+postNum;    
            
        String deleteIndex = "DELETE FROM postindex WHERE postID = ? AND postCreatorName = ?";
        String deletePost = "DROP TABLE "+tableName;
        
        try {
          
            stmt = con.createStatement();
            pstmt = con.prepareStatement(deleteIndex);
            pstmt.setInt(1, postNum);
            pstmt.setString(2, userName);
            pstmt.executeUpdate();
            
            
            
        }
        catch (SQLException e ) {
                
                System.out.println("execute delete index update error!");
                System.out.print(e);
         }
        
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
      
}
