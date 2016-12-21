
// DATE CALENDAR CREATE
function createDate() {
	if (calendar_Flag == false) {
		deleteCalendar();
		calendar_Flag = true;
	} else {
		calendar_Flag = true;
	}
	$('#calendar').fullCalendar({
		header : {
			left : 'listYear,month,agendaWeek,agendaDay',
			center : 'title',
			right : 'prev,next today'
		},
		timeFormat : "HH:mm",
		editable : false,
		height : 650,
		events : JSON.parse(remakeEvent),
		dayClick : function(date) {
			ShowCalendarPopup(date);

			if ($('#clickedDate').val() == "") {
				$('#clickedDate').val(date.format());	
			}
		},eventClick: function(calEvent) {
			e_title = calEvent.title;
			e_start = calEvent.start;			
			ShowAlternateCalendarPopup(e_title, e_start);
		}
	});
};

//DIET CALENDAR CREATE
function createDiet() {
	if (calendar_Flag == true) {
		deleteCalendar();
		calendar_Flag = false;
	} else {
		calendar_Flag = false;
	}
	$('#calendar').fullCalendar({
		header : {
			left : 'listYear,month,agendaWeek,agendaDay',
			center : 'title',
			right : 'prev,next today'
		},
		height : 650,
		events : finalobj,
		eventOrder: "location",
		dayClick : function(date) {    			
			$.ajax({
				type : "POST",
				url : "/diet/calendar",
				data : {
					MealDays : date.format(),
					emailAddress : $('#emailAddress').val()
				},
				success : function(data){
					if(data.bmenu != null || data.lmenu != null || data.dmenu != null){
						$('#Bmenu').val(data.bmenu);    						    					
    					$('#Bkcal').val(data.bkcal);
    					if($('#Bkcal').val()==0){$('#Bkcal').val("")};
    					
    					$('#Lmenu').val(data.lmenu);
    					$('#Lkcal').val(data.lkcal);
    					if($('#Lkcal').val()==0){$('#Lkcal').val("")};
    					
    					$('#Dmenu').val(data.dmenu);        					
    					$('#Dkcal').val(data.dkcal);
    					if($('#Dkcal').val()==0){$('#Dkcal').val("")};
    					$('#sumKcalId').val(data.totalkcal);
    					
    					$('#MealDays').val(date.format());
    					$('#btnPopupdelete').css('display','');
    					ShowDietPopup(date);
					}
					else if(data.bmenu == null && data.lmenu ==null && data.dmenu ==null){
						$('#MealDays').val(date.format());
    					$('#sumKcalId').val("");
						$('#btnPopupdelete').css('display','none');
						EmptyDietPopup(date);
					}
				}
			});
		}
		
	});
};

// CALENDAR POPUP(SAVE, CANCEL) SHOW
function ShowCalendarPopup(date) {
	ClearPopupFormValues();
	$('#popupCalendarForm').show();
    $('#eventSummary').focus();
}

// CALENDAR POPUP(UPDATE, DELETE, CANCEL) SHOW
function ShowAlternateCalendarPopup(e_title, e_start){
	var a = JSON.parse(calendarEvent);
	for(var i=0;i<a.items.length;i++){
		if(a.items[i].summary == e_title && a.items[i].start.dateTime == e_start._i){
			$('#ALeventSummary').val(e_title);
			$('#ALeventStartBirth').val(e_start._i.substring(0,10));
			$('#ALeventStartTime').val(e_start._i.substring(11,13));
			$('#ALeventStartMinute').val(e_start._i.substring(14,16));
			$('#ALeventEndBirth').val(a.items[i].end.dateTime.substring(0,10));
			$('#ALeventEndTime').val(a.items[i].end.dateTime.substring(11,13));
			$('#ALeventEndMinute').val(a.items[i].end.dateTime.substring(14,16));
			$('#ALeventDescription').val(a.items[i].description);
			$('#ALeventLocation').val(a.items[i].location);
		}
	}
	$('#popupAlternateCalendarForm').show();
}

//DIET EVENT CANCEL
function cancelDietEvent(){
	$('#popupDietForm').hide();
	ClearPopupFormValues();
}

// DIET POPUP SHOW
function ShowDietPopup(date) {
	$('#popupDietForm').show();
	$('#breakfastMenu').focus();
}

