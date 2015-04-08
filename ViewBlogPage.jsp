<!doctype html>

<html>
	<%@ page import="blogApp.User"%>
	<%@ page import="blogApp.Blog"%>
	<head>
		<link rel="stylesheet" type="text/css" href="BlogPageStyleSheet.css">
		<title>Blog Page</title>
		<script type="text/javascript">
			var xmlHttp = new XMLHttpRequest();
			function saveComment(comment){
				xmlHttp.open("Post", "CommentBlog", true);
				xmlHttp.onreadystatechange = displayComment;
				xmlHttp.send("");

			}
			function displayComment(){
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200){


				}
			}
		</script>
	</head>
	<body>
		<img id = "header_background" src="waterDrop.jpg">
		<div class = "header">
			<a href="WriteBlog" id = "write_button"></a>
			<a href="RSSNews" id = "rss_button"></a>
			<%	
				Blog[] currentBlogArray = null;
				if(request.getAttribute("blog-array") == null){
					
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/ViewBlog");
					rd.forward(request, response);
				}
				else{
					currentBlogArray = (Blog[])request.getAttribute("blog-array");
				}

				
				String username = "";
				if(session.isNew() || session.getAttribute("user-information") == null){

					username = "Unknown";

				}
				else{

					User user = (User)session.getAttribute("user-information");
					username = user.getUserName();
					
				}

				out.println("<div>" + username + "</div>");
			%>
			
			<nav>
				<ul>
				<li>
					<a id = "icon" href=""></a>
				<ul>
					<li>
						<a href="Setting"> Setting </a>
					</li>
					
					<li>
						<a href="Logout"> Logout </a>
					</li>
				</ul>
				</li>
				</ul>
			</nav>
			
		</div>
		<div class = "post">
			<ul id = "post-list">
				<%
					if(currentBlogArray == null){
							out.println("<li id = \"first-post\">\n" +
											"No post\n" +
										"</li>\n");
					}
					else{


						for(int i = 0; i < currentBlogArray.length; i++){

							if(currentBlogArray[i] != null){
								
								if(i == 1){
									out.println("<li id = \"first-post\">\n" +
													"<p>" + currentBlogArray[i].getTitle() + "</p>\n" +
													"<p>" + currentBlogArray[i].getContent() + "</p>\n" +
													"<p>" + currentBlogArray[i].getDate() + "</p>\n" +
													"<p>" + currentBlogArray[i].getUser().getUserName() + "</p>\n" +
												"</li>\n");
								}
								else{
									out.println("<li id = \"\">\n" +
													"<p>" + currentBlogArray[i].getTitle() + "</p>\n" +
													"<p>" + currentBlogArray[i].getContent() + "</p>\n" +
													"<p>" + currentBlogArray[i].getDate() + "</p>\n" +
													"<p>" + currentBlogArray[i].getUser().getUserName() + "</p>\n" +
												"</li>\n");
								}

							}

						}
					
					}

				%>
			</ul>
		</div>
	</body>

</html>