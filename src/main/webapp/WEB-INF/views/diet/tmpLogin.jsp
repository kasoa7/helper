<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script>
	$('document').ready(function(){
		  var obj = {"emailAddress" : <%= request.getParameter("email") %>}	
		  $.ajax({
		      url: "/email/register",
		      method: "post",
		      type: "json",
		      contentType: "application/json",
		      data: JSON.stringify(obj),
		      success : function(data) {
				if(data == "OK") {
					window.location = "/diet/calendar";
				//	DietControllerCaller();
				} else {
					alert("login fail");
				}
			  }
		  });
	});
</script>
</head>
<body>
</body>
</html>