// EMPTY POPUP SHOW
function EmptyDietPopup(date){
	ClearDietPopup();
	$('#popupDietForm').show();
	$('#breakfastMenu').focus();
}
// CLEAR DIET POPUP
function ClearDietPopup(date){
		$('#Bmenu').val("");
		$('#Bkcal').val("");
		$('#Lmenu').val("");
		$('#Lkcal').val("");
		$('#Dmenu').val("");
		$('#Dkcal').val("");
}
// DIET DELETE
function DeleteDietpopup(date){
    var formObj = $("form[role='form']");

    formObj.attr("action","/diet/remove");
    formObj.attr("method","post");   
    formObj.submit();       	
}

// DIET SAVE
function SaveDietpopup(date){   
	   if($('#Bkcal').val().length==0){
	      $('#Bkcal').val(0);
	   }
	   if($('#Dkcal').val().length==0){
	      $('#Dkcal').val(0);
	   }
	   if($('#Lkcal').val().length==0){
	      $('#Lkcal').val(0);
	   }
	   var formObj = $("form[role='form']");
	      
	   formObj.attr("action","/diet/register");
	   formObj.attr("method","post");   
	   formObj.submit();
}

// NUMBER SEARCH FUNCTION
function onlyNumberCode(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46) {
		return;
	} else {
		return false;
	}
}

// CALENDAR HIDE
function deleteCalendar() {
	$('#calendar').fullCalendar('destroy');
};

// CALENDAR EVENT UPDATE
function updateCalendarEvent() {
	var a = JSON.parse(calendarEvent);
	var e_id,ev_start,ev_end;
	for(var i=0;i<a.items.length;i++){
		if(a.items[i].summary == e_title && a.items[i].start.dateTime == e_start._i){
			e_id = a.items[i].id;
			ev_start = a.items[i].start.dateTime.substring(0,10);
			ev_end = a.items[i].end.dateTime.substring(0,10);
		}
	}
	
	if ($('#ALeventSummary').val() != "") {
		var obj = {
			title : $('#ALeventSummary').val(),
			start : $('#ALeventStartBirth').val() + "T"
					+ $('#ALeventStartTime option:selected').text() + ":"
					+ $('#ALeventStartMinute option:selected').text()
					+ ":00.000+09:00",
			end : $('#ALeventEndBirth').val() + "T"
					+ $('#ALeventEndTime option:selected').text() + ":"
					+ $('#ALeventEndMinute option:selected').text()
					+ ":00.000+09:00",
			location : $('#ALeventLocation').val(),
			description : $('#ALeventDescription').val(),
			id : e_id
		}
		$.ajax({
			url : "/email/updateCalendarEvent",
			method : "post",
			type : "json",
			contentType : "application/json",
			data : JSON.stringify(obj),
			success : function(data) {
				if (data == "OK") {
					window.location = "/diet/calendar";
				} else {
					console.log("UPDATE FAIL");
				}
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}
		});

		$('#popupAlternateCalendarForm').hide();
	}
};

//CALENDAR EVENT SAVE
function saveCalendarEvent() {
	if ($('#eventSummary').val() != "") {
		var obj = {
			title : $('#eventSummary').val(),
			start : $('#eventStartBirth').val() + "T"
					+ $('#eventStartTime option:selected').text() + ":"
					+ $('#eventStartMinute option:selected').text()
					+ ":00.000+09:00",
			end : $('#eventEndBirth').val() + "T"
					+ $('#eventEndTime option:selected').text() + ":"
					+ $('#eventEndMinute option:selected').text()
					+ ":00.000+09:00",
			location : $('#eventLocation').val(),
			description : $('#eventDescription').val()
		}
		$.ajax({
			url : "/email/newCalendarEvent",
			method : "post",
			type : "json",
			contentType : "application/json",
			data : JSON.stringify(obj),
			success : function(data) {
				if (data == "OK") {
					window.location = "/diet/calendar";
				} else {
					console.log("SAVE FAIL");
				}
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}
		});

		$('#popupCalendarForm').hide();
		ClearPopupFormValues();
	}
};

//CALENDAR EVENT DELETE
function deleteCalendarEvent() {
	var obj;
	var a = JSON.parse(calendarEvent);
	for(var i=0;i<a.items.length;i++){
		if(a.items[i].summary == e_title && a.items[i].start.dateTime == e_start._i){
			obj = { "id" : a.items[i].id};
		}
	}
	
	$.ajax({
		url : "/email/removeCalendarEvent",
		method : "post",
		type : "json",
		contentType : "application/json",
		data : JSON.stringify(obj),
		success : function(data) {
			if (data == "OK") {
				window.location = "/diet/calendar";
			} else {
				console.log("DELETE FAIL");
			}
		},
		error : function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		}
	});
	$('#popupCalendarForm').hide();
};

