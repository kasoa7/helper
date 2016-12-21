<%@page import="com.helper.util.UserUtils"%>
<%@page import="com.helper.controller.GoogleAuthHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Helper</title>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <link rel='stylesheet' href="<c:url value="/resources/css/main.css"/>"/>
</head>
<body background="/resources/img/background.jpg" style="background-size:100% 100%"><br/><br/><br/>
  <section class="container">
	 <article class="half">
		    <br/><br/><br/><br/><br/><br/><br/><h1><center>
		<%
			final GoogleAuthHelper helper = new GoogleAuthHelper();
			if (request.getParameter("code") == null || request.getParameter("state") == null) {
				
				out.println("<a id='buildLogin' href='" + helper.buildLoginUrl() + "'>log in with google</a>");
				session.setAttribute("state", helper.getStateToken());
				
			} else if (request.getParameter("code") != null && request.getParameter("state") != null
					&& request.getParameter("state").equals(session.getAttribute("state"))) {
				
				session.removeAttribute("state");
				helper.getUserInfoJson(request.getParameter("code"));
				
				response.sendRedirect("/email/tmpLogin?email="+UserUtils.getInstance().getEmailAddress());
			}
		%></center></h1>
    </article>
	<div class="half bg"></div>
	</section>

		
	<script type="text/javascript">
		$('.tabs .tab').click(function(){
		    if ($(this).hasClass('signin')) {
		        $('.tabs .tab').removeClass('active');
		        $(this).addClass('active');
		        $('.cont').hide();
		        $('.signin-cont').show();
		    } 
		    if ($(this).hasClass('signup')) {
		        $('.tabs .tab').removeClass('active');
		        $(this).addClass('active');
		        $('.cont').hide();
		        $('.signup-cont').show();
		    }
		});
		
		$('.container .bg').mousemove(function(e){
		    var amountMovedX = (e.pageX * -1 / 30);
		    var amountMovedY = (e.pageY * -1 / 9);
		    $(this).css('background-position', amountMovedX + 'px ' + amountMovedY + 'px');
		});
	</script>
	
</body>
</html>