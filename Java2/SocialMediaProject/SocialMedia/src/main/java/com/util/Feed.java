package com.util;

import java.util.Date;

public class Feed {
	private int FeedID;
	private int ProfileID;
	private String FeedCreated;
	private Date FeedModified;
	private Date FeedDeleted;
	private String FeedContent;
	private String PhotoPath;
	
	//Constructor with the field PhotoPath
	public Feed(int feedID, int profileID, String feedCreated, Date feedModified, Date feedDeleted, String feedContent,
			String photoPath) {
		super();
		FeedID = feedID;
		ProfileID = profileID;
		FeedCreated = feedCreated;
		FeedModified = feedModified;
		FeedDeleted = feedDeleted;
		FeedContent = feedContent;
		PhotoPath = photoPath;
	}

	public Feed(int profileID, String feedCreated, Date feedModified, Date feedDeleted, String feedContent) {
		super();
		ProfileID = profileID;
		FeedCreated = feedCreated;
		FeedModified = feedModified;
		FeedDeleted = feedDeleted;
		FeedContent = feedContent;
	}

	public Feed(int feedID, int profileID, String feedCreated, Date feedModified, Date feedDeleted, String feedContent) {
		super();
		FeedID = feedID;
		ProfileID = profileID;
		FeedCreated = feedCreated;
		FeedModified = feedModified;
		FeedDeleted = feedDeleted;
		FeedContent = feedContent;
	}

	public int getProfileID() {
		return ProfileID;
	}

	public void setProfileID(int profileID) {
		ProfileID = profileID;
	}

	public String getFeedCreated() {
		return FeedCreated;
	}

	public void setFeedCreated(String feedCreated) {
		FeedCreated = feedCreated;
	}

	public Date getFeedModified() {
		return FeedModified;
	}

	public void setFeedModified(Date feedModified) {
		FeedModified = feedModified;
	}

	public Date getFeedDeleted() {
		return FeedDeleted;
	}

	public void setFeedDeleted(Date feedDeleted) {
		FeedDeleted = feedDeleted;
	}

	public String getFeedContent() {
		return FeedContent;
	}

	public void setFeedContent(String feedContent) {
		FeedContent = feedContent;
	}

	public int getFeedID() {
		return FeedID;
	}

	@Override
	public String toString() {
		return "Feed [FeedID=" + FeedID + ", ProfileID=" + ProfileID + ", FeedCreated=" + FeedCreated
				+ ", FeedModified=" + FeedModified + ", FeedDeleted=" + FeedDeleted + ", FeedContent=" + FeedContent
				+ "]";
	}
	
	
	
	
	
	
	
}
