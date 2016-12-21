<%@page import="com.helper.util.CalendarEvents"%>
<%@page import="com.helper.util.UserUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html charset='euc-kr'>
<head>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    
    <!-- Google Login API -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>    
	<script src="https://apis.google.com/js/platform.js?onload=onLoadCallback" async defer></script>
	
    <!-- FullCalendar Library -->
    <link rel='stylesheet' href="<c:url value="/resources/fullcalendar/fullcalendar.css"/>"/>
    <link rel='stylesheet' href="<c:url value="/resources/css/bootstrap.css"/>"/>
    <link rel='stylesheet' href="<c:url value="/resources/css/styles.css"/>"/>
    
    <script src="<c:url value="/resources/lib/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/lib/moment.min.js"/>"></script>
    <script src="<c:url value="/resources/fullcalendar/fullcalendar.js"/>"></script>
    
    <!-- Google Calendar API-->
    <script type='text/javascript' src="<c:url value="/resources/fullcalendar/gcal.js"/>">></script>
    <script src="<c:url value="/resources/js/test.js"/>"></script>
    <script src="<c:url value="/resources/js/sidebarscript.js"/>"></script>
    <style>
    .fc-scroller {
   		overflow-y: hidden !important;
	}
    </style>
</head>

<body background="/resources/img/background.jpg" style="background-size:1700px 1500px; background-color:transparent">

<div id="container" class='container' style="margin-right:auto">
    <div id="menu-toggle">
		<img src="/resources/img/menu.png" width=30 height=30 alt="Menu"></img>
	</div>
 
	<nav id="menu2">
		<a href="#" onClick = createDate();>Date</a>
		<a href="#" id="dietHref" onClick = createDiet();>Diet</a>
		<a id='inputAgeatag' onClick=createViewAge();>Age</a><input type='text' id="inputAge" placeholder="age" onfocusout ="focusOut();" onkeydown="return onlyNumberCode(event)" style="width:180px; display:none; text-align: center;"><br/><input type="text" id="encKCAL" style="width:180px; text-align: center;display:none;">		
		<a href="#" onClick = createChart();>Chart</a>
		<a href="http://www.myfitnesspal.com/ko/food/calorie-chart-nutrition-facts" target="_blank">Calorie Reference Site</a>
		<a></a>
		<a href="https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://localhost:8088/email/test" style="color:red">★SignOut</a>
	</nav>
	
	</br></br>
	
    <div id='calendar'></div>
    
    <div id="popupCalendarForm" class="modal hide" style="display: none;">
    <div class="modal-header"><h3>Calendar Popup</h3></div>
    	<div class="modal-body">
  			<form id="EventForm" role="form" method="post">
	            <div>
	            	<input type="text" id="clickedDate" style="display:none"></br>
	            </div>
	            <div>
	            <input type="text" id="eventSummary" placeholder="Summary" style="width:250px"></br>
	            <input id="eventStartBirth" type="date">
	            <select id = "eventStartTime" name="selStartTime" style="width:70px"></select>
	            <select id = "eventStartMinute" name="selStartMinute" style="width:70px"></select></br>            
	            <input id="eventEndBirth" type="date">
	            <select id = "eventEndTime" name="selEndTime" style="width:70px"></select>
	            <select id = "eventEndMinute" name="selEndMinute" style="width:70px"></select></br>        
	            <input type="text" id="eventDescription" placeholder="Description" style="width:500px"></br>
	            <input type="text" id="eventLocation" placeholder="location" style="width:250px">
	            </div>
	            <hr width="320px">	
	            <div class="modal-footer">
	        		<button type="button" id="btnCalendarPopupSave" onClick="saveCalendarEvent()" class="btn btn-primary">Save</button>
	       			<button type="button" id="btnCalendarPopupCancel" onClick="cancelCalendarEvent()" class="btn btn-warning">Cancel</button>
	    		</div>
	        </form>             
    	</div>
  	</div>

   <div id="popupAlternateCalendarForm" class="modal hide" style="display: none;">
   <div class="modal-header"><h3>Calendar Alternate Popup</h3></div>
   <div class="modal-body">
		<form id="EventAlternateCalendarForm" role="form" method="post">
            <div>
            	<input type="text" id="clickedDate2" style="display:none"></br>
            </div>
            <div>
            <input type="text" id="ALeventSummary" placeholder="Summary" style="width:250px"></br>
            <input id="ALeventStartBirth" type="date">
            <select id = "ALeventStartTime" name="selStartTime" style="width:70px"></select>
            <select id = "ALeventStartMinute" name="selStartMinute" style="width:70px"></select></br>            
            <input id="ALeventEndBirth" type="date">
            <select id = "ALeventEndTime" name="selEndTime" style="width:70px"></select>
            <select id = "ALeventEndMinute" name="selEndMinute" style="width:70px"></select></br>            
            <input type="text" id="ALeventDescription" placeholder="Description" style="width:500px"></br>
            <input type="text" id="ALeventLocation" placeholder="location" style="width:250px">
            </div>
            <hr width="320px">
            <div class="modal-footer">
        		<button type="button" id="btnCalendarPopupUpdate" onClick="updateCalendarEvent()" class="btn btn-primary">Update</button>
       			<button type="button" id="btnCalendarPopupDelete" onClick="deleteCalendarEvent()" class="btn btn-primary">Delete</button>
       			<button type="button" id="btnCalendarPopupCancel" onClick="cancelAlternateCalendarEvent()" class="btn btn-warning">Cancel</button>
    		</div>
       </form>             
  </div>
  </div>
  
  <div id="popupDietForm" class="modal hide" style="display: none;">
    <div class="modal-header"><h3>Add new event</h3></div>
    <div class="modal-body">
  		<form id="EventForm" role="form" method="post">
            <div>
                  <label>Distinction&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Menu&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Kcal</label>
            </div>
            <div>
            <input type="hidden" name="emailAddress" id="emailAddress" value=<%=UserUtils.getInstance().getEmailAddress()%>/>
            <input type="hidden" name="MealDays" id="MealDays"/>
            <input type="text" name="Bmealtime" id="Bmealtime" value="breakfast" style="width:80px" readonly="readonly">&nbsp;&nbsp;&nbsp;
            <input type="text" name="Bmenu" id="Bmenu"  placeholder="menu" style="width:250px">&nbsp;&nbsp;&nbsp;
            <input type="text" name="Bkcal" id="Bkcal"  onkeydown="return onlyNumberCode(event)" placeholder="Kcal" style="width:50px">        
            </div>
            
            <div>
            <input type="text" name="Lmealtime" id="Lmealtime" value="lunch" style="width:80px" readonly="readonly"/>&nbsp;&nbsp;&nbsp;
            <input type="text" name="Lmenu" id="Lmenu"  placeholder="menu" style="width:250px"/>&nbsp;&nbsp;&nbsp;
            <input type="text" name="Lkcal" id="Lkcal"  onkeydown="return onlyNumberCode(event)" placeholder="Kcal" style="width:50px"/>            
            </div>
            
            <div>
            <input type="text" name="Dmealtime" id="Dmealtime" value="dinner" style="width:80px" readonly="readonly"/>&nbsp;&nbsp;&nbsp;
            <input type="text" name="Dmenu" id="Dmenu"  placeholder="menu" style="width:250px"/>&nbsp;&nbsp;&nbsp;
            <input type="text" name="Dkcal" id="Dkcal"  onkeydown="return onlyNumberCode(event)" placeholder="Kcal" style="width:50px">
            </div>
            
            <hr width="80px">
            <div align="right" style="margin-right:40px">
                 Sum : <input type = "text" id="sumKcalId"  style="width:60px" disabled="disabled">
            </div>
            
            <div class="modal-footer">
                <button type="button" id="btnPopupdelete" class="btn btn-warning" onclick="DeleteDietpopup()">Delete</button>                      
        		<button type="button" id="btnPopupSave" class="btn btn-primary" onclick="SaveDietpopup()">Save</button>
       			<button type="button" id="btnPopupCancel" class="btn btn-warning"onclick="cancelDietEvent()" >Cancel</button>
    		</div>
      
        </form>             
    </div>
  </div>
