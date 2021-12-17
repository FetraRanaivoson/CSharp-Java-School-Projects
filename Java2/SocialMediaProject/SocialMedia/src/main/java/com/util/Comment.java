package com.util;

import java.util.Date;

public class Comment {
	private int commentID;
	private int profileIDOfComment;
	private int feedID;
	String commentContent;
	String commentDate;
	
	public Comment(int commentID, int profileIDOfComment, int feedID, String commentContent, String formattedCommentDate) {
		super();
		this.commentID = commentID;
		this.profileIDOfComment = profileIDOfComment;
		this.feedID = feedID;
		this.commentContent = commentContent;
		this.commentDate = formattedCommentDate;
	}

	public Comment(int profileIDOfComment, int feedID, String commentContent, String commentDate) {
		super();
		this.profileIDOfComment = profileIDOfComment;
		this.feedID = feedID;
		this.commentContent = commentContent;
		this.commentDate = commentDate;
	}

	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public int getProfileIDOfComment() {
		return profileIDOfComment;
	}

	public void setProfileIDOfComment(int profileIDOfComment) {
		this.profileIDOfComment = profileIDOfComment;
	}

	public int getFeedID() {
		return feedID;
	}

	public void setFeedID(int feedID) {
		this.feedID = feedID;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	@Override
	public String toString() {
		return "Comment [commentID=" + commentID + ", profileIDOfComment=" + profileIDOfComment + ", feedID=" + feedID
				+ ", commentContent=" + commentContent + ", commentDate=" + commentDate + "]";
	}
	
	
}