//CALENDAR EVENT CANCEL
function cancelCalendarEvent() {
	$('#popupCalendarForm').hide();
	ClearPopupFormValues();
};

//AlternateCALENDAR EVENT CANCEL
function cancelAlternateCalendarEvent() {
	$('#popupAlternateCalendarForm').hide();
};

// CALENDAR FORM VALUES CLEAR
function ClearPopupFormValues() {
	$('#eventSummary').val("");
	$('#eventDescription').val("");
	$('#eventLocation').val("");
	$('#eventStartTime').val("00");
	$('#eventStartMinute').val("00");
}

// CHECK BOX DATE, TIME INITIALIZE
function initializeCheckBox() {
	var a=[]; var value = "";  

	for(var i=1;i<31;i++){
		if(i<10){ value = "0"+i; } 
		else{ value=i; } 
		if(i==0){
		a[i] = "<option value="+value+" selected>"+value+"</option>"
		}else{
			a[i] = "<option value="+value+">"+value+"</option>"
		}
	}
	$('#eventStartDay').append(a.join(''));
	$('#eventEndDay').append(a.join(''));
	
	for(var i=0;i<24;i++){ 
		if(i<10){ value = "0"+i; } 
		else{ value=i; } 
		if(i==0){
		a[i] = "<option value="+value+" selected>"+value+"</option>"
		}else{
			a[i] = "<option value="+value+">"+value+"</option>"
		}
	}
	$('#eventStartTime').append(a.join(''));
	$('#eventEndTime').append(a.join(''));
	$('#ALeventStartTime').append(a.join(''));
	$('#ALeventEndTime').append(a.join(''));
	
	for(var i=0;i<60;i++){ 
		if(i<10){ value = "0"+i; } 
		else{ value=i; } 
		if(i==0){
			a[i] = "<option value="+value+" selected>"+value+"</option>"
			}else{
				a[i] = "<option value="+value+">"+value+"</option>"
			}
	}
	$('#eventStartMinute').append(a.join(''));
	$('#eventEndMinute').append(a.join(''));
	$('#ALeventStartMinute').append(a.join(''));
	$('#ALeventEndMinute').append(a.join(''));
	
}

// ENTER(13), ESC(27) KEYUP SEARCH
$(document).keyup(function(e) {
	if (e.keyCode == 13) { // ENTER
		$('#btnCalendarPopupSave').click();
		if($('#inputAge').is(':focus')){
		//	document.getElementById('inputAge').style.display = 'none';	
			
			if($('#inputAge').val() == ""){
				$('#inputAge').val(0);
			}
			$.ajax({
				url : "/email/age",
				method : "post",
				data : $('#inputAge').val(),
				success : function(data) {
					if (data == "OK") {
						age = $('inputAge');
						printEncourageKCAL();
					} else {
						console.log("UPDATE FAIL");
					}
				},
				error : function(request, status, error) {
					alert("다시 입력해주세요!");
				}
			});
		}
	}
	if (e.keyCode == 27) { // ESC
		$('#popupAlternateCalendarForm').hide(); 
		$('#popupCalendarForm').hide(); 
		$('#popupDietForm').hide();
		$('#chartContainer').hide();
		if($('#menu2').attr('class') == "open"){ 
			$('#menu-toggle').click();
		}
	}
});

// Age Input Check
function createViewAge(){
	if(age_flag == 0){
		document.getElementById('inputAge').style.display = 'inline';
		$('#inputAge').focus();
		document.getElementById('encKCAL').style.display = 'inline';
		age_flag = 1;
	}else if(age_flag == 1){
		age_flag = 0;
	}
}

//AgeForm FocusOut Function
var age;
function focusOut(){
	if(!$('#inputAge').is(':focus')){
		document.getElementById('inputAge').style.display = 'none';
		document.getElementById('encKCAL').style.display = 'none';
	}
};

