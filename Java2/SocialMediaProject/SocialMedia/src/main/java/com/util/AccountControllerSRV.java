package com.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

@WebServlet("/AccountControllerSRV")
public class AccountControllerSRV extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private accountDbUtil accountDbUtil;
	private String photoPath;
	// Defining data source/connection pool for resource injection
	@Resource(name = "jdbc/socialmedia")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			accountDbUtil = new accountDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// For the passwords and email
			// Set the content type
			response.setContentType("text/html");
			// Creating the printWriter object
			PrintWriter out = response.getWriter();

			// Read the command
			String theCommand = request.getParameter("command");
			if (theCommand == null) { // THIS IS LOGIN, LET'S SAY...
				theCommand = "Login";
			}

			switch (theCommand) {

			case "Login": 
				// listFeeds (request, response);
				acceptUser(request, response);
				break;
				
			case "SignUp":
				signUpUser(request, response);
				break;
			
			case "Post":
				postStatus(request, response);
				break;
				
			case "DeletePost":
				deletePost(request, response);
				break;
				
			case "Comment":
				commentPost(request,response);
				break;
				
			case "DeleteComment":
				deleteComment(request,response);
				break;
				
			case "LikePost":
				likePost(request, response);
				break;
				
			default:
				listFeeds(request, response);
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void deleteComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int commentId =Integer.parseInt(request.getParameter("CommentId"));
		int profileIdLogged = Integer.parseInt(request.getParameter("profileIdLogged"));
		int commentOwnerId = Integer.parseInt(request.getParameter("CommentOwnerId"));
		
		if (profileIdLogged == commentOwnerId) {
			accountDbUtil.DeleteComment(commentId);
			populateHomePage(request, response);
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><body><script>alert('It's not your business!');</script></body></html>");
		}
		
	}

	private void likePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int feedID = Integer.parseInt(request.getParameter("FeedId"));
		int currentLoggedId = Integer.parseInt(request.getParameter("profileIdLogged"));
		
		accountDbUtil.LikePost(feedID, currentLoggedId);
		
		//Method to get all posts, comments, likes, etc
		populateHomePage(request, response);
		
	}

	private void signUpUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		accountDbUtil.SignUser(firstName, lastName, email, password);
		
		
	}

	private void commentPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int profileID_of_comment = Integer.parseInt(request.getParameter("profID"));
		int feedID = Integer.parseInt(request.getParameter("feedID"));
		String commentContent = request.getParameter("commentContent");
		
		// Add COMMENT to DB
		accountDbUtil.CommentPost(profileID_of_comment, feedID, commentContent);
		
		//Method to get all posts, comments, likes, etc
		populateHomePage(request, response);
		
	}

	private void deletePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int FeedId = Integer.parseInt(request.getParameter("FeedId"));
		int currentLoggedId = Integer.parseInt(request.getParameter("profileIdLogged"));
		int feedOwnerId = Integer.parseInt(request.getParameter("FeedOwnerId"));
		
		//Check if the user owns the post so can delete it
		if (currentLoggedId == feedOwnerId) {
			accountDbUtil.DeletePost(FeedId);
			//Method to get all posts, comments, likes, etc
			populateHomePage(request, response);
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><body><script>alert('It's not your business!');</script></body></html>");
		}
	}

	private void listFeeds(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Getting feeds from DB
		List<Feed> feeds = accountDbUtil.getFeeds();
		// Add feeds to the request
		request.setAttribute("FeedList", feeds);
		
		// Getting names from feed Id/////////////////////////////
		//List<Account> accounts = new ArrayList<>();
		//for (Feed feed :feeds) {
			//accounts.add(accountDbUtil.GetAccount(feed.getFeedID()));	
		//}
		//request.setAttribute("AccountHavingFeedList",accounts);
		///////////////////////////////////////////////////////
		
		// Send request to JSP
		RequestDispatcher dispatcher = request.getRequestDispatcher("/homePage.jsp");
		dispatcher.forward(request, response);
	}

	private void postStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		// Get account informations
		int profileID = Integer.parseInt(request.getParameter("currentProfileID"));
		String postContent = request.getParameter("postContent");
		// Add POST to DB
		accountDbUtil.AddPost(profileID, postContent);
		
		//Method to get all posts, comments, likes, etc
		populateHomePage(request, response);
		
		/*
		// Get account informations
		int profileID = Integer.parseInt(request.getParameter("currentProfileID"));
		String postContent = request.getParameter("postContent");
		Date postCreated = new java.util.Date();
		Part filePart= request.getPart("filepath");
		// Add POST to DB
		accountDbUtil.AddPost(profileID, postCreated, postContent);
		*/
	
	}
	
/*
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get account informations
		int profileID = Integer.parseInt(req.getParameter("currentProfileID"));
		String postContent = req.getParameter("postContent");
		Date postCreated = new java.util.Date();
		Part filePart= req.getPart("filepath");
				
		// Add POST to DB
		try {
			accountDbUtil.AddPostWithPhoto(profileID, postCreated, postContent, filePart);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	*/

	private void acceptUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Getting email and password
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		// Check if user exists in database
		boolean isUserRegistered = accountDbUtil.isUserRegistered(email, password);

		if (isUserRegistered) {
			// Getting the logged account
			Account loggedAccount = accountDbUtil.GetAccount(email, password);
			// Add accounts to the request
			request.setAttribute("LoggedAccount", loggedAccount);
			
			//Method to get all posts, comments, likes, etc
			populateHomePage(request, response);

			// Send request to JSP
			//RequestDispatcher dispatcher = request.getRequestDispatcher("/homePage.jsp");
			//dispatcher.forward(request, response);
			
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><body><script>alert('Invalid login information!');</script></body></html>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/loginPage.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void populateHomePage(HttpServletRequest request, HttpServletResponse response) throws Exception, SQLException {
		// Getting feeds from DB
		List<Feed> feeds = accountDbUtil.getFeeds();
		// Add feeds to the request
		request.setAttribute("FeedList", feeds);
		
		// Getting comment lists from DB
		List<Comment> comments = accountDbUtil.getComments();
		// Add accounts to the request
		request.setAttribute("CommentList", comments);
		
		// Getting accounts from DB
		List<Account> accounts = accountDbUtil.getAccounts();
		// Add accounts to the request
		request.setAttribute("AccountList", accounts);
		
		//Getting likes from DB
		List<Likes> likes = accountDbUtil.getAllLikes();
		//Add likes to the request
		request.setAttribute("LikeList", likes);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/homePage.jsp");
		dispatcher.forward(request, response);
		
		
	}
	
	private void listComments(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Getting accounts from DB
		List<Comment> comments = accountDbUtil.getComments();
		
		// Add accounts to the request
		request.setAttribute("CommentList", comments);
		
		// Send request to JSP
		RequestDispatcher dispatcher = request.getRequestDispatcher("/homePage.jsp");
		dispatcher.forward(request, response);
	}

	private void listAccounts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Getting accounts from DB
		List<Account> accounts = accountDbUtil.getAccounts();

		// Add accounts to the request
		request.setAttribute("AccountList", accounts);

		// Send request to JSP
		RequestDispatcher dispatcher = request.getRequestDispatcher("/homePage.jsp");
		dispatcher.forward(request, response);
	}

}
