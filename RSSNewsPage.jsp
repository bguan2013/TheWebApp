<!doctype html>

<html>
	<%@ page import="blogApp.User"%>
	<%@ page import="blogApp.Rss"%>
	<head>
		<link rel="stylesheet" type="text/css" href="RSSNewsPageStyleSheet.css">
		<title>News Page</title>
	</head>
	<body>
		<img id = "header_background" src="waterDrop.jpg">
		<div class = "header">
			<a href="WriteBlog" id = "write_button"></a>
			<a href="ViewBlog" id = "blog_button"></a>
			<%	
				Rss[] currentRSSArray = null;
				if(request.getAttribute("rss-array") == null){
					
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/RSSNews");
					rd.forward(request, response);
				}
				else{
					currentRSSArray = (Rss[])request.getAttribute("rss-array");
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
					if(currentRSSArray == null){
							out.println("<li id = \"first-post\">\n" +
											"No post\n" +
										"</li>\n");
					}
					else{


						for(int i = 0; i < currentRSSArray.length; i++){

							if(currentRSSArray[i] != null){
								
								if(i == 1){
									out.println("<li id = \"first-post\">\n" +
													"<p>" + currentRSSArray[i].getRssTitle() + "</p>\n" +
													"<p>" + currentRSSArray[i].getRssDescription() + "</p>\n" +
													"<p>" + currentRSSArray[i].getRssDate() + "</p>\n" +
													"<p>" + currentRSSArray[i].getRssAuthor() + "</p>\n" +
												"</li>\n");
								}
								else{
									out.println("<li>\n" +
													"<p>" + currentRSSArray[i].getRssTitle() + "</p>\n" +
													"<p>" + currentRSSArray[i].getRssDescription() + "</p>\n" +
													"<p>" + currentRSSArray[i].getRssDate() + "</p>\n" +
													"<p>" + currentRSSArray[i].getRssAuthor() + "</p>\n" +
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