//EncourageKcal Print Function
function printEncourageKCAL(){
	var tmp_age = $('#inputAge').val();
	var storeKCAL = 0;
	if(tmp_age>=10 && tmp_age<=12){
		storeKCAL = 1600;
	}else if(tmp_age>=13 && tmp_age<=15){
		storeKCAL = 1800;
	}else if(tmp_age>=16 && tmp_age<=19){
		storeKCAL = 2200;
	}else if(tmp_age>=20 && tmp_age<=29){
		storeKCAL = 2500;
	}else if(tmp_age>=30 && tmp_age<=49){
		storeKCAL = 2700;
	}else if(tmp_age>=50 && tmp_age<=64){
		storeKCAL = 2300;
	}else if(tmp_age>=65 && tmp_age<=74){
		storeKCAL = 2000;
	}else if(tmp_age>=75 && tmp_age<=100){
		storeKCAL = 1800;
	}else{
		alert("나이를 다시 입력해주세요");
	}
	$('#encKCAL').val("권장칼로리 : " + storeKCAL + "KCAL");
}

//create Chart Function
function createChart(){
	$('#chartContainer').show();
	 var chart = new CanvasJS.Chart("chartContainer",
			    {
			      title:{
			      text: "월별 칼로리"
			      },
			      axisY:{
			        title:"KCAL"   
			      },
			      animationEnabled: true,
			      data: [
			      {        
			        type: "stackedColumn",
			        toolTipContent: "{label}<br/><span style='\"'color: {color};'\"'><strong>{name}</strong></span>: {y}mn tonnes",
			        name: "아침",
			        showInLegend: "true",
			        dataPoints: []
			      },  {        
			        type: "stackedColumn",
			        toolTipContent: "{label}<br/><span style='\"'color: {color};'\"'><strong>{name}</strong></span>: {y}mn tonnes",
			        name: "점심",
			        showInLegend: "true",
			        dataPoints: []
			      },{        
				        type: "stackedColumn",
				        toolTipContent: "{label}<br/><span style='\"'color: {color};'\"'><strong>{name}</strong></span>: {y}mn tonnes",
				        name: "저녁",
				        showInLegend: "true",
				        dataPoints: []
				      }                      
			      ]
			      ,
			      legend:{
			        cursor:"pointer",
			        itemclick: function(e) {
			          if (typeof (e.dataSeries.visible) ===  "undefined" || e.dataSeries.visible) {
				          e.dataSeries.visible = false;
			          }
			          else
			          {
			            e.dataSeries.visible = true;
			          }
			          chart.render();
			        }
			      }
			    });
	 for(var i=0;i<json_Object.chartJSON.length;i++){
		 if(document.getElementsByTagName('h2')[0].innerHTML == "November 2016"){
				if(json_Object.chartJSON[i].date.substring(0,7) == "2016-11"){
					chart.options.data[0].dataPoints.push({ y: json_Object.chartJSON[i].Bkcal, label: json_Object.chartJSON[i].date});
				 	chart.options.data[1].dataPoints.push({ y: json_Object.chartJSON[i].Lkcal, label: json_Object.chartJSON[i].date});
				 	chart.options.data[2].dataPoints.push({ y: json_Object.chartJSON[i].Dkcal, label: json_Object.chartJSON[i].date});
				}
			} 
		 
		 if(document.getElementsByTagName('h2')[0].innerHTML == "December 2016"){
			if(json_Object.chartJSON[i].date.substring(0,7) == "2016-12"){
				chart.options.data[0].dataPoints.push({ y: json_Object.chartJSON[i].Bkcal, label: json_Object.chartJSON[i].date});
			 	chart.options.data[1].dataPoints.push({ y: json_Object.chartJSON[i].Lkcal, label: json_Object.chartJSON[i].date});
			 	chart.options.data[2].dataPoints.push({ y: json_Object.chartJSON[i].Dkcal, label: json_Object.chartJSON[i].date});
			}
		}
		 
		 if(document.getElementsByTagName('h2')[0].innerHTML == "January 2017"){
				if(json_Object.chartJSON[i].date.substring(0,7) == "2017-01"){
					chart.options.data[0].dataPoints.push({ y: json_Object.chartJSON[i].Bkcal, label: json_Object.chartJSON[i].date});
				 	chart.options.data[1].dataPoints.push({ y: json_Object.chartJSON[i].Lkcal, label: json_Object.chartJSON[i].date});
				 	chart.options.data[2].dataPoints.push({ y: json_Object.chartJSON[i].Dkcal, label: json_Object.chartJSON[i].date});
				}
		}
	 }
	 chart.render();
}
