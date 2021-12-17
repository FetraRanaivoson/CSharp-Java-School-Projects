package com.util;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Part;
import javax.sql.DataSource;

public class accountDbUtil {
	private DataSource dataSource;

	public accountDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// List of ACCOUNTS
	public List<Account> getAccounts() throws Exception {
		List<Account> accounts = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// Connection
			myConn = dataSource.getConnection();
			// Sql statement
			String sql = "SELECT * FROM account";
			myStmt = myConn.createStatement();
			// Execute sql query
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				// Getting the data
				int ProfileID = myRs.getInt("ProfileID");
				String FirstName = myRs.getString("FirstName");
				String LastName = myRs.getString("LastName");
				String Password = myRs.getString("Password");
				String MailAddress = myRs.getString("MailAddress");
				Date DateCreated = myRs.getDate("DateCreated");
				Date DateDeleted = myRs.getDate("DateDeleted");
				int ZipCode = myRs.getInt("ZipCode");

				// Create Account object
				Account account = new Account(ProfileID, FirstName, LastName, Password, MailAddress, DateCreated,
						DateDeleted, ZipCode);

				// Add the accounts to the list
				accounts.add(account);
			}
			return accounts;
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	// List of FEEDS
	public List<Feed> getFeeds() throws Exception {
		List<Feed> feeds = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// Connection
			myConn = dataSource.getConnection();
			// Sql statement
			String sql = "SELECT * FROM feed ORDER BY FeedCreated desc";
			myStmt = myConn.createStatement();
			// Execute sql query
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				// Getting the data
				int FeedID = myRs.getInt("FeedID");
				int ProfileID = myRs.getInt("ProfileID");
				Date FeedCreated = myRs.getDate("FeedCreated");
				Date FeedModified = myRs.getDate("FeedModified");
				Date FeedDeleted = myRs.getDate("FeedDeleted");
				String FeedContent = myRs.getString("FeedContent");
				
				Timestamp timestamp = new Timestamp(FeedCreated.getTime()); 
				LocalDateTime ldt = timestamp.toLocalDateTime();
				
				DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm");
				String formattedFeedCreated = ldt.format(myFormatObj);
				
				
				// Create Feed object
				Feed theFeed = new Feed(FeedID, ProfileID, formattedFeedCreated, FeedModified, FeedDeleted, FeedContent);

				// Add the accounts to the list
				feeds.add(theFeed);
			}
			return feeds;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null)
				myRs.close();
			if (myStmt != null)
				myStmt.close();
			if (myConn != null)
				myConn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// CHECK ACCOUNT
	public boolean isUserRegistered(String email, String password) throws SQLException {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// Connection
			myConn = dataSource.getConnection();

			// Sql statement
			String sql = "SELECT * FROM account WHERE MailAddress='" + email + "'" + " AND Password='" + password + "'";

			// Statement
			myStmt = myConn.prepareStatement(sql);

			// Set parameters
			// myStmt.setString(1, email);
			// myStmt.setString(2, password);

			// Execute
			myRs = myStmt.executeQuery(sql);

			// Check conditions
			if (myRs.next()) {
				return true;
			} else {
				return false;
			}

		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	// GET PARTICULAR USER
	public Account GetAccount(String email, String password) throws SQLException {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// Connection
			myConn = dataSource.getConnection();

			// Sql statement
			String sql = "SELECT * FROM account WHERE MailAddress='" + email + "'" + " AND Password='" + password + "'";

			// Statement
			myStmt = myConn.prepareStatement(sql);

			// Execute
			myRs = myStmt.executeQuery(sql);

			// Create the account object if exists
			Account account = null;
			if (myRs.next()) {
				int ProfileID = myRs.getInt("ProfileID");
				String FirstName = myRs.getString("FirstName");
				String LastName = myRs.getString("LastName");
				String Password = myRs.getString("Password");
				String MailAddress = myRs.getString("MailAddress");
				Date DateCreated = myRs.getDate("DateCreated");
				Date DateDeleted = myRs.getDate("DateDeleted");
				int ZipCode = myRs.getInt("ZipCode");
				account = new Account(ProfileID, FirstName, LastName, Password, MailAddress, DateCreated, DateDeleted,
						ZipCode);
			}
			return account;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void AddPost(int profileID, String feedContent) throws SQLException {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// Connection
			myConn = dataSource.getConnection();

			// Sql statement
			String sql = "INSERT INTO feed " 
						+ "(ProfileID, FeedCreated, FeedContent) " 
						+ "VALUES(?, CURRENT_TIMESTAMP, ?) ";

			// Statement
			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, profileID);
			myStmt.setString(2, feedContent);

			myStmt.execute();

		} finally {
			close(myConn, myStmt, null);
		}
	}
	
	
	public void LikePost(int feedID, int likerID) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// Connection
			myConn = dataSource.getConnection();
			
			// Sql statement
			String sql = "INSERT INTO feedreactions " 
						+ "(FeedID, ProfileID_of_liker, LikeDate) " 
						+ "VALUES(?, ?, CURRENT_TIMESTAMP) ";
			
			// Statement
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, feedID);
			myStmt.setInt(2, likerID);
			
			myStmt.execute();
			
		} finally {
			close(myConn, myStmt, null);
		}
	}
	
	public List <Likes> getAllLikes () throws SQLException {
		List<Likes> likes = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// Connection
			myConn = dataSource.getConnection();
			// Sql statement
			String sql = "SELECT * FROM feedreactions";
			myStmt = myConn.createStatement();
			// Execute sql query
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				// Getting the data
				int FeedID = myRs.getInt("FeedID");
				int profileIdOfLiker = myRs.getInt("ProfileId_of_liker");
				Date likeDate = myRs.getDate("LikeDate");

				// Create Likes object
				Likes like= new Likes (FeedID, profileIdOfLiker, likeDate);

				// Add the likes to the list
				likes.add(like);
			}
			return likes;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	
	
	public void AddPostWithPhoto(int profileID, Date feedCreated, 
			String feedContent, Part filePart) throws SQLException, IOException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		InputStream inputStream = null;
		
		if (filePart != null) {
			inputStream = filePart.getInputStream();
		}
		
		try {
			// Connection
			myConn = dataSource.getConnection();
			
			// Sql statement
			String sql = "INSERT INTO feed "
						+ "(ProfileID, FeedCreated, FeedContent, FeedPhotos) "
						+ "VALUES(?, ?, ?, ?) "; 
			
			// Statement
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, profileID);
			myStmt.setDate(2, new java.sql.Date(feedCreated.getTime()));
			myStmt.setString(3, feedContent);
			
			
			if (inputStream != null) {
				myStmt.setBlob(4, inputStream);
			}
			
			myStmt.execute();
			
		} finally {
			close(myConn, myStmt, null);
		}
	}
	

	// GET THE USER(FIRST, LAST NAME) WHO POSTS A FEED
	public Account GetAccount(int feedID) throws SQLException {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// Connection
			myConn = dataSource.getConnection();

			// Sql statement
			String sql = "SELECT * " + "FROM feed JOIN account " + "ON account.ProfileID = feed.ProfileID "
					+ "WHERE FeedID = ?";

			// Statement
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, feedID);

			// Execute
			myRs = myStmt.executeQuery(sql);

			// Create the account object if exists
			Account account = null;

			if (myRs.next()) {
				int ProfileID = myRs.getInt("ProfileID");
				String FirstName = myRs.getString("FirstName");
				String LastName = myRs.getString("LastName");
				String Password = myRs.getString("Password");
				String MailAddress = myRs.getString("MailAddress");
				Date DateCreated = myRs.getDate("DateCreated");
				Date DateDeleted = myRs.getDate("DateDeleted");
				int ZipCode = myRs.getInt("ZipCode");
				account = new Account(ProfileID, FirstName, LastName, Password, MailAddress, DateCreated, DateDeleted,
						ZipCode);
			}
			return account;

		} finally {
			close(myConn, myStmt, null);
		}

	}

	public void DeletePost(int feedId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			//Get connection
			myConn  = dataSource.getConnection();
			//Sql statement
			String sql = "DELETE FROM feed WHERE FeedID=?";
			//Prepare statement
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, feedId);
			//Execute Sql
			myStmt.execute();
			
		} finally {
			close(myConn, myStmt, null);
		}

	}
	
	
	//LIST OF COMMENT
	public List<Comment> getComments() throws Exception {
		List<Comment> comments = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// Connection
			myConn = dataSource.getConnection();
			// Sql statement
			String sql = "SELECT * FROM commentaries";
			myStmt = myConn.createStatement();
			// Execute sql query
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				// Getting the data
				int CommentID = myRs.getInt("CommentID");
				int ProfileID_of_comment = myRs.getInt("ProfileID_of_comment");
				int FeedID = myRs.getInt("FeedID");
				String CommentContent = myRs.getString("CommentContent");
				Date CommentDate = myRs.getDate("CommentDate");
				
				Timestamp timestamp = new Timestamp(CommentDate.getTime()); 
				LocalDateTime ldt = timestamp.toLocalDateTime();
				
				DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm");
				String formattedCommentDate = ldt.format(myFormatObj);
				
				
				
				
				// Create Account object
				Comment comment = new Comment(CommentID,ProfileID_of_comment,FeedID,CommentContent,formattedCommentDate);

				// Add the accounts to the list
				comments.add(comment);
			}
			return comments;
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	//COMMENT A POST
	public void CommentPost(int profileID, int feedID, String commentContent) 
			throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// Connection
			myConn = dataSource.getConnection();

			// Sql statement
			String sql = "INSERT INTO commentaries " 
					+ "(ProfileID_of_comment, FeedID, CommentContent, CommentDate) " 
					+ "VALUES(?, ?, ?, CURRENT_TIMESTAMP) ";
		
			// Statement
			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, profileID);
			myStmt.setInt(2, feedID);
			myStmt.setString(3, commentContent);

			myStmt.execute();

		} finally {
			close(myConn, myStmt, null);
		}
		
	}
	
	public void DeleteComment (int commentID) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// Connection
			myConn = dataSource.getConnection();
			
			// Sql statement
			String sql = "DELETE FROM commentaries "
						+ "WHERE CommentID=? ";
			// Statement
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, commentID);

			myStmt.execute();
		} finally {
			close(myConn, myStmt, null);
		}
	}
	
	

	public void SignUser(String firstName, String lastName, String email, String password) throws SQLException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// Connection
			myConn = dataSource.getConnection();
			
			// Sql statement
			String sql = "INSERT INTO account " 
					+ "(FirstName, LastName, MailAddress, Password, DateCreated) " 
					+ "VALUES(?, ?, ?, ?, CURRENT_TIMESTAMP) ";

			// Statement
			myStmt = myConn.prepareStatement(sql);

			myStmt.setString(1, firstName);
			myStmt.setString(2, lastName);
			myStmt.setString(3, email);
			myStmt.setString(4, password);

			myStmt.execute();
			
		} finally {
			close(myConn, myStmt, null);
		}
	}
	
	
	
}