</div>


 	<input type='button' id='ab' style='display : none' value='<%= CalendarEvents.getInstance().getRemakeCalendarEvents() %>'>
 	<input type='button' id='cd' style='display : none' value='<%= CalendarEvents.getInstance().getCalendarEvents() %>'>
	<input type='button' id='ef' style='display : none' value='<%= CalendarEvents.getInstance().getCalendar_flag() %>'>
	
<script>
		var e_start;
		var e_title;
		var age_flag = 0;
		$(document).ready(function(){
			initializeCheckBox();		
			
			remakeEvent = $('#ab').attr('value');
			calendarEvent = $('#cd').attr('value');		
			calendar_Flag = $('#ef').attr('value');
			
			if(calendar_Flag == "true"){
				createDate();
			}else if(calendar_Flag == "false"){
				createDiet();
			}
		});
		
		var totalKcal = ${totalkcal};
		var bmenu = ${bmenuobject};
		var lmenu = ${lmenuobject};
		var dmenu = ${dmenuobject};
		var json_Object = ${jsonObject};
		
		for(var i=0;i<totalKcal.length;i++){
			totalKcal[i].title = "Sum : " + totalKcal[i].title +"Kcal";
			if(bmenu[i] !=null){bmenu[i].location = "a";}
			if(lmenu[i] !=null){lmenu[i].location = "b";}
			if(dmenu[i] !=null){dmenu[i].location = "c";} 
		}	
		var finalobj = bmenu.concat(totalKcal).concat(lmenu).concat(dmenu);		
</script>
<script src="http://canvasjs.com/assets/script/canvasjs.min.js"></script>
  <div id="chartContainer" class="modal hide" style="height:600px; width:1300px; left:30%;"></div>
</body>
</html>
