package com.util;

import java.util.Date;

public class Likes {
	private int feedID;
	private int profileIdOfLiker;
	private Date likeDate;
	
	public Likes(int feedID, int profileIdOfLiker, Date likeDate) {
		super();
		this.feedID = feedID;
		this.profileIdOfLiker = profileIdOfLiker;
		this.likeDate = likeDate;
	}

	public Likes(int profileIdOfLiker, Date likeDate) {
		super();
		this.profileIdOfLiker = profileIdOfLiker;
		this.likeDate = likeDate;
	}

	public int getFeedID() {
		return feedID;
	}

	public void setFeedID(int feedID) {
		this.feedID = feedID;
	}

	public int getProfileIdOfLiker() {
		return profileIdOfLiker;
	}

	public void setProfileIdOfLiker(int profileIdOfLiker) {
		this.profileIdOfLiker = profileIdOfLiker;
	}

	public Date getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}

	@Override
	public String toString() {
		return "Likes [feedID=" + feedID + ", profileIdOfLiker=" + profileIdOfLiker + ", likeDate=" + likeDate + "]";
	}
	
	
}
