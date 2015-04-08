
<html>

	<%@page import="blogApp.User"%>
	
	<head>
		<link rel="stylesheet" type="text/css" href="writeABlogPage.css">
		<script type="text/javascript">
			function blogTitle(){
				if(document.getElementById("blog_title").value == ""){
					document.getElementById("blog_title").value = "Title: ";
				}
			}
			function post(){
				if(document.getElementById("blog_post").value == ""){
					document.getElementById("blog_post").value = "Write here... ";
				}
			}
		</script>
	</head>
	<body>
		<p>Share your thoughts!</p>
		<%
			if(session.getAttribute("user-information") == null){
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WriteBlog");
				rd.forward(request, response);
			}

			else{
				User currentUser = (User)session.getAttribute("user-information");
				out.println("<p id =\"username\">" + currentUser.getUserName() + "</p>");
			}
		%>
		<form action = "SaveBlog" method = "POST" >
			<input id = "blog_title" type = "text" name = "blog_title" value = "Title: " onfocus = "value = ''" onblur = "blogTitle();"/>
			<br>
			<textarea id = "blog_post" type = "text" name = "blog_post" onfocus = "value = ''" onblur = "post();">Write here...</textarea>
			<br>
			<input id = "submit" type = "submit" name = "Post" value = "Post">
		</form>
	</body>
</html> 