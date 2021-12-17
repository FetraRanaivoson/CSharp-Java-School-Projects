<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html lang="en">

<head>
	 
	<title>Home page</title>
	<link rel="stylesheet" href="style.css">
</head>

<body>
	<!-- This is the current user logged in -->
	<c:set var="loggedAccount" value="${LoggedAccount}" />


	<nav class="nav active">
		<div class="container">
			<h1 class="logo">
				Orange <img alt="" src="juice.jpg" height="40">Juice
			</h1>
			<input type="text" class="search-textBox"
				placeholder="Search friends" />
			<ul>
				<li>
					<h4>User: ${loggedAccount.firstName} ${loggedAccount.lastName}
					</h4>
				</li>
				<li><a href="#">
						<button class="home-page-button">
							<img alt="" src="home.png" height="40">
						</button>
				</a></li>
				<li><a href="#">
						<button class="profile-page-button">
							<img alt="" src="profile.jpg" height="40">
						</button>
				</a></li>
				<li><a href="#">
						<button
							onclick="window.location.href='loginPage.jsp';return false"
							class="logout-page-button">
							<img alt="" src="logout.jpg" height="30">
						</button>
				</a></li>
			</ul>
		</div>
	</nav>

	<div class="hero">
		<div class="hero-container">
			<section class="feed-container">

				<!-- ADD POST BEGINS-->
				<form action="AccountControllerSRV" method="get">
					<div class="add-post-container">
						<div class="profile-image">Img</div>
						<textarea class="post-textBox" placeholder="What's on your mind?"
							name="postContent" required></textarea>
						<input type="hidden" name="command" value="Post" /> <input
							class="userID" type="hidden" name="currentProfileID"
							value="${LoggedAccount.profileID }" />
						<!-- For storing the userID when he posts something -->

						<!-- Add photos<input type="file" id="photo-button" name="filepath" /-->
						<button type="submit" class="post-button">Post</button>
						<!--input class="post-button" type="submit" value="Post" /-->
					</div>
				</form>
				<!-- ADD POST ENDS -->

				<div></div>

				<c:forEach var="feed" items="${FeedList }">


					<c:url var="deletePost" value="AccountControllerSRV">
						<c:param name="command" value="DeletePost" />
						<c:param name="FeedId" value="${feed.feedID}" />
						<c:param name="FeedOwnerId" value="${feed.profileID}" />
						<c:param name="profileIdLogged" value="${loggedAccount.profileID}" />
					</c:url>

					<c:url var="likePost" value="AccountControllerSRV">
						<c:param name="command" value="LikePost" />
						<c:param name="FeedId" value="${feed.feedID}" />
						<c:param name="profileIdLogged" value="${loggedAccount.profileID}" />
					</c:url>


					<div class="a-post-container">
						<div class="profile-image"></div>
						<div class="username-date">
							<c:forEach var="account" items="${AccountList }">
								<c:if test="${feed.profileID == account.profileID }">
									<h4>${account.firstName}${account.lastName}</h4>
								</c:if>
							</c:forEach>
							<br>
							<h6>${feed.feedCreated }</h6>
						</div>

						<div class="post-content">
							<p>${feed.feedContent}</p>
							<a href="${deletePost}"
								onclick="if(!(confirm('Are you sure?'')))return false"
								style="margin-left: 355px;">Delete</a>
						</div>

						<div class="numberOf-like-comment"></div>


						<form action="AccountControllerSRV" method="get">
							<input type="hidden" name="command" value="Comment"> <input
								type="hidden" name="profID" value="${loggedAccount.profileID}">
							<input type="hidden" name="feedID" value="${feed.feedID}">

							<!-- Write a comment-->
							<div class="comment-container">
								<div class="post-comment-textBox">
									<div>Img</div>
									<textarea class="write-comment"
										placeholder="Write a comment..." name="commentContent"
										required></textarea>
								</div>

								<!-- Comment section -->
								<c:forEach var="tempComment" items="${CommentList}">

									<c:url var="deleteComment" value="AccountControllerSRV">
										<c:param name="command" value="DeleteComment" />
										<c:param name="CommentId" value="${tempComment.commentID}" />
										<c:param name="CommentOwnerId"
											value="${tempComment.profileIDOfComment}" />
										<c:param name="profileIdLogged"
											value="${loggedAccount.profileID}" />
									</c:url>



									<c:if test="${feed.feedID == tempComment.feedID}">
										<div class="post-comment-textBox">
											<c:forEach var="account" items="${AccountList }">
												<c:if
													test="${tempComment.profileIDOfComment == account.profileID }">
													<div>img</div>
													<h4>${account.firstName}${account.lastName}</h4>
												</c:if>
											</c:forEach>
											<div></div>
											<p class="user-comment-textBox">${tempComment.commentContent }</p>
											<h6>${tempComment.commentDate}</h6>
											<a href="${deleteComment }">Delete</a>
										</div>
									</c:if>
								</c:forEach>
								<!--  -->
							</div>

							<div class="like-comment-button">
								<% int counter=0; %>
								<c:forEach var="tempLike" items="${LikeList }">
									<c:if test="${feed.feedID == tempLike.feedID}">
									 	<% counter++; %>
									</c:if>
								</c:forEach>

								<label for="likeNumber"> <%=counter%></label> <a href="${likePost }">Like</a>
								<button type="submit" id="comBtn">Add Comment</button>
								<!--input id="comBtn" type="submit"value="Add comment"  /-->
							</div>
						</form>
					</div>
				</c:forEach>
			</section>
		</div>
	</div>


	<script type="text/javascript">

	
	</script>
</body>
</html>